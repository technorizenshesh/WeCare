package com.tech.docarelat.Activity.CareGiver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.mime.Header;

public class CareGiverBankInfoActivity extends AppCompatActivity implements View.OnClickListener {

    /* access modifiers changed from: private */
    public String account_name;
    /* access modifiers changed fr
    om: private */
    public String account_no;
    /* access modifiers changed from: private */
    public String account_type = "Chacking";

    /* access modifiers changed from: private */
    public String address;

    private Button btn_register;
    /* access modifiers changed from: private */
    public String c_city;
    /* access modifiers changed from: private */
    public String c_state;
    /* access modifiers changed from: private */
    public String c_zip;
    private final RequestParams category = new RequestParams();
    private final AsyncHttpClient client = new AsyncHttpClient();
    /* access modifiers changed from: private */
    public EditText edt_account_name;
    /* access modifiers changed from: private */
    public EditText edt_account_no;
    /* access modifiers changed from: private */
    public EditText edt_address;
    /* access modifiers changed from: private */
    public EditText edt_city;
    /* access modifiers changed from: private */
    public EditText edt_routing_no;
    /* access modifiers changed from: private */
    public EditText edt_state;
    /* access modifiers changed from: private */
    public EditText edt_zip;
    /* access modifiers changed from: private */
    public RadioButton rb_chacking;
    /* access modifiers changed from: private */
    public RadioButton rb_saving;
    private String regId;
    /* access modifiers changed from: private */
    public String routing_no;
    private TextView txt_gethelp;
    private String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_care_giver_bank_info);
        findId();
        this.regId = MySharedPref.getData(this, "regId", "");
        this.rb_chacking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    CareGiverBankInfoActivity.this.rb_saving.setChecked(false);
                    String unused = CareGiverBankInfoActivity.this.account_type = "Checking";
                }
            }
        });
        this.rb_saving.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    CareGiverBankInfoActivity.this.rb_chacking.setChecked(false);
                    String unused = CareGiverBankInfoActivity.this.account_type = "Savings";
                }
            }
        });
        this.btn_register.setOnClickListener(this);
        this.txt_gethelp.setOnClickListener(this);
        this.edt_account_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                CareGiverBankInfoActivity careGiverBankInfoActivity = CareGiverBankInfoActivity.this;
                String unused = careGiverBankInfoActivity.account_name = careGiverBankInfoActivity.edt_account_name.getText().toString();
                CareGiverBankInfoActivity careGiverBankInfoActivity2 = CareGiverBankInfoActivity.this;
                MySharedPref.saveData(careGiverBankInfoActivity2, "account_name", "" + CareGiverBankInfoActivity.this.account_name);
            }
        });
        this.edt_account_no.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                CareGiverBankInfoActivity careGiverBankInfoActivity = CareGiverBankInfoActivity.this;
                String unused = careGiverBankInfoActivity.account_no = careGiverBankInfoActivity.edt_account_no.getText().toString();
                CareGiverBankInfoActivity careGiverBankInfoActivity2 = CareGiverBankInfoActivity.this;
                MySharedPref.saveData(careGiverBankInfoActivity2, "account_no", "" + CareGiverBankInfoActivity.this.account_name);
            }
        });

        this.edt_routing_no.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {

            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                CareGiverBankInfoActivity careGiverBankInfoActivity = CareGiverBankInfoActivity.this;
                String unused = careGiverBankInfoActivity.routing_no = careGiverBankInfoActivity.edt_routing_no.getText().toString();
                CareGiverBankInfoActivity careGiverBankInfoActivity2 = CareGiverBankInfoActivity.this;
                MySharedPref.saveData(careGiverBankInfoActivity2, "routing_no", "" + CareGiverBankInfoActivity.this.routing_no);
            }
        });

        this.edt_address.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable) {

            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                CareGiverBankInfoActivity careGiverBankInfoActivity = CareGiverBankInfoActivity.this;
                String unused = careGiverBankInfoActivity.address = careGiverBankInfoActivity.edt_address.getText().toString();
                CareGiverBankInfoActivity careGiverBankInfoActivity2 = CareGiverBankInfoActivity.this;
                MySharedPref.saveData(careGiverBankInfoActivity2, "address", "" + CareGiverBankInfoActivity.this.address);
            }
        });
        this.edt_city.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                CareGiverBankInfoActivity careGiverBankInfoActivity = CareGiverBankInfoActivity.this;
                String unused = careGiverBankInfoActivity.c_city = careGiverBankInfoActivity.edt_city.getText().toString();
                CareGiverBankInfoActivity careGiverBankInfoActivity2 = CareGiverBankInfoActivity.this;
                MySharedPref.saveData(careGiverBankInfoActivity2, "city", "" + CareGiverBankInfoActivity.this.c_city);
            }
        });
        this.edt_state.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                CareGiverBankInfoActivity careGiverBankInfoActivity = CareGiverBankInfoActivity.this;
                String unused = careGiverBankInfoActivity.c_state = careGiverBankInfoActivity.edt_state.getText().toString();
                CareGiverBankInfoActivity careGiverBankInfoActivity2 = CareGiverBankInfoActivity.this;
                MySharedPref.saveData(careGiverBankInfoActivity2, "state", "" + CareGiverBankInfoActivity.this.c_state);
            }
        });
        this.edt_zip.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                CareGiverBankInfoActivity careGiverBankInfoActivity = CareGiverBankInfoActivity.this;
                String unused = careGiverBankInfoActivity.c_zip = careGiverBankInfoActivity.edt_zip.getText().toString();
                CareGiverBankInfoActivity careGiverBankInfoActivity2 = CareGiverBankInfoActivity.this;
                MySharedPref.saveData(careGiverBankInfoActivity2, "zip", "" + CareGiverBankInfoActivity.this.c_zip);
            }
        });
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
            this.user_id = jSONObject.getString("id");
            String string = jSONObject.getString("account_type");
            this.account_type = string;
            if (string.equalsIgnoreCase("Savings")) {
                this.rb_saving.setChecked(true);
                this.rb_chacking.setChecked(false);
            }
            this.edt_account_name.setText(jSONObject.getString("account_holder_name"));
            this.edt_account_no.setText(jSONObject.getString("account_no"));
            this.edt_routing_no.setText(jSONObject.getString("routing_no"));
            this.edt_address.setText(jSONObject.getString("caddress1"));
            this.edt_city.setText(jSONObject.getString("c_city"));
            this.edt_state.setText(jSONObject.getString("c_state"));
            this.edt_zip.setText(jSONObject.getString("c_zip_code"));
            this.btn_register.setText("Update");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        MySharedPref.saveData(this, "dataone", "yes");
        finish();
    }

    private void loadData() {
        if (MySharedPref.getData(this, "dataone", "").equals("yes")) {
            this.edt_account_name.setText(MySharedPref.getData(this, "account_name", ""));
            this.edt_account_no.setText(MySharedPref.getData(this, "account_no", ""));
            this.edt_routing_no.setText(MySharedPref.getData(this, "routing_no", ""));
            this.edt_address.setText(MySharedPref.getData(this, "address", ""));
            this.edt_city.setText(MySharedPref.getData(this, "city", ""));
            this.edt_state.setText(MySharedPref.getData(this, "state", ""));
            this.edt_zip.setText(MySharedPref.getData(this, "zip", ""));
        }
    }

    private void findId() {
        rb_chacking = (RadioButton) findViewById(R.id.rb_chacking);
        this.rb_saving = (RadioButton) findViewById(R.id.rb_saving);
        this.edt_account_name = (EditText) findViewById(R.id.edt_account_name);
        this.edt_routing_no = (EditText) findViewById(R.id.edt_routing_no);
        this.edt_address = (EditText) findViewById(R.id.edt_address);
        this.edt_city = (EditText) findViewById(R.id.edt_city);
        this.edt_state = (EditText) findViewById(R.id.edt_state);
        this.edt_zip = (EditText) findViewById(R.id.edt_zip);
        this.btn_register = (Button) findViewById(R.id.btn_register);
        this.txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);
        this.edt_account_no = (EditText) findViewById(R.id.edt_account_no);

    }

    public void onClick(View view) {
        if (view == this.btn_register) {
            validate();
        }
    }

    private void validate() {
        this.account_name = this.edt_account_name.getText().toString();
        this.account_no = this.edt_account_no.getText().toString();
        this.routing_no = this.edt_routing_no.getText().toString();
        this.address = this.edt_address.getText().toString();
        this.c_city = this.edt_city.getText().toString();
        this.c_state = this.edt_state.getText().toString();
        this.c_zip = this.edt_zip.getText().toString();
        if (this.account_name.equalsIgnoreCase("")) {
            this.edt_account_name.setError("Enter Name");
        } else if (this.account_no.equalsIgnoreCase("")) {
            this.edt_account_no.setError("Enter Account Number");
        } else if (this.routing_no.equalsIgnoreCase("")) {
            this.edt_routing_no.setError("Enter Number");
        } else if (this.address.equalsIgnoreCase("")) {
            this.edt_address.setError("Enter Address");
        } else if (this.c_city.equalsIgnoreCase("")) {
            this.edt_city.setError("Enter City");
        } else if (this.c_state.equalsIgnoreCase("")) {
            this.edt_state.setError("Enter state");
        } else if (this.c_zip.equalsIgnoreCase("")) {
            this.edt_zip.setError("Enter Zip");
        } else if (MySharedPref.getData(this, "ldata", (String) null) != null) {
            update();
        } else {
            registration();
        }
    }

    public void registration() {
        final ProgressDialog show = ProgressDialog.show(this, "", "Please wait...", true);
        try {
            category.put("first_name", CareGiverRegistrationActivity.fname);
            this.category.put("last_name", CareGiverRegistrationActivity.lname);
            this.category.put(PhoneAuthProvider.PROVIDER_ID, CareGiverRegistrationActivity.phone);
            this.category.put("alt_phone", CareGiverRegistrationActivity.alt_number);
            this.category.put("ext1", CareGiverRegistrationActivity.ext);
            this.category.put("ext2", CareGiverRegistrationActivity.lxi);
            this.category.put("email", CareGiverRegistrationActivity.email);
            this.category.put("mi", CareGiverRegistrationActivity.f127mi);
            this.category.put("dob", CareGiverRegistrationActivity.dob);
            this.category.put("address_line1", CareGiverRegistrationActivity.addres1);
            this.category.put("address_line2", CareGiverRegistrationActivity.address2);
            this.category.put("country", CareGiverRegistrationActivity.country);
            this.category.put("state", CareGiverRegistrationActivity.state);
            this.category.put("city", CareGiverRegistrationActivity.city);
            this.category.put("zip_code", CareGiverRegistrationActivity.zip);
            this.category.put("c_state", this.c_state);
            this.category.put("c_city", this.c_city);
            this.category.put("c_zip_code", this.c_zip);
            this.category.put("username", CreateGiverDetailActivity.username);
            this.category.put(EmailAuthProvider.PROVIDER_ID, CreateGiverDetailActivity.password);
            this.category.put("gender", CareGiverRegistrationActivity.gender);
            this.category.put("spoken_lang", CreateGiverDetailActivity.language);
            this.category.put("social_security_no", CareGiverRegistrationActivity.social_num);
            this.category.put("driving_lie_no", CareGiverRegistrationActivity.lic_no);
            this.category.put("operation", CreateGiverDetailActivity.operation);
            this.category.put("account_type", this.account_type);
            this.category.put("account_holder_name", this.account_name);
            this.category.put("account_no", this.account_no);
            this.category.put("routing_no", this.routing_no);
            this.category.put("type", "GIVER");
            this.category.put("register_id", this.regId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("======== category params ========== ");
        System.out.println(this.category);
        try {
            this.client.post("http://showupcare.com/WECARE/webservice/signup?", this.category, new JsonHttpResponseHandler() {
                public void onFailure(int i, Header[] headerArr, String str, Throwable th) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------onFailure-------------" + str + " Status code ::: " + i);
                    Toast.makeText(CareGiverBankInfoActivity.this, "Something went wrong, Try Again !", Toast.LENGTH_LONG).show();
                }

                public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------response----------- " + jSONObject);
                    try {
                        if (jSONObject.getString("status").equals("1")) {
                            Toast.makeText(CareGiverBankInfoActivity.this, "Please Login....", Toast.LENGTH_LONG).show();
                            CareGiverBankInfoActivity.this.startActivity(new Intent(CareGiverBankInfoActivity.this, GiverLoginActivity.class));
                            MySharedPref.saveData(CareGiverBankInfoActivity.this, "data", "");
                            MySharedPref.saveData(CareGiverBankInfoActivity.this, "dataone", "");
                            CareGiverRegistrationActivity.caregiverregistrationactivity.finish();
                            CreateGiverDetailActivity.creategiverdetailactivity.finish();
                            CareGiverBankInfoActivity.this.finish();
                            return;
                        }
                        CareGiverBankInfoActivity careGiverBankInfoActivity = CareGiverBankInfoActivity.this;
                        Toast.makeText(careGiverBankInfoActivity, "" + jSONObject.getString("result"), Toast.LENGTH_LONG).show();
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

    public void update() {
        final ProgressDialog show = ProgressDialog.show(this, "", "Please wait...", true);
        try {
            this.category.put("user_id", this.user_id);
            this.category.put("first_name", CareGiverRegistrationActivity.fname);
            this.category.put("last_name", CareGiverRegistrationActivity.lname);
            this.category.put(PhoneAuthProvider.PROVIDER_ID, CareGiverRegistrationActivity.phone);
            this.category.put("alt_phone", CareGiverRegistrationActivity.alt_number);
            this.category.put("ext1", CareGiverRegistrationActivity.ext);
            this.category.put("ext2", CareGiverRegistrationActivity.lxi);
            this.category.put("email", CareGiverRegistrationActivity.email);
            this.category.put("mi", CareGiverRegistrationActivity.f127mi);
            this.category.put("dob", CareGiverRegistrationActivity.dob);
            this.category.put("address_line1", CareGiverRegistrationActivity.addres1);
            this.category.put("address_line2", CareGiverRegistrationActivity.address2);
            this.category.put("country", CareGiverRegistrationActivity.country);
            this.category.put("state", CareGiverRegistrationActivity.state);
            this.category.put("city", CareGiverRegistrationActivity.city);
            this.category.put("zip_code", CareGiverRegistrationActivity.zip);
            this.category.put("c_state", this.c_state);
            this.category.put("c_city", this.c_city);
            this.category.put("c_zip_code", this.c_zip);
            this.category.put("username", CreateGiverDetailActivity.username);
            this.category.put(EmailAuthProvider.PROVIDER_ID, CreateGiverDetailActivity.password);
            this.category.put("gender", CareGiverRegistrationActivity.gender);
            this.category.put("spoken_lang", CreateGiverDetailActivity.language);
            this.category.put("social_security_no", CareGiverRegistrationActivity.social_num);
            this.category.put("driving_lie_no", CareGiverRegistrationActivity.lic_no);
            this.category.put("operation", CreateGiverDetailActivity.operation);
            this.category.put("account_type", this.account_type);
            this.category.put("account_holder_name", this.account_name);
            this.category.put("account_no", this.account_no);
            this.category.put("routing_no", this.routing_no);
            this.category.put("type", "GIVER");
            this.category.put("register_id", this.regId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("======== category params ========== ");
        System.out.println(this.category);
        try {
            this.client.post("http://showupcare.com/WECARE/webservice/update_profile?", this.category, new JsonHttpResponseHandler() {
                public void onFailure(int i, Header[] headerArr, String str, Throwable th) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------onFailure-------------" + str + " Status code ::: " + i);
                    Toast.makeText(CareGiverBankInfoActivity.this, "Something went wrong, Try Again !", Toast.LENGTH_LONG).show();
                }

                public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------response----------- " + jSONObject);
                    try {
                        if (jSONObject.getString("status").equals("1")) {
                            Toast.makeText(CareGiverBankInfoActivity.this, "Success", Toast.LENGTH_LONG).show();
                            MySharedPref.saveData(CareGiverBankInfoActivity.this, "data", "");
                            MySharedPref.saveData(CareGiverBankInfoActivity.this, "dataone", "");
                            Context applicationContext = CareGiverBankInfoActivity.this.getApplicationContext();
                            MySharedPref.saveData(applicationContext, "ldata", "" + jSONObject);

                            CareGiverRegistrationActivity.caregiverregistrationactivity.finish();
                            CreateGiverDetailActivity.creategiverdetailactivity.finish();
                            CareGiverBankInfoActivity.this.finish();
                            return;
                        }
                        CareGiverBankInfoActivity careGiverBankInfoActivity = CareGiverBankInfoActivity.this;
                        Toast.makeText(careGiverBankInfoActivity, "" + jSONObject.getString("result"), Toast.LENGTH_LONG).show();
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
