package ibf2021.assess2.Model;

import java.util.List;

import jakarta.json.JsonObject;

public class Book {
    private String searchname;
    private String title;
    private String key;
    private String covers;

    public String getCovers() {
        return this.covers;
    }

    public void setCovers(String covers) {
    String cover = "https://covers.openlibrary.org/b/olid/" + covers  +"-" + "S.jpg";                              // https://covers.openlibrary.org/b/olid/OL7440033M-S.jpg
        this.covers = cover;
    }

 

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

  

    public String getSearchname() {
        return this.searchname;
    }

    public void setSearchname(String searchname) {
        this.searchname = searchname;
    }
    public static Book createTitleList(JsonObject o){
        Book w = new Book();
        w.setTitle(o.getString("title"));
        w.setKey(o.getString("key").replace("works", "book"));   //format "key": "/works/OL14926019W" to /books/
      
        return w;
    }


}
