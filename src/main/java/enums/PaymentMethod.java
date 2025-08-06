package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
  DIRECT_BANK_TRANSFER("Direct bank transfer"),
  CHECK_PAYMENTS("Check payments"),
  COD("Cash on delivery");

  private final String method;
}
