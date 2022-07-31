package br.com.confitec.teste.adapters.http;

import br.com.confitec.teste.dto.request.RequestDTO;
import br.com.confitec.teste.dto.response.DadosDTO;
import br.com.confitec.teste.usecase.interfaces.ParcelamentoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/confitec/teste")
public class ParcelamentoRestController {

    private final ParcelamentoUseCase useCase;

    @PostMapping("parcelamento")
    public ResponseEntity<DadosDTO> parcelamento(@RequestBody RequestDTO request) {
        return new ResponseEntity<>(useCase.execute(request), HttpStatus.OK);
    }

}
