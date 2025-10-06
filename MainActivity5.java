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

public class MainActivity5 extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        TextView textView18 = findViewById(R.id.textView18);
        TextView textView20 = findViewById(R.id.textView20);
        TextView textView21 = findViewById(R.id.textView21);
        TextView textView22 = findViewById(R.id.textView22);
        TextView textView23 = findViewById(R.id.textView23);
        Spinner spinner_red_history = findViewById(R.id.spinner2);
        Intent intent5 = getIntent();
        String cus_id = intent5.getStringExtra("cid");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url5 = "http://10.0.2.2:8080/loyaltyfirst/PrizeIds.jsp?cid="+cus_id;
        StringRequest request5 = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<String> Array_List_Redhistory = new ArrayList<String>();
                String[] prizes = s.trim().split("\\$");
                for(int i =0; i<prizes.length;i++)
                {
                    Array_:Array_List_Redhistory.add(prizes[i]);
                }
                ArrayAdapter<String> Array_Adapter_Redhistory = new ArrayAdapter<String>(MainActivity5.this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Array_List_Redhistory);
                Array_Adapter_Redhistory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_red_history.setAdapter(Array_Adapter_Redhistory);
            }
        },null);
        requestQueue.add(request5);

        spinner_red_history.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String product_Id = adapterView.getSelectedItem().toString();
                String url2 = "http://10.0.2.2:8080/loyaltyfirst/RedemptionDetails.jsp?prizeid="+product_Id+"&cid="+cus_id;
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        String product_Desc = "";
                        String points_Needed = "";
                        String[] redHistory_values = str.trim().split("@");
                        String[] val1 = redHistory_values[0].split("\\$");
                        product_Desc = val1[0];
                        points_Needed = val1[1];

                        textView18.setText(product_Desc);
                        textView20.setText(points_Needed);
                        textView22.setText("------------------------------------------------------------------------------------------");

                        String values = "";
                        for(int i = 0; i< redHistory_values.length;i++){
                            String[] set = redHistory_values[i].split("\\$");
                            values = values + set[2] + "," + set[3] + "%";
                        }
                        String updated_val = values.replace(",", "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t").replace("%", "\n\n");
                        textView23.setText(updated_val);
                    }
                },null);
                requestQueue.add(stringRequest2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
