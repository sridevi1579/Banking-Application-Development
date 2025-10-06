package com.example.loyaltyfirst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class MainActivity6 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        Spinner spinner_add_points = findViewById(R.id.spinner3);
        Intent intent = getIntent();
        String cus_id = intent.getStringExtra("cid");
        Button button7 = findViewById(R.id.button7);
        TextView textView26 = findViewById(R.id.textView26);
        TextView textView28 = findViewById(R.id.textView28);
        TextView textView30 = findViewById(R.id.textView30);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cus_id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String str) {
                ArrayList<String> Array_List_addpoints = new ArrayList<String>();
                String[] res_add_points = str.trim().split("@");
                for(int i =0; i<res_add_points.length;i++)
                {
                    String set = res_add_points[i];
                    String[] result2 = set.split("\\$");
                    Array_List_addpoints.add(result2[0]);
                }
                ArrayAdapter<String> Array_Adapter_addpoints = new ArrayAdapter<String>(MainActivity6.this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Array_List_addpoints);
                Array_Adapter_addpoints.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_add_points.setAdapter(Array_Adapter_addpoints);
            }
        },null);
        queue.add(request);

        spinner_add_points.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String transaction_ref = adapterView.getSelectedItem().toString();
                String url6 = "http://10.0.2.2:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid="+cus_id+"&tref="+"'"+transaction_ref+"'";
                StringRequest request2 = new StringRequest(Request.Method.GET, url6, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String[] response_Val = s.trim().split("\\$");
                        textView26.setText(response_Val[2]);
                        textView28.setText(response_Val[0]);
                        textView30.setText(response_Val[1]);
                    }
                },null);
                queue.add(request2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tran_points = Integer.parseInt(textView26.getText().toString());
                String family_id = textView28.getText().toString();
                int percent = Integer.parseInt(textView30.getText().toString());
                float points_mfactor = (float)(percent/100.0);
                float family_Add_Points = points_mfactor*tran_points;
                String url7 = "http://10.0.2.2:8080/loyaltyfirst/FamilyIncrease.jsp?fid="+family_id+"&cid="+cus_id+"&npoints="+Math.round(family_Add_Points);
                StringRequest request3 = new StringRequest(Request.Method.GET, url7, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(MainActivity6.this,family_Add_Points+" Points added to the members of Family id "+family_id,Toast.LENGTH_LONG).show();
                    }
                },null);
                queue.add(request3);
            }
        });
    }

}
