package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// todo)) nothing hardcoded, pull info from config file, record works, settings work, keybind live-updates when recording, keybinds work, make work

public class MacroManager extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    public MacroManager() {
        setTitle("Macro Orchestrator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        mainPanel.add(createHomeScreen(), "HOME");
        mainPanel.add(createKeybindScreen(), "KEYBINDS");

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHomeScreen() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        JTextArea sequenceDisplay = new JTextArea("");
        sequenceDisplay.setEditable(false);
        sequenceDisplay.setBackground(new Color(230, 230, 230));
        leftPanel.add(new JLabel("Current Sequence"), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(sequenceDisplay), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton recordBtn = new JButton("Record");
        JButton viewBtn = new JButton("View Keybinds");
        JButton settingsBtn = new JButton("Settings");

        JPanel runsPanel = new JPanel(new BorderLayout());
        runsPanel.add(new JLabel("Runs:"), BorderLayout.NORTH);
        runsPanel.add(new JTextField("bash.sh / rs / jar"), BorderLayout.CENTER);

        viewBtn.addActionListener(e -> cardLayout.show(mainPanel, "KEYBINDS"));

        rightPanel.add(recordBtn);
        rightPanel.add(viewBtn);
        rightPanel.add(settingsBtn);
        rightPanel.add(runsPanel);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createKeybindScreen() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        panel.add(backBtn, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        String[] keys = {"a", "^", "b", "<", "v", ">"};
        for (String k : keys) {
            gridPanel.add(new JButton(k));
        }
        panel.add(gridPanel, BorderLayout.WEST);

        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        
        configPanel.add(new JButton("View Config File"));
        configPanel.add(createToggleRow("Max Combo Length", "8"));
        configPanel.add(createToggleRow("Minimize on Close", "v"));
        configPanel.add(createToggleRow("Enable Toggle Key", "v"));
        configPanel.add(new JButton("Combo Tester"));

        panel.add(configPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createToggleRow(String label, String val) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(new JTextField(val, 3), BorderLayout.EAST);
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MacroManager::new);
    }
}