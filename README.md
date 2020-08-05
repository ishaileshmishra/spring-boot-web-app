# Contentstack JavaSDK Using Spring

A Sample Example Code for contentstack Java using Spring

Step: 1 Open pom.xml

add dependency:

```
<dependency>
   <groupId>com.contentstack.sdk</groupId>
   <artifactId>java</artifactId>
   <version>1.5.3</version>
</dependency>
```

Open ExampleApplication.java containing main class, add below contentstack snippet

```
@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleApplication.class, args);
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
```

![Screenshot](https://github.com/ishaileshmishra/example/blob/master/snapshot.png?raw=true)


