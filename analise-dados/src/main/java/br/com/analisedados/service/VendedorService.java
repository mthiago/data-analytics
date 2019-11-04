package br.com.analisedados.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.analisedados.model.VendedorModel;

public class VendedorService {
	final static Logger logger = LogManager.getLogger(AnaliseDadosService.class);
	public static void criaDadosVendedor(String linha, List<VendedorModel> vendedores) {

		String[] textoSeparado = linha.split("ç");

		Long cpfVendedor = Long.valueOf(textoSeparado[1]);
		String nomeVendedor = textoSeparado[2];
		BigDecimal salarioVendedor = new BigDecimal(textoSeparado[3]);
		
		VendedorModel model = new VendedorModel(
												cpfVendedor != null ? cpfVendedor : 0,
												nomeVendedor != null ? nomeVendedor : "",
												salarioVendedor != null ? salarioVendedor : new BigDecimal(0),
												null);
		vendedores.add(model);

	}
	
}
