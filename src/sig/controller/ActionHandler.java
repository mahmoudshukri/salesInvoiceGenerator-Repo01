package sig.controller;

import sig.view.InvoiceGUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import sig.view.InvoiceHeaderDialog;
import sig.view.InvoiceLineDialog;

public class ActionHandler extends Component implements ActionListener, ListSelectionListener {

  private InvoiceGUI gui;
  private InvoiceHeaderDialog invoiceHeaderDialog;
  private InvoiceLineDialog invoiceLineDialog;

  public ActionHandler(InvoiceGUI gui) {
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
      
    case "createInvoiceHeaderCancel":
      System.out.println("createInvoiceHeaderCancel");
      createInvoiceCancel();
      break;
      
    case "createInvoiceHeaderOK":
      System.out.println("createInvoiceHeaderOK");
      createInvoiceOK();
      break;
      
    case "createInvoiceLineOK":
      System.out.println("createInvoiceLineOK");
      createLineOK();
      break;
      
    case "createInvoiceLineCancel":
      System.out.println("createInvoiceLineCancel");
      createLineCancel();
      break;

    }

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    int selectedIndex = gui.getHeaderTable().getSelectedRow();
    if (selectedIndex != -1) {
      System.out.println("You have selected row: " + selectedIndex);
      InvoiceHeader currentInvoice = gui.getInvoices().get(selectedIndex);
      gui.getNumLabel().setText("" + currentInvoice.getNum());
      gui.getDateLabel().setText(currentInvoice.getDate());
      gui.getCustomerLabel().setText(currentInvoice.getCustomer());
      gui.getTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
      InvoiceLinesTableModel linesTableModel = new InvoiceLinesTableModel(currentInvoice.getLines());
      gui.getLineTable().setModel(linesTableModel);
      linesTableModel.fireTableDataChanged();
    }
  }

  private void loadFile() {
    JFileChooser fc = new JFileChooser();
    try {
      int result = fc.showOpenDialog(gui);
      if (result == JFileChooser.APPROVE_OPTION) {
        File headerFile = fc.getSelectedFile();
        Path headerPath = Paths.get(headerFile.getAbsolutePath());
        java.util.List < String > headerLines = Files.readAllLines(headerPath);
        System.out.println("Invoices have been read");
        // 1,22-11-2020,Ali
        // 2,13-10-2021,Saleh
        // 3,09-01-2019,Ibrahim
        ArrayList < InvoiceHeader > invoicesArray = new ArrayList < > ();
        for (String headerLine: headerLines) {
            try{
          String[] headerParts = headerLine.split(",");
          int invoiceNum = Integer.parseInt(headerParts[0]);
          String invoiceDate = headerParts[1];
          String customerName = headerParts[2];

          InvoiceHeader invoice = new InvoiceHeader(invoiceNum, invoiceDate, customerName);
          invoicesArray.add(invoice);
          } catch (Exception ex) {
                       // ex.printStackTrace();
                        JOptionPane.showMessageDialog(gui, "Wrong File Format", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
        }
        System.out.println("Check point");
        result = fc.showOpenDialog(gui);
        if (result == JFileChooser.APPROVE_OPTION) {
          File lineFile = fc.getSelectedFile();
          Path linePath = Paths.get(lineFile.getAbsolutePath());
          java.util.List < String > lineLines = Files.readAllLines(linePath);
          System.out.println("Lines have been read");
          for (String lineLine: lineLines) {
              try{
            String lineParts[] = lineLine.split(",");
            int invoiceNum = Integer.parseInt(lineParts[0]);
            String itemName = lineParts[1];
            double itemPrice = Double.parseDouble(lineParts[2]);
            int count = Integer.parseInt(lineParts[3]);
            InvoiceHeader inv = null;
            for (InvoiceHeader invoice: invoicesArray) {
              if (invoice.getNum() == invoiceNum) {
                inv = invoice;
                break;
              }
            }

            InvoiceLine line = new InvoiceLine(itemName, itemPrice, count, inv);
            inv.getLines().add(line);
          } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(gui, "Wrong File Format", "Error", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                    }
          System.out.println("Check point");
        }
        gui.setInvoices(invoicesArray);
        InvoiceHeadersTableModel invoicesTableModel = new InvoiceHeadersTableModel(invoicesArray);
        gui.setInvoiceHeadersTableModel(invoicesTableModel);
        gui.getHeaderTable().setModel(invoicesTableModel);
        gui.getInvoiceHeadersTableModel().fireTableDataChanged();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
       JOptionPane.showMessageDialog(gui, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
       System.exit(0);
    }
  }

  private void saveFile() {
    ArrayList < InvoiceHeader > invoices = gui.getInvoices();
    String headers = "";
    String lines = "";
    for (InvoiceHeader invoice: invoices) {
      String invCSV = invoice.getAsCSV();
      headers += invCSV;
      headers += "\n";

      for (InvoiceLine line: invoice.getLines()) {
        String lineCSV = line.getAsCSV();
        lines += lineCSV;
        lines += "\n";
      }
    }
    System.out.println("Check point");
    try {
      JFileChooser fc = new JFileChooser();
      int result = fc.showSaveDialog(gui);
      if (result == JFileChooser.APPROVE_OPTION) {
        File headerFile = fc.getSelectedFile();
        FileWriter hfw = new FileWriter(headerFile);
        hfw.write(headers);
        hfw.flush();
        hfw.close();
        result = fc.showSaveDialog(gui);
        if (result == JFileChooser.APPROVE_OPTION) {
          File lineFile = fc.getSelectedFile();
          FileWriter lfw = new FileWriter(lineFile);
          lfw.write(lines);
          lfw.flush();
          lfw.close();
        }
      }
    } catch (Exception ex) {

    }
  }

  private void newInv() {
    invoiceHeaderDialog = new InvoiceHeaderDialog(gui);
    invoiceHeaderDialog.setVisible(true);

  }

  private void delInv() {
    int selectedRow = gui.getHeaderTable().getSelectedRow();
    if (selectedRow != -1) {
      gui.getInvoices().remove(selectedRow);
      gui.getInvoiceHeadersTableModel().fireTableDataChanged();
    }
  }

  private void newItem() {
    invoiceLineDialog = new InvoiceLineDialog(gui);
    invoiceLineDialog.setVisible(true);
  }

  private void delItem() {
    int selectedRow = gui.getLineTable().getSelectedRow();

    if (selectedRow != -1) {
      InvoiceLinesTableModel linesTableModel = (InvoiceLinesTableModel) gui.getLineTable().getModel();
      linesTableModel.getLines().remove(selectedRow);
      linesTableModel.fireTableDataChanged();
      gui.getInvoiceHeadersTableModel().fireTableDataChanged();
    }
  }

  private void createInvoiceCancel() {
    invoiceHeaderDialog.setVisible(false);
    invoiceHeaderDialog.dispose();
    invoiceHeaderDialog = null;
  }

  private void createInvoiceOK() {
    String date = invoiceHeaderDialog.getInvDateField().getText();
    String customer = invoiceHeaderDialog.getCustNameField().getText();
    int num = gui.getNextInvoiceNum();
    try {
            String[] dateParts = date.split("-"); 
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(gui, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12 || year > 2100) {
                    JOptionPane.showMessageDialog(gui, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } else {

    InvoiceHeader invoice = new InvoiceHeader(num, date, customer);
    gui.getInvoices().add(invoice);
    gui.getInvoiceHeadersTableModel().fireTableDataChanged();
    invoiceHeaderDialog.setVisible(false);
    invoiceHeaderDialog.dispose();
    invoiceHeaderDialog = null;
  }
  }
  } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

  private void createLineOK() {
    String item = invoiceLineDialog.getItemNameField().getText();
    String countStr = invoiceLineDialog.getItemCountField().getText();
    String priceStr = invoiceLineDialog.getItemPriceField().getText();
    int count = Integer.parseInt(countStr);
    double price = Double.parseDouble(priceStr);
    int selectedInvoice = gui.getHeaderTable().getSelectedRow();
    if (selectedInvoice != -1) {
      InvoiceHeader invoice = gui.getInvoices().get(selectedInvoice);
      InvoiceLine line = new InvoiceLine(item, price, count, invoice);
      invoice.getLines().add(line);
      InvoiceLinesTableModel linesTableModel = (InvoiceLinesTableModel) gui.getLineTable().getModel();
      linesTableModel.fireTableDataChanged();
      gui.getInvoiceHeadersTableModel().fireTableDataChanged();
    }
    invoiceLineDialog.setVisible(false);
    invoiceLineDialog.dispose();
    invoiceLineDialog = null;
  }

  private void createLineCancel() {
    invoiceLineDialog.setVisible(false);
    invoiceLineDialog.dispose();
    invoiceLineDialog = null;
  }

  private void Exit() {
    System.exit(0);

  }

}