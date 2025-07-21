package helper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class GuerrillamailAPI {

	private static final String API_BASE = "https://api.guerrillamail.com/ajax.php";
	private static final HttpClient client = HttpClient.newHttpClient();
	private static final String PASSWORD = "Abc@123456";
	private static String sidToken;
	private static int mailID;

	public static String getEmailAddress() throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(API_BASE + "?f=get_email_address&lang=en&sid_token=" + sidToken)).GET().build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject json = new JSONObject(response.body());

		String emailAddress = json.getString("email_addr");
		return emailAddress;
	}

	public static String getPassword() {
		return PASSWORD;
	}

	public static String getSIDToken() throws Exception {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_BASE + "?f=get_email_address&lang=en")).GET()
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject json = new JSONObject(response.body());

		sidToken = json.getString("sid_token");
		System.out.println("sidtoken =" + sidToken);
		return sidToken;
	}

	public static int getMailID() throws Exception {

		String checkUrl = API_BASE + "?f=check_email&sid_token=" + sidToken + "&seq=0";
		System.out.println("checkURl" + checkUrl);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(checkUrl)).GET().build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject json = new JSONObject(response.body());

		JSONArray emails = json.getJSONArray("list");

		JSONObject email = emails.getJSONObject(0);
		mailID = email.getInt("mail_id");

		System.out.println("mail id=" + mailID);
		return mailID;
	}

	public static String fetchEmail() throws Exception {
		System.out.println("call fetch method");
		String fetchUrl = API_BASE + "?f=fetch_email&sid_token=" + sidToken + "&email_id=" + mailID;
		System.out.println("get url" + fetchUrl);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fetchUrl)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject json = new JSONObject(response.body());

		String body = json.getString("mail_body");
		System.out.println("EMAIL BODY:\n" + body);
		return body;
	}

//	public static void registerAccount() throws Exception {
//		System.out.println("call");
//		String urlRegex = "<a[^>]+href=[\\\"']([^\\\"']+)[\\\"'][^>]*>\\\\s*Click here to set your new password.*?</a>";
//		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
//		System.out.println("get pattern");
//		Matcher matcher = pattern.matcher(fetchEmail());
////		System.out.println("got email" + matcher);
//
//		if (matcher.find()) {
//			String link = matcher.group(1);
//			System.out.println("already get link" + link);
//		}
//	}

}
