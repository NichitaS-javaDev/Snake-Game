import javax.swing.*;

public class Window extends JFrame {

    Window(){
        setSize(980,960);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new GamePanel());
    }

    public static void main(String[] args) {
        new Window().setVisible(true);
    }
}
