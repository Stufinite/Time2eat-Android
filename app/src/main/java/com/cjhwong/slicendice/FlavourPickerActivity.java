package com.cjhwong.slicendice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FlavourPickerActivity extends Activity {
    TextView result;
    private String flavours;
    private boolean added;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavour_picker);

        Bundle b = getIntent().getExtras();
        flavours = b.getString("flavour") == null ? "" : b.getString("flavour")  + (b.getString("flavour") .length() == 0 ? "":",");

        String flavourString = b.getString("flavourString");
        String[] flavourArray = flavourString != null ? flavourString.split(",") : ", , , , , , , ,".split(",");

        ((Button) findViewById(R.id.number1Button)).setText(flavourArray[0]);
        ((Button) findViewById(R.id.number2Button)).setText(flavourArray[1]);
        ((Button) findViewById(R.id.number3Button)).setText(flavourArray[2]);
        ((Button) findViewById(R.id.number4Button)).setText(flavourArray[3]);
        ((Button) findViewById(R.id.number5Button)).setText(flavourArray[4]);
        ((Button) findViewById(R.id.number6Button)).setText(flavourArray[5]);
        ((Button) findViewById(R.id.number7Button)).setText(flavourArray[6]);
        ((Button) findViewById(R.id.number8Button)).setText(flavourArray[7]);
        ((Button) findViewById(R.id.number9Button)).setText(flavourArray[8]);

        result = (TextView) findViewById(R.id.flavourResult);
        result.setText(flavours);
    }

    public void onPlusClicked(View v) {
        if (added) {
            flavours = flavours.substring(0, flavours.length() - 1);
//            flavours += "),";
            flavours += ",";
            added = !added;
        }
        result.setText(flavours);
    }

    public void onMinusClicked(View v) {
        String[] flavourArray = flavours.split(",");
        flavours = "";
        for (int i = 0, len = flavourArray.length - 1; i < len; i += 1) {
            flavours += flavourArray[i];
            flavours += ",";
        }
        added = false;
        result.setText(flavours);
    }

    public void onButtonClicked(View v) {
        Button b = (Button) v;
        if (!added) {
//            flavours += "(";
            flavours += b.getText();
            flavours += "/";
            added = true;
        } else {
            flavours += b.getText();
//            flavours += "),";
            flavours += ",";
            added = false;
        }
        result.setText(flavours);
    }

    public void onEqualClicked(View v) {
        if (added) {
            Toast.makeText(v.getContext(), "Damn! You need to complete all the forms", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent();
            i.putExtra("flavours", flavours.substring(0, flavours.length() - 1));
            setResult(RESULT_OK, i);
            finish();
        }
    }
}
