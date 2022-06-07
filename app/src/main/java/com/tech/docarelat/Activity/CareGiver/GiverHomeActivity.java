package com.tech.docarelat.Activity.CareGiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.tech.docarelat.Activity.Main.GetHelpActivity;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;

public class GiverHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imgLeftMenu;
    private RelativeLayout lay_delegets;
    private RelativeLayout lay_existingdele;
    private RelativeLayout lay_exit;
    private RelativeLayout lay_help;
    private RelativeLayout lay_logout;
    private RelativeLayout lay_profile;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_giver_home);
        findId();
        this.imgLeftMenu.setOnClickListener(this);
        this.lay_help.setOnClickListener(this);
        this.lay_exit.setOnClickListener(this);
        this.lay_profile.setOnClickListener(this);
        this.lay_existingdele.setOnClickListener(this);
        this.lay_delegets.setOnClickListener(this);
        this.lay_logout.setOnClickListener(this);
    }

    private void findId() {
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        this.lay_delegets = (RelativeLayout) findViewById(R.id.lay_delegets);
        this.lay_existingdele = (RelativeLayout) findViewById(R.id.lay_existingdele);
        this.lay_profile = (RelativeLayout) findViewById(R.id.lay_profile);
        this.lay_exit = (RelativeLayout) findViewById(R.id.lay_exit);
        this.lay_help = (RelativeLayout) findViewById(R.id.lay_help);
        this.lay_logout = (RelativeLayout) findViewById(R.id.lay_logout);
    }

    public void onClick(View view) {
        if (view == this.imgLeftMenu) {
            finish();
        } else if (view == this.lay_logout) {
            MySharedPref.saveData(this, "ldata", (String) null);
            MySharedPref.saveData(this, "type", (String) null);
            startActivity(new Intent(this, GiverLoginActivity.class));
            finish();
        } else if (view == this.lay_exit) {
            finish();
            System.exit(0);
        } else if (view == this.lay_delegets) {
            startActivity(new Intent(this, DeligetsNearYouActivity.class));
        } else if (view == this.lay_existingdele) {
            startActivity(new Intent(this, ExistingDelegationsActivity.class));
        } else if (view == this.lay_profile) {
            startActivity(new Intent(this, CareGiverRegistrationActivity.class));
        } else if (view == this.lay_help) {
            startActivity(new Intent(this, GetHelpActivity.class));
        }
    }
}
