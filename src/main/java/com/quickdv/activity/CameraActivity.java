package com.quickdv.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.quickdv.activity.until.DialogUntil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2015/6/1.
 */
public abstract class CameraActivity extends BaseActivity {

    protected DialogUntil dialogHelper;
    protected Target loadtarget;
    protected ImageView view;
    Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogHelper  =new DialogUntil(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            //选择图片
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor =getContentResolver().query(uri,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);  //获取照片路径



            loadtarget =new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    onImageSelect(bitmap,from);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            };
            Picasso.with(this).load(picturePath).into(loadtarget);

            try {
                if(bmp != null)//如果不释放的话，不断取图片，将会内存不够
                    bmp.recycle();

                    bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));


            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("the bmp toString: " + bmp);
//            imageSV.setBmp(bmp);

        }else{
            Toast.makeText(this, "请重新选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bmp !=null)
            bmp.recycle();
    }

    protected abstract void onImageSelect(Bitmap bitmap,Picasso.LoadedFrom from);
}
