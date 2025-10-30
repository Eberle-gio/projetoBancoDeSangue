package bancodesangue.poo.service;

import bancodesangue.poo.dao.DaoHospital;
import bancodesangue.poo.entity.Hospital;

public class HospitalService {

    private DaoHospital hospitalDao;

    public HospitalService() {
        this.hospitalDao = new DaoHospital();
    }

    public Hospital cadastrarHospital(Hospital hospital) {
        verificarAtributos(hospital);
        return hospitalDao.inserir(hospital);
    }

    public void verificarAtributos(Hospital hospital) {
        if (hospital.getNome() == null || hospital.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do hospital não pode ser vazio.");
        }
        if (hospital.getCnpj() == null || hospital.getCnpj() <= 13) {
            throw new IllegalArgumentException("CNPJ inválido.");
        }
        if (hospital.getEndereco() == null) {
            throw new IllegalArgumentException("Endereço do hospital não pode ser vazio.");
        }
        if (hospital.getTelefone() == null) {
            throw new IllegalArgumentException("Telefone do hospital não pode ser vazio.");
        }
    }

    public Hospital buscarPorNome(String nome) {
        return hospitalDao.buscarPorNome(nome);
    }

    public Hospital atualizar(Hospital hospital) {
        verificarAtributos(hospital);
        return hospitalDao.atualizar(hospital);
    }

    public void excluir(Hospital hospital) {
        hospitalDao.excluir(hospital);
    }
}
