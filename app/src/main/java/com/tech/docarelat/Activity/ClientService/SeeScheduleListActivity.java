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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tech.docarelat.Adapter.CareGiverListAdapter;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class SeeScheduleListActivity extends AppCompatActivity implements View.OnClickListener {
    /* access modifiers changed from: private */
    public RecyclerView Rvgiverlist;
    private Button btn_anyrequest;
    private Button btn_register;
    private final RequestParams category = new RequestParams();
    private final AsyncHttpClient client = new AsyncHttpClient();
    private String giver_id = "";
    private ImageButton imgLeftMenu;
    /* access modifiers changed from: private */
    public String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_see_schedule_list);
        findId();
        this.user_id = MySharedPref.getData(this, "user_id", "");
        new GetMyCaregiverTask().execute(new Void[0]);
        this.imgLeftMenu.setOnClickListener(this);
        this.btn_register.setOnClickListener(this);
        this.btn_anyrequest.setOnClickListener(this);
    }

    private void findId() {
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        this.Rvgiverlist = (RecyclerView) findViewById(R.id.Rvgiverlist);
        this.btn_register = (Button) findViewById(R.id.btn_register);
        this.btn_anyrequest = (Button) findViewById(R.id.btn_anyrequest);
    }

    public void onClick(View view) {
        if (view == this.imgLeftMenu) {
            finish();
        } else if (view == this.btn_register) {
            if (ScheduleCareGiverActivity.days == 0) {
                ScheduleCareGiverActivity.days = 1;
            }
            if (CareGiverListAdapter.list != null) {
                if (CareGiverListAdapter.list.size() > 0) {
                    for (int i = 0; i < CareGiverListAdapter.list.size(); i++) {
                        if (this.giver_id.equalsIgnoreCase("")) {
                            this.giver_id = CareGiverListAdapter.list.get(i);
                        } else {
                            this.giver_id += "," + CareGiverListAdapter.list.get(i);
                        }
                    }
                    if (ScheduleCareGiverActivity.isEdit.booleanValue()) {
                        ScheduleCareGiverActivity.isEdit = false;
                        updateRequest();
                        return;
                    }
                    sendRequest();
                } else if (ScheduleCareGiverActivity.isEdit.booleanValue()) {
                    ScheduleCareGiverActivity.isEdit = false;
                    updateRequest();
                } else {
                    sendRequest();
                }
            } else if (ScheduleCareGiverActivity.isEdit.booleanValue()) {
                ScheduleCareGiverActivity.isEdit = false;
                updateRequest();
            } else {
                sendRequest();
            }
        } else if (view == this.btn_anyrequest) {
            if (ScheduleCareGiverActivity.days == 0) {
                ScheduleCareGiverActivity.days = 1;
            }
            if (ScheduleCareGiverActivity.isEdit.booleanValue()) {
                ScheduleCareGiverActivity.isEdit = false;
                updateRequest();
                return;
            }
            sendRequest();
        }
    }

    private class GetMyCaregiverTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f146pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetMyCaregiverTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_giver_by_client?user_id=" + SeeScheduleListActivity.this.user_id + "&gender=" + ScheduleCareGiverActivity.gender;
            this.iserror = false;
            this.isValue = false;
            this.f146pd = ProgressDialog.show(SeeScheduleListActivity.this, "", "Please wait...", true);
            this.obj = null;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            try {
                Log.e("URL", "" + this.url);
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
                    this.f146pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f146pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f146pd.dismiss();
            try {
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    SeeScheduleListActivity.this.Rvgiverlist.setHasFixedSize(true);
                    SeeScheduleListActivity.this.Rvgiverlist.setLayoutManager(new LinearLayoutManager(SeeScheduleListActivity.this));
                    SeeScheduleListActivity.this.Rvgiverlist.setItemAnimator(new DefaultItemAnimator());
                    SeeScheduleListActivity.this.Rvgiverlist.setAdapter(new CareGiverListAdapter(SeeScheduleListActivity.this, jSONArray));
                    return;
                }
                SeeScheduleListActivity seeScheduleListActivity = SeeScheduleListActivity.this;
                Toast.makeText(seeScheduleListActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendRequest() {
        final ProgressDialog show = ProgressDialog.show(this, "", "Please wait...", true);
        try {
            this.category.put("user_id", this.user_id);
            this.category.put("giver_id", this.giver_id);
            this.category.put(FirebaseAnalytics.Param.START_DATE, ScheduleCareGiverActivity.start_date);
            this.category.put("start_time", ScheduleCareGiverActivity.start_time);
            this.category.put(FirebaseAnalytics.Param.END_DATE, ScheduleCareGiverActivity.end_date);
            this.category.put("end_time", ScheduleCareGiverActivity.end_time);
            this.category.put("work_type", ScheduleCareGiverActivity.time_type);
            this.category.put("service_type", ScheduleCareGiverActivity.type);
            this.category.put("gender", ScheduleCareGiverActivity.gender);
            this.category.put("address", ScheduleCareGiverActivity.address_one);
            this.category.put("address1", ScheduleCareGiverActivity.address_two);
            this.category.put("day_count", ScheduleCareGiverActivity.days);
            this.category.put("state", ScheduleCareGiverActivity.state);
            this.category.put("city", ScheduleCareGiverActivity.city);
            this.category.put("zip", ScheduleCareGiverActivity.zip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("======== category params ========== ");
        System.out.println(this.category);
        try {
            this.client.post("http://showupcare.com/WECARE/webservice/request_nearbuy_giver?", this.category, new JsonHttpResponseHandler() {
                public void onFailure(int i, Header[] headerArr, String str, Throwable th) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------onFailure-------------" + str + " Status code ::: " + i);
                    Toast.makeText(SeeScheduleListActivity.this, "Something went wrong, Try Again !", Toast.LENGTH_LONG).show();
                }

                public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------response----------- " + jSONObject);
                    try {
                        if (jSONObject.getString("status").equals("1")) {
                            Toast.makeText(SeeScheduleListActivity.this, "Success", Toast.LENGTH_LONG).show();
                            ScheduleCareGiverActivity.schedulecareactivity.finish();
                            SeeScheduleListActivity.this.finish();
                            return;
                        }
                        SeeScheduleListActivity seeScheduleListActivity = SeeScheduleListActivity.this;
                        Toast.makeText(seeScheduleListActivity, "" + jSONObject.getString("result"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
            Toast.makeText(this, "Sorry server is busy", Toast.LENGTH_LONG).show();
        }
    }

    public void updateRequest() {
        final ProgressDialog show = ProgressDialog.show(this, "", "Please wait...", true);
        try {
            this.category.put("user_id", this.user_id);
            this.category.put("giver_id", this.giver_id);
            this.category.put(FirebaseAnalytics.Param.START_DATE, ScheduleCareGiverActivity.start_date);
            this.category.put("start_time", ScheduleCareGiverActivity.start_time);
            this.category.put(FirebaseAnalytics.Param.END_DATE, ScheduleCareGiverActivity.end_date);
            this.category.put("end_time", ScheduleCareGiverActivity.end_time);
            this.category.put("work_type", ScheduleCareGiverActivity.time_type);
            this.category.put("service_type", ScheduleCareGiverActivity.type);
            this.category.put("gender", ScheduleCareGiverActivity.gender);
            this.category.put("address", ScheduleCareGiverActivity.address_one);
            this.category.put("address1", ScheduleCareGiverActivity.address_two);
            this.category.put("day_count", ScheduleCareGiverActivity.days);
            this.category.put("state", ScheduleCareGiverActivity.state);
            this.category.put("city", ScheduleCareGiverActivity.city);
            this.category.put("zip", ScheduleCareGiverActivity.zip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("======== category params ========== ");
        System.out.println(this.category);
        try {
            this.client.post("http://showupcare.com/WECARE/webservice/request_nearbuy_giver?", this.category, new JsonHttpResponseHandler() {
                public void onFailure(int i, Header[] headerArr, String str, Throwable th) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------onFailure-------------" + str + " Status code ::: " + i);
                    Toast.makeText(SeeScheduleListActivity.this, "Something went wrong, Try Again !", Toast.LENGTH_LONG).show();
                }

                public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------response----------- " + jSONObject);
                    try {
                        if (jSONObject.getString("status").equals("1")) {
                            Toast.makeText(SeeScheduleListActivity.this, "Success", Toast.LENGTH_LONG).show();
                            ScheduleCareGiverActivity.schedulecareactivity.finish();
                            SeeScheduleListActivity.this.finish();
                            return;
                        }
                        SeeScheduleListActivity seeScheduleListActivity = SeeScheduleListActivity.this;
                        Toast.makeText(seeScheduleListActivity, "" + jSONObject.getString("result"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
            Toast.makeText(this, "Sorry server is busy", Toast.LENGTH_LONG).show();
        }
    }
}
