package farmer.buddy;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class user {
  public static String domain;
  public static Long securityID = 0L;
  public static boolean status = true;
  public static int cnt = 0;
  public static String userName;
  public static String cityName;
  public static int landArea;
  public static String fileName = "loggerUser";
  public static void login(String username, String password) {
    try {
      JSONObject jsonObject = new JSONObject(server.getResponse("http://" + domain + "/login?id=" + username + "&pw=" + password));
      if (jsonObject.getBoolean("status")) {
         cityName = jsonObject.getString("cityName");
         landArea = jsonObject.getInt("landArea");
         userName = jsonObject.getString("userName");
         securityID = jsonObject.getLong("securityID");
         status=true;
      }
    } catch (JSONException e) {
      status = false;
      e.printStackTrace();
    }
  }
  public static void saveSID(Context context) {
    try {
      FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
      fos.write(securityID.byteValue());
      fos.write(longtoBytes(securityID));
      fos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  };
  public static boolean check() {
    if (!status)
      return false;
    try {
      JSONObject jsonObject = new JSONObject(server.getResponse("http://" + domain + "/check_user?sid="+securityID));
      status=jsonObject.getBoolean("status");
      cityName=jsonObject.getString("cityName");
      userName=jsonObject.getString("userName");
      landArea=jsonObject.getInt("landArea");
    } catch (JSONException e) {
      status = false;
      e.printStackTrace();
    }
    return status;
  }
  public static String getWeather() {
    return "weatherOK";
  }
  public static void logOut() {
    server.getResponse("http://"+domain+"/logout?sid="+securityID);
    status=false;
  }
  public static void init_user(Context context) {
    domain = context.getString(R.string.serverDomain);
    try {
      FileInputStream fis = context.openFileInput(fileName);
      securityID=0L;
      int b;
      while((b = fis.read()) != -1) {
        securityID<<=8;
        securityID|=b;
      }
      System.out.println("securityID = "+securityID);
      System.out.println(securityID);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private static byte[] longtoBytes(long data) {
   return new byte[]{
   (byte) ((data >> 56) & 0xff),
   (byte) ((data >> 48) & 0xff),
   (byte) ((data >> 40) & 0xff),
   (byte) ((data >> 32) & 0xff),
   (byte) ((data >> 24) & 0xff),
   (byte) ((data >> 16) & 0xff),
   (byte) ((data >> 8) & 0xff),
   (byte) ((data >> 0) & 0xff),
   };
  }
}