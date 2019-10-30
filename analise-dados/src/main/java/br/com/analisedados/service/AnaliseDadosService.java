package br.com.analisedados.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.analisedados.controller.FileController;
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
			if (dados.startsWith("001")) {
				criaDadosVendedor(dados, vendedores);
			}
			else if (dados.startsWith("002")) {
				criaDadosCliente(dados, clientes);
			}
			else if (dados.startsWith("003")) {
				criaDadosVenda(dados, vendas, vendedores);
			}
		}
		
	}

	private static void criaDadosVenda(String linha, List<VendaModel> vendas, List<VendedorModel> vendedores) {
		VendaModel model = new VendaModel();
		String[] textoSeparado = linha.split("ç");
		model.setSaleId(Integer.valueOf(textoSeparado[1]));
		String item = textoSeparado[2];
		String itemSemColcheteEsquerda = item.replace("[", "");
		String itemSemColcheteDireita = itemSemColcheteEsquerda.replace("]", "");
		String[] itemFinal = itemSemColcheteDireita.split(",");
		String item1 = itemFinal[0];
		String item2 = itemFinal[1];
		String item3 = itemFinal[2];
		String[] dadosItem1 = item1.split("-");
		ItemVendaModel itemVenda1 = new ItemVendaModel(dadosItem1[0], dadosItem1[1], dadosItem1[2]);
		String[] dadosItem2 = item2.split("-");
		ItemVendaModel itemVenda2 = new ItemVendaModel(dadosItem2[0], dadosItem2[1], dadosItem2[2]);
		String[] dadosItem3 = item3.split("-");
		ItemVendaModel itemVenda3 = new ItemVendaModel(dadosItem3[0], dadosItem3[1], dadosItem3[2]);
		List<ItemVendaModel> itens = new ArrayList<>();
		itens.add(itemVenda1);
		itens.add(itemVenda2);
		itens.add(itemVenda3);
		model.setItemVendaModel(itens);
		model.setNomeVendedor(textoSeparado[3]);

		for (VendedorModel v : vendedores) {
			if (v.getName().equals(textoSeparado[3])) {
				Integer quantidadeVenda = v.getQuantidadeVenda();
				if (quantidadeVenda == null) quantidadeVenda = 0;
				v.setQuantidadeVenda(quantidadeVenda+1);
			}
		}

		vendas.add(model);	
	}

	private static void criaDadosCliente(String linha, List<ClienteModel> clientes) {
		ClienteModel model = new ClienteModel();
		String[] textoSeparado = linha.split("ç");
		model.setCnpj(Long.valueOf(textoSeparado[1]));
		model.setName(textoSeparado[2]);
		model.setBusinessArea(textoSeparado[3]);
		clientes.add(model);	
	}

	private static void criaDadosVendedor(String linha, List<VendedorModel> vendedores) {
		VendedorModel model = new VendedorModel();
		String[] textoSeparado = linha.split("ç");
		model.setCpf(Long.valueOf(textoSeparado[1]));
		model.setName(textoSeparado[2]);
		model.setSalary(textoSeparado[3]);
		vendedores.add(model);
	}
}