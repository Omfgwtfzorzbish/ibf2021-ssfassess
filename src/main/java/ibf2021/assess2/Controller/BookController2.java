package ibf2021.assess2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ibf2021.assess2.Model.BookDetails;
import ibf2021.assess2.Service.BookService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BookController2 {
    @Controller
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired BookService bookService;


    @GetMapping("/book/{id}/")
    public String showBook(Model model, @PathVariable(value="id") String bookId){
        bookId =bookId.replace("book","works");
        List<BookDetails> bookdlist = Collections.EMPTY_LIST;

        // bookdlist =bookService.getBookDetails(bookId);

        List<String> excerptlist = new ArrayList<String>();
        List<String> descriptionlist = new ArrayList<String>();
        // for(BookDetails b:bookdlist){
            // excerptlist.add(b.getExcerpt());
            // descriptionlist.add(b.getDescription());}

        model.addAttribute("excerptlist", excerptlist);
        model.addAttribute("descriptionlist", descriptionlist);

        
        return "showbook";
    }

   
}

}
