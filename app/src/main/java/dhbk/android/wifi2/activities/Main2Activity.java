package dhbk.android.wifi2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dhbk.android.wifi2.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void testLeak(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
