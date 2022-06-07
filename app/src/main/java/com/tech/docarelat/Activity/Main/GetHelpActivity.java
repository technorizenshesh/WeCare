package com.tech.docarelat.Activity.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class GetHelpActivity extends AppCompatActivity {
    private Button btn_gethelp;
    private String user_id = "";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_get_help);
        this.btn_gethelp = (Button) findViewById(R.id.btn_gethelp);
        this.user_id = MySharedPref.getData(this, "user_id", "");
        this.btn_gethelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new GetHelpTask().execute(new Void[0]);
            }
        });
    }

    private class GetHelpTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f147pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetHelpTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_help";
            this.iserror = false;
            this.isValue = false;
            this.f147pd = ProgressDialog.show(GetHelpActivity.this, "", "Please wait...", true);
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
                    this.f147pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f147pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f147pd.dismiss();
            try {
                if (this.obj == null) {
                    Toast.makeText(GetHelpActivity.this, "Busy", Toast.LENGTH_LONG).show();
                } else if (this.obj.getString("status").equals("1")) {
                    Intent intent = new Intent("android.intent.action.DIAL");
                    intent.setData(Uri.parse("tel:" + this.obj.getJSONObject("result").getString("mobile")));
                    GetHelpActivity.this.startActivity(intent);
                } else {
                    GetHelpActivity getHelpActivity = GetHelpActivity.this;
                    Toast.makeText(getHelpActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
