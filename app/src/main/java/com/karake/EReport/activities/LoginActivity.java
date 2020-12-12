package com.karake.EReport.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karake.EReport.R;
import com.karake.EReport.helpers.EReportDB_Helper;
import com.karake.EReport.helpers.Seeds;
import com.karake.EReport.models.User;
import com.karake.EReport.utils.PrefManager;

public class LoginActivity extends AppCompatActivity {

    EditText ed_telephone;
    EditText ed_password;
    EReportDB_Helper db_helper;
    User current_user,seed_user;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db_helper = new EReportDB_Helper(getApplicationContext());

        seed_user = new User();
//        seed_user.setPassword("12345678");
//        seed_user.setPhone("0785290539");
//        seed_user.setEmail("niyonkuruelisa@gmail.com");
//        seed_user.setName("Niyonkuru Elisa");
//        new Seeds(getApplicationContext(),seed_user);

        ed_telephone = findViewById(R.id.ed_telephone);
        ed_password = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ed_telephone.getText().toString().isEmpty() || ed_password.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Enter Telephone & Password", Toast.LENGTH_SHORT).show();

                }else{
                    //Toast.makeText(LoginActivity.this, ed_telephone.getText().toString()+" / "+ed_password.getText().toString(), Toast.LENGTH_SHORT).show();
                    current_user = db_helper.getUserByCredential(ed_telephone.getText().toString(),ed_password.getText().toString());

                    if(current_user != null){

                        Toast.makeText(getApplicationContext(), "Cool! User Found.", Toast.LENGTH_SHORT).show();
                        new PrefManager(getApplicationContext()).setCurrentUser(current_user.getId());
                        Intent intent  = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(), "User Not Found!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}