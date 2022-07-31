package br.com.confitec.teste.usecase.interfaces;

import br.com.confitec.teste.dto.request.RequestDTO;
import br.com.confitec.teste.dto.response.DadosDTO;

public interface ParcelamentoUseCase {

    DadosDTO execute(RequestDTO dto);
}
