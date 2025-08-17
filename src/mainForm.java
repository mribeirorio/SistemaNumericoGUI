import org.mribeiro.constantes.Constantes;
import org.mribeiro.exception.CaracterInvalidoException;
import org.mribeiro.exception.IndiceForaDaFaixaException;
import org.mribeiro.exception.ValorInvalidoParaBaseException;
import org.mribeiro.fatores.FatoresNumericos;
import org.mribeiro.sistemaNumerico.BaseNumerica;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class mainForm {

    private long primosCalculados = 0;
    private long tempoGasto = 0;

    private static JFrame frame = new JFrame("Análise Numérica v1.0");
    private JPanel pnlContainer;
    private JPanel pnlMenu;
    private JButton btnEncerrar;
    private JButton btnCalcular;
    private JButton btnLimparTudo;

    private JTextField txtNumero;
    private JTextPane txtResultado;
    private JLabel lblNumero;
    private JPanel pnlResultado;
    private JLabel lblPrimoComposto;

    private final SpinnerNumberModel numberModelBase = new SpinnerNumberModel(10, 2, Constantes.ALGARISMOS_POSSIVEIS.length(), 1); // initial, min, max, step
    private final SpinnerNumberModel numberModelDigitos = new SpinnerNumberModel(3, 1, Constantes.ALGARISMOS_POSSIVEIS.length(), 1); // initial, min, max, step

    private JSpinner spnBase;       // = new JSpinner(numberModel);;
    private JSpinner spnDigitos;

    private JRadioButton btnFatores;
    private JRadioButton btnPrimos;
    private JRadioButton btnFatoracao;
    private JRadioButton btnDecompor;
    private JRadioButton btnPotencias;
    private JRadioButton btnExtenso;
    private JRadioButton btnPotenciaDasOrdens;
    private JRadioButton btnAnalise;
    private JCheckBox btnPrimosAte;

    private ButtonGroup btnGroup = new ButtonGroup();

    private JLabel lblDebug;
    private JLabel lblMenu;
    private JLabel lblAlgarismosValidos;
    private JScrollPane pnlScroll;
    private JLabel lblDigitos;
    private JComboBox<String> comboSepDec;// = new JComboBox<>();
    private JComboBox<String> comboSepClasse;// = new JComboBox<>();
    private JProgressBar progresso = new JProgressBar();
    private JRadioButton btnPrimosAteAtkin;
    private JCheckBox btnPrimosAteEratostenes;


    public mainForm() {
        progresso.setMinimum(0);
        progresso.setMaximum(100);
        progresso.setValue(77);

        comboSepDec.addItem(",");
        //
        comboSepClasse.addItem(".");
        comboSepClasse.addItem(":");
        comboSepClasse.addItem("-");
        comboSepClasse.addItem(";");
        //
        //txtResultado.setLineWrap(true);
        //txtResultado.setContentType("text/html");
        //txtResultado.setBorder(null);
        Insets padding = new Insets(10, 10, 10, 10);
        // Apply the padding to the JTextArea
        txtResultado.setMargin(padding);
        pnlScroll.setBorder(null);

        spnBase.setModel(numberModelBase);
        spnDigitos.setModel(numberModelDigitos);
        // Make the JSpinner's text field non-editable
        ((JSpinner.DefaultEditor) spnBase.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) spnDigitos.getEditor()).getTextField().setEditable(false);

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
        btnPrimosAteAtkin.setActionCommand("primosAteAtkin");
        btnPrimosAteEratostenes.setActionCommand("primosAteEratostenes");
        btnGroup.add(btnFatores);
        btnGroup.add(btnPrimos);
        btnGroup.add(btnFatoracao);
        btnGroup.add(btnDecompor);
        btnGroup.add(btnPotencias);
        btnGroup.add(btnPotenciaDasOrdens);
        btnGroup.add(btnExtenso);
        btnGroup.add(btnAnalise);
        btnGroup.add(btnPrimosAte);
        btnGroup.add(btnPrimosAteAtkin);
        btnGroup.add(btnPrimosAteEratostenes);
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
                if(currentValue == 10) {
                    //spnDigitos.setValue(3);
                    resetaBase10();
                } else {
                    resetaOutrasBases();
                }
            }
        });

        spnDigitos.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = (int) spnDigitos.getValue();
                //limpaEntrada();
                if((int)spnBase.getValue()==10) {
                    resetaBase10();
                } //else {
                    //resetaOutrasBases();
                //}
                //lblPrimoComposto.setText(getNomeDaBase(currentValue));
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
                    } catch (CaracterInvalidoException ex) {
                        throw new RuntimeException(ex);
                    } catch (ValorInvalidoParaBaseException ex) {
                        throw new RuntimeException(ex);
                    }
                    // A radio button is selected

                    String selectedActionCommand = selectedModel.getActionCommand();
                    Numeral num = null;
                    try {
                        num = getNumero();
                    } catch (CaracterInvalidoException ex) {
                        throw new RuntimeException(ex);
                    } catch (ValorInvalidoParaBaseException ex) {
                        throw new RuntimeException(ex);
                    }
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
                                strResultado = calculo.primosAte("simples");
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "primosAteAtkin": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.primosAte("atkin");
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "primosAteEratostenes": // calcula todos os fatores do número
                            try {
                                strResultado = calculo.primosAte("eratostenes");
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
                    //

                } else {
                    // No radio button is currently selected
                    JOptionPane.showMessageDialog(frame, "No option selected.");
                }
                // Rola até o início
                pnlScroll.getViewport().setViewPosition(new Point(0, 0));
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

    public static void main(String[] args)  {
        //JFrame frame = new JFrame("Análise Numérica v1.0");

        frame.setContentPane(new mainForm().pnlContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(850, 550);

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
        resetaBase10();
        menuHabilitado(false);
        ajustaAlgarismos();
        frame.setSize(850, 550);
        frame.repaint();
        frame.pack();
        frame.setLocationRelativeTo(null);// Center the frame on the screen
    }

    private void resetaBase10() {
        spnBase.setValue(10);
        spnDigitos.setValue(3);
        comboSepClasse.setSelectedIndex(0);
        comboSepDec.setSelectedIndex(0);
        spnDigitos.setEnabled(false);
        comboSepDec.setEnabled(false);
        comboSepClasse.setEnabled(false);
    }

    private void resetaOutrasBases() {
        //comboSepClasse.setSelectedIndex(0);
        //comboSepDec.setSelectedIndex(0);
        spnDigitos.setEnabled(true);
        comboSepClasse.setEnabled(true);
        comboSepDec.setEnabled(true);
        spnDigitos.setValue(1);
    }

    private Numeral getNumero() throws CaracterInvalidoException, ValorInvalidoParaBaseException {
        String str = txtNumero.getText();
        int base = (int)spnBase.getValue();
        int digitosPorClasse = (int)spnDigitos.getValue();
        char separadorDeClasses = comboSepClasse.getSelectedItem().toString().charAt(0);

        BaseNumerica baseNumerica = new BaseNumerica(base, digitosPorClasse, separadorDeClasses);
        Numeral num = null;
        try {
            num = new Numeral(baseNumerica, str);
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

    private void primoOuComposto() throws IndiceForaDaFaixaException, CaracterInvalidoException, ValorInvalidoParaBaseException {
        Numeral numero = getNumero();
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
    //
    //
    //
    //

    private ArrayList<Long> getFatoresPrimosAteAtkin(long limit) {
        long[] arr = new long[Math.toIntExact(limit + 1L)];
        Arrays.fill(arr, 0L);
        if (limit > 2L) {
            arr[2] = 1L;
        }

        if (limit > 3L) {
            arr[3] = 1L;
        }

        for(long x = 1L; x * x <= limit; ++x) {
            for(long y = 1L; y * y <= limit; ++y) {
                long n = 4L * x * x + y * y;
                if (n <= limit && (n % 12L == 1L || n % 12L == 5L)) {
                    arr[Math.toIntExact(n)] = (arr[Math.toIntExact(n)] + 1L) % 2L;
                }

                n = 3L * x * x + y * y;
                if (n <= limit && n % 12L == 7L) {
                    arr[Math.toIntExact(n)] = (arr[Math.toIntExact(n)] + 1L) % 2L;
                }

                n = 3L * x * x - y * y;
                if (x > y && n <= limit && n % 12L == 11L) {
                    arr[Math.toIntExact(n)] = (arr[Math.toIntExact(n)] + 1L) % 2L;
                }
            }
        }

        for(long i = 5L; i * i <= limit; ++i) {
            if (arr[Math.toIntExact(i)] != 0L) {
                for(long j = i * i; j <= limit; j += i * i) {
                    arr[Math.toIntExact(j)] = 0L;
                }
            }
        }

        ArrayList<Long> primes = new ArrayList();

        for(long i = 2L; i <= limit; ++i) {
            if (arr[Math.toIntExact(i)] == 1L) {
                primes.add(i);
            }
        }

        return primes;
    }




}
