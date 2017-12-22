package com.example.simon.zadanieap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simon.zadanieap.MailHelper.SendMailTask;

import java.util.ArrayList;
import java.util.List;

public class DalsiaObrazovka extends AppCompatActivity {

    String Pohlavie;
    String Vek;
    String Okres;
    String SystotickyTlak;
    String DialstoickyTlak;
    String Tep;
    String Hmotnost;
    String Email;
    String PoznamkyOLiekoch;

    EditText STlakEt;
    EditText DTlakEt;
    EditText TepEt;
    EditText HmotnostEt;
    EditText EmailEt;
    EditText PoznamkyEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dalsia_obrazovka);
        Intent i = this.getIntent();
        Pohlavie = i.getStringExtra("Pohlavie");
        Vek = i.getStringExtra("Vek");
        Okres = i.getStringExtra("Okres");

        STlakEt = (EditText) findViewById(R.id.SystolickyTlak);
        DTlakEt = (EditText) findViewById(R.id.DialstolickyTlak);
        TepEt = (EditText) findViewById(R.id.Tep);
        HmotnostEt = (EditText) findViewById(R.id.Hmotnost);
        EmailEt = (EditText) findViewById(R.id.Email);
        PoznamkyEt = (EditText) findViewById(R.id.PoznamkyOLiekoch);


    }

    public void OdoslatBtnClicked (View view){

        SystotickyTlak = STlakEt.getText().toString();
        DialstoickyTlak = DTlakEt.getText().toString();
        Tep = TepEt.getText().toString();
        Hmotnost = HmotnostEt.getText().toString();
        Email = EmailEt.getText().toString();
        PoznamkyOLiekoch = PoznamkyEt.getText().toString();


        //Cekujem ci aspon jeden udaj je vyplneny
        Boolean AsponJedenUdaj = false;
        String[] Udaje = {SystotickyTlak,DialstoickyTlak,Tep,Email,PoznamkyOLiekoch};
        for (String udaj:Udaje) {
            if(udaj != null){
                AsponJedenUdaj =true;
            }
        }


        // cekujem ci bol aspon jeden udaj vyplneny , ci je vyplnena hmotnost a ci zadal email
        if(AsponJedenUdaj == true) {

            if (Hmotnost != null) {

                if (Email != null) {
                    // odosielanie emailu
                    String EmailBody =  " Pohlavie: "+Pohlavie+"<br />"+
                                        " Vek: "+Vek+"<br />"+
                                        " Okres: "+Okres+ "<br />"+
                                        " Systolicky tlak: "+SystotickyTlak+" mmHg <br />"+
                                        " Dialstolicky tlak: "+DialstoickyTlak+" mmHg <br />"+
                                        " Tep: "+Tep + " bpm <br />"+
                                        " Hmotnost: "+ Hmotnost + " kg <br />"+
                                        " Email: "+ Email + "<br /> "+
                                        " Poznámky o liekoch: "+PoznamkyOLiekoch;



                    List toEmailList = new ArrayList();
                    toEmailList.add(Email);
                    //TODO ked to prvy krat posles tak ti na mail pride upozonenie ze nejaka apka chcela pristup a musis to povolit....
                     new SendMailTask(DalsiaObrazovka.this).execute("s","7"
                                                                            ,toEmailList,"ZadanieAP",  EmailBody);


                } else {
                    Toast.makeText(this, "Na vykonanie tejto operácie zadajte email", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Hmotnost je povinný údaj", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Musíte vyplniť aspoň jeden údaj okrem hmotnosti",Toast.LENGTH_LONG).show();
        }
    }

    public void MeratAktivituBtnClicked(View view){
        //TODO dorob tu ten graf
        Toast.makeText(this,"Merat aktivitu dorob",Toast.LENGTH_LONG).show();

        Intent i  =  new Intent(this,MeranieActivity.class);
        startActivity(i);


    }
}
