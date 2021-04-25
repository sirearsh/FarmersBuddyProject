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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherActivity extends AppCompatActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weather);
    if (!user.check()) {
      startActivity(new Intent(WeatherActivity.this, MainActivity.class));
      WeatherActivity.this.finish();
    }
    EditText CityName = (EditText)findViewById(R.id.CityName);
    Button checkWeather = (Button)findViewById(R.id.checkWeather);
    TextView output = (TextView)findViewById(R.id.weatherTXT);
    TextView max = (TextView)findViewById(R.id.maxTEMP);
    TextView min = (TextView)findViewById(R.id.minTEMP);
    TextView humid = (TextView)findViewById(R.id.humidityTXT);
    TextView wind = (TextView)findViewById(R.id.windTXT);
    TextView rise = (TextView)findViewById(R.id.sunriseTXT);
    TextView set = (TextView)findViewById(R.id.sunsetTXT);
    CityName.setText(user.cityName);
    checkWeather.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideSoftKeyboards(WeatherActivity.this, v);
        try {
          JSONObject jsonObject = new JSONObject(server.getResponse("https://api.openweathermap.org/data/2.5/weather?q="+CityName.getText().toString()+"&appid="+getString(R.string.weatherAPItoken)));
          DecimalFormat f = new DecimalFormat("##.00");
          String res = "temperature is "+f.format(Double.parseDouble(jsonObject.getJSONObject("main").getString("temp"))-273.15)+" °C \n";
          res +=  "but temperature feels like "+f.format(Double.parseDouble(jsonObject.getJSONObject("main").getString("feels_like"))-273.15)+" °C\n";
          output.setText(res);
          String maxt ="Max. Temp.\n "+f.format(Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_max"))-273.15)+" °C\n";
          max.setText(maxt);
          String mint ="Min. Temp.\n"+f.format(Double.parseDouble(jsonObject.getJSONObject("main").getString("temp_min"))-273.15)+" °C\n";
          min.setText(mint);
          String windStr ="Wind Speed \n"+(jsonObject.getJSONObject("wind").getString("speed"))+" km/h \n";
          wind.setText(windStr);
          String humidStr ="Humidity \n"+(jsonObject.getJSONObject("main").getString("humidity"))+" % \n";
          humid.setText(humidStr);
          String riseStr =(jsonObject.getJSONObject("sys").getString("sunrise"));
          //rise.setText(riseStr);
          String setStr =(jsonObject.getJSONObject("sys").getString("sunset"));
          //set.setText(setStr);

          Calendar cal = Calendar.getInstance();
          Long timestamp = Long.parseLong(setStr);
          cal.setTimeInMillis(timestamp * 1000);
          DateFormat format = new SimpleDateFormat("HH:mm:ss");
          set.setText("Sunset \n"+format.format(cal.getTime()));

          Calendar cal2 = Calendar.getInstance();
          Long timestamp2 = Long.parseLong(riseStr);
          cal2.setTimeInMillis(timestamp2 * 1000);
          DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
          rise.setText("Sunrise \n"+format2.format(cal2.getTime()));

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
