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
    if (!user.check()) {
      startActivity(new Intent(MenuActivity.this, MainActivity.class));
      MenuActivity.this.finish();
    }
    TextView menuText = (TextView)findViewById(R.id.menutext);
    menuText.setText(String.format("Welcome, %s", user.userName));
    Button weather = (Button)findViewById(R.id.weatherBTN);
    Button logout = (Button)findViewById(R.id.userLogOut);
    Button information = (Button)findViewById(R.id.informationBTN);
    weather.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MenuActivity.this, WeatherActivity.class));
      }
    });
    information.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity((new Intent(MenuActivity.this, InfoActivity.class)).putExtra("reqData","default"));
      }
    });
    logout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        user.logOut();
        MenuActivity.this.finish();
        startActivity(new Intent(MenuActivity.this, MainActivity.class));
      }
    });
  }
}
