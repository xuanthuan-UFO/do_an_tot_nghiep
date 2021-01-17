package com.xuanthuan.nutritionforcow.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.xuanthuan.nutritionforcow.Controler.Adapter_chooseRation;
import com.xuanthuan.nutritionforcow.Model.NothingSelectedSpinnerAdapter;
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
import java.util.Collections;

public class Fragment_ChooseFood extends Fragment {
    ListView lvChoose;
    Adapter_chooseRation adapter_chooseRation;
    ArrayList<Object_Food> list_food;
    Spinner spn_choose_food;
    ArrayList<String> list_spiner_food;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__choose_food, container, false);

        init();

        adapter_chooseRation = new Adapter_chooseRation(list_food, getActivity(), R.layout.item_choose_food);
        //   arrayList.add(new Choose_Food("Cỏ voi"));
        lvChoose.setAdapter(adapter_chooseRation);

        //     file = new File("/storage/emulated/0/data.xls");
//            FileInputStream inputStream = new FileInputStream(file);
//            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        try {
            InputStream as = getActivity().getAssets().open("data.xls");
            POIFSFileSystem myFileSystem = new POIFSFileSystem(as);
            HSSFWorkbook workbook = new HSSFWorkbook(myFileSystem);

            HSSFSheet sheet = workbook.getSheetAt(6);
            for (int row1 = 2; row1 <= 55; row1++) {
                HSSFRow row = sheet.getRow(row1);          //ở file Excel đếm từ 0
                HSSFCell a = row.getCell(0);                // -> c = giá trị cột 6, hàng 10
                String c = a.getStringCellValue();
                Log.d("cay", "onActivityResult: 150 -200" + c);         // trừ 2. vị trí row - 2 = vị trí trong list.
                list_spiner_food.add(c);

                ArrayAdapter<String> adapter_choose_food_spn = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list_spiner_food);
                adapter_choose_food_spn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_choose_food.setPrompt("Chọn thức ăn!");
                spn_choose_food.setAdapter(
                        new NothingSelectedSpinnerAdapter(
                                adapter_choose_food_spn,
                                R.layout.contact_spinner_row_nothing_selected,
                                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                                getActivity()));

                spn_choose_food.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("thuan", "onItemSelected:  clickkkkkkkk" + position);
                        int index = position + 2;
                        HSSFRow rowItem = sheet.getRow(index);      // lấy vị trí hàng chứa item đó

                        HSSFCell cellDM = rowItem.getCell(1);
                        double DM = cellDM.getNumericCellValue();

                        HSSFCell cellCP = rowItem.getCell(2);
                        double CP = cellCP.getNumericCellValue();

                        HSSFCell cellME = rowItem.getCell(17);
                        double ME = cellME.getNumericCellValue();

                        try {
                            Log.d("zzz", "onItemSelected: type = -1");
                            Object_Food object_food = new Object_Food(index, list_spiner_food.get(position - 1), DM, CP, ME, 0);
                            if (list_food.isEmpty()) {
                                list_food.add(object_food);
                            } else {
                                try {
                                    for (int i = 0; i < list_food.size(); i++) {
                                        if (list_food.get(i).getNameFood().equals(object_food.getNameFood())) {
                                            list_food.remove(i);
                                            break;
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                list_food.add(object_food);

                            }
                            adapter_chooseRation.notifyDataSetChanged();

                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            if (((Activity_NewRation) getActivity()).type != -1) {
                for (int m = 0; m < ((Activity_NewRation) getActivity()).list_getFood.size(); m++) {
                    Log.d("zzz", "onItemSelected: setSelection " + m + " " + ((Activity_NewRation) getActivity()).list_getFood.get(m).getIndex());

                    HSSFRow rowItem = sheet.getRow(((Activity_NewRation) getActivity()).list_getFood.get(m).getIndex());      // lấy vị trí hàng chứa item đó

                    HSSFCell cellDM = rowItem.getCell(1);
                    double DM = cellDM.getNumericCellValue();

                    HSSFCell cellCP = rowItem.getCell(2);
                    double CP = cellCP.getNumericCellValue();

                    HSSFCell cellME = rowItem.getCell(17);
                    double ME = cellME.getNumericCellValue();

                    Log.d("zzz", "onCreateView: ??? " +((Activity_NewRation) getActivity()).list_getFood.get(m).getIndex() + " " +  list_spiner_food.get(((Activity_NewRation) getActivity()).list_getFood.get(m).getIndex() -3));

                    try {
                        Object_Food object_food = new Object_Food(((Activity_NewRation) getActivity()).list_getFood.get(m).getIndex(), list_spiner_food.get(((Activity_NewRation) getActivity()).list_getFood.get(m).getIndex() -3 ), DM, CP, ME, ((Activity_NewRation) getActivity()).list_getFood.get(m).getKg());
                        if (list_food.isEmpty()) {
                            list_food.add(object_food);
                        } else {
                            try {
                                for (int i = 0; i < list_food.size(); i++) {
                                    if (list_food.get(i).getNameFood().equals(object_food.getNameFood())) {
                                        list_food.remove(i);
                                        break;
                                    }
                                }
                            } catch (Exception e) {

                            }
                            list_food.add(object_food);

                        }
                        adapter_chooseRation.notifyDataSetChanged();

                    } catch (Exception e) {
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }


    private void init() {
        lvChoose = view.findViewById(R.id.lvChoose_food);
        list_food = new ArrayList<>();
        spn_choose_food = view.findViewById(R.id.spn_choose_food);
        list_spiner_food = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }
}