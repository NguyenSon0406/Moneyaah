package com.example.moneyaah_system;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    ImageButton btnRecord;
    ImageButton menu;
    TextView txt_textRec;
    Button btn_Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRecord = findViewById(R.id.btn_record);
        txt_textRec = findViewById(R.id.txt_TextRec);
        btn_Save = findViewById(R.id.btn_save);
        //thiết lập hành động cho button Record
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Speak();
            }
        });
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(v);
            }
        });

    }

    public void Speak()
    {

        //tạo intent để hiện thị text ghi nhận sau khi record
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //Kiểm tra và yêu cầu người dùng đồng ý xác nhận yêu cầu truy cập cho phép sử dụng record
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.RECORD_AUDIO},1);
            return;
        }

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speak something");
        //
        try{
                activityResultLauncher.launch(intent);
        }catch (Exception e)
        {
            //Nếu có lỗi thì hiển thị lời nhắn
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    //tiếp nhận input từ record và xử lý
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    //tạo mảng lấy text từ voice intent
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        ArrayList<String> arrResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        //hiển thị ra textview
                        txt_textRec.setText(arrResult.get(0));

                    }

                }
            });
    public void alertDialog(View v){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo").setMessage("Bạn có chắc muốn lưu "+txt_textRec.getText().toString()+ " không ?");
            builder.setCancelable(true);
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    txt_textRec.setText(""+SplitNumber(txt_textRec.getText().toString()));

                }
            });
            builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();


    }
    //hàm tách số từ chuỗi
    public int SplitNumber( String str)
    {
        int num = 0 ;
        //lấy chuỗi từ text sau khi record
        //thay thế các kí tự không phải số, số âm
        str = str.replaceAll("[^0-9,-\\.]",",");

        String []item = str.split((","));
        int tam = 0;
        for(int i =0 ;i<item.length;i++)
        {
            try{
                int transfer = Integer.parseInt(item[i]);
                if(transfer>=500) {
                    num = transfer;
                    if(num>tam)
                        tam=num;
                }
            }catch (NumberFormatException e){

            }

        }
        return tam;
    }
}