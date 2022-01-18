package ibf2021.assess2.Model;

import jakarta.json.JsonObject;

public class BookDetails {
    private String title;
    private String description;
    private String excerpt;
    private String cover;

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExcerpt() {
        return this.excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public static BookDetails createExcerptList(JsonObject o){
        BookDetails w = new BookDetails();
        w.setExcerpt(o.getString("excerpt"));
          //format "key": "/works/OL14926019W" to /books/
        return w;
    }

}
