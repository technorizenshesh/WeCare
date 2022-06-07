package com.tech.docarelat.autoaddress;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import com.android.internal.http.multipart.MultipartEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebOperations {
    private String BaseUrl = "";
    Context context;
    private String filename = "";
    private String filepath = "ShoparStorage";
    private HttpClient httpClient = new DefaultHttpClient();
    private String json = null;
    File myInternalFile;
    private MultipartEntity reqEntity = null;
    private String url = null;

    public WebOperations(Context context2) {
        this.context = context2;
        this.myInternalFile = Environment.getExternalStorageDirectory();
        this.myInternalFile = new File(new ContextWrapper(context2).getDir(this.filepath, 0), this.filename);
    }

    public MultipartEntity getReqEntity() {
        return this.reqEntity;
    }

    public void setReqEntity(MultipartEntity multipartEntity) {
        this.reqEntity = multipartEntity;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String str) {
        this.filename = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String doGet() {
        try {
            String entityUtils = EntityUtils.toString(this.httpClient.execute(new HttpGet(this.BaseUrl + this.url)).getEntity());
            this.json = entityUtils;
            return entityUtils;
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    public String doGet1() {
        try {
            String entityUtils = EntityUtils.toString(this.httpClient.execute(new HttpGet(this.url)).getEntity());
            this.json = entityUtils;
            return entityUtils;
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    public String doGetMap() {
        try {
            String entityUtils = EntityUtils.toString(this.httpClient.execute(new HttpGet(this.url)).getEntity());
            this.json = entityUtils;
            return entityUtils;
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    public String doPost() {
        try {
            HttpPost httpPost = new HttpPost(this.BaseUrl + this.url);
            httpPost.setEntity(this.reqEntity);
            String entityUtils = EntityUtils.toString(this.httpClient.execute(httpPost).getEntity());
            this.json = entityUtils;
            return entityUtils;
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    public String doPostMap() {
        try {
            HttpPost httpPost = new HttpPost(this.url);
            httpPost.setEntity(this.reqEntity);
            String entityUtils = EntityUtils.toString(this.httpClient.execute(httpPost).getEntity());
            this.json = entityUtils;
            return entityUtils;
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    public void saveData(String str) {
        File file = new File(this.context.getFilesDir(), "files");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            FileOutputStream openFileOutput = this.context.openFileOutput(this.filename, 1);
            openFileOutput.write(str.getBytes());
            openFileOutput.close();
            System.out.println("data saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getData(String str) {
        try {
            FileInputStream openFileInput = this.context.openFileInput(str);
            String str2 = "";
            while (true) {
                int read = openFileInput.read();
                if (read != -1) {
                    str2 = str2 + Character.toString((char) read);
                } else {
                    System.out.println("data of " + str + "   " + str2);
                    openFileInput.close();
                    return str2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }
}
