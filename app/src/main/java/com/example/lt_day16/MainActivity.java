package com.example.lt_day16;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lt_day16.adapter.MusicAdapter;
import com.example.lt_day16.callback.OnMusicItemClickListener;
import com.example.lt_day16.model.Music;
import com.example.lt_day16.ultil.MusicParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements OnMusicItemClickListener {
    private RecyclerView rvMusics;
    private MusicAdapter adapter;
    private List<Music> arrMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrMusic = new ArrayList<>();
//        arrMusic.add(new Music("aca", "asc", "asd", "album_art.jpg"));
//        arrMusic.add(new Music("asd", "fa", "ac", "album_art.jpg"));
//        arrMusic.add(new Music("asd", "asd", "wq", "album_art.jpg"));
//        arrMusic.add(new Music("wqe", "aq", "fas", "album_art.jpg"));
        adapter = new MusicAdapter(this, arrMusic,this);
        rvMusics = findViewById(R.id.rv_music);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMusics.setLayoutManager(layoutManager);
        rvMusics.setAdapter(adapter);
        new DownloadMusicTask().execute("https://storage.googleapis.com/automotive-media/music.json");
    }

    @Override
    public void onMusicItemClick(int position) {

    }

    class DownloadMusicTask extends AsyncTask<String, Void, List<Music>> {
        @Override
        protected void onPostExecute(List<Music> music) {
            super.onPostExecute(music);
            arrMusic.clear();
            arrMusic.addAll(music);
            adapter.notifyDataSetChanged();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Start Parsing", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected List<Music> doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                return MusicParser.getInstance().parseData(builder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}