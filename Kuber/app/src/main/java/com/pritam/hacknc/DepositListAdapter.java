package com.pritam.hacknc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.reimaginebanking.api.nessieandroidsdk.models.Deposit;

import java.util.List;

/**
 * Created by Pritam on 10/30/16.
 */

public class DepositListAdapter extends ArrayAdapter<Deposit> {
    Context context;
    int resource;
    List<Deposit> objects;

    public DepositListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    public int getCount() {
        return objects.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView=inflater.inflate(resource,parent,false);
        }
        Deposit deposit = objects.get(position);

        TextView depositAmount = (TextView) convertView.findViewById(R.id.depositAmt);
        TextView depositStatus = (TextView) convertView.findViewById(R.id.depositStatus);
        TextView depositDate = (TextView) convertView.findViewById(R.id.depositDate);

        depositAmount.setText(deposit.getAmount()+"");
        depositStatus.setText(deposit.getStatus()+"");
        depositDate.setText(deposit.getTransactionDate());
        return convertView;
    }
}
