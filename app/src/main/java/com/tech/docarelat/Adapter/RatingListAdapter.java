package com.tech.docarelat.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import com.willy.ratingbar.ScaleRatingBar;
import java.io.PrintStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.ClientCookie;

public class RatingListAdapter extends RecyclerView.Adapter<RatingListAdapter.ViewHolder> {
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public JSONArray arr;
    private final RequestParams category = new RequestParams();
    private final AsyncHttpClient client = new AsyncHttpClient();
    /* access modifiers changed from: private */
    public String comment;
    /* access modifiers changed from: private */
    public String giver_id;
    private String user_id;

    public RatingListAdapter(Activity activity2, JSONArray jSONArray) {
        this.activity = activity2;
        this.arr = jSONArray;
        this.user_id = MySharedPref.getData(activity2, "id", (String) null);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rating_list, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        try {
            TextView textView = viewHolder.txt_name;
            textView.setText(this.arr.getJSONObject(i).getJSONObject("user_details").getString("first_name") + " " + this.arr.getJSONObject(i).getJSONObject("user_details").getString("last_name"));
            viewHolder.txt_number.setText(this.arr.getJSONObject(i).getJSONObject("user_details").getString(PhoneAuthProvider.PROVIDER_ID));
            viewHolder.btn_submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        Float valueOf = Float.valueOf(viewHolder.simpleRatingBar.getRating());
                        String unused = RatingListAdapter.this.giver_id = RatingListAdapter.this.arr.getJSONObject(i).getJSONObject("user_details").getString("id");
                        String unused2 = RatingListAdapter.this.comment = viewHolder.edt_comment.getText().toString();
                        RatingListAdapter.this.setUserComment(viewHolder.edt_comment, valueOf.floatValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    public int getItemCount() {
        return this.arr.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn_submit;
        EditText edt_comment;
        ScaleRatingBar simpleRatingBar;
        TextView txt_name;
        TextView txt_number;

        public void onClick(View view) {
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.btn_submit = (Button) view.findViewById(R.id.btn_submit);
            this.edt_comment = (EditText) view.findViewById(R.id.edt_comment);
            this.simpleRatingBar = (ScaleRatingBar) view.findViewById(R.id.simpleRatingBar);
            this.txt_number = (TextView) view.findViewById(R.id.txt_number);
            this.txt_name = (TextView) view.findViewById(R.id.txt_name);
        }
    }

    public void setUserComment(final EditText editText, float f) {
        final ProgressDialog show = ProgressDialog.show(this.activity, "", "Please wait...", true);
        try {
            this.category.put("user_id", this.user_id);
            this.category.put("giver_id", this.giver_id);
            RequestParams requestParams = this.category;
            requestParams.put("rating", "" + f);
            this.category.put(ClientCookie.COMMENT_ATTR, this.comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("======== category params ========== ");
        System.out.println(this.category);
        try {
            this.client.post("http://showupcare.com/WECARE/webservice/add_client_review?", this.category, new JsonHttpResponseHandler() {
                public void onFailure(int i, Header[] headerArr, String str, Throwable th) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------onFailure-------------" + str + " Status code ::: " + i);
                    Toast.makeText(RatingListAdapter.this.activity, "Something went wrong, Try Again !", Toast.LENGTH_LONG).show();
                }

                public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------response----------- " + jSONObject);
                    try {
                        if (jSONObject.getString("status").equals("1")) {
                            editText.setText("");
                            String unused = RatingListAdapter.this.comment = "";
                            Toast.makeText(RatingListAdapter.this.activity, "Success",  Toast.LENGTH_LONG).show();
                            return;
                        }
                        Activity access$300 = RatingListAdapter.this.activity;
                        Toast.makeText(access$300, "" + jSONObject.getString("result"),  Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
            Toast.makeText(this.activity, "Sorry server is busy",  Toast.LENGTH_LONG).show();
        }
    }
}
