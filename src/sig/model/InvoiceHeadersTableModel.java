/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mshukri
 */
public class InvoiceHeadersTableModel extends AbstractTableModel {
private ArrayList<InvoiceHeader> invoices;
public String[]columns={"num","date","customer","count"};

 public  InvoiceHeadersTableModel (ArrayList<InvoiceHeader>invoices) {
  
    this.invoices=invoices;
}

    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }
     public String getColumnName(int column) {
        return columns [column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoiceheader = invoices.get(rowIndex);
        
        
        switch (columnIndex){
            case 0: return invoiceheader.getNum();
            case 1: return invoiceheader.getDate();
            case 2: return invoiceheader.getCustomer();
            case 3: return invoiceheader.getInvoiceHeaderTotal();
            default : return "";
        }

    
    
}
}
