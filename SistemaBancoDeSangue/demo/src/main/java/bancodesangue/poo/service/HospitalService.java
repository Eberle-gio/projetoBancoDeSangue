package bancodesangue.poo.service;

import java.util.List;

import bancodesangue.poo.dao.DaoHospital;
import bancodesangue.poo.entity.Hospital;

public class HospitalService {

    private DaoHospital dao;

    public HospitalService() {
        this.dao = new DaoHospital();
    }

    public Hospital cadastrarHospital(Hospital hospital) {
        hospital.validarCadastro();

        Hospital existente = dao.buscarPorCnpj(hospital.getCnpj());
        if (existente != null && (hospital.getId() == null || !existente.getId().equals(hospital.getId()))) {
            throw new IllegalArgumentException("CNPJ já cadastrado no sistema.");
        }

        return dao.inserir(hospital);
    }

    public Hospital atualizarHospital(Hospital hospital) {
        hospital.validarCadastro();
        return dao.atualizar(hospital);
    }

    public List<Hospital> buscarTodos() {
        return dao.buscarTodos();
    }

    public Hospital buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    public Hospital buscarPorNome(String nome) {
        return dao.buscarPorNome(nome);
    }
}