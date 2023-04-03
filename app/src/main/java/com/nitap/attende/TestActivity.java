package com.nitap.attende;

import static org.apache.poi.ss.util.CellUtil.createCell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nitap.attende.models.Attendance;
import com.nitap.attende.models.SectionInfo;
import com.nitap.attende.models.Teacher;
import com.ttv.facerecog.R;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        XSSFWorkbook ourWorkBook = new XSSFWorkbook();
        Sheet sheet = ourWorkBook.createSheet("statSheet");
        Row row1 = sheet.createRow(0);
        Row row2 = sheet.createRow(1);

        row1.createCell(0).setCellValue("hdj");
        row1.createCell(1).setCellValue("bfkjsk");
        row2.createCell(0).setCellValue("jffs");
        row2.createCell(1).setCellValue("nfew");

        /*createCell(row1, 0, "Mike");
        createCell(row1, 1, "470");

        createCell(row2, 0, "Montessori");
        createCell(row2, 1, "460");*/


        File mydir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File myfile = new File(mydir.getAbsolutePath()+"test.xlsx");
        File myfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "test.xlsx");
        try {
            myfile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(myfile);
            ourWorkBook.write(fileOut);
            fileOut.close();
            Toast.makeText(this, "written", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writeAttendance(this,new Attendance());

    }

    public static void writeAttendance(Context context, Attendance attendance7)  {
        Attendance myattendance = new Attendance();
        Attendance attendance = new Attendance();
        attendance.attendance = new ArrayList<>();
        attendance.attendance.add("fwe");
        attendance.attendance.add("dwefw");


        try {
            Workbook workbook = null;
            // workbook = new HSSFWorkbook();
            File myfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Attendance.xlsx");
            //myfile.createNewFile();
            //workbook = new XSSFWorkbook(myfile.getPath());
            try {
                workbook = new XSSFWorkbook(myfile);
            } catch (Throwable e ) {
                workbook = new XSSFWorkbook();
            }
            SectionInfo sectionInfo = attendance.sectionInfo;
            //String sheetname = sectionInfo.degree+sectionInfo.branch+sectionInfo.year+sectionInfo.sem+sectionInfo.sectionName;
            String sheetname = "vjdhg";


            Sheet sheet = workbook.getSheet(sheetname);
            if (sheet==null) {
                sheet = workbook.createSheet(sheetname);
            }

            // Sheet sheet = workbook.createSheet("Countries");
            Row row = sheet.createRow(0);
            int noOfColumns = row.getPhysicalNumberOfCells();
            noOfColumns++;
            int rowIndex = 0;
            Iterator<String> iterator = attendance.attendance.iterator();
            while(iterator.hasNext()){
                String attendance1 = iterator.next();
                 row = sheet.createRow(rowIndex++);
                Cell cell0 = row.createCell(noOfColumns);
                cell0.setCellValue(attendance1);
                //Cell cell1 = row.createCell(1);
                //cell1.setCellValue(country.getShortCode());
            }





            FileOutputStream fos = new FileOutputStream(myfile);
            workbook.write(fos);
            fos.close();
            workbook.close();
        }catch (Throwable e) {
            e.printStackTrace();

            Toast.makeText(context, "Failed to update Excel sheet", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


}