package com.example.advenegro;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends Activity {
    private FirebaseAuth mAuth;
    private ImageView imageMenu;
    private ImageButton loginIconButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private ImageButton buttonHome;
    private ImageButton buttonAbout;
    private ImageButton buttonExplore;
    private ImageView imageFooter;
    private TextView textLoginTitle;
    private FirebaseUser firebaseUser;
    private Button homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageMenu = (ImageView) findViewById(R.id.menu_image);
        loginIconButton = (ImageButton) findViewById(R.id.login_icon_button);
        editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword2);
        buttonLogin = (Button) findViewById(R.id.create_button_addNew);
        textLoginTitle = (TextView) findViewById(R.id.title_maps);
        buttonHome = (ImageButton) findViewById(R.id.imageButton3);
        buttonAbout = (ImageButton) findViewById(R.id.imageButton2);
        buttonExplore = (ImageButton) findViewById(R.id.imageButton4);
        imageFooter = (ImageView) findViewById(R.id.footer_image);
        homeButton = (Button) findViewById(R.id.home_button);
        //give me that firebase auth baby
        mAuth = FirebaseAuth.getInstance();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Login();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });

    }


    private void Login(){
        if(editTextEmail.getText().toString().length()==0 || editTextPassword.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"You can't leave fields empty!",Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login done", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){
        //nista
    }
}