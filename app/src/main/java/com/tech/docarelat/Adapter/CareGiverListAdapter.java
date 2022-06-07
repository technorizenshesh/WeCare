package com.tech.docarelat.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class CareGiverListAdapter extends RecyclerView.Adapter<CareGiverListAdapter.ViewHolder> {
    public static List<String> list;
    private Activity activity;
    /* access modifiers changed from: private */
    public JSONArray arr;
    private String user_id;

    public CareGiverListAdapter(Activity activity2, JSONArray jSONArray) {
        this.activity = activity2;
        this.arr = jSONArray;
        this.user_id = MySharedPref.getData(activity2, "id", (String) null);
        list = new ArrayList();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycaregiver_list, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        try {
            TextView textView = viewHolder.txt_name;
            textView.setText(this.arr.getJSONObject(i).getJSONObject("user_details").getString("first_name") + " " + this.arr.getJSONObject(i).getJSONObject("user_details").getString("last_name"));
            viewHolder.txt_number.setText(this.arr.getJSONObject(i).getJSONObject("user_details").getString(PhoneAuthProvider.PROVIDER_ID));
            TextView textView2 = viewHolder.txt_datetime;
            textView2.setText(this.arr.getJSONObject(i).getString(FirebaseAnalytics.Param.START_DATE) + " , " + this.arr.getJSONObject(i).getString("start_time"));
            viewHolder.checkitem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (z) {
                        try {
                            Log.e("added", "" + CareGiverListAdapter.this.arr.getJSONObject(i).getJSONObject("user_details").getString("id"));
                            CareGiverListAdapter.list.add(CareGiverListAdapter.this.arr.getJSONObject(i).getJSONObject("user_details").getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Log.e("removed", "" + CareGiverListAdapter.this.arr.getJSONObject(i).getJSONObject("user_details").getString("id"));
                            CareGiverListAdapter.list.remove(CareGiverListAdapter.this.arr.getJSONObject(i).getJSONObject("user_details").getString("id"));
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
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
        CheckBox checkitem;
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
            this.checkitem = (CheckBox) view.findViewById(R.id.checkitem);
            view.setOnClickListener(this);
        }
    }
}
