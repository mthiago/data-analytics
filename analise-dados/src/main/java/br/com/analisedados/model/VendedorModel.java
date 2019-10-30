package br.com.analisedados.model;

public class VendedorModel {

	public VendedorModel(Long cpfVendedor, String nomeVendedor, String salarioVendedor, Integer quantidadeVenda) {
		this.cpfVendedor = cpfVendedor;
		this.nomeVendedor = nomeVendedor;
		this.salarioVendedor = salarioVendedor;
		this.quantidadeVenda = quantidadeVenda;
	}

	private Long cpfVendedor;
	private String nomeVendedor;
	private String salarioVendedor;
	private Integer quantidadeVenda;

	public Long getCpfVendedor() {
		return cpfVendedor;
	}
	public void setCpfVendedor(Long cpfVendedor) {
		this.cpfVendedor = cpfVendedor;
	}
	public String getNomeVendedor() {
		return nomeVendedor;
	}
	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}
	public String getSalarioVendedor() {
		return salarioVendedor;
	}
	public void setSalarioVendedor(String salarioVendedor) {
		this.salarioVendedor = salarioVendedor;
	}
	public Integer getQuantidadeVenda() {
		return quantidadeVenda;
	}
	public void setQuantidadeVenda(Integer quantidadeVenda) {
		this.quantidadeVenda = quantidadeVenda;
	}
	
}