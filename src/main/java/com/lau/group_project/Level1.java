package com.nhk.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Level1 extends AppCompatActivity {

    Button option1, option2, option3, option4;
    LinkedList<String> images,names,options;
    int count;
    ImageView image;
    TextView imgnry_timer;
    String right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        images = new LinkedList<>();
        names = new LinkedList<>();
        options = new LinkedList<>();
        count = 0;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        imgnry_timer = (TextView) findViewById(R.id.txt_timer);
        image = (ImageView) findViewById(R.id.appImage);
        option1 = findViewById(R.id.guess1);
        option2 = findViewById(R.id.guess2);
        option3 = findViewById(R.id.guess3);
        option4 = findViewById(R.id.guess4);


        try {

            URL google = new URL("https://www.pcmag.com/picks/best-android-apps");
            BufferedReader in = new BufferedReader(new InputStreamReader(google.openStream()));
            String input;
            StringBuffer stringBuffer = new StringBuffer();
            while ((input = in.readLine()) != null) {
                stringBuffer.append(input);
            }
            in.close();


            String s = stringBuffer.toString();

            String regex = "http(s?)://([\\w-]+\\.)+[\\w-]+(/[\\w- ./]*)+\\.(?:[gG][iI][fF]|[jJ][pP][gG]|[jJ][pP][eE][gG]|[pP][nN][gG]|[bB][mM][pP])";
            Matcher m = Pattern.compile(regex).matcher(s);
            String fulldata = "";
            while (m.find()) {
                String src = m.group();
                int startIndex = src.indexOf("src=") + 1;
                String srcTag = src.substring(startIndex, src.length());
                fulldata+= srcTag+"\n";
                images.add(srcTag);
            }


            String regex2 = "<h2 class=\"order-last md:order-first font-bold font-brand text-lg md:text-xl leading-normal w-full\">(.*?)</h2>";
            Matcher m2 = Pattern.compile(regex2).matcher(s);
            String fulldata2 = "";
            int count = 0;
            while(m2.find()){
                String src = m2.group();
                int startIndex = src.indexOf(">") + 1;
                int endIndex = src.indexOf("</h2>");
                String srcTag = src.substring(startIndex, endIndex);
                names.add(srcTag);
            }
            rrandomm();

            option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(option1.getText().toString().equalsIgnoreCase(right)){
                        rightAnswer();
                        rrandomm();
                    }else{
                        wrongAnswer();
                    }
                }
            });
            option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(option2.getText().toString().equalsIgnoreCase(right)){
                        rightAnswer();
                        rrandomm();
                    }else{
                        wrongAnswer();
                    }
                }
            });
            option3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(option3.getText().toString().equalsIgnoreCase(right)){
                        rightAnswer();
                        rrandomm();
                    }else{
                        wrongAnswer();
                    }
                }
            });
            option4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(option4.getText().toString().equalsIgnoreCase(right)){
                        rightAnswer();
                        rrandomm();
                    }else{
                        wrongAnswer();
                    }
                }
            });

        } catch (Exception e) {
            Log.e("Fetching", "Error Fetching https://www.pcmag.com/picks/best-android-apps");
            e.printStackTrace();
        }

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

    public void rrandomm(){
        if(count==103){
            imgnry_timer.setText("GameOver");
            option1.setOnClickListener(null);
            option2.setOnClickListener(null);
            option3.setOnClickListener(null);
            option4.setOnClickListener(null);
        }
        count++;
        Random rand = new Random();
        int first = rand.nextInt(names.size())+12;
        int second = first-12;
        image.setImageBitmap(getBitmapFromURL(images.get(first)));
        right = names.get(second);
        options.add(right);
        Log.v("all options",options.toString());
        while(options.size()<4){
            int N =rand.nextInt(names.size());
            if(!names.get(N).equalsIgnoreCase(right))
                options.add(names.get(N));
            Log.v("all options",options.toString());
        }
        int n = rand.nextInt(options.size());
        option1.setText(options.remove(n));
        n = rand.nextInt(options.size());
        option2.setText(options.remove(n));
        n = rand.nextInt(options.size());
        option3.setText(options.remove(n));
        option4.setText(options.remove(0));
    }

    public void rightAnswer(){
        Log.i("correctness", "right app");
    }
    public void wrongAnswer(){
        Log.i("correctness", "wrong app");
    }


}
