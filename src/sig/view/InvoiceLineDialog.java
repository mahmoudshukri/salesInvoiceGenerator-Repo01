package sig.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceLineDialog extends JDialog{
    // declare dialog variables (adding labels and text fields)
    private JTextField NameField;
    private JLabel NameLabel;
    private JTextField CountField;
    private JLabel CountLabel;
    private JTextField PriceField;
    private JLabel PriceLabel;
  // declare dialog button variables (adding buttons)
    private JButton okBtn;
    private JButton cancelBtn;
    
    
    
        public InvoiceLineDialog(InvoiceGUI gui) {
        NameField = new JTextField(25);
        NameLabel = new JLabel("Name");
        
        CountField = new JTextField(18);
        CountLabel = new JLabel("Count");
        
        PriceField = new JTextField(20);
        PriceLabel = new JLabel("Price");
        
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("createItemLineOK");
        cancelBtn.setActionCommand("createItemLineCancel");
        
        okBtn.addActionListener(gui.getActionHandler());
        cancelBtn.addActionListener(gui.getActionHandler());
        setLayout(new GridLayout(6, 4));
        
        add(NameLabel);
        add(NameField);
        add(CountLabel);
        add(CountField);
        add(PriceLabel);
        add(PriceField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
    }

    public  JTextField getNameField() {
        return NameField;
    }

    public  JTextField getCountField() {
        return CountField;
    }

    public  JTextField getPriceField() {
        return PriceField;
    }

   
}
