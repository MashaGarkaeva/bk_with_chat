package com.bookcross;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bookcross.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class InfoProfilScreen extends AppCompatActivity { //показывает данные пользователя
    TextView profileName, profileEmail, profileUsername, profilePassword;
    TextView titleName, titleUsername;
    Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoprofil);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileUsername = findViewById(R.id.profileUsername);
        profilePassword = findViewById(R.id.profilePassword);
        titleName = findViewById(R.id.titleName);
        titleUsername = findViewById(R.id.titleUsername);
        editButton = findViewById(R.id.editButton);
        showAllUserData();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });
    }
    public void showAllUserData(){

        Intent intent = getIntent();
        String nameUser = intent.getStringExtra(Constans.USERS_NAME);
        String emailUser = intent.getStringExtra(Constans.USERS_LOGIN);
        String usernameUser = intent.getStringExtra(Constans.USERS_USERNAME);
        String passwordUser = intent.getStringExtra(Constans.USERS_PASSWORD);

        titleName.setText(nameUser);
        titleUsername.setText(usernameUser);
        profileName.setText(nameUser);
        profileEmail.setText(emailUser);
        profileUsername.setText(usernameUser);
        profilePassword.setText(passwordUser);


    }
    public void passUserData(){
        String userUsername = profileUsername.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constans.TABLE_NAME_USER);
        Query checkUserDatabase = reference.orderByChild(Constans.USERS_USERNAME).equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.exists()){
                    String passwordFromDb = snapshot.child(userUsername).child(Constans.USERS_PASSWORD).getValue(String.class);

                        Intent intent = new Intent(InfoProfilScreen.this, EditProfilScreen.class);

                        String nameDb = snapshot.child(userUsername).child(Constans.USERS_NAME).getValue(String.class);
                        String emailDb = snapshot.child(userUsername).child(Constans.USERS_LOGIN).getValue(String.class);
                        String usernameDb = snapshot.child(userUsername).child(Constans.USERS_USERNAME).getValue(String.class);

                        intent.putExtra("name", nameDb);
                        intent.putExtra("email", emailDb);
                        intent.putExtra("userName", usernameDb);
                        intent.putExtra("password", passwordFromDb);

                        startActivity(intent);

                    }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
    });
}}