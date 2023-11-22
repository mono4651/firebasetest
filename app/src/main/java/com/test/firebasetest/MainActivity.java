package com.test.firebasetest;
// MainActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUserId, editTextPassword;
    private Button buttonSave;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        editTextUserId = findViewById(R.id.editTextUserId);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
            }
        });
    }

    private void saveUserData() {
        String userId = editTextUserId.getText().toString();
        String password = editTextPassword.getText().toString();

        // 유효성 검사 (예: 빈 문자열 확인)

        // 사용자 객체 생성
        User user = new User(userId, password);

        // Firestore에 사용자 정보 저장
        db.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    // 성공적으로 저장되었을 때의 처리
                    editTextUserId.setText("");
                    editTextPassword.setText("");
                    // 추가적인 로직을 여기에 추가할 수 있습니다.
                })
                .addOnFailureListener(e -> {
                    // 저장 실패 시의 처리
                    // 추가적인 로직을 여기에 추가할 수 있습니다.
                });
    }
}