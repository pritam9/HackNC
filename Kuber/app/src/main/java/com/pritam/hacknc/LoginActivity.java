package com.pritam.hacknc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

public class LoginActivity extends AppCompatActivity {
    private static final String N_TOKEN="b28d82a0e3ccaf8b9e07184881cbce29";

    public static NessieClient client = NessieClient.getInstance(N_TOKEN);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp.mId="581596f0360f81f104547669";
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
