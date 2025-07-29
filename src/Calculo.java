import org.mribeiro.fatores.FatoresNumericos;
import org.mribeiro.sistemaNumerico.Numeral;

public class Calculo {

    private Numeral numero;

    public Calculo(Numeral num) {

        this.numero = num;
    }

    private Numeral pegaNumero() throws Exception {
        return this.numero;
    }

    public String fatores() throws Exception {
        Numeral numero = pegaNumero();
        String txtResultado = "";
        if(numero.intValue()==0) {
            //txtResultado.setText("Zero tem um número infinito de fatores, o que significa que todo número inteiro, inteiro, racional, real e imaginário é um fator de zero. Isso ocorre porque qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.");
            txtResultado="Zero tem um número infinito de fatores, o que significa que todo número inteiro, inteiro, racional, real e imaginário é um fator de zero. Isso ocorre porque qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.";

        } else {
            FatoresNumericos fatores = new FatoresNumericos();
            String baseNumero = "";
            String titulo = "";
            try {
                long quantos = (fatores.getFatores(numero.longValue())).size();
                String strFatores = String.valueOf(fatores.getFatores(numero.longValue()));
                if (!numero.getBaseNumerica().isBaseDecimal()) {
                    baseNumero = "Número informado: " + numero.toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                    titulo = baseNumero + "\nFatores do número " + numero.toLiteral() + " (DECIMAL " + numero.getValorDecimal().toPlainString() + ")";
                } else {
                    titulo = "Fatores do número " + numero.toLiteral()+" (Parte inteira: "+numero.getCharSinal()+numero.getParteInteira().toLiteral()+")";
                }
                titulo += "\n--> " + quantos + " fator" + ((quantos==1) ? "" : "es");
                txtResultado = (titulo + "\n\n" + strFatores);
            } catch (Exception e) {
                throw new RuntimeException(e);
                //JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular os fatores.\nTente novamente.");
            }
        }
        return txtResultado;
    }

    public String primos() throws Exception {
        Numeral numero = pegaNumero();
        String txtResultado = "";
        if(numero.intValue()==0) {
            txtResultado = ("Zero tem um número infinito de fatores, o que significa que todo número inteiro, inteiro, racional, real e imaginário é um fator de zero. Isso ocorre porque qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.");
        } else {
            FatoresNumericos fatores = new FatoresNumericos();
            String baseNumero = "";
            String titulo = "";
            try {
                String strFatores = String.valueOf(fatores.getFatoresPrimos(numero.longValue()));
                int quantos = fatores.getFatoresPrimos(numero).size();
                if (!numero.getBaseNumerica().isBaseDecimal()) {
                    baseNumero = "Número informado: " + numero.toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                    titulo = baseNumero + "\nFatores primos do número " + numero.getParteInteira().toLiteral() + " (DECIMAL " + numero.getParteInteira().getValorAbsoluto().toPlainString() + ")";
                } else {
                    titulo = "Fatores primos do número " + numero.getParteInteira().getValorAbsoluto().toPlainString();
                }
                titulo += "\n--> " + quantos + " fator" + ((quantos==1) ? "": "es") + " primo" + ((quantos==1) ? "": "s");
                txtResultado = (titulo + "\n\n" + strFatores);
            } catch (Exception e) {
                throw new RuntimeException(e);
                //JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular os primos.\nTente novamente.");
            }
        }
        return txtResultado;
    }

    public String fatoracao() throws Exception {
        Numeral numero = pegaNumero();
        String txtResultado = "";
        if(numero.intValue()==0) {
            txtResultado = ("Zero tem um número infinito de fatores, o que significa que todo número inteiro, inteiro, racional, real e imaginário é um fator de zero. Isso ocorre porque qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.");
        } else {
            FatoresNumericos fatores = new FatoresNumericos();
            String baseNumero = "";
            String titulo = "";
            try {
                String strFatores = fatores.getFatoracaoStr(numero.longValue());
                if (!numero.getBaseNumerica().isBaseDecimal()) {
                    baseNumero = "Número informado: " + numero.getParteInteira().toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                    titulo = baseNumero + "\nFatoração do número " + numero.getParteInteira().toLiteral() + " (DECIMAL " + numero.getParteInteira().getValorAbsoluto().toPlainString() + ")";
                } else {
                    titulo = "Fatoração do número " + numero.getParteInteira().getValorAbsoluto().toPlainString();
                }
                titulo += "\n(Fatorando apenas a parte INTEIRA)";
                txtResultado = (titulo + "\n\n" + strFatores);
            } catch (Exception e) {
                throw new RuntimeException(e);
                //JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular a fatoração.\nTente novamente.");
            }
        }
        return txtResultado;
    }

    public String potencias() throws Exception {
        Numeral numero = pegaNumero();
        String txtResultado = "";
        if(numero.intValue()==0) {
            txtResultado = ("Zero tem um número infinito de fatores, o que significa que todo número inteiro, inteiro, racional, real e imaginário é um fator de zero. Isso ocorre porque qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.");
        } else {
            FatoresNumericos fatores = new FatoresNumericos();
            String baseNumero = "";
            String titulo = "";
            try {
                String strFatores = fatores.getProdutoDePotencias(numero.longValue());
                if (!numero.getBaseNumerica().isBaseDecimal()) {
                    baseNumero = "Número informado: " + numero.getParteInteira().toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                    titulo = baseNumero + "\nProduto de potências do número " + numero.getParteInteira().toLiteral() + " (DECIMAL " + numero.getParteInteira().getValorAbsoluto().toPlainString() + ")";
                } else {
                    titulo = "Produto de potências do número " + numero.getParteInteira().getValorAbsoluto().toPlainString();
                }
                titulo += "\n(Fatorando apenas a parte INTEIRA)";
                txtResultado = (titulo + "\n\n" + strFatores);
            } catch (Exception e) {
                throw new RuntimeException(e);
                //JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular as potências.\nTente novamente.");
            }
        }
        return txtResultado;
    }

    public String porExtenso() throws Exception {
        Numeral numero = pegaNumero();
        String baseNumero = "";
        String titulo = "";
        String txtResultado = "";
        try {
            String strExtenso = numero.porExtenso();
            if (!numero.getBaseNumerica().isBaseDecimal()) {
                baseNumero = "Número informado: " + numero.toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                titulo = baseNumero + "\nA transcrição só é possível na base 10.\n";
                titulo += "\n"+ "(base "+ numero.getBaseNumerica().getValorBase() + ") " + numero.toLiteral() + " --> (base 10) " + numero.getValorDecimal().toPlainString();
            } else {
                titulo = "Transcrição por extenso do número " + numero.toLiteral();
            }
            titulo += "\n\n" + strExtenso;
            txtResultado = (titulo);

        } catch (Exception e) {
            throw new RuntimeException(e);
            //JOptionPane.showMessageDialog(frame, "ERRO! Impossível transcrever o número por extenso.\nTente novamente.");
        }
        return txtResultado;
    }

    public String primosAte() throws Exception {
        Numeral numero = pegaNumero();
        String txtResultado = "";
        if(numero.intValue()==0) {
            txtResultado = ("Não existem primos menores que zero; o menor número primo é o 2.");
        } else {
            FatoresNumericos primosAte = new FatoresNumericos();
            String baseNumero = "";
            String titulo = "";
            try {

                String strPrimos = primosAte.getFatoresPrimosAte(numero.longValue()).toString();
                if (!numero.getBaseNumerica().isBaseDecimal()) {
                    baseNumero = "Número informado: " + numero.toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                    titulo = baseNumero + "\nPrimos até o número " + numero.getParteInteira().toLiteral() + " (DECIMAL " + numero.getParteInteira().getValorAbsoluto().toPlainString() + ")";
                    titulo += "\n--> " + primosAte.getFatoresPrimosAte(numero).size() + " números primos entre 2 e " + numero.getValorDecimal().toPlainString();
                } else {
                    titulo = "Primos até o número " + numero.getParteInteira().getValorAbsoluto().toPlainString();
                    titulo += "\n--> " + primosAte.getFatoresPrimosAte(numero).size() + " números primos entre 2 e " + numero.getParteInteira().getValorAbsoluto().toPlainString();
                }
                txtResultado = (titulo + "\n\n" + strPrimos);
            } catch (Exception e) {
                throw new RuntimeException(e);
                //JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular os fatores primos.\nTente novamente.");
            }
        }
        return txtResultado;
    }

    public String estrutura() throws Exception {
        Numeral numero = pegaNumero();
        String baseNumero = "";
        String titulo = "";
        String txtResultado = "";
        txtResultado = ("Estrutura numérica de "+String.valueOf(numero)+"\n\n(EM DESENVOLVIMENTO)");
        return txtResultado;
    }





}
