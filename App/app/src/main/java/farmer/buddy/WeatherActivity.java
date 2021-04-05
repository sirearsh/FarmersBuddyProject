package farmer.buddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherActivity extends AppCompatActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weather);
    if (!user.check()) {
      WeatherActivity.this.finish();
      startActivity(new Intent(WeatherActivity.this, MainActivity.class));
    }
    EditText CityName = (EditText)findViewById(R.id.CityName);
    Button checkWeather = (Button)findViewById(R.id.checkWeather);
    TextView output = (TextView)findViewById(R.id.weatherTXT);
    CityName.setText(user.cityName);
    checkWeather.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideSoftKeyboards(WeatherActivity.this, v);
        try {
          JSONObject jsonObject = new JSONObject(server.getResponse("https://api.openweathermap.org/data/2.5/weather?q="+CityName.getText().toString()+"&appid="+getString(R.string.weatherAPItoken)));
          DecimalFormat f = new DecimalFormat("##.00");
          String res = "temperature is "+f.format(Double.parseDouble(jsonObject.getJSONObject("main").getString("temp"))-273.15)+" degree celcius\n";
          res +=  "but temperature feels like "+f.format(Double.parseDouble(jsonObject.getJSONObject("main").getString("feels_like"))-273.15)+" degree celcius\n";
          output.setText(res);
        } catch (JSONException e) {
          output.setText(e.getMessage());
        }
      }
    });
    
    ((ConstraintLayout)findViewById(R.id.activity_weather)).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideSoftKeyboards(WeatherActivity.this, v);
      }
    });
  }
  public void hideSoftKeyboards(android.app.Activity activity, View view) {
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
  }
}
