
package sig.model;

import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader {
    private int num;
    private String customer;
    private Date date;
    private ArrayList<InvoiceLine> lines;

    public InvoiceHeader(int num, String customer, Date date) {
        this.num = num;
        this.customer = customer;
        this.date = date;
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

    public ArrayList<InvoiceLine> getLines() {
        return lines;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
    
    
}
