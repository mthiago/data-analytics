package br.com.analisedados.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import br.com.analisedados.model.ClienteModel;

public class ClienteService {

	public static void criaDadosCliente(String linha, List<ClienteModel> clientes) throws UnsupportedEncodingException {
		System.setProperty("file.encoding", "UTF-8");
		String[] textoSeparado = linha.split("ç");

		Long cnpjCliente = Long.valueOf(textoSeparado[1]);
		String nomeCliente = textoSeparado[2];
		String areaCliente = textoSeparado[3];

		ClienteModel model = new ClienteModel(
											cnpjCliente != null ? cnpjCliente : 0,
											nomeCliente != null ? nomeCliente : "",
											areaCliente != null ? areaCliente : "");
		clientes.add(model);	
	}

}
