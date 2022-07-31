
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
public class ListCobertura {

    @Expose
    private Long cobertura;
    @Expose
    private Double valor;
}
