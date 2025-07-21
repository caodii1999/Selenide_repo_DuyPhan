package helper;

public class Constants {

	public static final String WEB_URL = "https://demo.testarchitect.com/";
	public static final String PASSWORD_URL = "https://demo.testarchitect.com/my-account/lost-password/?show-reset-form=true&action=newaccount";

	public static enum Departments {
		AUTOMOBILES("Automobiles & Motorcycles"), CAR("Car Electronics"), PHONE_ACCESS("Mobile Phone Accessories"),
		COMPUTER_OFFICE("Computer & Office"), TABLET_ACCESS("Tablet Accessories"),
		CONSUMER_ELECTRONIC("Consumer Electronics"), ELECTRONIC_COMPONENT("Electronic Components & Supplies"),
		PHONE_TELE("Phones & Telecommunications"), WATCHES("Watches");

		private final String name;

		Departments(String name) {
			this.name = name;
		}

		public String getDepartmentName() {
			return name;
		}
	}

}
