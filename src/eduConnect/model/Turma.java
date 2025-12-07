package eduConnect.model;

import java.util.ArrayList;
import java.util.List;

public class Turma {
	
	private String codigo;
	private Professor professor;
	private Curso curso;
	private List<Aluno> listaAlunos;
	
	public Turma(String codigo, Professor professor,Curso curso) {
		this.codigo = codigo;
		this.professor = professor;
		this.curso = curso;
		this.listaAlunos = new ArrayList<>();
	}
	
	public Turma() {
	}

    public void adicionarAluno(Aluno aluno) {
        listaAlunos.add(aluno);
    }

    public void removerAluno(Aluno aluno) {
        listaAlunos.remove(aluno);

    }
    
    public void registrarAvaliacao(Aluno aluno, String descricao, double nota) {
    	if(!listaAlunos.contains(aluno)) { //verifica se o aluno está matriculado
    		System.out.println("O aluno não está matriculado nesta turma!");
    		return;
    	}
    	Avaliacao avaliacao = new Avaliacao(descricao);
    	avaliacao.atribuirNota(nota);
    	aluno.adicionarAvaliacao(avaliacao);
    }
    
    public String getResumo() {
        return "\n Turma " + codigo +
                "\n Professor: " + professor.getNome() +
                "\n Curso: " + curso.getNome() +
                "\n Carga horária:" + curso.getCargaHoraria() +
                "\n Quantidade de alunos matriculados: " + listaAlunos.size()+ "\n"+
                "\n* A remoção deste registro é permitida apenas a administradores do sistema. *";
    }

    public List<Aluno> getListaAlunos() {
        return listaAlunos;
    }

}
