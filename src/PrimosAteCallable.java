import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.mribeiro.sistemaNumerico.Numeral;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

class PrimosAteCallable implements Callable<String> {
    public String strRetorno;
    private Numeral num;
    public JProgressBar barra;
    public JTextPane saida;

    public PrimosAteCallable(Numeral numero, JProgressBar bar, JTextPane saida) {
        num = numero;
        this.barra = bar;
        this.saida = saida;
    }

    @Override
    public String call() throws Exception {

        ArrayList<Long> fatores = new ArrayList<>();
        String txtResultado = "";
        if (num.intValue() <= 1) {
            txtResultado = ("Não existem primos menores que 2; o menor número primo é o 2.");
        } else {
            //
            // retirado de fatoresNumericos
            //saida.setText("Calculando, aguarde...");
            long numero = num.longValue();
            ArrayList<BigInteger> factors = new ArrayList<>();

            BigInteger contador = BigInteger.TWO;
            BigInteger bignum = BigInteger.valueOf(numero);

            while (contador.compareTo(bignum) < 0) {
                factors.add(contador);
                barra.setValue(((BigInteger.valueOf(100).multiply(contador)).divide(bignum)).intValue());
                barra.repaint();
                saida.setText("Calculando, aguarde...\n\n"+contador.toString());
                contador = contador.nextProbablePrime();
            }
            ArrayList<BigInteger> arrayList = new ArrayList<>(factors.stream().distinct().toList());
            txtResultado = "Encontrei "+arrayList.size()+" primos entre 2 e "+numero+":\n\n";
            txtResultado += arrayList.toString();
            //saida.setText(txtResultado);
            //
        }
        //saida.setText(txtResultado);
        this.strRetorno = txtResultado;
        return txtResultado;
    }


}