package com.example.advenegro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.graphics.Bitmap;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


public class DashboardActivity extends AppCompatActivity {

    private ImageView menuImage;
    private TextView dashboardTitleText;
    private ImageButton logoutButton;
    private Button addNewButton;
    private ImageView cardBackgroundImage;
    private ImageView locationIcon;
    private ImageView shortDescriptionIcon;
    private Button deleteButton;
    private Button editButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    private CollectionReference blogsRef = FirebaseFirestore.getInstance().collection("blogs");

    private RecyclerView mFirestoreList;

    private FirestoreRecyclerAdapter adapter;

    private Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        menuImage = (ImageView) findViewById(R.id.menu_image);
        dashboardTitleText = (TextView) findViewById(R.id.title_dashboard);
        logoutButton = (ImageButton) findViewById(R.id.logout_icon_button_dashboard);
        addNewButton = (Button) findViewById(R.id.add_new_button_dashboard);
        cardBackgroundImage = (ImageView) findViewById(R.id.background_imageView_dashboard);
        locationIcon = (ImageView) findViewById(R.id.location_icon_dashboard);
        shortDescriptionIcon = (ImageView) findViewById(R.id.short_description_maps);

        mFirestoreList = findViewById(R.id.firebaseList);

        mAuth = FirebaseAuth.getInstance();

        Query query = blogsRef;

        FirestoreRecyclerOptions<Blog> options = new FirestoreRecyclerOptions.Builder<Blog>()
                .setQuery(query,Blog.class)
                .build();

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, AddNewActivity.class);
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

        adapter = new FirestoreRecyclerAdapter<Blog, BlogsViewHolder>(options){
            @NonNull
            @Override
            public BlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_template,parent, false);
                editButton = view.findViewById(R.id.edit_button_dashboard);
                deleteButton = view.findViewById(R.id.delete_button_dashboard);
                return new BlogsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BlogsViewHolder holder, int position, @NonNull Blog model){
                holder.cardTitle.setText(model.getTitle());
                holder.locationText.setText(model.getCity());
                holder.shortDescriptionText.setText(model.getShortdescription());
                Glide.with(getApplicationContext()).load(model.getImage()).into(holder.cardImage);
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(DashboardActivity.this, "OH MAJKO DRAGA" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        String documentID = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                        intent.putExtra("ID",documentID);
                        intent.putExtra("Model",model);
                        startActivity(intent);

                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String documentID = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                        blogsRef.document(documentID).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"Blog is deleted!",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"There was a problem deleting this blog!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

        load();
    }

    private class BlogsViewHolder extends RecyclerView.ViewHolder{
        private TextView cardTitle;
        private TextView locationText;
        private TextView shortDescriptionText;
        private ImageView cardImage;


        public BlogsViewHolder(@NonNull View itemView) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.name_of_location_dashboard);
            locationText = itemView.findViewById(R.id.location_textView_dashboard);
            shortDescriptionText = itemView.findViewById(R.id.short_description_textView);
            cardImage = itemView.findViewById(R.id.image_of_location_dashboard);
        }
    }

    private void load(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            String firstName = documentSnapshot.getString("firstName");
                            String lastName = documentSnapshot.getString("lastName");
                            dashboardTitleText.setText(firstName + " "+lastName);

                        }

                        else{
                            Toast.makeText(DashboardActivity.this,"There was a problem picking data",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    protected void onStart(){
        super.onStart();
        adapter.startListening();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            }
    }

}