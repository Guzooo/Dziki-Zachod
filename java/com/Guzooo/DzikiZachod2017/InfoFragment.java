package com.Guzooo.DzikiZachod2017;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class InfoFragment extends Fragment implements View.OnClickListener {


    public InfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_info, container, false);

        Button facebookG = layout.findViewById(R.id.info_facebook_guzooo);
        Button facebookP = layout.findViewById(R.id.info_facebook_palac);
        Button youtubeG = layout.findViewById(R.id.info_youtube_guzooo);
        Button wwwP = layout.findViewById(R.id.info_www_palac);
        Button kamper = layout.findViewById(R.id.info_camping);
        Button pokoj = layout.findViewById(R.id.info_room);
        Button formularz = layout.findViewById(R.id.info_formularz);
        Button opinia = layout.findViewById(R.id.info_opinia);

        facebookG.setOnClickListener(this);
        facebookP.setOnClickListener(this);
        youtubeG.setOnClickListener(this);
        wwwP.setOnClickListener(this);
        kamper.setOnClickListener(this);
        pokoj.setOnClickListener(this);
        formularz.setOnClickListener(this);
        opinia.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_facebook_guzooo:
                getPage("https://www.facebook.com/GuzoooApps/");
                break;
            case  R.id.info_facebook_palac:
                getPage("https://www.facebook.com/Pa%C5%82ac-w-Kurozw%C4%99kach-Kraina-bizon%C3%B3w-299669121906/");
                break;
            case R.id.info_youtube_guzooo:
                getPage("https://www.youtube.com/channel/UCgvo-tqtieM8hUz6nkL22Lg/about");
                break;
            case R.id.info_www_palac:
                getPage("http://www.kurozweki.com/");
                break;
            case R.id.info_camping:
                getPage("https://www.camperteam.pl/forum/viewtopic.php?p=731283&sid=db3d2f85141dd431af12aa5c09060e56#731283");
                break;
            case R.id.info_room:
                getPage("http://online.kwhotel.pl/#/search/24/pl");
                break;
            case R.id.info_formularz:
                getPage("https://docs.google.com/forms/d/e/1FAIpQLSeep50BYT6xukwSgcZRnKv5FvqLR8CpQWiKswrxflkWQzVrjg/viewform");
                break;
            case R.id.info_opinia:
                getPage("https://play.google.com/store/apps/details?id=com.Guzooo.DzikiZachod2017");
                break;
        }
    }

    private void getPage (String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
