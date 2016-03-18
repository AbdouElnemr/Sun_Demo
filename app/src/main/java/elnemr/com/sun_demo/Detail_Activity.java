package elnemr.com.sun_demo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Detail_Activity extends AppCompatActivity {

    TextView dayName, dayDate, desc;
    ImageView img;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.weatherclear);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dayName = (TextView) findViewById(R.id.dayName);
        dayDate = (TextView) findViewById(R.id.dayDate);
        desc = (TextView) findViewById(R.id.textView);
        img = (ImageView) findViewById(R.id.imageView);

        Calendar calendar = Calendar.getInstance();

        String time = calendar.getTime().toString();


        Intent intent = getIntent();
        String day_name = intent.getStringExtra("Day_name");
        String test_img = intent.getStringExtra("pic");

        switch (test_img) {
            case "Sunny":
                img.setImageResource(R.drawable.clearandsunny);
                desc.setText("Sunny");
                break;
            case "Rainy":
                img.setImageResource(R.drawable.bigrainy);
                desc.setText("Rainy");
                break;
            case "Cloudy":
                img.setImageResource(R.drawable.clouds);
                desc.setText("Cloudy");
                break;
        }

        dayName.setText(day_name);
        dayDate.setText(time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.appbar_detail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                Toast.makeText(this, " Share ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_message:
                Toast.makeText(this, " Message ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, Settings_Activity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}