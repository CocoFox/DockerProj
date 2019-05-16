package app.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import app.MainPanel;

public class RetourAction extends MouseAdapter {

    private MainPanel parent;
    private JLabel label;
    private String labelString;
    private String panel;

    public RetourAction(MainPanel parent, JLabel label, String panel) {
        this.parent = parent;
        this.label = label;
        this.labelString = label.getText();
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        parent.invoke(panel);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        label.setText(labelString);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        label.setText("<html><a href=''>" + labelString + "</a></html>");
    }
}