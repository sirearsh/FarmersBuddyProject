package farmer.buddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu);
    TextView menuText = (TextView)findViewById(R.id.menutext);
    menuText.setText("Welcome to menu");
    Button weather = (Button)findViewById(R.id.weatherBTN);
    weather.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MenuActivity.this, WeatherActivity.class));
      }
    });
  }
}
