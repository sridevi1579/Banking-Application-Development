package com.example.loyaltyfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button1);
        EditText editText1=findViewById(R.id.editText);
        EditText editText2=findViewById(R.id.editText2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username= editText1.getText().toString();
                String password= editText2.getText().toString();
                RequestQueue queue= Volley.newRequestQueue(MainActivity.this);

                String url = "http://10.0.2.2:8080/loyaltyfirst/login?user="+username+"&pass="+password;
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        if(str.trim().equals("No"))
                        {
                            Toast.makeText(MainActivity.this,"Invalid Username/Password",Toast.LENGTH_LONG).show();
                        }
                        else{
                            String[] result = str.trim().split(":");
                            String cid = result[1];
                            Intent intent=new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("cid",cid);
                            startActivity(intent);
                        }
                    }
                },null);
                queue.add(request);
            }
        });
    }
}