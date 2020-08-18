package com.contentstack.spring.example;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.*;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);

        // Main call from where app starts to run.

        System.out.println("News Headlines: "+new ExampleApplication().getNewsHeadlines());
        System.out.println("News Products: "+new ExampleApplication().getProductList());
    }


    private ArrayList<NewsModel> getNewsHeadlines() {

        try {
            ArrayList<NewsModel> newsHeadlines = new ArrayList<>();
            final Stack stack = Contentstack.stack("blt7979d15c28261b93", "cs17465ae5683299db9d259cb6", "production");
            ContentType contentType = stack.contentType("news");
            Query query = contentType.query();
            query.find(new QueryResultsCallBack() {
                @Override
                public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                    if (error == null) {
                        List<Entry> result = queryresult.getResultObjects();
                        for (Entry entry : result) {
                            JSONObject response = entry.toJSON();
                            newsHeadlines.add(new NewsModel(response.optString("title"), response.optString("body"), ""));
                        }
                    }
                }
            });
            return newsHeadlines;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    private ArrayList<String> getProductList() {


        try {

            ArrayList<String> allProducts = new ArrayList<>();
            Stack stack = Contentstack.stack("blt02f7b45378b008ee", "cs5b69faf35efdebd91d08bcf4", "production");
            Query query = stack.contentType("product").query();
            query.includeContentType();
            query.find(new QueryResultsCallBack() {
                @Override
                public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                    if (error == null) {
                        List<Entry> entryList = queryresult.getResultObjects();
                        if (queryresult.getResultObjects().size() > 0) {
                            for (Entry entry : entryList) {
                                JSONObject response = entry.toJSON();
                                allProducts.add(response.optString("title"));
                            }
                        }
                    }
                }
            });
            return allProducts;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
