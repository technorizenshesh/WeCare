package com.tech.docarelat.Activity.ClientService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;

public class ClientHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imgLeftMenu;
    private RelativeLayout lay_close;
    private RelativeLayout lay_extend;
    private RelativeLayout lay_logout;
    private RelativeLayout lay_profile;
    private RelativeLayout lay_rate;
    private RelativeLayout lay_schedule;
    private RelativeLayout lay_see;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_client_home);
        findId();
        this.imgLeftMenu.setOnClickListener(this);
        this.lay_schedule.setOnClickListener(this);
        this.lay_extend.setOnClickListener(this);
        this.lay_see.setOnClickListener(this);
        this.lay_profile.setOnClickListener(this);
        this.lay_rate.setOnClickListener(this);
        this.lay_logout.setOnClickListener(this);
        this.lay_close.setOnClickListener(this);
    }

    private void findId() {
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        this.lay_schedule = (RelativeLayout) findViewById(R.id.lay_schedule);
        this.lay_extend = (RelativeLayout) findViewById(R.id.lay_extend);
        this.lay_see = (RelativeLayout) findViewById(R.id.lay_see);
        this.lay_profile = (RelativeLayout) findViewById(R.id.lay_profile);
        this.lay_rate = (RelativeLayout) findViewById(R.id.lay_rate);
        this.lay_logout = (RelativeLayout) findViewById(R.id.lay_logout);
        this.lay_close = (RelativeLayout) findViewById(R.id.lay_close);
    }

    public void onClick(View view) {
        if (view == this.imgLeftMenu) {
            finish();
            System.exit(0);
        } else if (view == this.lay_schedule) {
            startActivity(new Intent(this, ScheduleCareGiverActivity.class));
        } else if (view == this.lay_see) {
            startActivity(new Intent(this, ScheduleResponseActivity.class));
        } else if (view == this.lay_extend) {
            startActivity(new Intent(this, ExtenTimeActivity.class));
        } else if (view == this.lay_profile) {
            startActivity(new Intent(this, ClientRegistrationActivity.class));
        } else if (view == this.lay_rate) {
            startActivity(new Intent(this, RateActivity.class));
        } else if (view == this.lay_logout) {
            MySharedPref.saveData(this, "ldata", (String) null);
            MySharedPref.saveData(this, "type", (String) null);
            startActivity(new Intent(this, ClientLoginActivity.class));
            finish();
        } else if (view == this.lay_close) {
            finish();
            System.exit(0);
        }
    }
}
