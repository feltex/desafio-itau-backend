package br.com.feltex.desafioitau.transacao;

import br.com.feltex.desafioitau.estatistica.EstatisticaDTO;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import org.springframework.stereotype.Repository;

@Repository
public class TransacaoRepository {

  private final List<TransacaoRequest> transacoes = new ArrayList<>();


  public void add(TransacaoRequest transacaoRequest) {
    transacoes.add(transacaoRequest);
  }

  public void limpar() {
    transacoes.clear();
  }

  public EstatisticaDTO estatistica(OffsetDateTime horaInicial) {

    if (transacoes.isEmpty()) {
      return new EstatisticaDTO();
    } else {

      final BigDecimal[] valoresFiltrados = transacoes.stream()
          .filter(t -> t.getDataHora().isAfter(horaInicial) || t.getDataHora().equals(horaInicial))
          .map(t -> t.getValor()).toArray(BigDecimal[]::new);
      DoubleStream doubleStream = Arrays.stream(valoresFiltrados).mapToDouble(BigDecimal::doubleValue);
      return new EstatisticaDTO(doubleStream.summaryStatistics());
    }
  }

}
