
package sig.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.util.Date;
import java.util.Vector;


public class ActionHandler extends Component implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
       System.out.println("Action Handling Called");
    switch(e.getActionCommand()) {
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
            JFileChooser Lf1 = new JFileChooser();
            JFileChooser Lf2 = new JFileChooser();
            int result1 = Lf1.showOpenDialog(this);
            int result2 = Lf2.showOpenDialog(this);
            if (result1 == JFileChooser.APPROVE_OPTION) {
                File f = Lf1.getSelectedFile();
                String filepath = f.getPath();
                String fi = f.getName();
                //Parsing CSV Data
                System.out.print(filepath);

                DefaultTableModel csv_data = new DefaultTableModel();

                try {

                    int start = 1;
                    InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filepath));
                    org.apache.commons.csv.CSVParser csvParser = CSVFormat.DEFAULT.parse(inputStreamReader);
                    for (CSVRecord csvRecord : csvParser) {
                        if (start == 0) {
                            start = 1;
                            csv_data.addColumn(csvRecord.get(0));
                            csv_data.addColumn(csvRecord.get(1));
                            csv_data.addColumn(csvRecord.get(2));

                        } else {
                            Vector row = new Vector();
                            row.add(csvRecord.get(0));
                            row.add(csvRecord.get(1));
                            row.add(csvRecord.get(2));
                            csv_data.addRow(row);
                        }
                    }

                } catch (Exception ex) {
                    System.out.println("Error in Parsing CSV File");
                }
               // headerTable.setModel(csv_data);
            }


                /*
                if (result2 == JFileChooser.APPROVE_OPTION) {
                    String path2 = Lf2.getSelectedFile().getPath();
                    FileInputStream InvoiceLine = null;

                    try {
                        InvoiceLine = new FileInputStream(path2);
                        int size = InvoiceLine.available();
                        byte[] c = new byte[size];
                        InvoiceLine.read(c);
                        headerTable.Lf2(new String(c));
                    } catch (FileNotFoundException f) {
                        f.printStackTrace();
                    } catch (IOException x) {
                        x.printStackTrace();
                    } finally {
                        try {
                            InvoiceHeader.close();
                        } catch (IOException t) {
                        }
                    }
                }
            }

                 */
        break;
        
        
        case "Save File":
        System.out.println("Save File");
        saveFile();
        break;
        
        
        case "Exit":
        System.out.println("Exit");
        System.exit(0);
        break;
       
    }
    
    
    }

    private void newInv() {
        
    }

    private void delInv() {
       
    }

    private void newItem() {
        
    }

    private void delItem() {
        
    }

    private void loadFile() {
        
    
    }
    private void saveFile() {
        
    }
}
