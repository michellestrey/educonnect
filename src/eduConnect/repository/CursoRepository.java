package eduConnect.repository;
import java.util.ArrayList;
import java.util.List;
import eduConnect.model.Curso;

public class CursoRepository {

    private List<Curso> cursos = new ArrayList<>();

    public void salvar(Curso curso) {
        cursos.add(curso);
    }

    public List<Curso> listarTodos() {
        return cursos;
    }
}
