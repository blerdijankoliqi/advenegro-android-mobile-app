package com.example.advenegro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditActivity extends AppCompatActivity {


    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Map<String,Object> map;
    private ImageView menuImage;
    private ImageView footerImage;
    private ImageButton logoutIconButton;
    private ImageButton backIconButton;
    private TextView editTitle;
    private EditText editTextEditTitle;
    private EditText editTextLocation;
    private EditText editTextShortDescription;
    private EditText editTextAbout;
    private EditText editTextLongitude;
    private EditText editTextLatitude;
    private EditText getEditTextLocation;
    private Button buttonUpdate;
    private Button buttonChooseImage;
    private ImageView imageViewSelectedImage;
    private Uri uri=null;
    public static String url;
    private int requestCode = 1;
    private String imgName;
    private Bitmap bitmap;
    private boolean indikatorPromjeneSlike = false;
    private Blog model;
    private String doc_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        menuImage = (ImageView) findViewById(R.id.menu_image);
        logoutIconButton = (ImageButton) findViewById(R.id.logout_icon_button_addNew);
        backIconButton = (ImageButton) findViewById(R.id.back_icon_button_addNew);
        editTitle = (TextView) findViewById(R.id.title_maps);
        editTextEditTitle = (EditText) findViewById(R.id.searchField);
        editTextLocation = (EditText) findViewById(R.id.LocationEditText_addNew);
        editTextShortDescription = (EditText) findViewById(R.id.ShortDescriptionEditText_addNew);
        editTextAbout = (EditText) findViewById(R.id.AboutEditText_addNew);
        editTextLongitude = (EditText) findViewById(R.id.LongitudeEditText_addNew);
        editTextLatitude = (EditText) findViewById(R.id.LatitudeEditText_addNew);
        buttonUpdate = (Button) findViewById(R.id.create_button_addNew);
        imageViewSelectedImage = (ImageView) findViewById(R.id.selected_image_addNew);
        buttonChooseImage = (Button) findViewById(R.id.choose_image_button_addNew);
        footerImage = (ImageView) findViewById(R.id.footer_image);
        editTextLocation = (EditText) findViewById(R.id.LocationEditText_addNew);
        Intent intent = getIntent();
        doc_id = intent.getStringExtra("ID");
        model = (Blog) intent.getSerializableExtra("Model");


        editTextEditTitle.setText(model.getTitle());
        editTextShortDescription.setText(model.getShortdescription());
        editTextLocation.setText(model.getCity());
        editTextAbout.setText(model.getDescription());
        editTextLongitude.setText(model.getLongitude());
        editTextLatitude.setText(model.getLatitude());

        //ucitavanje slike iz URL-a koja je vec u bazi, mora thread ili async jer je ovo funkcija kojoj treba vremena
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(model.getImage());
//                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            imageViewSelectedImage.setImageBitmap(image);
//                        }
//                    });
//                } catch(IOException e) {
//                    System.out.println(e);
//                }
//            }
//        });
//        thread.start();
        Glide.with(getApplicationContext()).load(model.getImage()).into(imageViewSelectedImage);

        backIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        //choose image

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(indikatorPromjeneSlike){
                    // upload image pokrece lanac, kada zavrsi upload, pokrece se fja sendData()
                    uploadImage();
                }else{
                    sendData();
                }
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == requestCode && resultCode == Activity.RESULT_OK){
            if(data == null){
                return;
            }else{
                Context context = getApplicationContext();
                uri = data.getData();
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                imgName = cursor.getString(nameIndex);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    imageViewSelectedImage.setImageBitmap(bitmap);
                    indikatorPromjeneSlike = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(context,imgName,Toast.LENGTH_LONG).show();
            }
        }
    }

    public void openFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,requestCode);
    }

    private void sendData(){
        map = new HashMap<>();
        if(editTextEditTitle.getText().toString()!=model.getTitle()){
            map.put("title", editTextEditTitle.getText().toString());
        }
        if(editTextLocation.getText().toString()!=model.getCity()){
            map.put("city", editTextLocation.getText().toString());
        }
        if(editTextShortDescription.getText().toString()!=model.getShortdescription()){
            map.put("shortdescription", editTextAbout.getText().toString());
        }
        if(editTextAbout.getText().toString()!=model.getDescription()){
            map.put("description", editTextShortDescription.getText().toString());
        }
        if(indikatorPromjeneSlike){
            map.put("image", url);
        }
        if(editTextLatitude.getText().toString()!=model.getLatitude()){
            map.put("latitude", editTextLatitude.getText().toString());
        }
        if(editTextLongitude.getText().toString()==model.getLongitude()){
            map.put("longitude", editTextLongitude.getText().toString());
        }
        if(map.size()!=0){
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("blogs").document(doc_id).update(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditActivity.this, "Successfully added the data to database", Toast.LENGTH_SHORT).show();
                            model.reset();
                            Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else if(map.size()==0){
            Toast.makeText(getApplicationContext(),"There is nothing to update!",Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadImage(){
        firebaseStorage = firebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        if(uri!=null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading image...");
            progressDialog.show();
            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString() + imgName);
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                  @Override
                                                                  public void onSuccess(Uri uri) {
                                                                      url = uri.toString();
                                                                      sendData();
                                                                  }
                                                              }
                    );

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }





}