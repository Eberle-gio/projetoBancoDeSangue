package bancodesangue.poo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bancodesangue.poo.entity.Descarte;
import bancodesangue.poo.entity.DoacaoEntrada;
import bancodesangue.poo.entity.DoacaoSaida;
import bancodesangue.poo.entity.Doador;
import bancodesangue.poo.entity.EntidadeBase;
import bancodesangue.poo.entity.Hospital;
import bancodesangue.poo.entity.Movimentacao;
import bancodesangue.poo.enums.TipoSanguineo;
import bancodesangue.poo.service.DescarteService;
import bancodesangue.poo.service.DoacaoEntradaService;
import bancodesangue.poo.service.DoacaoSaidaService;
import bancodesangue.poo.service.DoadorService;
import bancodesangue.poo.service.HospitalService;
import bancodesangue.poo.service.MovimentacaoService;
import bancodesangue.poo.util.JPAUtil;
import bancodesangue.poo.view.MenuSistema;

public class Main {

    private static DoadorService doadorService;
    private static HospitalService hospitalService;
    private static DoacaoEntradaService entradaService;
    private static DoacaoSaidaService saidaService;
    private static DescarteService descarteService;
    private static MovimentacaoService movimentacaoService;

    private static MenuSistema view;

    public static void main(String[] args) {
        try {
            doadorService = new DoadorService();
            hospitalService = new HospitalService();
            entradaService = new DoacaoEntradaService();
            saidaService = new DoacaoSaidaService();
            descarteService = new DescarteService();
            movimentacaoService = new MovimentacaoService();

            view = new MenuSistema();

            boolean rodando = true;
            while (rodando) {
                int opcao = view.exibirMenuPrincipal();

                switch (opcao) {
                    case 1:
                        rodarMenuHemonucleo();
                        break;
                    case 2:
                        rodarMenuHospital();
                        break;
                    case 3:
                        rodarMenuRelatorios();
                        break;
                    case 0:
                        rodando = false;
                        view.mostrarMensagem("Encerrando sistema... Até logo!");
                        break;
                    default:
                        view.mostrarMensagem("Opção inválida!");
                }
            }

        } catch (Exception e) {
            if (view != null)
                view.mostrarMensagem("ERRO CRÍTICO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (view != null)
                view.fechar();
            JPAUtil.close();
        }
    }

    private static void rodarMenuHemonucleo() {
        boolean voltar = false;
        while (!voltar) {
            int op = view.exibirMenuHemonucleo();
            try {
                switch (op) {
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
                        visualizarEstoque();
                        break;
                    case 5:
                        listarTodosDoadores();
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        view.mostrarMensagem("Opção inválida.");
                }
            } catch (Exception e) {
                view.mostrarMensagem("ERRO NA OPERAÇÃO: " + e.getMessage());
            }
        }
    }

    private static void rodarMenuHospital() {
        boolean voltar = false;
        while (!voltar) {
            int op = view.exibirMenuHospital();
            try {
                switch (op) {
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
                        view.mostrarMensagem("Opção inválida.");
                }
            } catch (Exception e) {
                view.mostrarMensagem("ERRO NA OPERAÇÃO: " + e.getMessage());
            }
        }
    }

    private static void rodarMenuRelatorios() {
        boolean voltar = false;
        while (!voltar) {
            int op = view.exibirMenuRelatorios();
            try {
                switch (op) {
                    case 1:
                        listarHistorico(movimentacaoService.buscarHistoricoCompleto());
                        break;
                    case 2:
                        int ordem = view.lerInteiro("1. Crescente | 2. Decrescente: ");
                        listarHistorico(movimentacaoService.buscarPorData(ordem == 1));
                        break;
                    case 3:
                        listarRanking(entradaService.gerarRankingDoadores(), "DOADORES");
                        break;
                    case 4:
                        listarRanking(saidaService.gerarRankingHospitais(), "HOSPITAIS");
                        break;
                    case 5:
                        listarSaidasData();
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        view.mostrarMensagem("Opção inválida.");
                }
            } catch (Exception e) {
                view.mostrarMensagem("ERRO AO GERAR RELATÓRIO: " + e.getMessage());
            }
        }
    }

    private static void cadastrarDoador() {
        view.mostrarMensagem(">>> CADASTRO DE DOADOR <<<");
        Doador d = new Doador();

        d.setNome(view.lerString("Nome Completo:"));
        d.setCpf(view.lerString("CPF (somente números):"));
        d.setIdade(view.lerInteiro("Idade:"));
        d.setPeso(view.lerDouble("Peso (kg):"));
        d.setTelefone(view.lerString("Telefone:"));
        d.setGenero(view.lerGenero());
        d.setTipoSanguineo(view.lerTipoSanguineo());

        doadorService.cadastrarDoador(d);
        view.mostrarMensagem("SUCESSO! Doador cadastrado com ID: " + d.getId());
    }

    private static void registrarEntrada() {
        view.mostrarMensagem(">>> REGISTRO DOAÇÃO <<<");
        String nomeDoador = view.lerString("Digite o Nome do Doador:");

        Doador d = doadorService.buscarPorNome(nomeDoador);

        if (d == null) {
            view.mostrarMensagem("AVISO: Doador não encontrado com este Nome.");
            return;
        }
        view.mostrarMensagem("Doador Identificado: " + d.getNome() + " [" + d.getTipoSanguineo() + "]");

        DoacaoEntrada entrada = new DoacaoEntrada();
        entrada.setDoador(d);

        entradaService.registrarDoacao(entrada);
        view.mostrarMensagem("SUCESSO! Doação registrada e estoque atualizado.");
    }

    private static void registrarDescarte() {
        view.mostrarMensagem(">>> REGISTRO DE DESCARTE DE BOLSA <<<");
        Descarte des = new Descarte();

        System.out.println("Qual o tipo sanguíneo da bolsa descartada?");
        des.setTipoSanguineo(view.lerTipoSanguineo());

        des.setMotivoDescarte(view.lerString("Motivo do Descarte:"));
        des.setResponsavelDescarte(view.lerString("Responsável pelo Descarte (Nome):"));

        descarteService.registrarDescarte(des);
        view.mostrarMensagem("SUCESSO! Descarte registrado no histórico.");
    }

    private static void visualizarEstoque() {
        Map<TipoSanguineo, Long> mapaEstoque = new LinkedHashMap<>();

        for (TipoSanguineo tipo : TipoSanguineo.values()) {
            long qtd = saidaService.consultarEstoqueAtual(tipo);
            mapaEstoque.put(tipo, qtd);
        }
        view.exibirGraficoEstoque(mapaEstoque);
    }

    private static void listarTodosDoadores() {
        view.mostrarMensagem("--- LISTA DE DOADORES CADASTRADOS ---");
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
        System.out.println();
    }

    private static void cadastrarHospital() {
        view.mostrarMensagem(">>> CADASTRO DE HOSPITAL <<<");
        Hospital h = new Hospital();

        h.setNome(view.lerString("Nome do Hospital:"));
        h.setCnpj(view.lerLong("CNPJ (somente números):"));
        h.setEndereco(view.lerString("Endereço:"));
        h.setTelefone(view.lerString("Telefone:"));

        hospitalService.cadastrarHospital(h);
        view.mostrarMensagem("SUCESSO! Hospital cadastrado.");
    }

    private static void registrarSaida() {
        view.mostrarMensagem(">>> SOLICITAÇÃO DE BOLSAS <<<");
        String nomeHospital = view.lerString("Digite o Nome do Hospital solicitante:");

        Hospital h = hospitalService.buscarPorNome(nomeHospital);
        if (h == null) {
            view.mostrarMensagem("AVISO: Hospital não encontrado. Cadastre-o primeiro.");
            return;
        }
        view.mostrarMensagem("Hospital Identificado: " + h.getNome());

        DoacaoSaida saida = new DoacaoSaida();
        saida.setHospital(h);

        System.out.println("Qual tipo de sangue é necessário?");
        saida.setTipoSanguineo(view.lerTipoSanguineo());

        saida.setQuantidadeBolsas(view.lerInteiro("Quantidade de bolsas:"));

        saidaService.registrarSaida(saida);
        view.mostrarMensagem("SUCESSO! Solicitação aprovada e bolsas liberadas.");
    }

    private static void listarHistorico(List<Movimentacao> lista) {
        if (lista.isEmpty()) {
            view.mostrarMensagem("Nenhum registro encontrado.");
            return;
        }

        System.out.println("----------------------------------------------------------------");
        for (Movimentacao m : lista) {
            String tipo = "";
            String detalhe = "";

            if (m instanceof DoacaoEntrada) {
                tipo = "[ENTRADA]";
                detalhe = "Doador: " + ((DoacaoEntrada) m).getDoador().getNome();
            } else if (m instanceof DoacaoSaida) {
                tipo = "[SAÍDA]  ";
                detalhe = "Hospital: " + ((DoacaoSaida) m).getHospital().getNome() +
                        " (Qtd: " + ((DoacaoSaida) m).getQuantidadeBolsas() + ")";
            } else if (m instanceof Descarte) {
                tipo = "[DESCARTE]";
                detalhe = "Motivo: " + ((Descarte) m).getMotivoDescarte();
            }

            System.out.println(m.getData() + " | " + tipo + " | Sangue: " + m.getTipoSanguineo() + " | " + detalhe);
        }
        System.out.println("----------------------------------------------------------------\n");
    }

    private static void listarRanking(List<Object[]> ranking, String titulo) {
        view.mostrarMensagem("--- " + titulo + " ---");

        if (ranking.isEmpty()) {
            System.out.println("Sem dados para exibir.");
            return;
        }

        for (Object[] row : ranking) {
            EntidadeBase ent = (EntidadeBase) row[0];
            Long qtd = (Long) row[1];
            System.out.println(ent.getNome() + " - Total: " + qtd);
        }
        System.out.println();
    }

    private static void listarSaidasData() {
        int ordem = view.lerInteiro("1. Crescente | 2. Decrescente: ");
        List<DoacaoSaida> lista = saidaService.buscarPorData(ordem == 1);

        if (lista.isEmpty()) {
            view.mostrarMensagem("Nenhuma saída registrada.");
            return;
        }

        System.out.println("--- SAÍDAS POR DATA ---");
        for (DoacaoSaida s : lista) {
            System.out.println(s.getData() + " | Hospital: " + s.getHospital().getNome() +
                    " | Sangue: " + s.getTipoSanguineo() + " | Qtd: " + s.getQuantidadeBolsas());
        }
        System.out.println();
    }
}