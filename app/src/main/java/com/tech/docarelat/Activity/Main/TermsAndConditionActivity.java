package com.tech.docarelat.Activity.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import com.google.android.gms.plus.PlusShare;
import com.tech.docarelat.R;

public class TermsAndConditionActivity extends AppCompatActivity {
    private ImageButton imgLeftMenu;
    private String url;
    private WebView webView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_terms_and_condition);
        this.url = getIntent().getExtras().getString(PlusShare.KEY_CALL_TO_ACTION_URL);
        WebView webView2 = (WebView) findViewById(R.id.webView);
        this.webView = webView2;
        webView2.getSettings().setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.loadUrl(this.url);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imgLeftMenu);
        this.imgLeftMenu = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TermsAndConditionActivity.this.finish();
            }
        });
    }
}
