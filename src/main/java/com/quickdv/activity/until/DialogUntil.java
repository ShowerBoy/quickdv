package com.quickdv.activity.until;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;

/**
 * Created by Administrator on 2015/6/1.
 */
public class DialogUntil {

    Context context;
    Activity act;

    final public int SELECT_PICTURE = 1;
    final public int SELECT_CAMER = 0;

    public DialogUntil(Activity context) {
        this.act = context;
    }

    public AlertDialog BuildCameraDialog() {
        CharSequence[] items = {"相机拍照", "从相册选择"};
        AlertDialog.Builder builder =  new AlertDialog.Builder(act)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == SELECT_PICTURE) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("image/*");
                            act.startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            act.startActivityForResult(intent, SELECT_CAMER);
                        }
                    }
                });
        return builder.create();

    }

}
