package com.corddt.link_manage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class LinkAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Link> links;
    private DatabaseHelper databaseHelper;

    public LinkAdapter(Context context, ArrayList<Link> links, DatabaseHelper databaseHelper) {
        this.context = context;
        this.links = links;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public Object getItem(int position) {
        return links.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.link_item, parent, false);
        }

        TextView linkName = convertView.findViewById(R.id.linkName);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button visitButton = convertView.findViewById(R.id.visitButton); // 添加访问按钮的引用

        final Link link = links.get(position);
        linkName.setText(link.getName());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditLinkDialog(link);
            }
        });

        // 设置访问按钮的点击监听器
        visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建意图以打开网页
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link.getUrl()));
                context.startActivity(intent);
            }
        });

        return convertView;
    }


    private void showEditLinkDialog(final Link link) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_link_editor, null);
        final EditText nameField = dialogView.findViewById(R.id.nameField);
        final EditText urlField = dialogView.findViewById(R.id.urlField);

        nameField.setText(link.getName());
        urlField.setText(link.getUrl());

        builder.setView(dialogView)
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        link.setName(nameField.getText().toString());
                        link.setUrl(urlField.getText().toString());
                        databaseHelper.updateLink(link);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
}
