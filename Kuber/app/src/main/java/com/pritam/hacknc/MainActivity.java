package com.pritam.hacknc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.AccountType;
import com.reimaginebanking.api.nessieandroidsdk.models.Account;
import com.reimaginebanking.api.nessieandroidsdk.models.Address;
import com.reimaginebanking.api.nessieandroidsdk.models.Customer;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String N_TOKEN="b28d82a0e3ccaf8b9e07184881cbce29";

    private static Button setLimitBtn;
    private static Button depositBtn;
    private static Button adjustExpBtn;
    private static Button actVsPlan;
    NessieClient client;
    public static List<String> categoryList = new ArrayList<>();
    public static List<Float> limitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(limitList.size()==0)
            setupCateories();
        client = NessieClient.getInstance(N_TOKEN);
        actVsPlan= (Button) findViewById(R.id.actualVsPlan);
        adjustExpBtn = (Button) findViewById(R.id.adjustExpenses);
        depositBtn = (Button) findViewById(R.id.depositBtn);
        setLimitBtn = (Button) findViewById(R.id.setLimitBtn);

        setOnClickListenersForBtns();
        client.ACCOUNT.getAccounts(AccountType.CHECKING, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                List<Account> accounts = (List<Account>) result;

                Log.d("ACCOUNTS"," Accounts are "+result);
            }

            @Override
            public void onFailure(NessieError error) {

            }
        });

        client.CUSTOMER.getCustomers(new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                Log.d("CUSTOMERS",result.toString());
            }

            @Override
            public void onFailure(NessieError error) {
                Log.d("ERROR",error.getMessage());
            }
        });

        client.ATM.getATM("56c66be5a73e492741506f2b", new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                Log.d("ATM",result.toString());
            }

            @Override
            public void onFailure(NessieError error) {
                Log.d("Error",error.getMessage());
            }
        });
    }

    private void setupCateories() {
        categoryList.add("Food");
        categoryList.add("Utilities");

        categoryList.add("Clothing");
        categoryList.add("Transportation");
        categoryList.add("Household");
        categoryList.add("Personal");

        //HardCode listview
        limitList.add(100F);
        limitList.add(100F);
        limitList.add(100F);
        limitList.add(100F);
        limitList.add(100F);
        limitList.add(100F);
    }

    private void setOnClickListenersForBtns() {

        //Login Button Listener
        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DepositActivity.class);
                startActivity(intent);
            }
        });

        adjustExpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });

        actVsPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ActualVsPlanned.class);
                startActivity(intent);
            }
        });

        //register Customer Listner
        setLimitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SetLimitActivity.class);
                startActivity(intent);

            }
        });
    }

}
