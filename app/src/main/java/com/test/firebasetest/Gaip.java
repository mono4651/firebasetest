package com.test.firebasetest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class Gaip extends AppCompatActivity {
    Button join_btn;
    Button exit_btn;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaip);

        join_btn = (Button) findViewById(R.id.join_button);
        exit_btn = (Button) findViewById(R.id.exit_button);
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gaip.this, MainPage.class);
                startActivity(intent);
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gaip.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
