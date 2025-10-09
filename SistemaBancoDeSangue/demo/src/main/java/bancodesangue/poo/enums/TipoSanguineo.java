package bancodesangue.poo.enums;

public enum TipoSanguineo {
    A_POS("A+"),
    A_NEG("A-"),
    B_POS("B+"),
    B_NEG("B-"),
    AB_POS("AB+"),
    AB_NEG("AB-"),
    O_POS("O+"),
    O_NEG("O-");

    private final String valor;

    TipoSanguineo(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}