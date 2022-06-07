package com.tech.docarelat.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tech.docarelat.Activity.CareGiver.ExistingDelegationsActivity;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class CareGiverExistingRequistListAdapter extends RecyclerView.Adapter<CareGiverExistingRequistListAdapter.ViewHolder> {
    public static List<String> list;
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public JSONArray arr;
    /* access modifiers changed from: private */
    public String request_id;
    /* access modifiers changed from: private */
    public String status = "";
    /* access modifiers changed from: private */
    public String user_id;

    public CareGiverExistingRequistListAdapter(Activity activity2, JSONArray jSONArray) {
        this.activity = activity2;
        this.arr = jSONArray;
        this.user_id = MySharedPref.getData(activity2, "user_id", (String) null);
        list = new ArrayList();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.existing_request_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        try {
            viewHolder.txt_name.setText(this.arr.getJSONObject(i).getString("user_name"));
            viewHolder.txt_number.setText(this.arr.getJSONObject(i).getString("user_phone"));
            TextView textView = viewHolder.txt_ttl_amount;
            textView.setText("$" + this.arr.getJSONObject(i).getString("total_amount"));
            TextView textView2 = viewHolder.txt_datetime;
            textView2.setText("Start Date :- " + this.arr.getJSONObject(i).getString(FirebaseAnalytics.Param.START_DATE));
            TextView textView3 = viewHolder.txt_enddatetime;
            textView3.setText("End Date :- " + this.arr.getJSONObject(i).getString(FirebaseAnalytics.Param.END_DATE));
            TextView textView4 = viewHolder.txt_stime;
            textView4.setText("Start Time :- " + this.arr.getJSONObject(i).getString("start_time"));
            TextView textView5 = viewHolder.txt_etime;
            textView5.setText("End Time :- " + this.arr.getJSONObject(i).getString("end_time"));
            viewHolder.txt_address.setText(this.arr.getJSONObject(i).getString("address"));
            viewHolder.txt_addresstwo.setText(this.arr.getJSONObject(i).getString("address1"));
            viewHolder.btn_reject.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        String unused = CareGiverExistingRequistListAdapter.this.request_id = CareGiverExistingRequistListAdapter.this.arr.getJSONObject(i).getString("id");
                        String unused2 = CareGiverExistingRequistListAdapter.this.status = "Pending";
                        new RejectTask().execute(new Void[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.btn_complete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        String unused = CareGiverExistingRequistListAdapter.this.request_id = CareGiverExistingRequistListAdapter.this.arr.getJSONObject(i).getString("id");
                        String unused2 = CareGiverExistingRequistListAdapter.this.status = "Complete";
                        new RejectTask().execute(new Void[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.arr.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn_complete;
        Button btn_reject;
        ImageView img_user;
        TextView txt_address;
        TextView txt_addresstwo;
        TextView txt_datetime;
        TextView txt_enddatetime;
        TextView txt_etime;
        TextView txt_name;
        TextView txt_number;
        TextView txt_stime;
        TextView txt_ttl_amount;

        public void onClick(View view) {
        }

        public ViewHolder(View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.txt_number = (TextView) view.findViewById(R.id.txt_number);
            this.txt_datetime = (TextView) view.findViewById(R.id.txt_datetime);
            this.img_user = (ImageView) view.findViewById(R.id.img_user);
            this.txt_address = (TextView) view.findViewById(R.id.txt_address);
            this.btn_reject = (Button) view.findViewById(R.id.btn_reject);
            this.btn_complete = (Button) view.findViewById(R.id.btn_complete);
            this.txt_enddatetime = (TextView) view.findViewById(R.id.txt_enddatetime);
            this.txt_ttl_amount = (TextView) view.findViewById(R.id.txt_ttl_amount);
            this.txt_addresstwo = (TextView) view.findViewById(R.id.txt_addresstwo);
            this.txt_stime = (TextView) view.findViewById(R.id.txt_stime);
            this.txt_etime = (TextView) view.findViewById(R.id.txt_etime);
            view.setOnClickListener(this);
        }
    }

    private class RejectTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f149pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private RejectTask() {
            this.url = "http://showupcare.com/WECARE/webservice/change_giver_status?request_id=" + CareGiverExistingRequistListAdapter.this.request_id + "&accept_giver_id=" + CareGiverExistingRequistListAdapter.this.user_id + "&status=" + CareGiverExistingRequistListAdapter.this.status;
            this.iserror = false;
            this.isValue = false;
            this.f149pd = ProgressDialog.show(CareGiverExistingRequistListAdapter.this.activity, "", "Please wait...", true);
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
                    this.f149pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f149pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f149pd.dismiss();
            try {
                if (this.obj == null) {
                    Toast.makeText(CareGiverExistingRequistListAdapter.this.activity, "Server is Busy", Toast.LENGTH_LONG).show();
                } else if (this.obj.getString("status").equals("1")) {
                    Toast.makeText(CareGiverExistingRequistListAdapter.this.activity, "Success", Toast.LENGTH_LONG).show();
                    new GetMyRequests().execute(new Void[0]);
                } else {
                    Activity access$500 = CareGiverExistingRequistListAdapter.this.activity;
                    Toast.makeText(access$500, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetMyRequests extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f148pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetMyRequests() {
            this.url = "http://showupcare.com/WECARE/webservice/get_accepted_request?giver_id=" + CareGiverExistingRequistListAdapter.this.user_id;
            this.iserror = false;
            this.isValue = false;
            this.f148pd = ProgressDialog.show(CareGiverExistingRequistListAdapter.this.activity, "", "Please wait...", true);
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
                    this.f148pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f148pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f148pd.dismiss();
            try {
                if (this.obj == null) {
                    Toast.makeText(CareGiverExistingRequistListAdapter.this.activity, "Server is Busy", Toast.LENGTH_LONG).show();
                } else if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    ExistingDelegationsActivity.RvExistinggiverlist.setHasFixedSize(true);
                    ExistingDelegationsActivity.RvExistinggiverlist.setLayoutManager(new LinearLayoutManager(CareGiverExistingRequistListAdapter.this.activity));
                    ExistingDelegationsActivity.RvExistinggiverlist.setItemAnimator(new DefaultItemAnimator());
                    ExistingDelegationsActivity.RvExistinggiverlist.setAdapter(new CareGiverExistingRequistListAdapter(CareGiverExistingRequistListAdapter.this.activity, jSONArray));
                } else {
                    Activity access$500 = CareGiverExistingRequistListAdapter.this.activity;
                    Toast.makeText(access$500, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
                    JSONArray jSONArray2 = new JSONArray();
                    ExistingDelegationsActivity.RvExistinggiverlist.setHasFixedSize(true);
                    ExistingDelegationsActivity.RvExistinggiverlist.setLayoutManager(new LinearLayoutManager(CareGiverExistingRequistListAdapter.this.activity));
                    ExistingDelegationsActivity.RvExistinggiverlist.setItemAnimator(new DefaultItemAnimator());
                    ExistingDelegationsActivity.RvExistinggiverlist.setAdapter(new CareGiverExistingRequistListAdapter(CareGiverExistingRequistListAdapter.this.activity, jSONArray2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
