
package br.com.confitec.teste.dto.request;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    @Expose
    private List<ListCobertura> listCobertura;
    @Expose
    private List<ListOpcaoParcelamento> listOpcaoParcelamento;
}
