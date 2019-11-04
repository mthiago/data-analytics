package br.com.analisedados.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.analisedados.controller.FileController;
import br.com.analisedados.enums.TipoDadosEnum;
import br.com.analisedados.model.ClienteModel;
import br.com.analisedados.model.ItemVendaModel;
import br.com.analisedados.model.VendaModel;
import br.com.analisedados.model.VendedorModel;


@SpringBootApplication
public class AnaliseDadosService {

	final static Logger logger = LogManager.getLogger(AnaliseDadosService.class);

	public static void main(String[] args) {

		try {
			
			
			//Lista os arquivos de um diretorio
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
				Integer idVendaMaisCara = verificaVendaMaisCara(vendas);
				String nomePiorVendedor = verificaPiorVendedor(vendedores);
				logger.info("Quantidade de clientes: " + clientes.size());
				logger.info("Quantidade de vendedores: " + vendedores.size());
				logger.info("Id venda mais cara: " + idVendaMaisCara.toString());
				logger.info("Pior vendedor: " + nomePiorVendedor);
			}

			//Monitora um diretorio em busca de criacoes/modificacoes
			FileController.monitorarDiretorio();
		} catch (Exception e) {
			logger.error(e.getCause());
		}

	}
	
	private static String verificaPiorVendedor(List<VendedorModel> vendedores) {
		String nomePiorVendedor = "";
		Integer quantidadeVendasPiorVendedor = null;
		for (VendedorModel v : vendedores) {
			if (quantidadeVendasPiorVendedor == null) {
				quantidadeVendasPiorVendedor = v.getQuantidadeVenda();
				nomePiorVendedor = v.getNomeVendedor();
			}
			else if (quantidadeVendasPiorVendedor > v.getQuantidadeVenda()) {
				nomePiorVendedor = v.getNomeVendedor();
			}
		}
		return nomePiorVendedor;
	}

	private static Integer verificaVendaMaisCara(List<VendaModel> vendas) {
		Integer idVendaMaisCara = 1;
		BigDecimal valorVendaMaisCara = new BigDecimal(1.0);
		for (VendaModel v : vendas) {
			for (ItemVendaModel i : v.getItemVendaModel()) {
				if (i.getPrecoItem().compareTo(valorVendaMaisCara) == 1) {
					valorVendaMaisCara = i.getPrecoItem();
					idVendaMaisCara = v.getCodigoVenda();
				}
			}
		}
		return idVendaMaisCara;
	}

}