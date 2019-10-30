package br.com.analisedados;

public class DadosVendedorModel {

	private Long cpf;
	private String name;
	private String salary;
	private Integer quantidadeVenda;
	
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public Integer getQuantidadeVenda() {
		return quantidadeVenda;
	}
	public void setQuantidadeVenda(Integer quantidadeVenda) {
		this.quantidadeVenda = quantidadeVenda;
	}
	
	
	
}
