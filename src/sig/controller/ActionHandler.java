
package sig.controller;

import sig.view.InvoiceGUI;
import sig.view.InvoiceHeaderDialog;
import sig.view.InvoiceLineDialog;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sig.model.InvoiceHeader;
import sig.model.InvoiceHeadersTableModel;
import sig.model.InvoiceLine;
import sig.model.InvoiceLinesTableModel;
import java.io.IOException;
import java.io.FileWriter;

public class ActionHandler extends Component implements ActionListener, ListSelectionListener {
  private InvoiceGUI gui;

  public InvoiceGUI getGui() {
    return gui;
  }
  private InvoiceLineDialog invoiceLineDialog;
  public InvoiceLineDialog getLineDialog() {
    return invoiceLineDialog;
  }
  private InvoiceHeaderDialog invoiceHeaderDialog;
  public InvoiceHeaderDialog getHeaderDialog() {
    return invoiceHeaderDialog;
  }

  public void invoiceActionListener(InvoiceGUI gui) {
    this.gui = gui;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("Action Handling Called");

    switch (e.getActionCommand()) {
    case "New Invoice":
      System.out.println("New invoice");
      newInv();
      break;

    case "Delete Invoice":
      System.out.println("Delete Invoice");
      delInv();
      break;

    case "New Item":
      System.out.println("New Item");
      newItem();
      break;

    case "Delete item":
      System.out.println("Delete Item");
      delItem();
      break;

    case "Load File":
      System.out.println("Load File");
      loadFile();

      break;

    case "Save File":
      System.out.println("Save File");
      saveFile();
      break;

    case "Exit":
      Exit();
      break;

    }

  }
@Override
  public void valueChanged(ListSelectionEvent e) {
    int selectedIndex = gui.getheaderTable().getSelectedRow();
    if (selectedIndex != -1) {
      System.out.println("YOU HAVE SELECTED ROW :" + selectedIndex);
      InvoiceHeader currentInvoiceHeader = gui.getInvoices().get(selectedIndex);
      gui.getnumLabel().setText("" + currentInvoiceHeader.getNum());
      gui.getdateLabel().setText("" + currentInvoiceHeader.getDate());
      gui.getcustomerLabel().setText(currentInvoiceHeader.getCustomer());
      gui.getTotalLabel().setText("" + currentInvoiceHeader.getInvoiceHeaderTotal());
      InvoiceLinesTableModel invoiceLineTableModel = new InvoiceLinesTableModel(currentInvoiceHeader.getLines());
      gui.getInvoiceLinetable().setModel(invoiceLineTableModel);
      invoiceLineTableModel.fireTableDataChanged();
    }

  }
  ////////////////////////*****************////////////////////////
  private void newInv() {
    invoiceHeaderDialog = new InvoiceHeaderDialog(gui);
    invoiceHeaderDialog.setVisible(true);

  }
  ////////////////////////*****************////////////////////////
  private void delInv() {
    int selectedRow = gui.getheaderTable().getSelectedRow();
    if (selectedRow != -1) {
      gui.getInvoices().remove(selectedRow);
      gui.getInvoiceHeadersTableModel().fireTableDataChanged();
    }
  }
 ////////////////////////////////////////***************add item in invoice line ****************////////////////////////////////////////
  private void newItem() {
    invoiceLineDialog = new InvoiceLineDialog(gui);
    invoiceLineDialog.setVisible(true);
  }
  ////////////////////////////////////////***************delete item from invoice line ****************////////////////////////////////////////
  private void delItem() {
    int selectedInv = gui.getheaderTable().getSelectedRow();
    int selectedRow = gui.getInvoiceLinetable().getSelectedRow();
    if (selectedInv != -1 && selectedRow != -1) {
      InvoiceHeader invoiceHeader = gui.getInvoices().get(selectedInv);
      invoiceHeader.getLines().remove(selectedRow);
      InvoiceLinesTableModel invoiceLinesTableModel = new InvoiceLinesTableModel(invoiceHeader.getLines());
      gui.getInvoiceLinetable().setModel(invoiceLinesTableModel);
      invoiceLinesTableModel.fireTableDataChanged();
      gui.getInvoiceHeadersTableModel().fireTableDataChanged();
    }

  }
  ////////////////////////////////////////****************Load FIle Code ****************////////////////////////////////////////
  private void loadFile() {
    JFileChooser loadfile = new JFileChooser();
    try {
      int result = loadfile.showOpenDialog(gui);
      if (result == JFileChooser.APPROVE_OPTION) {
        File invoiceheaderFile = loadfile.getSelectedFile();
        Path headerpath = Paths.get(invoiceheaderFile.getAbsolutePath());
        java.util.List < String > headerLines = Files.readAllLines(headerpath);
        System.out.println("Invoice Header Loaded.... ");
        
        
        
        ArrayList < InvoiceHeader > invoicesArray = new ArrayList <> ();
        for (String headerline: headerLines) {
          try {
            String[] headercomponets = headerline.split(",");
            int num = Integer.parseInt(headercomponets[0]);
            String date = headercomponets[1];
            String customername = headercomponets[2];

            InvoiceHeader invoiceHeader = new InvoiceHeader (num, date,customername);
            invoicesArray.add(invoiceHeader);
          } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(gui, "File Format Not Matching", "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
        System.out.println("Sales Invoice Check Point");

        result = loadfile.showOpenDialog(gui);
        if (result == JFileChooser.APPROVE_OPTION) {
          File itemlineFile = loadfile.getSelectedFile();
          Path linepath = Paths.get(itemlineFile.getAbsolutePath());
          java.util.List < String > lineLines = Files.readAllLines(linepath);
          System.out.println("Invoice Lines have been Loaded....");
          for (String lineLine: lineLines) {
            try {
              String[] linecomponets = lineLine.split(",");
              int invoice = Integer.parseInt(linecomponets[0]);
              String name = linecomponets[1];
              double price = Double.parseDouble(linecomponets[2]);
              int Count = Integer.parseInt(linecomponets[3]);
              InvoiceHeader inv = null;
              for (InvoiceHeader invoiceHeader: invoicesArray) {
                if (invoiceHeader.getNum() == invoice) {
                  inv = invoiceHeader;
                  break;
                }
              }
              InvoiceLine invoiceLine = new InvoiceLine(invoice, name, price, Count);
              inv.getLines().add(invoiceLine);
            } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(gui, "File Format Not Matching", "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("Item Lines check Point");
          }

          gui.setInvoices(invoicesArray);
          InvoiceHeadersTableModel invoiceHeaderTableModel = new InvoiceHeadersTableModel(invoicesArray);
          gui.setInvoiceHeadersTableModel(invoiceHeaderTableModel);
          gui.getheaderTable().setModel(invoiceHeaderTableModel);
          gui.getInvoiceHeadersTableModel().fireTableDataChanged();
        }
      }
    } catch (HeadlessException | IOException ex) {
      JOptionPane.showMessageDialog(gui, "Wrong File Format", "Error", JOptionPane.ERROR_MESSAGE);

    }

  }

  private void saveFile() {
 ArrayList<InvoiceHeader>invoiceHeaders = gui.getInvoices();
        String invoiceheader1="";
        String invoicelines="";
        for (InvoiceHeader invoiceHeader :invoiceHeaders ){
          String invcsv = invoiceHeader.getAsCSV();
          invoiceheader1 += invcsv;
          invoiceheader1 += "/n";
          
          
         for (InvoiceLine invoiceLine : invoiceHeader.getLines()) {
          String lineCSV = invoiceLine.getAsCSV();
          invoicelines += lineCSV;
          invoicelines += "\n";
         } 
          
        }
          System.out.println("Save Data Check");
        try{
         JFileChooser fc = new JFileChooser();
         int result = fc.showSaveDialog(gui);
         if (result == JFileChooser.APPROVE_OPTION){
             File headerFile = fc.getSelectedFile();
             FileWriter hfw = new FileWriter(headerFile);
                    hfw.write(invoiceheader1);
                    hfw.flush();
                    hfw.close();
             
             result = fc.showSaveDialog(gui);
             if (result == JFileChooser.APPROVE_OPTION){
             File lineFile = fc.getSelectedFile();
                 try (FileWriter lfw = new FileWriter(lineFile)) {
      
                     lfw.write(invoicelines);
                     lfw.flush();
                 }
             }
         }
        } catch (HeadlessException | IOException ex){
            
        }
        }    

  private void Exit() {
    System.exit(0);
  }
  private void createInvoiceHeaderCancel() {
        invoiceHeaderDialog.setVisible(false);
        invoiceHeaderDialog.dispose();
        invoiceHeaderDialog= null;
        
    }
  private void createInvoiceLineOK() {
//    String invoice = InvoiceLineDialog.getNameField().getText();
//    String CountStr = InvoiceLineDialog.getCountField().getText();
//    String PriceStr = InvoiceLineDialog.getPriceField().getText();
//     int count = Integer.parseInt(CountStr);
//     double price = Double.parseDouble(PriceStr);
//     int selectedInvoice = gui.getheaderTable().getSelectedRow();
//     if (selectedInvoice != -1){
//     InvoiceHeader invoiceHeader = gui.getInvoices().get(selectedInvoice);
//     InvoiceLine invoiceLine = new InvoiceLine(invoice, price, count, invoiceHeader);
//     invoiceHeader.getLines().add(invoiceLine);
//     InvoiceLinesTableModel linesTableModel = (InvoiceLinesTableModel) gui.getInvoiceLinetable().getModel();
//     linesTableModel.fireTableDataChanged();
//     
//     gui.InvoiceHeadersTableModel().fireTableDataChanged();
//     }  
        
        
    invoiceLineDialog.setVisible(false);
     invoiceLineDialog.dispose();
     invoiceLineDialog = null;    
    }
  
  
   private void createItemLineCancel() {
     invoiceLineDialog.setVisible(false);
     invoiceLineDialog.dispose();
     invoiceLineDialog = null;
    }

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

  
}