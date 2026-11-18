import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    static final int MAX = 100;
    static final int PRAZO_EMPRESTIMO = 14;
    static final double MULTA_POR_DIA = 1.00;

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        String[] titulos = {
                "Head First Java (Use a Cabeça! Java), 3ª edição",
                "Head First Design Patterns, 2nd edition",
                "Java Cookbook, 4th edition"
        };
        String[] autores = {
                "Kathy Sierra, Bert Bates e Trisha Gee",
                "Eric Freeman e Elisabeth Robson",
                "Ian F. Darwin"
        };
        String[] isbns = {
                "9781492091646",
                "9781492077992",
                "9781492072577"
        };

        int[] livroDoExemplar = {0, 0, 1, 1, 2};
        String[] patrimonio = {"EX001", "EX002", "EX003", "EX004", "EX005"};
        boolean[] exemplarEmBomEstado = {true, true, true, true, true};
        boolean[] exemplarEmprestado = {false, false, false, false, false};

        String[] matriculas = {"2026001", "2026002", "2026003"};
        String[] nomes = {"Geovana", "Gabriela", "Gabriel"};
        String[] tipos = {"Aluno", "Professor", "Funcionário"};

        String[] membroDoEmprestimo = new String[MAX];
        int[] exemplarDoEmprestimo = new int[MAX];
        LocalDate[] dataRetirada = new LocalDate[MAX];
        LocalDate[] prazoDevolucao = new LocalDate[MAX];
        LocalDate[] dataDevolucao = new LocalDate[MAX];
        int quantidadeEmprestimos = 0;

        String[] membroDaReserva = new String[MAX];
        int[] livroDaReserva = new int[MAX];
        LocalDate[] dataReserva = new LocalDate[MAX];
        int quantidadeReservas = 0;

        int perfil;

        do {
            System.out.println("\n==============================================");
            System.out.println(" SISTEMA DE GESTÃO DE BIBLIOTECA UNIVERSITÁRIA");
            System.out.println("==============================================");
            System.out.println("Selecione o perfil de acesso:");
            System.out.println("1 - Membro (Aluno / Professor / Funcionário)");
            System.out.println("2 - Bibliotecário / Administrador");
            System.out.println("0 - Encerrar sistema");
            System.out.print("Opção: ");

            while (!entrada.hasNextInt()) {
                System.out.print("Digite um número válido: ");
                entrada.nextLine();
            }
            perfil = entrada.nextInt();
            entrada.nextLine();

            if (perfil == 1) {
                int opcaoMembro;
                do {
                    System.out.println("\n--- MENU DO MEMBRO ---");
                    System.out.println("1 - Consultar acervo");
                    System.out.println("2 - Verificar disponibilidade de um livro");
                    System.out.println("3 - Solicitar empréstimo");
                    System.out.println("4 - Solicitar devolução");
                    System.out.println("5 - Reservar livro");
                    System.out.println("0 - Voltar ao menu de perfis");
                    System.out.print("Escolha uma opção: ");

                    while (!entrada.hasNextInt()) {
                        System.out.print("Digite um número válido: ");
                        entrada.nextLine();
                    }
                    opcaoMembro = entrada.nextInt();
                    entrada.nextLine();

                    switch (opcaoMembro) {
                        case 1 -> {
                            System.out.println("\n--- ACERVO ---");
                            for (int i = 0; i < titulos.length; i++) {
                                System.out.println((i + 1) + " - " + titulos[i]
                                        + " | Autor: " + autores[i]
                                        + " | ISBN: " + isbns[i]);
                            }
                        }

                        case 2 -> {
                            System.out.print("Informe o ISBN do livro: ");
                            String isbnBusca = entrada.nextLine();
                            int indiceLivro = -1;

                            for (int i = 0; i < isbns.length; i++) {
                                if (isbns[i].equals(isbnBusca)) {
                                    indiceLivro = i;
                                    break;
                                }
                            }

                            if (indiceLivro == -1) {
                                System.out.println("Livro não encontrado.");
                            } else {
                                int disponiveis = 0;
                                int total = 0;

                                for (int i = 0; i < patrimonio.length; i++) {
                                    if (livroDoExemplar[i] == indiceLivro) {
                                        total++;
                                        if (!exemplarEmprestado[i] && exemplarEmBomEstado[i]) {
                                            disponiveis++;
                                        }
                                    }
                                }

                                System.out.println("Livro: " + titulos[indiceLivro]);
                                System.out.println("Exemplares cadastrados: " + total);
                                System.out.println("Exemplares disponíveis: " + disponiveis);
                            }
                        }

                        case 3 -> {
                            System.out.print("Matrícula do membro: ");
                            String matricula = entrada.nextLine();
                            int indiceMembro = -1;

                            for (int i = 0; i < matriculas.length; i++) {
                                if (matriculas[i].equals(matricula)) {
                                    indiceMembro = i;
                                    break;
                                }
                            }

                            if (indiceMembro == -1) {
                                System.out.println("Empréstimo bloqueado: cadastro inválido.");
                                break;
                            }

                            int emprestimosAtivos = 0;
                            for (int i = 0; i < quantidadeEmprestimos; i++) {
                                if (membroDoEmprestimo[i].equals(matricula)
                                        && dataDevolucao[i] == null) {
                                    emprestimosAtivos++;
                                }
                            }

                            if (emprestimosAtivos >= 3) {
                                System.out.println("Empréstimo bloqueado: limite de 3 ativos atingido.");
                                break;
                            }

                            System.out.println("\nExemplares:");
                            for (int i = 0; i < patrimonio.length; i++) {
                                System.out.println(patrimonio[i] + " - "
                                        + titulos[livroDoExemplar[i]]
                                        + " | Emprestado: " + (exemplarEmprestado[i] ? "Sim" : "Não")
                                        + " | Bom estado: " + (exemplarEmBomEstado[i] ? "Sim" : "Não"));
                            }

                            System.out.print("Código de patrimônio do exemplar: ");
                            String codigo = entrada.nextLine();
                            int indiceExemplar = -1;

                            for (int i = 0; i < patrimonio.length; i++) {
                                if (patrimonio[i].equalsIgnoreCase(codigo)) {
                                    indiceExemplar = i;
                                    break;
                                }
                            }

                            if (indiceExemplar == -1) {
                                System.out.println("Exemplar não encontrado.");
                            } else if (exemplarEmprestado[indiceExemplar]) {
                                System.out.println("Empréstimo bloqueado: exemplar já emprestado.");
                            } else if (!exemplarEmBomEstado[indiceExemplar]) {
                                System.out.println("Empréstimo bloqueado: exemplar danificado.");
                            } else if (quantidadeEmprestimos >= MAX) {
                                System.out.println("Limite de registros atingido.");
                            } else {
                                LocalDate hoje = LocalDate.now();
                                membroDoEmprestimo[quantidadeEmprestimos] = matricula;
                                exemplarDoEmprestimo[quantidadeEmprestimos] = indiceExemplar;
                                dataRetirada[quantidadeEmprestimos] = hoje;
                                prazoDevolucao[quantidadeEmprestimos] = hoje.plusDays(PRAZO_EMPRESTIMO);
                                dataDevolucao[quantidadeEmprestimos] = null;
                                quantidadeEmprestimos++;
                                exemplarEmprestado[indiceExemplar] = true;

                                System.out.println("Empréstimo registrado para " + nomes[indiceMembro]
                                        + " (" + tipos[indiceMembro] + ").");
                                System.out.println("Data de retirada: " + hoje);
                                System.out.println("Prazo de devolução: " + hoje.plusDays(PRAZO_EMPRESTIMO));
                            }
                        }

                        case 4 -> {
                            System.out.print("Código de patrimônio do exemplar: ");
                            String codigo = entrada.nextLine();
                            int indiceExemplar = -1;
                            int indiceEmprestimo = -1;

                            for (int i = 0; i < patrimonio.length; i++) {
                                if (patrimonio[i].equalsIgnoreCase(codigo)) {
                                    indiceExemplar = i;
                                    break;
                                }
                            }

                            if (indiceExemplar == -1) {
                                System.out.println("Exemplar não encontrado.");
                                break;
                            }

                            for (int i = 0; i < quantidadeEmprestimos; i++) {
                                if (exemplarDoEmprestimo[i] == indiceExemplar
                                        && dataDevolucao[i] == null) {
                                    indiceEmprestimo = i;
                                    break;
                                }
                            }

                            if (indiceEmprestimo == -1) {
                                System.out.println("Não existe empréstimo ativo para esse exemplar.");
                            } else {
                                System.out.print("Quantos dias corridos se passaram desde a retirada? ");
                                while (!entrada.hasNextInt()) {
                                    System.out.print("Digite um número inteiro de dias: ");
                                    entrada.nextLine();
                                }
                                int diasDecorridos = entrada.nextInt();
                                entrada.nextLine();

                                LocalDate dataEfetiva = dataRetirada[indiceEmprestimo].plusDays(diasDecorridos);
                                dataDevolucao[indiceEmprestimo] = dataEfetiva;
                                exemplarEmprestado[indiceExemplar] = false;

                                int diasAtraso = diasDecorridos - PRAZO_EMPRESTIMO;

                                System.out.println("Devolução registrada em " + dataEfetiva + ".");
                                if (diasAtraso > 0) {
                                    double multa = diasAtraso * MULTA_POR_DIA;
                                    System.out.println("Dias de atraso: " + diasAtraso);
                                    System.out.printf("Multa calculada: R$ %.2f%n", multa);
                                } else {
                                    System.out.println("Devolução dentro do prazo. Não há multa.");
                                }

                                System.out.print("O exemplar foi devolvido em bom estado? (s/n): ");
                                String resposta = entrada.nextLine();
                                if (resposta.equalsIgnoreCase("n")) {
                                    exemplarEmBomEstado[indiceExemplar] = false;
                                    System.out.println("Exemplar bloqueado até regularização pelo bibliotecário.");
                                }
                            }
                        }

                        case 5 -> {
                            System.out.print("Matrícula do membro: ");
                            String matricula = entrada.nextLine();
                            boolean membroValido = false;

                            for (String m : matriculas) {
                                if (m.equals(matricula)) {
                                    membroValido = true;
                                    break;
                                }
                            }

                            if (!membroValido) {
                                System.out.println("Reserva bloqueada: cadastro inválido.");
                                break;
                            }

                            System.out.print("ISBN do livro que deseja reservar: ");
                            String isbnBusca = entrada.nextLine();
                            int indiceLivro = -1;

                            for (int i = 0; i < isbns.length; i++) {
                                if (isbns[i].equals(isbnBusca)) {
                                    indiceLivro = i;
                                    break;
                                }
                            }

                            if (indiceLivro == -1) {
                                System.out.println("Livro não encontrado.");
                                break;
                            }

                            int totalExemplares = 0;
                            int disponiveis = 0;

                            for (int i = 0; i < patrimonio.length; i++) {
                                if (livroDoExemplar[i] == indiceLivro) {
                                    totalExemplares++;
                                    if (!exemplarEmprestado[i] && exemplarEmBomEstado[i]) {
                                        disponiveis++;
                                    }
                                }
                            }

                            if (totalExemplares == 0) {
                                System.out.println("Livro sem exemplares cadastrados.");
                            } else if (disponiveis > 0) {
                                System.out.println("Reserva não permitida: existe exemplar disponível para empréstimo.");
                            } else if (quantidadeReservas >= MAX) {
                                System.out.println("Limite de reservas atingido.");
                            } else {
                                membroDaReserva[quantidadeReservas] = matricula;
                                livroDaReserva[quantidadeReservas] = indiceLivro;
                                dataReserva[quantidadeReservas] = LocalDate.now();
                                quantidadeReservas++;
                                System.out.println("Reserva registrada para o livro " + titulos[indiceLivro] + ".");
                            }
                        }

                        case 0 -> System.out.println("Voltando ao menu principal...");

                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                } while (opcaoMembro != 0);

            } else if (perfil == 2) {
                int opcaoAdmin;
                do {
                    System.out.println("\n--- MENU DO BIBLIOTECÁRIO / ADMINISTRADOR ---");
                    System.out.println("1 - Consultar empréstimos ativos");
                    System.out.println("2 - Consultar reservas registradas");
                    System.out.println("3 - Regularizar estado de exemplar danificado");
                    System.out.println("4 - Listar situação geral dos exemplares");
                    System.out.println("0 - Voltar ao menu de perfis");
                    System.out.print("Escolha uma opção: ");

                    while (!entrada.hasNextInt()) {
                        System.out.print("Digite um número válido: ");
                        entrada.nextLine();
                    }
                    opcaoAdmin = entrada.nextInt();
                    entrada.nextLine();

                    switch (opcaoAdmin) {
                        case 1 -> {
                            System.out.println("\n--- EMPRÉSTIMOS ATIVOS ---");
                            boolean encontrou = false;

                            for (int i = 0; i < quantidadeEmprestimos; i++) {
                                if (dataDevolucao[i] == null) {
                                    int exemplar = exemplarDoEmprestimo[i];
                                    System.out.println("Membro: " + membroDoEmprestimo[i]
                                            + " | Exemplar: " + patrimonio[exemplar]
                                            + " | Retirada: " + dataRetirada[i]
                                            + " | Prazo: " + prazoDevolucao[i]);
                                    encontrou = true;
                                }
                            }

                            if (!encontrou) {
                                System.out.println("Não há empréstimos ativos.");
                            }
                        }

                        case 2 -> {
                            System.out.println("\n--- RESERVAS ---");
                            if (quantidadeReservas == 0) {
                                System.out.println("Não há reservas registradas.");
                            } else {
                                for (int i = 0; i < quantidadeReservas; i++) {
                                    System.out.println("Livro: " + titulos[livroDaReserva[i]]
                                            + " | Matrícula: " + membroDaReserva[i]
                                            + " | Data: " + dataReserva[i]);
                                }
                            }
                        }

                        case 3 -> {
                            System.out.print("Código de patrimônio do exemplar a regularizar: ");
                            String codigo = entrada.nextLine();
                            int indiceExemplar = -1;

                            for (int i = 0; i < patrimonio.length; i++) {
                                if (patrimonio[i].equalsIgnoreCase(codigo)) {
                                    indiceExemplar = i;
                                    break;
                                }
                            }

                            if (indiceExemplar == -1) {
                                System.out.println("Exemplar não encontrado.");
                            } else if (exemplarEmBomEstado[indiceExemplar]) {
                                System.out.println("Este exemplar já está em bom estado.");
                            } else {
                                exemplarEmBomEstado[indiceExemplar] = true;
                                System.out.println("Exemplar " + patrimonio[indiceExemplar]
                                        + " regularizado e liberado para empréstimos.");
                            }
                        }

                        case 4 -> {
                            System.out.println("\n--- SITUAÇÃO DOS EXEMPLARES ---");
                            for (int i = 0; i < patrimonio.length; i++) {
                                System.out.println(patrimonio[i] + " - "
                                        + titulos[livroDoExemplar[i]]
                                        + " | Emprestado: " + (exemplarEmprestado[i] ? "Sim" : "Não")
                                        + " | Bom estado: " + (exemplarEmBomEstado[i] ? "Sim" : "Não"));
                            }
                        }

                        case 0 -> System.out.println("Voltando ao menu principal...");

                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                } while (opcaoAdmin != 0);

            } else if (perfil != 0) {
                System.out.println("Perfil inválido. Tente novamente.");
            }

        } while (perfil != 0);

        System.out.println("Programa encerrado.");
        entrada.close();
    }
}