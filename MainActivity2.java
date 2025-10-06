package com.example.loyaltyfirst;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstaceState) {

        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String cus_id = intent.getStringExtra("cid");
        TextView customer_name = findViewById(R.id.textView4);
        TextView customer_points = findViewById(R.id.textView5);
        Button all_transactions = findViewById(R.id.button);
        Button transaction_details = findViewById(R.id.button2);
        Button redemption_history = findViewById(R.id.button4);
        Button add_family_points = findViewById(R.id.button5);
        Button exit_button = findViewById(R.id.button6);
        ImageView image_View = findViewById(R.id.imageView);

        String url = "http://10.0.2.2:8080/loyaltyfirst/Info.jsp?cid="+cus_id;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] x = s.trim().split("\\$");
                String name = x[0];
                String points = x[1];
                customer_name.setText(name);
                customer_points.setText(points);
            }
        },null);
        queue.add(request);

        String url2 ="http://10.0.2.2:8080/loyaltyfirst/images/"+cus_id+".jpeg";

        ImageRequest request2 = new ImageRequest(url2, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                image_View.setImageBitmap(bitmap);
            }
        },0,0,null,null);
        queue.add(request2);

        all_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity2.this,MainActivity3.class);
                intent2.putExtra("cid",cus_id);
                startActivity(intent2);
            }
        });

        redemption_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity2.this,MainActivity5.class);
                intent3.putExtra("cid",cus_id);
                startActivity(intent3);
            }
        });

        transaction_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity2.this,MainActivity4.class);
                intent1.putExtra("cid",cus_id);
                startActivity(intent1);
            }
        });

        add_family_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity2.this, MainActivity6.class);
                intent4.putExtra("cid",cus_id);
                startActivity(intent4);
            }
        });

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent5);
            }
        });

    }
}
