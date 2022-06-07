package com.tech.docarelat.Activity.CareGiver;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.tech.docarelat.Adapter.CareGiverExistingRequistListAdapter;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ExistingDelegationsActivity extends AppCompatActivity implements View.OnClickListener {
    public static RecyclerView RvExistinggiverlist;
    private JSONArray arr;
    private ImageButton imgLeftMenu;
    /* access modifiers changed from: private */
    public String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_existing_delegations);
        findId();
        this.user_id = MySharedPref.getData(this, "user_id", "");
        this.imgLeftMenu.setOnClickListener(this);
        new GetMyRequests().execute(new Void[0]);
    }

    private void findId() {
        this.imgLeftMenu = (ImageButton) findViewById(R.id.imgLeftMenu);
        RvExistinggiverlist = (RecyclerView) findViewById(R.id.Rvgiverlist);
    }

    public void onClick(View view) {
        if (view == this.imgLeftMenu) {
            finish();
        }
    }

    private class GetMyRequests extends AsyncTask<Void, Void, Void> {
        boolean isValue;
        boolean iserror;
        JSONObject obj;

        /* renamed from: pd */
        final ProgressDialog f132pd;
        String url;

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private GetMyRequests() {
            this.url = "http://showupcare.com/WECARE/webservice/get_accepted_request?giver_id=" + ExistingDelegationsActivity.this.user_id;
            this.iserror = false;
            this.isValue = false;
            this.f132pd = ProgressDialog.show(ExistingDelegationsActivity.this, "", "Please wait...", true);
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
                    this.f132pd.dismiss();
                    Log.e("Fail 3", e.toString());
                    e.printStackTrace();
                    this.iserror = true;
                    return null;
                }
            } catch (Exception e2) {
                this.f132pd.dismiss();
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            this.f132pd.dismiss();
            try {
                if (this.obj == null) {
                    Toast.makeText(ExistingDelegationsActivity.this, "Server is Busy", Toast.LENGTH_LONG).show();
                } else if (this.obj.getString("status").equals("1")) {
                    JSONArray jSONArray = this.obj.getJSONArray("result");
                    ExistingDelegationsActivity.RvExistinggiverlist.setHasFixedSize(true);
                    ExistingDelegationsActivity.RvExistinggiverlist.setLayoutManager(new LinearLayoutManager(ExistingDelegationsActivity.this));
                    ExistingDelegationsActivity.RvExistinggiverlist.setItemAnimator(new DefaultItemAnimator());
                    ExistingDelegationsActivity.RvExistinggiverlist.setAdapter(new CareGiverExistingRequistListAdapter(ExistingDelegationsActivity.this, jSONArray));
                } else {
                    ExistingDelegationsActivity existingDelegationsActivity = ExistingDelegationsActivity.this;
                    Toast.makeText(existingDelegationsActivity, "" + this.obj.getString("result"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static String resizeAndCompressImageBeforeSend(Context context, String str, String str2) {
        int length;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, 800, 800);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        int i = 100;
        do {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + i);
            decodeFile.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
            length = byteArrayOutputStream.toByteArray().length;
            i += -5;
            Log.d("compressBitmap", "Size: " + (length / 1024) + " kb");
        } while (length >= 716800);
        try {
            Log.d("compressBitmap", "cacheDir: " + context.getCacheDir());
            FileOutputStream fileOutputStream = new FileOutputStream(context.getCacheDir() + str2);
            decodeFile.compress(Bitmap.CompressFormat.JPEG, i, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception unused) {
            Log.e("compressBitmap", "Error on saving file");
        }
        return context.getCacheDir() + str2;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        Log.d("MemoryInformation", "image height: " + i3 + "---image width: " + i4);
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2;
            }
        }
        Log.d("MemoryInformation", "inSampleSize: " + i5);
        return i5;
    }
}
