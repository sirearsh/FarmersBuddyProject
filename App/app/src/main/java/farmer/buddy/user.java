package farmer.buddy;

import org.json.JSONException;
import org.json.JSONObject;

public class user {
//  public static int securityID;
  public static boolean status = false;
  public static int cnt = 0;
  public static String cityName;
  public static int landArea;
  public static void login(String domain, String username, String password) {
    try {
      JSONObject jsonObject = new JSONObject(server.getResponse("http://" + domain + "/login?id=" + username + "&pw=" + password));
      if (jsonObject.getBoolean("status")) {
         cityName = jsonObject.getString("cityName");
         landArea = jsonObject.getInt("landArea");
         status=true;
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
  public static boolean check() {
    return status;
  }
  public static String getWeather() {
    return "weatherOK";
  }
}
