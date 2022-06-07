package com.tech.docarelat.Activity.ClientService;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.tech.docarelat.Adapter.CurrentCareGiverListAdapter;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ExtenTimeActivity extends AppCompatActivity implements View.OnClickListener {
    /* access modifiers changed from: private */
    public RecyclerView Rvcurrentgiverlist;
    private ImageButton imgLeftMenu;
    /* access modifiers changed from: private */
    public String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_exten_time);
        findId();
        this.user_id = MySharedPref.getData(this, "user_id", "");
        this.imgLeftMenu.setOnClickListener(this);
        new GetMyCaregiverTask().execute(new Void[0]);
    }

    private void findId() {
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        this.Rvcurrentgiverlist = (RecyclerView) findViewById(R.id.Rvcurrentgiverlist);
    }

    public void onClick(View view) {
        if (view == this.imgLeftMenu) {
            finish();
        }
    }

    private class GetMyCaregiverTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f139pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetMyCaregiverTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_current_giver_by_client?user_id=" + ExtenTimeActivity.this.user_id;
            this.iserror = false;
            this.isValue = false;
            this.f139pd = ProgressDialog.show(ExtenTimeActivity.this, "", "Please wait...", true);
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
                    this.f139pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f139pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f139pd.dismiss();
            try {
                if (this.obj == null) {
                    return;
                }
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    ExtenTimeActivity.this.Rvcurrentgiverlist.setHasFixedSize(true);
                    ExtenTimeActivity.this.Rvcurrentgiverlist.setLayoutManager(new LinearLayoutManager(ExtenTimeActivity.this));
                    ExtenTimeActivity.this.Rvcurrentgiverlist.setItemAnimator(new DefaultItemAnimator());
                    ExtenTimeActivity.this.Rvcurrentgiverlist.setAdapter(new CurrentCareGiverListAdapter(ExtenTimeActivity.this, jSONArray));
                    return;
                }
                ExtenTimeActivity extenTimeActivity = ExtenTimeActivity.this;
                Toast.makeText(extenTimeActivity, "" + this.obj.getString("result"), 1).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
