package com.tech.docarelat.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tech.docarelat.Activity.ClientService.ScheduleCareGiverActivity;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import org.json.JSONArray;
import org.json.JSONException;

public class CurrentCareGiverListAdapter extends RecyclerView.Adapter<CurrentCareGiverListAdapter.ViewHolder> {
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public JSONArray arr;
    private String user_id;

    public CurrentCareGiverListAdapter(Activity activity2, JSONArray jSONArray) {
        this.activity = activity2;
        this.arr = jSONArray;
        this.user_id = MySharedPref.getData(activity2, "id", (String) null);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.current_giver_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        try {
            TextView textView = viewHolder.txt_name;
            textView.setText(this.arr.getJSONObject(i).getJSONObject("user_details").getString("first_name") + " " + this.arr.getJSONObject(i).getJSONObject("user_details").getString("last_name"));
            viewHolder.txt_number.setText(this.arr.getJSONObject(i).getJSONObject("user_details").getString(PhoneAuthProvider.PROVIDER_ID));
            TextView textView2 = viewHolder.txt_datetime;
            textView2.setText(this.arr.getJSONObject(i).getString(FirebaseAnalytics.Param.START_DATE) + " , " + this.arr.getJSONObject(i).getString("start_time"));
            viewHolder.btn_extend.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(CurrentCareGiverListAdapter.this.activity, ScheduleCareGiverActivity.class);
                        intent.putExtra("data", "" + CurrentCareGiverListAdapter.this.arr.getJSONObject(i));
                        intent.putExtra("status", "");
                        CurrentCareGiverListAdapter.this.activity.startActivity(intent);
                    } catch (Exception e) {
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
        Button btn_extend;
        ImageView img_user;
        TextView txt_datetime;
        TextView txt_name;
        TextView txt_number;

        public void onClick(View view) {
        }

        public ViewHolder(View view) {
            super(view);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
            this.txt_number = (TextView) view.findViewById(R.id.txt_number);
            this.txt_datetime = (TextView) view.findViewById(R.id.txt_datetime);
            this.img_user = (ImageView) view.findViewById(R.id.img_user);
            this.btn_extend = (Button) view.findViewById(R.id.btn_extend);
            view.setOnClickListener(this);
        }
    }
}
