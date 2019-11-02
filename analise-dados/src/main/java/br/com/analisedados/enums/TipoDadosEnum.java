package br.com.analisedados.enums;

public enum TipoDadosEnum {

	VENDEDOR("001"),
	CLIENTE("002"),
	VENDA("003");

	private String codigoDados;

	TipoDadosEnum(String codigoDados){
		this.codigoDados = codigoDados;
	}

    public String codigoDados() {
    	return codigoDados;
    }

   public static TipoDadosEnum fromValue(String codigoDados){
	   if(codigoDados != null){
		   for(TipoDadosEnum codigo : TipoDadosEnum.values()){
			   if(codigo.codigoDados.equals(codigoDados)) {
				   return codigo;
			   }
		   }
	   }
	   return null;
   }	
	
}