package com.tech.docarelat.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tech.docarelat.Activity.ClientService.ScheduleCareGiverActivity;
import com.tech.docarelat.Activity.ClientService.ScheduleResponseActivity;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class CareGiverResponseListAdapter extends RecyclerView.Adapter<CareGiverResponseListAdapter.ViewHolder> {
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public JSONArray arr;
    /* access modifiers changed from: private */
    public String request_id;
    /* access modifiers changed from: private */
    public String user_id;

    public CareGiverResponseListAdapter(Activity activity2, JSONArray jSONArray) {
        this.activity = activity2;
        this.arr = jSONArray;
        this.user_id = MySharedPref.getData(activity2, "user_id", (String) null);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycaregiver_responselist, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = viewHolder;
        final int i2 = i;
        try {
            if (this.arr.getJSONObject(i2).getString("accept_giver_id").equalsIgnoreCase("")) {
                viewHolder2.txt_name.setText("Not Responded..!!!");
                viewHolder2.txt_number.setText("");
                TextView textView = viewHolder2.txt_datetime;
                textView.setText("Start : " + this.arr.getJSONObject(i2).getString(FirebaseAnalytics.Param.START_DATE) + " , " + this.arr.getJSONObject(i2).getString("start_time"));
                TextView textView2 = viewHolder2.txt_enddatetime;
                textView2.setText("End : " + this.arr.getJSONObject(i2).getString(FirebaseAnalytics.Param.END_DATE) + " , " + this.arr.getJSONObject(i2).getString("end_time"));
                TextView textView3 = viewHolder2.txt_amount;
                textView3.setText("Amount : " + this.arr.getJSONObject(i2).getString("total_amount") + "$");
                viewHolder2.img_edt.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(CareGiverResponseListAdapter.this.activity, ScheduleCareGiverActivity.class);
                            intent.putExtra("data", "" + CareGiverResponseListAdapter.this.arr.getJSONObject(i2));
                            CareGiverResponseListAdapter.this.activity.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                viewHolder2.img_delete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            String unused = CareGiverResponseListAdapter.this.request_id = CareGiverResponseListAdapter.this.arr.getJSONObject(i2).getString("id");
                            new DeleteTask().execute(new Void[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return;
            }
            TextView textView4 = viewHolder2.txt_name;
            textView4.setText(this.arr.getJSONObject(i2).getJSONObject("user_details").getString("first_name") + " " + this.arr.getJSONObject(i2).getJSONObject("user_details").getString("last_name"));
            viewHolder2.txt_number.setText(this.arr.getJSONObject(i2).getJSONObject("user_details").getString(PhoneAuthProvider.PROVIDER_ID));
            TextView textView5 = viewHolder2.txt_datetime;
            textView5.setText("Start : " + this.arr.getJSONObject(i2).getString(FirebaseAnalytics.Param.START_DATE) + " , " + this.arr.getJSONObject(i2).getString("start_time"));
            TextView textView6 = viewHolder2.txt_enddatetime;
            textView6.setText("End : " + this.arr.getJSONObject(i2).getString(FirebaseAnalytics.Param.END_DATE) + " , " + this.arr.getJSONObject(i2).getString("end_time"));
            TextView textView7 = viewHolder2.txt_amount;
            textView7.setText("Amount : " + this.arr.getJSONObject(i2).getString("total_amount") + "$");
            viewHolder2.img_edt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(CareGiverResponseListAdapter.this.activity, ScheduleCareGiverActivity.class);
                        intent.putExtra("data", "" + CareGiverResponseListAdapter.this.arr.getJSONObject(i2));
                        CareGiverResponseListAdapter.this.activity.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder2.img_delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        String unused = CareGiverResponseListAdapter.this.request_id = CareGiverResponseListAdapter.this.arr.getJSONObject(i2).getString("id");
                        new DeleteTask().execute(new Void[0]);
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
        ImageView img_delete;
        ImageView img_edt;
        TextView txt_amount;
        TextView txt_datetime;
        TextView txt_enddatetime;
        TextView txt_name;
        TextView txt_number;

        public void onClick(View view) {
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.img_edt = (ImageView) view.findViewById(R.id.img_edt);
            this.img_delete = (ImageView) view.findViewById(R.id.img_delete);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.txt_number = (TextView) view.findViewById(R.id.txt_number);
            this.txt_datetime = (TextView) view.findViewById(R.id.txt_datetime);
            this.txt_enddatetime = (TextView) view.findViewById(R.id.txt_enddatetime);
            this.txt_amount = (TextView) view.findViewById(R.id.txt_amount);
        }
    }

    private class DeleteTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f152pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private DeleteTask() {
            this.url = "http://showupcare.com/WECARE/webservice/delete_request?req_id=" + CareGiverResponseListAdapter.this.request_id;
            this.iserror = false;
            this.isValue = false;
            this.f152pd = ProgressDialog.show(CareGiverResponseListAdapter.this.activity, "", "Please wait...", true);
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
                    this.f152pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f152pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f152pd.dismiss();
            try {
                if (this.obj.getString("status").equals("1")) {
                    new GetMyCaregiverTask().execute(new Void[0]);
                    return;
                }
                Activity access$000 = CareGiverResponseListAdapter.this.activity;
                Toast.makeText(access$000, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetMyCaregiverTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f153pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetMyCaregiverTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_client_request?user_id=" + CareGiverResponseListAdapter.this.user_id;
            this.iserror = false;
            this.isValue = false;
            this.f153pd = ProgressDialog.show(CareGiverResponseListAdapter.this.activity, "", "Please wait...", true);
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
                    this.f153pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f153pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f153pd.dismiss();
            try {
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    ScheduleResponseActivity.Rvgiverlist.setHasFixedSize(true);
                    ScheduleResponseActivity.Rvgiverlist.setLayoutManager(new LinearLayoutManager(CareGiverResponseListAdapter.this.activity));
                    ScheduleResponseActivity.Rvgiverlist.setItemAnimator(new DefaultItemAnimator());
                    ScheduleResponseActivity.Rvgiverlist.setAdapter(new CareGiverResponseListAdapter(CareGiverResponseListAdapter.this.activity, jSONArray));
                    return;
                }
                Activity access$000 = CareGiverResponseListAdapter.this.activity;
                Toast.makeText(access$000, "" + this.obj.getString("result"), 1).show();
                JSONArray jSONArray2 = new JSONArray();
                ScheduleResponseActivity.Rvgiverlist.setHasFixedSize(true);
                ScheduleResponseActivity.Rvgiverlist.setLayoutManager(new LinearLayoutManager(CareGiverResponseListAdapter.this.activity));
                ScheduleResponseActivity.Rvgiverlist.setItemAnimator(new DefaultItemAnimator());
                ScheduleResponseActivity.Rvgiverlist.setAdapter(new CareGiverResponseListAdapter(CareGiverResponseListAdapter.this.activity, jSONArray2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
