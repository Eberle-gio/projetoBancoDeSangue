package bancodesangue.poo.view;

import java.util.Map;
import java.util.Scanner;

import bancodesangue.poo.enums.Genero;
import bancodesangue.poo.enums.TipoSanguineo;

public class MenuSistema {

    private Scanner scanner;

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE_BOLD = "\u001B[1;37m";

    public MenuSistema() {
        this.scanner = new Scanner(System.in);
    }

    public void fechar() {
        scanner.close();
    }

    public int exibirMenuPrincipal() {
        System.out.println("\n" + WHITE_BOLD + "==========================================" + RESET);
        System.out.println("       SISTEMA DE BANCO DE SANGUE");
        System.out.println(WHITE_BOLD + "==========================================" + RESET);
        System.out.println("Selecione seu perfil:");
        System.out.println("1. HEMONÚCLEO");
        System.out.println("2. HOSPITAL");
        System.out.println("3. RELATÓRIOS E ESTATÍSTICAS");
        System.out.println("0. Sair");
        return lerInteiro(">>> Opção: ");
    }

    public int exibirMenuHemonucleo() {
        System.out.println("\n--- PAINEL DO HEMONÚCLEO ---");
        System.out.println("1. Cadastrar Novo Doador");
        System.out.println("2. Registrar Doação");
        System.out.println("3. Registrar Descarte de Bolsa");
        System.out.println("4. Visualizar Estoque de Bolsas");
        System.out.println("5. Listar Todos os Doadores");
        System.out.println("0. Voltar");
        return lerInteiro("Escolha: ");
    }

    public int exibirMenuHospital() {
        System.out.println("\n--- PAINEL DO HOSPITAL ---");
        System.out.println("1. Cadastrar Hospital");
        System.out.println("2. Solicitação de Bolsas ");
        System.out.println("0. Voltar");
        return lerInteiro("Escolha: ");
    }

    public int exibirMenuRelatorios() {
        System.out.println("\n--- CENTRAL DE RELATÓRIOS ---");
        System.out.println("1. Histórico Completo");
        System.out.println("2. Histórico por Data");
        System.out.println("3. Doadores que mais doaram");
        System.out.println("4. Hospitais que mais solicitaram");
        System.out.println("5. Relatório de Saídas por Data");
        System.out.println("0. Voltar");
        return lerInteiro("Escolha: ");
    }

    public void exibirGraficoEstoque(Map<TipoSanguineo, Long> dadosEstoque) {
        System.out.println("\n" + CYAN + "=== MONITORAMENTO DE ESTOQUE DE SANGUE ===" + RESET);
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf(WHITE_BOLD + "| %-5s | %-10s | %-12s | %-30s |%n" + RESET,
                "TIPO", "QTD", "STATUS", "GRÁFICO VISUAL");
        System.out.println("-------------------------------------------------------------------------");

        long totalGeral = 0;

        for (Map.Entry<TipoSanguineo, Long> entry : dadosEstoque.entrySet()) {
            TipoSanguineo tipo = entry.getKey();
            Long qtd = entry.getValue();
            totalGeral += qtd;

            String nomeAmigavel = formatarTipo(tipo);
            String status = obterStatusEstoque(qtd);
            String corStatus = obterCorStatus(qtd);
            String barraGrafica = gerarBarra(qtd);

            System.out.printf("| %-5s | %-10d | %s%-12s" + RESET + " | %s%-30s" + RESET + " |%n",
                    nomeAmigavel, qtd, corStatus, status, corStatus, barraGrafica);
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.printf(WHITE_BOLD + "| %-5s | %-10d | %-45s |%n" + RESET, "TOTAL", totalGeral, "Bolsas no Freezer");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println();
    }

    private String formatarTipo(TipoSanguineo tipo) {
        switch (tipo) {
            case A_POS:
                return "A+";
            case A_NEG:
                return "A-";
            case B_POS:
                return "B+";
            case B_NEG:
                return "B-";
            case AB_POS:
                return "AB+";
            case AB_NEG:
                return "AB-";
            case O_POS:
                return "O+";
            case O_NEG:
                return "O-";
            default:
                return tipo.name();
        }
    }

    private String obterStatusEstoque(long qtd) {
        if (qtd == 0)
            return "ZERADO";
        if (qtd < 5)
            return "CRÍTICO";
        if (qtd < 10)
            return "BAIXO";
        return "IDEAL";
    }

    private String obterCorStatus(long qtd) {
        if (qtd < 5)
            return RED;
        if (qtd < 10)
            return YELLOW;
        return GREEN;
    }

    private String gerarBarra(long qtd) {
        int maxBarras = 20;
        int preenchido = (int) Math.min(qtd, maxBarras);

        return "█".repeat(preenchido);

    }

    public String lerString(String mensagem) {
        System.out.print(mensagem + " ");
        String input = scanner.nextLine();
        System.out.println();
        return input;
    }

    public int lerInteiro(String mensagem) {
        System.out.print(mensagem + " ");
        String input = scanner.nextLine();
        System.out.println();
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return -1;
        }
    }

    public double lerDouble(String mensagem) {
        System.out.print(mensagem + " ");
        String input = scanner.nextLine();
        System.out.println();
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            return -1.0;
        }
    }

    public Long lerLong(String mensagem) {
        System.out.print(mensagem + " ");
        String input = scanner.nextLine();
        System.out.println();
        try {
            return Long.parseLong(input);
        } catch (Exception e) {
            return -1L;
        }
    }

    public Genero lerGenero() {
        System.out.println("Gênero (1. Masculino | 2. Feminino): ");
        int op = lerInteiro(">>>");
        if (op == 1)
            return Genero.MASCULINO;
        if (op == 2)
            return Genero.FEMININO;
        System.out.println("Inválido. Definido como MASCULINO.\n");
        return Genero.MASCULINO;
    }

    public TipoSanguineo lerTipoSanguineo() {
        System.out.println("Tipo Sanguíneo:");
        System.out.println("1. A+  | 2. A- ");
        System.out.println("3. B+  | 4. B- ");
        System.out.println("5. AB+ | 6. AB- ");
        System.out.println("7. O+  | 8. O- ");

        int op = lerInteiro(">>>");
        switch (op) {
            case 1:
                return TipoSanguineo.A_POS;
            case 2:
                return TipoSanguineo.A_NEG;
            case 3:
                return TipoSanguineo.B_POS;
            case 4:
                return TipoSanguineo.B_NEG;
            case 5:
                return TipoSanguineo.AB_POS;
            case 6:
                return TipoSanguineo.AB_NEG;
            case 7:
                return TipoSanguineo.O_POS;
            case 8:
                return TipoSanguineo.O_NEG;
            default:
                System.out.println("Inválido. Definido como O+.\n");
                return TipoSanguineo.O_POS;
        }
    }

    public void mostrarMensagem(String msg) {
        System.out.println(msg);
        System.out.println();
    }
}