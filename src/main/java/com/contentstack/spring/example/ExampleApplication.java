package com.contentstack.spring.example;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleApplication.class, args);
        Stack stack = Contentstack.stack("blt02f7b45378b008ee",
                "cs5b69faf35efdebd91d08bcf4", "production");
        Query query = stack.contentType("product").query();
        query.includeContentType();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entryList = queryresult.getResultObjects();
                    if (queryresult.getResultObjects().size() > 0) {
                        for (Entry entry : entryList) {
                            System.out.println("entry: " + entry.getTitle());
                        }
                    }
                } else {
                    System.out.println("Error: " + error);
                }
            }
        });
    }

}
