package bancodesangue.poo.util;

public class ValidadorCPF {

    public static boolean validar(String cpf) {
        if (cpf == null)
            return false;

        cpf = cpf.replaceAll("\\D", ""); // remove tudo que não é número
        if (cpf.length() != 11)
            return false;

        // Verifica se todos os dígitos são iguais
        if (cpf.chars().distinct().count() == 1)
            return false;

        try {
            int[] peso1 = { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
            int[] peso2 = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * peso1[i];
            }
            int dig1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * peso2[i];
            }
            soma += dig1 * peso2[9];
            int dig2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            return dig1 == Character.getNumericValue(cpf.charAt(9)) &&
                    dig2 == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
