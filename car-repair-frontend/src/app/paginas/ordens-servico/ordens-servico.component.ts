import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Cliente, OrdemItem, OrdemServico, OrdemStatus, Servico, Veiculo } from '../../modelos/modelos';
import { ClientesService } from '../../services/dominios/clientes.service';
import { OrdensServicoService } from '../../services/dominios/ordens-servico.service';
import { ServicosService } from '../../services/dominios/servicos.service';
import { VeiculosService } from '../../services/dominios/veiculos.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-ordens-servico',
  imports: [CommonModule, FormsModule],
  templateUrl: './ordens-servico.component.html',
  styleUrl: './ordens-servico.component.css'
})
export class OrdensServicoComponent implements OnInit {
  clientes: Cliente[] = [];
  veiculos: Veiculo[] = [];
  veiculosFiltrados: Veiculo[] = [];
  servicos: Servico[] = [];
  ordens: OrdemServico[] = [];
  errosFormulario: string[] = [];

  novaOrdem: Omit<OrdemServico, 'id'> = this.ordemVazia();
  itensTemp: OrdemItem[] = [];

  // campos temporários do item
  itemServicoId = '';
  itemQuantidade = 1;
  itemPrecoUnitario = 0;
  itemObservacao = '';

  readonly statusOpcoes: OrdemStatus[] = ['ABERTA', 'EM_ANDAMENTO', 'AGUARDANDO_PECAS', 'CONCLUIDA', 'CANCELADA'];

  constructor(
    private readonly clientesService: ClientesService,
    private readonly veiculosService: VeiculosService,
    private readonly servicosService: ServicosService,
    private readonly ordensService: OrdensServicoService,
    private readonly mensagemService: MensagemService
  ) {}

  ngOnInit(): void {
    forkJoin({
      clientes: this.clientesService.listar(),
      veiculos: this.veiculosService.listar(),
      servicos: this.servicosService.listar()
    }).subscribe({
      next: ({ clientes, veiculos, servicos }) => {
        this.clientes = clientes;
        this.veiculos = veiculos;
        this.servicos = servicos;
      },
      error: () => this.mensagemService.erro('Falha ao carregar dados de apoio.')
    });
    this.carregarOrdens();
  }

  onClienteChange(): void {
    this.novaOrdem.veiculoId = '';
    this.veiculosFiltrados = this.veiculos.filter(
      (v) => v.clienteId === this.novaOrdem['clienteId' as keyof typeof this.novaOrdem]
    );
  }

  preencherPreco(): void {
    const s = this.servicos.find((s) => s.id === this.itemServicoId);
    if (s) this.itemPrecoUnitario = s.precoBase;
  }

  adicionarItem(): void {
    if (!this.itemServicoId || this.itemQuantidade < 1 || this.itemPrecoUnitario <= 0) {
      this.mensagemService.aviso('Selecione o serviço, quantidade e preço.');
      return;
    }
    const svc = this.servicos.find((s) => s.id === this.itemServicoId);
    this.itensTemp = [
      ...this.itensTemp,
      {
        servicoId: this.itemServicoId,
        servicoNome: svc?.nome,
        quantidade: this.itemQuantidade,
        precoUnitario: this.itemPrecoUnitario,
        observacao: this.itemObservacao || undefined
      }
    ];
    this.itemServicoId = '';
    this.itemQuantidade = 1;
    this.itemPrecoUnitario = 0;
    this.itemObservacao = '';
  }

  removerItem(idx: number): void {
    this.itensTemp = this.itensTemp.filter((_, i) => i !== idx);
  }

  get totalItens(): number {
    return this.itensTemp.reduce((s, i) => s + i.precoUnitario * i.quantidade, 0);
  }

  salvar(form: NgForm): void {
    this.errosFormulario = this.validar();
    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios.');
      return;
    }
    const payload = { ...this.novaOrdem, itens: [...this.itensTemp] };
    this.ordensService.adicionar(payload).subscribe({
      next: () => {
        this.mensagemService.sucesso('Ordem de serviço cadastrada com sucesso.');
        form.resetForm();
        this.novaOrdem = this.ordemVazia();
        this.itensTemp = [];
        this.carregarOrdens();
      }
    });
  }

  alterarStatus(ordem: OrdemServico, status: OrdemStatus): void {
    this.ordensService.alterarStatus(ordem.id, status).subscribe({
      next: () => {
        this.mensagemService.sucesso(`Status alterado para ${status}.`);
        this.carregarOrdens();
      }
    });
  }

  nomeServico(id: string): string {
    return this.servicos.find((s) => s.id === id)?.nome ?? id;
  }

  private carregarOrdens(): void {
    this.ordensService.listar().subscribe({ next: (l) => (this.ordens = l) });
  }

  private validar(): string[] {
    const e: string[] = [];
    if (!this.novaOrdem.veiculoId) e.push('Selecione um veículo.');
    if (!this.novaOrdem.descricaoProblema?.trim()) e.push('Descreva o problema.');
    if (!this.itensTemp.length) e.push('Adicione ao menos um serviço à ordem.');
    return e;
  }

  private ordemVazia(): Omit<OrdemServico, 'id'> {
    return {
      veiculoId: '',
      descricaoProblema: '',
      observacoes: '',
      status: 'ABERTA',
      itens: []
    };
  }
}
