package br.com.confitec.teste.adapters.http;

import br.com.confitec.teste.dto.request.RequestDTO;
import br.com.confitec.teste.dto.response.DadosDTO;
import br.com.confitec.teste.usecase.interfaces.ParcelamentoUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ParcelamentoRestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ParcelamentoUseCase useCase;

    @Test
    @DisplayName("Should return 200 when the request is valid")
    void parcelamentoWhenRequestIsValidThenReturn200() {
        RequestDTO request = new RequestDTO();
        request.setListCobertura(new ArrayList<>());
        request.setListOpcaoParcelamento(new ArrayList<>());

        DadosDTO dados = new DadosDTO(Collections.emptyList());

        when(useCase.execute(request)).thenReturn(dados);

        ResponseEntity<DadosDTO> response =
                restTemplate.postForEntity("/confitec/teste/parcelamento", request, DadosDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dados, response.getBody());
    }

    @Test
    @DisplayName("Should return 400 when the request is invalid")
    void parcelamentoWhenRequestIsInvalidThenReturn400() {
        RequestDTO request = new RequestDTO();
        request.setListCobertura(Collections.emptyList());
        request.setListOpcaoParcelamento(Collections.emptyList());

        ResponseEntity<DadosDTO> response =
                restTemplate.postForEntity("/confitec/teste/parcelamento", request, DadosDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}