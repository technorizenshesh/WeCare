package com.tech.docarelat.Activity.CareGiver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.plus.PlusShare;
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


public class CareGiverRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    public static String addres1 = null;
    public static String address2 = null;
    public static String alt_number = null;
    public static Activity caregiverregistrationactivity = null;
    public static String city = "";
    public static String city_id = null;
    public static String country = "United States";
    public static String country_id = "231";
    public static String dob = null;
    public static String email = null;
    public static String ext = null;
    public static String fname = null;
    public static String gender = "";
    public static String lic_no = null;
    public static String lname = null;
    public static String lxi = null;

    /* renamed from: mi */
    public static String f127mi = null;
    public static String phone = null;
    public static String social_num = null;
    public static String state = "";
    public static String state_id;
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
    private String[] country_idlist;
    private String[] country_list;
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
    private EditText ed_lastname;
    private EditText ed_lic_no;
    private EditText ed_lxi;
    private EditText ed_mi;
    /* access modifiers changed from: private */
    public EditText ed_phone;
    /* access modifiers changed from: private */
    public EditText ed_social_seq_num;
    private EditText ed_zip;
    private AutoCompleteTextView edt_city;
    private AutoCompleteTextView edt_gender;
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
        setContentView(R.layout.activity_care_giver_registration);
        findId();
        caregiverregistrationactivity = this;
       imgLeftMenu.setOnClickListener(this);
       btn_continue.setOnClickListener(this);
       txt_gethelp.setOnClickListener(this);
       txt_conditions.setOnClickListener(this);
        loadGender();
        try {
            GPSTracker gPSTracker = new GPSTracker(caregiverregistrationactivity);
            if (gPSTracker.canGetLocation()) {
               latitude = Double.valueOf(gPSTracker.getLatitude());
               longitude = Double.valueOf(gPSTracker.getLongitude());
            } else {
                gPSTracker.showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       ed_addressone.setThreshold(1);
       ed_addressone.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                editable.length();
            }
        });
       ed_addresstwo.setThreshold(1);
       ed_addresstwo.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                editable.length();
            }
        });
        new GetStateTask().execute(new Void[0]);
       ed_social_seq_num.addTextChangedListener(new TextWatcher() {
            private boolean spaceDeleted;

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
               spaceDeleted = "-".equals(charSequence.subSequence(i, i2 + i).toString());
            }

            public void afterTextChanged(Editable editable) {
                CareGiverRegistrationActivity.this.ed_social_seq_num.removeTextChangedListener(this);
                int selectionStart = CareGiverRegistrationActivity.this.ed_social_seq_num.getSelectionStart();
                String formatText = formatText(editable);
                CareGiverRegistrationActivity.this.ed_social_seq_num.setText(formatText);
                CareGiverRegistrationActivity.this.ed_social_seq_num.setSelection(selectionStart + (formatText.length() - editable.length()));
                if (this.spaceDeleted) {
                   spaceDeleted = false;
                }
                CareGiverRegistrationActivity.this.ed_social_seq_num.addTextChangedListener(this);
            }

            private String formatText(CharSequence charSequence) {
                StringBuilder sb = new StringBuilder();
                if (charSequence.length() != 3 && charSequence.length() != 6) {
                    sb.append(charSequence);
                } else if (!this.spaceDeleted) {
                    sb.append(charSequence + "-");
                } else {
                    sb.append(charSequence);
                }
                return sb.toString();
            }
        });
       ed_phone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = CareGiverRegistrationActivity.this.ed_phone.getText().toString().length();
                if (length > 1) {
                    CareGiverRegistrationActivity careGiverRegistrationActivity = CareGiverRegistrationActivity.this;
                    String unused = careGiverRegistrationActivity.phone_lastChar = careGiverRegistrationActivity.ed_phone.getText().toString().substring(length - 1);
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = CareGiverRegistrationActivity.this.ed_phone.getText().toString().length();
                Log.d("LENGTH", "" + length);
                if (CareGiverRegistrationActivity.this.phone_lastChar.equals("-")) {
                    return;
                }
                if (length == 3 || length == 7) {
                    CareGiverRegistrationActivity.this.ed_phone.append("-");
                }
            }
        });

       ed_altphone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = CareGiverRegistrationActivity.this.ed_altphone.getText().toString().length();
                if (length > 1) {
                    CareGiverRegistrationActivity careGiverRegistrationActivity = CareGiverRegistrationActivity.this;
                    String unused = careGiverRegistrationActivity.alt_lastChar = careGiverRegistrationActivity.ed_altphone.getText().toString().substring(length - 1);
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = CareGiverRegistrationActivity.this.ed_altphone.getText().toString().length();
                Log.d("LENGTH", "" + length);
                if (CareGiverRegistrationActivity.this.alt_lastChar.equals("-")) {
                    return;
                }
                if (length == 3 || length == 7) {
                    CareGiverRegistrationActivity.this.ed_altphone.append("-");
                }
            }
        });
       ed_email.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                CareGiverRegistrationActivity.email = CareGiverRegistrationActivity.this.ed_email.getText().toString();
                if (!CareGiverRegistrationActivity.email.matches(CareGiverRegistrationActivity.this.emailPattern) || editable.length() <= 0) {
                    CareGiverRegistrationActivity.this.ed_email.setError("invalid email");
                }
            }
        });
       ed_dob.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String str;
                if (!charSequence.toString().equals(CareGiverRegistrationActivity.this.current)) {
                    String replaceAll = charSequence.toString().replaceAll("[^\\d.]|\\.", "");
                    String replaceAll2 = CareGiverRegistrationActivity.this.current.replaceAll("[^\\d.]|\\.", "");
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
                        str = replaceAll + CareGiverRegistrationActivity.this.ddmmyyyy.substring(replaceAll.length());
                    } else {
                        int parseInt = Integer.parseInt(replaceAll.substring(2, 4));
                        int parseInt2 = Integer.parseInt(replaceAll.substring(0, 2));
                        int parseInt3 = Integer.parseInt(replaceAll.substring(4, 8));
                        if (parseInt2 < 1) {
                            parseInt2 = 1;
                        } else if (parseInt2 > 12) {
                            parseInt2 = 12;
                        }
                        CareGiverRegistrationActivity.this.cal.set(2, parseInt2 - 1);
                        if (parseInt3 < 1900) {
                            parseInt3 = 1900;
                        } else if (parseInt3 > 2100) {
                            parseInt3 = 2100;
                        }
                        CareGiverRegistrationActivity.this.cal.set(1, parseInt3);
                        if (parseInt > CareGiverRegistrationActivity.this.cal.getActualMaximum(5)) {
                            parseInt = CareGiverRegistrationActivity.this.cal.getActualMaximum(5);
                        }
                        str = String.format("%02d%02d%02d", new Object[]{Integer.valueOf(parseInt2), Integer.valueOf(parseInt), Integer.valueOf(parseInt3)});
                    }
                    String format = String.format("%s/%s/%s", new Object[]{str.substring(0, 2), str.substring(2, 4), str.substring(4, 8)});
                    if (i4 < 0) {
                        i4 = 0;
                    }
                    String unused = CareGiverRegistrationActivity.this.current = format;
                    CareGiverRegistrationActivity.this.ed_dob.setText(CareGiverRegistrationActivity.this.current);
                    EditText access$1000 = CareGiverRegistrationActivity.this.ed_dob;
                    if (i4 >= CareGiverRegistrationActivity.this.current.length()) {
                        i4 = CareGiverRegistrationActivity.this.current.length();
                    }
                    access$1000.setSelection(i4);
                }
            }
        });
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
           ed_phone.setText(jSONObject.getString(PhoneAuthProvider.PROVIDER_ID).replace("+1 ", ""));
           ed_altphone.setText(jSONObject.getString("alt_phone").replace("+1 ", ""));
           ed_ext.setText(jSONObject.getString("ext1"));
           ed_lxi.setText(jSONObject.getString("ext2"));
           ed_email.setText(jSONObject.getString("email"));
           ed_fname.setText(jSONObject.getString("first_name"));
           ed_mi.setText(jSONObject.getString("mi"));
           ed_lastname.setText(jSONObject.getString("last_name"));
           ed_dob.setText(jSONObject.getString("dob"));
           ed_addressone.setText(jSONObject.getString("address_line1"));
           ed_addresstwo.setText(jSONObject.getString("address_line2"));
           edt_city.setText(jSONObject.getString("city"));
           edt_state.setText(jSONObject.getString("state"));
           ed_zip.setText(jSONObject.getString("zip_code"));
           ed_social_seq_num.setText(jSONObject.getString("social_security_no"));
           ed_lic_no.setText(jSONObject.getString("driving_lie_no"));
           edt_gender.setText(jSONObject.getString("gender"));
           chk_termsandcondition.setChecked(true);
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
                    GeoAutoCompleteAdapter geoAutoCompleteAdapter = new GeoAutoCompleteAdapter(this, arrayList, "" +latitude, "" +longitude);
                   ed_addressone.setAdapter(geoAutoCompleteAdapter);
                   ed_addresstwo.setAdapter(geoAutoCompleteAdapter);
                }
            }
           countDrop1++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findId() {
        imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
       btn_continue = (Button) findViewById(R.id.btn_continue);
       edt_gender = (AutoCompleteTextView) findViewById(R.id.edt_gender);
       ed_phone = (EditText) findViewById(R.id.ed_phone);
       ed_ext = (EditText) findViewById(R.id.ed_ext);
       ed_altphone = (EditText) findViewById(R.id.ed_altphone);
       ed_lxi = (EditText) findViewById(R.id.ed_lxi);
       ed_email = (EditText) findViewById(R.id.ed_email);
       ed_fname = (EditText) findViewById(R.id.ed_fname);
       ed_mi = (EditText) findViewById(R.id.ed_mi);
       ed_lastname = (EditText) findViewById(R.id.ed_lastname);
       ed_dob = (EditText) findViewById(R.id.ed_dob);
       ed_addressone = (AutoCompleteTextView) findViewById(R.id.ed_addressone);
       ed_addresstwo = (AutoCompleteTextView) findViewById(R.id.ed_addresstwo);
       ed_social_seq_num = (EditText) findViewById(R.id.ed_social_seq_num);
       ed_lic_no = (EditText) findViewById(R.id.ed_lic_no);
       txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);
       txt_conditions = (TextView) findViewById(R.id.txt_conditions);
       chk_termsandcondition = (CheckBox) findViewById(R.id.chk_termsandcondition);
       ed_zip = (EditText) findViewById(R.id.ed_zip);
       edt_state = (AutoCompleteTextView) findViewById(R.id.edt_state);
       edt_city = (AutoCompleteTextView) findViewById(R.id.edt_city);
    }

    public void onClick(View view) {
        if (view ==imgLeftMenu) {
            finish();
        } else if (view ==btn_continue) {
            vailidate();
        } else if (view ==txt_gethelp) {
            startActivity(new Intent(this, GetHelpActivity.class));
        } else if (view ==txt_conditions) {
            Intent intent = new Intent(this, TermsAndConditionActivity.class);
            intent.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "https://drive.google.com/file/d/1Ia5fjolukqrlPSw-EeZKHeG7_CL7FM1t/view?usp=sharing");
            startActivity(intent);
        }
    }

    private void vailidate() {
        phone =ed_phone.getText().toString();
        ext =ed_ext.getText().toString();
        alt_number =ed_altphone.getText().toString();
        lxi =ed_lxi.getText().toString();
        email =ed_email.getText().toString();
        fname =ed_fname.getText().toString();
        f127mi =ed_mi.getText().toString();
        lname =ed_lastname.getText().toString();
        dob =ed_dob.getText().toString();
        addres1 =ed_addressone.getText().toString();
        address2 =ed_addresstwo.getText().toString();
        social_num =ed_social_seq_num.getText().toString();
        lic_no =ed_lic_no.getText().toString();
        zip =ed_zip.getText().toString();
       ed_dob.setError((CharSequence) null);
        state =edt_state.getText().toString();
        city =edt_city.getText().toString();
        gender =edt_gender.getText().toString();
        if (phone.equalsIgnoreCase("")) {
           ed_phone.setError("Please Enter Phone Number");
        } else if (email.equalsIgnoreCase("")) {
           ed_email.setError("Please Enter Email");
        } else if (fname.equalsIgnoreCase("")) {
           ed_fname.setError("Please Enter First Name");
        } else if (lname.equalsIgnoreCase("")) {
           ed_lastname.setError("Please Enter Last Name");
        } else if (dob.equalsIgnoreCase("")) {
           ed_dob.setError("Please Enter Date of Birth");
        } else if (addres1.equalsIgnoreCase("")) {
           ed_addressone.setError("Please Enter Address");
        } else if (country.equalsIgnoreCase("")) {
            Toast.makeText(this, "please select Country", Toast.LENGTH_LONG).show();
        } else if (state.equalsIgnoreCase("")) {
            Toast.makeText(this, "please select State", Toast.LENGTH_LONG).show();
        } else if (city.equalsIgnoreCase("")) {
            Toast.makeText(this, "please select City", Toast.LENGTH_LONG).show();
        } else if (social_num.equalsIgnoreCase("")) {
           ed_social_seq_num.setError("Please Enter Social Id Number");
        } else if (lic_no.equalsIgnoreCase("")) {
           ed_lic_no.setError("Please Enter Licence number");
        } else if (gender.equalsIgnoreCase("")) {
           edt_gender.setError("Please Enter gender");

        } else if (this.chk_termsandcondition.isChecked()) {
            phone =phonec_code + " " + phone;
            alt_number =altc_code + " " + alt_number;
            startActivity(new Intent(this, CreateGiverDetailActivity.class));

        } else {
            Toast.makeText(caregiverregistrationactivity, "Please Accept Terms And Conditions",Toast.LENGTH_LONG).show();
        }
    }

    private class GetStateTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f129pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetStateTask() {
           url = "http://showupcare.com/WECARE/webservice/get_state?country_id=" + CareGiverRegistrationActivity.country_id;
           iserror = false;
           isValue = false;
           f129pd = ProgressDialog.show(CareGiverRegistrationActivity.this, "", "Please wait...", true);
           obj = null;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            try {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(this.url);
                try {
                    if (httpPost.toString().contains("unsuccessful")) {
                       isValue = true;
                        return null;
                    }
                    String entityUtils = EntityUtils.toString(defaultHttpClient.execute(httpPost).getEntity());
                    PrintStream printStream = System.out;
                    printStream.println("-------Response------" + entityUtils);
                   obj = new JSONObject(entityUtils);
                    return null;
                } catch (Exception e) {
                   f129pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                   iserror = true;
                    return null;
                }
            } catch (Exception e2) {
               f129pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
           f129pd.dismiss();
            try {
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray =obj.getJSONArray("result");
                    String[] unused = CareGiverRegistrationActivity.this.state_list = new String[jSONArray.length()];
                    String[] unused2 = CareGiverRegistrationActivity.this.state_idlist = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        String string = jSONArray.getJSONObject(i).getString("name");
                        String string2 = jSONArray.getJSONObject(i).getString("id");
                        CareGiverRegistrationActivity.this.state_list[i] = string;
                        CareGiverRegistrationActivity.this.state_idlist[i] = string2;
                    }
                    CareGiverRegistrationActivity.this.loadState();
                    return;
                }
                CareGiverRegistrationActivity careGiverRegistrationActivity = CareGiverRegistrationActivity.this;
                Toast.makeText(careGiverRegistrationActivity, "" +obj.getString("result"), Toast.LENGTH_LONG).show();
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
        final ProgressDialog f128pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetCityTask() {
           url = "http://showupcare.com/WECARE/webservice/get_city?country_id=" + CareGiverRegistrationActivity.country_id + "&state_id=" + CareGiverRegistrationActivity.state_id;
           iserror = false;
           isValue = false;
           f128pd = ProgressDialog.show(CareGiverRegistrationActivity.this, "", "Please wait...", true);
           obj = null;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            try {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(this.url);
                try {
                    if (httpPost.toString().contains("unsuccessful")) {
                       isValue = true;
                        return null;
                    }
                    String entityUtils = EntityUtils.toString(defaultHttpClient.execute(httpPost).getEntity());
                    PrintStream printStream = System.out;
                    printStream.println("-------Response------" + entityUtils);
                   obj = new JSONObject(entityUtils);
                    return null;
                } catch (Exception e) {
                   f128pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                   iserror = true;
                    return null;
                }
            } catch (Exception e2) {
               f128pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
           f128pd.dismiss();
            try {
                if (this.obj == null) {
                    return;
                }
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray =obj.getJSONArray("result");
                    String[] unused = CareGiverRegistrationActivity.this.city_list = new String[jSONArray.length()];
                    String[] unused2 = CareGiverRegistrationActivity.this.city_idlist = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        String string = jSONArray.getJSONObject(i).getString("name");
                        String string2 = jSONArray.getJSONObject(i).getString("id");
                        CareGiverRegistrationActivity.this.city_list[i] = string;
                        CareGiverRegistrationActivity.this.city_idlist[i] = string2;
                    }
                    CareGiverRegistrationActivity.this.loadcity();
                    return;
                }
                CareGiverRegistrationActivity careGiverRegistrationActivity = CareGiverRegistrationActivity.this;
                Toast.makeText(careGiverRegistrationActivity, "" +obj.getString("result"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void datepicker() {
        Calendar instance = Calendar.getInstance();
        new DatePickerDialog(caregiverregistrationactivity, R.style.AlertDialog, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                CareGiverRegistrationActivity.this.ed_dob.setText("" + (i2 + 1) + "/" + i3 + "/" + i);
            }
        }, instance.get(1), instance.get(2), instance.get(5)).show();
    }

    private void loadGender() {
        
       arrayAdapter = new ArrayAdapter<>(this, 0,gender_list);
       edt_gender.setThreshold(1);
       edt_gender.setAdapter(this.arrayAdapter);
    }

    public void loadState() {
       arrayAdapter = new ArrayAdapter<>(this, 0,state_list);
       edt_state.setThreshold(1);
       edt_state.setAdapter(this.arrayAdapter);
        new GetCityTask().execute(new Void[0]);
    }

    public void loadcity() {
       arrayAdapter = new ArrayAdapter<>(this, 0,city_list);
       edt_city.setThreshold(1);
       edt_city.setAdapter(this.arrayAdapter);
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Really Exit?").setMessage("Are you sure you want to exit?").setNegativeButton(0, (DialogInterface.OnClickListener) null).setPositiveButton(0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                CareGiverRegistrationActivity.this.setResult(-1, new Intent().putExtra("EXIT", true));
                MySharedPref.saveData(CareGiverRegistrationActivity.this, "data", "");
                MySharedPref.saveData(CareGiverRegistrationActivity.this, "dataone", "");
                CareGiverRegistrationActivity.this.finish();
            }
        }).create().show();
    }
}
