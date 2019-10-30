package br.com.analisedados.view.model;

public class ItemVendaModel {
	
	ItemVendaModel(String codigoItem, String quantidadeItem, String precoItem){
		this.codigoItem = codigoItem;
		this.quantidadeItem = quantidadeItem;
		this.precoItem = precoItem;
	}
	
	private String codigoItem;
	private String quantidadeItem;
	private String precoItem;

	public String getCodigoItem() {
		return codigoItem;
	}
	public void setCodigoItem(String codigoItem) {
		this.codigoItem = codigoItem;
	}
	public String getQuantidadeItem() {
		return quantidadeItem;
	}
	public void setQuantidadeItem(String quantidadeItem) {
		this.quantidadeItem = quantidadeItem;
	}
	public String getPrecoItem() {
		return precoItem;
	}
	public void setPrecoItem(String precoItem) {
		this.precoItem = precoItem;
	}
	
}