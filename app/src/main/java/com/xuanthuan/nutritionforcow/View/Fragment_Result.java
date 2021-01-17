package com.xuanthuan.nutritionforcow.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanthuan.nutritionforcow.R;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Result extends Fragment {
    View view;
    String name, wishKg, kgPresent;

    double indexDM = 0;
    double indexME = 0;
    double indexCP = 0;
    double targetme;
    double targetdm;
    double targetcP;

    double cPcompare = 0;
    double mEcompare = 0;
    double dMcompare = 0;

    List<Double> listCP;
    List<Double> listME;
    List<Double> listDM;

    Button btn_save;
    TextView txt_result;
    ProgressBar progress_DM, progress_CP, progress_ME;
    int maxDm, maxCP, maxME;
    SharedPreferences sharedPreferences;
    String detail_resultDm, detail_resultCP, detail_resultME;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment__result, container, false);

        sharedPreferences = getActivity().getSharedPreferences("RECENT", getActivity().MODE_PRIVATE);

        init();

        return view;
    }

    private void keyTarget() {
        try {
            name = ((Activity_NewRation) getActivity()).fragment_inforRation.edt_name_ration.getText().toString().trim();
            kgPresent = ((Activity_NewRation) getActivity()).fragment_inforRation.txt_kg_present.getText().toString().trim();
            wishKg = ((Activity_NewRation) getActivity()).fragment_inforRation.txtWeightWish.getText().toString().trim();

            Log.e("target", "keyTarget: " + name + kgPresent + wishKg);
            if (!(name.isEmpty()) && !(kgPresent.equals("0") && !(wishKg.isEmpty()))) {
                targetdm = ((Activity_NewRation) getActivity()).fragment_inforRation.list_DigitFood.get(((Activity_NewRation) getActivity()).fragment_inforRation.indexWishKg).getdM();
                targetcP = ((Activity_NewRation) getActivity()).fragment_inforRation.list_DigitFood.get(((Activity_NewRation) getActivity()).fragment_inforRation.indexWishKg).getCP();
                targetme = ((Activity_NewRation) getActivity()).fragment_inforRation.list_DigitFood.get(((Activity_NewRation) getActivity()).fragment_inforRation.indexWishKg).getmE();

                Log.d("target", "keyTarget: " + targetcP + " " + targetdm + " " + targetme + " ");

                indexFood();    // chỉ số trong thức ăn để so sánh
            } else {
                Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("target", "keyTarget: " + e.getMessage());
        }

    }

    private void indexFood() {
        try {
            for (int i = 0; i < ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.size(); i++) {
                indexDM = (((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.get(i).getdM() / 100) * ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.get(i).getKg();
                indexCP = (((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.get(i).getCP() * 10) * indexDM;
                indexME = ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.get(i).getmE() * indexDM;

                listCP.add(indexCP);
                listME.add(indexME);
                listDM.add(indexDM);
                Log.d("zzz", "indexFood: " + indexDM + " " + indexCP + " " + indexME);
            }

            for (int t = 0; t < listCP.size(); t++) {
                cPcompare = cPcompare + listCP.get(t);
            }
            for (int t = 0; t < listME.size(); t++) {
                mEcompare = mEcompare + listME.get(t);
            }
            for (int t = 0; t < listDM.size(); t++) {
                dMcompare = dMcompare + listDM.get(t);
            }

            result();
        } catch (Exception e) {

        }
    }

    private void result (){
        progress_DM.setIndeterminate(false);
        progress_CP.setIndeterminate(false);
        progress_ME.setIndeterminate(false);

        maxDm = (int) (targetdm * 2);
        maxCP = (int) (targetcP * 2);
        maxME = (int) (targetme * 2);

        progress_DM.setMax(maxDm);
        progress_CP.setMax(maxCP);
        progress_ME.setMax(maxME);

        progress_DM.setProgress((int) dMcompare);
        progress_CP.setProgress((int) cPcompare);
        progress_ME.setProgress((int) mEcompare);

        if ((dMcompare < (targetdm - 0.6))) {
            int color = 0xFFF1DC23;
            progress_DM.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            detail_resultDm = "Chỉ số vật chất khô(DM) đang ở mức thấp, bạn cần tăng thêm lượng thức ăn!";
        } else {
            int color = 0xFF00C4DD;
            progress_DM.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            detail_resultDm = "Chỉ số vật chất khô(DM) đã đạt mức cần thiết";
        }

        if (((targetcP - 50) > cPcompare)) {
            int color = 0xFFF1DC23;
            progress_CP.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            detail_resultCP = "Chỉ số Protein(CP) đang ở mức thấp, bạn cần tăng thêm lượng thức ăn";
        } else if ((cPcompare > (targetcP + 50))) {
            int color = 0xFFFF3300;
            progress_CP.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            detail_resultCP = "Chỉ số Protein(CP) đang ở mức cao, bạn nên giảm số lượng thức ăn";
        } else {
            int color = 0xFF00C4DD;
            progress_CP.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            detail_resultCP = "Chỉ số Protein(CP) đã đạt mức cần thiết";
        }

        if (((targetme - 0.7) > mEcompare)) {
            int color = 0xFFF1DC23;
            progress_ME.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            detail_resultME = "Chỉ số Năng lượng(ME) đang ở mức thấp, bạn cần tăng thêm lượng thức ăn";
        } else if ((mEcompare > (targetme + 0.7))) {
            int color = 0xFFFF3300;
            progress_ME.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            detail_resultME = "Chỉ số Năng lượng(ME) đang ở mức cao, bạn nên giảm số lượng thức ăn";

        } else {
            int color = 0xFF00C4DD;
            progress_ME.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            detail_resultME = "Chỉ số Năng lượng(ME) đã đạt mức cần thiết";

        }

        txt_result.setText("Để bò tăng trưởng " + ((Activity_NewRation)getActivity()).fragment_inforRation.txtWeightWish.getText().toString() + " với danh sách thức ăn bạn nhập vào, ta có dữ liệu phân tích: " + "\n" + detail_resultDm + "\n" + detail_resultCP + "\n" + detail_resultME);

        save();

        Log.d("zzz", "result: CP " + cPcompare + "/" + targetcP);
        Log.d("zzz", "result: Me " + mEcompare + "/" + targetme);
        Log.d("zzz", "result: Dm " + dMcompare + "/" + targetdm);
    }

    private void init() {
        btn_save = view.findViewById(R.id.btn_save);
        txt_result = view.findViewById(R.id.txt_result);
        progress_CP = view.findViewById(R.id.progress_CP);
        progress_DM = view.findViewById(R.id.progress_DM);
        progress_ME = view.findViewById(R.id.progress_ME);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        listME = new ArrayList<>();
        listCP = new ArrayList<>();
        listDM = new ArrayList<>();
        listCP.clear();
        listME.clear();
        listDM.clear();
        cPcompare = 0;
        mEcompare = 0;
        dMcompare = 0;
        detail_resultDm = "";
        detail_resultME = "";
        detail_resultCP = "";
        keyTarget();

    }

    private void save(){
        btn_save.setVisibility(View.VISIBLE);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkExist = 0;
                String foood = "";
                JSONObject object = new JSONObject();
                JSONObject object1 = new JSONObject();
                try {
                    for (int i = 0; i < ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.size(); i++) {
                        try {
                            object1.put("indexFood", ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.get(i).getIndex());
                            object1.put("nameFood", ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.get(i).getNameFood());
                            object1.put("kgFood", ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.get(i).getKg());
                          //  array.put(object1);
                            if (i == ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.size() - 1) {
                                foood += object1.toString();
                            } else {
                                foood += object1.toString() + ",";
                            }
                            Log.d("zzz", "onContextItemSelected: food"+ i + " "  + foood);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    JSONArray arrayfood = new JSONArray("[" + foood + "]");
                    Log.d("zzz", "onContextItemSelected: array" + arrayfood);

                    object.put("name", ((Activity_NewRation) getActivity()).fragment_inforRation.edt_name_ration.getText().toString());
                    object.put("type", ((Activity_NewRation) getActivity()).fragment_inforRation.checkSheet);
                    object.put("kg_present", ((Activity_NewRation) getActivity()).fragment_inforRation.kgCow);
                    object.put("wish_kg", ((Activity_NewRation) getActivity()).fragment_inforRation.indexWishKg); // sau này sửa, vẫn phải check kg bồ hiện tại -> lấy ra list target, rồi từ index này lấy ra giá trị trong list.
                    object.put("data", arrayfood);

                    Log.d("zzz", "onClick: object " + object);
                    JSONArray arrayRecent =new JSONArray();

                    if (sharedPreferences.contains("dataRecent")) {
                        String arrayData = sharedPreferences.getString("dataRecent", "");
                        //  Log.d("zzz", "saveRecent: oooo" + arrayData);
                        arrayRecent = new JSONArray(arrayData);
                        for (int i = 0; i < arrayRecent.length(); i++) {
                            JSONObject objectrecent = arrayRecent.getJSONObject(i);
                            String checkName = objectrecent.getString("name");
                            if (((Activity_NewRation) getActivity()).fragment_inforRation.edt_name_ration.getText().toString().trim().equals(checkName)) {
                                Toast.makeText(getActivity(), "Tên khẩu phần đã tồn tại, vui lòng nhập tên khác", Toast.LENGTH_SHORT).show();
                                checkExist =1;
                            }
                        }

                        if (checkExist == 0) {
                            arrayRecent.put(object);
                            sharedPreferences.edit().putString("dataRecent", arrayRecent + "").apply();
                        }

                    } else {
                        arrayRecent.put(object);
                        sharedPreferences.edit().putString("dataRecent", arrayRecent + "").apply();
                    }

//                    try {
//                        String arrayData = sharedPreferences.getString("dataRecent", "");
//                      //  Log.d("zzz", "saveRecent: oooo" + arrayData);
//                        arrayRecent = new JSONArray(arrayData);
//                        for (int i = 0; i < arrayRecent.length(); i++) {
//                            JSONObject objectrecent = arrayRecent.getJSONObject(i);
//                            String checkName = objectrecent.getString("name");
//                            if (((Activity_NewRation) getActivity()).fragment_inforRation.edt_name_ration.getText().toString().trim().equals(checkName)) {
//                                arrayRecent.remove(i);
//                            }
//                        }
//                        Log.d("zzz", "saveRecent: ok");
//                    } catch (Exception e) {
//                        Log.d("zzz", "saveRecent: " + e.getMessage());
//                    }
//
//




//                    if (sharedPreferences.contains(((Activity_NewRation) getActivity()).fragment_inforRation.edt_name_ration.getText().toString().trim())) {
//                        Toast.makeText(getActivity(), "Tên Khẩu phần đã tồn tại, vui lòng đặt tên khác!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        sharedPreferences.edit().putString(((Activity_NewRation) getActivity()).fragment_inforRation.edt_name_ration.getText().toString().trim(), object + "").commit();
//                        Toast.makeText(getActivity(), "Lưu Thành công!", Toast.LENGTH_SHORT).show();
//                    }

                } catch (Exception e) {
                    Log.d("zzz", "Exception: " + e.getMessage());
                }
            }
        });
    }


//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Taget" + name, getActivity().MODE_PRIVATE);
//                sharedPreferences.edit().putString("tagetdm", String.valueOf(tagetdm));
//                sharedPreferences.edit().putString("tag.etca", String.valueOf(tagetca));
//                sharedPreferences.edit().putString("tagetme", String.valueOf(tagetme));
//                sharedPreferences.edit().putString("tagetprotein", String.valueOf(tagetprotein));
//                sharedPreferences.edit().putString("name", String.valueOf(name));


//    @Override
//    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
//
//        super.onCreateContextMenu(menu, v, menuInfo);
//        getActivity().getMenuInflater().inflate(R.menu.menu_save_ration, menu);
//
//    }
//
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_save:
//                JSONArray array = new JSONArray();
//                JSONObject object = new JSONObject();
//                JSONObject object1 = new JSONObject();
//                try {
//                    for (int i = 0; i < ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.size(); i++) {
//                        try {
//                            object1.put("nameFood", ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.get(i).getNameFood());
//                            object1.put("kgFood", ((Activity_NewRation) getActivity()).fragment_chooseFood.list_food.get(i).getKg());
//                            array.put(i, object1);
//                            Log.d("zzz", "onContextItemSelected: " + array);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    break;
//                } catch (Exception e) {
//                }
//        }
//        return super.onContextItemSelected(item);
//    }
}