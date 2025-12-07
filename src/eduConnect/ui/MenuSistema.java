package eduConnect.ui;

import java.util.List;
import java.util.Scanner;

import eduConnect.model.*;
import eduConnect.service.UsuarioService;
import eduConnect.service.CursoService;
import eduConnect.service.TurmaService;

public class MenuSistema {

    private final UsuarioService usuarioService = new UsuarioService();
    private final CursoService cursoService = new CursoService();
    private final TurmaService turmaService = new TurmaService();

    private final Scanner sc = new Scanner(System.in);
    private Usuario usuarioLogado;

    // ====================== AUXILIARES ======================

    private int lerInteiro(String msg) {
        while (true) {
            System.out.print(msg);
            String entrada = sc.nextLine();
            try {
                if (entrada.contains(".") || entrada.contains(",")) {
                    throw new NumberFormatException();
                }
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Formato inválido. Digite apenas números inteiros.");
            }
        }
    }

    private double lerNota(String msg) {
        while (true) {
            System.out.print(msg);
            String entrada = sc.nextLine();
            try {
                double n = Double.parseDouble(entrada);
                if (n < 0 || n > 10) {
                    System.out.println("A nota deve estar entre 0 e 10.");
                } else {
                    return n;
                }
            } catch (NumberFormatException e) {
                System.out.println("Nota inválida. Digite apenas números (0 a 10).");
            }
        }
    }

    private int lerOpcao() {
        while (true) {
            String entrada = sc.nextLine();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite apenas números.");
            }
        }
    }

    private boolean isAdmin() {
        return usuarioLogado instanceof Administrador;
    }

    private boolean isRoot() {
        return usuarioLogado instanceof Administrador
                && ((Administrador) usuarioLogado).isRoot();
    }

    private boolean isProfessor() {
        return usuarioLogado instanceof Professor;
    }

    private boolean isAluno() {
        return usuarioLogado instanceof Aluno;
    }

    // ====================== FLUXO PRINCIPAL ======================

    public void exibir() {
        autenticarPrimeiroAcesso(); // obriga login antes do menu

        int continuar = 1;
        while (continuar == 1) {
            exibirMenuPorPerfil();
            int op = lerOpcao();
            tratarOpcao(op);
            continuar = continuarOuEncerrar();
        }

        System.out.println("Sistema encerrado.");
    }

    // ====================== AUTENTICAÇÃO ======================

    private void autenticarPrimeiroAcesso() {
        while (true) {
            System.out.println("\n=== AUTENTICAÇÃO EDUCONNECT ===");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Cadastrar novo usuário (Aluno/Professor)");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            int op = lerOpcao();

            switch (op) {
                case 1 -> {
                    if (processarLogin()) return;
                }
                case 2 -> cadastrarNovoUsuarioLogin();
                case 0 -> {
                    System.out.println("Encerrando...");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private boolean processarLogin() {
        System.out.print("Login: ");
        String login = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario autenticado = usuarioService.autenticar(login, senha);

        if (autenticado == null) {
            System.out.println("Credenciais inválidas. Usuário não encontrado.");
            return false;
        }

        usuarioLogado = autenticado;

        if (isRoot()) {
            System.out.println("Login realizado como ADMINISTRADOR ROOT: " + usuarioLogado.getNome());
        } else if (isAdmin()) {
            System.out.println("Login realizado como ADMINISTRADOR: " + usuarioLogado.getNome());
        } else if (isProfessor()) {
            System.out.println("Login realizado como PROFESSOR: " + usuarioLogado.getNome());
        } else if (isAluno()) {
            System.out.println("Login realizado como ALUNO: " + usuarioLogado.getNome());
        }

        return true;
    }

    private void cadastrarNovoUsuarioLogin() {
        System.out.println("\n=== Cadastro de usuário para login ===");
        System.out.println("1 - Aluno");
        System.out.println("2 - Professor");
        System.out.print("Tipo: ");

        int tipo = lerOpcao();

        switch (tipo) {
            case 1 -> cadastrarAluno();
            case 2 -> cadastrarProfessor();
            default -> System.out.println("Tipo inválido.");
        }
    }

    private int continuarOuEncerrar() {
        System.out.println("\nDeseja voltar ao menu principal?");
        System.out.println("1 - Voltar ao menu");
        System.out.println("0 - Encerrar sistema");
        System.out.print("Opção: ");

        int op = lerOpcao();
        while (op != 0 && op != 1) {
            System.out.println("Opção inválida. Digite 1 ou 0.");
            op = lerOpcao();
        }
        return op;
    }

    // ====================== MENU POR PERFIL ======================

    private void exibirMenuPorPerfil() {
        System.out.println("\n=== MENU EDUCONNECT ===");

        // CADASTROS DE USUÁRIO:
        // Somente administradores podem cadastrar aluno/professor
        if (isAdmin()) {
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Cadastrar Professor");
        }

        // CADASTRO DE CURSO / TURMA / ASSOCIAÇÃO / AVALIAÇÃO:
        // Somente professores e administradores
        if (isAdmin() || isProfessor()) {
            System.out.println("3 - Cadastrar Curso");
            System.out.println("4 - Criar Turma");
            System.out.println("5 - Associar Aluno à Turma");
            System.out.println("6 - Registrar Avaliação");
        }

        // Todos podem ver relatórios
        System.out.println("7 - Relatórios gerais");

        // REMOÇÃO: apenas administradores
        if (isAdmin()) {
            System.out.println("8 - Remover Aluno");
            System.out.println("9 - Remover Professor");
            System.out.println("10 - Remover Curso");
            System.out.println("11 - Remover Turma");
        }

        // CADASTRAR ADMIN: apenas ROOT
        if (isRoot()) {
            System.out.println("12 - Cadastrar Administrador");
        }

        System.out.println("0 - Sair");
        System.out.print("Opção: ");
    }

    private void tratarOpcao(int op) {
        switch (op) {
            case 1 -> {
                if (isAdmin()) cadastrarAluno();
                else System.out.println("Acesso negado. Apenas administradores podem cadastrar alunos.");
            }
            case 2 -> {
                if (isAdmin()) cadastrarProfessor();
                else System.out.println("Acesso negado. Apenas administradores podem cadastrar professores.");
            }
            case 3 -> {
                if (isAdmin() || isProfessor()) cadastrarCurso();
                else System.out.println("Acesso negado. Apenas professores e administradores podem cadastrar cursos.");
            }
            case 4 -> {
                if (isAdmin() || isProfessor()) criarTurma();
                else System.out.println("Acesso negado. Apenas professores e administradores podem criar turmas.");
            }
            case 5 -> {
                if (isAdmin() || isProfessor()) associarAlunoTurma();
                else System.out.println("Acesso negado. Apenas professores e administradores podem associar alunos a turmas.");
            }
            case 6 -> {
                if (isAdmin() || isProfessor()) registrarAvaliacao();
                else System.out.println("Acesso negado. Apenas professores e administradores podem registrar avaliações.");
            }
            case 7 -> relatorios();
            case 8 -> {
                if (isAdmin()) removerAluno();
                else System.out.println("Acesso negado.");
            }
            case 9 -> {
                if (isAdmin()) removerProfessor();
                else System.out.println("Acesso negado.");
            }
            case 10 -> {
                if (isAdmin()) removerCurso();
                else System.out.println("Acesso negado.");
            }
            case 11 -> {
                if (isAdmin()) removerTurma();
                else System.out.println("Acesso negado.");
            }
            case 12 -> {
                if (isRoot()) cadastrarAdministrador();
                else System.out.println("Acesso negado. Somente ROOT cadastra administradores.");
            }
            case 0 -> {
                System.out.println("Encerrando...");
                System.exit(0);
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    // ====================== CADASTROS ======================

    private void cadastrarAluno() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("Curso: ");
        String curso = sc.nextLine();

        Aluno a = new Aluno(nome, login, senha, matricula, curso);
        usuarioService.cadastrarAluno(a);
        System.out.println("Aluno cadastrado.");
    }

    private void cadastrarProfessor() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Especialidade: ");
        String esp = sc.nextLine();
        System.out.print("Registro: ");
        String reg = sc.nextLine();

        Professor p = new Professor(nome, login, senha, esp, reg);
        usuarioService.cadastrarProfessor(p);
        System.out.println("Professor cadastrado.");
    }

    private void cadastrarAdministrador() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Administrador novo = new Administrador(nome, login, senha, false);
        boolean ok = usuarioService.cadastrarAdministrador((Administrador) usuarioLogado, novo);

        if (ok) {
            System.out.println("Administrador cadastrado (comum).");
        } else {
            System.out.println("Não foi possível cadastrar administrador. Apenas ROOT pode criar administradores.");
        }
    }

    private void cadastrarCurso() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Código: ");
        String codigo = sc.nextLine();
        int carga = lerInteiro("Carga horária: ");

        Curso c = new Curso(nome, codigo, carga);
        cursoService.cadastrarCurso(c);
        System.out.println("Curso cadastrado.");
    }

    // ====================== TURMAS / ASSOCIAÇÃO / AVALIAÇÃO ======================

    private void criarTurma() {
        List<Professor> professores = usuarioService.listarProfessores();
        List<Curso> cursos = cursoService.listarCursos();

        if (professores.isEmpty() || cursos.isEmpty()) {
            System.out.println("É necessário ter professor e curso cadastrados.");
            return;
        }

        System.out.print("Código da turma: ");
        String codigo = sc.nextLine();

        System.out.println("Selecione o professor:");
        for (int i = 0; i < professores.size(); i++) {
            System.out.println(i + " - " + professores.get(i).getNome());
        }
        int idxProf = lerOpcao();
        if (idxProf < 0 || idxProf >= professores.size()) {
            System.out.println("Professor inexistente.");
            return;
        }

        System.out.println("Selecione o curso:");
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println(i + " - " + cursos.get(i).getNome());
        }
        int idxCurso = lerOpcao();
        if (idxCurso < 0 || idxCurso >= cursos.size()) {
            System.out.println("Curso inexistente.");
            return;
        }

        Professor prof = professores.get(idxProf);
        Curso curso = cursos.get(idxCurso);

        Turma t = new Turma(codigo, prof, curso);
        turmaService.criarTurma(t);
        System.out.println("Turma criada.");
    }

    private void associarAlunoTurma() {
        List<Aluno> alunos = usuarioService.listarAlunos();
        List<Turma> turmas = turmaService.listarTurmas();

        if (alunos.isEmpty() || turmas.isEmpty()) {
            System.out.println("É necessário ter alunos e turmas.");
            return;
        }

        System.out.println("Selecione a turma:");
        for (int i = 0; i < turmas.size(); i++) {
            System.out.println(i + " - " + turmas.get(i).getResumo());
        }
        int idxTurma = lerOpcao();
        if (idxTurma < 0 || idxTurma >= turmas.size()) {
            System.out.println("Turma inexistente.");
            return;
        }
        Turma turma = turmas.get(idxTurma);

        System.out.println("Selecione o aluno:");
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i).getNome());
        }
        int idxAluno = lerOpcao();
        if (idxAluno < 0 || idxAluno >= alunos.size()) {
            System.out.println("Aluno inexistente.");
            return;
        }
        Aluno aluno = alunos.get(idxAluno);

        turmaService.associarAluno(turma, aluno);
        System.out.println("Aluno associado à turma.");
    }

    private void registrarAvaliacao() {
        List<Turma> turmas = turmaService.listarTurmas();

        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma disponível.");
            return;
        }

        System.out.println("Selecione a turma:");
        for (int i = 0; i < turmas.size(); i++) {
            System.out.println(i + " - " + turmas.get(i).getResumo());
        }
        int idxTurma = lerOpcao();
        if (idxTurma < 0 || idxTurma >= turmas.size()) {
            System.out.println("Turma inexistente.");
            return;
        }
        Turma turma = turmas.get(idxTurma);

        if (turma.getListaAlunos().isEmpty()) {
            System.out.println("Turma sem alunos.");
            return;
        }

        System.out.println("Selecione o aluno:");
        for (int i = 0; i < turma.getListaAlunos().size(); i++) {
            System.out.println(i + " - " + turma.getListaAlunos().get(i).getNome());
        }
        int idxAluno = lerOpcao();
        if (idxAluno < 0 || idxAluno >= turma.getListaAlunos().size()) {
            System.out.println("Aluno inexistente na turma.");
            return;
        }
        Aluno a = turma.getListaAlunos().get(idxAluno);

        System.out.print("Descrição da avaliação: ");
        String desc = sc.nextLine();
        double nota = lerNota("Nota: ");

        Avaliacao av = new Avaliacao(desc);
        av.atribuirNota(nota);
        a.adicionarAvaliacao(av);

        System.out.println("Avaliação registrada.");
    }

    // ====================== REMOÇÕES (APENAS ADMIN) ======================

    private void removerAluno() {
        List<Aluno> alunos = usuarioService.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("Selecione o aluno para remover:");
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println(i + " - " + alunos.get(i).getNome());
        }
        int idx = lerOpcao();

        boolean ok = usuarioService.removerAluno((Administrador) usuarioLogado, idx);
        if (ok) {
            System.out.println("Aluno removido.");
        } else {
            System.out.println("Não foi possível remover aluno (verifique permissões ou índice).");
        }
    }

    private void removerProfessor() {
        List<Professor> professores = usuarioService.listarProfessores();
        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
            return;
        }

        System.out.println("Selecione o professor para remover:");
        for (int i = 0; i < professores.size(); i++) {
            System.out.println(i + " - " + professores.get(i).getNome());
        }
        int idx = lerOpcao();

        boolean ok = usuarioService.removerProfessor((Administrador) usuarioLogado, idx);
        if (ok) {
            System.out.println("Professor removido.");
        } else {
            System.out.println("Não foi possível remover professor (verifique permissões ou índice).");
        }
    }

    private void removerCurso() {
        List<Curso> cursos = cursoService.listarCursos();
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }

        System.out.println("Selecione o curso para remover:");
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println(i + " - " + cursos.get(i).getNome());
        }
        int idx = lerOpcao();

        boolean ok = cursoService.removerCurso((Administrador) usuarioLogado, idx);
        if (ok) {
            System.out.println("Curso removido.");
        } else {
            System.out.println("Não foi possível remover curso (verifique permissões ou índice).");
        }
    }

    private void removerTurma() {
        List<Turma> turmas = turmaService.listarTurmas();
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
            return;
        }

        System.out.println("Selecione a turma para remover:");
        for (int i = 0; i < turmas.size(); i++) {
            System.out.println(i + " - " + turmas.get(i).getResumo());
        }
        int idx = lerOpcao();

        boolean ok = turmaService.removerTurma((Administrador) usuarioLogado, idx);
        if (ok) {
            System.out.println("Turma removida.");
        } else {
            System.out.println("Não foi possível remover turma (verifique permissões ou índice).");
        }
    }

    // ====================== RELATÓRIOS ======================

    private void relatorios() {
        System.out.println("\n=== RELATÓRIOS GERAIS ===");

        System.out.println("\nAlunos cadastrados:");
        for (Aluno a : usuarioService.listarAlunos()) {
            System.out.println(a.gerarRelatorio());
        }

        System.out.println("\nProfessores cadastrados:");
        for (Professor p : usuarioService.listarProfessores()) {
            System.out.println(p.gerarRelatorio());
        }

        System.out.println("\nCursos cadastrados:");
        for (Curso c : cursoService.listarCursos()) {
            System.out.println(c.gerarRelatorio());
        }

        System.out.println("\nTurmas cadastradas:");
        for (Turma t : turmaService.listarTurmas()) {
            System.out.println(t.getResumo());
        }

        // Somente admin enxerga contagem de administradores
        if (isAdmin()) {
            int qtd = usuarioService.listarAdministradores().size();
            System.out.println("\nAdministradores cadastrados: " + qtd);
            if (isRoot()) {
                for (Administrador adm : usuarioService.listarAdministradores()) {
                    System.out.println(adm.gerarRelatorio());
                }
            } else {
                System.out.println("(Detalhes disponíveis apenas para ROOT)");
            }
        }
    }
}
