package com.xuanthuan.nutritionforcow.View;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanthuan.nutritionforcow.Model.Object_Food;
import com.xuanthuan.nutritionforcow.R;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Fragment_InforRation extends Fragment {
    Spinner choose_typeCow, choose_Mywish_kg;
    List<String> list_typeCow, list_myWish_Kg;
    public TextView txtloaibo, txt_choose_type, txtWeight, txt_kg_present, txtWeightWish;
    public EditText edt_name_ration;
    public int checkSheet;
    public List<Object_Food> list_DigitFood;
    public int kgCow, indexWishKg;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__infor_ration, container, false);
        init();

        choose_typeCow();
        //  file = new File("/storage/emulated/0/data.xls");
        inputWeight();    // nhap can nang hien tai
        edt_name_ration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (((Activity_NewRation) getActivity()).type != -1) {
            edt_name_ration.setText(((Activity_NewRation) getActivity()).nameMyRation);
        }


        return view;
    }

    private void inputWeight() {
        if (((Activity_NewRation) getActivity()).type != -1) {
            txt_kg_present.setText(((Activity_NewRation) getActivity()).kg_present + "");
            kgCow = Integer.parseInt(txt_kg_present.getText().toString().trim());
            if ((100 <= kgCow) && (kgCow <= 150)) {
                checkWishKg(6, 10);
            } else if ((150 < kgCow) && (kgCow <= 200)) {
                checkWishKg(11, 15);
            } else if ((200 < kgCow) && (kgCow <= 250)) {
                checkWishKg(16, 21);
            } else if ((250 < kgCow) && (kgCow <= 300)) {
                checkWishKg(22, 27);
            } else if ((300 < kgCow) && (kgCow <= 350)) {
                checkWishKg(28, 33);
            } else if ((350 < kgCow) && (kgCow <= 400)) {
                checkWishKg(34, 40);
            } else if ((400 < kgCow) && (kgCow <= 450)) {
                checkWishKg(41, 48);
            } else if ((450 < kgCow) && (kgCow <= 500)) {
                checkWishKg(49, 56);
            } else if (kgCow > 500) {
                checkWishKg(57, 65);
            }
            choose_My_wish_Kg();
        }

        txt_kg_present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_DigitFood.clear();
                list_myWish_Kg.clear();
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_input_weight);

                EditText edt_kg_present = dialog.findViewById(R.id.edt_kg_present);
                Button btnOk = dialog.findViewById(R.id.btn_confirm_weight);
                Button btnCancel = dialog.findViewById(R.id.btn_notconfirm_weight);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String weight = edt_kg_present.getText().toString().trim();
                        if (weight.isEmpty()) {
                            Toast.makeText(getActivity(), "Vui lòng nhập cân nặng bò", Toast.LENGTH_SHORT).show();
                        } else {
                            txt_kg_present.setText(weight + "");

                            kgCow = Integer.parseInt(txt_kg_present.getText().toString().trim());
                            if ((100 <= kgCow) && (kgCow <= 150)) {
                                checkWishKg(6, 10);
                            } else if ((150 < kgCow) && (kgCow <= 200)) {
                                checkWishKg(11, 15);
                            } else if ((200 < kgCow) && (kgCow <= 250)) {
                                checkWishKg(16, 21);
                            } else if ((250 < kgCow) && (kgCow <= 300)) {
                                checkWishKg(22, 27);
                            } else if ((300 < kgCow) && (kgCow <= 350)) {
                                checkWishKg(28, 33);
                            } else if ((350 < kgCow) && (kgCow <= 400)) {
                                checkWishKg(34, 40);
                            } else if ((400 < kgCow) && (kgCow <= 450)) {
                                checkWishKg(41, 48);
                            } else if ((450 < kgCow) && (kgCow <= 500)) {
                                checkWishKg(49, 56);
                            } else if (kgCow > 500) {
                                checkWishKg(57, 65);
                            }
                            choose_My_wish_Kg();
                            dialog.dismiss();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }               // nhập cân nặng bò hiện tại

    private void choose_My_wish_Kg() {
        ArrayAdapter<String> adapter_Wish_Kg = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list_myWish_Kg);
        adapter_Wish_Kg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_Mywish_kg.setAdapter(adapter_Wish_Kg);
        choose_Mywish_kg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Log.d("thuan", "onItemSelected:  clickkkkkkkk" + position);

                indexWishKg = position;

                txtWeightWish.setText(list_myWish_Kg.get(position) + "/d");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (((Activity_NewRation) getActivity()).type != -1) {
            choose_Mywish_kg.setSelection(((Activity_NewRation) getActivity()).wish_kg);
        }
    }       //chọn cân nặng bò mong muốn tăng

    private void choose_typeCow() {
        list_typeCow.add("Bò đực");
        list_typeCow.add("Bò cái tơ ");
        ArrayAdapter<String> adapter_Type = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list_typeCow);
        adapter_Type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_typeCow.setAdapter(adapter_Type);
        choose_typeCow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_choose_type.setVisibility(View.VISIBLE);
                txtloaibo.setText(list_typeCow.get(position));
                if (position == 0) {
                    checkSheet = 0;
                    Log.d("thuan", "onItemSelected: " + checkSheet);
                } else if (position == 1) {
                    checkSheet = 1;
                    Log.d("thuan", "onItemSelected: " + checkSheet);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (((Activity_NewRation) getActivity()).type != -1) {
            choose_typeCow.setSelection(((Activity_NewRation) getActivity()).type);
        }
    }

    private void init() {
        choose_typeCow = view.findViewById(R.id.choose_typeCow);
        choose_Mywish_kg = view.findViewById(R.id.choose_Mywish_kg);
        txtloaibo = view.findViewById(R.id.txtloaibo);
        txt_choose_type = view.findViewById(R.id.txt_choose_type);
        txtWeight = view.findViewById(R.id.txtWeight);
        list_typeCow = new ArrayList<>();
        list_myWish_Kg = new ArrayList<>();
        txt_kg_present = view.findViewById(R.id.txt_kg_present);
        edt_name_ration = view.findViewById(R.id.edt_name_ration);
        txtWeightWish = view.findViewById(R.id.txtWeightWish);
        list_DigitFood = new ArrayList<>();

    }

    private void checkWishKg(int rowfirst, int rowSecond) {
        try {
            // FileInputStream inputStream = new FileInputStream(file); // khong dung nua, ma dung inpúttream, doc tu file assets
            //  HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            list_myWish_Kg.clear();
            InputStream as = getActivity().getAssets().open("data.xls");
            POIFSFileSystem myFileSystem = new POIFSFileSystem(as);
            HSSFWorkbook workbook = new HSSFWorkbook(myFileSystem);
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int row1 = rowfirst; row1 <= rowSecond; row1++) {
                HSSFRow row = sheet.getRow(row1);          //ở file Excel đếm từ 0
                HSSFCell cellwishkg = row.getCell(1);                // -> c = giá trị cột 6, hàng 10
                double valueWishkg = cellwishkg.getNumericCellValue();

                HSSFCell cellTagetDM = row.getCell(2);
                double valueTagetDM = cellTagetDM.getNumericCellValue();

                HSSFCell cellTagetCP = row.getCell(10);
                double valueTagetCP = cellTagetCP.getNumericCellValue();

                HSSFCell cellTagetMe = row.getCell(6);
                double valueTagetMe = cellTagetMe.getNumericCellValue();

                list_myWish_Kg.add(valueWishkg + " Kg");
                list_DigitFood.add(new Object_Food(valueTagetDM, valueTagetCP, valueTagetMe));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }       // lấy ra cân nặng bò muốn tăng

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}