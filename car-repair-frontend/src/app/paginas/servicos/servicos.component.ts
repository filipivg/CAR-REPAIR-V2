import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Servico } from '../../modelos/modelos';
import { ServicosService } from '../../services/dominios/servicos.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-servicos',
  imports: [CommonModule, FormsModule],
  templateUrl: './servicos.component.html',
  styleUrl: './servicos.component.css'
})
export class ServicosComponent implements OnInit {
  servicos: Servico[] = [];
  errosFormulario: string[] = [];
  novoServico: Omit<Servico, 'id'> = this.servicoVazio();

  constructor(
    private readonly servicosService: ServicosService,
    private readonly mensagemService: MensagemService
  ) {}

  ngOnInit(): void { this.carregar(); }

  salvar(form: NgForm): void {
    this.errosFormulario = this.validar();
    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios.');
      return;
    }
    this.servicosService.adicionar(this.novoServico).subscribe({
      next: () => {
        this.mensagemService.sucesso('Serviço cadastrado com sucesso.');
        form.resetForm(this.servicoVazio());
        this.carregar();
      }
    });
  }

  private carregar(): void {
    this.servicosService.listar().subscribe({ next: (l) => (this.servicos = l) });
  }

  private validar(): string[] {
    const e: string[] = [];
    if (!this.novoServico.nome?.trim()) e.push('Informe o nome do serviço.');
    if (!this.novoServico.precoBase || this.novoServico.precoBase <= 0) e.push('Preço base deve ser maior que zero.');
    return e;
  }

  private servicoVazio(): Omit<Servico, 'id'> {
    return { nome: '', descricao: '', precoBase: 0, tempoEstimadoMinutos: undefined };
  }
}
