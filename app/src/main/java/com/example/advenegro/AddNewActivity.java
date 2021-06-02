package com.example.advenegro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

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

//import com.google.android.gms.tasks.Continuation;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddNewActivity extends AppCompatActivity {

    private ImageView menuImage;
    private ImageView footerImage;
    private ImageButton logoutButton;
    private ImageButton backIconButton;
    private TextView textViewTitle;
    private EditText editTextTitle;
    private EditText editTextLocation;
    private EditText editTextShortDescription;
    private EditText editTextAbout;
    private EditText editTextLongitude;
    private EditText editTextLatitude;
    private Button buttonCreateBlog;
    private Button buttonChooseImage;
    private ImageView imageViewSelectedImage;
    private FirebaseFirestore firebaseFirestore;
    private Map<String,Object> map;
    private Uri uri=null;
    private int requestCode = 1;
    private String imgName;
    private Bitmap bitmap;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    public static String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        menuImage = (ImageView) findViewById(R.id.menu_image);
        logoutButton = (ImageButton) findViewById(R.id.logout_icon_button_addNew);
        backIconButton = (ImageButton) findViewById(R.id.back_icon_button_addNew);
        textViewTitle = (TextView) findViewById(R.id.title_maps);
        editTextTitle = (EditText) findViewById(R.id.searchField);
        editTextLocation = (EditText) findViewById(R.id.LocationEditText_addNew);
        editTextShortDescription = (EditText) findViewById(R.id.ShortDescriptionEditText_addNew);
        editTextAbout = (EditText) findViewById(R.id.AboutEditText_addNew);
        editTextLongitude = (EditText) findViewById(R.id.LongitudeEditText_addNew);
        editTextLatitude = (EditText) findViewById(R.id.LatitudeEditText_addNew);
        buttonCreateBlog = (Button) findViewById(R.id.create_button_addNew);
        imageViewSelectedImage = (ImageView) findViewById(R.id.selected_image_addNew);
        buttonChooseImage = (Button) findViewById(R.id.choose_image_button_addNew);
        footerImage = (ImageView) findViewById(R.id.footer_image);
        buttonChooseImage = (Button) findViewById(R.id.choose_image_button_addNew);
        mAuth = FirebaseAuth.getInstance();

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        buttonCreateBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        backIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
                startActivity(intent);
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
        map.put("title", editTextTitle.getText().toString());
        map.put("city", editTextLocation.getText().toString());
        map.put("description", editTextAbout.getText().toString());
        map.put("shortdescription", editTextShortDescription.getText().toString());
        map.put("image", url);
        map.put("latitude", editTextLatitude.getText().toString());
        map.put("longitude", editTextLongitude.getText().toString());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("blogs").document().set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNewActivity.this,"Successfully added the data to database", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddNewActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                }
        });

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
                    Toast.makeText(AddNewActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }
}