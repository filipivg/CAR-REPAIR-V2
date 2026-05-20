import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { ClientesService } from '../../services/dominios/clientes.service';
import { VeiculosService } from '../../services/dominios/veiculos.service';
import { ServicosService } from '../../services/dominios/servicos.service';
import { OrdensServicoService } from '../../services/dominios/ordens-servico.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  totalClientes = 0;
  totalVeiculos = 0;
  totalServicos = 0;
  totalOrdens = 0;
  ordensAbertas = 0;
  ordensEmAndamento = 0;
  ordensConcluidas = 0;
  carregando = true;

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
      servicos: this.servicosService.listar(),
      ordens: this.ordensService.listar()
    }).subscribe({
      next: ({ clientes, veiculos, servicos, ordens }) => {
        this.totalClientes = clientes.length;
        this.totalVeiculos = veiculos.length;
        this.totalServicos = servicos.length;
        this.totalOrdens = ordens.length;
        this.ordensAbertas = ordens.filter((o) => o.status === 'ABERTA').length;
        this.ordensEmAndamento = ordens.filter((o) => o.status === 'EM_ANDAMENTO').length;
        this.ordensConcluidas = ordens.filter((o) => o.status === 'CONCLUIDA').length;
        this.carregando = false;
      },
      error: () => {
        this.mensagemService.erro('Não foi possível carregar os indicadores.');
        this.carregando = false;
      }
    });
  }
}
