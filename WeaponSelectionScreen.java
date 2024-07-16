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

        JButton swordButton = new JButton("剣");
        swordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectWeapon("剣", 15);
            }
        });

        JButton axeButton = new JButton("斧");
        axeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectWeapon("斧", 20);
            }
        });

        JButton bowButton = new JButton("弓");
        bowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectWeapon("弓", 10);
            }
        });

        weaponPanel.add(swordButton);
        weaponPanel.add(axeButton);
        weaponPanel.add(bowButton);

        add(weaponPanel, BorderLayout.CENTER);
    }

    private void selectWeapon(String weaponName, int weaponPower) {
        Player player = GameManager.getInstance().getPlayer();
        player.setWeapon(weaponName);
        Weapon weapon = new Weapon(weaponName, weaponPower);
        GameManager.getInstance().setWeapon(weapon);

        // 次のチャプターに遷移
        if (frame instanceof Main) {
            ((Main) frame).switchToChapter1();
        }
    }
}
