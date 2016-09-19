package gt.practice.java2d.puzzle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleEx extends JFrame {

    private static final double DESIRED_WIDTH = 300.0;
    private java.util.List<Point> solution = new ArrayList<>();
    private java.util.List<MyButton> buttons;
    private JPanel panel;
    private BufferedImage source;
    private BufferedImage resizedImage;
    private int width;
    private int height;
    private Image image;
    private MyButton lastButton;

    public PuzzleEx() throws HeadlessException {
        initUI();
    }

    private void initUI() {
        solution.add(new Point(0, 0));
        solution.add(new Point(0, 1));
        solution.add(new Point(0, 2));
        solution.add(new Point(1, 0));
        solution.add(new Point(1, 1));
        solution.add(new Point(1, 2));
        solution.add(new Point(2, 0));
        solution.add(new Point(2, 1));
        solution.add(new Point(2, 2));
        solution.add(new Point(3, 0));
        solution.add(new Point(3, 1));
        solution.add(new Point(3, 2));

        buttons = new ArrayList<>();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new GridLayout(4, 3, 0, 0));

        try {
            source = loadImage();
            int newHeight = getNewHeight(source.getWidth(),
                    source.getHeight());
            resizedImage =
                    resizeImage(source, (int) DESIRED_WIDTH, newHeight, BufferedImage.TYPE_INT_ARGB);
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = resizedImage.getWidth();
        height = resizedImage.getHeight();
        add(panel, BorderLayout.CENTER);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                image = createImage(
                        new FilteredImageSource(resizedImage.getSource(),
                                                new CropImageFilter(j * width/3,
                                                                    i*height/4,
                                                                    width/3,
                                                                    height/4)));
                MyButton myButton = new MyButton(image);
                myButton.putClientProperty("position", new Point(i, j));

                if (i == 3 && j == 2) {
                    lastButton = new MyButton();
                    lastButton.setBorderPainted(false);
                    lastButton.setContentAreaFilled(false);
                    lastButton.setLastButton();
                    lastButton.putClientProperty("position", new Point(i,j));
                } else {
                    buttons.add(myButton);
                }
            }
        }

        Collections.shuffle(buttons);
        buttons.add(lastButton);

        for (MyButton button : buttons) {
            panel.add(button);
            button.setBorder(BorderFactory.createLineBorder(Color.gray));
            button.addActionListener(new ClickAction());
        }

        pack();
        setTitle("Puzzle");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int typeIntArgb) {
        BufferedImage resizedImage = new BufferedImage(width, height, typeIntArgb);
        Graphics2D graphics = (Graphics2D) resizedImage.getGraphics();
        graphics.drawImage(originalImage, 0, 0, width, height, null);
        graphics.dispose();

        return resizedImage;
    }

    private int getNewHeight(int width, int height) {
        double aspectRatio = DESIRED_WIDTH / (double) width;
        return (int) (height * aspectRatio);
    }

    private BufferedImage loadImage() throws IOException {
        URL resource = PuzzleEx.class.getResource("gt.jpg");
        return ImageIO.read(resource);
    }

    private class ClickAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkButton(e);
            checkSolution();
        }

        private void checkButton(ActionEvent e) {
            int lastButtonIndex = 0;
            for (MyButton button : buttons) {
                if (button.isLastButton()) {
                    lastButtonIndex = buttons.indexOf(button);
                }
            }

            JButton button = (JButton) e.getSource();
            int buttonClickedIndex = buttons.indexOf(button);

            if (buttonClickedIndex - 1 == lastButtonIndex ||
                buttonClickedIndex + 1 == lastButtonIndex ||
                buttonClickedIndex - 3 == lastButtonIndex ||
                buttonClickedIndex + 3 == lastButtonIndex) {
                Collections.swap(buttons, buttonClickedIndex, lastButtonIndex);
                updateButtons();
            }
        }

        private void updateButtons() {
            panel.removeAll();
            for(MyButton myBUtton : buttons) {
                panel.add(myBUtton);
            }

            panel.validate();
        }
    }

    private void checkSolution() {
        java.util.List<MyButton> currentButtonList = new ArrayList<>();

        for(MyButton myButton : buttons) {
            currentButtonList.add(myButton);
        }

        if (compareList(currentButtonList, solution)) {
            JOptionPane.showMessageDialog(panel, "Finished",
                                "Congratulations",
                                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean compareList(List<MyButton> currentButtonList, List<Point> solution) {
        return currentButtonList.toString().contentEquals(solution.toString());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PuzzleEx puzzleEx = new PuzzleEx();
                puzzleEx.setVisible(true);
            }
        });
    }
}
