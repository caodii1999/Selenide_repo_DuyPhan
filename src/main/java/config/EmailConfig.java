package config;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class EmailConfig {

  private static final String API_BASE = "https://api.guerrillamail.com/ajax.php";
  private static final HttpClient client = HttpClient.newHttpClient();
  private static String sidToken;

  public static String getSIDToken() throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(API_BASE + "?f=get_email_address&lang=en")).GET()
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    JSONObject json = new JSONObject(response.body());

    sidToken = json.getString("sid_token");
    return sidToken;
  }

  public static String getEmailAddress() throws Exception {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(API_BASE + "?f=get_email_address&lang=en&sid_token=" + sidToken))
          .GET()
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      JSONObject json = new JSONObject(response.body());

      return json.getString("email_addr");
    } catch (IOException | InterruptedException e) {
      e.printStackTrace(); // or use a logger
      return null; // or return a fallback value or throw a runtime exception if preferred
    }
  }
}
