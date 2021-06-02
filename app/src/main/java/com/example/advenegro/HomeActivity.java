package com.example.advenegro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeActivity extends AppCompatActivity {

    private ImageView menuImage;
    private ImageButton loginIconButton;
    private ImageButton homeButton;
    private ImageButton aboutButton;
    private ImageButton exploreButton;
    private ImageView footerImage;
    private TextView homeTitleLoginActivity;



    private TextView numberOfActivities;
    private TextView enjoyWithUs;
    private Button showAll;


    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference blogsRef = FirebaseFirestore.getInstance().collection("blogs");
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;
    private FirestoreRecyclerAdapter adapter1;
    private Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menuImage = (ImageView) findViewById(R.id.menu_image);
        loginIconButton = (ImageButton) findViewById(R.id.login_icon_button);
        homeTitleLoginActivity = (TextView) findViewById(R.id.title_maps);
        homeButton = (ImageButton) findViewById(R.id.imageButton3);
        aboutButton = (ImageButton) findViewById(R.id.imageButton2);
        exploreButton = (ImageButton) findViewById(R.id.imageButton4);
        footerImage = (ImageView) findViewById(R.id.footer_image);

        numberOfActivities = (TextView) findViewById(R.id.numberOfActivities);
        enjoyWithUs = (TextView) findViewById(R.id.enjoyWithUs);

        showAll = (Button) findViewById(R.id.showAll);




        mFirestoreList = findViewById(R.id.recyclerHome);
        mAuth = FirebaseAuth.getInstance();

        loginIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });

        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });



        Query query = blogsRef.orderBy("description", Query.Direction.DESCENDING).limit(2);

        blogsRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    private static final String TAG ="";

                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            numberOfActivities.setText("Number of activities: " + count);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });








        loadData(query);
    }

    public void loadData(Query query){
        FirestoreRecyclerOptions<Blog> options = new FirestoreRecyclerOptions.Builder<Blog>()
                .setQuery(query,Blog.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Blog, BlogsViewHolder>(options){
            @NonNull
            @Override
            public BlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home,parent, false);
                return new BlogsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BlogsViewHolder holder, int position, @NonNull Blog model){
                holder.cardTitle.setText(model.getTitle());
                holder.locationText.setText(model.getCity());
                Glide.with(getApplicationContext()).load(model.getImage()).into(holder.cardImage);

                holder.cardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("Model",model);
                        startActivity(intent);
                    }
                });


            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

    }

    private class BlogsViewHolder extends RecyclerView.ViewHolder{
        private TextView cardTitle;
        private TextView locationText;
        private ImageView cardImage;
        private Button cardButton;


        public BlogsViewHolder(@NonNull View itemView) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.titleHome);
            locationText = itemView.findViewById(R.id.cityHome);
            cardImage = itemView.findViewById(R.id.image_of_location_home);
            cardButton = itemView.findViewById(R.id.buttonGo);
        }
    }


    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
}