package com.tech.docarelat.Activity.ClientService;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.tech.docarelat.Activity.Main.GetHelpActivity;
import com.tech.docarelat.Adapter.CareGiverResponseListAdapter;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ScheduleResponseActivity extends AppCompatActivity implements View.OnClickListener {
    public static RecyclerView Rvgiverlist;
    private ImageButton imgLeftMenu;
    private TextView txt_gethelp;
    /* access modifiers changed from: private */
    public String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_schedule_response);
        findId();
        this.imgLeftMenu.setOnClickListener(this);
        this.txt_gethelp.setOnClickListener(this);
        this.user_id = MySharedPref.getData(this, "user_id", "");
        new GetMyCaregiverTask().execute(new Void[0]);
    }

    private void findId() {
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        Rvgiverlist = (RecyclerView) findViewById(R.id.Rvgiverlist);
        this.txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);
    }

    public void onClick(View view) {
        if (view == this.imgLeftMenu) {
            finish();
        } else if (view == this.txt_gethelp) {
            startActivity(new Intent(this, GetHelpActivity.class));
        }
    }

    private class GetMyCaregiverTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f145pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetMyCaregiverTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_client_request?user_id=" + ScheduleResponseActivity.this.user_id;
            this.iserror = false;
            this.isValue = false;
            this.f145pd = ProgressDialog.show(ScheduleResponseActivity.this, "", "Please wait...", true);
            this.obj = null;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            try {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(this.url);
                try {
                    if (httpPost.toString().contains("unsuccessful")) {
                        this.isValue = true;
                        return null;
                    }
                    String entityUtils = EntityUtils.toString(defaultHttpClient.execute(httpPost).getEntity());
                    PrintStream printStream = System.out;
                    printStream.println("-------Response------" + entityUtils);
                    this.obj = new JSONObject(entityUtils);
                    return null;
                } catch (Exception e) {
                    this.f145pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f145pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f145pd.dismiss();
            try {
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    ScheduleResponseActivity.Rvgiverlist.setHasFixedSize(true);
                    ScheduleResponseActivity.Rvgiverlist.setLayoutManager(new LinearLayoutManager(ScheduleResponseActivity.this));
                    ScheduleResponseActivity.Rvgiverlist.setItemAnimator(new DefaultItemAnimator());
                    ScheduleResponseActivity.Rvgiverlist.setAdapter(new CareGiverResponseListAdapter(ScheduleResponseActivity.this, jSONArray));
                    return;
                }
                ScheduleResponseActivity scheduleResponseActivity = ScheduleResponseActivity.this;
                Toast.makeText(scheduleResponseActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
