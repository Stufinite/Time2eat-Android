package faith.stufinite.time2eat;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import 	android.view.LayoutInflater;

public class MainActivity extends Activity {
    private LayoutInflater inflater;
    private View MainView;
    private View TodayView;
    private View OfferView;
    private View NearbyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inflater = (LayoutInflater)this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        MainView = inflater.inflate(R.layout.activity_main, null);
        TodayView = inflater.inflate(R.layout.today, null);
        OfferView = inflater.inflate(R.layout.onoffer, null);
        NearbyView = inflater.inflate(R.layout.nearby, null);
        super.onCreate(savedInstanceState);
        setContentView(MainView);
        Button SearchButton = (Button)findViewById(R.id.button);
        Button TodayButton = (Button)findViewById(R.id.button2);
        Button OfferButton = (Button)findViewById(R.id.button3);
        Button NearbyButton = (Button)findViewById(R.id.button4);
        SearchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了main", Toast.LENGTH_SHORT).show();
                if (!(findViewById(android.R.id.content) == MainView))
                {
                    jumptomain();
                }
            }
        });

        TodayButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了today", Toast.LENGTH_SHORT).show();
                    jumptotoday();
            }
        });

        OfferButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了offer", Toast.LENGTH_SHORT).show();
                    jumptooffer();
            }
        });

        NearbyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了nearby", Toast.LENGTH_SHORT).show();
                    jumptonearby();
            }
        });
    }
    public void jumptomain() {
        setContentView(MainView);
        Button SearchButton = (Button)findViewById(R.id.button);
        Button TodayButton = (Button)findViewById(R.id.button2);
        Button OfferButton = (Button)findViewById(R.id.button3);
        Button NearbyButton = (Button)findViewById(R.id.button4);
        SearchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了main", Toast.LENGTH_SHORT).show();
                if (!(findViewById(android.R.id.content) == MainView))
                {
                    jumptomain();
                }
            }
        });

        TodayButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了today", Toast.LENGTH_SHORT).show();
                jumptotoday();
            }
        });

        OfferButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了offer", Toast.LENGTH_SHORT).show();
                jumptooffer();
            }
        });

        NearbyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了nearby", Toast.LENGTH_SHORT).show();
                jumptonearby();
            }
        });
    }
    public void jumptotoday() {
        setContentView(TodayView);
        Button SearchButton = (Button)findViewById(R.id.tbutton);
        Button TodayButton = (Button)findViewById(R.id.tbutton2);
        Button OfferButton = (Button)findViewById(R.id.tbutton3);
        Button NearbyButton = (Button)findViewById(R.id.tbutton4);
        SearchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了main", Toast.LENGTH_SHORT).show();
                if (!(findViewById(android.R.id.content) == MainView))
                {
                    jumptomain();
                }
            }
        });

        TodayButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了today", Toast.LENGTH_SHORT).show();
                jumptotoday();
            }
        });

        OfferButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了offer", Toast.LENGTH_SHORT).show();
                jumptooffer();
            }
        });

        NearbyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了nearby", Toast.LENGTH_SHORT).show();
                jumptonearby();
            }
        });
    }
    public void jumptooffer() {
        setContentView(OfferView);
        Button SearchButton = (Button)findViewById(R.id.obutton);
        Button TodayButton = (Button)findViewById(R.id.obutton2);
        Button OfferButton = (Button)findViewById(R.id.obutton3);
        Button NearbyButton = (Button)findViewById(R.id.obutton4);
        SearchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了main", Toast.LENGTH_SHORT).show();
                if (!(findViewById(android.R.id.content) == MainView))
                {
                    jumptomain();
                }
            }
        });

        TodayButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了today", Toast.LENGTH_SHORT).show();
                jumptotoday();
            }
        });

        OfferButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了offer", Toast.LENGTH_SHORT).show();
                jumptooffer();
            }
        });

        NearbyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了nearby", Toast.LENGTH_SHORT).show();
                jumptonearby();
            }
        });
    }
    public void jumptonearby() {
        setContentView(NearbyView);
        Button SearchButton = (Button)findViewById(R.id.nbutton);
        Button TodayButton = (Button)findViewById(R.id.nbutton2);
        Button OfferButton = (Button)findViewById(R.id.nbutton3);
        Button NearbyButton = (Button)findViewById(R.id.nbutton4);
        SearchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了main", Toast.LENGTH_SHORT).show();
                if (!(findViewById(android.R.id.content) == MainView))
                {
                    jumptomain();
                }
            }
        });

        TodayButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了today", Toast.LENGTH_SHORT).show();
                jumptotoday();
            }
        });

        OfferButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了offer", Toast.LENGTH_SHORT).show();
                jumptooffer();
            }
        });

        NearbyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你點了nearby", Toast.LENGTH_SHORT).show();
                jumptonearby();
            }
        });
    }
}
