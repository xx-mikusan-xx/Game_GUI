import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private String name;
    private String gender;

    public GamePanel(String name, String gender) {
        this.name = name;
        this.gender = gender;
        initPanel();
    }

    private void initPanel() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCharacterInfo(g);
    }

    private void drawCharacterInfo(Graphics g) {
        // Set font and color for drawing text
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);

        // Define the text to be displayed
        String text = "かつて、平和な王国「エルデンリア」は光の力によって繁栄していました。しかし、闇の勢力が復活し、王国に混乱と恐怖が広がり始めます。プレイヤー名は「選ばれし者」として、光と闇のバランスを取り戻すための冒険に出ます。";

        // Define the position to start drawing the text
        int x = 50;
        int y = 50;

        // Draw each character of the text one by one
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            g.drawString(String.valueOf(c), x, y);
            x += 15; // Increase the x-coordinate to create a horizontal shift
            if (x >= getWidth() - 50) { // If the x-coordinate exceeds the panel width, move to the next line
            x = 50;
            y += 30; // Increase the y-coordinate to create a vertical shift
            }
        }

        // Transition to the next screen after displaying the text
        // You can add your code here to transition to the next screen
    }
}
