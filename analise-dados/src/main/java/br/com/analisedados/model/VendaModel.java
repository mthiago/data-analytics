package br.com.analisedados.model;

import java.util.List;

public class VendaModel {

	private Integer codigoVenda;
	private List<ItemVendaModel> itemVendaModel;
	private String nomeVendedor;

	public VendaModel(Integer codigoVenda, List<ItemVendaModel> itemVendaModel, String nomeVendedor) {
		this.codigoVenda = codigoVenda;
		this.itemVendaModel = itemVendaModel;
		this.nomeVendedor = nomeVendedor;
	}
	
	public Integer getCodigoVenda() {
		return codigoVenda;
	}
	public void setCodigoVenda(Integer codigoVenda) {
		this.codigoVenda = codigoVenda;
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