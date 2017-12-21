package com.example.simon.zadanieap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    EditText VekEt;
    Spinner OkresSpinner;

    String Vek;
    String Pohlavie;
    String Okres;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        OkresSpinner = (Spinner) findViewById(R.id.OkresySpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Okresy , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        OkresSpinner.setAdapter(adapter);


    }

    public void RadioBtnClicked (View view){

        switch (view.getId()){
            case R.id.MužRadio :{
                Pohlavie = "Muz";
            }break;
            case R.id.ŽenaRadio :{
                Pohlavie = "Zena";
            }break;
        }

    }



    public void DalejClicked (View view){

        Okres = OkresSpinner.getSelectedItem().toString();
        VekEt = (EditText) findViewById(R.id.VekEt);
        Vek = VekEt.getText().toString();


        if (Okres == null || Vek == null || Pohlavie == null){
            Toast.makeText(this,"Prosím zadajte údaje",Toast.LENGTH_LONG).show();
        }else {
            Intent i = new Intent(this, DalsiaObrazovka.class);
            i.putExtra("Pohlavie",Pohlavie);
            i.putExtra("Vek",Vek);
            i.putExtra("Okres",Okres);
            startActivity(i);
            finish();
        }

    }
}
