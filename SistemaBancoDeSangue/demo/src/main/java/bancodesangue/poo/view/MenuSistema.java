package bancodesangue.poo.view;

import java.util.Scanner;

import bancodesangue.poo.enums.Genero;
import bancodesangue.poo.enums.TipoSanguineo;

public class MenuSistema {

    private Scanner scanner;

    public MenuSistema() {
        this.scanner = new Scanner(System.in);
    }

    public void fechar() {
        scanner.close();
    }

    public int exibirMenuPrincipal() {
        System.out.println("==========================================");
        System.out.println("       SISTEMA DE BANCO DE SANGUE");
        System.out.println("==========================================");
        System.out.println("Selecione seu perfil:");
        System.out.println("1. HEMONÚCLEO");
        System.out.println("2. HOSPITAL");
        System.out.println("3. RELATÓRIOS E ESTATÍSTICAS");
        System.out.println("0. Sair");
        return lerInteiro(">>> Opção: ");
    }

    public int exibirMenuHemonucleo() {
        System.out.println("--- PAINEL DO HEMONÚCLEO ---");
        System.out.println("1. Cadastrar Novo Doador");
        System.out.println("2. Registrar Doação");
        System.out.println("3. Registrar Descarte de Bolsa");
        System.out.println("4. Visualizar Estoque Atual");
        System.out.println("5. Listar Todos os Doadores");
        System.out.println("0. Voltar");
        return lerInteiro("Escolha: ");
    }

    public int exibirMenuHospital() {
        System.out.println("--- PAINEL DO HOSPITAL ---");
        System.out.println("1. Cadastrar Hospital");
        System.out.println("2. Solicitar Bolsas (Saída)");
        System.out.println("0. Voltar");
        return lerInteiro("Escolha: ");
    }

    public int exibirMenuRelatorios() {
        System.out.println("--- CENTRAL DE RELATÓRIOS ---");
        System.out.println("1. Histórico Completo");
        System.out.println("2. Histórico por Data");
        System.out.println("3. Ranking: TOP DOADORES");
        System.out.println("4. Ranking: TOP HOSPITAIS");
        System.out.println("5. Relatório de Saídas por Data");
        System.out.println("0. Voltar");
        return lerInteiro("Escolha: ");
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
        System.out.println("1. A+  | 2. A- | 3. B+  | 4. B-");
        System.out.println("5. AB+ | 6. AB- | 7. O+  | 8. O-");

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