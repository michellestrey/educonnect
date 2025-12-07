package eduConnect.service;

import java.util.ArrayList;
import java.util.List;

import eduConnect.model.Curso;
import eduConnect.model.Administrador;

public class CursoService {

    private List<Curso> cursos = new ArrayList<>();

    public void cadastrarCurso(Curso c) {
        cursos.add(c);
    }

    public boolean removerCurso(Administrador admin, int index) {
        if (!admin.isRoot()) return false;
        if (index < 0 || index >= cursos.size()) return false;
        cursos.remove(index);
        return true;
    }

    public List<Curso> listarCursos() {
        return cursos;
    }
}