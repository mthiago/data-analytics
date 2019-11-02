package br.com.analisedados.controller;

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

import br.com.analisedados.model.ClienteModel;
import br.com.analisedados.model.ItemVendaModel;
import br.com.analisedados.model.VendaModel;
import br.com.analisedados.model.VendedorModel;

public class FileController {

	public static ArrayList<String> listarArquivos() {
		File file = new File("C:/temp");
		File afile[] = file.listFiles();
		ArrayList<String> arquivos = new ArrayList<>();
		for (int i=0; i < afile.length; i++) {
			arquivos.add(afile[i].toString());
		}
		return arquivos;
	}

	public static List<String> verificarDadosArquivo(String arquivo) throws IOException {
			BufferedReader br = new BufferedReader(new FileReader(arquivo));			
			String linha;
			List<String> linhas = new ArrayList<>();
			while ((linha = br.readLine()) != null) {
				linhas.add(linha);
			}
			br.close();
			return linhas;
	}

	public static void monitorarDiretorio() throws IOException, InterruptedException {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Path path = Paths.get("C:/temp");
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				verificarDadosArquivo(event.context().toString());
			}
			key.reset();
		}
	}

	public static void montaRetorno(List<VendedorModel> vendedores, List<ClienteModel> clientes, List<VendaModel> vendas) {

		Integer idVendaMaisCara = 1;
		Double valorVendaMaisCara = 1.0;
		for (VendaModel v : vendas) {
			for (ItemVendaModel i : v.getItemVendaModel()) {
				if (Double.compare(Double.valueOf(i.getPrecoItem()), valorVendaMaisCara) == 1) {
					valorVendaMaisCara = Double.valueOf(i.getPrecoItem());
					idVendaMaisCara = v.getCodigoVenda();
				}
			}
		}

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


		System.out.println("Quantidade de clientes: " + clientes.size());
		System.out.println("Quantidade de vendedores: " + vendedores.size());
		System.out.println("Id venda mais cara: " + idVendaMaisCara.toString());
		System.out.println("Pior vendedor: " + nomePiorVendedor);


		
	}



}
