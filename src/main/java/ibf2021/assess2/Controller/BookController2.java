package ibf2021.assess2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Controller

public class BookController2 {
    private static final Logger logger = LoggerFactory.getLogger(BookController2.class);

    @Autowired BookService bookService;


    @GetMapping("/book/{id}")
    public String showBook(Model model, @PathVariable(value="id") String bookId){
       
        BookDetails onebook = bookService.getBookDetails(bookId);

        String excerpt = onebook.getExcerpt();
        String description = onebook.getDescription();
        String title = onebook.getTitle();

        model.addAttribute("excerpt", excerpt);
        model.addAttribute("description", description);
        model.addAttribute("title", title);
        model.addAttribute("onebook", onebook);
        
        return "showbook";
    }

   
}