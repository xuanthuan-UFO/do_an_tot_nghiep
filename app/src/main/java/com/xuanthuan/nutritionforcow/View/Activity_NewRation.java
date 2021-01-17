package com.xuanthuan.nutritionforcow.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuanthuan.nutritionforcow.Model.Object_Food;
import com.xuanthuan.nutritionforcow.Model.Object_GetFood;
import com.xuanthuan.nutritionforcow.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Activity_NewRation extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    TextView name_toolbar_newRation;
    public Fragment_ChooseFood fragment_chooseFood;
    public Fragment_InforRation fragment_inforRation;
    public Fragment_Result fragment_result;
    public Toolbar toolbar;

    String nameMyRation;
    public int type = -1 , kg_present, wish_kg;
    ArrayList<Object_GetFood> list_getFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ration);

        Log.d("zzz", "onCreate:  check" + type);

        init();
        name_toolbar_newRation = findViewById(R.id.txtname);

        Intent intent = getIntent();
        try {
            if (intent != null) {
                list_getFood = new ArrayList<>();
                nameMyRation = intent.getStringExtra("dataNameRation");
                JSONArray array;
                SharedPreferences sharedPreferences = getSharedPreferences("RECENT", MODE_PRIVATE);
                if (sharedPreferences.contains("dataRecent")) {
                    try {
                        array = new JSONArray(sharedPreferences.getString("dataRecent", ""));
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objectrecent = array.getJSONObject(i);
                            String nameRation = objectrecent.getString("name");
                            if (nameMyRation.equals(nameRation)) {
                                type = objectrecent.getInt("type");
                                kg_present = objectrecent.getInt("kg_present");
                                wish_kg = objectrecent.getInt("wish_kg");

                                try {
                                    JSONArray arrayFood = objectrecent.getJSONArray("data");
                                    for (int j = 0; i < arrayFood.length(); j++) {
                                        JSONObject objectFood = arrayFood.getJSONObject(j);
                                        String nameFood = objectFood.getString("nameFood");
                                        int indexFood = objectFood.getInt("indexFood");
                                        int kgFood = objectFood.getInt("kgFood");
                                        list_getFood.add(new Object_GetFood(indexFood, nameFood, kgFood));
                                    }
                                } catch (Exception e) {
                                    Log.d("orrrrrr", "onCreate: " + e.getMessage());
                                }

                                Log.d("zzz", "onCreate: " + nameMyRation + " " +  type + " " + kg_present + " " +wish_kg + " " + list_getFood.get(0).getName());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("zzz", "onCreate: arrrrrr" + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            Log.d("zzz", "onCreate: err" + e.getMessage());
        }


        setAdapterBottom();
        setbottomViewPager();
        viewPager.setCurrentItem(0);
        name_toolbar_newRation.setText("Thông tin");
        viewPager.setOffscreenPageLimit(3);


    }

    private void init() {
        fragment_inforRation = new Fragment_InforRation();
        fragment_chooseFood = new Fragment_ChooseFood();
        fragment_result = new Fragment_Result();
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_bar);
        toolbar = findViewById(R.id.toolbar_new);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setAdapterBottom() {
        ViewPagerAdapterBottomNavi adapterBottomNavi = new ViewPagerAdapterBottomNavi(getSupportFragmentManager(), 0);
        adapterBottomNavi.addFragment(fragment_inforRation);
        adapterBottomNavi.addFragment(fragment_chooseFood);
        adapterBottomNavi.addFragment(fragment_result);
        viewPager.setAdapter(adapterBottomNavi);
    }

    private void setbottomViewPager() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_infor_ration:
                        viewPager.setCurrentItem(0);
                        name_toolbar_newRation.setText("Thông tin");
                        break;
                    case R.id.menu_food_ration:
                        viewPager.setCurrentItem(1);
                        name_toolbar_newRation.setText("Chọn thức ăn");
                        break;
                    case R.id.menu_evaluate_ration:
                        viewPager.setCurrentItem(2);
                        name_toolbar_newRation.setText("Phân tích");
                        break;
                }

                return true;
            }
        });
        viewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                bottomNavigationView.setSelectedItemId(position);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_infor_ration).setChecked(true);
                        name_toolbar_newRation.setText("Thông tin");
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_food_ration).setChecked(true);
                        name_toolbar_newRation.setText("Chọn thức ăn");
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_evaluate_ration).setChecked(true);
                        name_toolbar_newRation.setText("Phân tích");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}