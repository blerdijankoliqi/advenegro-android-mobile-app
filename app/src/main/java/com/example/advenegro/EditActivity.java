package com.example.advenegro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private ImageView menuImage;
    private ImageView footerImage;
    private ImageButton logoutIconButton;
    private ImageButton backIconButton;
    private TextView editTitle;
    private EditText titleEditText;
    private EditText locationEditText;
    private EditText shortDescriptionEditText;
    private EditText aboutEditText;
    private EditText longitudeEditText;
    private EditText latitudeEditText;
    private Button updateButton;
    private Button chooseImageButton;
    private ImageView selectedImageImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        menuImage = (ImageView) findViewById(R.id.menu_image);
        logoutIconButton = (ImageButton) findViewById(R.id.logout_icon_button_addNew);
        backIconButton = (ImageButton) findViewById(R.id.back_icon_button_addNew);
        editTitle = (TextView) findViewById(R.id.title_maps);
        titleEditText = (EditText) findViewById(R.id.searchField);
        locationEditText = (EditText) findViewById(R.id.LocationEditText_addNew);
        shortDescriptionEditText = (EditText) findViewById(R.id.ShortDescriptionEditText_addNew);
        aboutEditText = (EditText) findViewById(R.id.AboutEditText_addNew);
        longitudeEditText = (EditText) findViewById(R.id.LongitudeEditText_addNew);
        latitudeEditText = (EditText) findViewById(R.id.LatitudeEditText_addNew);
        updateButton = (Button) findViewById(R.id.create_button_addNew);
        selectedImageImageView = (ImageView) findViewById(R.id.selected_image_addNew);
        chooseImageButton = (Button) findViewById(R.id.choose_image_button_addNew);
        footerImage = (ImageView) findViewById(R.id.footer_image);
    }
}