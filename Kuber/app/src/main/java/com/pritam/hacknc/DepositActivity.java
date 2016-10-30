package com.pritam.hacknc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.TransactionMedium;
import com.reimaginebanking.api.nessieandroidsdk.models.Deposit;
import com.reimaginebanking.api.nessieandroidsdk.models.PostResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DepositActivity extends AppCompatActivity {
    DepositListAdapter adapter;
    List<Deposit> depositList;
    final String optId="5815d468360f81f10454767e";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        final ListView depositLv = (ListView) findViewById(R.id.depositList);
        final EditText depositAmt = (EditText) findViewById(R.id.edtDepositAmt);
        final EditText description = (EditText) findViewById(R.id.edtDepositTest);
        //EditText description = findViewById(R.id.edtDepositTest)
        findViewById(R.id.btnConfirmDeposit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-DD");
                String date = s.format(new Date());
                Log.d("ON CLICK","Values are "+date+" ; amount "+depositAmt.getText().toString().trim()+" ; desc "+description.getText().toString().trim());
                Deposit deposit = new Deposit.Builder()
                        .amount(Integer.parseInt(depositAmt.getText().toString().trim()))
                        .description(description.getText().toString().trim())
                        .transactionDate(date.substring(0,date.length()-1))
                        .medium(TransactionMedium.BALANCE)
                        .build();
                LoginActivity.client.DEPOSIT.createDeposit(optId, deposit, new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result) {
                        PostResponse<Deposit> depo = (PostResponse<Deposit>) result;
                        if(adapter==null){
                            depositList=new ArrayList<Deposit>();
                            depositList.add(depo.getObjectCreated());
                            adapter=new DepositListAdapter(DepositActivity.this,R.layout.list_view,depositList);
                            adapter.setNotifyOnChange(true);
                            depositLv.setAdapter(adapter);
                        }
                        else{
                            adapter.add(depo.getObjectCreated());
                        }

                        adapter.notifyDataSetChanged();
                        Toast.makeText(DepositActivity.this, "Done!! "+depo.getObjectCreated().getId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(NessieError error) {
                        Log.d("ERROR","Error is "+error.getMessage());
                        Toast.makeText(DepositActivity.this, "Error Depositing amount"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        LoginActivity.client.DEPOSIT.getDeposits(optId, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                List<Deposit> depositList = (List<Deposit>) result;
                adapter=new DepositListAdapter(DepositActivity.this,R.layout.list_view,depositList);
                adapter.setNotifyOnChange(true);
                depositLv.setAdapter(adapter);
            }

            @Override
            public void onFailure(NessieError error) {
                Log.d("ERROR",error.getMessage());
                Toast.makeText(DepositActivity.this, "Error getting previous Deposits", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
