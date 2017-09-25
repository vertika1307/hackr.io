package com.production.hackr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Submit extends AppCompatActivity {
    private EditText urledit;
    private Button sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        urledit=(EditText)findViewById(R.id.urlSubmit);

        sub=(Button)findViewById(R.id.submitButton);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(URLUtil.isValidUrl(urledit.toString())){
                    Toast.makeText(getBaseContext(),"Your tutorial has been submitted",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getBaseContext(),"Enter a Valid URL",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
