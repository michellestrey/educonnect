package eduConnect.model;

import java.util.ArrayList;
import java.util.List;

import eduConnect.security.Autenticacao;

public abstract class Usuario implements Autenticacao{
	
	protected String nome;
	protected String login;
	protected String senha;
	
	private List<Usuario> usuarios = new ArrayList<>();
	
	public Usuario(String nome, String login, String senha) {
		super();
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}
	  @Override
	    public boolean autenticar(String login, String senha) {
	        return this.login.equals(login) && this.senha.equals(senha);
	    }
	

}
