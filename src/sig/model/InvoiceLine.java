package sig.model;

public class InvoiceLine {
    private InvoiceHeader invoice;
    private String name;
    private double price;
    private int count;
   
   
    public InvoiceLine(InvoiceHeader invoice, String name, int count) {
        this.invoice = invoice;
        this.name = name;
        this.count = count;
    }

    public InvoiceLine(String item, double price, int Count, int invNum) {
    }

    public InvoiceLine(int invoice, String name, double price, int Count) {
    }

    public InvoiceLine(String invoice, double price, int count, InvoiceHeader invoiceHeader) {
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public double getLineTotal(){
       return price*count;
   }
    
    
    
    
    

    public int getCount() {
        return count;
        
        
        
        
        
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    

@Override
    public String toString() {
        return "InvoiceLine{" + "Invoice Number=" + invoice.getNum() + ", Item Name" + name + ", Price=" + price + ", Count=" + count + '}';
    }

  //  public InvoiceHeader getInvoice() {
  //      return invoice;
  //  }
    
    
  public String getAsCSV(){
       return invoice.getNum() + "," + name + "," + price + "," + count;
   }  
    
    
}