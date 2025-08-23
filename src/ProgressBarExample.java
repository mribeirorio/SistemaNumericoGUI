import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProgressBarExample extends JFrame {

    private final JProgressBar progressBar;
    private final JButton startButton;
    private final JLabel lblNumPrimos;
    private final JLabel lblTempoGasto;
    private final JTextField txtNumero;

    private int numPrimos = 0;
    private long timeStart = 0;
    private long timeEnd = 0;

    public ProgressBarExample() {
        setTitle("Progress Bar Demo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill both horizontal and vertical space
        gbc.weightx = 1.0; // Allow horizontal expansion
        gbc.weighty = 1.0; // Allow vertical expansion

        txtNumero = new JTextField();
        lblNumPrimos = new JLabel("Primos computados: " + numPrimos, SwingConstants.CENTER);
        lblTempoGasto = new JLabel("", SwingConstants.CENTER);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Show percentage text

        startButton = new JButton("Start Calculation");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLongCalculation();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1)); // 4 rows, 1 column

        panel.add(txtNumero, gbc);
        panel.add(progressBar);
        panel.add(startButton);
        panel.add(lblNumPrimos, gbc);
        panel.add(lblTempoGasto, gbc);
        add(panel);
    }

    private void startLongCalculation() {
        startButton.setEnabled(false); // Disable button during calculation
        progressBar.setValue(0); // Reset progress bar

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                //ArrayList<BigInteger> getFatoresPrimosAteEratostenes;  {
                ArrayList<Long> getFatoresPrimosAteEratostenes;  {
                    //BigInteger min = BigInteger.TWO;
                    //BigInteger limite = new BigInteger(txtNumero.getText());
                    long min = 2;
                    long limite = Long.parseLong(txtNumero.getText());
                    //ArrayList<BigInteger> f = new ArrayList<>();
                    ArrayList<Long> f = new ArrayList<>();
                    for (long i = min; i <= limite; i++) {
                    timeStart = System.nanoTime();
                    //for (BigInteger i = min; limite.compareTo(i) == 1; i = i.add(BigInteger.ONE)) {
                        boolean e_primo = true;
                        for (long j = 2; j <= (i / 2); j++) {
                        //for (BigInteger j = BigInteger.TWO; j.compareTo(i.divide(BigInteger.TWO)) <= 0; j = j.add(BigInteger.ONE)) {
                            if (i % j == 0) {
                            //if (i.remainder(j).compareTo(BigInteger.ZERO) == 0) {
                                e_primo = false;
                                break;
                            }
                        }
                        if (e_primo) {
                            f.add(i);
                            numPrimos++;
                        }
                        publish((int) ((i * 100 / limite) + 1));
                    }
                    timeEnd = System.nanoTime();
                    //return f;
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                // Update progress bar on EDT
                for (Integer progress : chunks) {
                    progressBar.setValue(progress);
                    lblNumPrimos.setText("Primos computados: " + numPrimos);
                }
            }

            @Override
            protected void done() {
                lblTempoGasto.setText("Tempo gasto: "+(float)((timeEnd-timeStart)/1000000/1000)+" segundos");
                startButton.setEnabled(true); // Re-enable button
                //JOptionPane.showMessageDialog(ProgressBarExample.this, "Calculation Complete!");
            }
        };
        worker.execute(); // Start the SwingWorker
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProgressBarExample().setVisible(true);
            }
        });
    }
}