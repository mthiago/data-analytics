package br.com.analisedados.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.analisedados.model.ItemVendaModel;
import br.com.analisedados.model.VendaModel;
import br.com.analisedados.model.VendedorModel;

public class VendaService {

	public static void criaDadosVenda(String linha, List<VendaModel> vendas, List<VendedorModel> vendedores) {

		String[] linhaSeparada = linha.split("ç");
		
		String item = linhaSeparada[2].replace("[", "").replace("]", "");
		List<ItemVendaModel> itens = montaListaItemVenda(item);

		montaQuantidadeVenda(vendedores, linhaSeparada);

		VendaModel model = new VendaModel(Integer.valueOf(linhaSeparada[1]), itens, linhaSeparada[3]);
		vendas.add(model);	
	}
	
	private static void montaQuantidadeVenda(List<VendedorModel> vendedores, String[] textoSeparado) {
		String nomeVendedor = textoSeparado[3];
		VendedorModel vendedor = vendedores.stream().filter(vend -> vend.getNomeVendedor().equals(nomeVendedor)).findFirst().get();
		
		Integer quantidadeVenda = vendedor.getQuantidadeVenda();
		if (quantidadeVenda == null) quantidadeVenda = 0;
		vendedor.setQuantidadeVenda(quantidadeVenda+1);
	}

	private static List<ItemVendaModel> montaListaItemVenda(String item) {
		String[] itemCompleto = item.split(",");
		String item1 = itemCompleto[0];
		String item2 = itemCompleto[1];
		String item3 = itemCompleto[2];
		
		ItemVendaModel itemVenda1 = separaItens(item1);
		ItemVendaModel itemVenda2 = separaItens(item2);
		ItemVendaModel itemVenda3 = separaItens(item3);

		List<ItemVendaModel> itens = new ArrayList<>();
		itens.add(itemVenda1);
		itens.add(itemVenda2);
		itens.add(itemVenda3);
		return itens;
	}
	
	private static ItemVendaModel separaItens(String item) {
		String[] dadosItem = item.split("-");
		
		Integer codigoItem = Integer.valueOf(dadosItem[0]);
		Integer quantidadeItem = Integer.valueOf(dadosItem[1]);
		BigDecimal precoItem = new BigDecimal(dadosItem[2]);
		
		ItemVendaModel itemVenda = new ItemVendaModel(
													codigoItem != null ? codigoItem : 0,
													quantidadeItem != null ? quantidadeItem : 0,
													precoItem != null ? precoItem : new BigDecimal(0));
		return itemVenda;
	}
	
}
