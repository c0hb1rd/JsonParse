package com.hongbao.c0hb1rd.testsch;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText userEdit, passEdit;
    private String JSON_DATA;
    private Person user;
    private ListView contentView;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0x123) {
                //Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
                showDataToUI();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String USER_NAME = userEdit.getText().toString();
                final String USER_PASS = passEdit.getText().toString();

                if (!USER_NAME.equals("") && !USER_NAME.equals(" ")) {
                    if (!USER_PASS.equals("") && !USER_PASS.equals(" ") && USER_PASS.equals("123456")) {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    JSON_DATA = getJsonData(USER_NAME);
                                    jsonDataParse(JSON_DATA);
                                    handler.sendEmptyMessage(0x123);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    } else {
                        Toast.makeText(MainActivity.this, "Require True Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Require Not Empty Username", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initialization() {
        loginButton = (Button) findViewById(R.id.login_button);
        userEdit = (EditText) findViewById(R.id.edit_username);
        passEdit = (EditText) findViewById(R.id.edit_password);
        contentView = (ListView) findViewById(R.id.list_content);
    }

    private void jsonDataParse(String json_data) throws JSONException {

        user = new Person();

        JSONArray jsonArray = new JSONArray(json_data);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        user.setIdCard(jsonObject.optString("k_shenfenzheng"));
        user.setTech(jsonObject.getString("k_suoxuezhuanye"));
        user.setSchool(jsonObject.getString("k_biye"));
        user.setName(jsonObject.getString("k_xingming"));
        user.setSex(jsonObject.getString("k_xingbie"));

    }

    private void showDataToUI() {
        String[] data = {"key", "value"};
        List<Map<String, String>> content = new ArrayList<>();

        for (String key: user.key) {
            Map<String, String> map = new HashMap<>();
            map.put("key", key);
            map.put("value", user.getValue(key));
            content.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, content, R.layout.content_layout, data, new int[] {R.id.key, R.id.value});
        contentView.setAdapter(adapter);

    }

    private String getJsonData(String userName) throws IOException {
        StringBuilder data = new StringBuilder();

        URL url = new URL("http://121.42.32.213/testClass.php?id=" + userName);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader reader = new BufferedReader(in);

        String inputLine;
        while((inputLine = reader.readLine()) != null) {
            data.append(inputLine);
            data.append("\n");
        }

        return data.toString();
    }


}
