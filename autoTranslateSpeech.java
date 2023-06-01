import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class app {
	
	public static String langList[] = {"English", "Spanish", "German", "French", "Japanese", "Russian", "Chinese", "Hindi"};
	public static JComboBox<String> comboBoxFrom = new JComboBox<>(langList);
	public static JComboBox<String> comboBoxTo = new JComboBox<>(langList);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Voice Translation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        
        //frame.setLayout(new GridLayout(5,5));
        JPanel panel = new JPanel();
        JPanel nPanel = new JPanel();
        JPanel wPanel = new JPanel();
        JPanel ePanel = new JPanel();
        JPanel sPanel = new JPanel();
        JPanel cPanel = new JPanel();
        
        frame.add(panel);
        frame.add(nPanel, BorderLayout.NORTH);
        frame.add(wPanel, BorderLayout.WEST);
        frame.add(ePanel, BorderLayout.EAST);
        frame.add(sPanel, BorderLayout.SOUTH);
        frame.add(cPanel, BorderLayout.CENTER);
        
        //frame.pack();
        
        placeComponents(nPanel, ePanel, wPanel, sPanel, cPanel, panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel nPanel, JPanel ePanel, JPanel wPanel, JPanel sPanel, JPanel cPanel, JPanel panel) {
        nPanel.setLayout(new GridBagLayout());
        wPanel.setLayout(new GridBagLayout());
        ePanel.setLayout(new GridBagLayout());
        sPanel.setLayout(new GridBagLayout());
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(5, 5, 5,  5);

        // From
        JLabel label = new JLabel("Translate From: ");
        c.gridx = 2;
        c.gridy = 0;
        nPanel.add(label, c);

        c.gridx = 3;
        c.gridy = 0;
        nPanel.add(comboBoxFrom, c);
        
     // To
        JLabel label2 = new JLabel("Translate To: ");
        c.gridx = 0;
        c.gridy = 0;
        nPanel.add(label2, c);
        
        c.gridx = 1;
        c.gridy = 0;
        nPanel.add(comboBoxTo, c);

        // text field        
        JTextArea textArea = new JTextArea();
        c.gridx = 0;
        c.gridy = 0;
        textArea.setPreferredSize(new Dimension(280, 280));
        textArea.setEditable(false);
        cPanel.add(textArea, c);
        
        // Buttons
        JButton button = new JButton("Translate");
        c.gridx = 1;
        c.gridy = 0;
        sPanel.add(button, c);
        
        JButton button2 = new JButton("Swap");
        c.gridx = 2;
        c.gridy = 0;
        sPanel.add(button2, c);


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the translation logic here
            	textArea.append("To Language: " + comboBoxTo.getSelectedItem().toString() + ", Language Code: " + setToLanguage() + "\n");
            	textArea.append("From Language: " + comboBoxFrom.getSelectedItem().toString() + ", Language Code: " + setFromLanguage() + "\n");
            }
        });
        
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the translation logic here
            	switchLanguage();
            }
        });
    }
    
 // define switchLanguage method
    private static void switchLanguage() {
        // gets the appropriate language codes
        String tLanguage = comboBoxTo.getSelectedItem().toString();
        String fLanguage = comboBoxFrom.getSelectedItem().toString();

        // switches the language codes
        String tempLanguage = fLanguage;
        fLanguage = tLanguage;
        tLanguage = tempLanguage;

        // sets the language codes
        comboBoxFrom.setSelectedItem(fLanguage);
        comboBoxTo.setSelectedItem(tLanguage);
    }
    
    public static String setFromLanguage() {
        String fromLanguage;

        switch (comboBoxFrom.getSelectedItem().toString()) {
            case "English":
                fromLanguage = "en";
                break;
            case "Spanish":
                fromLanguage = "es";
                break;
            case "German":
                fromLanguage = "de";
                break;
            case "French":
                fromLanguage = "fr";
                break;
            case "Japanese":
                fromLanguage = "ja";
                break;
            case "Russian":
                fromLanguage = "ru";
                break;
            case "Chinese":
                fromLanguage = "zh-tw";
                break;
            case "Hindi":
                fromLanguage = "hi";
                break;
            default:
                throw new IllegalArgumentException("Invalid menu option");
        }

        return fromLanguage;
    }
    
    public static String setToLanguage() {
        String toLanguage;

        switch (comboBoxTo.getSelectedItem().toString()) {
            case "English":
            	toLanguage = "en";
                break;
            case "Spanish":
            	toLanguage = "es";
                break;
            case "German":
            	toLanguage = "de";
                break;
            case "French":
            	toLanguage = "fr";
                break;
            case "Japanese":
            	toLanguage = "ja";
                break;
            case "Russian":
            	toLanguage = "ru";
                break;
            case "Chinese":
            	toLanguage = "zh-tw";
                break;
            case "Hindi":
            	toLanguage = "hi";
                break;
            default:
                throw new IllegalArgumentException("Invalid menu option");
        }

        return toLanguage;
    }
}
