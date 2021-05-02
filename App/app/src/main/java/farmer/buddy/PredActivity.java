package farmer.buddy;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Iterator;

public class PredActivity extends AppCompatActivity {
  private EditText city;
  private Button predict;
  private LinearLayout listView;
  @Override
  public void onCreate(Bundle savedInstancec) {
    super.onCreate(savedInstancec);
    setContentView(R.layout.activity_pred);
    city = (EditText)findViewById(R.id.city);
    predict = (Button)findViewById(R.id.predict);
    listView = (LinearLayout)findViewById(R.id.listView);
    city.setText(user.cityName);
    resetRelative();
    predict.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideSoftKeyboards(PredActivity.this, v);
        resetRelative();
      }
    });
  }
  public void resetRelative() {
    listView.removeAllViews();
    try {
      JSONObject jsonObject = new JSONObject(server.getResponse("http://"+getString(R.string.serverDomain)+"/ai?city="+city.getText().toString().toLowerCase()));
      RelativeLayout header = (RelativeLayout)getLayoutInflater().inflate(R.layout.text_layout, null);
      ((TextView)header.findViewById(R.id.text_layout_text)).setText(String.format("found %d predictions for %s.", jsonObject.length(), city.getText().toString()));
      Iterator<String> keys = jsonObject.keys();
      listView.addView(header);
      while (keys.hasNext()) {
        String crop = keys.next();
        Double price = jsonObject.getDouble(crop);
        RelativeLayout newObj = (RelativeLayout)getLayoutInflater().inflate(R.layout.text_block, null);
        TextView textView = (TextView)newObj.findViewById(R.id.textView);
        textView.setText(crop+" : Rs. "+Math.round(price));
        listView.addView(newObj);
      }
    } catch (JSONException e) {
      RelativeLayout header = (RelativeLayout)getLayoutInflater().inflate(R.layout.text_layout, null);
      ((TextView)header.findViewById(R.id.text_layout_text)).setText(e.getMessage());
      listView.addView(header);
      e.printStackTrace();
    }
  }
  public void hideSoftKeyboards(android.app.Activity activity, View view) {
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
  }
}
