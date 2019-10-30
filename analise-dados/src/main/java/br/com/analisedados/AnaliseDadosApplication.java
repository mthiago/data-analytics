package br.com.analisedados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnaliseDadosApplication {

	public static void main(String[] args) {
		try {
		   BufferedReader br = new BufferedReader(new		 
				   FileReader("C:/temp/dados.txt"));
		   String linha;
		   List<DadosClienteModel> clientes = new ArrayList();
		   List<DadosVendedorModel> vendedores = new ArrayList();
		   List<DadosVendaModel> vendas = new ArrayList();
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
		   for (DadosVendaModel v : vendas) {
			   for (ItemVendaModel i : v.getItemVendaModel()) {
				   if (Double.compare(Double.valueOf(i.getItemPrice()), valorVendaMaisCara) == 1) {
					   valorVendaMaisCara = Double.valueOf(i.getItemPrice());
					   idVendaMaisCara = v.getSaleId();
				   }
			   }
		   }

		  String nomePiorVendedor = "";
		  Integer quantidadeVendasPiorVendedor = null;
		  for (DadosVendedorModel v : vendedores) {
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
					
	} catch (Exception e) {
		
	}
		
}

	private static void criaDadosVenda(String linha, List<DadosVendaModel> vendas, List<DadosVendedorModel> vendedores) {
		DadosVendaModel model = new DadosVendaModel();
		   String[] textoSeparado = linha.split("ç");
		   model.setSaleId(Integer.valueOf(textoSeparado[1]));
		   String item = textoSeparado[2];
		   String itemSemColcheteEsquerda = item.replace("[", "");
		   String itemSemColcheteDireita = item.replace("]", "");
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
		   List<ItemVendaModel> itens = new ArrayList();
		   itens.add(itemVenda1);
		   itens.add(itemVenda2);
		   itens.add(itemVenda3);
		   model.setItemVendaModel(itens);
		   model.setNomeVendedor(textoSeparado[3]);
		   
		   for (DadosVendedorModel v : vendedores) {
			   if (v.getName().equals(textoSeparado[3])) {
				   Integer quantidadeVenda = v.getQuantidadeVenda();
				   if (quantidadeVenda == null) quantidadeVenda = 0;
				   v.setQuantidadeVenda(quantidadeVenda+1);
			   }
		   }
		   
		   vendas.add(model);	
	}
	
	private static void criaDadosCliente(String linha, List<DadosClienteModel> clientes) {
		DadosClienteModel model = new DadosClienteModel();
		   String[] textoSeparado = linha.split("ç");
		   model.setCnpj(Long.valueOf(textoSeparado[1]));
		   model.setName(textoSeparado[2]);
		   model.setBusinessArea(textoSeparado[3]);
		   clientes.add(model);	
	}

	private static void criaDadosVendedor(String linha, List<DadosVendedorModel> vendedores) {
		   DadosVendedorModel model = new DadosVendedorModel();
		   String[] textoSeparado = linha.split("ç");
		   model.setCpf(Long.valueOf(textoSeparado[1]));
		   model.setName(textoSeparado[2]);
		   model.setSalary(textoSeparado[3]);
		   vendedores.add(model);
	}
}