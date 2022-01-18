package ibf2021.assess2.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2021.assess2.Model.Book;
import ibf2021.assess2.Model.BookDetails;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    
    public List<Book> getBookTittleId(String bookname){
       // List<Book> list = new ArrayList<>(); logger.info(bookname);
        final String url = UriComponentsBuilder
            .fromUriString("http://openlibrary.org/search.json")
            .queryParam("title", bookname)
            .queryParam("limit", "20")
            .toUriString(); logger.info(url);

            final RequestEntity<Void> req = RequestEntity.get(url).build();
            final RestTemplate template = new RestTemplate();
            final ResponseEntity<String> resp = template.exchange(req,String.class);

            if (resp.getStatusCode() != HttpStatus.OK)
            throw new IllegalArgumentException("Error: %s".formatted(resp.getStatusCode().toString()));
            final String body = resp.getBody();

            //logger.info("payload:%s".formatted(body));

            try{ InputStream is = new ByteArrayInputStream(body.getBytes());
                final JsonReader reader = Json.createReader(is);
                final JsonObject result = reader.readObject();
                final JsonArray readings = result.getJsonArray("docs"); //contains main, description and icon
                
                return readings.stream()
                    .map(v -> (JsonObject)v)
                    .map(Book::createTitleList)
                    .collect(Collectors.toList());

            }catch (Exception e) {
                logger.error("error in json bookservice");
            }
            return Collections.EMPTY_LIST;
    }

    public List<BookDetails> getBookDetails(String id){
       
        final String url = UriComponentsBuilder
            .fromUriString("https://openlibrary.org/"+ id)  //id is /works/{id}
            .toUriString(); 
            
            logger.info(id); System.out.println(id);

            final RequestEntity<Void> req = RequestEntity.get(url).build();
            final RestTemplate template = new RestTemplate();
            final ResponseEntity<String> resp = template.exchange(req,String.class);

            if (resp.getStatusCode() != HttpStatus.OK)
            throw new IllegalArgumentException("Error: %s".formatted(resp.getStatusCode().toString()));
            final String body = resp.getBody();

            //logger.info("payload:%s".formatted(body));

            try{ InputStream is = new ByteArrayInputStream(body.getBytes());
                final JsonReader reader = Json.createReader(is);
                final JsonObject result = reader.readObject();
                final JsonArray readings = result.getJsonArray("excerpts"); //contains main, description and icon
                
                return readings.stream()
                    .map(v -> (JsonObject)v)
                    .map(BookDetails::createExcerptList)//creates bookdetails objects with excerpts for each excerpt
                    .map(w -> {         //for each excerpt add the description
                        w.setDescription("description");       
                        return w;               //RETURNS A bookdetails bject WITH multiple excerpts with description
                    })
                    .collect(Collectors.toList()); 

            }catch (Exception e) {
                logger.error("error in json bookservice");
            }
            return Collections.EMPTY_LIST;
    }
}
