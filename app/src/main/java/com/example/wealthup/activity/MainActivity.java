package com.example.wealthup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.wealthup.R;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView createAccountLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        emailEditText = findViewById(R.id.emailText);
        passwordEditText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        createAccountLink = findViewById(R.id.createAccountLink);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        createAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewAccountActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void performLogin() {

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("O e-mail é obrigatório.");
            emailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("A senha é obrigatória.");
            passwordEditText.requestFocus();
            return;
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("E-mail inválido.");
            emailEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("A senha deve ter pelo menos 6 caracteres.");
            passwordEditText.requestFocus();
            return;
        }

        if (email.equals("teste@teste.com") && password.equals("123456")) {
            Toast.makeText(MainActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "E-mail ou senha incorretos.", Toast.LENGTH_LONG).show();
        }
    }
}