package bancodesangue.poo.service;

import bancodesangue.poo.dao.DaoHospital;
import bancodesangue.poo.entity.Hospital;
import bancodesangue.poo.util.ValidadorCNPJ;

public class HospitalService {

    private DaoHospital hospitalDao;

    public HospitalService(DaoHospital hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    public Hospital cadastrarHospital(Hospital hospital) {
        verificarAtributos(hospital);
        return hospitalDao.inserir(hospital);
    }

    public void verificarAtributos(Hospital hospital) {
        if (hospital.getNome() == null || hospital.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (!ValidadorCNPJ.validarCnpj(hospital.getCnpj())) {
            throw new IllegalArgumentException("CNPJ inválido.");
        }

        Hospital existente = hospitalDao.buscarPorCnpj(hospital.getCnpj());
        if (existente != null && (hospital.getId() == null || !existente.getId().equals(hospital.getId()))) {
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }
    }
}