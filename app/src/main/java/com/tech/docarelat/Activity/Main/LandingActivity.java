package com.tech.docarelat.Activity.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity landingActivity;
    private Button btn_client;
    private Button btn_giver;
    private String regId = "";
    private TextView txt_gethelp;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_landing);
        findId();
        this.regId = MySharedPref.getData(this, "regId", "");
        Log.e("regId", "-----------> " + this.regId);
        landingActivity = this;
        this.btn_client.setOnClickListener(this);
        this.btn_giver.setOnClickListener(this);
        this.txt_gethelp.setOnClickListener(this);
    }

    private void findId() {
        this.btn_client = (Button) findViewById(R.id.btn_client);
        this.btn_giver = (Button) findViewById(R.id.btn_giver);
        this.txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);
    }

    public void onClick(View view) {
        if (view == this.btn_client) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("type", "CLIENT");
            startActivity(intent);
        } else if (view == this.btn_giver) {
            Intent intent2 = new Intent(this, MainActivity.class);
            intent2.putExtra("type", "GIVER");
            startActivity(intent2);
        } else if (view == this.txt_gethelp) {
            startActivity(new Intent(this, GetHelpActivity.class));
        }
    }
}
