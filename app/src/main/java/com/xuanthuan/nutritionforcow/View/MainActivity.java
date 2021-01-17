package com.xuanthuan.nutritionforcow.View;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xuanthuan.nutritionforcow.R;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button btncreate, btnMyRation;
    int REQUEST_CODE = 1334;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File("/storage/emulated/0/data.xls");
        // FileInputStream inputStream = new FileInputStream(file);
        //   HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        //  HSSFWorkbook workbook = new HSSFWorkbook(myFileSystem);
        //   HSSFSheet sheet = workbook.getSheetAt(0);
        //  HSSFRow row = sheet.getRow(9);
        //   HSSFCell a = row.getCell(5);
        //   double c = a.getNumericCellValue();
        checkFilePermissions();
        init();


        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity_NewRation.class));
            }
        });

        btnMyRation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity_MyRation.class));
            }
        });

    }

    private void init() {
        btncreate = findViewById(R.id.btncreate);
        btnMyRation = findViewById(R.id.btnMyRation);

        //test, check thử, vì đã gán mặc định cho máy này đường dẫn
        // Log.d("aaaaaaaa", "onActivityResultaa: "+file.getPath());
        // Do something with the result...
        try {
            InputStream as = getAssets().open("data.xls");
            POIFSFileSystem myFileSystem = new POIFSFileSystem(as);
            HSSFWorkbook workbook = new HSSFWorkbook(myFileSystem);
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = sheet.getRow(9);          //ở đây đếm từ 0, nên khi đọc file excel thì + thêm 1 index ở file excel
            HSSFCell a = row.getCell(5);                // -> c = giá trị cột 6, hàng 10
            double c = a.getNumericCellValue();
            Log.d("cay", "onActivityResult: aa " + c);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //   xin quyền
    private void checkFilePermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        } else {
            Log.d("check", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    //data này - dme 1 tháng
    private void checkUri() {
        //        btnchoosefile = findViewById(R.id.btnchoosefile);
//        btnchoosefile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, FilePickerActivity.class);
//                // Set these depending on your use case. These are the defaults.
//                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
//                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
//                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
//                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
//                startActivityForResult(i, REQUEST_CODE);
//
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//
//                //lay ra uri dang file hoặc string vì FileInputStream đọc dạng string.
//                List<Uri> files = Utils.getSelectedFilesFromResult(data);
//                for (Uri uri: files) {
//                    File file = Utils.getFileForUri(uri);
//                    Log.d("cay", "onActivityResult: " + file);
//                    File file1 = new File("/storage/emulated/0/data.xls");
//                    // Log.d("aaaaaaaa", "onActivityResultaa: "+file.getPath());
//                    // Do something with the result...
//                    try {
//                        FileInputStream inputStream = new FileInputStream(file);
//                        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);          //tạo workbook.
//                        HSSFSheet sheet = workbook.getSheetAt(0);                  // dạo trang
//
//                        HSSFRow row = sheet.getRow(9);                          //tạo dòng
//                        HSSFCell a = row.getCell(5);                            // tạo ô theo dòng trên
//
//                        double c = a.getNumericCellValue();                             //kiểm tra xem ô trong file xls là dạng gì rồi export ra dạng đó
//                        Log.d("cay", "onActivityResult: "+ c);
//

//                      thư viên API để đọc file
////                    POIFSFileSystem poifs = new POIFSFileSystem (inputStream);
////                    InputStream din = poifs.createDocumentInputStream (String.valueOf(workbook));
////                    HSSFRequest req = new HSSFRequest ();
////                    req.addListenerForAllRecords (new Event());
////                    HSSFEventFactory factory = new HSSFEventFactory ();
////                    factory.processEvents (req, din);
////                    inputStream.close ();
////                    din.close ();
////                    Log.d("path", "onActivityResult:  okkkkkkkkk");
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            super.onActivityResult(requestCode, resultCode, data);
//        }

//        //xin quyền
//        private void checkFilePermissions() {
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//                int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
//                permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
//                if (permissionCheck != 0) {
//
//                    this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
//                }
//            } else {
//                Log.d("check", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
//            }
//        }
//
//
//        // cái này để lấy ra đường dẫn, sau này có thể dùng.
//        public static String getPath(Context context, Uri uri) throws URISyntaxException {
//            if ("content".equalsIgnoreCase(uri.getScheme())) {
//                String[] projection = {"_data"};
//                Cursor cursor = null;
//                try {
//                    cursor = context.getContentResolver().query(uri, projection, null, null, null);
//                    int column_index = cursor.getColumnIndexOrThrow("_data");
//                    if (cursor.moveToFirst()) {
//                        return cursor.getString(column_index);
//                    }
//                } catch (Exception e) {
//                    // Eat it
//                }
//            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//                return uri.getPath();
//            }
//            return null;
//        }
//            }
//        });

    }

}