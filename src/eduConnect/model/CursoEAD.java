package eduConnect.model;

public class CursoEAD extends Curso{
	
	private String plataforma;
	
	public CursoEAD(String nome, String codigo, int cargaHoraria, String plataforma) {
		super(nome, codigo, cargaHoraria);
		this.plataforma = plataforma;
	}
	   public String getPlataforma() {
	        return plataforma;
	    }

	    @Override
	    public String detalharCurso() {
	        return super.detalharCurso() +
	               "\nModalidade: EAD" +
	               "\nPlataforma virtual: " + plataforma;
	    }
	}


