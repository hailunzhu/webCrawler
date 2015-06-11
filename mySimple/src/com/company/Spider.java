package com.company;

import java.io.*;
import java.net.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by hailunzhu on 6/10/15.
 */
public class Spider {

    private static final int MAX_PAGE_TO_VISIT= 10;
    private static Set<String> pagesVisited = new HashSet<String>();
    private static List<String> pagesToVisit = new LinkedList<String>();

    private static logger log = new logger();

    private final String USER_AGENT = "Mozilla/5.0";

    private static Set<String> resultSet = new HashSet<String>();

    /**
     * find the nextURL
     * @return nextURL string
     */
    public String nextURL(){
        String nextURL = null;

        do {
            if (pagesToVisit.isEmpty()){
                log.error("No more links existed!");
                break;
            }
            nextURL = pagesToVisit.remove(0);
        }while(pagesVisited.contains(nextURL));

        return nextURL;
    }

    public void start(String startUrl, String keyWord) throws IOException {

        log.step("start web crawler");

        String url = null;
        while(this.pagesVisited.size() <= MAX_PAGE_TO_VISIT){

            // get next url
            if (this.pagesVisited.isEmpty()){
                url = startUrl;
            }else{
                url = nextURL();
            }

            this.pagesVisited.add(url);

            if (url == null || url.length()==0){
                log.error("No valid url!");
                log.info("No result found!");
                return;
            }

            search(url, keyWord);
        }

        if (this.resultSet.isEmpty()){
            log.info("No result found!");
        }else{
            for (String s : this.resultSet){
                System.out.println(s);
            }
        }
    }

    public void search(String startUrl, String keyWord) {

        log.info("Search for " + startUrl);
        log.info("Key word: " + keyWord);

        if (!startUrl.startsWith("http")){
            startUrl = "http://"+startUrl;
        }

//        URL url = new URL(startUrl);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Aagent", USER_AGENT);
//        int responseCode = con.getResponseCode();
//        System.out.println("Response Code : " + responseCode);
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//        String line;
//        StringBuffer res = new StringBuffer();
//        while((line= in.readLine()) != null){
//            res.append(line);
//        }
//        in.close();
//
//        System.out.println(res.toString());


        log.info(startUrl);
        Connection connection = Jsoup.connect(startUrl);
        Document htmlDoc = null;
        try {
            htmlDoc = connection.get();
        } catch (IOException e) {
           //  e.printStackTrace();
            log.error(e.toString());

        }
        Elements links = htmlDoc.select("a[href]");

       // log.print(htmlDoc.toString());
        // get links
        this.pagesToVisit.addAll(getLinks(links));

        if (findWord(htmlDoc,keyWord)){
            this.resultSet.add(startUrl);
        }

    }


    public boolean findWord(Document htmlDoc, String word){

        Elements all = htmlDoc.getAllElements();
        for (Element e : all){
            if (e.data().contains(word))
                return true;
        }
        return false;
    }

    public List<String> getLinks(Elements linksPage){

        String url;
        List<String> links = new LinkedList<String>();
        for (Element l : linksPage){
        //    log.print(l.toString());
            url = l.absUrl("href");
            if (!url.isEmpty())
                links.add(l.absUrl("href"));
        }

        log.print(links.toString());
        return links;
    }










}
