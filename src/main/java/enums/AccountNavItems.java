package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountNavItems {
  DASHBOARD("Dashboard"),
  ORDERS("Orders"),
  SUBSCRIPTIONS("Subscriptions"),
  DOWNLOADS("Downloads"),
  ADDRESSES("Addresses"),
  ACCOUNT_DETAILS("Account details"),
  LOGOUT("Logout");

  private final String item;
}
