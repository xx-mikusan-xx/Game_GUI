import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WeaponSelectionPanel extends JPanel {

    private ImageIcon[] weaponIcons;
    private int selectedWeaponIndex = 0;

    public WeaponSelectionPanel() {
        loadWeaponImages();
        initPanel();
    }

    private void loadWeaponImages() {
        // Load weapon images from files or resources
        // This is an example using placeholder images, replace with actual image file paths
        weaponIcons = new ImageIcon[]{
            new ImageIcon("path/to/weapon1.png"),
            new ImageIcon("path/to/weapon2.png"),
            new ImageIcon("path/to/weapon3.png")
        };
    }

    private void initPanel() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    selectedWeaponIndex = (selectedWeaponIndex - 1 + weaponIcons.length) % weaponIcons.length;
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    selectedWeaponIndex = (selectedWeaponIndex + 1) % weaponIcons.length;
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Logic to confirm weapon selection and proceed
                    System.out.println("Weapon selected: " + selectedWeaponIndex);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWeapons(g);
    }

    private void drawWeapons(Graphics g) {
        // Set font and color for drawing text
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);

        // Draw the selected weapon in the center
        ImageIcon selectedWeapon = weaponIcons[selectedWeaponIndex];
        int x = (getWidth() - selectedWeapon.getIconWidth()) / 2;
        int y = (getHeight() - selectedWeapon.getIconHeight()) / 2;
        g.drawImage(selectedWeapon.getImage(), x, y, this);

        // Draw weapon navigation instructions
        String message = "左右キーで武器を選択、エンターキーで決定";
        int msgX = (getWidth() - g.getFontMetrics().stringWidth(message)) / 2;
        int msgY = getHeight() - 30;
        g.drawString(message, msgX, msgY);
    }
}
