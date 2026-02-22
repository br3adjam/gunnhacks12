import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

public class gui implements ActionListener {

    public gui(){
        JFrame frame = new JFrame();

        JButton button = new JButton("Click me");
        buutton.addActionListener();
        JLabel label = new JLabel("Number of clicks: 0");

        JPanel panel = new JPanel(); 
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("OUR GUI");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new gui();
    }
}
