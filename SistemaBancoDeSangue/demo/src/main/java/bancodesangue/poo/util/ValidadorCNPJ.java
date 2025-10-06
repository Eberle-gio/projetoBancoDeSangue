package bancodesangue.poo.util;

public class ValidadorCNPJ {
    public static boolean validarCnpj(Long cnpj) {
        if (cnpj == null)
            return false;
        String cnpjStr = String.valueOf(cnpj);
        return cnpjStr.length() == 14;
    }

}
