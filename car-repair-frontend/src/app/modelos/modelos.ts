// ─── Paginação ────────────────────────────────────────────────
export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

// ─── Cliente ──────────────────────────────────────────────────
export interface Cliente {
  id: string;
  nome: string;
  cpf: string;
  telefone: string;
  email?: string;
  endereco?: string;
  bairro?: string;
  cidade?: string;
  estado?: string;
  cep?: string;
  dataHoraCriacao?: string;
  ativo?: boolean;
}

// ─── Veículo ──────────────────────────────────────────────────
export interface Veiculo {
  id: string;
  clienteId: string;
  clienteNome?: string;
  placa: string;
  marca: string;
  modelo: string;
  anoFabricacao?: number;
  anoModelo?: number;
  cor?: string;
  renavam?: string;
  combustivel?: string;
  dataHoraCriacao?: string;
  ativo?: boolean;
}

// ─── Serviço (catálogo) ───────────────────────────────────────
export interface Servico {
  id: string;
  nome: string;
  descricao?: string;
  precoBase: number;
  tempoEstimadoMinutos?: number;
  ativo?: boolean;
}

// ─── Ordem de Serviço ─────────────────────────────────────────
export type OrdemStatus =
  | 'ABERTA'
  | 'EM_ANDAMENTO'
  | 'AGUARDANDO_PECAS'
  | 'CONCLUIDA'
  | 'CANCELADA';

export interface OrdemItem {
  id?: string;
  servicoId: string;
  servicoNome?: string;
  quantidade: number;
  precoUnitario: number;
  subtotal?: number;
  observacao?: string;
}

export interface OrdemServico {
  id: string;
  veiculoId: string;
  veiculoPlaca?: string;
  veiculoModelo?: string;
  clienteNome?: string;
  status?: OrdemStatus;
  descricaoProblema?: string;
  diagnostico?: string;
  observacoes?: string;
  dataPrevisaoEntrega?: string;
  dataConclusao?: string;
  kmVeiculo?: number;
  valorTotal?: number;
  itens: OrdemItem[];
  dataHoraCriacao?: string;
  ativo?: boolean;
}
