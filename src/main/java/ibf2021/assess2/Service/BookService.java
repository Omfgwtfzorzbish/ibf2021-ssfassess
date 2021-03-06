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

    public BookDetails getBookDetails(String id){
        BookDetails onebook = new BookDetails();
        String s ="https://openlibrary.org/works/" + id +".json"; logger.info(s);
        final String url = UriComponentsBuilder
            .fromUriString(s)  //id is /works/{id}
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
                onebook.setTitle(result.getString("title"));
                if (result.containsKey("description"))
                    {try {onebook.setDescription(result.getString("description"));}
                     catch (Exception e) {
                    }
                    try {onebook.setDescription(result.getJsonObject("description").getString("value"));}
                     catch (Exception e) {
                    }
                }

                if (result.containsKey("excerpt"))
                    {try {onebook.setExcerpt(result.getString("excerpt"));}
                    catch (Exception e) {
                    }
                }

                if(result.containsKey("excerpts")){
                    JsonArray excerpts = result.getJsonArray("excerpts");
                    List<JsonObject> excerptlist = 
                    excerpts.stream().map(v ->(JsonObject)v).collect(Collectors.toList());
                    onebook.setExcerpt(excerptlist.get(0).getString("excerpt"));
                }
                if(result.containsKey("cover")){
                    try {onebook.setCover(result.getString("cover"));}
                    catch (Exception e) {
                    }
                }
                if(result.containsKey("covers")){
                    JsonArray covers = result.getJsonArray("covers");
                    List<JsonObject> coverlist = 
                    covers.stream().map(v ->(JsonObject)v).collect(Collectors.toList());
                    coverlist.get(0).getString("covers");
                    String coverurl = "https://covers.openlibrary.org/b/olid/" + id +"-" +"M.jpg";
                    onebook.setCover(coverurl);
                                                    //https://covers.openlibrary.org/b/olid/OL7440033M-S.jpg
                }
                

            }catch (Exception e) {
                logger.error("error in json bookservice"); e.printStackTrace();
            }
            return onebook;
    }
}
