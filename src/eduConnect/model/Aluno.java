package eduConnect.model;

import java.util.ArrayList;
import java.util.List;

import eduConnect.security.Autenticacao;

public class Aluno extends Usuario implements Autenticacao{
	 
	private String matricula;
	private String curso;
	
	private List<Avaliacao> avaliacoes = new ArrayList<>();
	
	public Aluno(String nome, String login,String senha, String matricula, String curso) {
		super(nome, login, senha);
		this.matricula = matricula;
		this.curso = curso;
	}
    
	@Override
	public boolean autenticar(String login, String senha) {
		return this.login.equals(login)&& this.senha.equals(senha);
 		
	}

    public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	public void adicionarAvaliacao(Avaliacao avaliacao) {
		avaliacoes.add(avaliacao);
	}

	public List<Avaliacao> getAvaliacoes(){
		return avaliacoes;
	}

	 public String gerarRelatorio() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("=== RELATÓRIO DE ALUNO ===\n");
	        sb.append("Nome: ").append(nome).append("\n");
	        sb.append("Matrícula: ").append(matricula).append("\n");
	        sb.append("Curso: ").append(curso).append("\n");
	        sb.append("* A remoção deste registro é permitida apenas a administradores do sistema. *\n");

	        if (avaliacoes.isEmpty()) {
	            sb.append("Avaliações: Nenhuma registrada.\n");
	        } else {
	            sb.append("Avaliações:\n");
	            for (Avaliacao av : avaliacoes) {
	                sb.append(" - ").append(av.getDescricao())
	                  .append(": ").append(av.getNota()).append("\n");
	            }
	        }

	        return sb.toString();
	    }

}
