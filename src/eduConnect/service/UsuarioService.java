package eduConnect.service;

import java.util.ArrayList;
import java.util.List;

import eduConnect.model.*;

public class UsuarioService {

    private List<Aluno> alunos = new ArrayList<>();
    private List<Professor> professores = new ArrayList<>();
    private List<Administrador> administradores = new ArrayList<>();

    public UsuarioService() {
        // cria root
        Administrador root = new Administrador("Administrador do Sistema", "admin", "123", true);
        administradores.add(root);
    }

    public Usuario autenticar(String login, String senha) {

        for (Administrador a : administradores)
            if (a.autenticar(login, senha))
                return a;

        for (Aluno a : alunos)
            if (a.autenticar(login, senha))
                return a;

        for (Professor p : professores)
            if (p.autenticar(login, senha))
                return p;

        return null;
    }

    public void cadastrarAluno(Aluno a) {
        alunos.add(a);
    }

    public void cadastrarProfessor(Professor p) {
        professores.add(p);
    }

    public boolean cadastrarAdministrador(Administrador admCriador, Administrador novoAdm) {
        if (admCriador == null || !admCriador.isRoot()) return false;
        administradores.add(novoAdm);
        return true;
    }

    public boolean removerAluno(Administrador admin, int index) {
        if (admin == null) return false;
        if (!admin.isRoot()) return false;
        if (index < 0 || index >= alunos.size()) return false;
        alunos.remove(index);
        return true;
    }

    public boolean removerProfessor(Administrador admin, int index) {
        if (admin == null) return false;
        if (!admin.isRoot()) return false;
        if (index < 0 || index >= professores.size()) return false;
        professores.remove(index);
        return true;
    }

    public boolean removerAdministrador(Administrador root, int index) {
        if (root == null || !root.isRoot()) return false;
        if (index <= 0 || index >= administradores.size()) return false; 
        // index 0 sempre é o ROOT
        administradores.remove(index);
        return true;
    }

    public List<Aluno> listarAlunos() { return alunos; }
    public List<Professor> listarProfessores() { return professores; }
    public List<Administrador> listarAdministradores() { return administradores; }
}
