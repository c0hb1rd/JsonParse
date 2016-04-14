package com.hongbao.c0hb1rd.schoolapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by c0hb1rd on 4/13/16.
 * @author c0hb1rd
 */

public class SchoolApp extends AppCompatActivity implements View.OnClickListener{

    Button btn_submit;
    EditText edit_id, edit_password;
    String data;
    PersonInfo person;
    TextView info_text;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0x123) {
                parseEasyJson(data);
                String information = "[*]Name: " + person.name + "\n" +
                                    "[*]Sex: " + person.sex + "\n" +
                                    "[*]School: " + person.school + "\n" +
                                    "[*]School_Num: " + person.schoolNum + "\n" +
                                    "[*]IDCard: " + person.idCard + "\n" +
                                    "[*]Tech: " + person.tech;
                info_text.setText(information);
            } else if (msg.what == 0x456) {
                Toast.makeText(SchoolApp.this, "Have some error", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialization();
        btn_submit.setOnClickListener(this);
    }

    public void initialization() {
        btn_submit = (Button) findViewById(R.id.btn_submit);
        edit_id = (EditText) findViewById(R.id.edit_school_num);
        edit_password = (EditText) findViewById(R.id.edit_password);
        info_text = (TextView) findViewById(R.id.information_text);
    }

    @Override
    public void onClick(View v) {
        final String SCHOOL_NUM = edit_id.getText().toString();
        final String PASSWORD = edit_password.getText().toString();

        if (!SCHOOL_NUM.equals("")) {
            if (PASSWORD.equals("123456")) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            data = getHtmlContent(SCHOOL_NUM);
                            handler.sendEmptyMessage(0x123);
                        } catch (IOException e) {
                            handler.sendEmptyMessage(0x456);
                        }
                    }
                }.start();
            }
        } else {
            Toast.makeText(SchoolApp.this, "School num do not exist or password error.", Toast.LENGTH_SHORT).show();
        }
    }

    public String getHtmlContent(String school_num) throws IOException {
        String resultData = "";
        URL url = new URL("http://121.42.32.213/testClass.php?id=" + school_num);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

        InputStreamReader in = new InputStreamReader(urlConn.getInputStream());

        BufferedReader buffer = new BufferedReader(in);
        String inputLine;

        while (((inputLine = buffer.readLine()) != null)) {

            resultData += inputLine + "\n";
        }

        in.close();

        urlConn.disconnect();

        return resultData;
    }


    private void parseEasyJson(String json){
        person = new PersonInfo();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                person.setName(jsonObject.getString("k_xingming"));
                person.setSex(jsonObject.getString("k_xingbie"));
                person.setSchool(jsonObject.getString("k_biye"));
                person.setTech(jsonObject.getString("k_suoxuezhuanye"));
                person.setSchoolNum(jsonObject.getString("k_kaoshenghao"));
                person.setIdCard(jsonObject.getString("k_shenfenzheng"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
