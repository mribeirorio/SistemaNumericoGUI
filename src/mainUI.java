import javax.swing.*;


public class mainUI {

    private static JFrame frame = new JFrame("Análise Numérica v0.3");
    private JPanel mainUIPanel;
    private JTabbedPane tabbedPane;

    public mainUI() {
//

        // Create content for Tab 1
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Content of Tab 1"));
        tabbedPane.addTab("Tab 1", panel1);

        // Create content for Tab 2
        JPanel panel2 = new JPanel();
        panel2.add(new JButton("Click Me!"));
        tabbedPane.addTab("Tab 2", panel2);

        // Create content for Tab 3 with an icon
        JPanel panel3 = new JPanel();
        panel3.add(new JCheckBox("Check this"));
        ImageIcon icon = new ImageIcon("path/to/your/icon.png"); // Replace with actual path
        tabbedPane.addTab("Tab 3", icon, panel3, "Tooltip for Tab 3"); // Title, Icon, Component, Tooltip

//


    }

    public static void main(String[] args)  {

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Sistema Numérico GUI");
        System.setProperty("com.apple.awt.application.name", "Sistema Numérico GUI");
// Optionally, set the system Look and Feel for a native macOS appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            /*JFrame frame = new JFrame("Main Application Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);

            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenuItem exitItem = new JMenuItem("Exit");
            exitItem.addActionListener(e -> System.exit(0));
            fileMenu.add(exitItem);
            menuBar.add(fileMenu);
            frame.setJMenuBar(menuBar); // This menu bar will appear in the screen menu bar*/
            JFrame frame = new JFrame();

            frame.setContentPane(new mainUI().mainUIPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();

            JMenu fileMenu = new JMenu("Arquivo");
            JMenuItem openItem = new JMenuItem("Abrir");
            JMenuItem exitItem = new JMenuItem("Sair");

            fileMenu.add(openItem);
            fileMenu.addSeparator();
            fileMenu.add(exitItem);

            menuBar.add(fileMenu);
            frame.setJMenuBar(menuBar);


            frame.setSize(850, 550);

            frame.setLocationRelativeTo(null);// Center the frame on the screen
            frame.pack();
            frame.setVisible(true);
        });




        //SwingUtilities.invokeLater(mainForm::new);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
