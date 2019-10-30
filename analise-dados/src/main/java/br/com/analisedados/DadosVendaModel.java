package br.com.analisedados;

import java.util.List;

public class DadosVendaModel {

	private Integer saleId;
	private List<ItemVendaModel> itemVendaModel;
	private String nomeVendedor;
	public Integer getSaleId() {
		return saleId;
	}
	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}

	public List<ItemVendaModel> getItemVendaModel() {
		return itemVendaModel;
	}
	public void setItemVendaModel(List<ItemVendaModel> itemVendaModel) {
		this.itemVendaModel = itemVendaModel;
	}
	public String getNomeVendedor() {
		return nomeVendedor;
	}
	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}
	
	
	
}

