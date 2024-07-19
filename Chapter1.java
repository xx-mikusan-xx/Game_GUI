import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Chapter1 extends JPanel {
    private Player player;
    private Weapon weapon;
    private Companion companion;

    private JButton fightButton;
    private JButton escapeButton;
    private JButton leftButton;
    private JButton straightButton;
    private JButton rightButton;

    public Chapter1(Player player, Weapon weapon, Companion companion) {
        this.player = player;
        this.weapon = weapon;
        this.companion = companion;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Combat buttons
        fightButton = new JButton("たたかう");
        escapeButton = new JButton("逃げる");
        fightButton.addActionListener(e -> battle1(true));
        escapeButton.addActionListener(e -> battle1(false));

        JPanel actionPanel = new JPanel();
        actionPanel.add(fightButton);
        actionPanel.add(escapeButton);

        add(actionPanel, BorderLayout.SOUTH);

        battle1(true);
    }

    private void battle1(boolean fight) {
        if (fight) {
            JOptionPane.showMessageDialog(this, "経験値: 100ポイント\nアイテム: 闇のクリスタル, ヒーリングポーション x2");
            player.setExperience(player.getExperience() + 100);
            player.setHp(player.getHp() - 20);
        } else {
            Battle battle1 = new Battle();
            if (battle1.escape()) {
                JOptionPane.showMessageDialog(this, "逃げるのに成功しました!");
            } else {
                JOptionPane.showMessageDialog(this, "逃げるのに失敗しました!");
                battle1.fight(player, 20);
                JOptionPane.showMessageDialog(this, "経験値: 100ポイント\nアイテム: 闇のクリスタル, ヒーリングポーション x2");
                player.setExperience(player.getExperience() + 100);
                player.setHp(player.getHp() - 20);
            }
        }
        selectPath1();
    }

    private void selectPath1() {
        leftButton = new JButton("左");
        straightButton = new JButton("直進");
        rightButton = new JButton("右");

        leftButton.addActionListener(e -> handleLeftPath());
        straightButton.addActionListener(e -> handleStraightPath());
        rightButton.addActionListener(e -> handleRightPath());

        JPanel pathPanel = new JPanel();
        pathPanel.add(leftButton);
        pathPanel.add(straightButton);
        pathPanel.add(rightButton);

        add(pathPanel, BorderLayout.NORTH);
    }

    private void handleLeftPath() {
        JOptionPane.showMessageDialog(this, "時間がかかって敵に遭遇しました。");
        battle1(true);
    }

    private void handleStraightPath() {
        Battle battle3 = new Battle();
        battle3.fight(player, 50);
        player.setExperience(player.getExperience() + 500);
        JOptionPane.showMessageDialog(this, "貴重な武器「銀の剣」を手に入れました!");
    }

    private void handleRightPath() {
        Random rand = new Random();
        if (companion.getName().equals("カルロス") && rand.nextInt(100) < 70) {
            treasureBox();
        } else {
            handleTrap();
        }
    }

    private void handleTrap() {
        Object[] options = {"罠を解除", "リアナ王女の魔法", "罠を無視"};
        int choice = JOptionPane.showOptionDialog(this, "罠に遭遇しました。どうしますか？", "選択", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        Random rand = new Random();
        switch (choice) {
            case JOptionPane.YES_OPTION:
                if (rand.nextInt(100) < 50) {
                    treasureBox();
                } else {
                    player.setHp(player.getHp() - 30);
                    JOptionPane.showMessageDialog(this, "罠が作動しました!");
                }
                break;
            case JOptionPane.NO_OPTION:
                if (rand.nextInt(100) < 50) {
                    treasureBox();
                } else {
                    player.setHp(player.getHp() - 10);
                    JOptionPane.showMessageDialog(this, "魔法が誤作動しましたが、宝箱を開けました!");
                }
                break;
            case JOptionPane.CANCEL_OPTION:
                player.setHp(player.getHp() - 30);
                JOptionPane.showMessageDialog(this, "罠が作動しましたが、宝箱を開けました!");
                treasureBox();
                break;
        }
    }

    private void treasureBox() {
        Random rand = new Random();
        int item = rand.nextInt(3);

        switch (item) {
            case 0:
                player.setMp(player.getMp() + 10);
                player.setAttackPower((int) (player.getAttackPower() * 1.05));
                JOptionPane.showMessageDialog(this, "魔法の指輪を手に入れました!");
                break;
            case 1:
                player.setHp(100);
                player.setMp(100);
                JOptionPane.showMessageDialog(this, "エリクサーを手に入れました!");
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "銀の鍵を手に入れました!");
                break;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chapter 1");
        Player player = new Player("勇者", 100, 50, 20);
        Weapon weapon = new Weapon("剣", 10);
        Companion companion = new Companion("カルロス");

        Chapter1 chapter1 = new Chapter1(player, weapon, companion);
        frame.add(chapter1);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
