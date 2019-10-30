package br.com.analisedados.model;

public class ClienteModel {

	public ClienteModel(Long cnpjCliente, String nomeCliente, String areaCliente) {
		this.cnpjCliente = cnpjCliente;
		this.nomeCliente = nomeCliente;
		this.areaCliente = areaCliente;
	}
	
	private Long cnpjCliente;
	private String nomeCliente;
	private String areaCliente;

	public Long getCnpj() {
		return cnpjCliente;
	}
	public void setCnpj(Long cnpj) {
		this.cnpjCliente = cnpj;
	}
	public String getNome() {
		return nomeCliente;
	}
	public void setNome(String nome) {
		this.nomeCliente = nome;
	}
	public String getArea() {
		return areaCliente;
	}
	public void setArea(String area) {
		this.areaCliente = area;
	}
	
}