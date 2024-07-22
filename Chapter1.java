import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Chapter1 extends JPanel {
    private Player player;
    private Weapon weapon;
    private Companion companion;

    public Chapter1(Player player, Weapon weapon) {
        this.player = player;
        this.weapon = weapon;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Display introduction message
        JOptionPane.showMessageDialog(this, "これからゲームが始まります。まずは仲間を選択しましょう。");

        // Companion selection
        selectCompanion();

        // Start first battle
        firstBattle();
    }

    private void selectCompanion() {
        String[] options = {"戦士 ライアン", "魔法使い エリス", "盗賊 カルロス"};
        ImageIcon[] icons = {
            new ImageIcon("path/ryan.png"),
            new ImageIcon("path/eris.png"),
            new ImageIcon("path/carlos.png")
        };
        JPanel panel = new JPanel(new GridLayout(1, 3));
        
        for (int i = 0; i < options.length; i++) {
            panel.add(new JLabel(options[i], icons[i], SwingConstants.CENTER));
        }

        int choice = JOptionPane.showOptionDialog(this, panel, "仲間を選択してください",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                companion = new Companion("ライアン", "攻撃力が2, スキル: 攻撃引き受け");
                break;
            case 1:
                companion = new Companion("エリス", "スキル: 敵全体に氷属性ダメージ");
                break;
            case 2:
                companion = new Companion("カルロス", "スキル: 罠を見破る");
                break;
            default:
                companion = new Companion("ライアン", "攻撃力が2, スキル: 攻撃引き受け");
                break;
        }
    }

    private void firstBattle() {
        ImageIcon enemyIcon = new ImageIcon("path/enemy1.png");
        JLabel enemyLabel = new JLabel(enemyIcon);

        JOptionPane.showMessageDialog(this, enemyLabel, "敵が現れた！", JOptionPane.PLAIN_MESSAGE);

        int result = JOptionPane.showConfirmDialog(this, "戦う？", "戦闘開始", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            fightEnemy();
        } else {
            endGame();
        }
    }

    private void fightEnemy() {
        int enemyHp = 30; 
        Random rand = new Random();

        while (enemyHp > 0 && player.getHp() > 0) {
            enemyHp -= weapon.getPower();
            player.setHp(player.getHp() - rand.nextInt(5)); 

            if (companion.getName().equals("エリス")) {
                enemyHp -= 10; 
            }

            // Update HP and show status
            JOptionPane.showMessageDialog(this, "敵HP: " + enemyHp + "\nプレイヤーHP: " + player.getHp());

            if (enemyHp <= 0) {
                JOptionPane.showMessageDialog(this, "勝利しました！");
                return;
            }

            if (player.getHp() <= 0) {
                JOptionPane.showMessageDialog(this, "敗北しました！");
                endGame();
                return;
            }
        }
    }

    private void endGame() {
        int choice = JOptionPane.showConfirmDialog(this, "ゲームをやめますか？", "終了",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            resetGame();
        }
    }

    private void resetGame() {
        player.setHp(100);
        initUI();
    }
}