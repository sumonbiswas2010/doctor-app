package com.example.doctoronline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Integer.parseInt;

public class DocInfo extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://doctor-server.herokuapp.com";

    TextView name, phone, location, speciality, chamber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_doc_info);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        location = findViewById(R.id.location);
        chamber = findViewById(R.id.chamber);
        speciality = findViewById(R.id.speciality);

        Intent i =getIntent();
        String q_id = i.getStringExtra("q_id");

        HashMap<String, String> map = new HashMap<>();

        map.put("id", q_id);
        //loc.setText("Doctor's Available Near " + location);

        Call<DocData> call = retrofitInterface.executeDocData(map);

        call.enqueue(new Callback<DocData>() {
            @Override
            public void onResponse(Call<DocData> call, Response<DocData> response) {

                if (response.code() == 200) {

                    DocData result = response.body();

                    name.setText(result.getName());
                    phone.setText(result.getPhone());
                    location.setText(result.getLocation());
                    speciality.setText(result.getSpeciality());
                    chamber.setText(result.getChember());


                } else if (response.code() == 400) {
                    Toast.makeText(DocInfo.this, "No Doctors Found!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(DocInfo.this, "Wrong move",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DocData> call, Throwable t) {
                Toast.makeText(DocInfo.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}