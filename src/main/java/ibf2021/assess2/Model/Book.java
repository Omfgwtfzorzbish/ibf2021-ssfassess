package ibf2021.assess2.Model;

public class Book {
    private String bookname;


    public String getBookname() {
        return this.bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname.trim().toLowerCase().replace(" ", "\\+");
    }
}
