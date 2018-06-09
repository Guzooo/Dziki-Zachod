package com.Guzooo.DzikiZachod2017;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class SpolecznoscFragment extends Fragment {


    public SpolecznoscFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_spolecznosc, container, false);

        WebView webView = layout.findViewById(R.id.instagram);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.pl");

        return layout;
    }
}
