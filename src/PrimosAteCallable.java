import java.util.concurrent.Callable;
import org.mribeiro.constantes.Constantes;
import org.mribeiro.fatores.FatoresNumericos;
import org.mribeiro.sistemaNumerico.ClasseNumerica;
import org.mribeiro.sistemaNumerico.Numeral;
import org.mribeiro.sistemaNumerico.OrdemNumerica;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

class PrimosAteCallable implements Callable<String> {
    private Numeral numero;

    public PrimosAteCallable(Numeral numero) {
        this.numero = numero;
    }

    @Override
    public String call() throws Exception {
        ArrayList<Long> fatores = new ArrayList<>();
        String txtResultado = "";
        if(numero.intValue()<=1) {
            txtResultado = ("Não existem primos menores que 2; o menor número primo é o 2.");
        } else {
            FatoresNumericos primosAte = new FatoresNumericos();
            String baseNumero = "";
            String titulo = "";
            try {
                fatores = primosAte.getFatoresPrimosAte(numero.longValue());
                String strPrimos = fatores.toString();
                if (!numero.getBaseNumerica().isBaseDecimal()) {
                    baseNumero = "Número informado: " + numero.toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                    titulo = baseNumero + "\nPrimos até o número " + numero.getParteInteira().toLiteral() + " (DECIMAL " + numero.getParteInteira().getValorAbsoluto().toPlainString() + ")";
                    titulo += "\n--> " + fatores.size() + " números primos entre 2 e " + numero.getValorDecimal().toPlainString();
                } else {
                    titulo = "Primos até o número " + numero.getParteInteira().getValorAbsoluto().toPlainString();
                    titulo += "\n--> " + fatores.size() + " números primos entre 2 e " + numero.getParteInteira().getValorAbsoluto().toPlainString();
                }
                txtResultado = (titulo + "\n\n" + strPrimos);
            } catch (Exception e) {
                throw new RuntimeException(e);
                //JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular os fatores primos.\nTente novamente.");
            }
        }
        return txtResultado;
    }
}