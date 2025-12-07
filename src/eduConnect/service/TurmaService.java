package eduConnect.service;

import java.util.ArrayList;
import java.util.List;

import eduConnect.model.*;

public class TurmaService {

    private List<Turma> turmas = new ArrayList<>();

    public void criarTurma(Turma t) {
        turmas.add(t);
    }

    public boolean removerTurma(Administrador admin, int index) {
        if (!admin.isRoot()) return false;
        if (index < 0 || index >= turmas.size()) return false;
        turmas.remove(index);
        return true;
    }

    public void associarAluno(Turma t, Aluno a) {
        t.adicionarAluno(a);
    }

    public List<Turma> listarTurmas() {
        return turmas;
    }
}
