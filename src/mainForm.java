import org.mribeiro.constantes.Constantes;
import org.mribeiro.exception.IndiceForaDaFaixaException;
import org.mribeiro.fatores.FatoresNumericos;
import org.mribeiro.sistemaNumerico.Numeral;

import javax.swing.*;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JSpinner.DefaultEditor;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class mainForm {
    private static JFrame frame = new JFrame("Análise Numérica v1.0");
    private JPanel pnlContainer;
    private JPanel pnlMenu;
    private JButton btnEncerrar;
    private JButton btnCalcular;
    private JTextField txtNumero;
    private JTextArea txtResultado;
    private JLabel lblNumero;
    private JPanel pnlResultado;
    private JLabel lblPrimoComposto;

    private final SpinnerNumberModel numberModel = new SpinnerNumberModel(10, 0, Constantes.ALGARISMOS_POSSIVEIS.length()-1, 1); // initial, min, max, step
    private JSpinner spnBase;// = new JSpinner(numberModel);;

    private JRadioButton btnFatores;
    private JRadioButton btnPrimos;
    private JRadioButton btnFatoracao;
    private JRadioButton btnPotencias;
    private JRadioButton btnExtenso;
    private JRadioButton btnPrimosAte;
    private JRadioButton btnEstrutura;
    private ButtonGroup btnGroup = new ButtonGroup();

    private JLabel lblDebug;
    private JLabel lblMenu;

    public mainForm() {
        txtResultado.setLineWrap(true);
        txtResultado.setBorder(null);
        txtNumero.setBorder(null);
        spnBase.setModel(numberModel);
        // Make the JSpinner's text field non-editable
        ((JSpinner.DefaultEditor) spnBase.getEditor()).getTextField().setEditable(false);

        // menu
        btnFatores.setActionCommand("fatores");
        btnPrimos.setActionCommand("primos");
        btnFatoracao.setActionCommand("fatoracao");
        btnPotencias.setActionCommand("potencias");
        btnExtenso.setActionCommand("extenso");
        btnPrimosAte.setActionCommand("primosAte");
        btnEstrutura.setActionCommand("estrutura");
        btnGroup.add(btnFatores);
        btnGroup.add(btnPrimos);
        btnGroup.add(btnFatoracao);
        btnGroup.add(btnPotencias);
        btnGroup.add(btnExtenso);
        btnGroup.add(btnPrimosAte);
        btnGroup.add(btnEstrutura);
        btnFatores.setSelected(true);

        btnEncerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        spnBase.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // This method is called whenever the JSpinner's value changes
                Object currentValue = spnBase.getValue();
                //System.out.println("Spinner value changed to: " + currentValue);
                limpaTudo();
            }
        });

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonModel selectedModel = btnGroup.getSelection();
                if (selectedModel != null) {
                    try {
                        primoOuComposto();
                    } catch (IndiceForaDaFaixaException ex) {
                        throw new RuntimeException(ex);
                    }
                    // A radio button is selected
                    String selectedActionCommand = selectedModel.getActionCommand();
                    //JOptionPane.showMessageDialog(frame, "Selected: " + selectedActionCommand);
                    switch(selectedActionCommand) {
                        case "fatores": // calcula todos os fatores do número
                            fatores();
                            break;
                        case "primos": // calcula todos os fatores do número
                            primos();
                            break;
                        case "fatoracao": // calcula todos os fatores do número
                            fatoracao();
                            break;
                        case "potencias": // calcula todos os fatores do número
                            potencias();
                            break;
                        case "extenso": // calcula todos os fatores do número
                            porExtenso();
                            break;
                        case "primosAte": // calcula todos os fatores do número
                            primosAte();
                            break;
                        case "estrutura": // calcula todos os fatores do número
                            estrutura();
                            break;
                    }

                } else {
                    // No radio button is currently selected
                    JOptionPane.showMessageDialog(frame, "No option selected.");

                }
            }
        });
        txtNumero.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    primoOuComposto();
                } catch (IndiceForaDaFaixaException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }



    public static void main(String[] args) {

        //JFrame frame = new JFrame("Análise Numérica v1.0");
        frame.setContentPane(new mainForm().pnlContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);// Center the frame on the screen
        frame.setVisible(true);

    }

    private void limpaTudo() {
        txtResultado.setText("");
        txtNumero.setText("");
        btnFatores.setSelected(true);
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

    private void fatores() {
        Numeral numero = pegaNumero();
        FatoresNumericos fatores = new FatoresNumericos();
        String baseNumero = "";
        String titulo = "";
        try {
            String strFatores = String.valueOf(fatores.getFatores(numero.longValue()));
            if(!numero.getBaseNumerica().isBaseDecimal()) {
                baseNumero = "Número informado: "+numero.toLiteral()+" (base "+numero.getBaseNumerica().getValorBase()+")";
                titulo = baseNumero + "\nFatores do número "+numero.getParteInteira().toLiteral()+" (DECIMAL "+numero.getParteInteira().getValorAbsoluto().toPlainString()+")";
                titulo += "\n--> "+fatores.getFatores(numero).size()+" fatores";
            } else {
                titulo = "Fatores do número "+numero.getParteInteira().getValorAbsoluto().toPlainString();
                titulo += "\n--> "+fatores.getFatores(numero).size()+" fatores";
            }
            txtResultado.setText(titulo+"\n\n"+strFatores);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular os fatores.\nTente novamente.");
        }
    }

    private void primos() {
        Numeral numero = pegaNumero();
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

    private void fatoracao() {
        Numeral numero = pegaNumero();
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

    private void potencias() {
        Numeral numero = pegaNumero();
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
        FatoresNumericos primosAte = new FatoresNumericos();
        String baseNumero = "";
        String titulo = "";
        try {

            String strPrimos = primosAte.getFatoresPrimosAte(numero.longValue()).toString();
            if(!numero.getBaseNumerica().isBaseDecimal()) {
                baseNumero = "Número informado: "+numero.toLiteral()+" (base "+numero.getBaseNumerica().getValorBase()+")";
                titulo = baseNumero + "\nPrimos até o número "+numero.getParteInteira().toLiteral()+" (DECIMAL "+numero.getParteInteira().getValorAbsoluto().toPlainString()+")";
                titulo += "\n--> "+primosAte.getFatoresPrimosAte(numero).size()+" números primos entre 2 e "+numero.getValorDecimal().toPlainString();
            } else {
                titulo = "Primos até o número "+numero.getParteInteira().getValorAbsoluto().toPlainString();
                titulo += "\n--> "+primosAte.getFatoresPrimosAte(numero).size()+" números primos entre 2 e "+numero.getParteInteira().getValorAbsoluto().toPlainString();
            }

            txtResultado.setText(titulo+"\n\n" + strPrimos);

        } catch (Exception e) {
            //throw new RuntimeException(e);
            JOptionPane.showMessageDialog(frame, "ERRO! Impossível calcular os fatores primos.\nTente novamente.");
        }
    }

    private void estrutura() {
        Numeral numero = pegaNumero();
        String baseNumero = "";
        String titulo = "";
        txtResultado.setText(String.valueOf(numero));
    }

    private void primoOuComposto() throws IndiceForaDaFaixaException {
        Numeral numero = pegaNumero();
        FatoresNumericos fatores = new FatoresNumericos();
        lblPrimoComposto.setText((fatores.e_Primo(numero)) ? "Número PRIMO" : "Número COMPOSTO");
    }
}
