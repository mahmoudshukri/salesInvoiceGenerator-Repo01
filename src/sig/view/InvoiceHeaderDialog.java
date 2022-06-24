/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sig.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class InvoiceHeaderDialog extends JDialog {
    private JTextField custNameField;
    private JTextField invDateField;
    private JLabel custNameLbl;
    private JLabel invDateLbl;
    private JButton okBtn;
    private JButton cancelBtn;

    public InvoiceHeaderDialog(InvoiceGUI gui) {
        custNameLbl = new JLabel("Customer Name:");
        custNameField = new JTextField(25);
        invDateLbl = new JLabel("Invoice Date:");
        invDateField = new JTextField(25);
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("createInvoiceHeaderOK");
        cancelBtn.setActionCommand("createInvoiceHeaderCancel");
        
        okBtn.addActionListener(gui.getHandler());
        cancelBtn.addActionListener(gui.getHandler());
        setLayout(new GridLayout(8, 4));
        
        add(invDateLbl);
        add(invDateField);
        add(custNameLbl);
        add(custNameField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
        
    }

    public JTextField getCustNameField() {
        return custNameField;
    }

    public JTextField getInvDateField() {
        return invDateField;
    }
    
}
