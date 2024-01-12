package com.corddt.link_manage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView linksListView;
    Button addButton;
    DatabaseHelper databaseHelper; // 添加数据库帮助器引用
    LinkAdapter linkAdapter; // 添加链接适配器引用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linksListView = findViewById(R.id.linksListView);
        addButton = findViewById(R.id.addButton);
        databaseHelper = new DatabaseHelper(this); // 初始化数据库帮助器

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddLinkDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayLinks(); // 刷新显示链接
    }

    private void displayLinks() {
        ArrayList<Link> links = databaseHelper.getAllLinks();
        linkAdapter = new LinkAdapter(this, links, databaseHelper);
        linksListView.setAdapter(linkAdapter);
    }

    private void showAddLinkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_link_editor, null);
        final EditText nameField = dialogView.findViewById(R.id.nameField);
        final EditText urlField = dialogView.findViewById(R.id.urlField);

        builder.setView(dialogView)
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameField.getText().toString();
                        String url = urlField.getText().toString();
                        Link newLink = new Link(name, url);
                        databaseHelper.addLink(newLink);
                        displayLinks();
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
}
