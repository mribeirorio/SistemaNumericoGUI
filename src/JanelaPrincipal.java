import javax.swing.*;

public class JanelaPrincipal {

    public static void main(String[] args) {

        Principal minhaJanela = new Principal();

        JFrame frame = new JFrame("Minha janela no JFrame");

        frame.setSize(700,300);

        frame.pack();

        frame.setVisible(true);
    }

}
