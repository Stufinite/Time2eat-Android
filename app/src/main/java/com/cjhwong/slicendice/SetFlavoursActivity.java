package com.cjhwong.slicendice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SetFlavoursActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_flavours);

        Bundle b = getIntent().getExtras();
        String flavour = b.getString("flavourString");
        if (flavour != null && !flavour.equals("")) {
            String[] flavourArray = flavour.split(",");

            ((EditText) findViewById(R.id.flavour_1)).setText(flavourArray[0]);
            ((EditText) findViewById(R.id.flavour_2)).setText(flavourArray[1]);
            ((EditText) findViewById(R.id.flavour_3)).setText(flavourArray[2]);
            ((EditText) findViewById(R.id.flavour_4)).setText(flavourArray[3]);
            ((EditText) findViewById(R.id.flavour_5)).setText(flavourArray[4]);
            ((EditText) findViewById(R.id.flavour_6)).setText(flavourArray[5]);
            ((EditText) findViewById(R.id.flavour_7)).setText(flavourArray[6]);
            ((EditText) findViewById(R.id.flavour_8)).setText(flavourArray[7]);
            ((EditText) findViewById(R.id.flavour_9)).setText(flavourArray[8]);
        }
    }

    public void onCancel(View v) {
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }

    public void onSave(View v) {
        String flavourString = "";
        flavourString += ((EditText) findViewById(R.id.flavour_1)).getText().toString() + ",";
        flavourString += ((EditText) findViewById(R.id.flavour_2)).getText().toString() + ",";
        flavourString += ((EditText) findViewById(R.id.flavour_3)).getText().toString() + ",";
        flavourString += ((EditText) findViewById(R.id.flavour_4)).getText().toString() + ",";
        flavourString += ((EditText) findViewById(R.id.flavour_5)).getText().toString() + ",";
        flavourString += ((EditText) findViewById(R.id.flavour_6)).getText().toString() + ",";
        flavourString += ((EditText) findViewById(R.id.flavour_7)).getText().toString() + ",";
        flavourString += ((EditText) findViewById(R.id.flavour_8)).getText().toString() + ",";
        flavourString += ((EditText) findViewById(R.id.flavour_9)).getText().toString();

        Intent i = new Intent();
        i.putExtra("flavours", flavourString);
        setResult(RESULT_OK, i);
        finish();
    }
}
