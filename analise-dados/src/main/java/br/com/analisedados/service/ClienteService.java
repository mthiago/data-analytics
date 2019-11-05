package br.com.analisedados.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import br.com.analisedados.model.ClienteModel;

public class ClienteService {

	public static void criaDadosCliente(String linha, List<ClienteModel> clientes) throws UnsupportedEncodingException {
		
		//String[] linhaSeparada = linha.split("รง");
		String[] linhaSeparada = linha.split("็");

		Long cnpjCliente = Long.valueOf(linhaSeparada[1]);
		String nomeCliente = linhaSeparada[2];
		String areaCliente = linhaSeparada[3];

		ClienteModel model = new ClienteModel(
											cnpjCliente != null ? cnpjCliente : 0,
											nomeCliente != null ? nomeCliente : "",
											areaCliente != null ? areaCliente : "");
		clientes.add(model);	
	}

}
