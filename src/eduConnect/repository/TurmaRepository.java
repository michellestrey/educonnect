package eduConnect.repository;
import java.util.ArrayList;
import java.util.List;
import eduConnect.model.Turma;

public class TurmaRepository {

    private List<Turma> turmas = new ArrayList<>();

    public void salvar(Turma turma) {
        turmas.add(turma);
    }

    public List<Turma> listarTodos() {
        return turmas;
    }
}
