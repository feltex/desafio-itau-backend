package br.com.feltex.desafioitau.transacao;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transacao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public record TransacaoController(TransacaoRepository transacaoRepository) {

  @PostMapping
  public ResponseEntity adicionar(@RequestBody TransacaoRequest transacaoRequest) {
    log.info("Adicionando transacao");

    try {
      validarTransacao(transacaoRequest);
      transacaoRepository.add(transacaoRequest);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (IllegalArgumentException illegalArgumentException) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }


  @DeleteMapping
  public ResponseEntity limpar() {
    log.info("Limpando Transacoes");
    transacaoRepository.limpar();
    return ResponseEntity.status(HttpStatus.OK).build();
  }


  private void validarTransacao(TransacaoRequest transacaoRequest) {

    if (transacaoRequest.getValor().compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Valor da transação inválido!");
    }
    if (transacaoRequest.getDataHora().isAfter(OffsetDateTime.now())) {
      throw new IllegalArgumentException("Data da transação inválida");
    }
  }


}
