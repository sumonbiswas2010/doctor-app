package com.example.doctoronline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import static java.lang.Integer.parseInt;


public class DocActivity<adapter> extends AppCompatActivity {
    public ListView lv;
    TextView tv, loc;
    Button btn;
    EditText new_loc;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://doctor-server.herokuapp.com";
    int xx=0;
    public String q[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_doc);

        lv = findViewById(R.id.lv);
        tv = findViewById(R.id.tv);
        btn =  findViewById(R.id.btn);
        new_loc = findViewById(R.id.et);

        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        String location = i.getStringExtra("location");
        tv.setText("Hi " + name);
        loc = findViewById(R.id.loc);
        loc.setText("Doctor's Available Near " + location);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        handleDocDialog(location);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loc = new_loc.getText().toString();
                handleDocDialog(loc);

            }

        }

        );
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), DocInfo.class);
                i.putExtra("q_id", q[position]);
                startActivity(i);
            }
        });
    }

    private void handleDocDialog(String location) {
        HashMap<String, String> map = new HashMap<>();

            map.put("location", location);
            loc.setText("Doctor's Available Near " + location);

        //map.put("password", passwordEdit.getText().toString());

        //Call<DocResult> call = retrofitInterface.executeDoc(map);
        Call<DocResult> call = retrofitInterface.executeDoc(map);

        call.enqueue(new Callback<DocResult>() {
            @Override
            public void onResponse(Call<DocResult> call, Response<DocResult> response) {

                if (response.code() == 200) {

                    DocResult result = response.body();

                    String[] name = result.getName();
                    String[] id = result.getId();
                    q=id;
                    String first_count = result.getCount();
                    int count = (parseInt(result.getCount()));
                    String [] data = new String[count];

                    for(int i=0;i<count;i++)
                    {
                        data[i]="ID: "+id[i]+ "----"+ "Doctor's Name: " + name[i];
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(DocActivity.this, android.R.layout.simple_dropdown_item_1line, (data));
                    lv.setAdapter(adapter);


                } else if (response.code() == 400) {
                    Toast.makeText(DocActivity.this, "No Doctors Found!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(DocActivity.this, "Wrong move",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DocResult> call, Throwable t) {
                Toast.makeText(DocActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}