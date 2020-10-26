package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbmhs;
    EditText id;
    EditText judul;
    EditText pengarang;
    Button add;
    Button view;
    Button update;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbmhs = new DatabaseHelper(this);

        id = (EditText)findViewById(R.id.id);
        judul = (EditText)findViewById(R.id.judulBuku);
        pengarang = (EditText)findViewById(R.id.pengarangBuku);
        add = (Button)findViewById(R.id.button_add);
        view = (Button)findViewById(R.id.button_view);
        update = (Button)findViewById(R.id.button_update);
        delete = (Button)findViewById(R.id.button_delete);

        addData();
        viewData();
        updateData();
        deleteData();
    }

    public void addData(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean masuk = dbmhs.insertData(judul.getText().toString(), pengarang.getText().toString());
                if(masuk==true)
                    Toast.makeText(MainActivity.this,"Data Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data Gagal Ditambahkan",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewData(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = dbmhs.getAllData();
                if(res.getCount()==0){
                    showMessage("Error","No Data Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID: "+res.getString(0)+"\n");
                    buffer.append("Judul: "+res.getString(1)+"\n");
                    buffer.append("Pengarang: "+res.getString(2)+"\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void updateData(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = dbmhs.updateData(id.getText().toString(), judul.getText().toString(), pengarang.getText().toString());
                if(isUpdate==true) {
                    Toast.makeText(MainActivity.this, "Data Berhasil Diubah", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data Gagal Diubah", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteData(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows = dbmhs.deleteData(id.getText().toString());
                if(deleteRows > 0){
                    Toast.makeText(MainActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data Gagal Dihapus", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}