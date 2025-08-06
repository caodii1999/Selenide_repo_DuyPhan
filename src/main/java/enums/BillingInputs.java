package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BillingInputs {
  FIRST_NAME("billing_first_name"),
  LAST_NAME("billing_last_name"),
  ADDRESS("billing_address_1"),
  CITY("billing_city"),
  PHONE("billing_phone"),
  EMAIL("billing_email");

  private final String inputs;
}
