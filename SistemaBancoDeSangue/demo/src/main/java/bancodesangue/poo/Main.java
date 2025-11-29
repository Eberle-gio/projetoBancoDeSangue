package bancodesangue.poo;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import bancodesangue.poo.dao.DaoDescarte;
import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.dao.DaoDoacaoSaida;
import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.dao.DaoHospital;
import bancodesangue.poo.dao.DaoMovimentacao;
import bancodesangue.poo.entity.DoacaoDescarte;
import bancodesangue.poo.entity.DoacaoEntrada;
import bancodesangue.poo.entity.DoacaoSaida;
import bancodesangue.poo.entity.Doador;
import bancodesangue.poo.entity.Hospital;
import bancodesangue.poo.entity.Movimentacao;
import bancodesangue.poo.enums.Genero;
import bancodesangue.poo.enums.TipoSanguineo;
import bancodesangue.poo.service.DescarteService;
import bancodesangue.poo.service.DoacaoEntradaService;
import bancodesangue.poo.service.DoacaoSaidaService;
import bancodesangue.poo.service.DoadorService;
import bancodesangue.poo.service.HospitalService;
import bancodesangue.poo.service.MovimentacaoService;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static DoadorService doadorService;
    private static HospitalService hospitalService;
    private static DoacaoEntradaService entradaService;
    private static DoacaoSaidaService saidaService;
    private static DescarteService descarteService;
    private static MovimentacaoService movimentacaoService;
    private static DaoDoador daoDoador;
    private static DaoHospital daoHospital;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenciaPU");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando sistema e conectando ao banco de dados...");

            daoDoador = new DaoDoador(em);
            daoHospital = new DaoHospital(em);
            DaoDoacaoEntrada daoEntrada = new DaoDoacaoEntrada(em);
            DaoDoacaoSaida daoSaida = new DaoDoacaoSaida(em);
            DaoDescarte daoDescarte = new DaoDescarte(em);
            DaoMovimentacao daoMovimentacao = new DaoMovimentacao(em);

            doadorService = new DoadorService(daoDoador);
            hospitalService = new HospitalService(daoHospital);
            entradaService = new DoacaoEntradaService(daoEntrada, daoDoador);
            saidaService = new DoacaoSaidaService(daoSaida, daoEntrada, daoHospital);
            descarteService = new DescarteService(daoDescarte);
            movimentacaoService = new MovimentacaoService(daoMovimentacao);

            // MENU PRINCIPAL
            boolean rodando = true;
            while (rodando) {
                System.out.println("\n==========================================");
                System.out.println("       SISTEMA DE BANCO DE SANGUE");
                System.out.println("==========================================");
                System.out.println("Selecione seu perfil:");
                System.out.println("1. HEMONÚCLEO (Gerenciar Doações e Estoque)");
                System.out.println("2. HOSPITAL (Solicitar Sangue)");
                System.out.println("3. RELATÓRIOS E ESTATÍSTICAS");
                System.out.println("0. Sair");
                System.out.print(">>> Opção: ");

                int opcao = lerInteiro();

                switch (opcao) {
                    case 1:
                        menuHemonucleo();
                        break;
                    case 2:
                        menuHospital();
                        break;
                    case 3:
                        menuRelatorios();
                        break;
                    case 0:
                        rodando = false;
                        System.out.println("Encerrando sistema... Até logo!");
                        break;
                    default:
                        System.out.println("ERRO: Opção inválida!");
                }
            }

        } catch (Exception e) {
            System.out.println("Falha no Sistema: " + e.getMessage());
            e.printStackTrace();
        } finally {

            if (em != null)
                em.close();
            if (emf != null)
                emf.close();
            scanner.close();
        }
    }

    private static void menuHemonucleo() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- PAINEL DO HEMONÚCLEO ---");
            System.out.println("1. Cadastrar Novo Doador");
            System.out.println("2. Registrar Doação");
            System.out.println("3. Registrar Descarte de Bolsa");
            System.out.println("4. Listar Todos os Doadores");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            int opcao = lerInteiro();

            try {
                switch (opcao) {
                    case 1:
                        cadastrarDoador();
                        break;
                    case 2:
                        registrarEntrada();
                        break;
                    case 3:
                        registrarDescarte();
                        break;
                    case 4:
                        listarTodosDoadores();
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("ERRO NA OPERAÇÃO: " + e.getMessage());
            }
        }
    }

    private static void menuHospital() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- PAINEL DO HOSPITAL ---");
            System.out.println("1. Cadastrar Hospital");
            System.out.println("2. Solicitar Bolsas ");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            int opcao = lerInteiro();

            try {
                switch (opcao) {
                    case 1:
                        cadastrarHospital();
                        break;
                    case 2:
                        registrarSaida();
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("ERRO NA OPERAÇÃO: " + e.getMessage());
            }
        }
    }

    private static void menuRelatorios() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- CENTRAL DE RELATÓRIOS ---");
            System.out.println("1. Histórico Completo");
            System.out.println("2. Histórico por Data ");
            System.out.println("3. Maiores Doadores ");
            System.out.println("4. Hospitais que mais Solicitaram ");
            System.out.println("5. Relatório de Saídas por Data");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            int opcao = lerInteiro();

            try {
                switch (opcao) {
                    case 1:
                        listarHistoricoGeral();
                        break;
                    case 2:
                        listarHistoricoPorData();
                        break;
                    case 3:
                        listarRankingDoadores();
                        break;
                    case 4:
                        listarRankingHospitais();
                        break;
                    case 5:
                        listarSaidasPorData();
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("ERRO AO GERAR RELATÓRIO: " + e.getMessage());
            }
        }
    }

    private static void cadastrarDoador() {
        System.out.println("\n>>> NOVO CADASTRO DE DOADOR <<<");
        Doador d = new Doador();

        System.out.print("Nome Completo: ");
        d.setNome(scanner.nextLine());

        System.out.print("CPF (apenas números): ");
        d.setCpf(scanner.nextLine());

        System.out.print("Idade: ");
        d.setIdade(lerInteiro());

        System.out.print("Peso (kg): ");
        d.setPeso(lerDouble());

        System.out.print("Telefone: ");
        d.setTelefone(scanner.nextLine());

        d.setGenero(lerGenero());
        d.setTipoSanguineo(lerTipoSanguineo());

        Doador salvo = doadorService.cadastrarDoador(d);
        System.out.println("SUCESSO! Doador cadastrado com ID: " + salvo.getId());
    }

    private static void registrarEntrada() {
        System.out.println("\n>>> REGISTRAR DOAÇÃO (ENTRADA) <<<");

        System.out.print("Digite o nome do Doador: ");
        String nomeDoador = scanner.nextLine();

        Doador doador = daoDoador.buscarPorNome(nomeDoador);
        if (doador == null) {
            System.out.println("AVISO: Doador não encontrado com este ID.");
            return;
        }
        System.out.println("Doador Identificado: " + doador.getNome() + " [" + doador.getTipoSanguineo() + "]");

        DoacaoEntrada entrada = new DoacaoEntrada();
        entrada.setDoador(doador);

        entradaService.registrarDoacao(entrada);
        System.out.println("SUCESSO! Doação registrada e estoque atualizado.");
    }

    private static void registrarDescarte() {
        System.out.println("\n>>> REGISTRAR DESCARTE DE BOLSA <<<");
        DoacaoDescarte descarte = new DoacaoDescarte();

        System.out.println("Qual o tipo sanguíneo da bolsa descartada?");
        descarte.setTipoSanguineo(lerTipoSanguineo());

        System.out.print("Motivo do Descarte: ");
        descarte.setMotivoDescarte(scanner.nextLine());

        System.out.print("Responsável pelo Descarte (Nome): ");
        descarte.setResponsavelDescarte(scanner.nextLine());

        descarteService.registrarDescarte(descarte);
        System.out.println("SUCESSO! Descarte registrado no histórico.");
    }

    private static void listarTodosDoadores() {
        System.out.println("\n--- LISTA DE DOADORES CADASTRADOS ---");
        List<Doador> lista = doadorService.buscarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum doador cadastrado.");
        } else {
            for (Doador d : lista) {
                String ultDoacao = (d.getDataUltimaDoacao() == null) ? "Nunca doou"
                        : d.getDataUltimaDoacao().toString();
                System.out.println("ID: " + d.getId() + " | Nome: " + d.getNome() + " | CPF: " + d.getCpf() +
                        " | Tipo: " + d.getTipoSanguineo() + " | Última Doação: " + ultDoacao);
            }
        }
    }

    private static void cadastrarHospital() {
        System.out.println("\n>>> NOVO CADASTRO DE HOSPITAL <<<");
        Hospital h = new Hospital();

        System.out.print("Nome do Hospital: ");
        h.setNome(scanner.nextLine());

        System.out.print("CNPJ (apenas números): ");
        h.setCnpj(lerLong());
        System.out.print("Endereço: ");
        h.setEndereco(scanner.nextLine());

        System.out.print("Telefone: ");
        h.setTelefone(scanner.nextLine());

        hospitalService.cadastrarHospital(h);
        System.out.println("SUCESSO! Hospital cadastrado.");
    }

    private static void registrarSaida() {
        System.out.println("\n>>> SOLICITAR BOLSAS (SAÍDA) <<<");

        System.out.print("Digite o ID do Hospital solicitante: ");
        Long idHospital = lerLong();

        Hospital hospital = daoHospital.buscarPorId(idHospital);
        if (hospital == null) {
            System.out.println("AVISO: Hospital não encontrado. Cadastre-o primeiro.");
            return;
        }
        System.out.println("Hospital Identificado: " + hospital.getNome());

        DoacaoSaida saida = new DoacaoSaida();
        saida.setHospital(hospital);

        System.out.println("Qual tipo de sangue é necessário?");
        saida.setTipoSanguineo(lerTipoSanguineo());

        System.out.print("Quantidade de bolsas: ");
        saida.setQuantidadeBolsas(lerInteiro());

        saidaService.registrarSaida(saida);
        System.out.println("SUCESSO! Solicitação aprovada e bolsas liberadas do estoque.");
    }

    private static void listarHistoricoGeral() {
        System.out.println("\n--- HISTÓRICO GERAL (Todas as movimentações) ---");
        imprimirListaMovimentacao(movimentacaoService.buscarHistoricoCompleto());
    }

    private static void listarHistoricoPorData() {
        System.out.println("Como deseja ordenar?");
        System.out.println("1. Mais Antigo -> Mais Recente ");
        System.out.println("2. Mais Recente -> Mais Antigo ");
        int op = lerInteiro();
        boolean asc = (op == 1);

        System.out.println("\n--- Histórico por Data ---");
        imprimirListaMovimentacao(movimentacaoService.buscarPorData(asc));
    }

    private static void imprimirListaMovimentacao(List<Movimentacao> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("Nenhuma movimentação encontrada.");
            return;
        }

        for (Movimentacao m : lista) {
            String tipo = "";
            String detalhe = "";

            if (m instanceof DoacaoEntrada) {
                tipo = "[ENTRADA]";
                detalhe = "Doador: " + ((DoacaoEntrada) m).getDoador().getNome();
            } else if (m instanceof DoacaoSaida) {
                tipo = "[SAÍDA]  "; // Espaços para alinhar visualmente
                detalhe = "Hospital: " + ((DoacaoSaida) m).getHospital().getNome() +
                        " (Qtd: " + ((DoacaoSaida) m).getQuantidadeBolsas() + ")";
            } else if (m instanceof DoacaoDescarte) {
                tipo = "[DESCARTE]";
                detalhe = "Motivo: " + ((DoacaoDescarte) m).getMotivoDescarte();
            }

            System.out.println(
                    "Data: " + m.getData() + " | " + tipo + " | Sangue: " + m.getTipoSanguineo() + " | " + detalhe);
        }
    }

    private static void listarRankingDoadores() {
        System.out.println("\n--- MAIRES DOADORES --");
        List<Object[]> ranking = entradaService.gerarRankingDoadores();

        if (ranking.isEmpty()) {
            System.out.println("Nenhuma doação registrada ainda.");
            return;
        }

        int pos = 1;
        for (Object[] row : ranking) {
            Doador d = (Doador) row[0];
            Long total = (Long) row[1];
            System.out.println(pos + "º Lugar: " + d.getNome() + " (" + d.getTipoSanguineo() + ") - Total Doado: "
                    + total + " bolsa(s)");
            pos++;
        }
    }

    private static void listarRankingHospitais() {
        System.out.println("\n--- HOSPITAIS QUE MAIS SOLICITARAM ---");
        List<Object[]> ranking = saidaService.gerarRankingHospitais();

        if (ranking.isEmpty()) {
            System.out.println("Nenhuma saída registrada ainda.");
            return;
        }

        int pos = 1;
        for (Object[] row : ranking) {
            Hospital h = (Hospital) row[0];
            Long total = (Long) row[1];
            System.out.println(pos + "º Lugar: " + h.getNome() + " - Total Solicitado: " + total + " bolsa(s)");
            pos++;
        }
    }

    private static void listarSaidasPorData() {
        System.out.println("Como deseja ordenar?");
        System.out.println("1. Mais Antigo -> Mais Recente");
        System.out.println("2. Mais Recente -> Mais Antigo");
        int op = lerInteiro();
        boolean asc = (op == 1);

        System.out.println("\n--- RELATÓRIO DE SAÍDAS ---");
        List<DoacaoSaida> saidas = saidaService.buscarPorData(asc);

        if (saidas.isEmpty()) {
            System.out.println("Nenhuma saída registrada.");
            return;
        }

        for (DoacaoSaida s : saidas) {
            System.out.println(s.getData() + " | Hospital: " + s.getHospital().getNome() +
                    " | Sangue: " + s.getTipoSanguineo() + " | Qtd: " + s.getQuantidadeBolsas());
        }
    }

    private static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private static double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            return -1.0;
        }
    }

    private static Long lerLong() {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (Exception e) {
            return -1L;
        }
    }

    private static Genero lerGenero() {
        System.out.println("Selecione o Gênero:");
        System.out.println("1. Masculino");
        System.out.println("2. Feminino");
        int op = lerInteiro();
        if (op == 1)
            return Genero.MASCULINO;
        if (op == 2)
            return Genero.FEMININO;
        System.out.println("Opção inválida! Definindo padrão como MASCULINO.");
        return Genero.MASCULINO;
    }

    private static TipoSanguineo lerTipoSanguineo() {
        System.out.println("Selecione o Tipo Sanguíneo:");
        System.out.println("1. A+  | 2. A-");
        System.out.println("3. B+  | 4. B-");
        System.out.println("5. AB+ | 6. AB-");
        System.out.println("7. O+  | 8. O-");

        int op = lerInteiro();
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
                System.out.println("Opção inválida! Definindo padrão como O+ (por segurança).");
                return TipoSanguineo.O_POS;
        }
    }
}