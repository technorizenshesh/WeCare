package com.tech.docarelat.Activity.CareGiver;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.tech.docarelat.Adapter.CareGiverRequistListAdapter;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class DeligetsNearYouActivity extends AppCompatActivity implements View.OnClickListener {
    public static RecyclerView Rvgiverlist;
    private JSONArray arr;
    private Button btn_apply;
    private ImageButton imgLeftMenu;
    /* access modifiers changed from: private */
    public String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_deligets_near_you);
        findId();
        this.user_id = MySharedPref.getData(this, "user_id", "");
        this.imgLeftMenu.setOnClickListener(this);
        this.btn_apply.setOnClickListener(this);
        new GetMyRequests().execute(new Void[0]);
    }

    private void findId() {
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        Rvgiverlist = (RecyclerView) findViewById(R.id.Rvgiverlist);
        this.btn_apply = (Button) findViewById(R.id.btn_apply);
    }

    public void onClick(View view) {
        if (view == this.imgLeftMenu) {
            finish();
        } else if (view == this.btn_apply) {
            finish();
        }
    }

    private class GetMyRequests extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f131pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetMyRequests() {
            this.url = "http://showupcare.com/WECARE/webservice/get_pending_request?giver_id=" + DeligetsNearYouActivity.this.user_id;
            this.iserror = false;
            this.isValue = false;
            this.f131pd = ProgressDialog.show(DeligetsNearYouActivity.this, "", "Please wait...", true);
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
                    this.f131pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f131pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f131pd.dismiss();
            try {
                if (this.obj == null) {
                    Toast.makeText(DeligetsNearYouActivity.this, "Server is Busy", Toast.LENGTH_LONG).show();
                } else if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    DeligetsNearYouActivity.Rvgiverlist.setHasFixedSize(true);
                    DeligetsNearYouActivity.Rvgiverlist.setLayoutManager(new LinearLayoutManager(DeligetsNearYouActivity.this));
                    DeligetsNearYouActivity.Rvgiverlist.setItemAnimator(new DefaultItemAnimator());
                    DeligetsNearYouActivity.Rvgiverlist.setAdapter(new CareGiverRequistListAdapter(DeligetsNearYouActivity.this, jSONArray));
                } else {
                    DeligetsNearYouActivity deligetsNearYouActivity = DeligetsNearYouActivity.this;
                    Toast.makeText(deligetsNearYouActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
