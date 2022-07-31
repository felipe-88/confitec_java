
package br.com.confitec.teste.dto.response;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DadoDTO {

    @Expose
    private Long quantidadeParcelas;
    @Expose
    private Double valorParcelamentoTotal;
    @Expose
    private Double valorPrimeiraParcela;
    @Expose
    private Double valorDemaisParcelas;
}
