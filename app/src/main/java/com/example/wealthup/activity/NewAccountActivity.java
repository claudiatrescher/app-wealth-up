package com.example.wealthup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.wealthup.R;
import com.example.wealthup.database.dao.UserDao;
import com.example.wealthup.database.model.UserModel;

public class NewAccountActivity extends AppCompatActivity {

    EditText nameText;
    EditText emailText;
    EditText passwordText;
    Button loginButton;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_account);

        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        preferences = PreferenceManager.getDefaultSharedPreferences(NewAccountActivity.this);
        edit = preferences.edit();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_account), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String name = nameText.getText().toString();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameText.setError("O nome é obrigatório.");
            nameText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailText.setError("O e-mail é obrigatório.");
            emailText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordText.setError("A senha é obrigatória.");
            passwordText.requestFocus();
            return;
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("E-mail inválido.");
            emailText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordText.setError("A senha deve ter pelo menos 6 caracteres.");
            passwordText.requestFocus();
            return;
        }
        else{
            UserDao userDao = new UserDao(NewAccountActivity.this);
            UserModel userB = new UserModel();

            userB.setName(name);
            userB.setEmail(email);
            userB.setPassword(password);

            userB.setId(userDao.Insert(userB));

            edit.putInt("KEY_ID", userB.getId());
            edit.putString("KEY_NAME",userB.getName());
            edit.apply();

            Intent intent = new Intent(NewAccountActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}