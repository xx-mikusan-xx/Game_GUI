import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeaponSelectionPanel extends JPanel {

    private JFrame frame;
    private JRadioButton swordButton;
    private JRadioButton bowButton;
    private JRadioButton staffButton;

    public WeaponSelectionPanel(JFrame frame) {
        this.frame = frame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Weapon Selection", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        JPanel weaponPanel = new JPanel();
        weaponPanel.setLayout(new GridLayout(3, 1));

        swordButton = new JRadioButton("Sword");
        bowButton = new JRadioButton("Bow");
        staffButton = new JRadioButton("Staff");
        ButtonGroup weaponGroup = new ButtonGroup();
        weaponGroup.add(swordButton);
        weaponGroup.add(bowButton);
        weaponGroup.add(staffButton);

        weaponPanel.add(swordButton);
        weaponPanel.add(bowButton);
        weaponPanel.add(staffButton);
        add(weaponPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Serif", Font.PLAIN, 24));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmWeaponSelection();
            }
        });
        buttonPanel.add(confirmButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void confirmWeaponSelection() {
        String selectedWeapon = null;
        if (swordButton.isSelected()) {
            selectedWeapon = "Sword";
        } else if (bowButton.isSelected()) {
            selectedWeapon = "Bow";
        } else if (staffButton.isSelected()) {
            selectedWeapon = "Staff";
        }

        if (selectedWeapon == null) {
            JOptionPane.showMessageDialog(this, "Please select a weapon.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Player player = GameManager.getInstance().getPlayer();
        player.setWeapon(selectedWeapon);

        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
    }
}
