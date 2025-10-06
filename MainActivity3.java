package com.example.loyaltyfirst;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        TextView textView10 = findViewById(R.id.textView10);
        Intent intent = getIntent();
        String cid = intent.getStringExtra("cid");
        String url4 = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url4, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] transactions = s.trim().split("@");
                StringBuilder formattedResponse = new StringBuilder();

                for (String transaction : transactions) {
                    String[] parts = transaction.split("\\$");

                    if (parts.length >= 4) {
                        String transactionId = parts[0];
                        String datePart = parts[1];
                        String points = parts[2];
                        String total = parts[3] + "$";

                        formattedResponse.append(String.format("%-20s %-30s %-15s %s\n", transactionId, datePart, points, total));
                    }
                }

                textView10.setText(formattedResponse.toString());
            }
        }, null);

        queue.add(request);
    }
}
