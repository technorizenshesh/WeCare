package com.tech.docarelat.Activity.ClientService;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tech.docarelat.Activity.Main.GetHelpActivity;
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
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ClientPersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayAdapter<String> arrayAdapter = null;
    private Button btn_register;
    private String c_code = "+1";
    /* access modifiers changed from: private */
    public Calendar cal = Calendar.getInstance();
    private final RequestParams category = new RequestParams();
    /* access modifiers changed from: private */
    public String[] city_list;
    private final AsyncHttpClient client = new AsyncHttpClient();
    int countDrop1 = 0;
    /* access modifiers changed from: private */
    public String current = "";
    /* access modifiers changed from: private */
    public String ddmmyyyy = "MMDDYYYY";
    /* access modifiers changed from: private */
    public AutoCompleteTextView ed_addressone;
    /* access modifiers changed from: private */
    public AutoCompleteTextView ed_addresstwo;
    /* access modifiers changed from: private */
    public EditText ed_dob;
    /* access modifiers changed from: private */
    public EditText ed_fname;
    /* access modifiers changed from: private */
    public EditText ed_lastname;
    /* access modifiers changed from: private */
    public EditText ed_mi;
    /* access modifiers changed from: private */
    public EditText ed_relation;
    /* access modifiers changed from: private */
    public EditText ed_relphone;
    /* access modifiers changed from: private */
    public EditText ed_zip;
    /* access modifiers changed from: private */
    public AutoCompleteTextView edt_city;
    /* access modifiers changed from: private */
    public AutoCompleteTextView edt_state;
    private ImageButton imgLeftMenu;
    /* access modifiers changed from: private */
    public String language;
    /* access modifiers changed from: private */
    public String[] language_list;
    /* access modifiers changed from: private */
    public String[] language_list1;
    /* access modifiers changed from: private */
    public String lastChar = "";
    private Double latitude;
    private Double longitude;
    /* access modifiers changed from: private */
    public String pre_language;
    /* access modifiers changed from: private */
    public String raddrass2;
    /* access modifiers changed from: private */
    public String raddress1;
    /* access modifiers changed from: private */
    public String rcity;
    /* access modifiers changed from: private */
    public String rdob;
    private String regId;
    /* access modifiers changed from: private */
    public String relation;
    /* access modifiers changed from: private */
    public String rfname;
    /* access modifiers changed from: private */
    public String rlname;
    /* access modifiers changed from: private */
    public String rmi;
    private String rphone;
    /* access modifiers changed from: private */
    public String rstate;
    /* access modifiers changed from: private */
    public String rstate_id;
    /* access modifiers changed from: private */
    public String rzip;
    private Spinner sp_language;
    private Spinner sp_prelanguage;
    /* access modifiers changed from: private */
    public String[] state_idlist;
    /* access modifiers changed from: private */
    public String[] state_list;
    private TextView txt_gethelp;
    private String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_client_personal_info);
        findId();
        this.regId = MySharedPref.getData(this, "regId", "");
        new GetLanguageTask().execute(new Void[0]);
        this.btn_register.setOnClickListener(this);
        this.imgLeftMenu.setOnClickListener(this);
        this.txt_gethelp.setOnClickListener(this);
        try {
            GPSTracker gPSTracker = new GPSTracker(ClientRegistrationActivity.clientRegister);
            if (gPSTracker.canGetLocation()) {
                this.latitude = Double.valueOf(gPSTracker.getLatitude());
                this.longitude = Double.valueOf(gPSTracker.getLongitude());
            } else {
                gPSTracker.showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.ed_addressone.setThreshold(1);
        this.ed_addressone.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.raddress1 = clientPersonalInfoActivity.ed_addressone.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "raddress1", "" + ClientPersonalInfoActivity.this.raddress1);
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
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.raddrass2 = clientPersonalInfoActivity.ed_addresstwo.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "raddrass2", "" + ClientPersonalInfoActivity.this.raddrass2);
            }

            public void afterTextChanged(Editable editable) {
                editable.length();
            }
        });
        this.ed_relphone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = ClientPersonalInfoActivity.this.ed_relphone.getText().toString().length();
                if (length > 1) {
                    ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                    String unused = clientPersonalInfoActivity.lastChar = clientPersonalInfoActivity.ed_relphone.getText().toString().substring(length - 1);
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = ClientPersonalInfoActivity.this.ed_relphone.getText().toString().length();
                Log.d("LENGTH", "" + length);
                if (ClientPersonalInfoActivity.this.lastChar.equals("-")) {
                    return;
                }
                if (length == 3 || length == 7) {
                    ClientPersonalInfoActivity.this.ed_relphone.append("-");
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
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.rdob = clientPersonalInfoActivity.ed_dob.getText().toString();
                MySharedPref.saveData(ClientPersonalInfoActivity.this, "rdob", "" + ClientPersonalInfoActivity.this.rdob);
                if (!charSequence.toString().equals(ClientPersonalInfoActivity.this.current)) {
                    String replaceAll = charSequence.toString().replaceAll("[^\\d.]|\\.", "");
                    String replaceAll2 = ClientPersonalInfoActivity.this.current.replaceAll("[^\\d.]|\\.", "");
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
                        str = replaceAll + ClientPersonalInfoActivity.this.ddmmyyyy.substring(replaceAll.length());
                    } else {
                        int parseInt = Integer.parseInt(replaceAll.substring(2, 4));
                        int parseInt2 = Integer.parseInt(replaceAll.substring(0, 2));
                        int parseInt3 = Integer.parseInt(replaceAll.substring(4, 8));
                        if (parseInt2 < 1) {
                            parseInt2 = 1;
                        } else if (parseInt2 > 12) {
                            parseInt2 = 12;
                        }
                        ClientPersonalInfoActivity.this.cal.set(2, parseInt2 - 1);
                        if (parseInt3 < 1900) {
                            parseInt3 = 1900;
                        } else if (parseInt3 > 2100) {
                            parseInt3 = 2100;
                        }
                        ClientPersonalInfoActivity.this.cal.set(1, parseInt3);
                        if (parseInt > ClientPersonalInfoActivity.this.cal.getActualMaximum(5)) {
                            parseInt = ClientPersonalInfoActivity.this.cal.getActualMaximum(5);
                        }
                        str = String.format("%02d%02d%02d", new Object[]{Integer.valueOf(parseInt2), Integer.valueOf(parseInt), Integer.valueOf(parseInt3)});
                    }
                    String format = String.format("%s/%s/%s", new Object[]{str.substring(0, 2), str.substring(2, 4), str.substring(4, 8)});
                    if (i4 < 0) {
                        i4 = 0;
                    }
                    String unused2 = ClientPersonalInfoActivity.this.current = format;
                    ClientPersonalInfoActivity.this.ed_dob.setText(ClientPersonalInfoActivity.this.current);
                    EditText access$800 = ClientPersonalInfoActivity.this.ed_dob;
                    if (i4 >= ClientPersonalInfoActivity.this.current.length()) {
                        i4 = ClientPersonalInfoActivity.this.current.length();
                    }
                    access$800.setSelection(i4);
                }
            }
        });
        this.ed_relation.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.relation = clientPersonalInfoActivity.ed_relation.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "relation", "" + ClientPersonalInfoActivity.this.relation);
            }
        });
        this.ed_fname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.rfname = clientPersonalInfoActivity.ed_fname.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "rfname", "" + ClientPersonalInfoActivity.this.rfname);
            }
        });
        this.ed_mi.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.rmi = clientPersonalInfoActivity.ed_mi.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "rmi", "" + ClientPersonalInfoActivity.this.rmi);
            }
        });
        this.ed_lastname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.rlname = clientPersonalInfoActivity.ed_lastname.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "rlname", "" + ClientPersonalInfoActivity.this.rlname);
            }
        });
        this.ed_dob.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.rdob = clientPersonalInfoActivity.ed_dob.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "rdob", "" + ClientPersonalInfoActivity.this.rdob);
            }
        });
        this.edt_city.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.rcity = clientPersonalInfoActivity.edt_city.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "rcity", "" + ClientPersonalInfoActivity.this.rcity);
            }
        });
        this.edt_state.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.rstate = clientPersonalInfoActivity.edt_state.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "rstate", "" + ClientPersonalInfoActivity.this.rstate);
            }
        });
        this.ed_zip.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.rzip = clientPersonalInfoActivity.ed_zip.getText().toString();
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "rzip", "" + ClientPersonalInfoActivity.this.rzip);
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
            this.ed_relphone.setText(jSONObject.getString("r_phone").replace("+1 ", ""));
            this.ed_fname.setText(jSONObject.getString("r_first_name"));
            this.ed_relation.setText(jSONObject.getString("relationship"));
            this.ed_mi.setText(jSONObject.getString("r_mi"));
            this.ed_lastname.setText(jSONObject.getString("r_last_name"));
            this.ed_dob.setText(jSONObject.getString("r_dob"));
            this.ed_addressone.setText(jSONObject.getString("r_address_line1"));
            this.ed_addresstwo.setText(jSONObject.getString("r_address_line2"));
            this.edt_city.setText(jSONObject.getString("r_city"));
            this.edt_state.setText(jSONObject.getString("r_state"));
            this.ed_zip.setText(jSONObject.getString("r_ca"));
            this.language = jSONObject.getString("spoken_lang");
            this.pre_language = jSONObject.getString("preferred_lang");
            this.btn_register.setText("Update");
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
                    GeoAutoCompleteAdapter geoAutoCompleteAdapter = new GeoAutoCompleteAdapter(ClientRegistrationActivity.clientRegister, arrayList, "" + this.latitude, "" + this.longitude);
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
        this.sp_language = (Spinner) findViewById(R.id.sp_language);
        this.sp_prelanguage = (Spinner) findViewById(R.id.sp_prelanguage);
        this.btn_register = (Button) findViewById(R.id.btn_register);
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        this.txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);
        this.ed_relphone = (EditText) findViewById(R.id.ed_relphone);
        this.ed_relation = (EditText) findViewById(R.id.ed_relation);
        this.ed_fname = (EditText) findViewById(R.id.ed_fname);
        this.ed_mi = (EditText) findViewById(R.id.ed_mi);
        this.ed_dob = (EditText) findViewById(R.id.ed_dob);
        this.ed_addressone = (AutoCompleteTextView) findViewById(R.id.ed_addressone);
        this.ed_addresstwo = (AutoCompleteTextView) findViewById(R.id.ed_addresstwo);
        this.ed_lastname = (EditText) findViewById(R.id.ed_lastname);
        this.edt_state = (AutoCompleteTextView) findViewById(R.id.edt_state);
        this.edt_city = (AutoCompleteTextView) findViewById(R.id.edt_city);
        this.ed_zip = (EditText) findViewById(R.id.ed_zip);
    }

    public void onClick(View view) {
        if (view == this.btn_register) {
            validate();
        } else if (view == this.imgLeftMenu) {
            finish();
        } else if (view == this.txt_gethelp) {
            startActivity(new Intent(this, GetHelpActivity.class));
        }
    }

    public void datepicker() {
        Calendar instance = Calendar.getInstance();
        new DatePickerDialog(this, R.style.AlertDialog, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                ClientPersonalInfoActivity.this.ed_dob.setText("" + (i2 + 1) + "/" + i3 + "/" + i);
            }
        }, instance.get(1), instance.get(2), instance.get(5)).show();
    }

    private void validate() {
        this.rphone = this.ed_relphone.getText().toString();
        this.relation = this.ed_relation.getText().toString();
        this.rfname = this.ed_fname.getText().toString();
        this.rmi = this.ed_mi.getText().toString();
        this.rlname = this.ed_lastname.getText().toString();
        this.rdob = this.ed_dob.getText().toString();
        this.raddress1 = this.ed_addressone.getText().toString();
        this.raddrass2 = this.ed_addresstwo.getText().toString();
        this.rstate = this.edt_state.getText().toString();
        this.rcity = this.edt_city.getText().toString();
        this.rzip = this.ed_zip.getText().toString();
        if (this.language.equals("Spoken language")) {
            Toast.makeText(this, "Please Select Spoken Language", Toast.LENGTH_LONG).show();
        } else if (this.pre_language.equals("Preferred language")) {
            Toast.makeText(this, "Please Select Preferred Language", Toast.LENGTH_LONG).show();
        } else {
            this.rphone = this.c_code + " " + this.rphone;
            if (MySharedPref.getData(this, "ldata", (String) null) != null) {
                update();
            } else {
                registration();
            }
        }
    }

    private void loadData() {
        if (MySharedPref.getData(this, "data", "").equals("yes")) {
            EditText editText = this.ed_relphone;
            editText.setText("" + MySharedPref.getData(this, "rphone", ""));
            EditText editText2 = this.ed_relation;
            editText2.setText("" + MySharedPref.getData(this, "relation", ""));
            EditText editText3 = this.ed_fname;
            editText3.setText("" + MySharedPref.getData(this, "rfname", ""));
            EditText editText4 = this.ed_mi;
            editText4.setText("" + MySharedPref.getData(this, "rmi", ""));
            EditText editText5 = this.ed_lastname;
            editText5.setText("" + MySharedPref.getData(this, "rlname", ""));
            EditText editText6 = this.ed_dob;
            editText6.setText("" + MySharedPref.getData(this, "rdob", ""));
            AutoCompleteTextView autoCompleteTextView = this.ed_addressone;
            autoCompleteTextView.setText("" + MySharedPref.getData(this, "raddress1", ""));
            AutoCompleteTextView autoCompleteTextView2 = this.ed_addresstwo;
            autoCompleteTextView2.setText("" + MySharedPref.getData(this, "   raddrass2  ", ""));
            AutoCompleteTextView autoCompleteTextView3 = this.edt_city;
            autoCompleteTextView3.setText("" + MySharedPref.getData(this, "rcity", ""));
            AutoCompleteTextView autoCompleteTextView4 = this.edt_state;
            autoCompleteTextView4.setText("" + MySharedPref.getData(this, "rstate", ""));
            EditText editText7 = this.ed_zip;
            editText7.setText("" + MySharedPref.getData(this, "rzip", ""));
        }
    }

    public void registration() {
        final ProgressDialog show = ProgressDialog.show(this, "", "Please wait...", true);
        try {
            this.category.put("first_name", ClientRegistrationActivity.fname);
            this.category.put("last_name", ClientRegistrationActivity.lname);
            this.category.put(PhoneAuthProvider.PROVIDER_ID, ClientRegistrationActivity.phone);
            this.category.put("alt_phone", ClientRegistrationActivity.alt_number);
            this.category.put("ext1", ClientRegistrationActivity.ext);
            this.category.put("ext2", ClientRegistrationActivity.lxi);
            this.category.put("email", ClientRegistrationActivity.email);
            this.category.put("mi", ClientRegistrationActivity.f136mi);
            this.category.put("dob", ClientRegistrationActivity.dob);
            this.category.put("address_line1", ClientRegistrationActivity.addres1);
            this.category.put("address_line2", ClientRegistrationActivity.address2);
            this.category.put("country", ClientRegistrationActivity.country);
            this.category.put("state", ClientRegistrationActivity.state);
            this.category.put("city", ClientRegistrationActivity.city);
            this.category.put("zip_code", ClientRegistrationActivity.zip);
            this.category.put("username", ClientRegistrationActivity.username);
            this.category.put(EmailAuthProvider.PROVIDER_ID, ClientRegistrationActivity.pass1);
            this.category.put("gender", ClientRegistrationActivity.gender);
            this.category.put("spoken_lang", this.language);
            this.category.put("preferred_lang", this.pre_language);
            this.category.put("r_phone", this.rphone);
            this.category.put("relationship", this.relation);
            this.category.put("r_email", "");
            this.category.put("r_first_name", this.rfname);
            this.category.put("r_last_name", this.rlname);
            this.category.put("r_mi", this.rmi);
            this.category.put("r_dob", this.rdob);
            this.category.put("r_address_line1", this.raddress1);
            this.category.put("r_address_line2", this.raddrass2);
            this.category.put("r_country", ClientRegistrationActivity.country);
            this.category.put("r_state", this.rstate);
            this.category.put("r_city", this.rcity);
            this.category.put("r_ca", this.rzip);
            this.category.put("card_type", PaymentInfoActivity.card_type);
            this.category.put("card_name", PaymentInfoActivity.card_name);
            this.category.put("card_num", PaymentInfoActivity.card_num);
            this.category.put("exp_date", PaymentInfoActivity.exp_date);
            this.category.put("cvv", PaymentInfoActivity.cvv);
            this.category.put("caddress1", PaymentInfoActivity.caddress1);
            this.category.put("caddres2", PaymentInfoActivity.caddres2);
            this.category.put("c_country", ClientRegistrationActivity.country);
            this.category.put("c_state", PaymentInfoActivity.cstate);
            this.category.put("c_city", PaymentInfoActivity.ccity);
            this.category.put("c_zip_code", PaymentInfoActivity.czip);
            this.category.put("type", "CLIENT");
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
                    Toast.makeText(ClientPersonalInfoActivity.this, "Something went wrong, Try Again !", Toast.LENGTH_LONG).show();
                }

                public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------response----------- " + jSONObject);
                    try {
                        if (jSONObject.getString("status").equals("1")) {
                            MySharedPref.saveData(ClientPersonalInfoActivity.this, "data", "");
                            MySharedPref.saveData(ClientPersonalInfoActivity.this, "dataone", "");
                            Toast.makeText(ClientPersonalInfoActivity.this, "Please Login....", Toast.LENGTH_LONG).show();
                            Context applicationContext = ClientPersonalInfoActivity.this.getApplicationContext();
                            MySharedPref.saveData(applicationContext, "ldata", "" + jSONObject);
                            ClientPersonalInfoActivity.this.startActivity(new Intent(ClientPersonalInfoActivity.this, ClientLoginActivity.class));
                            ClientRegistrationActivity.clientRegister.finish();
                            PaymentInfoActivity.clientpaymentactivity.finish();
                            ClientPersonalInfoActivity.this.finish();
                            return;
                        }
                        ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                        Toast.makeText(clientPersonalInfoActivity, "" + jSONObject.getString("result"), Toast.LENGTH_LONG).show();
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
            this.category.put("first_name", ClientRegistrationActivity.fname);
            this.category.put("last_name", ClientRegistrationActivity.lname);
            this.category.put(PhoneAuthProvider.PROVIDER_ID, ClientRegistrationActivity.phone);
            this.category.put("alt_phone", ClientRegistrationActivity.alt_number);
            this.category.put("ext1", ClientRegistrationActivity.ext);
            this.category.put("ext2", ClientRegistrationActivity.lxi);
            this.category.put("email", ClientRegistrationActivity.email);
            this.category.put("mi", ClientRegistrationActivity.f136mi);
            this.category.put("dob", ClientRegistrationActivity.dob);
            this.category.put("address_line1", ClientRegistrationActivity.addres1);
            this.category.put("address_line2", ClientRegistrationActivity.address2);
            this.category.put("country", ClientRegistrationActivity.country);
            this.category.put("state", ClientRegistrationActivity.state);
            this.category.put("city", ClientRegistrationActivity.city);
            this.category.put("zip_code", ClientRegistrationActivity.zip);
            this.category.put("username", ClientRegistrationActivity.username);
            this.category.put(EmailAuthProvider.PROVIDER_ID, ClientRegistrationActivity.pass1);
            this.category.put("gender", ClientRegistrationActivity.gender);
            this.category.put("spoken_lang", this.language);
            this.category.put("preferred_lang", this.pre_language);
            this.category.put("r_phone", this.rphone);
            this.category.put("relationship", this.relation);
            this.category.put("r_email", "");
            this.category.put("r_first_name", this.rfname);
            this.category.put("r_last_name", this.rlname);
            this.category.put("r_mi", this.rmi);
            this.category.put("r_dob", this.rdob);
            this.category.put("r_address_line1", this.raddress1);
            this.category.put("r_address_line2", this.raddrass2);
            this.category.put("r_country", ClientRegistrationActivity.country);
            this.category.put("r_state", this.rstate);
            this.category.put("r_city", this.rcity);
            this.category.put("r_ca", this.rzip);
            this.category.put("card_type", PaymentInfoActivity.card_type);
            this.category.put("card_name", PaymentInfoActivity.card_name);
            this.category.put("card_num", PaymentInfoActivity.card_num);
            this.category.put("exp_date", PaymentInfoActivity.exp_date);
            this.category.put("cvv", PaymentInfoActivity.cvv);
            this.category.put("caddress1", PaymentInfoActivity.caddress1);
            this.category.put("caddres2", PaymentInfoActivity.caddres2);
            this.category.put("c_country", ClientRegistrationActivity.country);
            this.category.put("c_state", PaymentInfoActivity.cstate);
            this.category.put("c_city", PaymentInfoActivity.ccity);
            this.category.put("c_zip_code", PaymentInfoActivity.czip);
            this.category.put("type", "CLIENT");
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
                    Toast.makeText(ClientPersonalInfoActivity.this, "Something went wrong, Try Again !", Toast.LENGTH_LONG).show();
                }

                public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------response----------- " + jSONObject);
                    try {
                        if (jSONObject.getString("status").equals("1")) {
                            MySharedPref.saveData(ClientPersonalInfoActivity.this, "data", "");
                            MySharedPref.saveData(ClientPersonalInfoActivity.this, "dataone", "");
                            Toast.makeText(ClientPersonalInfoActivity.this, "Success", Toast.LENGTH_LONG).show();
                            ClientRegistrationActivity.clientRegister.finish();
                            PaymentInfoActivity.clientpaymentactivity.finish();
                            ClientPersonalInfoActivity.this.finish();
                            return;
                        }
                        ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                        Toast.makeText(clientPersonalInfoActivity, "" + jSONObject.getString("result"), Toast.LENGTH_LONG).show();
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

    private class GetLanguageTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f134pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetLanguageTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_languages";
            this.iserror = false;
            this.isValue = false;
            this.f134pd = ProgressDialog.show(ClientPersonalInfoActivity.this, "", "Please wait...", true);
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
                    this.f134pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f134pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f134pd.dismiss();
            try {
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    String[] unused = ClientPersonalInfoActivity.this.language_list = new String[(jSONArray.length() + 1)];
                    String[] unused2 = ClientPersonalInfoActivity.this.language_list1 = new String[(jSONArray.length() + 1)];
                    int i = 0;
                    if (MySharedPref.getData(ClientPersonalInfoActivity.this, "dataone", "").equals("yes")) {
                        String[] access$2600 = ClientPersonalInfoActivity.this.language_list;
                        access$2600[0] = "" + MySharedPref.getData(ClientPersonalInfoActivity.this, "language", "");
                        String[] access$2700 = ClientPersonalInfoActivity.this.language_list1;
                        access$2700[0] = "" + MySharedPref.getData(ClientPersonalInfoActivity.this, "pre_language", "");
                    } else {
                        String data = MySharedPref.getData(ClientPersonalInfoActivity.this, "ldata", (String) null);
                        if (data != null) {
                            try {
                                JSONObject jSONObject = new JSONObject(data).getJSONObject("result");
                                ClientPersonalInfoActivity.this.language_list[0] = jSONObject.getString("spoken_lang");
                                ClientPersonalInfoActivity.this.language_list1[0] = jSONObject.getString("preferred_lang");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ClientPersonalInfoActivity.this.language_list[0] = "Spoken language";
                            ClientPersonalInfoActivity.this.language_list1[0] = "Preferred language";
                        }
                    }
                    while (i < jSONArray.length()) {
                        String string = jSONArray.getJSONObject(i).getString("language");
                        i++;
                        ClientPersonalInfoActivity.this.language_list[i] = string;
                        ClientPersonalInfoActivity.this.language_list1[i] = string;
                    }
                    ClientPersonalInfoActivity.this.loadLanguage();
                    return;
                }
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                Toast.makeText(clientPersonalInfoActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void loadLanguage() {
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, 0, this.language_list);
        arrayAdapter2.setDropDownViewResource(0);
        this.sp_language.setAdapter(arrayAdapter2);
        this.sp_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.language = clientPersonalInfoActivity.language_list[i];
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "language", "" + ClientPersonalInfoActivity.this.language);
            }
        });
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, 0, this.language_list1);
        arrayAdapter3.setDropDownViewResource(0);
        this.sp_prelanguage.setAdapter(arrayAdapter3);
        this.sp_prelanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                String unused = clientPersonalInfoActivity.pre_language = clientPersonalInfoActivity.language_list1[i];
                ClientPersonalInfoActivity clientPersonalInfoActivity2 = ClientPersonalInfoActivity.this;
                MySharedPref.saveData(clientPersonalInfoActivity2, "pre_language", "" + ClientPersonalInfoActivity.this.pre_language);
            }
        });
        new GetStateTask().execute(new Void[0]);
    }

    public void onBackPressed() {
        MySharedPref.saveData(this, "dataone", "yes");
        finish();
    }

    private class GetStateTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f135pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetStateTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_state?country_id=" + ClientRegistrationActivity.country_id;
            this.iserror = false;
            this.isValue = false;
            this.f135pd = ProgressDialog.show(ClientPersonalInfoActivity.this, "", "Please wait...", true);
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
                    this.f135pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f135pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f135pd.dismiss();
            try {
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    String[] unused = ClientPersonalInfoActivity.this.state_list = new String[jSONArray.length()];
                    String[] unused2 = ClientPersonalInfoActivity.this.state_idlist = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        String string = jSONArray.getJSONObject(i).getString("name");
                        String string2 = jSONArray.getJSONObject(i).getString("id");
                        ClientPersonalInfoActivity.this.state_list[i] = string;
                        ClientPersonalInfoActivity.this.state_idlist[i] = string2;
                    }
                    ClientPersonalInfoActivity.this.loadState();
                    return;
                }
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                Toast.makeText(clientPersonalInfoActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
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
        final ProgressDialog f133pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetCityTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_city?country_id=" + ClientRegistrationActivity.country_id + "&state_id=" + ClientPersonalInfoActivity.this.rstate_id;
            this.iserror = false;
            this.isValue = false;
            this.f133pd = ProgressDialog.show(ClientPersonalInfoActivity.this, "", "Please wait...", true);
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
                    this.f133pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f133pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f133pd.dismiss();
            try {
                if (this.obj == null) {
                    return;
                }
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    String[] unused = ClientPersonalInfoActivity.this.city_list = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        ClientPersonalInfoActivity.this.city_list[i] = jSONArray.getJSONObject(i).getString("name");
                    }
                    ClientPersonalInfoActivity.this.loadcity();
                    return;
                }
                ClientPersonalInfoActivity clientPersonalInfoActivity = ClientPersonalInfoActivity.this;
                Toast.makeText(clientPersonalInfoActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
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
