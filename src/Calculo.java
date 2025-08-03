import org.mribeiro.constantes.Constantes;
import org.mribeiro.fatores.FatoresNumericos;
import org.mribeiro.sistemaNumerico.ClasseNumerica;
import org.mribeiro.sistemaNumerico.Numeral;
import org.mribeiro.sistemaNumerico.OrdemNumerica;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculo {

    private Numeral numero;
    //
    private DecimalFormat fmtGeral = new DecimalFormat("#,##0.##################"); // Shows one decimal if significant
    private DecimalFormat fmtDecimalBase10 = new DecimalFormat("0.##################"); // Shows one decimal if significant
    private DecimalFormat fmtInteiroBase10 = new DecimalFormat("#,##0");

    public Calculo(Numeral num) {

        this.numero = num;
    }

    private Numeral getNumero() throws Exception {
        return this.numero;
    }

    public boolean isDecimal() {
        return (this.numero.getBaseNumerica().getValorBase() == 10);
    }

    public String fatores() throws Exception {
        Numeral numero = getNumero();
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
        Numeral numero = getNumero();
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
        Numeral numero = getNumero();
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

    public String decompor() throws Exception {
        Numeral numero = getNumero();
        //boolean isDecimal = numero.getBaseNumerica().isBaseDecimal();
        int base = numero.getBaseNumerica().getValorBase();
        int digitosPorClasse = numero.getBaseNumerica().getDigitosPorClasse();
        String strNomeBase = Constantes.ARRAY_NOME_SISTEMAS_NUMERICOS[base];
        String charDigito = "#";
        String strFmtNaoDecimal = "\"#," + charDigito.repeat(digitosPorClasse) + ".##################";
        //DecimalFormat fmtBaseNaoDecimal = new DecimalFormat(strFmtNaoDecimal);
        FormatoNaoDecimal fmtBaseNaoDecimal = new FormatoNaoDecimal();
        String strTitulo = "";
        String strComentario = "";
        String strClasse = "";
        String strOrdem  = "";
        String strDecomposicao = "";
        String strSoma="";
        String fmtNum = "";
        String txtResultado = "";
        OrdemNumerica ord;
        ClasseNumerica classe;
        // OBS.: Os formatos numéricos estão definidos nos campos da classe
        //
        //
        strTitulo = "DECOMPOSIÇÃO DO NÚMERO "
                + (isDecimal() ? fmtGeral.format(numero.getValorAbsoluto()) : numero.toLiteral()) + "\n";
        strTitulo += (isDecimal()) ? "BASE DECIMAL (base "+numero.getBaseNumerica().getValorBase()+")\n"
                : (strNomeBase!="" ? "BASE "+strNomeBase : "BASE NÃO-DECIMAL") + " (base "+base+", "+digitosPorClasse+" dígitos por classe)\n";
        strComentario = "No sistema DECIMAL as ordens numéricas são agrupadas de 3 em 3, " +
                "formando as classes numéricas. Cada ordem e cada classe ganha um nome próprio.\n" +
                "Em bases não-decimais as ordens e classes não têm nome, " +
                "são apenas numeradas da direita para a esquerda em ordem crescente.\n" +
                "O grupamento de classes é livre, pode ser de 2, 3 ou mais algarismos dependendo da aplicação.\n";
        //
        // teste o valor ZERO
        if (numero.getValorAbsoluto().compareTo(BigDecimal.ZERO) == 0) {
             return  "Zero tem um número infinito de fatores.\n" +
                     "Isto significa que todo número inteiro, racional, real e imaginário é um fator de zero.\n" +
                     "Qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.";
        }
        // Parte inteira
        strDecomposicao = (isDecimal() ? "PARTE INTEIRA:" : "PARTE INTEIRA --> valores na base 10:");
        strDecomposicao += "\n";
        // CLASSES
        for (int i=numero.getParteInteira().size()-1; i>=0; i--) {
            strClasse = numero.getParteInteira().get(i).getNomeDaClasse();
            strClasse = (isDecimal() ?
                            ((i == 0) ? "Classe das " : "Classe dos ") + strClasse + ": " + numero.getParteInteira().get(i).toLiteral()
                            : (i+1) + "a. classe:");
            strClasse = "\n" + strClasse;
            strDecomposicao += (digitosPorClasse > 1) ? strClasse + "\n" : "";
            // ORDENS
            for (int j=numero.getParteInteira().get(i).size()-1; j>=0; j--) {
                ord = numero.getParteInteira().get(i).get(j);
                fmtNum = fmtInteiroBase10.format(ord.getValorPosicional());
                strOrdem = ord.getNomeDaOrdem();
                if(isDecimal()) {
                    strDecomposicao += "    " + ord.toLiteral() + " " + strOrdem + " (" + fmtNum + " unidades)" + "\n";
                } else {
                    strDecomposicao += "    " + strOrdem + ": " + ord.toLiteral()  + " --> " + fmtNum + " unidades\n";
                }
                strSoma += fmtNum + " + ";
                strClasse += "\n\n";
            }
        }
        // apaga o último sinal de "+"
        strSoma = strSoma.substring(0, strSoma.length()-3);
        strDecomposicao += "\n" + strSoma;
        //
        // Só processe a parte decimal se for maior que zero
        //
        if(numero.getParteDecimal().size() > 0) {
            // Parte decimal
            strDecomposicao += "\n\n";
            strDecomposicao += (isDecimal() ? "PARTE DECIMAL:" : "PARTE DECIMAL --> valores na base 10:");
            strDecomposicao += "\n";
            strSoma = "";
            //
            // CLASSES decimais não têm nome próprio
            for (int i = numero.getParteDecimal().size() - 1; i >= 0; i--) {
                // ORDENS decimais
                for (int j = numero.getParteDecimal().get(i).size() - 1; j >= 0; j--) {
                    ord = numero.getParteDecimal().get(i).get(j);
                    strOrdem = ord.getNomeDaOrdem();
                    fmtNum = fmtDecimalBase10.format(ord.getValorPosicional());
                    if (isDecimal()) {
                        strDecomposicao += "    " + ord.toLiteral() + " " + strOrdem + " (" + fmtNum + " da unidade)" + "\n";
                    } else {
                        strDecomposicao += "    " + strOrdem + ": " + ord.toLiteral() + " --> " + fmtNum + " da unidade\n";
                    }
                    strSoma += fmtNum + " + ";
                    strClasse += "\n\n";
                }
            }
            // apaga o último sinal de "+"
            strSoma = strSoma.substring(0, strSoma.length()-3);
            strDecomposicao += "\n"+ strSoma;
        }
        //
        txtResultado = strTitulo + "\n" + strDecomposicao;
        //
        //
        return txtResultado;
    }

    public String potencias() throws Exception {
        Numeral numero = getNumero();
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

    public String potenciaDasOrdens() throws Exception {
        Numeral numero = getNumero();
        String txtResultado = "";
        ArrayList<OrdemNumerica> arr = new ArrayList<>();
        OrdemNumerica ord;
        int base = numero.getBaseNumerica().getValorBase();

        if(numero.getParteInteira().getValorAbsoluto().compareTo(BigDecimal.ZERO) > 0) {
            arr = numero.getTodasAsOrdens(Constantes.NUMERAL_PARTE_INTEIRA);
            txtResultado += ("PARTE INTEIRA\n\n");
            for(int i=arr.size()-1; i>=0; i--) {
                ord = arr.get(i);
                //txtResultado += "("+ord.getValorAbsoluto().toPlainString()+"x"+base+"ˆ"+ord.getExpoente()+") + ";
                txtResultado += "("+ord.toLiteral()+" x "+base+"ˆ"+ord.getExpoente()+") + ";
            }
            txtResultado = txtResultado.substring(0, txtResultado.length() - 3);
            txtResultado += "\n\n";
        }
        if(numero.getParteDecimal().getValorAbsoluto().compareTo(BigDecimal.ZERO) > 0) {
            arr = numero.getTodasAsOrdens(Constantes.NUMERAL_PARTE_DECIMAL);
            txtResultado += "PARTE DECIMAL\n\n";
            for(int i=0; i< arr.size(); i++) {
                ord = arr.get(i);
                txtResultado += "("+ord.getValorAbsoluto().toPlainString()+"x"+base+"ˆ"+ord.getExpoente()+") + ";
            }
            txtResultado = txtResultado.substring(0, txtResultado.length() - 3);
            txtResultado += "\n\n";
        }

        return txtResultado;
    }

    public String porExtenso() throws Exception {
        Numeral numero = getNumero();
        String baseNumero = "";
        String titulo = "";
        String txtResultado = "";
        try {
            String strExtenso = numero.porExtenso();
            if (!isDecimal()) {
                baseNumero = "Número informado: " + numero.toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                titulo = baseNumero + "\nA transcrição só é possível na base 10.\n";
                titulo += "\n"+ "(base "+ numero.getBaseNumerica().getValorBase() + ") " + numero.toLiteral() + " --> (base 10) " + numero.getValorDecimal().toPlainString();
            } else {
                titulo = "Transcrição por extenso do número " + fmtGeral.format(numero.getValorAbsoluto());
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
        Numeral numero = getNumero();
        ArrayList<Long> fatores = new ArrayList<>();
        String txtResultado = "";
        if(numero.intValue()==0) {
            txtResultado = ("Não existem primos menores que zero; o menor número primo é o 2.");
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

    public String analise() throws Exception {
        Numeral numero = getNumero();
        // ordens e classes
        int numOrdensInteiras = numero.getNumOrdens(0);
        int numOrdensDecimais = numero.getNumOrdens(1);
        String nomeBase = numero.getBaseNumerica().getNomeBase();
        String strTitulo = "Análise do número "+numero.toLiteral()+"\n\n";
        String strOrdensEClasses = "Possui "+numOrdensInteiras+" ordens inteiras e "+numOrdensDecimais+" ordens decimais."+"\n";
        String strBaseNumero = "Base numérica: "+nomeBase+" base "+numero.getBaseNumerica().getValorBase()+"\n";

        String txtResultado = strTitulo+strBaseNumero+strOrdensEClasses;
        return txtResultado;
    }





}
