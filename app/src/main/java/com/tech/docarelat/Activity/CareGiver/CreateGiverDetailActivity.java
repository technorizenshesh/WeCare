package com.tech.docarelat.Activity.CareGiver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.EmailAuthProvider;
import com.tech.docarelat.Activity.Main.GetHelpActivity;
import com.tech.docarelat.Adapter.MyAdapter;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.Constent.StateVO;
import com.tech.docarelat.R;

import java.io.PrintStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class CreateGiverDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity creategiverdetailactivity = null;
    public static String language = "";
    public static String[] language_list;
    public static String operation;
    public static String[] operation_list = {"10 miles radius", "20 miles radius", "30 miles radius", "40 miles radius", "50 miles radius"};
    public static String pass;
    public static String password;
    public static TextView txt_lang;
    public static String username;
    private Button btn_continue;
    private EditText ed_cpass;
    private EditText edt_pass;
    private EditText edt_username;
    ArrayList<String> list = null;
    private Spinner sp_language;
    private Spinner sp_operation;
    private TextView txt_gethelp;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_create_giver_detail);
        findId();
        creategiverdetailactivity = this;
        loadOprtaion();
        new GetLanguageTask().execute(new Void[0]);
        this.btn_continue.setOnClickListener(this);
        this.txt_gethelp.setOnClickListener(this);
        String data = MySharedPref.getData(this, "ldata", (String) null);
        if (data != null) {
            loadProData(data);
        } else {
            loadData();
        }
    }

    private void loadProData(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("result");
            Log.e("ldata", "" + jSONObject);
            this.edt_username.setText(jSONObject.getString("username"));
            this.edt_pass.setText(jSONObject.getString(EmailAuthProvider.PROVIDER_ID));
            this.ed_cpass.setText(jSONObject.getString(EmailAuthProvider.PROVIDER_ID));
            txt_lang.setText(jSONObject.getString("spoken_lang"));
            txt_lang.setVisibility(View.VISIBLE);
            language = jSONObject.getString("spoken_lang");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        if (MySharedPref.getData(this, "data", "").equals("yes")) {
            EditText editText = this.edt_username;
            editText.setText("" + MySharedPref.getData(this, "username", ""));
            EditText editText2 = this.edt_pass;
            editText2.setText("" + MySharedPref.getData(this, EmailAuthProvider.PROVIDER_ID, ""));
            EditText editText3 = this.ed_cpass;
            editText3.setText("" + MySharedPref.getData(this, "pass", ""));
            txt_lang.setVisibility(View.VISIBLE);
            TextView textView = txt_lang;
            textView.setText("" + MySharedPref.getData(this, "language", ""));
        }
    }

    private void findId() {
        this.btn_continue = (Button) findViewById(R.id.btn_continue);
        this.sp_language = (Spinner) findViewById(R.id.sp_language);
        this.sp_operation = (Spinner) findViewById(R.id.sp_operation);
        this.edt_username = (EditText) findViewById(R.id.edt_username);
        this.edt_pass = (EditText) findViewById(R.id.edt_pass);
        this.ed_cpass = (EditText) findViewById(R.id.ed_cpass);
        this.txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);
        txt_lang = (TextView) findViewById(R.id.txt_lang);
    }

    public void onClick(View view) {
        if (view == this.btn_continue) {
            validtae();
        } else if (view == this.txt_gethelp) {
            startActivity(new Intent(this, GetHelpActivity.class));
        }
    }

    private void validtae() {
        username = this.edt_username.getText().toString();
        password = this.edt_pass.getText().toString();
        pass = this.ed_cpass.getText().toString();
        this.list = MyAdapter.list;
        System.out.println("----size---" + this.list.size());
        int size = this.list.size();
        if (username.equalsIgnoreCase("")) {
            this.edt_username.setError("Enter User Name");
        } else if (username.length() < 6) {
            this.edt_username.setError("User Name is to short");
        } else if (password.equalsIgnoreCase("")) {
            this.edt_pass.setError("Enter password");
        } else if (password.length() < 6) {
            this.edt_pass.setError("password is to short");
        } else if (pass.equalsIgnoreCase("")) {
            this.ed_cpass.setError("Re-enter password");

        } else if (pass.length() < 6) {
            this.ed_cpass.setError("password is to short");

        } else {
            if (size == 0) {
                if (language.equalsIgnoreCase("")) {
                    Toast.makeText(creategiverdetailactivity, "Select language", Toast.LENGTH_LONG).show();
                } else if (password.equals(pass)) {
                    MySharedPref.saveData(this, "data", "yes");
                    MySharedPref.saveData(this, "username", username);
                    MySharedPref.saveData(this, EmailAuthProvider.PROVIDER_ID, password);
                    MySharedPref.saveData(this, "pass", pass);
                    startActivity(new Intent(this, CareGiverBankInfoActivity.class));
                } else {
                    Toast.makeText(this, "Password should be same....", Toast.LENGTH_LONG).show();
                }
            } else if (password.equals(pass)) {
                language = "";
                for (int i = 0; i < this.list.size(); i++) {
                    if (language.equalsIgnoreCase("")) {
                        language = this.list.get(i);
                    } else {
                        language += "," + this.list.get(i);
                    }
                }
                MySharedPref.saveData(this, "data", "yes");
                MySharedPref.saveData(this, "username", username);
                MySharedPref.saveData(this, EmailAuthProvider.PROVIDER_ID, password);
                MySharedPref.saveData(this, "pass", pass);
                MySharedPref.saveData(this, "language", language);
                startActivity(new Intent(this, CareGiverBankInfoActivity.class));
            } else {
                Toast.makeText(this, "Password should be same....", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class GetLanguageTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        final ProgressDialog f130pd;
        String url;

        public void onPreExecute() {
        }

        private GetLanguageTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_languages";
            this.iserror = false;
            this.isValue = false;
            this.f130pd = ProgressDialog.show(CreateGiverDetailActivity.this, "", "Please wait...", true);
            this.obj = null;
        }


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
                    this.f130pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f130pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f130pd.dismiss();
            try {
                if (this.obj == null) {
                    Toast.makeText(CreateGiverDetailActivity.this, "Busy", Toast.LENGTH_LONG).show();
                } else if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    CreateGiverDetailActivity.language_list = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        CreateGiverDetailActivity.language_list[i] = jSONArray.getJSONObject(i).getString("language");
                    }
                    CreateGiverDetailActivity.this.loadLanguage();
                } else {
                    CreateGiverDetailActivity createGiverDetailActivity = CreateGiverDetailActivity.this;
                    Toast.makeText(createGiverDetailActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void loadLanguage() {

        ArrayList arrayList = new ArrayList();
        MyAdapter.list = new ArrayList<>();
        for (int i = 0; i < language_list.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(language_list[i]);
            if (MySharedPref.getData(this, "data", "").equals("yes")) {
                String[] split = MySharedPref.getData(this, "language", "").split(",");
                if (split.length > 0) {
                    for (String equals : split) {
                        if (equals.equals(language_list[i])) {
                            stateVO.setSelected(true);
                            MyAdapter.list.add(language_list[i]);
                        }
                    }
                }
            }
            arrayList.add(stateVO);
        }
        this.sp_language.setAdapter(new MyAdapter(this, 0, arrayList));
    }

    private void loadOprtaion() {

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 0, operation_list);
        arrayAdapter.setDropDownViewResource(0);
        this.sp_operation.setAdapter(arrayAdapter);
        this.sp_operation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                CreateGiverDetailActivity.operation = CreateGiverDetailActivity.operation_list[i];
                MySharedPref.saveData(CreateGiverDetailActivity.this, "operation", CreateGiverDetailActivity.operation);
            }
        });
    }
}
