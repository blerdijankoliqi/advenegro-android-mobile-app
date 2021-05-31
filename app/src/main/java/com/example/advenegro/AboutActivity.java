package com.example.advenegro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private ImageView menuImage;
    private ImageButton loginIconButton;
    private ImageButton homeButton;
    private ImageButton aboutButton;
    private ImageButton exploreButton;
    private ImageView footerImage;
    private TextView ourHomeTitleLoginActivity;
    private ImageView firstImage;
    private ImageView secondImage;
    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        menuImage = (ImageView) findViewById(R.id.menu_image);
        loginIconButton = (ImageButton) findViewById(R.id.login_icon_button);
        ourHomeTitleLoginActivity = (TextView) findViewById(R.id.title_maps);
        homeButton = (ImageButton) findViewById(R.id.imageButton3);
        aboutButton = (ImageButton) findViewById(R.id.imageButton2);
        exploreButton = (ImageButton) findViewById(R.id.imageButton4);
        footerImage = (ImageView) findViewById(R.id.footer_image);
        firstImage = (ImageView) findViewById(R.id.imageViewOne);
        secondImage = (ImageView) findViewById(R.id.imageViewTwo);
        firstText = (TextView) findViewById(R.id.first_textView);
        secondText = (TextView) findViewById(R.id.second_textView);
        thirdText = (TextView) findViewById(R.id.third_textView);
    }
}