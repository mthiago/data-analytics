package br.com.analisedados.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.analisedados.controller.FileController;
import br.com.analisedados.enums.TipoDadosEnum;
import br.com.analisedados.model.ClienteModel;
import br.com.analisedados.model.ItemVendaModel;
import br.com.analisedados.model.VendaModel;
import br.com.analisedados.model.VendedorModel;

@SpringBootApplication
public class AnaliseDadosService {

	public static void main(String[] args) {
		try {
			//Lista os arquivos de um diretório
			ArrayList<String> listaArquivos = FileController.listarArquivos();

			//Verifica os dados de um arquivo
			for (String arquivo : listaArquivos) {
				List<String> listaDadosArquivo = FileController.verificarDadosArquivo(arquivo);	

				List<VendedorModel> vendedores = new ArrayList<>();
				List<ClienteModel> clientes = new ArrayList<>();
				List<VendaModel> vendas = new ArrayList<>();
				
				for (String dadosArquivo : listaDadosArquivo) {
					if (dadosArquivo.startsWith(TipoDadosEnum.VENDEDOR.codigoDados())) {
						VendedorService.criaDadosVendedor(dadosArquivo, vendedores);
					}
					else if (dadosArquivo.startsWith(TipoDadosEnum.CLIENTE.codigoDados())) {
						ClienteService.criaDadosCliente(dadosArquivo, clientes);
					}
					else if (dadosArquivo.startsWith(TipoDadosEnum.VENDA.codigoDados())) {
						VendaService.criaDadosVenda(dadosArquivo, vendas, vendedores);
					}					
				}
				FileController.montaRetorno(vendedores, clientes, vendas);
			}

			//Monitora um diretório em busca de criações/modificações
			FileController.monitorarDiretorio();
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

}