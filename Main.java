import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("光と闇の冒険者達");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            
            TitleScreen titleScreen = new TitleScreen(frame);
            frame.add(titleScreen);
            
            frame.setVisible(true);
        });
    }
}
