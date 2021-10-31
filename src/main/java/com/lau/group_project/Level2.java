package com.nhk.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Level2 extends AppCompatActivity {

    Button option1, option2, option3, option4;
    LinkedList<String> images,names,options;
    int count;
    int score;
    ImageView image;
    String right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        images = new LinkedList<>();
        names = new LinkedList<>();
        options = new LinkedList<>();
        count = 0;
        score = 0;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);

        image = (ImageView) findViewById(R.id.appImage);
        option1 = findViewById(R.id.guess1);
        option2 = findViewById(R.id.guess2);
        option3 = findViewById(R.id.guess3);
        option4 = findViewById(R.id.guess4);


        try {
//            DownloadTask dt = new DownloadTask();
//            String s = download();
            URL google = new URL("https://www.pcmag.com/picks/best-android-apps");
            BufferedReader in = new BufferedReader(new InputStreamReader(google.openStream()));
            String input;
            StringBuffer stringBuffer = new StringBuffer();
            while ((input = in.readLine()) != null) {
                stringBuffer.append(input);
            }
            in.close();


            String s = stringBuffer.toString();

            getImagesFromDownloadedURL(s);
            getNamesFromDownloadedURL(s);
            rrandomm();

            btnSetOnClickListener(option1);
            btnSetOnClickListener(option2);
            btnSetOnClickListener(option3);
            btnSetOnClickListener(option4);


        } catch (Exception e) {
            Log.e("Fetching", "Error Fetching https://www.pcmag.com/picks/best-android-apps");
            e.printStackTrace();
        }

    }

    public void btnSetOnClickListener(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCorrectAnswer(btn);
            }
        });
    }

    public void checkCorrectAnswer(Button btn){
        if(btn.getText().toString().equalsIgnoreCase(right)){
            rightAnswer();
            rrandomm();
        }else{
            wrongAnswer();
            rrandomm();
        }
    }

    public void getImagesFromDownloadedURL(String s){
        String regex = "\"(https://i.pcmag.com/imagery/(collection-group-product|roundup-products).*?)\"";
        Matcher m = Pattern.compile(regex).matcher(s);
        while (m.find()) {
            String src = m.group(1);
            images.add(src);
        }
    }

    public void getNamesFromDownloadedURL(String s){
        String regex = "alt=\"(.*?) Image\"";
        Matcher m = Pattern.compile(regex).matcher(s);
        int count = 0;
        while(m.find()){
            String src = m.group(1);
            names.add(src);
            count++;
            if(count==103)
                break;
        }
    }

    public String download(){
        return new DownloadTask().doInBackground("https://www.pcmag.com/picks/best-android-apps");
    }




    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    public void stopGame(){
        option1.setOnClickListener(null);
        option2.setOnClickListener(null);
        option3.setOnClickListener(null);
        option4.setOnClickListener(null);
    }

    public void shuffleOptions(){
        Random rand = new Random();
        int n = rand.nextInt(options.size());
        option1.setText(options.remove(n));
        n = rand.nextInt(options.size());
        option2.setText(options.remove(n));
        n = rand.nextInt(options.size());
        option3.setText(options.remove(n));
        n = rand.nextInt(options.size());
        option4.setText(options.remove(n));
    }

    public void rrandomm(){
        if(count==103){
            stopGame();
        }

        count++;
        Random rand = new Random();
        int first = rand.nextInt(names.size());
        int second = first;
        image.setImageBitmap(getBitmapFromURL(images.get(first)));
        right = names.get(second);
        options.add(right);
        while(options.size()<4){
            int N =rand.nextInt(names.size());
            if(!names.get(N).equalsIgnoreCase(right))
                options.add(names.get(N));
        }
        shuffleOptions();
    }

    public void rightAnswer(){
        score+=2;
        Log.i("correctness", "right app");
    }
    public void wrongAnswer(){
        score = Math.max(score-1,0);
        Log.i("correctness", "wrong app");
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;
            try{
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while(data!=-1){
                    char current = (char) data;
                    result += current;
                    data=reader.read();
                }

                in.close();
                reader.close();
                return result;
            }catch(Exception e){
                e.printStackTrace();
                return "Failed";
            }

        }}

}
