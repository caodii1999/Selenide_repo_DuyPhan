package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Pages {

  HOME("home"), ABOUT_US("about-us"), SHOP("shop"), OFFERS("offers"), BLOG("blog"), CONTACT_US(
      "contact"), CHECKOUT("checkout"), ORDER_STATUS("/order-received/");

  private final String pageName;


}
