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
			ArrayList<String> arquivos = FileController.listarArquivos();

			//Verifica os dados de um arquivo
			for (String arquivo : arquivos) {
				List<String> dadosArquivo = FileController.verificarDadosArquivo(arquivo);	

				List<VendedorModel> vendedores = new ArrayList<>();
				List<ClienteModel> clientes = new ArrayList<>();
				List<VendaModel> vendas = new ArrayList<>();
				consultaInfosVendaArquivo(dadosArquivo, vendedores, clientes, vendas);

				FileController.montaRetorno(vendedores, clientes, vendas);
			}

			//Monitora um diretório em busca de criações/modificações
			FileController.monitorarDiretorio();
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	private static void consultaInfosVendaArquivo(List<String> dadosArquivo, List<VendedorModel> vendedores, List<ClienteModel> clientes, List<VendaModel> vendas) {
		for (String dados : dadosArquivo) {
			if (dados.startsWith(TipoDadosEnum.VENDEDOR.codigoDados())) {
				criaDadosVendedor(dados, vendedores);
			}
			else if (dados.startsWith(TipoDadosEnum.CLIENTE.codigoDados())) {
				criaDadosCliente(dados, clientes);
			}
			else if (dados.startsWith(TipoDadosEnum.VENDA.codigoDados())) {
				criaDadosVenda(dados, vendas, vendedores);
			}
		}
	}

	private static void criaDadosVenda(String linha, List<VendaModel> vendas, List<VendedorModel> vendedores) {

		String[] textoSeparado = linha.split("ç");
		String item = textoSeparado[2].replace("[", "").replace("]", "");
		List<ItemVendaModel> itens = buildItens(item);

		buildQuantidadeVenda(vendedores, textoSeparado);

		VendaModel model = new VendaModel(Integer.valueOf(textoSeparado[1]), itens, textoSeparado[3]);

		vendas.add(model);	
	}

	private static void buildQuantidadeVenda(List<VendedorModel> vendedores, String[] textoSeparado) {
		String nomeVendedor = textoSeparado[3];
		VendedorModel vendedor = vendedores.stream().filter(vend -> vend.getNomeVendedor().equals(nomeVendedor)).findFirst().get();
		
		Integer quantidadeVenda = vendedor.getQuantidadeVenda();
		if (quantidadeVenda == null) quantidadeVenda = 0;
		vendedor.setQuantidadeVenda(quantidadeVenda+1);

	}

	private static List<ItemVendaModel> buildItens(String item) {
		String[] itemFinal = item.split(",");
		String item1 = itemFinal[0];
		String item2 = itemFinal[1];
		String item3 = itemFinal[2];
		
		ItemVendaModel itemVenda1 = separaItens(item1);
		ItemVendaModel itemVenda2 = separaItens(item2);
		ItemVendaModel itemVenda3 = separaItens(item3);

		List<ItemVendaModel> itens = new ArrayList<>();
		itens.add(itemVenda1);
		itens.add(itemVenda2);
		itens.add(itemVenda3);
		return itens;
	}

	private static ItemVendaModel separaItens(String item) {
		String[] dadosItem = item.split("-");
		ItemVendaModel itemVenda = new ItemVendaModel(dadosItem[0], dadosItem[1], dadosItem[2]);
		return itemVenda;
	}

	private static void criaDadosCliente(String linha, List<ClienteModel> clientes) {
		String[] textoSeparado = linha.split("ç");
		ClienteModel model = new ClienteModel(Long.valueOf(textoSeparado[1]), textoSeparado[2], textoSeparado[3]);
		clientes.add(model);	
	}

	private static void criaDadosVendedor(String linha, List<VendedorModel> vendedores) {
		String[] textoSeparado = linha.split("ç");
		VendedorModel model = new VendedorModel(Long.valueOf(textoSeparado[1]), textoSeparado[2], textoSeparado[3], null);
		vendedores.add(model);
	}
}