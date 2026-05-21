import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { validarPlacaBasica } from '../../core/validacoes/campos.util';
import { Cliente, Veiculo } from '../../modelos/modelos';
import { ClientesService } from '../../services/dominios/clientes.service';
import { VeiculosService } from '../../services/dominios/veiculos.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-veiculos',
  imports: [CommonModule, FormsModule],
  templateUrl: './veiculos.component.html',
  styleUrl: './veiculos.component.css'
})
export class VeiculosComponent implements OnInit {
  clientes: Cliente[] = [];
  veiculos: Veiculo[] = [];
  errosFormulario: string[] = [];
  novoVeiculo: Omit<Veiculo, 'id'> = this.veiculoVazio();
  readonly anoAtual = new Date().getFullYear();

  constructor(
    private readonly clientesService: ClientesService,
    private readonly veiculosService: VeiculosService,
    private readonly mensagemService: MensagemService
  ) {}

  ngOnInit(): void {
    this.clientesService.listar().subscribe({ next: (l) => (this.clientes = l) });
    this.carregarVeiculos();
  }

  salvar(form: NgForm): void {
    this.errosFormulario = this.validar();
    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios.');
      return;
    }
    this.veiculosService.adicionar(this.novoVeiculo).subscribe({
      next: () => {
        this.mensagemService.sucesso('Veículo salvo com sucesso.');
        form.resetForm(this.veiculoVazio());
        this.carregarVeiculos();
      }
    });
  }

  nomeCliente(clienteId: string): string {
    return this.clientes.find((c) => c.id === clienteId)?.nome ?? clienteId;
  }

  private carregarVeiculos(): void {
    this.veiculosService.listar().subscribe({ next: (l) => (this.veiculos = l) });
  }

  private validar(): string[] {
    const e: string[] = [];
    if (!this.novoVeiculo.clienteId) e.push('Selecione um cliente.');
    if (!validarPlacaBasica(this.novoVeiculo.placa)) e.push('Placa inválida (ex.: ABC-1234).');
    if (!this.novoVeiculo.marca?.trim()) e.push('Informe a marca.');
    if (!this.novoVeiculo.modelo?.trim()) e.push('Informe o modelo.');
    const ano = this.novoVeiculo.anoFabricacao ?? 0;
    if (ano < 1950 || ano > this.anoAtual) e.push(`Ano entre 1950 e ${this.anoAtual}.`);
    return e;
  }

  private veiculoVazio(): Omit<Veiculo, 'id'> {
    return {
      clienteId: '',
      placa: '',
      marca: '',
      modelo: '',
      anoFabricacao: this.anoAtual,
      anoModelo: this.anoAtual,
      cor: '',
      combustivel: ''
    };
  }
}
