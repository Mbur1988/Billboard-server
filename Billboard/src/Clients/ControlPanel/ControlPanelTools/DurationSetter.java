package Clients.ControlPanel.ControlPanelTools;

import java.awt.event.*;
import java.text.Format;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.schedulePanel;
import static Clients.ControlPanel.ControlPanelInterface.ControlPanelInterface.screenWidth;
import static Clients.ControlPanel.ControlPanelTools.Tools.*;

public class DurationSetter {

    static int mins;

    public static void setDuration() {

        JLabel lbl_duration = new JLabel("Set duration: ");
        addLabel(schedulePanel, lbl_duration, ((screenWidth / 3)), 250, 180, 50);

        JTextField tf_mins = new JTextField("duration (mins)");
        addTextfield(schedulePanel, tf_mins, ((screenWidth / 3)), 300, 160, 40);

        tf_mins.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tf_mins.setText(null);
            }
            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        tf_mins.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    mins = Integer.parseInt(tf_mins.getText());


                } catch (NumberFormatException i) {
                    if (tf_mins != null) {
                        JOptionPane.showMessageDialog(schedulePanel,
                                "Please set duration in number of minutes.",
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                lbl_duration.setText("Set duration: " + mins);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {        // This part seems to create some errors.
                //mins = Integer.parseInt(tf_mins.getText());    // It's just meant to update the label when
                //lbl_duration.setText("Set duration: " + mins); // values are removed.
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    mins = Integer.parseInt(tf_mins.getText());


                } catch (NumberFormatException i) {
                    if (tf_mins != null) {
                        JOptionPane.showMessageDialog(schedulePanel,
                                "Please set duration in number of minutes.",
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                lbl_duration.setText("Set duration: " + mins);
            }
        });

    }
}
