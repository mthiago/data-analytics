package br.com.analisedados;

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

import br.com.analisedados.view.model.ClienteModel;
import br.com.analisedados.view.model.VendaModel;
import br.com.analisedados.view.model.VendedorModel;
import br.com.analisedados.view.model.ItemVendaModel;

@SpringBootApplication
public class AnaliseDadosApplication {

	public static void main(String[] args) {

		try {
			
			File file = new File("C:/temp");
			File afile[] = file.listFiles();
			
			for (int i=0; i < afile.length; i++) {
				verificaDados(afile[i].toString());
			}

			
			WatchService watchService
			= FileSystems.getDefault().newWatchService();

			Path path = Paths.get("C:/temp");

			path.register(
					watchService, 
					StandardWatchEventKinds.ENTRY_CREATE,  
					StandardWatchEventKinds.ENTRY_MODIFY);

			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					verificaDados(event.context().toString());
				}
				key.reset();
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	private static void verificaDados(String string) throws IOException {
		BufferedReader br = null;
		if (string != null) {
			br = new BufferedReader(new		 
					FileReader("C:/temp/" + string));	
		} else {
			br = new BufferedReader(new		 
					FileReader("C:/temp/dados.txt"));
		}
		String linha;
		List<ClienteModel> clientes = new ArrayList<>();
		List<VendedorModel> vendedores = new ArrayList<>();
		List<VendaModel> vendas = new ArrayList<>();
		while ((linha = br.readLine()) != null) {
			if (linha.startsWith("001")) {
				criaDadosVendedor(linha, vendedores);
			}
			else if (linha.startsWith("002")) {
				criaDadosCliente(linha, clientes);
			}
			else if (linha.startsWith("003")) {
				criaDadosVenda(linha, vendas, vendedores);
			}
		}

		Integer idVendaMaisCara = 1;
		Double valorVendaMaisCara = 1.0;
		for (VendaModel v : vendas) {
			for (ItemVendaModel i : v.getItemVendaModel()) {
				if (Double.compare(Double.valueOf(i.getItemPrice()), valorVendaMaisCara) == 1) {
					valorVendaMaisCara = Double.valueOf(i.getItemPrice());
					idVendaMaisCara = v.getSaleId();
				}
			}
		}

		String nomePiorVendedor = "";
		Integer quantidadeVendasPiorVendedor = null;
		for (VendedorModel v : vendedores) {
			if (quantidadeVendasPiorVendedor == null) {
				quantidadeVendasPiorVendedor = v.getQuantidadeVenda();
				nomePiorVendedor = v.getName();
			}
			else if (quantidadeVendasPiorVendedor > v.getQuantidadeVenda()) {
				nomePiorVendedor = v.getName();
			}
		}


		System.out.println("Quantidade de clientes: " + clientes.size());
		System.out.println("Quantidade de vendedores: " + vendedores.size());
		System.out.println("Id venda mais cara: " + idVendaMaisCara.toString());
		System.out.println("Pior vendedor: " + nomePiorVendedor);


		br.close();

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