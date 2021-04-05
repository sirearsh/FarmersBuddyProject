package farmer.buddy;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class server {
  public static OkHttpClient client = new OkHttpClient.Builder()
    .connectTimeout(5, TimeUnit.SECONDS)
    .writeTimeout(5, TimeUnit.SECONDS)
    .readTimeout(5, TimeUnit.SECONDS)
    .build();

  public static String getResponse(String Url){
    System.out.println(Url);
    String res;
    try {
      Request request = new Request.Builder()
        .url(Url)
        .build();
      Response response = client.newCall(request).execute();
      if (!response.isSuccessful()) {
        res = "Error";
      } else {
        res =  response.body().string();
      }
    } catch(IOException e) {
      res = "IOException";
    }
    System.out.println(res);
    return res;
  }
}