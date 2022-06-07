package com.tech.docarelat.Activity.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tech.docarelat.Activity.CareGiver.CareGiverRegistrationActivity;
import com.tech.docarelat.Activity.CareGiver.GiverLoginActivity;
import com.tech.docarelat.Activity.ClientService.ClientLoginActivity;
import com.tech.docarelat.Activity.ClientService.ClientRegistrationActivity;
import com.tech.docarelat.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity mainactivity;
    private Button btn_login;
    private Button btn_signup;
    private TextView txt_gethelp;
    private String type;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        this.type = getIntent().getExtras().getString("type");
        findId();
        mainactivity = this;
        this.btn_login.setOnClickListener(this);
        this.btn_signup.setOnClickListener(this);
        this.txt_gethelp.setOnClickListener(this);
    }

    private void findId() {
        btn_login = (Button) findViewById(R.id.btn_login);
        this.btn_signup = (Button) findViewById(R.id.btn_signup);
        this.txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);


    }

    public void onClick(View view) {
        if (view == this.btn_login) {
            if (this.type.equals("CLIENT")) {
                startActivity(new Intent(this, ClientLoginActivity.class));
            } else {
                startActivity(new Intent(this, GiverLoginActivity.class));
            }
        } else if (view == this.btn_signup) {
            if (this.type.equals("CLIENT")) {
                startActivity(new Intent(this, ClientRegistrationActivity.class));
            } else {
                startActivity(new Intent(this, CareGiverRegistrationActivity.class));
            }
        } else if (view == this.txt_gethelp) {
            startActivity(new Intent(this, GetHelpActivity.class));
        }
    }
}
