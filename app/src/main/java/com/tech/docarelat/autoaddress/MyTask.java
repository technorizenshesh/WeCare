package com.tech.docarelat.autoaddress;

import android.os.AsyncTask;

public class MyTask extends AsyncTask<String, String, String> {

    /* renamed from: wo */
    WebOperations f168wo = null;

    /* renamed from: x */
    int f169x = 0;

    public MyTask(WebOperations webOperations, int i) {
        this.f169x = i;
        this.f168wo = webOperations;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String str) {
        super.onPostExecute(str);
    }

    /* access modifiers changed from: protected */
    public String doInBackground(String... strArr) {
        int i = this.f169x;
        if (i == 0) {
            return this.f168wo.doPost();
        }
        if (i == 2) {
            return this.f168wo.doPostMap();
        }
        if (i == 3) {
            return this.f168wo.doGetMap();
        }
        return this.f168wo.doGet();
    }
}
