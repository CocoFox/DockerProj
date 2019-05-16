package app;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * IHM
 */
public class IHM extends JFrame {
  
  private static final long serialVersionUID = 1L;
  private MainPanel mainPanel;

  public IHM(String title, int width, int height) throws Exception {
    super(title);
    pack();
    setSize(width, height);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    mainPanel = new MainPanel();
    JScrollPane scr = new JScrollPane(mainPanel);
    scr.setBorder(BorderFactory.createEmptyBorder());
    scr.getVerticalScrollBar().setUnitIncrement(16);
    add(scr);
  }
}