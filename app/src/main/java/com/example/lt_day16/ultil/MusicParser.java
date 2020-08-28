package com.example.lt_day16.ultil;

import com.example.lt_day16.model.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MusicParser {
    private MusicParser() {

    }

    private static MusicParser INSTANCE;

    public static MusicParser getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MusicParser();
        }
        return INSTANCE;
    }

    public List<Music> parseData(String json) {
        List<Music> result = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(json);
            JSONArray musicArray = rootObject.getJSONArray("music");
            for (int i = 0;i< musicArray.length();i++){
                JSONObject item = musicArray.getJSONObject(i);//get item o vi trí thứ i
                String title = item.getString("title");
                String album = item.getString("album");
                String source = item.getString("source");
                String image = item.getString("image");
                result.add(new Music(title,album,source,image));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
