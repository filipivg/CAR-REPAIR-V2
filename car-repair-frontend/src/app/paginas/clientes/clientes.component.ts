import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { validarCpfBasico, validarTelefoneBasico } from '../../core/validacoes/campos.util';
import { Cliente } from '../../modelos/modelos';
import { ClientesService } from '../../services/dominios/clientes.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-clientes',
  imports: [CommonModule, FormsModule],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.css'
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[] = [];
  errosFormulario: string[] = [];

  novoCliente: Omit<Cliente, 'id'> = this.clienteVazio();

  constructor(
    private readonly clientesService: ClientesService,
    private readonly mensagemService: MensagemService
  ) {}

  ngOnInit(): void { this.carregar(); }

  salvar(form: NgForm): void {
    this.errosFormulario = this.validar();
    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios.');
      return;
    }
    this.clientesService.adicionar(this.novoCliente).subscribe({
      next: () => {
        this.mensagemService.sucesso('Cliente salvo com sucesso.');
        form.resetForm(this.clienteVazio());
        this.carregar();
      }
    });
  }

  private carregar(): void {
    this.clientesService.listar().subscribe({
      next: (lista) => (this.clientes = lista)
    });
  }

  private validar(): string[] {
    const e: string[] = [];
    if (!this.novoCliente.nome?.trim()) e.push('Informe o nome.');
    if (!validarCpfBasico(this.novoCliente.cpf)) e.push('CPF deve ter 11 dígitos.');
    if (!validarTelefoneBasico(this.novoCliente.telefone)) e.push('Telefone inválido.');
    return e;
  }

  private clienteVazio(): Omit<Cliente, 'id'> {
    return { nome: '', cpf: '', telefone: '', email: '' };
  }
}
