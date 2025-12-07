package eduConnect.model;

import eduConnect.security.Autenticacao;

public class Professor extends Usuario implements Autenticacao{
	
	private String especialidade;
	private String registro;
	
	public Professor(String nome, String login, String senha,String especialidade, String registro) {
		super(nome, login, senha);
		this.especialidade = especialidade;
		this.registro = registro;
	}
	
	@Override
	public boolean autenticar(String login, String senha) {
		return this.login.equals(login)&& this.senha.equals(senha);
 		
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}
	
	public String gerarRelatorio() {
        return "=== RELATÓRIO DE PROFESSOR ===\n" +
               "Nome: " + nome + "\n" +
               "Especialidade: " + especialidade + "\n" +
               "Registro: " + registro + "\n" +
               "* A remoção deste registro é permitida apenas a administradores do sistema. *\n";
    }
	
}
