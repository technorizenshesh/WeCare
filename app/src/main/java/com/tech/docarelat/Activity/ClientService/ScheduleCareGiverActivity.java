package com.tech.docarelat.Activity.ClientService;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tech.docarelat.Activity.Main.GetHelpActivity;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ScheduleCareGiverActivity extends AppCompatActivity implements View.OnClickListener {
    public static String address_one = "";
    public static String address_two = "";
    public static String city = "";
    public static int count_one = 0;
    public static int count_two = 0;
    public static String country_id = "231";
    public static long days = 0;
    public static String end_date = "";
    public static String end_time = "";
    public static int final_count = 0;
    public static int first_mnth = 0;
    public static String gender = "";
    public static Boolean isEdit = false;
    public static int last_mnth = 0;
    public static Activity schedulecareactivity = null;
    public static int shour = 0;
    public static int sminute = 0;
    public static String start_date = "";
    public static String start_time = "";
    public static String state = "";
    public static String state_id = "";
    public static String time_type = "One Time";
    public static String type = "";
    public static String zip = "";
    ArrayAdapter<String> arrayAdapter = null;
    private final RequestParams category = new RequestParams();
    /* access modifiers changed from: private */
    public String[] city_idlist;
    /* access modifiers changed from: private */
    public String[] city_list;
    private final AsyncHttpClient client = new AsyncHttpClient();
    private EditText ed_addressone;
    private EditText ed_addresstwo;
    private EditText ed_zip;
    private AutoCompleteTextView edt_city;
    private AutoCompleteTextView edt_state;
    private RadioGroup gender_radiogroup;
    String giver_id = "";
    private ImageButton imgLeftMenu;
    private ImageView img_enddate;
    private ImageView img_endtime;
    private ImageView img_startdate;
    private ImageView img_starttime;
    private LinearLayout lay_daily;
    private LinearLayout lay_onetime;
    private LinearLayout lay_weekly;
    Calendar mindatcal;
    private RadioButton radioButton;
    String sAM_PM;
    /* access modifiers changed from: private */
    public String[] state_idlist;
    /* access modifiers changed from: private */
    public String[] state_list;
    String status = "";
    long timeInMill;
    private TextView txt_continue;
    /* access modifiers changed from: private */
    public TextView txt_enddate;
    private TextView txt_endtime;
    private TextView txt_gethelp;
    /* access modifiers changed from: private */
    public TextView txt_startdate;
    private TextView txt_starttime;
    private RadioGroup type_radiogroup;
    String user_id;
    private View view_daily;
    private View view_onetime;
    private View view_weekly;
    String worktype = "";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_schedule_care_giver);
        findId();
        this.user_id = MySharedPref.getData(this, "user_id", "");
        schedulecareactivity = this;
        this.imgLeftMenu.setOnClickListener(this);
        this.lay_onetime.setOnClickListener(this);
        this.lay_daily.setOnClickListener(this);
        this.lay_weekly.setOnClickListener(this);
        this.txt_continue.setOnClickListener(this);
        this.txt_gethelp.setOnClickListener(this);
        this.img_startdate.setOnClickListener(this);
        this.img_enddate.setOnClickListener(this);
        this.img_starttime.setOnClickListener(this);
        this.img_endtime.setOnClickListener(this);
        new GetStateTask().execute(new Void[0]);
        if (getIntent().getExtras() != null) {
            String string = getIntent().getExtras().getString("data");
            this.status = getIntent().getExtras().getString("status");
            Log.e("worktype", "====================>" + this.status);
            try {
                loadData(new JSONObject(string));
                isEdit = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadData(JSONObject jSONObject) {
        Log.e("object", "" + jSONObject);
        try {
            EditText editText = this.ed_addressone;
            editText.setText("" + jSONObject.getString("address"));
            EditText editText2 = this.ed_addresstwo;
            editText2.setText("" + jSONObject.getString("address1"));
            this.giver_id = jSONObject.getString("address1");
            AutoCompleteTextView autoCompleteTextView = this.edt_state;
            autoCompleteTextView.setText("" + jSONObject.getString("state"));
            AutoCompleteTextView autoCompleteTextView2 = this.edt_city;
            autoCompleteTextView2.setText("" + jSONObject.getString("city"));
            EditText editText3 = this.ed_zip;
            editText3.setText("" + jSONObject.getString("zip"));
            TextView textView = this.txt_startdate;
            textView.setText("" + jSONObject.getString(FirebaseAnalytics.Param.START_DATE));
            TextView textView2 = this.txt_starttime;
            textView2.setText("" + jSONObject.getString("start_time"));
            start_date = jSONObject.getString(FirebaseAnalytics.Param.START_DATE);
            end_date = jSONObject.getString(FirebaseAnalytics.Param.END_DATE);
            start_time = jSONObject.getString("start_time");
            end_time = jSONObject.getString("end_time");
            TextView textView3 = this.txt_enddate;
            textView3.setText("" + jSONObject.getString(FirebaseAnalytics.Param.END_DATE));
            TextView textView4 = this.txt_endtime;
            textView4.setText("" + jSONObject.getString("end_time"));
            type = jSONObject.getString("service_type");
            time_type = jSONObject.getString("work_type");
            this.worktype = jSONObject.getString("work_type");
            if (time_type.equals("One Time")) {
                this.view_onetime.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_blue));
                this.view_daily.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
                this.view_weekly.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
                time_type = "One Time";
            } else if (time_type.equals("Daily")) {
                this.view_onetime.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
                this.view_daily.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_blue));
                this.view_weekly.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
                time_type = "Daily";
            } else if (time_type.equals("Weekly")) {
                this.view_onetime.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
                this.view_daily.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
                this.view_weekly.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_blue));
                time_type = "Weekly";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void findId() {
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        this.lay_onetime = (LinearLayout) findViewById(R.id.lay_onetime);
        this.lay_daily = (LinearLayout) findViewById(R.id.lay_daily);
        this.lay_weekly = (LinearLayout) findViewById(R.id.lay_weekly);
        this.view_onetime = findViewById(R.id.view_onetime);
        this.view_daily = findViewById(R.id.view_daily);
        this.view_weekly = findViewById(R.id.view_weekly);
        this.txt_continue = (TextView) findViewById(R.id.txt_continue);
        this.txt_gethelp = (TextView) findViewById(R.id.txt_gethelp);
        this.txt_startdate = (TextView) findViewById(R.id.txt_startdate);
        this.txt_enddate = (TextView) findViewById(R.id.txt_enddate);
        this.txt_starttime = (TextView) findViewById(R.id.txt_starttime);
        this.txt_endtime = (TextView) findViewById(R.id.txt_endtime);
        this.img_startdate = (ImageView) findViewById(R.id.img_startdate);
        this.img_enddate = (ImageView) findViewById(R.id.img_enddate);
        this.img_starttime = (ImageView) findViewById(R.id.img_starttime);
        this.img_endtime = (ImageView) findViewById(R.id.img_endtime);
        this.type_radiogroup = (RadioGroup) findViewById(R.id.type_radiogroup);
        this.gender_radiogroup = (RadioGroup) findViewById(R.id.gender_radiogroup);
        this.ed_addressone = (EditText) findViewById(R.id.ed_addressone);
        this.ed_addresstwo = (EditText) findViewById(R.id.ed_addresstwo);
        this.edt_state = (AutoCompleteTextView) findViewById(R.id.edt_state);
        this.edt_city = (AutoCompleteTextView) findViewById(R.id.edt_city);
        this.ed_zip = (EditText) findViewById(R.id.ed_zip);
    }

    public void onClick(View view) {
        if (view == this.imgLeftMenu) {
            finish();
        } else if (view == this.lay_onetime) {
            this.view_onetime.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_blue));
            this.view_daily.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
            this.view_weekly.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
            time_type = "One Time";
            Toast.makeText(this, "" + time_type, Toast.LENGTH_LONG).show();
        } else if (view == this.lay_daily) {
            this.view_onetime.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
            this.view_daily.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_blue));
            this.view_weekly.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
            time_type = "Daily";
            Toast.makeText(this, "" + time_type, Toast.LENGTH_LONG).show();
        } else if (view == this.lay_weekly) {
            this.view_onetime.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
            this.view_daily.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_black));
            this.view_weekly.setBackgroundColor(ContextCompat.getColor(this, R.color.my_app_blue));
            time_type = "Weekly";
            Toast.makeText(this, "" + time_type, Toast.LENGTH_LONG).show();
        } else if (view == this.txt_continue) {
            vaildate();
        } else if (view == this.txt_gethelp) {
            startActivity(new Intent(this, GetHelpActivity.class));
        } else if (view == this.img_startdate) {
            showCalView();
        } else if (view == this.img_enddate) {
            if (start_date.equalsIgnoreCase("")) {
                Toast.makeText(this, "Please select start date first... ", Toast.LENGTH_LONG).show();
            } else {
                showCalView1();
            }
        } else if (view == this.img_starttime) {
            if (end_date.equalsIgnoreCase("")) {
                Toast.makeText(this, "Please select end date first... ", Toast.LENGTH_LONG).show();
            } else {
                showTimePicker(this.txt_starttime);
            }
        } else if (view != this.img_endtime) {
        } else {
            if (start_time.equalsIgnoreCase("")) {
                Toast.makeText(this, "Please select start time first... ", Toast.LENGTH_LONG).show();
            } else {
                showTimePicker1(this.txt_endtime);
            }
        }
    }

    public void printDifference(Date date, Date date2) {
        long time = date2.getTime() - date.getTime();
        PrintStream printStream = System.out;
        printStream.println("startDate : " + date);
        PrintStream printStream2 = System.out;
        printStream2.println("endDate : " + date2);
        PrintStream printStream3 = System.out;
        printStream3.println("different : " + time);
        long j = time / 86400000;
        days = Math.abs(j);
        long j2 = time % 86400000;
        long j3 = j2 / 3600000;
        long j4 = j2 % 3600000;
        System.out.printf("%d days, %d hours, %d minutes, %d seconds%n", new Object[]{Long.valueOf(j), Long.valueOf(j3), Long.valueOf(j4 / 60000), Long.valueOf((j4 % 60000) / 1000)});
    }

    private void vaildate() {
        try {
            printDifference(new SimpleDateFormat("MM/dd/yyyy").parse(start_date), new SimpleDateFormat("MM/dd/yyyy").parse(end_date));
        } catch (ParseException e) {
            e.printStackTrace();
            if (first_mnth == last_mnth) {
                final_count = (count_two - count_one) + 1;
            } else {
                int i = (count_two - count_one) + 1;
                final_count = i;
                final_count = i + 30;
            }
        }
        days++;
        Log.e("final_count ==> ", "" + days);
        start_time = this.txt_starttime.getText().toString();
        end_time = this.txt_endtime.getText().toString();
        city = this.edt_city.getText().toString();
        state = this.edt_state.getText().toString();
        zip = this.ed_zip.getText().toString();
        RadioButton radioButton2 = (RadioButton) findViewById(this.type_radiogroup.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        type = radioButton2.getText().toString();
        RadioButton radioButton3 = (RadioButton) findViewById(this.gender_radiogroup.getCheckedRadioButtonId());
        this.radioButton = radioButton3;
        gender = radioButton3.getText().toString();
        address_one = this.ed_addressone.getText().toString();
        address_two = this.ed_addresstwo.getText().toString();
        if (start_date.equals("Start Date")) {
            Toast.makeText(this, "Please select start date...", Toast.LENGTH_LONG).show();
        } else if (end_date.equals("End Date")) {
            Toast.makeText(this, "Please select end date...", Toast.LENGTH_LONG).show();
        } else if (start_time.equals("Start Time")) {
            Toast.makeText(this, "Please select start time", Toast.LENGTH_LONG).show();
        } else if (end_time.equals("End Time")) {
            Toast.makeText(this, "Please select end time", Toast.LENGTH_LONG).show();
        } else if (this.status.equalsIgnoreCase("")) {
            System.out.println(time_type + " , " + start_date + " , " + end_date + " , " + start_time + " , " + end_time + " , " + type + " , " + address_one + " , " + address_two + " , " + gender);
            startActivity(new Intent(this, SeeScheduleListActivity.class));
        } else {
            updateRequest();
        }
    }

    public void showCalView() {
        final Calendar instance = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                String str;
                String str2 = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}[i2];
                int i4 = i2 + 1;
                ScheduleCareGiverActivity.first_mnth = i4;
                if (i4 >= 10) {
                    String.valueOf(i4);
                } else {
                    String.valueOf(i4);
                }
                if (i3 >= 10) {
                    str = String.valueOf(i3);
                } else {
                    str = "0" + String.valueOf(i3);
                }
                String str3 = "" + i + "-" + str2 + "-" + str;
                ScheduleCareGiverActivity.this.mindatcal = Calendar.getInstance();
                ScheduleCareGiverActivity.this.mindatcal.set(i, i2, i3);
                instance.getTime();
                ScheduleCareGiverActivity.this.timeInMill = new Date(i, i2, i3).getTime();
                ScheduleCareGiverActivity.this.txt_startdate.setText(str3);
                ScheduleCareGiverActivity.this.txt_enddate.setText("End Date");
                ScheduleCareGiverActivity.start_date = i4 + "/" + i3 + "/" + i;
                ScheduleCareGiverActivity.end_date = "";
                ScheduleCareGiverActivity.count_one = i3;
            }
        }, instance.get(1), instance.get(2), instance.get(5));
        datePickerDialog.getDatePicker().setMinDate(instance.getTimeInMillis());
        datePickerDialog.show();
    }

    public void showCalView1() {
        Calendar instance = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                String str;
                String str2 = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}[i2];
                int i4 = i2 + 1;
                ScheduleCareGiverActivity.last_mnth = i4;
                if (i4 >= 10) {
                    String.valueOf(i4);
                } else {
                    String.valueOf(i4);
                }
                if (i3 >= 10) {
                    str = String.valueOf(i3);
                } else {
                    str = "0" + String.valueOf(i3);
                }
                Calendar.getInstance().getTime();
                ScheduleCareGiverActivity.this.txt_enddate.setText("" + i + "-" + str2 + "-" + str);
                ScheduleCareGiverActivity.end_date = i4 + "/" + i3 + "/" + i;
                ScheduleCareGiverActivity.count_two = i3;
                StringBuilder sb = new StringBuilder();
                sb.append("------------------> ");
                sb.append(ScheduleCareGiverActivity.end_date);
                Log.e("End Date", sb.toString());
            }
        }, instance.get(1), instance.get(2), instance.get(5));
        datePickerDialog.getDatePicker().setMinDate(this.mindatcal.getTimeInMillis());
        datePickerDialog.show();
    }

    public void showTimePicker(final TextView textView) {
        Calendar instance = Calendar.getInstance();
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            /* JADX WARNING: Code restructure failed: missing block: B:3:0x0009, code lost:
                if (r8 == 0) goto L_0x0010;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:5:0x000e, code lost:
                if (r8 == 0) goto L_0x0010;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onTimeSet(android.widget.TimePicker r7, int r8, int r9) {
                /*
                    r6 = this;
                    r7 = 12
                    java.lang.String r0 = "PM"
                    java.lang.String r1 = "AM"
                    if (r8 >= r7) goto L_0x000c
                    r0 = r1
                    if (r8 != 0) goto L_0x0012
                    goto L_0x0010
                L_0x000c:
                    int r8 = r8 + -12
                    if (r8 != 0) goto L_0x0012
                L_0x0010:
                    r8 = 12
                L_0x0012:
                    r7 = 10
                    java.lang.String r1 = ":0"
                    java.lang.String r2 = ":"
                    java.lang.String r3 = " "
                    if (r8 >= r7) goto L_0x00ac
                    android.widget.TextView r7 = r9
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder
                    r4.<init>()
                    java.lang.String r5 = "0"
                    r4.append(r5)
                    r4.append(r8)
                    r4.append(r2)
                    r4.append(r9)
                    r4.append(r3)
                    r4.append(r0)
                    java.lang.String r4 = r4.toString()
                    r7.setText(r4)
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    r7.append(r5)
                    r7.append(r8)
                    r7.append(r2)
                    r7.append(r9)
                    r7.append(r3)
                    r7.append(r0)
                    java.lang.String r7 = r7.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.start_time = r7
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.shour = r8
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.sminute = r9
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity r7 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.this
                    r7.sAM_PM = r0
                    if (r9 != 0) goto L_0x012d
                    android.widget.TextView r7 = r9
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    r2.<init>()
                    r2.append(r5)
                    r2.append(r8)
                    r2.append(r1)
                    r2.append(r9)
                    r2.append(r3)
                    r2.append(r0)
                    java.lang.String r2 = r2.toString()
                    r7.setText(r2)
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    r7.append(r5)
                    r7.append(r8)
                    r7.append(r1)
                    r7.append(r9)
                    r7.append(r3)
                    r7.append(r0)
                    java.lang.String r7 = r7.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.start_time = r7
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.shour = r8
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.sminute = r9
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity r7 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.this
                    r7.sAM_PM = r0
                    goto L_0x012d
                L_0x00ac:
                    if (r9 != 0) goto L_0x00ee
                    android.widget.TextView r7 = r9
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    r2.<init>()
                    r2.append(r8)
                    r2.append(r1)
                    r2.append(r9)
                    r2.append(r3)
                    r2.append(r0)
                    java.lang.String r2 = r2.toString()
                    r7.setText(r2)
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    r7.append(r8)
                    r7.append(r1)
                    r7.append(r9)
                    r7.append(r3)
                    r7.append(r0)
                    java.lang.String r7 = r7.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.start_time = r7
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.shour = r8
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.sminute = r9
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity r7 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.this
                    r7.sAM_PM = r0
                    goto L_0x012d
                L_0x00ee:
                    android.widget.TextView r7 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r8)
                    r1.append(r2)
                    r1.append(r9)
                    r1.append(r3)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r7.setText(r1)
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    r7.append(r8)
                    r7.append(r2)
                    r7.append(r9)
                    r7.append(r3)
                    r7.append(r0)
                    java.lang.String r7 = r7.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.start_time = r7
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.shour = r8
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.sminute = r9
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity r7 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.this
                    r7.sAM_PM = r0
                L_0x012d:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.C12973.onTimeSet(android.widget.TimePicker, int, int):void");
            }
        }, instance.get(11), instance.get(12), false).show();
    }

    public void showTimePicker1(final TextView textView) {
        Calendar instance = Calendar.getInstance();
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            /* JADX WARNING: Code restructure failed: missing block: B:3:0x0009, code lost:
                if (r10 == 0) goto L_0x0010;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:5:0x000e, code lost:
                if (r10 == 0) goto L_0x0010;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onTimeSet(android.widget.TimePicker r9, int r10, int r11) {
                /*
                    r8 = this;
                    r9 = 12
                    java.lang.String r0 = "PM"
                    java.lang.String r1 = "AM"
                    if (r10 >= r9) goto L_0x000c
                    r0 = r1
                    if (r10 != 0) goto L_0x0012
                    goto L_0x0010
                L_0x000c:
                    int r10 = r10 + -12
                    if (r10 != 0) goto L_0x0012
                L_0x0010:
                    r10 = 12
                L_0x0012:
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    int r1 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.shour
                    r9.append(r1)
                    java.lang.String r1 = " / "
                    r9.append(r1)
                    r9.append(r10)
                    java.lang.String r9 = r9.toString()
                    java.lang.String r2 = "----Hour Data---- "
                    android.util.Log.e(r2, r9)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    int r2 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.sminute
                    r9.append(r2)
                    r9.append(r1)
                    r9.append(r11)
                    java.lang.String r9 = r9.toString()
                    java.lang.String r1 = "----Mint Data---- "
                    android.util.Log.e(r1, r9)
                    int r9 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.shour
                    r1 = 1
                    java.lang.String r2 = "Please select the time after start time "
                    r3 = 10
                    java.lang.String r4 = ":0"
                    java.lang.String r5 = ":"
                    java.lang.String r6 = "0"
                    java.lang.String r7 = " "
                    if (r9 <= r10) goto L_0x0160
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity r9 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.this
                    java.lang.String r9 = r9.sAM_PM
                    boolean r9 = r0.equals(r9)
                    if (r9 == 0) goto L_0x006c
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity r9 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.this
                    android.widget.Toast r9 = android.widget.Toast.makeText(r9, r2, r1)
                    r9.show()
                    goto L_0x0366
                L_0x006c:
                    if (r10 >= r3) goto L_0x00ec
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r6)
                    r1.append(r10)
                    r1.append(r5)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r6)
                    r9.append(r10)
                    r9.append(r5)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    if (r11 != 0) goto L_0x0366
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r6)
                    r1.append(r10)
                    r1.append(r4)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r6)
                    r9.append(r10)
                    r9.append(r4)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    goto L_0x0366
                L_0x00ec:
                    if (r11 != 0) goto L_0x0127
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r10)
                    r1.append(r4)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r10)
                    r9.append(r4)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    goto L_0x0366
                L_0x0127:
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r10)
                    r1.append(r5)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r10)
                    r9.append(r5)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    goto L_0x0366
                L_0x0160:
                    int r9 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.shour
                    if (r9 != r10) goto L_0x0276
                    int r9 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.sminute
                    if (r9 <= r11) goto L_0x0173
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity r9 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.this
                    android.widget.Toast r9 = android.widget.Toast.makeText(r9, r2, r1)
                    r9.show()
                    goto L_0x0366
                L_0x0173:
                    int r9 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.sminute
                    if (r9 != r11) goto L_0x0182
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity r9 = com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.this
                    android.widget.Toast r9 = android.widget.Toast.makeText(r9, r2, r1)
                    r9.show()
                    goto L_0x0366
                L_0x0182:
                    if (r10 >= r3) goto L_0x0202
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r6)
                    r1.append(r10)
                    r1.append(r5)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r6)
                    r9.append(r10)
                    r9.append(r5)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    if (r11 != 0) goto L_0x0366
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r6)
                    r1.append(r10)
                    r1.append(r4)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r6)
                    r9.append(r10)
                    r9.append(r4)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    goto L_0x0366
                L_0x0202:
                    if (r11 != 0) goto L_0x023d
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r10)
                    r1.append(r4)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r10)
                    r9.append(r4)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    goto L_0x0366
                L_0x023d:
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r10)
                    r1.append(r5)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r10)
                    r9.append(r5)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    goto L_0x0366
                L_0x0276:
                    if (r10 >= r3) goto L_0x02f5
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r6)
                    r1.append(r10)
                    r1.append(r5)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r6)
                    r9.append(r10)
                    r9.append(r5)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    if (r11 != 0) goto L_0x0366
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r6)
                    r1.append(r10)
                    r1.append(r4)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r6)
                    r9.append(r10)
                    r9.append(r4)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    goto L_0x0366
                L_0x02f5:
                    if (r11 != 0) goto L_0x032f
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r10)
                    r1.append(r4)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r10)
                    r9.append(r4)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                    goto L_0x0366
                L_0x032f:
                    android.widget.TextView r9 = r9
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    r1.append(r10)
                    r1.append(r5)
                    r1.append(r11)
                    r1.append(r7)
                    r1.append(r0)
                    java.lang.String r1 = r1.toString()
                    r9.setText(r1)
                    java.lang.StringBuilder r9 = new java.lang.StringBuilder
                    r9.<init>()
                    r9.append(r10)
                    r9.append(r5)
                    r9.append(r11)
                    r9.append(r7)
                    r9.append(r0)
                    java.lang.String r9 = r9.toString()
                    com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.end_time = r9
                L_0x0366:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.techno.docareupdatefinal.Activity.ClientService.ScheduleCareGiverActivity.C12984.onTimeSet(android.widget.TimePicker, int, int):void");
            }
        }, instance.get(11), instance.get(12), false).show();
    }

    private class GetStateTask extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f144pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetStateTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_state?country_id=" + ScheduleCareGiverActivity.country_id;
            this.iserror = false;
            this.isValue = false;
            this.f144pd = ProgressDialog.show(ScheduleCareGiverActivity.this, "", "Please wait...", true);
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
                    this.f144pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f144pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f144pd.dismiss();
            try {
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    String[] unused = ScheduleCareGiverActivity.this.state_list = new String[jSONArray.length()];
                    String[] unused2 = ScheduleCareGiverActivity.this.state_idlist = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        String string = jSONArray.getJSONObject(i).getString("name");
                        String string2 = jSONArray.getJSONObject(i).getString("id");
                        ScheduleCareGiverActivity.this.state_list[i] = string;
                        ScheduleCareGiverActivity.this.state_idlist[i] = string2;
                    }
                    ScheduleCareGiverActivity.this.loadState();
                    return;
                }
                ScheduleCareGiverActivity scheduleCareGiverActivity = ScheduleCareGiverActivity.this;
                Toast.makeText(scheduleCareGiverActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
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
        final ProgressDialog f143pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetCityTask() {
            this.url = "http://showupcare.com/WECARE/webservice/get_city?country_id=" + ScheduleCareGiverActivity.country_id + "&state_id=" + ScheduleCareGiverActivity.state_id;
            this.iserror = false;
            this.isValue = false;
            this.f143pd = ProgressDialog.show(ScheduleCareGiverActivity.this, "", "Please wait...", true);
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
                    this.f143pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f143pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f143pd.dismiss();
            try {
                if (this.obj == null) {
                    return;
                }
                if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    String[] unused = ScheduleCareGiverActivity.this.city_list = new String[jSONArray.length()];
                    String[] unused2 = ScheduleCareGiverActivity.this.city_idlist = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        String string = jSONArray.getJSONObject(i).getString("name");
                        String string2 = jSONArray.getJSONObject(i).getString("id");
                        ScheduleCareGiverActivity.this.city_list[i] = string;
                        ScheduleCareGiverActivity.this.city_idlist[i] = string2;
                    }
                    ScheduleCareGiverActivity.this.loadcity();
                    return;
                }
                ScheduleCareGiverActivity scheduleCareGiverActivity = ScheduleCareGiverActivity.this;
                Toast.makeText(scheduleCareGiverActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
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

    public void updateRequest() {
        final ProgressDialog show = ProgressDialog.show(this, "", "Please wait...", true);
        try {
            this.category.put("user_id", this.user_id);
            this.category.put("giver_id", this.giver_id);
            this.category.put(FirebaseAnalytics.Param.START_DATE, start_date);
            this.category.put("start_time", start_time);
            this.category.put(FirebaseAnalytics.Param.END_DATE, end_date);
            this.category.put("end_time", end_time);
            this.category.put("work_type", time_type);
            this.category.put("service_type", type);
            this.category.put("gender", gender);
            this.category.put("address", address_one);
            this.category.put("address1", address_two);
            this.category.put("day_count", days);
            this.category.put("state", state);
            this.category.put("city", city);
            this.category.put("zip", zip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("======== category params ========== ");
        System.out.println(this.category);
        try {
            this.client.post("http://showupcare.com/WECARE/webservice/request_nearbuy_giver?", this.category, new JsonHttpResponseHandler() {
                public void onFailure(int i, Header[] headerArr, String str, Throwable th) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------onFailure-------------" + str + " Status code ::: " + i);
                    Toast.makeText(ScheduleCareGiverActivity.this, "Something went wrong, Try Again !", Toast.LENGTH_LONG).show();
                }

                public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
                    show.dismiss();
                    PrintStream printStream = System.out;
                    printStream.println("-----------response----------- " + jSONObject);
                    try {
                        if (jSONObject.getString("status").equals("1")) {
                            Toast.makeText(ScheduleCareGiverActivity.this, "Success", Toast.LENGTH_LONG).show();
                            ScheduleCareGiverActivity.this.finish();
                            return;
                        }
                        ScheduleCareGiverActivity scheduleCareGiverActivity = ScheduleCareGiverActivity.this;
                        Toast.makeText(scheduleCareGiverActivity, "" + jSONObject.getString("result"), Toast.LENGTH_LONG).show();
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
