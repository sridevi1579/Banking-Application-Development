package com.example.loyaltyfirst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent = getIntent();
        Spinner spinner = findViewById(R.id.spinner);
        TextView textView12 = findViewById(R.id.textView12);
        TextView textView13 = findViewById(R.id.textView13);
        TextView textView15 = findViewById(R.id.textView15);

        ArrayList<String> Array_List = new ArrayList<String>();
        String cus_id = intent.getStringExtra("cid");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid=" + cus_id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String str) {
                String[] result = str.trim().split("@");
                for (int i = 0; i < result.length; i++) {
                    String n_Val = result[i];
                    String[] result2 = n_Val.split("\\$");
                    Array_List.add(result2[0]);
                }
                ArrayAdapter<String> Array_Adapter = new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Array_List);
                Array_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(Array_Adapter);
            }
        }, null);
        queue.add(request);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String transaction_id = adapterView.getSelectedItem().toString();
                String url2 = "http://10.0.2.2:8080/loyaltyfirst/TransactionDetails.jsp?tref="+"'"+transaction_id+"'";
                StringRequest request1 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        String[] result3 = str.trim().split("@");
                        String[] result3_val = result3[0].split("\\$");
                        String transaction_points = result3_val[1];
                        String transaction_date = result3_val[0];
                        textView12.setText(transaction_date);
                        textView13.setText(transaction_points);
                        String returned_Val = "";
                        for(int i = 0; i< result3.length;i++){
                            String[] values = result3[i].split("\\$");
                            returned_Val = returned_Val + values[2] + "," + values[4] + "," + values[3] + "%";
                        }
                        String final_Val = returned_Val.replace(",", "\t\t\t\t\t\t\t\t\t\t\t\t").replace("%", "\n\n");
                        textView15.setText(final_Val);

                    }
                },null);
                queue.add(request1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        }
    }

