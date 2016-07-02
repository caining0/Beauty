package com.onlylady.beautyapp.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by cbs on 2016/4/1.
 */
public class MFileUtils {
    private static MFileUtils fileUtils = null;

    public static MFileUtils getInstance() {
        if (fileUtils == null)
            fileUtils = new MFileUtils();
        return fileUtils;
    }

    private MFileUtils() {

    }

    public String saveData2File(final byte[] data,String file_name) {
        final File file = createFile(file_name);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OutputStream os = new FileOutputStream(file);
                    os.write(data);
                    os.flush();
                    os.close();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
        return file.getAbsolutePath();
    }
    public File createFile(String file_name) {
        File updateDir = new File(Environment.getExternalStorageDirectory() + "/" + "beauty" + "/");
        File file = new File(updateDir + "/" + file_name);

        if (!updateDir.exists()) {
            updateDir.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {

                return null;
            }
        }
        return file;

    }
    public boolean haveFile(String file_name){
        File updateDir = new File(Environment.getExternalStorageDirectory() + "/" + "beauty" + "/");
        File file = new File(updateDir + "/" + file_name);
        if (file.exists()){
            return true;
        }else {
            return false;
        }
    }
    public String getFilePath(String file_name){
        File updateDir = new File(Environment.getExternalStorageDirectory() + "/" + "beauty" + "/");
        File file = new File(updateDir + "/" + file_name);
        return file.getAbsolutePath();
    }
}
