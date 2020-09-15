package com.rudypark3091.api.billboardTop100.service;

import com.rudypark3091.api.billboardTop100.web.dto.ResponseDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetService {

    private String USER_AGENT = "Mozilla/5.0";
    private String TARGET_URL = "https://www.billboard.com/charts/hot-100";
    private String responseBody;

    public void getRequest(String targetUrl) throws Exception {
        URL url = new URL(targetUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", this.USER_AGENT);

        int responseCode = httpURLConnection.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("response code: " + responseCode);
        this.responseBody = response.toString();
    }

    public ArrayList<ResponseDto> getResponseDto(int rankParam) throws Exception {
        ArrayList<ResponseDto> responseDtoList = new ArrayList<ResponseDto>();
        Document document = Jsoup.parse(this.responseBody);
        for (int i = 1; i <= rankParam; i++) {
            Elements chartList = document.select("#charts > div > div.chart-list.container > ol > li:nth-child(" + i + ")");
            Elements rank = chartList.select("button > span.chart-element__rank.flex--column.flex--xy-center.flex--no-shrink > span.chart-element__rank__number");
            Elements song = chartList.select("button > span.chart-element__information > span.chart-element__information__song.text--truncate.color--primary");
            Elements artist = chartList.select("button > span.chart-element__information > span.chart-element__information__artist.text--truncate.color--secondary");

            responseDtoList.add(new ResponseDto(Integer.parseInt(rank.text()), artist.text(), song.text()));
        }
        return responseDtoList;
    }

    public ArrayList<ResponseDto> findAllSongs(int rankParam) throws Exception {
        this.getRequest(this.TARGET_URL);
        ArrayList<ResponseDto> responseDtoList = this.getResponseDto(rankParam);

        return responseDtoList;
    }
}
