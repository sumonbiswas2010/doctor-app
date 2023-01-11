package com.example.doctoronline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class signup extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://doctor-server.herokuapp.com";
    Button signupBtn;
    EditText nameEdit, emailEdit, name2Edit, passwordEdit, gender, location, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.signup_dialog);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Button signupBtn = findViewById(R.id.signup);
        final EditText nameEdit = findViewById(R.id.nameEdit);
        final EditText emailEdit = findViewById(R.id.emailEdit);
        final EditText name2edit = findViewById(R.id.name2Edit);
        final EditText passwordEdit = findViewById(R.id.passwordEdit);
        final EditText gender = findViewById(R.id.gender);
        final EditText location = findViewById(R.id.location);
        final EditText number = findViewById(R.id.number);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("first_name", nameEdit.getText().toString());
                map.put("last_name", name2edit.getText().toString());
                map.put("password", passwordEdit.getText().toString());
                map.put("email", emailEdit.getText().toString());
                map.put("location", location.getText().toString());
                map.put("gender", gender.getText().toString());
                map.put("number", number.getText().toString());

                Call<Void> call = retrofitInterface.executeSignup(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            Toast.makeText(signup.this,
                                    "Signed up successfully", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(getApplicationContext(), login.class);
                            i.putExtra("mail", emailEdit.getText().toString());
                            i.putExtra("pass", passwordEdit.getText().toString());
                            startActivity(i);

                        } else if (response.code() == 400) {
                            Toast.makeText(signup.this,
                                    "Already registered", Toast.LENGTH_LONG).show();
                        }
                        else if (response.code() == 401) {
                            Toast.makeText(signup.this,
                                    "Phone Number Already Register", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(signup.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}