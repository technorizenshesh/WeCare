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
import com.tech.docarelat.Activity.CareGiver.DeligetsNearYouActivity;
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

public class CareGiverRequistListAdapter extends
        RecyclerView.Adapter<CareGiverRequistListAdapter.ViewHolder> {
    public static List<String> list;
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public JSONArray arr;
    /* access modifiers changed from: private */
    public String request_id;
    /* access modifiers changed from: private */
    public String user_id;

    public CareGiverRequistListAdapter(Activity activity2, JSONArray jSONArray) {
        this.activity = activity2;
        this.arr = jSONArray;
        this.user_id = MySharedPref.getData(activity2, "user_id", (String) null);
        list = new ArrayList();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_item_view, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        try {
            viewHolder.txt_name.setText(this.arr.getJSONObject(i).getString("user_name"));
            viewHolder.txt_number.setText(this.arr.getJSONObject(i).getString("user_phone"));
            TextView textView = viewHolder.txt_amount;
            textView.setText("Total Amount : $" + this.arr.getJSONObject(i).getString("total_amount"));
            TextView textView2 = viewHolder.txt_ttl_amount;
            textView2.setText("$" + this.arr.getJSONObject(i).getString("total_amount"));
            TextView textView3 = viewHolder.txt_datetime;
            textView3.setText("Start Date :- " + this.arr.getJSONObject(i).getString(FirebaseAnalytics.Param.START_DATE));
            TextView textView4 = viewHolder.txt_enddatetime;
            textView4.setText("End Date :- " + this.arr.getJSONObject(i).getString(FirebaseAnalytics.Param.END_DATE));
            TextView textView5 = viewHolder.txt_stime;
            textView5.setText("Start Time :- " + this.arr.getJSONObject(i).getString("start_time"));
            TextView textView6 = viewHolder.txt_etime;
            textView6.setText("End Time :- " + this.arr.getJSONObject(i).getString("end_time"));
            TextView textView7 = viewHolder.txt_city;
            textView7.setText("City : " + this.arr.getJSONObject(i).getString("city"));
            TextView textView8 = viewHolder.txt_state;
            textView8.setText("State : " + this.arr.getJSONObject(i).getString("state"));
            TextView textView9 = viewHolder.txt_zip;
            textView9.setText("Zip : " + this.arr.getJSONObject(i).getString("zip"));
            viewHolder.txt_address.setText(this.arr.getJSONObject(i).getString("address"));
            viewHolder.txt_addresstwo.setText(this.arr.getJSONObject(i).getString("address1"));

            viewHolder.btn_accept.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        String unused = CareGiverRequistListAdapter.this.request_id = CareGiverRequistListAdapter.this.arr.getJSONObject(i).getString("id");
                        new AcceptTask().execute(new Void[0]);
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
        Button btn_accept;
        ImageView img_user;
        TextView txt_address;
        TextView txt_addresstwo;
        TextView txt_amount;
        TextView txt_city;
        TextView txt_datetime;
        TextView txt_enddatetime;
        TextView txt_etime;
        TextView txt_name;
        TextView txt_number;
        TextView txt_state;
        TextView txt_stime;
        TextView txt_ttl_amount;
        TextView txt_zip;

        public void onClick(View view) {
        }

        public ViewHolder(View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.txt_city = (TextView) view.findViewById(R.id.txt_city);
            this.txt_state = (TextView) view.findViewById(R.id.txt_state);
            this.txt_zip = (TextView) view.findViewById(R.id.txt_zip);
            this.txt_addresstwo = (TextView) view.findViewById(R.id.txt_addresstwo);
            this.txt_number = (TextView) view.findViewById(R.id.txt_number);
            this.txt_datetime = (TextView) view.findViewById(R.id.txt_datetime);
            this.img_user = (ImageView) view.findViewById(R.id.img_user);
            this.txt_address = (TextView) view.findViewById(R.id.txt_address);
            this.btn_accept = (Button) view.findViewById(R.id.btn_accept);
            this.txt_enddatetime = (TextView) view.findViewById(R.id.txt_enddatetime);
            this.txt_stime = (TextView) view.findViewById(R.id.txt_stime);
            this.txt_etime = (TextView) view.findViewById(R.id.txt_etime);
            this.txt_amount = (TextView) view.findViewById(R.id.txt_amount);
            this.txt_ttl_amount = (TextView) view.findViewById(R.id.txt_ttl_amount);
            view.setOnClickListener(this);
        }
    }

    private class AcceptTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f150pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private AcceptTask() {
            this.url = "http://showupcare.com/WECARE/webservice/accept_request?request_id=" + CareGiverRequistListAdapter.this.request_id + "&accept_giver_id=" + CareGiverRequistListAdapter.this.user_id + "&status=Accept";
            this.iserror = false;
            this.isValue = false;
            this.f150pd = ProgressDialog.show(CareGiverRequistListAdapter.this.activity, "", "Please wait...", true);
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
                    this.f150pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f150pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f150pd.dismiss();
            try {
                if (this.obj == null) {
                    Toast.makeText(CareGiverRequistListAdapter.this.activity, "Server is Busy", Toast.LENGTH_LONG).show();
                } else if (this.obj.getString("status").equals("1")) {
                    Toast.makeText(CareGiverRequistListAdapter.this.activity, "Accepted Successfully", Toast.LENGTH_LONG).show();
                    new GetMyRequests().execute(new Void[0]);
                } else {
                    Activity access$400 = CareGiverRequistListAdapter.this.activity;
                    Toast.makeText(access$400, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
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
        final ProgressDialog f151pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetMyRequests() {
            this.url = "http://showupcare.com/WECARE/webservice/get_pending_request?giver_id=" + CareGiverRequistListAdapter.this.user_id;
            this.iserror = false;
            this.isValue = false;
            this.f151pd = ProgressDialog.show(CareGiverRequistListAdapter.this.activity, "", "Please wait...", true);
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
                    this.f151pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f151pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f151pd.dismiss();
            try {
                if (this.obj == null) {
                    Toast.makeText(CareGiverRequistListAdapter.this.activity, "Server is Busy", Toast.LENGTH_LONG).show();
                } else if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    DeligetsNearYouActivity.Rvgiverlist.setHasFixedSize(true);
                    DeligetsNearYouActivity.Rvgiverlist.setLayoutManager(new LinearLayoutManager(CareGiverRequistListAdapter.this.activity));
                    DeligetsNearYouActivity.Rvgiverlist.setItemAnimator(new DefaultItemAnimator());
                    DeligetsNearYouActivity.Rvgiverlist.setAdapter(new CareGiverRequistListAdapter(CareGiverRequistListAdapter.this.activity, jSONArray));
                } else {
                    Activity access$400 = CareGiverRequistListAdapter.this.activity;
                    Toast.makeText(access$400, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
                    JSONArray jSONArray2 = new JSONArray();
                    DeligetsNearYouActivity.Rvgiverlist.setHasFixedSize(true);
                    DeligetsNearYouActivity.Rvgiverlist.setLayoutManager(new LinearLayoutManager(CareGiverRequistListAdapter.this.activity));
                    DeligetsNearYouActivity.Rvgiverlist.setItemAnimator(new DefaultItemAnimator());
                    DeligetsNearYouActivity.Rvgiverlist.setAdapter(new CareGiverRequistListAdapter(CareGiverRequistListAdapter.this.activity, jSONArray2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
