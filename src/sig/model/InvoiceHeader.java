
package sig.model;

import java.util.ArrayList;
import java.util.Date;
import sig.model.InvoiceLine;

public class InvoiceHeader {
    private int num;
    private String customer;
    private Date date;
    private ArrayList<InvoiceLine> lines;

    public InvoiceHeader(int num, String customer, Date date ) {
        this.num = num;
        this.customer = customer;
        this.date = date;
    }

    public InvoiceHeader(int num, String date, String Customer) {
    }

    
    public double getInvoiceHeaderTotal(){
        double total=0.0;
        for(InvoiceLine invoiceline : getLines()){
            total += invoiceline.getLineTotal();
        }
        
        return total;
        }


    public ArrayList<InvoiceLine> getLines() {
        if (lines == null){
            lines = new ArrayList<>();
        }
        return lines;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


//    public void setLines (ArrayList<InvoiceLine> lines) {
//        this.lines = lines;
//    }
    
    
    @Override
    public String toString() {
        return "InvoiceHeader{" + "Number=" + num + ", Date=" + date + ", Customer=" + customer + '}';
    }

    
    

   public String getAsCSV(){
       return num + "," + date + "," +customer;
   }
}
