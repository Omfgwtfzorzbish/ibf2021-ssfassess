package ibf2021.assess2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ibf2021.assess2.Model.Book;
import ibf2021.assess2.Service.BookService;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    
    @Autowired BookService bookService;


    Book book = new Book();
    @GetMapping("/")
    public String showForm( Model model) {
       model.addAttribute("book", book);
        return "booklanding";
    }

    @PostMapping("/booksearch")
    public String search(@ModelAttribute Book book, Model model){
   
        List<Book> booklist = Collections.EMPTY_LIST;
        
            booklist = bookService.getBookTittleId(book.getSearchname());


        model.addAttribute("booklist", booklist);
        model.addAttribute("book", book);
        return "booksearch";
    }
}
