package com.tech.docarelat.Activity.ClientService;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.EmailAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tech.docarelat.Activity.Main.LandingActivity;
import com.tech.docarelat.Activity.Main.MainActivity;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class ClientLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_login;
    private final RequestParams category = new RequestParams();
    private final AsyncHttpClient client = new AsyncHttpClient();
    private EditText ed_email;
    private EditText edt_pass;
    private String email;
    private String pass;
    private String regId;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_client_login);
        findId();
        this.regId = MySharedPref.getData(this, "regId", "");
        this.btn_login.setOnClickListener(this);
    }

    private void findId() {
        this.ed_email = (EditText) findViewById(R.id.ed_email);
        this.edt_pass = (EditText) findViewById(R.id.edt_pass);
        this.btn_login = (Button) findViewById(R.id.btn_login);
    }

    public void onClick(View view) {
        if (view == this.btn_login) {
            this.email = this.ed_email.getText().toString();
            this.pass = this.edt_pass.getText().toString();
            if (this.email.equalsIgnoreCase("")) {
                this.ed_email.setError("Enter Email");
            } else if (this.pass.equalsIgnoreCase("")) {
                this.edt_pass.setError("Enter Password");
            } else {
                login();
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void login() {

        final ProgressDialog show = ProgressDialog.show(this, "", "Please wait...", true);
        try {
            this.category.put("username", this.email);
            this.category.put(EmailAuthProvider.PROVIDER_ID, this.pass);
            this.category.put("type", "CLIENT");
            this.category.put("register_id", this.regId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("======== category params ========== ");
        System.out.println(this.category);

        try {
            this.client.post("http://showupcare.com/WECARE/webservice/login?", this.category
                    , new JsonHttpResponseHandler() {

                public void onFailure(int i, Header[] headerArr, String str, Throwable th) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------onFailure-------------" + str + " Status code ::: " + i);
                    Toast.makeText(ClientLoginActivity.this, "Something went wrong, Try Again !", 1).show();
                }

                public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------response----------- " + jSONObject);
                    try {
                        if (jSONObject.getString("status").equals("1")) {
                            Context applicationContext = ClientLoginActivity.this.getApplicationContext();
                            MySharedPref.saveData(applicationContext, "ldata", "" + jSONObject);
                            MySharedPref.saveData(ClientLoginActivity.this.getApplicationContext(), "type", "CLIENT");
                            Context applicationContext2 = ClientLoginActivity.this.getApplicationContext();
                            MySharedPref.saveData(applicationContext2, "user_id", "" + jSONObject.getJSONObject("result").getString("id"));
                            ClientLoginActivity.this.startActivity(new Intent(ClientLoginActivity.this, ClientHomeActivity.class));
                            if (LandingActivity.landingActivity != null) {
                                LandingActivity.landingActivity.finish();
                                MainActivity.mainactivity.finish();
                            }
                            ClientLoginActivity.this.finish();
                            return;
                        }
                        ClientLoginActivity clientLoginActivity = ClientLoginActivity.this;
                        Toast.makeText(clientLoginActivity, "" + jSONObject.getString("result"), 1).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e2) {
            e2.printStackTrace();
            Toast.makeText(this, "Sorry server is busy", 0).show();
        }
    }
}
