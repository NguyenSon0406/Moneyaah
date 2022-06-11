package com.example.moneyaah_system;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyaah_system.fragment.ExspenseFragment;
import com.example.moneyaah_system.fragment.IncomeFragment;
import com.example.moneyaah_system.screens.NoteScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecordVoice extends AppCompatActivity {
    ImageButton btnRecord;
    ImageButton menu;
    TextView txt_textRec;
    Button btn_Save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_voice);
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
        if(ActivityCompat.checkSelfPermission(RecordVoice.this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(RecordVoice.this,new String[]{
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
                String getNumber = ""+SplitNumber(txt_textRec.getText().toString());
                String voiceText = txt_textRec.getText().toString();

                Intent myIntent = new Intent(RecordVoice.this, NoteScreen.class);
                Bundle bundle = new Bundle();
                bundle.putString("money",getNumber);
                bundle.putString("note",voiceText);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
//                ExspenseFragment expenseFrag = new ExspenseFragment();
////                viewpageAdapter.getItem(1).setArguments(bundle);
//                androidx.fragment.app.FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
//                expenseFrag.setArguments(bundle);
//                fragTransaction.replace(R.id.container, expenseFrag).commit();

//                IncomeFragment incomeFrag = new IncomeFragment();
//                incomeFrag.setArguments(bundle);
//                fragTransaction.replace(R.id.container,incomeFrag).commit();
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
//ham noteScreen tra ve intent

}