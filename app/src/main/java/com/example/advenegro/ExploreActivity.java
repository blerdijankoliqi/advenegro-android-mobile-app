package com.example.advenegro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExploreActivity extends AppCompatActivity {

    private ImageView menuImage;
    private ImageButton loginIconButton;
    private ImageButton homeButton;
    private ImageButton aboutButton;
    private ImageButton exploreButton;
    private ImageView footerImage;
    private TextView exploreTitleLoginActivity;

    private Button allFilterButton;
    private Button podgoricaFilterButton;
    private Button niksicFilterButton;
    private Button ulcinjFilterButton;
    private Button budvaFilterButton;
    private Button zabljakFilterButton;

    private ImageView cardBackgroundImage;
    private ImageView cardImage;
    private TextView cardTitle;
    private ImageView locationIcon;
    private TextView locationText;
    private ImageView shortDescriptionIcon;
    private TextView shortDescriptionText;
    private Button seeMoreButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference blogsRef = FirebaseFirestore.getInstance().collection("blogs");
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        menuImage = (ImageView) findViewById(R.id.menu_image);
        loginIconButton = (ImageButton) findViewById(R.id.login_icon_button);
        exploreTitleLoginActivity = (TextView) findViewById(R.id.title_maps);
        homeButton = (ImageButton) findViewById(R.id.imageButton3);
        aboutButton = (ImageButton) findViewById(R.id.imageButton2);
        exploreButton = (ImageButton) findViewById(R.id.imageButton4);
        footerImage = (ImageView) findViewById(R.id.footer_image);

        allFilterButton = (Button) findViewById(R.id.all_button_dashboard);
        podgoricaFilterButton = (Button) findViewById(R.id.sea_button_dashboard);
        niksicFilterButton = (Button) findViewById(R.id.river_button_dashboard);
        ulcinjFilterButton = (Button) findViewById(R.id.lake_button_dashboard);
        budvaFilterButton = (Button) findViewById(R.id.mountain_button_dashboard);
        zabljakFilterButton = (Button) findViewById(R.id.forest_button_dashboard);


        cardBackgroundImage = (ImageView) findViewById(R.id.background_imageView_dashboard);
        cardImage = (ImageView) findViewById(R.id.image_of_location_dashboard);
        cardTitle = (TextView) findViewById(R.id.name_of_location_dashboard);
        locationIcon = (ImageView) findViewById(R.id.location_icon_dashboard);
        locationText = (TextView) findViewById(R.id.location_textView_dashboard);
        shortDescriptionIcon = (ImageView) findViewById(R.id.short_description_maps);
        shortDescriptionText = (TextView) findViewById(R.id.short_description_textView_maps);
        seeMoreButton = (Button) findViewById(R.id.edit_button_dashboard);

        mFirestoreList = findViewById(R.id.recycleViewExplore);
        mAuth = FirebaseAuth.getInstance();


        allFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = blogsRef;
                loadData(query);
                adapter.startListening();
            }
        });

        podgoricaFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = blogsRef.whereEqualTo("city","Podgorica");
                loadData(query);
                adapter.startListening();

            }
        });

        budvaFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = blogsRef.whereEqualTo("city","Budva");
                loadData(query);
                adapter.startListening();

            }
        });

        niksicFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = blogsRef.whereEqualTo("city","Niksic");
                loadData(query);
                adapter.startListening();

            }
        });

        ulcinjFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = blogsRef.whereEqualTo("city","Ulcinj");
                loadData(query);
                adapter.startListening();

            }
        });

        zabljakFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = blogsRef.whereEqualTo("city","Zabljak");
                loadData(query);
                adapter.startListening();

            }
        });




        Query query = blogsRef;
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
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_template_home,parent, false);
                return new BlogsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ExploreActivity.BlogsViewHolder holder, int position, @NonNull Blog model){
                holder.cardTitle.setText(model.getTitle());
                holder.locationText.setText(model.getCity());
                holder.shortDescriptionText.setText(model.getShortdescription());
                Glide.with(getApplicationContext()).load(model.getImage()).into(holder.cardImage);
            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

    }

    private class BlogsViewHolder extends RecyclerView.ViewHolder{
        private TextView cardTitle;
        private TextView locationText;
        private TextView shortDescriptionText;
        private ImageView cardImage;


        public BlogsViewHolder(@NonNull View itemView) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.name_of_location_explore);
            locationText = itemView.findViewById(R.id.location_textView_explore);
            shortDescriptionText = itemView.findViewById(R.id.short_description_textView_explore);
            cardImage = itemView.findViewById(R.id.image_of_location_home);
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