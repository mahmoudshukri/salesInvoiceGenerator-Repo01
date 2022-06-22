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
public class InvoiceLinesTableModel extends AbstractTableModel {

    public void setInvoicelines(ArrayList<InvoiceLine> invoicelines) {
        this.invoicelines = invoicelines;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }
    private ArrayList<InvoiceLine> invoicelines;
     private String[] columns = {"Invoice Number","Item Name","Price","Count","Invoice Total"};
     public InvoiceLinesTableModel(ArrayList<InvoiceLine> invoicelines) {
        this.invoicelines = invoicelines;
    }

    @Override
    public int getRowCount() {
        return invoicelines.size();
     
    }

    @Override
    public int getColumnCount() {
        return columns.length;
        
    }
     public String getColumnName(int n) {
        return columns[n];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine invoiceline = invoicelines.get(rowIndex);
        
        switch(columnIndex){
            case 0: return invoiceline.getInvoice().getNum();
            case 1 :return invoiceline.getName();
            case 2 :return invoiceline.getPrice();
            case 3 :return invoiceline.getCount();
            case 4 :return invoiceline.getLineTotal();
            default : return "";
        
        }
    }
    
}
