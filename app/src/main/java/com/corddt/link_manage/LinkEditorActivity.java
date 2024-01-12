package com.corddt.link_manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LinkEditorActivity extends AppCompatActivity {
    EditText nameField, urlField;
    Button saveButton;
    DatabaseHelper databaseHelper; // 添加数据库帮助器引用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_editor);

        nameField = findViewById(R.id.nameField);
        urlField = findViewById(R.id.urlField);
        saveButton = findViewById(R.id.saveButton);
        databaseHelper = new DatabaseHelper(this); // 初始化数据库帮助器

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入
                String name = nameField.getText().toString();
                String url = urlField.getText().toString();

                // 创建新的链接对象
                Link newLink = new Link(name, url);

                // 保存链接到数据库
                databaseHelper.addLink(newLink);

                // 返回到主活动
                Intent intent = new Intent(LinkEditorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
