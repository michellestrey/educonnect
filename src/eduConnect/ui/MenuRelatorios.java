package eduConnect.ui;

import java.util.Scanner;

import eduConnect.model.Aluno;
import eduConnect.model.Curso;
import eduConnect.model.Professor;

public class MenuRelatorios {
	
	public static void exibirMenu(Aluno aluno, Professor professor, Curso curso) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU DE RELATÓRIOS ===");
            System.out.println("1 - Relatório de Aluno");
            System.out.println("2 - Relatório de Professor");
            System.out.println("3 - Relatório de Curso");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.println();
                    System.out.println(aluno.gerarRelatorio());
                    break;
                case 2:
                    System.out.println();
                    System.out.println(professor.gerarRelatorio());
                    break;
                case 3:
                    System.out.println();
                    System.out.println(curso.gerarRelatorio());
                    break;
                case 0:
                    System.out.println("Saindo do menu de relatórios...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);
    }

}
