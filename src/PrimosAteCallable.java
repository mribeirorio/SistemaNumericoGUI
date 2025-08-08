import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.ArrayList;

import org.mribeiro.sistemaNumerico.Numeral;

import javax.swing.*;

class PrimosAteCallable implements Callable<String> {
    private String strRetorno;

    public PrimosAteCallable(Numeral num, JTextPane saida) {
        ArrayList<Long> fatores = new ArrayList<>();
        String txtResultado = "";
        if (num.intValue() <= 1) {
            txtResultado = ("Não existem primos menores que 2; o menor número primo é o 2.");
        } else {
            //
            saida.setText("Calculando, aguarde...");
            // retirado de fatoresNumericos
            long numero = num.longValue();
            ArrayList<BigInteger> factors = new ArrayList<>();

            BigInteger contador = BigInteger.TWO;
            BigInteger bignum = BigInteger.valueOf(numero);

            while (contador.compareTo(bignum) < 0) {
                factors.add(contador);
                saida.setText(contador.toString());
                contador = contador.nextProbablePrime();
            }
            ArrayList<BigInteger> arrayList = new ArrayList<>(factors.stream().distinct().toList());
            txtResultado = arrayList.toString();
            //
        }
        strRetorno = txtResultado;
    }

    @Override
    public String call() throws Exception {
        return strRetorno;
    }


}