import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeaponSelectionScreen extends JPanel {
    private JFrame frame;

    public WeaponSelectionScreen(JFrame frame) {
        this.frame = frame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("武器を選んでください", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        JPanel weaponPanel = new JPanel();
        weaponPanel.setLayout(new GridLayout(3, 1));

        weaponPanel.add(createWeaponPanel("剣", "img/sword.png", 15));
        weaponPanel.add(createWeaponPanel("弓", "img/bow.png", 20));
        weaponPanel.add(createWeaponPanel("魔法の杖", "img/stick.png", 10));

        add(weaponPanel, BorderLayout.CENTER);
    }

    private JPanel createWeaponPanel(String weaponName, String imagePath, int weaponPower) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel(weaponName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(nameLabel, BorderLayout.WEST);

        ImageIcon weaponIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(weaponIcon);
        panel.add(imageLabel, BorderLayout.CENTER);

        JButton selectButton = new JButton("選択");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectWeapon(weaponName, weaponPower);
            }
        });
        panel.add(selectButton, BorderLayout.EAST);

        return panel;
    }

    private void selectWeapon(String weaponName, int weaponPower) {
        Player player = GameManager.getInstance().getPlayer();
        player.setWeapon(weaponName);
        Weapon weapon = new Weapon(weaponName, weaponPower);
        GameManager.getInstance().setWeapon(weapon);

        if (frame instanceof Main) {
            ((Main) frame).switchToChapter1();
        }
    }
}
