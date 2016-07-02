/*
package com.onlylady.beauty.elses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.onlylady.beauty.R;


public class LogInfoActivity extends Activity implements OnClickListener {
    private TextView mInfoTextView;

    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_log_info);
        this.mBackButton = (Button) this.findViewById(R.id.bt_back);
        this.mBackButton.setOnClickListener(this);
        this.mInfoTextView = (TextView) this.findViewById(R.id.tv_play_info);
        this.initData();
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }

    private void initData() {
        Intent intent = this.getIntent();
        if (null == intent) {
            return;
        }

        Bundle bundle = intent.getExtras(); 
        if (null == bundle) {
            return;
        }

        String info = bundle.getString("logInfo");
        if (TextUtils.isEmpty(info)) {
            return;
        }

        this.mInfoTextView.setText(info);
    }

}
*/
