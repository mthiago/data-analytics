package br.com.analisedados.service;

import java.util.List;

import br.com.analisedados.model.ClienteModel;

public class ClienteService {

	public static void criaDadosCliente(String linha, List<ClienteModel> clientes) {
		String[] textoSeparado = linha.split("ç");
		ClienteModel model = new ClienteModel(Long.valueOf(textoSeparado[1]), textoSeparado[2], textoSeparado[3]);
		clientes.add(model);	
	}
	
}
