package eduConnect.model;

public class Avaliacao {
	
	private double nota;
	private String descricao;
	
	public Avaliacao(double adicionarNota, String descricao) {
		atribuirNota(adicionarNota); //valida nota
		this.descricao = descricao;
	}

	public Avaliacao(String descricao) {
		this.descricao = descricao;
		this.nota = 0.0;
		
	}
	
	//FASE 3
	public void atribuirNota(double valor) {
    if (valor < 0 || valor > 10) {
        throw new IllegalArgumentException("Nota deve estar entre 0 e 10. Tente novamente!");
    }
    this.nota = valor;
   }

	public String getDescricao() {
		return descricao;
	}

	public double getNota() {
		return nota;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}   
	
	@Override
	public String toString() {
		return "AVALIAÇÃO \n" +
	           "Descrição: " +  descricao +
	           "\nNota: " + nota;
		
	}

}
