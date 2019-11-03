package br.com.analisedados.model;

import java.math.BigDecimal;

public class ItemVendaModel {
	
	public ItemVendaModel(Integer codigoItem, Integer quantidadeItem, BigDecimal precoItem){
		this.codigoItem = codigoItem;
		this.quantidadeItem = quantidadeItem;
		this.precoItem = precoItem;
	}
	
	private Integer codigoItem;
	private Integer quantidadeItem;
	private BigDecimal precoItem;

	public Integer getCodigoItem() {
		return codigoItem;
	}
	public void setCodigoItem(Integer codigoItem) {
		this.codigoItem = codigoItem;
	}
	public Integer getQuantidadeItem() {
		return quantidadeItem;
	}
	public void setQuantidadeItem(Integer quantidadeItem) {
		this.quantidadeItem = quantidadeItem;
	}
	public BigDecimal getPrecoItem() {
		return precoItem;
	}
	public void setPrecoItem(BigDecimal precoItem) {
		this.precoItem = precoItem;
	}
	
}