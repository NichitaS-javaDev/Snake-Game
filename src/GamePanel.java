import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private final int DOT_SIZE = 48;
    private final int ALL_DOTS = 420;
    private int dots;
    private int score;
    private final int[] y = new int[ALL_DOTS];
    private final int[] x = new int[ALL_DOTS];
    private int appleX, appleY;
    private Image dot, apple;
    private boolean inGame = true;
    private boolean down = false;
    private boolean right = true;
    private boolean left = false;
    private boolean up =false;
    Timer timer;

    GamePanel(){
        setBackground(Color.BLACK);
        InitGame();
        LoadImage();
        addKeyListener(new PanelKeyPressed());
        setFocusable(true);
    }

    public void InitGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 144 - i*DOT_SIZE;
            y[i] = 144;
        }
        timer = new Timer(200,this);
        timer.start();
        createApple();
    }

    public void LoadImage(){
        ImageIcon image = new ImageIcon("Square.png");
        dot = image.getImage();
        image = new ImageIcon("AppleIcon.png");
        apple = image.getImage();
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(right) {
            x[0] += DOT_SIZE;
        }
        if(left) {
            x[0] -= DOT_SIZE;
        }
        if(up) {
            y[0] -= DOT_SIZE;
        }
        if(down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if(appleX == x[0] && appleY == y[0]){
            dots++;
            createApple();
            score++;
        }
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(19)*DOT_SIZE;
    }

    public void checkCollision(){
        if(x[0] > this.getHeight() || x[0] > this.getWidth()
                || y[0] < 0 || x[0] < 0 || y[0] > this.getWidth() || y[0] > this.getHeight()){
            inGame = false;
        }
        for (int i = dots; i > 0 ; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        else{
            g.setFont(new Font("",Font.BOLD+Font.ITALIC,50));
            g.setColor(Color.YELLOW);
            g.drawString("GAME  OVER",this.getWidth()/3,this.getHeight()/2);
            g.drawString("Your score : " + score,this.getWidth()/3,this.getHeight()/2+100);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            move();
            checkApple();
            checkCollision();
            repaint();
        }
    }

    class PanelKeyPressed extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_DOWN && !up){
                down = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_UP && !down){
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                down = false;
                up = false;
            }
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                down = false;
                up = false;
            }
        }
    }
}
