import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterSelectionScreen extends JPanel {

    private JFrame frame;
    private JTextField nameField;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;

    public CharacterSelectionScreen(JFrame frame) {
        this.frame = frame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Character Selection", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        formPanel.add(nameLabel);

        nameField = new JTextField();
        formPanel.add(nameField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        formPanel.add(genderLabel);

        JPanel genderPanel = new JPanel();
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        formPanel.add(genderPanel);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.PLAIN, 24));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startGame() {
        String name = nameField.getText();
        String gender = maleButton.isSelected() ? "Male" : "Female";

        if (name.isEmpty() || (!maleButton.isSelected() && !femaleButton.isSelected())) {
            JOptionPane.showMessageDialog(this, "Please enter a name and select a gender.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(name, gender);
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
    }
}
