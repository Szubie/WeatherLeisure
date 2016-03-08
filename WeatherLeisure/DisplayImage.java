import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class DisplayImage {

    public DisplayImage() {

        WeatherModel model = new WeatherModel();


        JFrame frame = new JFrame("Display Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);

        
        JLabel background1 = new JLabel(new ImageIcon("background.png"));
        frame.add(background1); 
        frame.setResizable(false);


        Container pane = frame.getContentPane();

        JPanel topBar = new JPanel();
        topBar.setBackground(Color.BLUE);
        JLabel label1 = new JLabel("Weather",
                    JLabel.CENTER);
        label1.setForeground(Color.WHITE);
        topBar.add(label1);


        ImageIcon image = new ImageIcon("images/Cloud.png");
        JLabel imageLabel = new JLabel(image);
        imageLabel.setVisible(true);

        pane.add(imageLabel, BorderLayout.CENTER);
        pane.add(topBar,BorderLayout.NORTH);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLUE);

        JButton weatherButton = new JButton("Weather");
        JButton proposalButton = new JButton("Proposal");
        JButton settingsButton = new JButton("Settings");

        bottomPanel.add(weatherButton);
        bottomPanel.add(proposalButton);
        bottomPanel.add(settingsButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

    }


    public ImageIcon resizeImg(ImageIcon image, int width, int height ){  
        BufferedImage bi = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(image.getImage(), 200, 200, width, height, null);
        ImageIcon newIcon = new ImageIcon(bi);
        return newIcon;
    }

 
    public static void main (String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new DisplayImage();
            }
        });
    }
}