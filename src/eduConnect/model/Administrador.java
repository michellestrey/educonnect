package eduConnect.model;

public class Administrador extends Usuario {

    private boolean root;

    // construtor para criar admin comum (root = false)
    public Administrador(String nome, String login, String senha) {
        this(nome, login, senha, false);
    }

    // construtor para criar admin com controle de root
    public Administrador(String nome, String login, String senha, boolean root) {
        super(nome, login, senha);
        this.root = root;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public String gerarRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Relatório de Administrador ---\n");
        sb.append("Nome: ").append(getNome()).append("\n");
        sb.append("Login: ").append(getLogin()).append("\n");
        sb.append("Tipo: ").append(root ? "ROOT" : "Comum").append("\n");
        return sb.toString();
    }
}

