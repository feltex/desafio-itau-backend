package br.com.feltex.desafioitau.transacao;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequest {

  private BigDecimal valor;
  private OffsetDateTime dataHora;

}
