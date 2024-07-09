import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        initUI();
    }

    private void initUI() {
        setTitle("RPG Game");

        add(new TitleScreen(this));

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void switchToCharacterSelection() {
        getContentPane().removeAll();
        add(new CharacterSelectionScreen(this));
        revalidate();
        repaint();
    }

    public void switchToGamePanel() {
        getContentPane().removeAll();
        add(new GamePanel());
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
