package farmer.buddy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Iterator;

public class InfoActivity extends AppCompatActivity {
  String requested_data;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info);
    requested_data=getIntent().getStringExtra("reqData");
    JSONObject infoData;
    try {
      infoData = new JSONObject(server.getResponse("http://"+getString(R.string.serverDomain)+"/info?id="+requested_data));
      LinearLayout list = findViewById(R.id.info_list);
      JSONObject lists = (JSONObject) infoData.get("list");
      Iterator<String> keys = lists.keys();
      while (keys.hasNext()) {
        String key = keys.next();
        JSONArray jsonArray = lists.getJSONArray(key);
        String text=jsonArray.getString(0);
        String image=jsonArray.getString(1);
        View newObj = getLayoutInflater().inflate(R.layout.info_list_layout,null);
        Button newButton = newObj.findViewById(R.id.info_list_button);
        ImageView imageView = newObj.findViewById(R.id.image);
        imageView.setImageResource(getResId(image,R.drawable.class));
        newButton.setText(text);
        newButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startActivity((new Intent(InfoActivity.this, InfoActivity.class)).putExtra("reqData",key));
          }
        });
        list.addView(newObj);
      }
      View newObj = getLayoutInflater().inflate(R.layout.text_layout,null);
      TextView newText = newObj.findViewById(R.id.text_layout_text);
      newText.setText(infoData.getString("desc"));
      newText.setMovementMethod(new ScrollingMovementMethod());
      list.addView(newObj);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
  public static int getResId(String resName, Class<?> c) {
    try {
      Field idField = c.getDeclaredField(resName);
      return idField.getInt(idField);
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }
}

