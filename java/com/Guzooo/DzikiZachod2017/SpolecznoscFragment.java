package com.Guzooo.DzikiZachod2017;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.time.LocalDate;

import static android.content.Intent.ACTION_VIEW;


public class SpolecznoscFragment extends Fragment {

    public static WebView webView;
    private static final String homePage = "https://www.instagram.com/explore/tags/DzikiZach%C3%B3d2018/";

    private static boolean clearHistory;

    public SpolecznoscFragment() {
        // Required empty public constructor
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View layout = inflater.inflate(R.layout.fragment_spolecznosc, container, false);

       final View imageView = layout.findViewById(R.id.spolecznosc_image);
       final TextView textView = layout.findViewById(R.id.spolecznosc_text);
       webView = layout.findViewById(R.id.instagram);

       webView.setWebViewClient(new WebViewClient() {

           Handler handler = new Handler();

           @Override
           public void onPageStarted(WebView view, String url, Bitmap favicon) {
               super.onPageStarted(view, url, favicon);

               imageView.setVisibility(View.VISIBLE);

               Animation puls = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.puls);
               imageView.startAnimation(puls);

               handler.post(new Runnable() {
                   @Override
                   public void run() {
                       textView.setText(Integer.toString(webView.getProgress()) + "%");
                       handler.postDelayed(this, 100);
                   }
               });
           }

           @Override
           public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);

               imageView.clearAnimation();

               imageView.setVisibility(View.INVISIBLE);

               if(clearHistory){
                   webView.clearHistory();
                   clearHistory = false;
               }
           }
       });

       webView.getSettings().setJavaScriptEnabled(true);

       webView.setOnKeyListener(new View.OnKeyListener() {
           @Override
           public boolean onKey(View v, int keyCode, KeyEvent event) {
               if (event.getAction() == KeyEvent.ACTION_DOWN) {
                   WebView webView = (WebView) v;

                   switch (keyCode) {
                       case KeyEvent.KEYCODE_BACK:
                           if (webView.canGoBack()) {
                               webView.goBack();
                               return true;
                           }
                           break;
                   }
               }
               return false;
           }
       });

       if (savedInstanceState != null) {
           webView.restoreState(savedInstanceState);
       } else {
           webView.loadUrl(homePage);
       }
       return layout;
   }

   public static void ClickInstagramHasztag(){
        clearHistory = true;
        webView.loadUrl(homePage);
   }

    public static void ClickSpolecznoscioweMedia(String url){
        webView.loadUrl(url);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        webView.saveState(outState);
    }
}
