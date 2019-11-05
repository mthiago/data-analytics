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
import br.com.analisedados.service.AnaliseDadosService;

public class FileController {

	public static ArrayList<String> listarArquivos() {
		String diretorioAtual = new File("").getAbsolutePath();
		File file = new File(diretorioAtual + "/src/main/webapp/WEB-INF/arquivos");
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
		String diretorioAtual = new File("").getAbsolutePath();
		Path path = Paths.get(diretorioAtual + "/src/main/webapp/WEB-INF/arquivos");
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				String arquivo = diretorioAtual + "/src/main/webapp/WEB-INF/arquivos/" + event.context().toString();
				AnaliseDadosService.criarDados(arquivo);
			}
			key.reset();
		}
	}

}
