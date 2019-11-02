package br.com.analisedados.service;

import java.util.ArrayList;
import java.util.List;

import br.com.analisedados.model.ItemVendaModel;
import br.com.analisedados.model.VendaModel;
import br.com.analisedados.model.VendedorModel;

public class VendaService {

	public static void criaDadosVenda(String linha, List<VendaModel> vendas, List<VendedorModel> vendedores) {
		String[] textoSeparado = linha.split("ç");
		String item = textoSeparado[2].replace("[", "").replace("]", "");
		List<ItemVendaModel> itens = buildItens(item);

		buildQuantidadeVenda(vendedores, textoSeparado);

		VendaModel model = new VendaModel(Integer.valueOf(textoSeparado[1]), itens, textoSeparado[3]);
		vendas.add(model);	
	}
	
	private static void buildQuantidadeVenda(List<VendedorModel> vendedores, String[] textoSeparado) {
		String nomeVendedor = textoSeparado[3];
		VendedorModel vendedor = vendedores.stream().filter(vend -> vend.getNomeVendedor().equals(nomeVendedor)).findFirst().get();
		
		Integer quantidadeVenda = vendedor.getQuantidadeVenda();
		if (quantidadeVenda == null) quantidadeVenda = 0;
		vendedor.setQuantidadeVenda(quantidadeVenda+1);
	}

	private static List<ItemVendaModel> buildItens(String item) {
		String[] itemFinal = item.split(",");
		String item1 = itemFinal[0];
		String item2 = itemFinal[1];
		String item3 = itemFinal[2];
		
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
		ItemVendaModel itemVenda = new ItemVendaModel(dadosItem[0], dadosItem[1], dadosItem[2]);
		return itemVenda;
	}
	
}
