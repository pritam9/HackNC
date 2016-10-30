package com.pritam.hacknc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.AccountType;
import com.reimaginebanking.api.nessieandroidsdk.models.Account;
import com.reimaginebanking.api.nessieandroidsdk.models.Address;
import com.reimaginebanking.api.nessieandroidsdk.models.Customer;
import com.reimaginebanking.api.nessieandroidsdk.models.PostResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUp extends AppCompatActivity {
    public static String mId;
    public static String aId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText fName = (EditText) findViewById(R.id.edtFname);
        final EditText lName = (EditText) findViewById(R.id.edtLname);
        final EditText streetNo = (EditText) findViewById(R.id.edtAstreet);
        final EditText streetName = (EditText) findViewById(R.id.edtAstreetName);
        final EditText city = (EditText) findViewById(R.id.edtAcity);
        final EditText state = (EditText) findViewById(R.id.edtAState);
        final EditText zip = (EditText) findViewById(R.id.edtAZip);
        EditText username = (EditText) findViewById(R.id.edtUsername);
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String accountNumber = s.format(new Date());
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address newAddress = new Address.Builder()
                        .city(city.getText().toString().trim())
                        .state(state.getText().toString().trim())
                        .streetNumber(streetNo.getText().toString().trim())
                        .streetName(streetName.getText().toString().trim())
                        .zip(zip.getText().toString().trim())
                        .build();
                Customer newCust = new Customer.Builder()
                        .firstName(fName.getText().toString().trim())
                        .lastName(lName.getText().toString().trim())
                        .address(newAddress)
                        .build();
                LoginActivity.client.CUSTOMER.createCustomer(newCust, new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result) {
                        PostResponse<Customer> postResponse = (PostResponse<Customer>) result;
                        mId=postResponse.getObjectCreated().getId();
                        Log.d("Response",postResponse.getObjectCreated().getId());
                        addNewAccount();
                    }

                    @Override
                    public void onFailure(NessieError error) {

                    }
                });
            }
        });
    }

    private void addNewAccount() {
        Account account = new Account.Builder().balance(500).nickname("NickName").rewards(0).type(AccountType.CHECKING).build();
        LoginActivity.client.ACCOUNT.createAccount(mId, account, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                PostResponse<Account> acct = (PostResponse<Account>) result;
                aId=acct.getObjectCreated().getId();
                Toast.makeText(SignUp.this, "Checking Account created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(NessieError error) {
                Toast.makeText(SignUp.this, "Error while creating Account. "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
