package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Departments {

  AUTOMOBILES("Automobiles & Motorcycles"), CAR("Car Electronics"), PHONE_ACCESS(
      "Mobile Phone Accessories"),
  COMPUTER_OFFICE("Computer & Office"), TABLET_ACCESS("Tablet Accessories"),
  CONSUMER_ELECTRONIC("Consumer Electronics"), ELECTRONIC_COMPONENT(
      "Electronic Components & Supplies"),
  PHONE_TELE("Phones & Telecommunications"), WATCHES("Watches");

  private final String type;
}
