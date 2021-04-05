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
  private static boolean wait;
  private static String res;
  public static String getResponse(String Url) {
    System.out.println(Url);
    wait=true;
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Request request = new Request.Builder()
            .url(Url)
            .build();
          Response response = client.newCall(request).execute();
          if (!response.isSuccessful()) {
            res = "Error";
          } else {
            res = response.body().string();
          }
        } catch (IOException e) {
          res = "IOException";
        }
        wait=false;
      }
    });
    t.start();
    int time=0;
    while (wait) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (time++==10) {
        if (t.isAlive()) {
          t.interrupt();
          res = "interrupt";
        }
      }
    }
    System.out.println(res);
    return res;
  }
}