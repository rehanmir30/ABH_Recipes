package com.example.abhrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class Open_web_Links extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_web_links);

        WebView browser = findViewById(R.id.webView);
        Intent i = getIntent();
        String browse_url = i.getStringExtra("url");
        browser.loadUrl(browse_url);
        finish();
    }
}