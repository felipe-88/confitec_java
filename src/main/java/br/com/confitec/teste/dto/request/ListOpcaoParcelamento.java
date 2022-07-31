
package br.com.confitec.teste.dto.request;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListOpcaoParcelamento {

    @Expose
    private Double juros;
    @Expose
    private Long quantidadeMaximaParcelas;
    @Expose
    private Long quantidadeMinimaParcelas;

}
