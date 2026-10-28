import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Java Assignment 2: Pet Selector
 * Select a pet using a radio button, view its illustration, and receive a message box.
 * Uses Java Swing and Java2D; no external image files or libraries are required.
 */
public class PetSelector extends JFrame {
    private final PetIllustration illustration = new PetIllustration();
    private final JLabel selectedLabel = new JLabel("Selected pet: Pig", SwingConstants.CENTER);

    public PetSelector() {
        super("Pet Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 430);
        setMinimumSize(new Dimension(560, 370));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(18, 12));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));
        root.setBackground(new Color(248, 250, 253));
        setContentPane(root);

        JLabel heading = new JLabel("Choose your favourite pet", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setForeground(new Color(28, 53, 87));
        root.add(heading, BorderLayout.NORTH);

        JPanel choices = new JPanel();
        choices.setLayout(new BoxLayout(choices, BoxLayout.Y_AXIS));
        choices.setBackground(Color.WHITE);
        choices.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 227, 237)),
                new EmptyBorder(18, 18, 18, 18)));

        JLabel chooseLabel = new JLabel("PET OPTIONS");
        chooseLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        chooseLabel.setForeground(new Color(95, 110, 130));
        chooseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        choices.add(chooseLabel);
        choices.add(Box.createVerticalStrut(12));

        ButtonGroup group = new ButtonGroup();
        String[] pets = {"Bird", "Cat", "Dog", "Rabbit", "Pig"};
        for (String pet : pets) {
            JRadioButton button = new JRadioButton(pet);
            button.setFont(new Font("SansSerif", Font.PLAIN, 16));
            button.setBackground(Color.WHITE);
            button.setFocusPainted(false);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            group.add(button);
            choices.add(button);
            choices.add(Box.createVerticalStrut(8));
            button.addActionListener(e -> showPet(pet, true));
            if (pet.equals("Pig")) button.setSelected(true);
        }

        JPanel preview = new JPanel(new BorderLayout(8, 8));
        preview.setBackground(Color.WHITE);
        preview.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 227, 237)),
                new EmptyBorder(10, 10, 10, 10)));
        illustration.setPreferredSize(new Dimension(390, 280));
        preview.add(illustration, BorderLayout.CENTER);
        selectedLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        selectedLabel.setForeground(new Color(28, 53, 87));
        preview.add(selectedLabel, BorderLayout.SOUTH);

        root.add(choices, BorderLayout.WEST);
        root.add(preview, BorderLayout.CENTER);
        showPet("Pig", false);
    }

    private void showPet(String pet, boolean showMessage) {
        illustration.setPet(pet);
        selectedLabel.setText("Selected pet: " + pet);
        if (showMessage) {
            JOptionPane.showMessageDialog(this,
                    "You selected the " + pet + "!",
                    "Pet Selection",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static class PetIllustration extends JPanel {
        private String pet = "Pig";
        private final Color ink = new Color(65, 58, 62);

        PetIllustration() {
            setBackground(new Color(246, 248, 252));
        }

        void setPet(String pet) {
            this.pet = pet;
            repaint();
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g2.setColor(new Color(232, 239, 248));
            g2.fill(new Ellipse2D.Double(w * .18, h * .14, w * .64, h * .70));
            switch (pet) {
                case "Bird": drawBird(g2, w, h); break;
                case "Cat": drawCat(g2, w, h); break;
                case "Dog": drawDog(g2, w, h); break;
                case "Rabbit": drawRabbit(g2, w, h); break;
                default: drawPig(g2, w, h); break;
            }
            g2.dispose();
        }

        private void line(Graphics2D g, int x1, int y1, int x2, int y2, int width) {
            g.setColor(ink); g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(x1, y1, x2, y2);
        }
        private void eye(Graphics2D g, int x, int y) {
            g.setColor(ink); g.fill(new Ellipse2D.Double(x - 4, y - 4, 8, 8));
        }
        private void drawPig(Graphics2D g, int w, int h) {
            int cx=w/2, cy=(int)(h*.55);
            g.setColor(new Color(246, 166, 178)); g.fillOval(cx-94, cy-54, 188, 112);
            g.setColor(new Color(239, 143, 160));
            int[] ex={cx-60,cx-45,cx-22}, ey={cy-40,cy-86,cy-43}; g.fillPolygon(ex,ey,3);
            int[] ex2={cx+20,cx+45,cx+62}, ey2={cy-43,cy-86,cy-38}; g.fillPolygon(ex2,ey2,3);
            g.setColor(new Color(255, 206, 213)); g.fillOval(cx-47,cy-30,94,61);
            g.setColor(new Color(231, 126, 146)); g.fillOval(cx-30,cy-9,60,37);
            g.setColor(ink); g.fillOval(cx-16,cy+2,7,9); g.fillOval(cx+9,cy+2,7,9);
            eye(g,cx-34,cy-18); eye(g,cx+34,cy-18);
            g.setColor(new Color(246,166,178)); g.fillRoundRect(cx-65,cy+42,28,43,12,12); g.fillRoundRect(cx+38,cy+42,28,43,12,12);
            line(g,cx-15,cy+28,cx,cy+34,3); line(g,cx,cy+34,cx+15,cy+28,3);
            g.setColor(new Color(246,166,178)); g.drawArc(cx+76,cy-38,31,34,45,280); g.drawArc(cx+88,cy-51,26,28,50,290);
        }
        private void drawCat(Graphics2D g, int w, int h) {
            int cx=w/2, cy=(int)(h*.53);
            g.setColor(new Color(220, 185, 148));
            int[] x={cx-68,cx-57,cx-17}, y={cy-30,cy-102,cy-65}; g.fillPolygon(x,y,3);
            int[] x2={cx+17,cx+57,cx+68}, y2={cy-65,cy-102,cy-30}; g.fillPolygon(x2,y2,3);
            g.fillOval(cx-72,cy-70,144,145);
            g.setColor(new Color(247, 224, 198)); g.fillOval(cx-47,cy-8,42,31); g.fillOval(cx+5,cy-8,42,31);
            g.setColor(new Color(95,150,111)); g.fillOval(cx-35,cy-29,13,19); g.fillOval(cx+22,cy-29,13,19);
            eye(g,cx-29,cy-20); eye(g,cx+29,cy-20);
            g.setColor(new Color(215,116,130)); g.fillPolygon(new int[]{cx-7,cx+7,cx},new int[]{cy+3,cy+3,cy+12},3);
            line(g,cx,cy+12,cx-8,cy+19,2); line(g,cx-8,cy+19,cx-17,cy+15,2); line(g,cx,cy+12,cx+8,cy+19,2); line(g,cx+8,cy+19,cx+17,cy+15,2);
            line(g,cx-37,cy+10,cx-72,cy+4,2); line(g,cx-37,cy+18,cx-72,cy+24,2); line(g,cx+37,cy+10,cx+72,cy+4,2); line(g,cx+37,cy+18,cx+72,cy+24,2);
        }
        private void drawDog(Graphics2D g, int w, int h) {
            int cx=w/2, cy=(int)(h*.54);
            g.setColor(new Color(186,130,83)); g.fillOval(cx-82,cy-64,164,150);
            g.setColor(new Color(133,86,58)); g.fillOval(cx-85,cy-47,45,83); g.fillOval(cx+40,cy-47,45,83);
            g.setColor(new Color(250,222,184)); g.fillOval(cx-48,cy+2,96,56);
            g.setColor(Color.WHITE); g.fillOval(cx-39,cy-28,25,29); g.fillOval(cx+14,cy-28,25,29);
            eye(g,cx-26,cy-14); eye(g,cx+26,cy-14);
            g.setColor(ink); g.fillOval(cx-13,cy+3,26,18); line(g,cx,cy+20,cx,cy+31,3); line(g,cx,cy+31,cx-11,cy+26,3); line(g,cx,cy+31,cx+11,cy+26,3);
        }
        private void drawRabbit(Graphics2D g, int w, int h) {
            int cx=w/2, cy=(int)(h*.57);
            g.setColor(new Color(238,232,220)); g.fillRoundRect(cx-52,cy-130,38,112,19,19); g.fillRoundRect(cx+14,cy-130,38,112,19,19);
            g.setColor(new Color(245,175,186)); g.fillRoundRect(cx-41,cy-119,15,83,8,8); g.fillRoundRect(cx+25,cy-119,15,83,8,8);
            g.setColor(new Color(238,232,220)); g.fillOval(cx-70,cy-77,140,142);
            g.setColor(Color.WHITE); g.fillOval(cx-40,cy-25,25,27); g.fillOval(cx+15,cy-25,25,27);
            eye(g,cx-28,cy-12); eye(g,cx+27,cy-12);
            g.setColor(new Color(235,145,164)); g.fillOval(cx-7,cy+1,14,11);
            line(g,cx,cy+12,cx-7,cy+19,2); line(g,cx-7,cy+19,cx-14,cy+15,2); line(g,cx,cy+12,cx+7,cy+19,2); line(g,cx+7,cy+19,cx+14,cy+15,2);
        }
        private void drawBird(Graphics2D g, int w, int h) {
            int cx=w/2, cy=(int)(h*.53);
            g.setColor(new Color(105,180,218)); g.fillOval(cx-68,cy-48,136,105);
            g.setColor(new Color(69,145,190)); g.fillOval(cx-38,cy-22,75,65);
            g.setColor(Color.WHITE); g.fillOval(cx+14,cy-36,32,34); eye(g,cx+32,cy-20);
            g.setColor(new Color(242,177,65)); g.fillPolygon(new int[]{cx+48,cx+78,cx+48},new int[]{cy-12,cy-1,cy+7},3);
            g.setColor(new Color(244,190,75)); g.fillPolygon(new int[]{cx-7,cx+7,cx},new int[]{cy+51,cy+51,cy+70},3);
            line(g,cx-20,cy+51,cx-23,cy+70,3); line(g,cx+4,cy+51,cx+8,cy+70,3);
            g.setColor(new Color(105,180,218)); g.fillOval(cx-78,cy-3,48,30);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PetSelector().setVisible(true));
    }
}
