package com.Guzooo.DzikiZachod2017;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GwiazdaFragment extends Fragment implements View.OnClickListener {

    private boolean kod1;
    private boolean kod2;
    private boolean kod3;

    private final String txtkod1 = "001";
    private final String txtkod2 = "002";
    private final String txtkod3 = "003";

    private View layout;

    public GwiazdaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_gwiazda, container, false);

        Button btn = layout.findViewById(R.id.gwia3);
        btn.setOnClickListener(this);

        kod1=false;
        kod2=false;
        kod3=false;

        return layout;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gwia3:
                EditText ed = layout.findViewById(R.id.gwia2);
                if (ed.getText().toString().trim().equals(txtkod1)){
                    Kod(kod1);
                    kod1=true;
                } else if (ed.getText().toString().trim().equals(txtkod2)){
                    Kod(kod2);
                    kod2=true;
                } else if (ed.getText().toString().trim().equals(txtkod3)){
                    Kod(kod3);
                    kod3=true;
                } else if (ed.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(), "Wpisz kod!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Kod jest błędny", Toast.LENGTH_SHORT).show();
                }

        }
        TextView textView = layout.findViewById(R.id.gwia4);

        if(kod1 && kod2 && kod3){
            Toast.makeText(getActivity(), "Masz już wszystkie kody", Toast.LENGTH_SHORT).show();
            textView.setText("Masz już wszystkie kody");
        } else if ((kod1 && kod2) || (kod2 && kod3) || (kod3 && kod1)){
            textView.setText("Brakuje jeszcze jednego kodu");
        } else if (kod1 || kod2 || kod3){
            textView.setText("Musisz znaleźć 2 kody");
        }
    }

    private void Kod(boolean kod){
        if(kod){
            Toast.makeText(getActivity(), "Ten kod został już użyty", Toast.LENGTH_SHORT).show();
        } else {
            kod = true;
            Toast.makeText(getActivity(), "Dodałeś kolejny kod", Toast.LENGTH_SHORT).show();
        }
    }
}
