package br.com.analisedados.service;

import java.util.List;

import br.com.analisedados.model.VendedorModel;

public class VendedorService {

	public static void criaDadosVendedor(String linha, List<VendedorModel> vendedores) {
		String[] textoSeparado = linha.split("ç");
		VendedorModel model = new VendedorModel(Long.valueOf(textoSeparado[1]), textoSeparado[2], textoSeparado[3], null);
		vendedores.add(model);
	}
	
}
