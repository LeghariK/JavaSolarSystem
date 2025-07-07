import javax.swing.*;

public class SolarSystem {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Solar System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new SolarSystemPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
