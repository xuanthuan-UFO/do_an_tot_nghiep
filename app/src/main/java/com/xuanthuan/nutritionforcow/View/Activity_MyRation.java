package com.xuanthuan.nutritionforcow.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.xuanthuan.nutritionforcow.Controler.Adapter_MyRation;
import com.xuanthuan.nutritionforcow.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_MyRation extends AppCompatActivity {

    ListView listView;
    Adapter_MyRation adapter;
    ArrayList<String> list;
    Toolbar toolbar;
    JSONArray array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__my_ration);

        init();

        SharedPreferences sharedPreferences = getSharedPreferences("RECENT", MODE_PRIVATE);
        if (sharedPreferences.contains("dataRecent")) {
            try {
                array = new JSONArray(sharedPreferences.getString("dataRecent", ""));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objectrecent = array.getJSONObject(i);
                    String nameRation = objectrecent.getString("name");
                    list.add(nameRation);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar_myRation);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        list = new ArrayList<>();
        listView = findViewById(R.id.lv_Myration);
        adapter = new Adapter_MyRation(list, Activity_MyRation.this, R.layout.item_my_ration);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_MyRation.this, Activity_NewRation.class);
                intent.putExtra("dataNameRation", list.get(position));
                startActivity(intent);
            }
        });
    }
}