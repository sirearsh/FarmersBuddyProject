package farmer.buddy;

public class user {
  public static boolean status = false;
  public static int cnt = 0;
  public static void login(String domain, String username, String password) {
    String response = server.getResponse("http://"+ domain +"/login?id="+username+"&pw="+password);
    if (response.equals("successful"))
      status = true;
    else status = false;
  }
  public static boolean check() {
    return status;
  }
  public static String getWeather() {
    return "weatherOK";
  }
}
