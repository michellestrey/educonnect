package eduConnect.model;

public class Curso {
	
	private String nome;
	private String codigo;
	private int cargaHoraria;
	
	public Curso(String nome, String codigo, int cargaHoraria) {
		this.nome = nome;
		this.codigo = codigo;
		this.cargaHoraria = cargaHoraria;
	}

	public Curso() {
	
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	public String detalharCurso() {
		return "Curso: " + nome +
				"\nCódigo: " + codigo +
				"\nCargaHorária: " + cargaHoraria + "h";
	}
	public String gerarRelatorio() {
        return "=== RELATÓRIO DE CURSO ===\n" +
               "Nome: " + nome + "\n" +
               "Código: " + codigo + "\n" +
               "Carga horária: " + cargaHoraria + "h\n";
    }
	

}
