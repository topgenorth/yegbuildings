package net.opgenorth.yeg.buildings.views;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import net.opgenorth.yeg.buildings.R;

public class BuildingInfoViewer extends Activity {
    private WebView _webView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildinginfoviewer);
        _webView = (WebView) findViewById(R.id.buildinginfo_webview);

//        WebSettings webSettings = _webView.getSettings();
//        webSettings.setSavePassword(false);
//        webSettings.setSaveFormData(false);
//        webSettings.setJavaScriptEnabled(false);
//        webSettings.setSupportZoom(true);

        _webView.loadUrl("file:///android_asset/AMacDonald2Building.html");
    }
}