package rs.systempro.igramemorije;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import static rs.systempro.igramemorije.R.id.glMreza;

public class GameActivity extends AppCompatActivity {

    int solve;
    ArrayList<Drawable> images;
    ImageButton firstOpened;
    ImageButton secondOpened;
    int numOfOpened;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent myIntent = getIntent();
        final int h= myIntent.getIntExtra("height", 3);
        final int v = myIntent.getIntExtra("width",3);

        images = new ArrayList<>();
        solve = 0;
        numOfOpened = 0;
       for(int i=0;i<(h*v)/2;i++)
       {

           int resid =  getResources().getIdentifier("slicica"+(i+1),"drawable",getPackageName());

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               images.add(getDrawable(resid));
               images.add(getDrawable(resid));
           }
           else {
               images.add(getResources().getDrawable(resid));
               images.add(getResources().getDrawable(resid));


           }
           Collections.shuffle(images);
       }

        final RelativeLayout rlRoot = (RelativeLayout)findViewById(R.id.rlRoot);
        final GridLayout glMreza = (GridLayout) findViewById(R.id.glMreza);


        glMreza.setColumnCount(v);

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;
            }
        },1000,1000);


        rlRoot.post(new Runnable() {
            @Override
            public void run() {
                int rootH = rlRoot.getHeight();
                int rootW = rlRoot.getWidth();

                Toast.makeText(getApplicationContext(), rootH + " - " + rootW, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < h * v; i++) {
                    final ImageButton b = new ImageButton(getApplicationContext());
                    final Drawable image = images.get(i);


                    b.setBackgroundColor(Color.RED);
                    //b.setImageDrawable(images.get(i));
                    b.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    b.setPadding(0,0,0,0);

                    glMreza.addView(b);
                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    if(rootH/h <rootW/v) {
                        param.height = rootH / h - 20;
                        param.width = rootH / h -20;
                    }
                    else {
                        param.width = rootW / v - 20;
                        param.height = rootW / v- 20;
                    }
                    param.setMargins(10,10,10,10);
                    b.setLayoutParams(param);

                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //b.setImageDrawable(image);
                            if(numOfOpened == 2)
                            {
                                numOfOpened=numOfOpened-1;
                                //b.setImageDrawable(image);
                                firstOpened.setImageDrawable(null);
                                secondOpened.setImageDrawable(null);
                                b.setImageDrawable(image);
                                firstOpened = b;
                            }
                            else if(numOfOpened == 1) {

                                b.setImageDrawable(image);
                                numOfOpened++;
                                secondOpened = b;
                                b.setImageDrawable(image);
                                if(firstOpened.getDrawable().equals(secondOpened.getDrawable()))
                                {
                                    numOfOpened = 0;
                                    firstOpened.setClickable(false);
                                    secondOpened.setClickable(false);

                                }
                            }
                            else
                            {
                                numOfOpened = 1;
                                firstOpened = b;
                            }


                        }
                    });

                }


                 }
        });


    }


}
