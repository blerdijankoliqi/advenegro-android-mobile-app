package com.example.advenegro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    //REKO SAM TI BLJERD ODJE DA STAVIS REFERENCU NA KORISNIKAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!
    //REKO SAM TI BLJERD ODJE DA STAVIS REFERENCU NA KORISNIKAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!
    //REKO SAM TI BLJERD ODJE DA STAVIS REFERENCU NA KORISNIKAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!
    //REKO SAM TI BLJERD ODJE DA STAVIS REFERENCU NA KORISNIKAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!
    //REKO SAM TI BLJERD ODJE DA STAVIS REFERENCU NA KORISNIKAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!
    //REKO SAM TI BLJERD ODJE DA STAVIS REFERENCU NA KORISNIKAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!
    //REKO SAM TI BLJERD ODJE DA STAVIS REFERENCU NA KORISNIKAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!
    //REKO SAM TI BLJERD ODJE DA STAVIS REFERENCU NA KORISNIKAAAAAAAA!!!!!!!!!!!!!!!!!!!!!!!!!!


    private Button loginButton;
    private FirebaseUser firebaseUser;
    @Override
    protected void onStart(){
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser !=null){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.LoginButtonMain);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(myIntent);

                //test za branch

            }
        });
    }
}