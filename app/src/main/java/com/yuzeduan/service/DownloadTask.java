package com.yuzeduan.service;

import android.os.AsyncTask;

import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.util.CacheUtil;
import com.yuzeduan.util.ParseJSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import static com.yuzeduan.activity.MainActivity.READING;


public class DownloadTask extends AsyncTask<String, Integer, Void> {
    private int lastProgress;
    private DownloadListener listener;


    @Override
    protected Void doInBackground(String... strings) {
        String url = strings[0];
        downloadDatas(url, 5);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if(progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    public void downloadDatas(String url, int time){
        String id = null;
        String itemId;
        String readingAddress;
        String listResponse;
        String itemResponse;
        ReadingMusicList reading;
        int total = 1;
        for(int i = 0; i < time; i++){
            if( i > 0){
                url = "http://v3.wufazhuce.com:8000/api/channel/reading/more/" +id+ "?channel=wdj&version=4.0.2&platform=android";
            }
            listResponse = getJSON(url);
            ArrayList<ReadingMusicList> list = ParseJSONUtil.parseReadingMusicList(listResponse, READING);
            reading = list.get(list.size() - 1);
            id = reading.getId();
            Iterator<ReadingMusicList> it = list.iterator();
            while (it.hasNext()){
                reading = it.next();
                itemId = reading.getmItemId();
                readingAddress = "http://v3.wufazhuce.com:8000/api/essay/" + itemId +"?platform=android";
                itemResponse = getJSON(readingAddress);
                ParseJSONUtil.parseReading(itemResponse);
                publishProgress(total * 2);
                total++;
            }
        }
        listener.onSuccess();
    }

    public String getJSON(String address){
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            CacheUtil.saveJSON(address, response.toString());  // 实现JSON数据的缓存
            return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
