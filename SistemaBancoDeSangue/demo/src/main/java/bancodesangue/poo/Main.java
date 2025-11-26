package bancodesangue.poo;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import bancodesangue.poo.dao.DaoDescarte;
import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.dao.DaoDoacaoSaida;
// Imports dos DAOs
import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.dao.DaoHospital;
import bancodesangue.poo.entity.DoacaoDescarte;
import bancodesangue.poo.entity.DoacaoEntrada;
import bancodesangue.poo.entity.DoacaoSaida;
// Imports das Entidades
import bancodesangue.poo.entity.Doador;
import bancodesangue.poo.entity.Hospital;
import bancodesangue.poo.enums.Genero;
// Imports dos Enums
import bancodesangue.poo.enums.TipoSanguineo;
import bancodesangue.poo.service.DescarteService;
import bancodesangue.poo.service.DoacaoEntradaService;
import bancodesangue.poo.service.DoacaoSaidaService;
// Imports dos Services
import bancodesangue.poo.service.DoadorService;
import bancodesangue.poo.service.HospitalService;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    // Services estáticos para serem usados nos menus
    private static DoadorService doadorService;
    private static HospitalService hospitalService;
    private static DoacaoEntradaService entradaService;
    private static DoacaoSaidaService saidaService;
    private static DescarteService descarteService;

    // DAOs auxiliares para buscas rápidas no menu (ex: buscar doador pelo ID)
    private static DaoDoador daoDoador;
    private static DaoHospital daoHospital;

    public static void main(String[] args) {
        // 1. CONFIGURAÇÃO INICIAL (A "Fábrica" de conexões)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenciaPU");
        EntityManager em = emf.createEntityManager();

        try {
            // 2. INJEÇÃO DE DEPENDÊNCIA (Criamos tudo aqui uma única vez)

            // Instancia os DAOs com a conexão (em)
            daoDoador = new DaoDoador(em);
            daoHospital = new DaoHospital(em);
            DaoDoacaoEntrada daoEntrada = new DaoDoacaoEntrada(em);
            DaoDoacaoSaida daoSaida = new DaoDoacaoSaida(em);
            DaoDescarte daoDescarte = new DaoDescarte(em);

            // Instancia os Services injetando os DAOs neles
            doadorService = new DoadorService(daoDoador);
            hospitalService = new HospitalService(daoHospital);
            entradaService = new DoacaoEntradaService(daoEntrada, daoDoador);
            saidaService = new DoacaoSaidaService(daoSaida, daoEntrada, daoHospital);
            descarteService = new DescarteService(daoDescarte);

            // 3. MENU PRINCIPAL
            boolean rodando = true;
            while (rodando) {
                System.out.println("\n=== SISTEMA DE BANCO DE SANGUE ===");
                System.out.println("Selecione seu perfil:");
                System.out.println("1. HEMONÚCLEO (Gerenciar Doadores, Coletas e Estoque)");
                System.out.println("2. HOSPITAL (Solicitar Sangue)");
                System.out.println("0. Sair");
                System.out.print("Opção: ");

                int opcao = lerInteiro();

                switch (opcao) {
                    case 1:
                        menuHemonucleo();
                        break;
                    case 2:
                        menuHospital();
                        break;
                    case 0:
                        rodando = false;
                        System.out.println("Encerrando sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fecha a conexão ao sair
            if (em != null)
                em.close();
            if (emf != null)
                emf.close();
            scanner.close();
        }
    }

    // --- SUB-MENU: HEMONÚCLEO ---
    private static void menuHemonucleo() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- PAINEL DO HEMONÚCLEO ---");
            System.out.println("1. Cadastrar Novo Doador");
            System.out.println("2. Registrar DOAÇÃO (Entrada)");
            System.out.println("3. Registrar DESCARTE de Bolsa");
            System.out.println("4. Listar todos os Doadores");
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
                        listarDoadores();
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }
    }

    // --- SUB-MENU: HOSPITAL ---
    private static void menuHospital() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- PAINEL DO HOSPITAL ---");
            System.out.println("1. Cadastrar Hospital");
            System.out.println("2. Solicitar Bolsas de Sangue (Saída)");
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
                System.out.println("ERRO: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS DE AÇÃO (HEMONÚCLEO) ---

    private static void cadastrarDoador() {
        System.out.println("\n--- Cadastro de Doador ---");
        Doador d = new Doador();

        System.out.print("Nome: ");
        d.setNome(scanner.nextLine());

        System.out.print("CPF (somente números): ");
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
        System.out.println("Doador " + salvo.getNome() + " cadastrado com sucesso! ID: " + salvo.getId());
    }

    private static void registrarEntrada() {
        System.out.println("\n--- Registrar Doação (Entrada) ---");

        System.out.print("ID do Doador: ");
        Long idDoador = lerLong();

        // Buscamos o doador apenas para associar ao objeto (o Service valida tudo
        // depois)
        Doador doador = daoDoador.buscarPorId(idDoador);
        if (doador == null) {
            System.out.println("Doador não encontrado!");
            return;
        }
        System.out.println("Doador selecionado: " + doador.getNome() + " (" + doador.getTipoSanguineo() + ")");

        DoacaoEntrada entrada = new DoacaoEntrada();
        entrada.setDoador(doador);
        // Data e Tipo Sanguíneo são preenchidos automaticamente no Service

        entradaService.registrarDoacao(entrada);
        System.out.println("Doação registrada com sucesso! Estoque atualizado.");
    }

    private static void registrarDescarte() {
        System.out.println("\n--- Registrar Descarte ---");

        DoacaoDescarte descarte = new DoacaoDescarte();

        System.out.print("Motivo do Descarte: ");
        descarte.setMotivoDescarte(scanner.nextLine());

        System.out.print("Responsável pelo Descarte: ");
        descarte.setResponsavelDescarte(scanner.nextLine());

        System.out.println("Qual tipo sanguíneo está sendo descartado?");
        descarte.setTipoSanguineo(lerTipoSanguineo());

        descarteService.registrarDescarte(descarte);
        System.out.println("Descarte registrado.");
    }

    private static void listarDoadores() {
        System.out.println("\n--- Lista de Doadores ---");
        for (Doador d : doadorService.buscarTodos()) {
            System.out.println("ID: " + d.getId() + " | Nome: " + d.getNome() + " | Tipo: " + d.getTipoSanguineo()
                    + " | Última Doação: " + (d.getDataUltimaDoacao() == null ? "Nunca" : d.getDataUltimaDoacao()));
        }
    }

    // --- MÉTODOS DE AÇÃO (HOSPITAL) ---

    private static void cadastrarHospital() {
        System.out.println("\n--- Cadastro de Hospital ---");
        Hospital h = new Hospital();

        System.out.print("Nome do Hospital: ");
        h.setNome(scanner.nextLine());

        System.out.print("CNPJ (somente números): ");
        h.setCnpj(lerLong()); // Ajuste: seu metodo setCnpj recebe Long, confira na sua Entity

        System.out.print("Endereço: ");
        h.setEndereco(scanner.nextLine());

        System.out.print("Telefone: ");
        h.setTelefone(scanner.nextLine());

        hospitalService.cadastrarHospital(h);
        System.out.println("Hospital cadastrado com sucesso!");
    }

    private static void registrarSaida() {
        System.out.println("\n--- Solicitar Bolsas (Saída) ---");

        System.out.print("ID do Hospital solicitante: ");
        Long idHospital = lerLong();
        Hospital hospital = daoHospital.buscarPorId(idHospital);

        if (hospital == null) {
            System.out.println("Hospital não encontrado! Cadastre-o primeiro.");
            return;
        }

        DoacaoSaida saida = new DoacaoSaida();
        saida.setHospital(hospital);

        System.out.println("Qual tipo de sangue o hospital precisa?");
        saida.setTipoSanguineo(lerTipoSanguineo());

        System.out.print("Quantidade de bolsas: ");
        saida.setQuantidadeBolsas(lerInteiro());

        saidaService.registrarSaida(saida);
        System.out.println("Solicitação aprovada! Estoque atualizado.");
    }

    // --- MÉTODOS AUXILIARES DE LEITURA ---

    private static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static Long lerLong() {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    private static Genero lerGenero() {
        System.out.println("Selecione o Gênero:");
        System.out.println("1. MASCULINO");
        System.out.println("2. FEMININO");
        int op = lerInteiro();
        if (op == 1)
            return Genero.MASCULINO;
        if (op == 2)
            return Genero.FEMININO;
        System.out.println("Opção inválida, definindo padrão MASCULINO.");
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
                System.out.println("Opção inválida. Definindo O+ por segurança.");
                return TipoSanguineo.O_POS;
        }
    }
}