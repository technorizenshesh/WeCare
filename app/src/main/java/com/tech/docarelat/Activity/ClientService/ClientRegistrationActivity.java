package com.tech.docarelat.Activity.ClientService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.PlusShare;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tech.docarelat.Activity.Main.GetHelpActivity;
import com.tech.docarelat.Activity.Main.TermsAndConditionActivity;
import com.tech.docarelat.App.GPSTracker;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import com.tech.docarelat.autoaddress.GeoAutoCompleteAdapter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ClientRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    public static String addres1 = null;
    public static String address2 = null;
    public static String alt_number = null;
    public static String city = "";
    public static String city_id = null;
    public static Activity clientRegister = null;
    public static String country = "United States";
    public static String country_id = "231";
    public static String dob = null;
    public static String email = null;
    public static String ext = null;
    public static String fname = null;
    public static String gender = "";
    public static String lname = null;
    public static String lxi = null;

    /* renamed from: mi */
    public static String f136mi = null;
    public static String pass1 = null;
    public static String pass2 = null;
    public static String phone = null;
    public static String state = "";
    public static String state_id;
    public static String username;
    public static String zip;
    /* access modifiers changed from: private */
    public String alt_lastChar = "";
    private String altc_code = "+1";
    ArrayAdapter<String> arrayAdapter = null;
    private Button btn_continue;
    /* access modifiers changed from: private */
    public Calendar cal = Calendar.getInstance();
    private CheckBox chk_termsandcondition;
    /* access modifiers changed from: private */
    public String[] city_idlist;
    /* access modifiers changed from: private */
    public String[] city_list;
    int countDrop1 = 0;
    /* access modifiers changed from: private */
    public String current = "";
    /* access modifiers changed from: private */
    public String ddmmyyyy = "MMDDYYYY";
    private AutoCompleteTextView ed_addressone;
    private AutoCompleteTextView ed_addresstwo;
    /* access modifiers changed from: private */
    public EditText ed_altphone;
    /* access modifiers changed from: private */
    public EditText ed_dob;
    /* access modifiers changed from: private */
    public EditText ed_email;
    private EditText ed_ext;
    private EditText ed_fname;
    private AutoCompleteTextView ed_gender;
    private EditText ed_lastname;
    private EditText ed_lxi;
    private EditText ed_mi;
    private EditText ed_passone;
    private EditText ed_passtwo;
    /* access modifiers changed from: private */
    public EditText ed_phone;
    private EditText ed_user_name;
    private EditText ed_zip;
    private AutoCompleteTextView edt_city;
    private AutoCompleteTextView edt_state;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String[] gender_list = {"Male", "Female"};
    private ImageButton imgLeftMenu;
    private Double latitude;
    private Double longitude;
    /* access modifiers changed from: private */
    public String phone_lastChar = "";
    private String phonec_code = "+1";
    /* access modifiers changed from: private */
    public String[] state_idlist;
    /* access modifiers changed from: private */
    public String[] state_list;
    private TextView txt_conditions;
    private TextView txt_gethelp;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_client_registration);
        clientRegister = this;
        hideKeyboard();
        findId();
        this.imgLeftMenu.setOnClickListener(this);
        this.btn_continue.setOnClickListener(this);
        this.txt_gethelp.setOnClickListener(this);
        this.txt_conditions.setOnClickListener(this);
        loadGender();
        try {
            GPSTracker gPSTracker = new GPSTracker(clientRegister);
            if (gPSTracker.canGetLocation()) {
                this.latitude = Double.valueOf(gPSTracker.getLatitude());
                this.longitude = Double.valueOf(gPSTracker.getLongitude());
            } else {
                gPSTracker.showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.ed_phone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = ClientRegistrationActivity.this.ed_phone.getText().toString().length();
                if (length > 1) {
                    ClientRegistrationActivity clientRegistrationActivity = ClientRegistrationActivity.this;
                    String unused = clientRegistrationActivity.phone_lastChar = clientRegistrationActivity.ed_phone.getText().toString().substring(length - 1);
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = ClientRegistrationActivity.this.ed_phone.getText().toString().length();
                Log.d("LENGTH", "" + length);
                if (ClientRegistrationActivity.this.phone_lastChar.equals("-")) {
                    return;
                }
                if (length == 3 || length == 7) {
                    ClientRegistrationActivity.this.ed_phone.append("-");
                }
            }
        });

        this.ed_altphone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = ClientRegistrationActivity.this.ed_altphone.getText().toString().length();
                if (length > 1) {
                    ClientRegistrationActivity clientRegistrationActivity = ClientRegistrationActivity.this;
                    String unused = clientRegistrationActivity.alt_lastChar = clientRegistrationActivity.ed_altphone.getText().toString().substring(length - 1);
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = ClientRegistrationActivity.this.ed_altphone.getText().toString().length();
                Log.d("LENGTH", "" + length);
                if (ClientRegistrationActivity.this.alt_lastChar.equals("-")) {
                    return;
                }
                if (length == 3 || length == 7) {
                    ClientRegistrationActivity.this.ed_altphone.append("-");
                }
            }
        });

        this.ed_dob.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String str;
                if (!charSequence.toString().equals(ClientRegistrationActivity.this.current)) {
                    String replaceAll = charSequence.toString().replaceAll("[^\\d.]|\\.", "");
                    String replaceAll2 = ClientRegistrationActivity.this.current.replaceAll("[^\\d.]|\\.", "");
                    int length = replaceAll.length();
                    int i4 = length;
                    int i5 = 2;
                    while (i5 <= length && i5 < 6) {
                        i4++;
                        i5 += 2;
                    }
                    if (replaceAll.equals(replaceAll2)) {
                        i4--;
                    }
                    if (replaceAll.length() < 8) {
                        str = replaceAll + ClientRegistrationActivity.this.ddmmyyyy.substring(replaceAll.length());
                    } else {
                        int parseInt = Integer.parseInt(replaceAll.substring(2, 4));
                        int parseInt2 = Integer.parseInt(replaceAll.substring(0, 2));
                        int parseInt3 = Integer.parseInt(replaceAll.substring(4, 8));
                        if (parseInt2 < 1) {
                            parseInt2 = 1;
                        } else if (parseInt2 > 12) {
                            parseInt2 = 12;
                        }
                        ClientRegistrationActivity.this.cal.set(2, parseInt2 - 1);
                        if (parseInt3 < 1900) {
                            parseInt3 = 1900;
                        } else if (parseInt3 > 2100) {
                            parseInt3 = 2100;
                        }
                        ClientRegistrationActivity.this.cal.set(1, parseInt3);
                        if (parseInt > ClientRegistrationActivity.this.cal.getActualMaximum(5)) {
                            parseInt = ClientRegistrationActivity.this.cal.getActualMaximum(5);
                        }
                        str = String.format("%02d%02d%02d", new Object[]{Integer.valueOf(parseInt2), Integer.valueOf(parseInt), Integer.valueOf(parseInt3)});
                    }
                    String format = String.format("%s/%s/%s", new Object[]{str.substring(0, 2), str.substring(2, 4), str.substring(4, 8)});
                    if (i4 < 0) {
                        i4 = 0;
                    }
                    String unused = ClientRegistrationActivity.this.current = format;
                    ClientRegistrationActivity.this.ed_dob.setText(ClientRegistrationActivity.this.current);
                    EditText access$700 = ClientRegistrationActivity.this.ed_dob;
                    if (i4 >= ClientRegistrationActivity.this.current.length()) {
                        i4 = ClientRegistrationActivity.this.current.length();
                    }
                    access$700.setSelection(i4);
                }
            }
        });

        this.ed_addressone.setThreshold(1);
        this.ed_addressone.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                editable.length();
            }
        });
        this.ed_addresstwo.setThreshold(1);
        this.ed_addresstwo.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                editable.length();
            }
        });

        this.ed_email.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                ClientRegistrationActivity.email = ClientRegistrationActivity.this.ed_email.getText().toString();
                if (!ClientRegistrationActivity.email.matches(ClientRegistrationActivity.this.emailPattern) || editable.length() <= 0) {
                    ClientRegistrationActivity.this.ed_email.setError("invalid email");
                }
            }
        });

        new GetStateTask().execute(new Void[0]);

        MySharedPref.saveData(this, "data", "");
        MySharedPref.saveData(this, "dataone", "");
        String data = MySharedPref.getData(this, "ldata", (String) null);
        if (data != null) {
            loadData(data);
        }
    }

    private void loadData(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("result");
            Log.e("ldata", "" + jSONObject);
            this.ed_phone.setText(jSONObject.getString(PhoneAuthProvider.PROVIDER_ID).replace("+1 ", ""));
            this.ed_altphone.setText(jSONObject.getString("alt_phone").replace("+1 ", ""));
            this.ed_ext.setText(jSONObject.getString("ext1"));
            this.ed_lxi.setText(jSONObject.getString("ext2"));
            this.ed_email.setText(jSONObject.getString("email"));
            this.ed_fname.setText(jSONObject.getString("first_name"));
            this.ed_mi.setText(jSONObject.getString("mi"));
            this.ed_lastname.setText(jSONObject.getString("last_name"));
            this.ed_dob.setText(jSONObject.getString("dob"));
            this.ed_addressone.setText(jSONObject.getString("address_line1"));
            this.ed_addresstwo.setText(jSONObject.getString("address_line2"));
            this.edt_city.setText(jSONObject.getString("city"));
            this.edt_state.setText(jSONObject.getString("state"));
            this.ed_zip.setText(jSONObject.getString("zip_code"));
            this.ed_user_name.setText(jSONObject.getString("username"));
            this.ed_passone.setText(jSONObject.getString(EmailAuthProvider.PROVIDER_ID));
            this.ed_passtwo.setText(jSONObject.getString(EmailAuthProvider.PROVIDER_ID));
            this.ed_gender.setText(jSONObject.getString("gender"));
            this.chk_termsandcondition.setChecked(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void hideKeyboard() {
        getWindow().setSoftInputMode(2);
    }

    private void loadDataDrop1(String str) {
        try {
            if (this.countDrop1 == 0) {
                ArrayList arrayList = new ArrayList();
                if (str != null) {
                    arrayList.add(str);
                    GeoAutoCompleteAdapter geoAutoCompleteAdapter = new GeoAutoCompleteAdapter(clientRegister, arrayList, "" + this.latitude, "" + this.longitude);
                    this.ed_addressone.setAdapter(geoAutoCompleteAdapter);
                    this.ed_addresstwo.setAdapter(geoAutoCompleteAdapter);
                }
            }
            this.countDrop1++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findId() {
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        this.btn_continue = (Button) findViewById(R.id.btn_continue);
        this.ed_gender = (AutoCompleteTextView) findViewById(R.id.ed_gender);
        this.ed_phone = (EditText) findViewById(R.id.ed_phone);
        this.ed_ext = (EditText) findViewById(R.id.ed_ext);
        this.ed_altphone = (EditText) findViewById(R.id.ed_altphone);
        this.ed_lxi = (EditText) findViewById(R.id.ed_lxi);
        this.ed_email = (EditText) findViewById(R.id.ed_email);
        this.ed_fname = (EditText) findViewById(R.id.ed_fname);
        this.ed_mi = (EditText) findViewById(R.id.ed_mi);
        this.ed_lastname = (EditText) findViewById(R.id.ed_lastname);
        this.ed_dob = (EditText) findViewById(R.id.ed_dob);
        this.ed_addressone = (AutoCompleteTextView) findViewById(R.id.ed_addressone);
        this.ed_addresstwo = (AutoCompleteTextView) findViewById(R.id.ed_addresstwo);
        this.ed_user_name = (EditText) findViewById(R.id.ed_user_name);
        this.ed_passone = (EditText) findViewById(R.id.ed_passone);
        this.ed_passtwo = (EditText) findViewById(R.id.ed_passtwo);
        this.txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);
        this.txt_conditions = (TextView) findViewById(R.id.txt_conditions);
        this.chk_termsandcondition = (CheckBox) findViewById(R.id.chk_termsandcondition);
        this.ed_zip = (EditText) findViewById(R.id.ed_zip);
        this.edt_state = (AutoCompleteTextView) findViewById(R.id.edt_state);
        this.edt_city = (AutoCompleteTextView) findViewById(R.id.edt_city);
    }

    public void onClick(View view) {
        if (view == this.imgLeftMenu) {
            finish();
        } else if (view == this.btn_continue) {
            vailidate();
        } else if (view == this.txt_gethelp) {
            startActivity(new Intent(this, GetHelpActivity.class));
        } else if (view == this.txt_conditions) {
            Intent intent = new Intent(this, TermsAndConditionActivity.class);
            intent.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "https://drive.google.com/file/d/1Ia5fjolukqrlPSw-EeZKHeG7_CL7FM1t/view?usp=sharing");
            startActivity(intent);
        }
    }

    private void vailidate() {
        phone = this.ed_phone.getText().toString();
        ext = this.ed_ext.getText().toString();
        alt_number = this.ed_altphone.getText().toString();
        lxi = this.ed_lxi.getText().toString();
        email = this.ed_email.getText().toString();
        fname = this.ed_fname.getText().toString();
        f136mi = this.ed_mi.getText().toString();
        lname = this.ed_lastname.getText().toString();
        dob = this.ed_dob.getText().toString();
        addres1 = this.ed_addressone.getText().toString();
        address2 = this.ed_addresstwo.getText().toString();
        username = this.ed_user_name.getText().toString();
        pass1 = this.ed_passone.getText().toString();
        pass2 = this.ed_passtwo.getText().toString();
        zip = this.ed_zip.getText().toString();
        state = this.edt_state.getText().toString();
        city = this.edt_city.getText().toString();
        gender = this.ed_gender.getText().toString();
        if (phone.equalsIgnoreCase("")) {
            this.ed_phone.setError("Please Enter Phone Number");
        } else if (email.equalsIgnoreCase("")) {
            this.ed_email.setError("Please Enter Email");
        } else if (fname.equalsIgnoreCase("")) {
            this.ed_fname.setError("Please Enter First Name");
        } else if (lname.equalsIgnoreCase("")) {
            this.ed_lastname.setError("Please Enter Last Name");
        } else if (dob.equalsIgnoreCase("")) {
            this.ed_dob.setError("Please Enter Date of Birth");
        } else if (addres1.equalsIgnoreCase("")) {
            this.ed_addressone.setError("Please Enter Address");
        } else if (country.equalsIgnoreCase("")) {
            Toast.makeText(this, "please select Country", Toast.LENGTH_LONG).show();
        } else if (state.equalsIgnoreCase("")) {
            Toast.makeText(this, "please select State", Toast.LENGTH_LONG).show();
        } else if (city.equalsIgnoreCase("")) {
            Toast.makeText(this, "please select City", Toast.LENGTH_LONG).show();
        } else if (username.equalsIgnoreCase("")) {
            this.ed_user_name.setError("Please Enter Username");
        } else if (username.length() < 6) {
            this.ed_user_name.setError("User Name to Short");
        } else if (pass1.equalsIgnoreCase("")) {
            this.ed_passone.setError("Please Enter Password");
        } else if (pass1.length() < 6) {
            this.ed_passone.setError("Password is to Short");
        } else if (pass2.equalsIgnoreCase("")) {
            this.ed_passtwo.setError("Please Enter password");
        } else if (pass2.length() < 6) {
            this.ed_passtwo.setError("Password is to Short");
        } else if (gender.equalsIgnoreCase("")) {
            this.ed_gender.setError("Please Enter gender");
        } else if (!pass1.equals(pass2)) {
            Toast.makeText(this, "Password must be same", Toast.LENGTH_LONG).show();
        } else if (this.chk_termsandcondition.isChecked()) {
            phone = this.phonec_code + " " + phone;
            alt_number = this.altc_code + " " + alt_number;
            startActivity(new Intent(this, PaymentInfoActivity.class));
        } else {
            Toast.makeText(clientRegister, "Please Accept Terms And Conditions", Toast.LENGTH_LONG).show();
        }
    }

    private class GetStateTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f138pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {

        }

        private GetStateTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_state?country_id=" + ClientRegistrationActivity.country_id;
            this.iserror = false;
            this.isValue = false;
            this.f138pd = ProgressDialog.show(ClientRegistrationActivity.this, "", "Please wait...", true);
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
                    this.f138pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f138pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f138pd.dismiss();

            try {
                if (obj.getString("status").equals("1")) {
                    JSONArray jSONArray = obj.getJSONArray("result");
                    String[] unused = ClientRegistrationActivity.this.state_list =
                            new String[jSONArray.length()];
                    String[] unused2 = ClientRegistrationActivity.this.state_idlist =
                            new String[jSONArray.length()];

                    for (int i = 0; i < jSONArray.length(); i++) {
                        String string = jSONArray.getJSONObject(i).getString("name");
                        String string2 = jSONArray.getJSONObject(i).getString("id");
                        ClientRegistrationActivity.this.state_list[i] = string;
                        ClientRegistrationActivity.this.state_idlist[i] = string2;
                    }
                    ClientRegistrationActivity.this.loadState();
                    return;
                }
                ClientRegistrationActivity clientRegistrationActivity = ClientRegistrationActivity.this;
                Toast.makeText(clientRegistrationActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
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
        final ProgressDialog f137pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetCityTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_city?country_id=" + ClientRegistrationActivity.country_id + "&state_id=" + ClientRegistrationActivity.state_id;
            this.iserror = false;
            this.isValue = false;
            this.f137pd = ProgressDialog.show(ClientRegistrationActivity.this, "", "Please wait...", true);
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
                    this.f137pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f137pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f137pd.dismiss();
            try {
                if (this.obj == null) {
                    return;
                }
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    String[] unused = ClientRegistrationActivity.this.city_list = new String[jSONArray.length()];
                    String[] unused2 = ClientRegistrationActivity.this.city_idlist = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        String string = jSONArray.getJSONObject(i).getString("name");
                        String string2 = jSONArray.getJSONObject(i).getString("id");
                        ClientRegistrationActivity.this.city_list[i] = string;
                        ClientRegistrationActivity.this.city_idlist[i] = string2;
                    }
                    ClientRegistrationActivity.this.loadcity();
                    return;
                }
                ClientRegistrationActivity clientRegistrationActivity = ClientRegistrationActivity.this;
                Toast.makeText(clientRegistrationActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadGender() {
        this.arrayAdapter = new ArrayAdapter<>(this, 0, this.gender_list);
        this.ed_gender.setThreshold(1);
        this.ed_gender.setAdapter(this.arrayAdapter);
    }

    public void loadState() {
        this.arrayAdapter = new ArrayAdapter<>(this, 0, state_list);
        this.edt_state.setThreshold(1);
        this.edt_state.setAdapter(this.arrayAdapter);

        try {
            new GetCityTask().execute(new Void[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadcity() {

        this.arrayAdapter = new ArrayAdapter<>(this, 0, this.city_list);
        this.edt_city.setThreshold(1);
        this.edt_city.setAdapter(this.arrayAdapter);
    }

    public void onBackPressed() {
        if (MySharedPref.getData(this, "ldata", (String) null) != null) {
            finish();

        } else {
            new AlertDialog.Builder(this).setTitle("Really Exit?").setMessage("Are you sure you want to exit?").setNegativeButton(0, (DialogInterface.OnClickListener) null).setPositiveButton(0, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ClientRegistrationActivity.this.setResult(-1, new Intent().putExtra("EXIT", true));
                    MySharedPref.saveData(ClientRegistrationActivity.this, "data", "");
                    MySharedPref.saveData(ClientRegistrationActivity.this, "dataone", "");
                    ClientRegistrationActivity.this.finish();
                }
            }).create().show();
        }
    }
}
