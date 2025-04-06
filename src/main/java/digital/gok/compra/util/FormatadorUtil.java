package digital.gok.compra.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatadorUtil {
    public static String formatarCpf(String cpf) {
        if (cpf == null) return "";
        cpf = cpf.replaceAll("\\D", "");
        cpf = String.format("%011d", Long.parseLong(cpf));
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String formatarMoeda(BigDecimal valor) {
        if (valor == null) return "R$ 0,00";
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

    public static String formatarPercentual(double valor) {
        return String.format("%.2f%%", valor);
    }
}