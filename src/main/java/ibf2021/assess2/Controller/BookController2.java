package ibf2021.assess2.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ibf2021.assess2.Model.BookDetails;
import ibf2021.assess2.Service.BookService;
import ibf2021.assess2.Service.RepoService;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller

public class BookController2 {
    private static final Logger logger = LoggerFactory.getLogger(BookController2.class);

    @Autowired 
    private BookService bookService;

    @Autowired
    private RepoService repoService;



    @GetMapping("/book/{id}")
    public String showBook(Model model, @PathVariable(value="id") String bookId){
    BookDetails onebook = new BookDetails();
    Optional<String> opt = repoService.getCache(bookId.trim().toLowerCase());
    
    if(opt.isPresent()){
        onebook.setIscached("is cached");
        String[] arr = opt.get().split("/");
        onebook.setTitle(arr[1]);   onebook.setDescription(arr[2]);
        onebook.setExcerpt(arr[3]); onebook.setCover(arr[4]);
        }else{
            try {
                onebook = bookService.getBookDetails(bookId);
                repoService.saveToRedis(bookId, onebook.getTitle(), onebook.getExcerpt(), onebook.getDescription(), onebook.getCover());
            
          
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        
        String excerpt = onebook.getExcerpt();
        String description = onebook.getDescription();
        String title = onebook.getTitle();
        String iscached = onebook.getIscached();
        String cover = onebook.getCover();
      

        model.addAttribute("excerpt", excerpt);
        model.addAttribute("description", description);
        model.addAttribute("title", title);
        model.addAttribute("iscached", iscached);
        model.addAttribute("onebook", onebook);
        model.addAttribute("cover", cover);
        
        return "showbook";
    }

   
}