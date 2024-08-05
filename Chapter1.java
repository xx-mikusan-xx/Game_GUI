import javax.swing.*;
import java.awt.*;
import java.util.Random;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        setBackgroundImage(imagePath);
    }

    public void setBackgroundImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class Chapter1 extends JPanel {
    private Player player;
    private Weapon weapon;
    private Companion companion;
    private BackgroundPanel backgroundPanel;
    private JTextArea logArea;
    private JPanel actionPanel;
    private JLabel playerStatusLabel;
    private JLabel companionStatusLabel;
    private JFrame topFrame;
    private JLabel enemyImageLabel;
    private Enemy currentEnemy;

    public Chapter1(Player player, Weapon weapon) {
        this.player = player;
        this.weapon = weapon;
        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
    
        // メイン画面（背景パネル）
        backgroundPanel = new BackgroundPanel("img/intro_background.png");
        add(backgroundPanel, BorderLayout.CENTER);
    
        // ログエリア
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(400, 100));
        
        // 行動選択パネル
        actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());
    
        // ステータスパネル
        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        playerStatusLabel = new JLabel();
        companionStatusLabel = new JLabel();
        statusPanel.add(playerStatusLabel);
        statusPanel.add(companionStatusLabel);
        add(statusPanel, BorderLayout.NORTH);
    
        // ログエリアと行動選択パネルをまとめたパネル
        JPanel logAndActionPanel = new JPanel();
        logAndActionPanel.setLayout(new BorderLayout());
        logAndActionPanel.add(actionPanel, BorderLayout.NORTH);
        logAndActionPanel.add(scrollPane, BorderLayout.CENTER);
        logAndActionPanel.setPreferredSize(new Dimension(400, 150));
        add(logAndActionPanel, BorderLayout.SOUTH);
    
        // 敵画像ラベル
        enemyImageLabel = new JLabel();
        enemyImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
        updateStatus();
    
        // メニューバー作成
        createMenuBar();
    
        // ゲーム開始
        appendLog("これからゲームが始まります。まずは仲間を選択しましょう。");
        selectCompanion();
        startFirstBattle();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu optionsMenu = new JMenu("オプション");
        JMenuItem statusItem = new JMenuItem("ステータス");
        JMenuItem itemsItem = new JMenuItem("アイテム");
        JMenuItem saveItem = new JMenuItem("セーブ");

        statusItem.addActionListener(e -> showStatus());
        itemsItem.addActionListener(e -> showItems());
        saveItem.addActionListener(e -> saveGame());

        optionsMenu.add(statusItem);
        optionsMenu.add(itemsItem);
        optionsMenu.add(saveItem);

        menuBar.add(optionsMenu);

        if (topFrame != null) {
            topFrame.setJMenuBar(menuBar);
        }
    }

    private void showStatus() {
        String status = "プレイヤー HP: " + player.getHp() + " / MP: " + player.getMp() +
                        "\n攻撃力: " + player.getAttackPower() +
                        "\n経験値: " + player.getExperience();
        if (companion != null) {
            status += "\n仲間: " + companion.getName() + "\nスキル: " + companion.getSkill();
        }
        appendLog("ステータス:\n" + status);
    }

    private void showItems() {
        String items = "1. ポーション\n2. エリクサー\n3. 銀の剣";
        appendLog("アイテム:\n" + items);
    }

    private void saveGame() {
        appendLog("ゲームがセーブされました。");
    }

    private void selectCompanion() {
        String[] options = {"戦士 ライアン", "魔法使い エリス", "盗賊 カルロス"};
        ImageIcon[] icons = {
            new ImageIcon("img/ryan.png"),
            new ImageIcon("img/eris.png"),
            new ImageIcon("img/carlos.png")
        };
        JPanel panel = new JPanel(new GridLayout(1, 3));

        for (int i = 0; i < options.length; i++) {
            panel.add(new JLabel(options[i], icons[i], SwingConstants.CENTER));
        }

        int choice = JOptionPane.showOptionDialog(this, panel, "仲間を選択してください",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                companion = new Companion("ライアン", "攻撃引き受け",100);
                break;
            case 1:
                companion = new Companion("エリス", "敵全体に氷属性ダメージ",100);
                break;
            case 2:
                companion = new Companion("カルロス", "罠を見破る",100);
                break;
            default:
                companion = new Companion("ライアン", "攻撃引き受け",100);
                break;
        }
        updateStatus();
    }

    private void startFirstBattle() {
        changeBackground("img/battle_background.png");
        setEnemyImage("img/enemy1.png");
        appendLog("モンスターが現れました！戦闘が始まります。");
    
        // 戦闘進行UIの表示
        showBattleUI();
        currentEnemy = new Enemy("モンスター", 30, 5);
    }

    private void showBattleUI() {
        actionPanel.removeAll();
        String[] options = {"攻撃", "魔法", "アイテム", "逃げる"};
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> handleBattleAction(option));
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void handleBattleAction(String action) {
        switch (action) {
            case "攻撃":
                attackEnemy(currentEnemy);
                break;
            case "魔法":
                useMagic(currentEnemy);
                break;
            case "アイテム":
                useItem();
                break;
            case "逃げる":
                escape();
                break;
        }
        if (!checkBattleOutcome(currentEnemy)) {
            enemyAttack(currentEnemy);
            checkBattleOutcome(currentEnemy);
        }
    }

    private void attackEnemy(Enemy enemy) {
        int damage = weapon.getPower();
        enemy.setHp(enemy.getHp() - damage);
        appendLog("プレイヤーの攻撃！ 敵のHP: " + enemy.getHp());

        if (companion != null && "エリス".equals(companion.getName())) {
            damage = 10; // エリスのスキルによる追加ダメージ
            enemy.setHp(enemy.getHp() - damage);
            appendLog("エリスのスキルが発動しました！ 敵のHP: " + enemy.getHp());
        }
        if (enemy.getHp() <= 0) {
            appendLog("敵を倒しました！");
            changeBackground("img/rootSelect_background.png");
            removeEnemyImage();
            selectPath();
        }
    }

    private void useMagic(Enemy enemy) {
        String[] spells = {"ファイア", "アイス"};
        actionPanel.removeAll();
        for (String spell : spells) {
            JButton button = new JButton(spell);
            button.addActionListener(e -> {
                int damage = 0;
                if ("ファイア".equals(spell)) {
                    damage = 20;
                    player.setMp(player.getMp() - 10);
                } else if ("アイス".equals(spell)) {
                    damage = 30;
                    player.setMp(player.getMp() - 20);
                }
                enemy.setHp(enemy.getHp() - damage);
                appendLog("魔法を使いました！ 敵のHP: " + enemy.getHp());
                showBattleUI();
                if (!checkBattleOutcome(enemy)) {
                    enemyAttack(enemy);
                    checkBattleOutcome(enemy);
                }
            });
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void useItem() {
        String[] items = {"ポーション", "エリクサー"};
        actionPanel.removeAll();
        for (String item : items) {
            JButton button = new JButton(item);
            button.addActionListener(e -> {
                if ("ポーション".equals(item)) {
                    player.setHp(player.getHp() + 20);
                    appendLog("ポーションを使いました。 プレイヤーHP: " + player.getHp());
                } else if ("エリクサー".equals(item)) {
                    player.setHp(100);
                    player.setMp(100);
                    appendLog("エリクサーを使いました。 プレイヤーHP: " + player.getHp() + ", MP: " + player.getMp());
                }
                showBattleUI();
                if (!checkBattleOutcome(currentEnemy)) {
                    enemyAttack(currentEnemy);
                    checkBattleOutcome(currentEnemy);
                }
            });
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void escape() {
        int chance = new Random().nextInt(100);
        if (chance < 50) {
            appendLog("逃げることができました！");
            changeBackground("img/rootSelect_background.png");
            removeEnemyImage();
            selectPath();
        } else {
            appendLog("逃げられなかった！");
            enemyAttack(currentEnemy);
            checkBattleOutcome(currentEnemy);
        }
    }

    private void enemyAttack(Enemy enemy) {
        int damage = enemy.getAttackPower();
        player.setHp(player.getHp() - damage);
        appendLog("敵の攻撃！ プレイヤーHP: " + player.getHp());
    }

    private boolean checkBattleOutcome(Enemy enemy) {
        if (player.getHp() <= 0) {
            appendLog("プレイヤーが倒されました...");
            changeBackground("img/game_over_background.png");
            removeEnemyImage();
            return true;
        } else if (enemy.getHp() <= 0) {
            appendLog("敵を倒しました！");
            changeBackground("img/rootSelect_background.png");
            removeEnemyImage();
            selectPath();
            return true;
        }
        return false;
    }

    private void selectPath() {
        actionPanel.removeAll();
        String[] options = {"左の道", "右の道", "まっすぐ進む"};
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                appendLog(option + "を選びました。");
                handlePathSelection(option);
            });
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }
    
    private void handlePathSelection(String path) {
        switch (path) {
            case "左の道":
                appendLog("時間がかかって敵に遭遇しました。");
                startBattle(new Enemy("モンスター", 50, 10));
                break;
            case "右の道":
                handleRightPath();
                break;
            case "まっすぐ進む":
                appendLog("経験値とアイテムを手に入れました！");
                player.addExperience(500);
                player.addItem("銀の剣");
                updateStatus();
                break;
        }
    }
    
    private void handleRightPath() {
        if (companion != null && "カルロス".equals(companion.getName())) {
            int chance = new Random().nextInt(100);
            if (chance < 70) {
                appendLog("カルロスが罠を見破りました。宝箱を開けます！");
                player.addItem("宝箱の中身");
                updateStatus();
            } else {
                appendLog("カルロスが罠を見破れませんでした。");
                handleTrap();
            }
        } else {
            handleTrap();
        }
    }
    
    private void handleTrap() {
        String[] options = {"慎重に罠を解除する", "魔法で罠を解除する", "罠を無視して宝箱を開ける"};
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                appendLog(option + "を試みます。");
                handleTrapOption(option);
            });
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }
    
    private void handleTrapOption(String option) {
        switch (option) {
            case "慎重に罠を解除する":
                int chance = new Random().nextInt(100);
                if (chance < 50) {
                    appendLog("罠を解除できました。宝箱を開けます！");
                    openTreasureChest();
                } else {
                    appendLog("罠が作動しました。パーティー全員が30ダメージを受けました。");
                    player.setHp(player.getHp() - 30);
                    if (companion != null) {
                        companion.setHp(companion.getHp() - 30);
                    }
                    updateStatus();
                }
                break;
            case "魔法で罠を解除する":
                if (companion != null && "リアナ王女".equals(companion.getName())) {
                    chance = new Random().nextInt(100);
                    if (chance < 50) {
                        appendLog("魔法で罠を解除できました。宝箱を開けます！");
                        openTreasureChest();
                    } else {
                        appendLog("魔法が誤作動しました。罠が作動しましたが、宝箱を開けられます。");
                        player.setHp(player.getHp() - 10);
                        if (companion != null) {
                            companion.setHp(companion.getHp() - 10);
                        }
                        player.addItem("魔法の指輪");
                        player.addItem("エリクサー");
                        player.addItem("銀の鍵");
                        updateStatus();
                    }
                } else {
                    appendLog("リアナ王女がいないため、魔法で罠を解除できません。");
                }
                break;
            case "罠を無視して宝箱を開ける":
                appendLog("罠が作動しました。30ダメージを受けましたが、宝箱を開けます。");
                player.setHp(player.getHp() - 30);
                if (companion != null) {
                    companion.setHp(companion.getHp() - 30);
                }
                openTreasureChest();
                break;
        }
    }
    
    private void openTreasureChest() {
        player.addItem("魔法の指輪");
        player.addItem("エリクサー");
        player.addItem("銀の鍵");
        appendLog("宝箱を開けました！ 以下のアイテムを獲得しました:\n- 魔法の指輪\n- エリクサー\n- 銀の鍵");
        updateStatus();
    }
    
    private void startBattle(Enemy enemy) {
        currentEnemy = enemy;
        changeBackground("img/battle_background.png");
        setEnemyImage("img/enemy.png");
        appendLog(enemy.getName() + "が現れました！戦闘が始まります。");
        showBattleUI();
    }
    

    private void appendLog(String message) {
        logArea.append(message + "\n");
    }

    private void updateStatus() {
        playerStatusLabel.setText("プレイヤー - HP: " + player.getHp() + " MP: " + player.getMp());
        if (companion != null) {
            companionStatusLabel.setText("仲間 - " + companion.getName() + " スキル: " + companion.getSkill());
        }
    }

    private void changeBackground(String imagePath) {
        backgroundPanel.setBackgroundImage(imagePath);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }

    private void setEnemyImage(String imagePath) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
    
        int newWidth = 200; // 適切なサイズに調整
        int newHeight = -1; // アスペクト比を維持
    
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
    
        enemyImageLabel.setIcon(resizedIcon);
    
        // 中央に配置、少し下に配置するために空のラベルを追加
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1; // 1行目に配置
        centerPanel.add(enemyImageLabel, gbc);
    
        // 上部に空のラベルを追加
        JLabel emptyLabel = new JLabel();
        gbc.gridy = 0; // 0行目に空のラベルを配置
        centerPanel.add(emptyLabel, gbc);
    
        backgroundPanel.removeAll();
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }

    private void removeEnemyImage() {
        backgroundPanel.removeAll();
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }
}
