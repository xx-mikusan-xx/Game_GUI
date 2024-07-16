import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void initUI() {
        setLayout(new BorderLayout());

        // たたかう・逃げるボタン
        fightButton = new JButton("たたかう");
        escapeButton = new JButton("逃げる");
        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle1(true);
            }
        });
        escapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle1(false);
            }
        });

        JPanel actionPanel = new JPanel();
        actionPanel.add(fightButton);
        actionPanel.add(escapeButton);

        add(actionPanel, BorderLayout.SOUTH);

        battle1(true);
    }

    public void battle1(boolean fight) {
        if (fight) {
            Battle battle1 = new Battle();
            battle1.fight(player, 20);
        } else {
            Battle battle1 = new Battle();
            if (battle1.escape()) {
                JOptionPane.showMessageDialog(this, "逃げるのに成功しました!");
            } else {
                JOptionPane.showMessageDialog(this, "逃げるのに失敗しました!");
                battle1.fight(player, 20);
            }
        }
        selectPath1();
    }

    public void selectPath1() {
        // 道選択ボタン
        leftButton = new JButton("左");
        straightButton = new JButton("直進");
        rightButton = new JButton("右");

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLeftPath();
            }
        });
        straightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStraightPath();
            }
        });
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRightPath();
            }
        });

        JPanel pathPanel = new JPanel();
        pathPanel.add(leftButton);
        pathPanel.add(straightButton);
        pathPanel.add(rightButton);

        add(pathPanel, BorderLayout.NORTH);
    }

    public void handleLeftPath() {
        Battle battle2 = new Battle();
        battle2.fight(player, 10);  // 弱い敵
    }

    public void handleStraightPath() {
        Battle battle3 = new Battle();
        battle3.fight(player, 50);  // 強い敵
        player.setExperience(player.getExperience() + 500);
        weapon.setPower(weapon.getPower() + 10);  // 強い武器を手に入れる
    }

    public void handleRightPath() {
        Random rand = new Random();
        if (companion.getName().equals("カルロス")) {
            if (rand.nextInt(100) < 70) {
                treasureBox();
            } else {
                handleTrap();
            }
        } else {
            handleTrap();
        }
    }

    public void handleTrap() {
        Object[] options = {"罠を解除", "リアナ王女の魔法", "罠を無視"};
        int choice = JOptionPane.showOptionDialog(this, "罠に遭遇しました。どうしますか？", "選択", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        Random rand = new Random();
        switch (choice) {
            case JOptionPane.YES_OPTION:
                if (rand.nextInt(100) < 50) {
                    treasureBox();
                } else {
                    player.setHp(player.getHp() - 30);
                }
                break;
            case JOptionPane.NO_OPTION:
                if (rand.nextInt(100) < 50) {
                    treasureBox();
                } else {
                    player.setHp(player.getHp() - 10);
                    treasureBox();
                }
                break;
            case JOptionPane.CANCEL_OPTION:
                player.setHp(player.getHp() - 3);
                treasureBox();
                break;
        }
    }

    public void treasureBox() {
        Random rand = new Random();
        int item = rand.nextInt(3);

        switch (item) {
            case 0:
                player.setMp(player.getMp() + 10);
                player.setAttackPower((int) (player.getAttackPower() * 1.05));
                JOptionPane.showMessageDialog(this, "魔法の指輪を手に入れました!");
                break;
            case 1:
                player.setHp(100); // 全回復と仮定
                player.setMp(100); // 全回復と仮定
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