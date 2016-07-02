package com.onlylady.beautyapp.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.onlylady.beautyapp.R;

import java.io.InputStream;

public class TextReaderActivity extends AppCompatActivity {


    private TextView textreader;
    private TextView title;
//    private ImageView menugoback;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_text_reader);
        //this.menugoback = (ImageView) findViewById(R.id.menu_goback);
        this.title = (TextView) findViewById(R.id.title);
        this.textreader = (TextView) findViewById(R.id.textreader);
        title.setText("服务协议");
        textreader.setText( readFile("fwxy.txt"));
//        if (getIntent().getBooleanExtra("fwxy", false)) {
//            title.setText("服务协议");
//            textreader.setText( readFile("fwxy.txt"));
//        }else if (getIntent().getBooleanExtra("yssm", false)){
//            title.setText("隐私声明");
//            textreader.setText( readFile("yssm.txt"));
//        }

    }

    private String readFile(String fileName) {
        String content = "";
        try {
            InputStream in = getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            in.close();
            content = new String(buffer);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return content.replace("\\n", "\n");
    }
    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(UmengKey.TEXTREADERACTIVITY);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(UmengKey.TEXTREADERACTIVITY);
    }

}
