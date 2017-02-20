package com.tonikamitv.loginregister.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tonikamitv.loginregister.R;

public class ScanBarcodeActivity extends AppCompatActivity {
    public static final int REQUEST_QR_SCAN = 4;
    TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        textContent = (TextView) findViewById(R.id.textContent);

        Button buttonIntent = (Button) findViewById(R.id.buttonIntent);
        buttonIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                startActivityForResult(Intent.createChooser(intent, "Scan with"), REQUEST_QR_SCAN);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_QR_SCAN && resultCode == RESULT_OK) {
            String contents = intent.getStringExtra("SCAN_RESULT");
            textContent.setText(contents);
        }
    }
}
