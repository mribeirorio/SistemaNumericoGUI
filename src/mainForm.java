import org.mribeiro.constantes.Constantes;
import org.mribeiro.exception.IndiceForaDaFaixaException;
import org.mribeiro.fatores.FatoresNumericos;
import org.mribeiro.sistemaNumerico.Numeral;

import javax.swing.*;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Enumeration;

public class mainForm {
    private static JFrame frame = new JFrame("Análise Numérica v1.0");
    private JPanel pnlContainer;
    private JPanel pnlMenu;
    private JButton btnEncerrar;
    private JButton btnCalcular;
    private JButton btnLimparTudo;

    private JTextField txtNumero;
    private JTextArea txtResultado;
    private JLabel lblNumero;
    private JPanel pnlResultado;
    private JLabel lblPrimoComposto;

    private final SpinnerNumberModel numberModel = new SpinnerNumberModel(10, 2, Constantes.ALGARISMOS_POSSIVEIS.length(), 1); // initial, min, max, step
    private JSpinner spnBase;// = new JSpinner(numberModel);;
    private JRadioButton btnFatores;
    private JRadioButton btnPrimos;
    private JRadioButton btnFatoracao;
    private JRadioButton btnDecompor;
    private JRadioButton btnPotencias;
    private JRadioButton btnExtenso;
    private JRadioButton btnPotenciaDasOrdens;
    private JRadioButton btnAnalise;
    private JRadioButton btnPrimosAte;

    private ButtonGroup btnGroup = new ButtonGroup();

    private JLabel lblDebug;
    private JLabel lblMenu;
    private JLabel lblAlgarismosValidos;
    private JScrollPane pnlScroll;


    public mainForm() {
        txtResultado.setLineWrap(true);
        txtResultado.setBorder(null);
        pnlScroll.setBorder(null);

        spnBase.setModel(numberModel);
        // Make the JSpinner's text field non-editable
        ((JSpinner.DefaultEditor) spnBase.getEditor()).getTextField().setEditable(false);

        txtNumero.setBorder(null);
        //JTextField textField = new JTextField(20);
        PlainDocument doc = (PlainDocument) txtNumero.getDocument();
        doc.setDocumentFilter(new NumericDocumentFilter((Integer) spnBase.getValue()));
        // Atualiza o rótulo de algarismos válidos
        ajustaAlgarismos();

        // menu
        btnFatores.setActionCommand("fatores");
        btnPrimos.setActionCommand("primos");
        btnFatoracao.setActionCommand("fatoracao");
        btnDecompor.setActionCommand("decompor");
        btnPotencias.setActionCommand("potencias");
        btnExtenso.setActionCommand("extenso");
        btnPotenciaDasOrdens.setActionCommand("potenciaDasOrdens");
        btnAnalise.setActionCommand("analise");
        btnPrimosAte.setActionCommand("primosAte");
        btnGroup.add(btnFatores);
        btnGroup.add(btnPrimos);
        btnGroup.add(btnFatoracao);
        btnGroup.add(btnDecompor);
        btnGroup.add(btnPotencias);
        btnGroup.add(btnPotenciaDasOrdens);
        btnGroup.add(btnExtenso);
        btnGroup.add(btnAnalise);
        btnGroup.add(btnPrimosAte);
        btnAnalise.setSelected(true);

        menuHabilitado(false);

        btnEncerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        spnBase.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = (int) spnBase.getValue();
                limpaEntrada();
                // redefine o filtro de entrada de acordo com a base numérica
                doc.setDocumentFilter(new NumericDocumentFilter(currentValue));
                lblPrimoComposto.setText(getNomeDaBase(currentValue));
            }
        });

        lblPrimoComposto.setText(getNomeDaBase((int)spnBase.getValue()));

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strResultado = "";
                ButtonModel selectedModel = btnGroup.getSelection();
                if (selectedModel != null) {
                    try {
                        primoOuComposto();
                    } catch (IndiceForaDaFaixaException ex) {
                        throw new RuntimeException(ex);
                    }
                    // A radio button is selected

                    String selectedActionCommand = selectedModel.getActionCommand();
                    Numeral num = pegaNumero();
                    Calculo calculo;
                    try {
                        if(num.getParteDecimal().getValorAbsoluto().compareTo(BigDecimal.ZERO) > 0) {
                            calculo = new Calculo(num);
                        } else {
                            calculo = new Calculo(num);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    //JOptionPane.showMessageDialog(frame, "Selected: " + selectedActionCommand);
                    switch(selectedActionCommand) {
                        case "fatores": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.fatores();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "primos": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.primos();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "fatoracao": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.fatoracao();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "decompor": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.decompor();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "potencias": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.potencias();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "potenciaDasOrdens": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.potenciaDasOrdens();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "extenso": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.porExtenso();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "primosAte": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.primosAte();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "analise": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.analise();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                    }
                    txtResultado.setText(strResultado);
                } else {
                    // No radio button is currently selected
                    JOptionPane.showMessageDialog(frame, "No option selected.");
                }
            }
        });

        txtNumero.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Called when text is inserted
                checkTextFieldStatus();
                limpaResultado();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Called when text is removed
                checkTextFieldStatus();
                limpaResultado();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Called when attributes of the text are changed (less common for basic text fields)
                checkTextFieldStatus();
                limpaResultado();
            }
        });

        btnLimparTudo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpaTudo();
            }
        });


    }

    public static void main(String[] args) {
        //JFrame frame = new JFrame("Análise Numérica v1.0");

        frame.setContentPane(new mainForm().pnlContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(750, 550);

        frame.setLocationRelativeTo(null);// Center the frame on the screen
        frame.pack();
        frame.setVisible(true);
        SwingUtilities.invokeLater(mainForm::new);

    }

    private String getNomeDaBase(int base) {
        String strBase = Constantes.ARRAY_NOME_SISTEMAS_NUMERICOS[base];
        if(strBase=="") {
            strBase = "BASE "+base;
        } else {
            strBase = "BASE "+strBase;
        }
        return strBase;
    }

    private void setNomeDaBase() {
        lblPrimoComposto.setText(getNomeDaBase((int)spnBase.getValue()));
    }

    private void limpaEntrada() {
        // Limpa o filtro de algarismos para poder resetar o campo de texto
        AbstractDocument currentDoc = (AbstractDocument) txtNumero.getDocument();
        currentDoc.setDocumentFilter(null); // Remove the filter
        txtNumero.setText("");
        // recoloca o filtro de algarismos
        int currentValue = (int) spnBase.getValue();
        currentDoc.setDocumentFilter(new NumericDocumentFilter(currentValue));
        checkTextFieldStatus();
    }

    private void limpaResultado() {
        lblPrimoComposto.setText("");
        txtResultado.setText("");
    }

    private void ajustaAlgarismos() {
        String strAlgarismos="Algarismos válidos na base "+spnBase.getValue().toString()+" -> ";
        strAlgarismos += Constantes.ALGARISMOS_POSSIVEIS.substring(0,(int)spnBase.getValue());
        lblAlgarismosValidos.setText(strAlgarismos);
        //limpaTudo();
        setNomeDaBase();
    }

    private void checkTextFieldStatus() {
        if (txtNumero.getText().isEmpty()) {
            // Action to perform when the text field is empty
            menuHabilitado(false);
            lblPrimoComposto.setText("");
            ajustaAlgarismos();
        } else {
            // Action to perform when the text field is not empty
            menuHabilitado(true);
            ajustaAlgarismos();
        }
    }

    private void limpaTudo() {
        limpaEntrada();
        txtResultado.setText("");
        lblPrimoComposto.setText("");
        btnAnalise.setSelected(true);
        spnBase.setValue(10);
        menuHabilitado(false);
        ajustaAlgarismos();
        frame.setSize(750, 550);
        frame.repaint();
        frame.pack();
        frame.setLocationRelativeTo(null);// Center the frame on the screen
    }

    private Numeral pegaNumero() {
        String str = txtNumero.getText();
        int base = (int)spnBase.getValue();
        Numeral num = null;
        try {
            num = new Numeral(base,str);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            JOptionPane.showMessageDialog(frame, "ERRO! Valor numérico inválido.\nTente novamente.");
        }
        return num;
    }

    private void menuHabilitado(boolean enabled) {
        btnCalcular.setEnabled(enabled);
        //btnLimparTudo.setEnabled(enabled);
        Enumeration<AbstractButton> buttons = btnGroup.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            button.setEnabled(enabled);
        }
    }

    private void primoOuComposto() throws IndiceForaDaFaixaException {
        Numeral numero = pegaNumero();
        if(numero != null) {
            FatoresNumericos fatores = new FatoresNumericos();
            if(fatores.e_Primo(numero)) {
                lblPrimoComposto.setText("Número PRIMO");
                lblPrimoComposto.setForeground(Color.RED);
            } else if(numero.intValue()==0 || numero.intValue()==1) {
                lblPrimoComposto.setText("O número "+numero.longValue()+" não é primo nem composto");
                lblPrimoComposto.setForeground(Color.ORANGE);
            } else {
                lblPrimoComposto.setText("Número COMPOSTO");
                lblPrimoComposto.setForeground(Color.BLUE);
            }
        } else {
            lblPrimoComposto.setText("");
        }
    }
/*
    private void fatores() {
        Numeral numero = pegaNumero();
        if(numero.intValue()==0) {
            txtResultado.setText("Zero tem um número infinito de fatores, o que significa que todo número inteiro, inteiro, racional, real e imaginário é um fator de zero. Isso ocorre porque qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.");
        } else {
            FatoresNumericos fatores = new FatoresNumericos();
            String baseNumero = "";
            String titulo = "";
            try {
                long tamanho = (fatores.getFatores(numero.longValue())).size();
                String strFatores = String.valueOf(fatores.getFatores(numero.longValue()));
                if (!numero.getBaseNumerica().isBaseDecimal()) {
                    baseNumero = "Número informado: " + numero.toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                    titulo = baseNumero + "\nFatores do número " + numero.getParteInteira().toLiteral() + " (DECIMAL " + numero.getParteInteira().getValorAbsoluto().toPlainString() + ")";
                    titulo += "\n--> " + tamanho + " fatores";
                } else {
                    titulo = "Fatores do número " + numero.getParteInteira().getValorAbsoluto().toPlainString();
                    titulo += "\n--> " + tamanho + " fatores";
                }
                txtResultado.setText(titulo + "\n\n" + strFatores);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular os fatores.\nTente novamente.");
            }
        }
    }

    private void primos() {
        Numeral numero = pegaNumero();
        if(numero.intValue()==0) {
            txtResultado.setText("Zero tem um número infinito de fatores, o que significa que todo número inteiro, inteiro, racional, real e imaginário é um fator de zero. Isso ocorre porque qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.");
        } else {
            FatoresNumericos fatores = new FatoresNumericos();
            String baseNumero = "";
            String titulo = "";
            try {
                String strFatores = String.valueOf(fatores.getFatoresPrimos(numero.longValue()));
                if (!numero.getBaseNumerica().isBaseDecimal()) {
                    baseNumero = "Número informado: " + numero.toLiteral() + " (base " + numero.getBaseNumerica().getValorBase() + ")";
                    titulo = baseNumero + "\nFatores primos do número " + numero.getParteInteira().toLiteral() + " (DECIMAL " + numero.getParteInteira().getValorAbsoluto().toPlainString() + ")";
                    titulo += "\n--> " + fatores.getFatoresPrimos(numero).size() + " fatores primos";
                } else {
                    titulo = "Fatores primos do número " + numero.getParteInteira().getValorAbsoluto().toPlainString();
                    titulo += "\n--> " + fatores.getFatoresPrimos(numero).size() + " fatores primos";
                }
                txtResultado.setText(titulo + "\n\n" + strFatores);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular os primos.\nTente novamente.");
            }
        }
    }

    private void fatoracao() {
        Numeral numero = pegaNumero();
        if(numero.intValue()==0) {
            txtResultado.setText("Zero tem um número infinito de fatores, o que significa que todo número inteiro, inteiro, racional, real e imaginário é um fator de zero. Isso ocorre porque qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.");
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
                txtResultado.setText(titulo + "\n\n" + strFatores);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular a fatoração.\nTente novamente.");
            }
        }
    }

    private void potencias() {
        Numeral numero = pegaNumero();
        if(numero.intValue()==0) {
            txtResultado.setText("Zero tem um número infinito de fatores, o que significa que todo número inteiro, inteiro, racional, real e imaginário é um fator de zero. Isso ocorre porque qualquer número multiplicado por zero é igual a zero, então qualquer número pode dividir zero sem deixar resto.");
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
                txtResultado.setText(titulo + "\n\n" + strFatores);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular as potências.\nTente novamente.");
            }
        }
    }

    private void porExtenso() {
        Numeral numero = pegaNumero();
        String baseNumero = "";
        String titulo = "";
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
            txtResultado.setText(titulo);

        } catch (Exception e) {
            //throw new RuntimeException(e);
            JOptionPane.showMessageDialog(frame, "ERRO! Impossível transcrever o número por extenso.\nTente novamente.");
        }
    }

    private void primosAte() {
        Numeral numero = pegaNumero();
        if(numero.intValue()==0) {
            txtResultado.setText("Não existem primos menores que zero; o menor número primo é o 2.");
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
                txtResultado.setText(titulo + "\n\n" + strPrimos);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular os fatores primos.\nTente novamente.");
            }
        }
    }

    private void estrutura() {
        Numeral numero = pegaNumero();
        String baseNumero = "";
        String titulo = "";
        txtResultado.setText(String.valueOf(numero));
    }
*/

}
