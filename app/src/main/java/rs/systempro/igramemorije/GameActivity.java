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
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static rs.systempro.igramemorije.R.id.glMreza;

public class GameActivity extends AppCompatActivity {

    int h;
    int v;
    ArrayList<Drawable> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent myIntent = getIntent();
        h= myIntent.getIntExtra("height", 3);
        v = myIntent.getIntExtra("width",3);

        images = new ArrayList<>();
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
        rlRoot.post(new Runnable() {
            @Override
            public void run() {
                int rootH = rlRoot.getHeight();
                int rootW = rlRoot.getWidth();

                Toast.makeText(getApplicationContext(), rootH + " - " + rootW, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < h * v; i++) {
                    ImageButton b = new ImageButton(getApplicationContext());



                    b.setBackgroundColor(Color.RED);
                    b.setImageDrawable(images.get(i));
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
                }


                 }
        });


    }


}
