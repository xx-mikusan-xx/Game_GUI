import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    private String name;
    private String gender;
    private String text;
    private int charIndex = 0;
    private boolean showEnterMessage = false;

    public GamePanel(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.text = "かつて、平和な王国「エルデンリア」は光の力によって繁栄していました。しかし、闇の勢力が復活し、王国に混乱と恐怖が広がり始めます。プレイヤー名は「選ばれし者」として、光と闇のバランスを取り戻すための冒険に出ます。";
        initPanel();
        startTextAnimation();
    }

    private void initPanel() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && showEnterMessage) {
                    // Next screen transition code goes here
                    System.out.println("Enter key pressed. Transition to the next screen.");
                }
            }
        });
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
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.WHITE);
        String message = "エンターキーを押してください";
        int stringWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, getWidth() - stringWidth - 10, getHeight() - 10);
    }

    private void startTextAnimation() {
        Timer timer = new Timer(300, e -> { // Set the interval to 300 milliseconds
            charIndex++;
            if (charIndex > text.length()) {
                ((Timer) e.getSource()).stop();
                showEnterMessage = true;
                repaint();
            } else {
                repaint();
            }
        });
        timer.start();
    }
}
