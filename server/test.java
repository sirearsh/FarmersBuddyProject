import org.json.*;

public class test {
    public static void main (String [] args) {
        try {
            JSONObject obj = new JSONObject("{\"coord\":{\"lon\":-0.1257,\"lat\":51.5085},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"base\":\"stations\",\"main\":{\"temp\":279.46,\"feels_like\":275.45,\"temp_min\":278.15,\"temp_max\":280.93,\"pressure\":1017,\"humidity\":70},\"visibility\":10000,\"wind\":{\"speed\":6.69,\"deg\":360,\"gust\":12.35},\"clouds\":{\"all\":40},\"dt\":1617611031,\"sys\":{\"type\":1,\"id\":1414,\"country\":\"GB\",\"sunrise\":1617600383,\"sunset\":1617647973},\"timezone\":3600,\"id\":2643743,\"name\":\"London\",\"cod\":200}");
            System.out.println(obj.getJSONObject("main").get("temp").toString());
            System.out.println(obj.getJSONObject("main").get("feels_like").toString());
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}