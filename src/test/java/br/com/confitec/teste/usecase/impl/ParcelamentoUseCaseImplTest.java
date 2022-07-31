package br.com.confitec.teste.usecase.impl;

import br.com.confitec.teste.dto.request.ListCobertura;
import br.com.confitec.teste.dto.request.ListOpcaoParcelamento;
import br.com.confitec.teste.dto.request.RequestDTO;
import br.com.confitec.teste.dto.response.DadoDTO;
import br.com.confitec.teste.dto.response.DadosDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ParcelamentoUseCaseImplTest {

    @InjectMocks
    private ParcelamentoUseCaseImpl parcelamentoUseCase;

    @Test
    @DisplayName(
            "Should return the first installment when the value to be paid is greater than zero")
    void calcularPrimParcelaWhenValorASerPagoIsGreaterThanZeroThenReturnFirstInstallment() {
        RequestDTO requestDTO = new RequestDTO();
        List<ListCobertura> listCobertura = new ArrayList<>();
        List<ListOpcaoParcelamento> listOpcaoParcelamento = new ArrayList<>();

        listCobertura.add(new ListCobertura(1L, 100.00));
        listCobertura.add(new ListCobertura(2L, 200.00));
        listCobertura.add(new ListCobertura(3L, 300.00));

        listOpcaoParcelamento.add(new ListOpcaoParcelamento(0.0, 12L, 1L));

        requestDTO.setListCobertura(listCobertura);
        requestDTO.setListOpcaoParcelamento(listOpcaoParcelamento);

        DadosDTO dadosDTO = parcelamentoUseCase.execute(requestDTO);

        assertNotNull(dadosDTO);
        assertNotNull(dadosDTO.getDados());
        assertEquals(11, dadosDTO.getDados().size());

        DadoDTO dadoDTO = dadosDTO.getDados().get(0);
        assertEquals(1, dadoDTO.getQuantidadeParcelas().intValue());
        assertEquals(600, dadoDTO.getValorParcelamentoTotal().intValue());
        assertEquals(600, dadoDTO.getValorPrimeiraParcela().intValue());
    }

    @Test
    @DisplayName("Should return zero when the value to be paid is less than or equal to zero")
    void calcularPrimParcelaWhenValorASerPagoIsLessThanOrEqualToZeroThenReturnZero() {
        Double valorASerPago = Double.valueOf(0);
        int qtdParcelas = 1;
        Double result = (Double) ReflectionTestUtils.invokeMethod(parcelamentoUseCase, "calcularPrimParcela", valorASerPago, qtdParcelas);

        assertEquals(Double.valueOf(0), result);
    }

    @Test
    @DisplayName("Should return the value to be paid when the interest rate is zero")
    void calcularValorASerPagoWhenJurosIsZeroThenReturnValueToBePaid() {
        List<ListCobertura> listCobertura = new ArrayList<>();
        listCobertura.add(new ListCobertura(1L, 100.00));
        listCobertura.add(new ListCobertura(2L, 200.00));
        listCobertura.add(new ListCobertura(3L, 300.00));

        List<ListOpcaoParcelamento> listOpcaoParcelamento = new ArrayList<>();
        listOpcaoParcelamento.add(new ListOpcaoParcelamento(0.0, 12L, 1L));

        RequestDTO requestDTO = new RequestDTO(listCobertura, listOpcaoParcelamento);

        DadosDTO dadosDTO = parcelamentoUseCase.execute(requestDTO);

        assertNotNull(dadosDTO);
        assertEquals(600.00, dadosDTO.getDados().get(0).getValorParcelamentoTotal());
    }

    @Test
    @DisplayName(
            "Should return the correct value when the value to be paid is not divisible by the number of installments")
    void
    calcularDemaisParcelasWhenValorASerPagoIsNotDivisibleByQtdParcelasThenReturnCorrectValue() {
        Double valorASerPago = Double.valueOf(100);
        int qtdParcelas = 3;
        Double expected = Double.valueOf(33.33);

        Double actual = (Double) ReflectionTestUtils.invokeMethod(parcelamentoUseCase, "calcularDemaisParcelas", valorASerPago, qtdParcelas);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName(
            "Should return the correct value when the value to be paid is divisible by the number of installments")
    void calcularDemaisParcelasWhenValorASerPagoIsDivisibleByQtdParcelasThenReturnCorrectValue() {
        Double valorASerPago = Double.valueOf(100);
        int qtdParcelas = 2;
        Double result = (Double) ReflectionTestUtils.invokeMethod(parcelamentoUseCase, "calcularDemaisParcelas", valorASerPago, qtdParcelas);

        assertEquals(Double.valueOf(50), result);
    }

    @Test
    @DisplayName(
            "Should return the difference between the value to be paid and the remainder when the value to be paid is greater than zero")
    void
    calcularDiferencaWhenValorASerPagoIsGreaterThanZeroThenReturnTheDifferenceBetweenTheValueToBePaidAndTheRemainder() {
        Double valorASerPago = Double.valueOf(100);
        int qtdParcelas = 10;
        Double diferenca = (Double) ReflectionTestUtils.invokeMethod(parcelamentoUseCase, "calcularDiferenca", valorASerPago, qtdParcelas);

        assertNotNull(diferenca);
        assertEquals(90, diferenca);
    }

    @Test
    @DisplayName("Should return zero when the value to be paid is less than or equal to zero")
    void calcularDiferencaWhenValorASerPagoIsLessThanOrEqualToZeroThenReturnZero() {
        Double valorASerPago = Double.valueOf(0);
        int qtdParcelas = 1;

        Double result = (Double) ReflectionTestUtils.invokeMethod(parcelamentoUseCase, "calcularDiferenca", valorASerPago, qtdParcelas);

        assertEquals(Double.valueOf(0), result);
    }

    @Test
    @DisplayName("Should return the rest of the division between valorASerPago and qtdParcelas")
    void calcularRestoShouldReturnTheRestOfTheDivisionBetweenValorASerPagoAndQtdParcelas() {
        Double valorASerPago = Double.valueOf(10);
        int qtdParcelas = 2;
        Double result = (Double) ReflectionTestUtils.invokeMethod(parcelamentoUseCase, "calcularResto", valorASerPago, qtdParcelas);

        assertNotNull(result);
        assertEquals(Double.valueOf(0), result);
    }

    @Test
    @DisplayName("Should return the sum of all values in listCobertura")
    void calcularValorParcelamentoTotalShouldReturnSumOfAllValuesInListCobertura() {
        RequestDTO requestDTO = new RequestDTO();
        List<ListCobertura> listCobertura = new ArrayList<>();
        listCobertura.add(new ListCobertura(1L, 100.00));
        listCobertura.add(new ListCobertura(2L, 200.00));
        listCobertura.add(new ListCobertura(3L, 300.00));
        requestDTO.setListCobertura(listCobertura);
        Double result = (Double) ReflectionTestUtils.invokeMethod(parcelamentoUseCase, "calcularValorParcelamentoTotal", requestDTO);

        assertNotNull(result);
        assertEquals(600.00, result);
    }

    @Test
    @DisplayName("Should return a list of DadoDTO when the listOpcaoParcelamento is not empty")
    void executeWhenListOpcaoParcelamentoIsNotEmptyThenReturnListOfDadoDTO() {
        RequestDTO requestDTO = new RequestDTO();
        List<ListCobertura> listCobertura = new ArrayList<>();
        List<ListOpcaoParcelamento> listOpcaoParcelamento = new ArrayList<>();
        ListOpcaoParcelamento listOpcaoParcelamento1 = new ListOpcaoParcelamento();
        listOpcaoParcelamento1.setJuros(0.0);
        listOpcaoParcelamento1.setQuantidadeMaximaParcelas(3L);
        listOpcaoParcelamento1.setQuantidadeMinimaParcelas(2L);
        listOpcaoParcelamento.add(listOpcaoParcelamento1);
        requestDTO.setListCobertura(listCobertura);
        requestDTO.setListOpcaoParcelamento(listOpcaoParcelamento);

        DadosDTO dadosDTO = parcelamentoUseCase.execute(requestDTO);

        assertNotNull(dadosDTO);
    }

    @Test
    @DisplayName("Should return a list of DadoDTO when the listCobertura is not empty")
    void executeWhenListCoberturaIsNotEmptyThenReturnListOfDadoDTO() {
        List<ListCobertura> listCobertura = new ArrayList<>();
        listCobertura.add(new ListCobertura(1L, Double.valueOf(100)));
        listCobertura.add(new ListCobertura(2L, Double.valueOf(200)));
        listCobertura.add(new ListCobertura(3L, Double.valueOf(300)));

        List<ListOpcaoParcelamento> listOpcaoParcelamento = new ArrayList<>();
        listOpcaoParcelamento.add(
                new ListOpcaoParcelamento(Double.valueOf(0), Long.valueOf(3), Long.valueOf(3)));

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setListCobertura(listCobertura);
        requestDTO.setListOpcaoParcelamento(listOpcaoParcelamento);

        List<DadoDTO> dados = new ArrayList<>();
        dados.add(
                new DadoDTO(
                        Long.valueOf(3),
                        Double.valueOf(600),
                        Double.valueOf(200),
                        Double.valueOf(200)));

        DadosDTO dadosDTO = new DadosDTO();
        dadosDTO.setDados(dados);

        DadosDTO result = parcelamentoUseCase.execute(requestDTO);

        assertEquals(dadosDTO, result);
    }
}