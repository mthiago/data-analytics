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

		List<ItemVendaModel> itens = new ArrayList<>();
		itens = buildItens(textoSeparado, itens);

		buildItens(textoSeparado, itens);

		buildQuantidadeVenda(vendedores, textoSeparado);

		VendaModel model = new VendaModel(Integer.valueOf(textoSeparado[1]), itens, textoSeparado[3]);

		vendas.add(model);	
	}

	private static void buildQuantidadeVenda(List<VendedorModel> vendedores, String[] textoSeparado) {
		for (VendedorModel v : vendedores) {
			if (v.getNomeVendedor().equals(textoSeparado[3])) {
				Integer quantidadeVenda = v.getQuantidadeVenda();
				if (quantidadeVenda == null) quantidadeVenda = 0;
				v.setQuantidadeVenda(quantidadeVenda+1);
			}
		}
	}

	private static List<ItemVendaModel> buildItens(String[] textoSeparado, List<ItemVendaModel> itens) {
		String item = textoSeparado[2].replace("[", "").replace("]", "");
		String[] itemFinal = item.split(",");
		String item1 = itemFinal[0];
		String item2 = itemFinal[1];
		String item3 = itemFinal[2];
		String[] dadosItem1 = item1.split("-");
		ItemVendaModel itemVenda1 = new ItemVendaModel(dadosItem1[0], dadosItem1[1], dadosItem1[2]);
		String[] dadosItem2 = item2.split("-");
		ItemVendaModel itemVenda2 = new ItemVendaModel(dadosItem2[0], dadosItem2[1], dadosItem2[2]);
		String[] dadosItem3 = item3.split("-");
		ItemVendaModel itemVenda3 = new ItemVendaModel(dadosItem3[0], dadosItem3[1], dadosItem3[2]);
		itens.add(itemVenda1);
		itens.add(itemVenda2);
		itens.add(itemVenda3);
		return itens;
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