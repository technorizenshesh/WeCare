package com.tech.docarelat.Activity.ClientService;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.tech.docarelat.Activity.Main.GetHelpActivity;
import com.tech.docarelat.App.GPSTracker;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import com.tech.docarelat.autoaddress.GeoAutoCompleteAdapter;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class PaymentInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public static String caddres2 = null;
    public static String caddress1 = null;
    public static String card_name = null;
    public static String card_num = null;
    public static String card_type = "Credit Card";
    public static String ccity;
    public static String ccity_id;
    public static Activity clientpaymentactivity;
    public static String cstate;
    public static String cstate_id;
    public static String cvv;
    public static String czip;
    public static String exp_date;
    ArrayAdapter<String> arrayAdapter = null;
    private Button btn_continue;
    /* access modifiers changed from: private */
    public String[] city_idlist;
    /* access modifiers changed from: private */
    public String[] city_list;
    int countDrop1 = 0;
    private AutoCompleteTextView ed_caddressone;
    private AutoCompleteTextView ed_caddresstwo;
    private EditText ed_cnumber;
    private EditText ed_cvv;
    /* access modifiers changed from: private */
    public EditText ed_expdate;
    private EditText ed_holdername;
    private EditText ed_zip;
    private AutoCompleteTextView edt_city;
    private AutoCompleteTextView edt_state;
    private ImageButton imgLeftMenu;
    private Double latitude;
    private Double longitude;
    /* access modifiers changed from: private */
    public String mLastInput = "";
    /* access modifiers changed from: private */
    public RadioButton rd_ccard;
    /* access modifiers changed from: private */
    public RadioButton rd_dcard;
    /* access modifiers changed from: private */
    public String[] state_idlist;
    /* access modifiers changed from: private */
    public String[] state_list;
    private TextView txt_gethelp;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_payment_info);
        findId();
        clientpaymentactivity = this;
        this.btn_continue.setOnClickListener(this);
        this.imgLeftMenu.setOnClickListener(this);
        this.txt_gethelp.setOnClickListener(this);
        this.rd_dcard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    PaymentInfoActivity.this.rd_ccard.setChecked(false);
                    PaymentInfoActivity.card_type = "Debit Card";
                }
            }
        });
        this.rd_ccard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    PaymentInfoActivity.this.rd_dcard.setChecked(false);
                    PaymentInfoActivity.card_type = "Credit Card";
                }
            }
        });
        try {
            GPSTracker gPSTracker = new GPSTracker(this);
            if (gPSTracker.canGetLocation()) {
                this.latitude = Double.valueOf(gPSTracker.getLatitude());
                this.longitude = Double.valueOf(gPSTracker.getLongitude());
            } else {
                gPSTracker.showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.ed_caddressone.setThreshold(1);
        this.ed_caddressone.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                editable.length();
            }
        });

        this.ed_caddresstwo.setThreshold(1);

        this.ed_caddresstwo.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                editable.length();
            }
        });


        this.ed_expdate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String charSequence2 = charSequence.toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy", Locale.GERMANY);
                try {
                    Calendar.getInstance().setTime(simpleDateFormat.parse(charSequence2));
                } catch (ParseException unused) {
                    if (charSequence.length() != 2 || PaymentInfoActivity.this.mLastInput.endsWith("/")) {
                        if (charSequence.length() != 2 || !PaymentInfoActivity.this.mLastInput.endsWith("/")) {
                            if (charSequence.length() == 1 && Integer.parseInt(charSequence2) > 1) {
                                EditText access$300 = PaymentInfoActivity.this.ed_expdate;
                                access$300.setText("0" + PaymentInfoActivity.this.ed_expdate.getText().toString() + "/");
                                PaymentInfoActivity.this.ed_expdate.setSelection(PaymentInfoActivity.this.ed_expdate.getText().toString().length());
                            }
                        } else if (Integer.parseInt(charSequence2) <= 12) {
                            PaymentInfoActivity.this.ed_expdate.setText(PaymentInfoActivity.this.ed_expdate.getText().toString().substring(0, 1));
                            PaymentInfoActivity.this.ed_expdate.setSelection(PaymentInfoActivity.this.ed_expdate.getText().toString().length());
                        } else {
                            PaymentInfoActivity.this.ed_expdate.setText("");
                            PaymentInfoActivity.this.ed_expdate.setSelection(PaymentInfoActivity.this.ed_expdate.getText().toString().length());
                            Toast.makeText(PaymentInfoActivity.this.getApplicationContext(), "Enter a valid month", Toast.LENGTH_LONG).show();
                        }
                    } else if (Integer.parseInt(charSequence2) <= 12) {
                        EditText access$3002 = PaymentInfoActivity.this.ed_expdate;
                        access$3002.setText(PaymentInfoActivity.this.ed_expdate.getText().toString() + "/");
                        PaymentInfoActivity.this.ed_expdate.setSelection(PaymentInfoActivity.this.ed_expdate.getText().toString().length());
                    }
                    PaymentInfoActivity paymentInfoActivity = PaymentInfoActivity.this;
                    String unused2 = paymentInfoActivity.mLastInput = paymentInfoActivity.ed_expdate.getText().toString();
                }
            }
        });
        new GetStateTask().execute(new Void[0]);
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
            if (jSONObject.getString("card_type").equalsIgnoreCase("Debit Card")) {
                this.rd_dcard.setChecked(true);
                this.rd_ccard.setChecked(false);
            }
            this.ed_holdername.setText(jSONObject.getString("card_name"));
            this.ed_cnumber.setText(jSONObject.getString("card_num"));
            this.ed_expdate.setText(jSONObject.getString("exp_date"));
            this.ed_cvv.setText(jSONObject.getString("cvv"));
            this.ed_caddressone.setText(jSONObject.getString("caddress1"));
            this.ed_caddresstwo.setText(jSONObject.getString("caddres2"));
            this.edt_city.setText(jSONObject.getString("c_city"));
            this.edt_state.setText(jSONObject.getString("c_state"));
            this.ed_zip.setText(jSONObject.getString("c_zip_code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataDrop1(String str) {
        try {
            if (this.countDrop1 == 0) {
                ArrayList arrayList = new ArrayList();
                if (str != null) {
                    arrayList.add(str);
                    GeoAutoCompleteAdapter geoAutoCompleteAdapter = new GeoAutoCompleteAdapter(this, arrayList, "" + this.latitude, "" + this.longitude);
                    this.ed_caddresstwo.setAdapter(geoAutoCompleteAdapter);
                    this.ed_caddressone.setAdapter(geoAutoCompleteAdapter);
                }
            }
            this.countDrop1++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findId() {
        this.btn_continue = (Button) findViewById(R.id.btn_continue);
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        this.rd_dcard = (RadioButton) findViewById(R.id.rd_dcard);
        this.rd_ccard = (RadioButton) findViewById(R.id.rd_ccard);
        this.edt_state = (AutoCompleteTextView) findViewById(R.id.edt_state);
        this.edt_city = (AutoCompleteTextView) findViewById(R.id.edt_city);
        this.ed_zip = (EditText) findViewById(R.id.ed_zip);
        this.ed_holdername = (EditText) findViewById(R.id.ed_holdername);
        this.ed_cnumber = (EditText) findViewById(R.id.ed_cnumber);
        this.ed_expdate = (EditText) findViewById(R.id.ed_expdate);
        this.ed_cvv = (EditText) findViewById(R.id.ed_cvv);
        this.ed_caddressone = (AutoCompleteTextView) findViewById(R.id.ed_caddressone);
        this.ed_caddresstwo = (AutoCompleteTextView) findViewById(R.id.ed_caddresstwo);
        this.txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);
    }

    public void onClick(View view) {
        if (view == this.btn_continue) {
            validate();
        } else if (view == this.imgLeftMenu) {
            finish();
        } else if (view == this.txt_gethelp) {
            startActivity(new Intent(this, GetHelpActivity.class));
        }
    }

    private boolean checkDate(String str) {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(1);
        int i2 = instance.get(2);
        String[] split = str.split("/");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        Log.e("Check Year", i + " == " + parseInt2);
        Log.e("Check Month", i2 + " == " + parseInt);
        if (i == parseInt2) {
            if (i2 != parseInt && parseInt <= i2) {
                return false;
            }
            return true;
        } else if (parseInt2 <= i || (i2 != parseInt && parseInt <= i2)) {
            return false;
        } else {
            return true;
        }
    }

    private void validate() {
        card_name = this.ed_holdername.getText().toString();
        card_num = this.ed_cnumber.getText().toString();
        exp_date = this.ed_expdate.getText().toString();
        cvv = this.ed_cvv.getText().toString();
        caddress1 = this.ed_caddressone.getText().toString();
        caddres2 = this.ed_caddresstwo.getText().toString();
        cstate = this.edt_state.getText().toString();
        ccity = this.edt_city.getText().toString();
        czip = this.ed_zip.getText().toString();
        if (card_name.equalsIgnoreCase("")) {
            this.ed_holdername.setError("Please Enter Name");
        } else if (card_num.equalsIgnoreCase("")) {
            this.ed_cnumber.setError("Please Enter Number");
        } else if (exp_date.equalsIgnoreCase("")) {
            this.ed_expdate.setError("Please Enter Date");
        } else if (exp_date.length() < 7) {
            this.ed_expdate.setError("Please Enter Right Date");
        } else if (!checkDate(exp_date)) {
            this.ed_expdate.setError("Wrong Date");
        } else if (cvv.equalsIgnoreCase("")) {
            this.ed_cvv.setError("Please Enter Cvv");
        } else if (caddress1.equalsIgnoreCase("")) {
            this.ed_caddressone.setError("Please Enter Address");
        } else {
            MySharedPref.saveData(this, "data", "yes");
            MySharedPref.saveData(this, "card_name", card_name);
            MySharedPref.saveData(this, "card_num", card_num);
            MySharedPref.saveData(this, "exp_date", exp_date);
            MySharedPref.saveData(this, "cvv", cvv);
            MySharedPref.saveData(this, "caddress1", caddress1);
            MySharedPref.saveData(this, "caddres2", caddres2);
            MySharedPref.saveData(this, "ccity", ccity);
            MySharedPref.saveData(this, "cstate", cstate);
            MySharedPref.saveData(this, "czip", czip);
            startActivity(new Intent(this, ClientPersonalInfoActivity.class));
        }
    }

    private void loadData() {
        if (MySharedPref.getData(this, "data", "").equals("yes")) {
            EditText editText = this.ed_holdername;
            editText.setText("" + MySharedPref.getData(this, "card_name", ""));
            EditText editText2 = this.ed_cnumber;
            editText2.setText("" + MySharedPref.getData(this, "card_num", ""));
            EditText editText3 = this.ed_expdate;
            editText3.setText("" + MySharedPref.getData(this, "exp_date", ""));
            EditText editText4 = this.ed_cvv;
            editText4.setText("" + MySharedPref.getData(this, "cvv", ""));
            AutoCompleteTextView autoCompleteTextView = this.ed_caddressone;
            autoCompleteTextView.setText("" + MySharedPref.getData(this, "caddress1", ""));
            AutoCompleteTextView autoCompleteTextView2 = this.ed_caddresstwo;
            autoCompleteTextView2.setText("" + MySharedPref.getData(this, "caddres2", ""));
            AutoCompleteTextView autoCompleteTextView3 = this.edt_state;
            autoCompleteTextView3.setText("" + MySharedPref.getData(this, "cstate", ""));
            AutoCompleteTextView autoCompleteTextView4 = this.edt_city;
            autoCompleteTextView4.setText("" + MySharedPref.getData(this, "ccity", ""));
            EditText editText5 = this.ed_zip;
            editText5.setText("" + MySharedPref.getData(this, "czip", ""));
        }
    }

    public void datepicker() {
        Calendar instance = Calendar.getInstance();
        new DatePickerDialog(this, R.style.AlertDialog, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                PaymentInfoActivity.this.ed_expdate.setText("" + (i2 + 1) + "/" + i);
            }
        }, instance.get(1), instance.get(2), instance.get(5)).show();
    }

    private class GetStateTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f141pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetStateTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_state?country_id=" + ClientRegistrationActivity.country_id;
            this.iserror = false;
            this.isValue = false;
            this.f141pd = ProgressDialog.show(PaymentInfoActivity.this, "", "Please wait...", true);
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
                    this.f141pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f141pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f141pd.dismiss();
            try {
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    String[] unused = PaymentInfoActivity.this.state_list = new String[jSONArray.length()];
                    String[] unused2 = PaymentInfoActivity.this.state_idlist = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        String string = jSONArray.getJSONObject(i).getString("name");
                        String string2 = jSONArray.getJSONObject(i).getString("id");
                        PaymentInfoActivity.this.state_list[i] = string;
                        PaymentInfoActivity.this.state_idlist[i] = string2;
                    }
                    PaymentInfoActivity.this.loadState();
                    return;
                }
                PaymentInfoActivity paymentInfoActivity = PaymentInfoActivity.this;
                Toast.makeText(paymentInfoActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetCityTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f140pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetCityTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_city?country_id=" + ClientRegistrationActivity.country_id + "&state_id=" + PaymentInfoActivity.cstate_id;
            this.iserror = false;
            this.isValue = false;
            this.f140pd = ProgressDialog.show(PaymentInfoActivity.this, "", "Please wait...", true);
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
                    this.f140pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f140pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f140pd.dismiss();
            try {
                if (this.obj == null) {
                    return;
                }
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    String[] unused = PaymentInfoActivity.this.city_list = new String[jSONArray.length()];
                    String[] unused2 = PaymentInfoActivity.this.city_idlist = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        String string = jSONArray.getJSONObject(i).getString("name");
                        String string2 = jSONArray.getJSONObject(i).getString("id");
                        PaymentInfoActivity.this.city_list[i] = string;
                        PaymentInfoActivity.this.city_idlist[i] = string2;
                    }
                    PaymentInfoActivity.this.loadcity();
                    return;
                }
                PaymentInfoActivity paymentInfoActivity = PaymentInfoActivity.this;
                Toast.makeText(paymentInfoActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadState() {
        this.arrayAdapter = new ArrayAdapter<>(this, 0, this.state_list);
        this.edt_state.setThreshold(1);
        this.edt_state.setAdapter(this.arrayAdapter);
        new GetCityTask().execute(new Void[0]);
    }

    public void loadcity() {
        this.arrayAdapter = new ArrayAdapter<>(this, 0, this.city_list);
        this.edt_city.setThreshold(1);
        this.edt_city.setAdapter(this.arrayAdapter);
    }
}
