import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        initUI();
    }

    private void initUI() {
        setTitle("RPG Game");

        // Add the initial screen (TitleScreen, GamePanel, etc.)
        add(new TitleScreen(this));

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void switchToGamePanel(String name, String gender) {
        getContentPane().removeAll();
        add(new GamePanel(name, gender));
        revalidate();
        repaint();
    }

    public void switchToWeaponSelection() {
        getContentPane().removeAll();
        add(new WeaponSelectionPanel());
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}
