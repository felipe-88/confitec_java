package br.com.confitec.teste.usecase.impl;

import br.com.confitec.teste.dto.request.ListOpcaoParcelamento;
import br.com.confitec.teste.dto.request.RequestDTO;
import br.com.confitec.teste.dto.response.DadoDTO;
import br.com.confitec.teste.dto.response.DadosDTO;
import br.com.confitec.teste.usecase.interfaces.ParcelamentoUseCase;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

@Component
public class ParcelamentoUseCaseImpl implements ParcelamentoUseCase {

    public DadosDTO execute(RequestDTO dto) {
        List<DadoDTO> dados = new ArrayList<>();
        List<ListOpcaoParcelamento> listOpcaoParcelamento = dto.getListOpcaoParcelamento();

        listOpcaoParcelamento.forEach(op -> {
            Long min = op.getQuantidadeMinimaParcelas();
            Long max = op.getQuantidadeMaximaParcelas();
            for (int i= min.intValue(); i < max.intValue(); i++) {
                Double valorParcelamentoTotal = Double.valueOf(0);
                Double valorPrimeiraParcela = Double.valueOf(0);
                Double valorDemaisParcelas = Double.valueOf(0);

                if (op.getJuros().equals(0))
                    valorParcelamentoTotal = calcularValorParcelamentoTotal(dto);
                else
                    valorParcelamentoTotal = calcularValorASerPago(calcularValorParcelamentoTotal(dto),op.getJuros(),i);

                valorDemaisParcelas = calcularDemaisParcelas(valorParcelamentoTotal, i);
                valorPrimeiraParcela = calcularPrimParcela(valorParcelamentoTotal, i);

                dados.add(new DadoDTO(Long.valueOf(i), valorParcelamentoTotal, valorPrimeiraParcela, valorDemaisParcelas));
            }
        });
        return new DadosDTO(dados);
    }

    private Double calcularValorParcelamentoTotal(RequestDTO dto) {
        DoubleStream doubleStream = dto.getListCobertura().stream().mapToDouble(listCobertura -> listCobertura.getValor());
        return doubleStream.sum();
    }

    private Double calcularValorASerPago(Double valorTotal, Double taxa, int qtdParcelas) {
        return valorTotal *  Math.pow((1 + taxa.doubleValue()), qtdParcelas);
    }

    private Double calcularResto(Double valorASerPago, int qtdParcelas) {
        return valorASerPago % qtdParcelas;
    }

    private Double calcularDiferenca(Double valorASerPago, int qtdParcelas) {
        return valorASerPago - calcularResto(valorASerPago, qtdParcelas);
    }

    private Double calcularDemaisParcelas(Double valorASerPago, int qtdParcelas) {
        Double diferenca = calcularDiferenca(valorASerPago,qtdParcelas);
        return diferenca / qtdParcelas;
    }

    private Double calcularPrimParcela(Double valorASerPago, int qtdParcelas) {
        return calcularDemaisParcelas(valorASerPago,qtdParcelas) + calcularResto(valorASerPago,qtdParcelas);
    }
}
