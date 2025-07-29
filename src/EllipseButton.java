//file Ellipse.java

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class EllipseButton extends JPanel implements MouseListener {

    private String title = null;
    private Vector listeners = null;
    private boolean hit = false;

    public EllipseButton(String title) {
        super();
        this.title = title;
        listeners = new Vector();
        addMouseListener(this);
    }

    public static void main(String[] args) {//TEST
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        EllipseButton button = new EllipseButton("Special button");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("I am clicked!");
            }
        });
        Container cont = jFrame.getContentPane();
        cont.setLayout(new FlowLayout());
        cont.add(new JLabel("TEST ME:"));
        cont.add(button);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public Dimension getPreferredSize() {
        return new Dimension(150, 75);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g);
        if (hit == true) {
            g2D.setColor(Color.green);
        } else {
            g2D.setColor(Color.yellow);
        }
        ;
        g2D.fillOval(0, 0, getWidth() - 2, getHeight() - 2);
        g2D.setColor(Color.black);
        g2D.drawOval(0, 0, getWidth() - 2, getHeight() - 2);
        g2D.drawString(title, 10, getHeight() / 2);
    }

    public void mousePressed(MouseEvent e) {
        hit = true;
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        hit = false;
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        fireEvent(new ActionEvent(this, 0, title));
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void addActionListener(ActionListener listener) {
        listeners.addElement(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.removeElement(listener);
    }

    private void fireEvent(ActionEvent event) {
        for (int i = 0; i < listeners.size(); i++) {
            ActionListener listener = (ActionListener) listeners.elementAt(i);
            listener.actionPerformed(event);
        }
        ;
    }

}//end class
