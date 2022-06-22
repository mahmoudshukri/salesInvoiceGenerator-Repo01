package sig.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceHeaderDialog extends JDialog{
   private JTextField customerName;
    private JTextField invDate;
    private JLabel customerNameLabel;
    private JLabel invDateLabel;
    private JButton okBtn;
    private JButton cancelBtn;

    public InvoiceHeaderDialog(InvoiceGUI gui) {
        customerNameLabel = new JLabel("Customer Name:");
        customerName = new JTextField(20);
        invDateLabel = new JLabel("Invoice Date:");
        invDate = new JTextField(25);
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("createInvoiceHeaderOK");
        cancelBtn.setActionCommand("createInvoiceHeaderCancel");
        
        okBtn.addActionListener(gui.getActionHandler());
        cancelBtn.addActionListener(gui.getActionHandler());
        setLayout(new GridLayout(4, 3));
        
        add(customerNameLabel);
        add(customerName);
        add(invDateLabel);
        add(invDate);
        add(okBtn);
        add(cancelBtn);
        
        pack();
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public JTextField getCustomerName() {
        return customerName;
    }

    public JLabel getInvDateLabel() {
        return invDateLabel;
    }
   
}
