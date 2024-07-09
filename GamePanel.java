import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {

    private String text;
    private int charIndex = 0;
    private boolean showEnterMessage = false;
    private Timer timer;

    public GamePanel() {
        String name = GameManager.getInstance().getPlayerName();
        String gender = GameManager.getInstance().getPlayerGender();
        this.text = "かつて、平和な王国「エルデンリア」は光の力によって繁栄していました。しかし、闇の勢力が復活し、王国に混乱と恐怖が広がり始めます。" + name + "は「選ばれし者」として、光と闇のバランスを取り戻すための冒険に出ます。";
        initPanel();
        startTextDisplay();
    }

    private void initPanel() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "enterPressed");
        getActionMap().put("enterPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showEnterMessage) {
                    System.out.println("Enter key pressed. Transition to the next screen.");
                    transitionToNextScreen();
                }
            }
        });
    }

    private void startTextDisplay() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < text.length()) {
                    charIndex++;
                    repaint();
                } else {
                    showEnterMessage = true;
                    timer.stop();
                    repaint();
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCharacterInfo(g);
        if (showEnterMessage) {
            drawEnterMessage(g);
        }
    }

    private void drawCharacterInfo(Graphics g) {
        // Set font and color for drawing text
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);

        // Define the position to start drawing the text
        int x = 50;
        int y = 50;

        // Draw each character of the text one by one up to the current index
        for (int i = 0; i < charIndex; i++) {
            char c = text.charAt(i);
            g.drawString(String.valueOf(c), x, y);
            x += 15; // Increase the x-coordinate to create a horizontal shift
            if (x >= getWidth() - 50) { // If the x-coordinate exceeds the panel width, move to the next line
                x = 50;
                y += 30; // Increase the y-coordinate to create a vertical shift
            }
        }
    }

    private void drawEnterMessage(Graphics g) {
        // Set font and color for drawing the enter message
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.YELLOW);

        // Draw the enter message at the bottom right corner
        String message = "エンターキーを押してください";
        int x = getWidth() - g.getFontMetrics().stringWidth(message) - 20;
        int y = getHeight() - 30;
        g.drawString(message, x, y);
    }

    private void transitionToNextScreen() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame instanceof Main) {
            ((Main) topFrame).switchToWeaponSelection();
        }
    }
}
