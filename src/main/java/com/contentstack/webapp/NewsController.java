package com.contentstack.webapp;

import com.contentstack.sdk.Error;
import com.contentstack.sdk.*;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class NewsController {




    @GetMapping("/headline")
    public String headline(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        //model.addAttribute("name", name);

        ArrayList<NewsModel> listOfHeadlines = getNewsHeadlines();
        if (listOfHeadlines == null || listOfHeadlines.size() == 0) {
            model.addAttribute("headlineDTO", "Could not fetch Headlines..");
        } else {
            model.addAttribute("headlineDTO", getNewsHeadlines());
        }
        return "headline";
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
                            JSONObject url = response.optJSONObject("thumbnail");
                            String imageUrl = url.optString("url");
                            newsHeadlines.add(new NewsModel(response.optString("title"),
                                    response.optString("body"), imageUrl));
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