package model;

import config.EmailConfig;
import config.EnvConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private String firstName;
  private String lastName;
  private String companyName;
  private String country;
  private String address;
  private int zipCode;
  private String city;
  @Builder.Default
  private String email = initEmail();
  @Builder.Default
  private String password = initPassword();
  private String phoneNumber;


  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  private static String initEmail() {
    String email = EnvConfig.getEmail();
    if (email.isEmpty()) {
      try {
        email = EmailConfig.getEmailAddress();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return email;
  }

  private static String initPassword() {
    return EnvConfig.getPassword();
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public String getFullAddress() {
    return address + " " + city + " " + country;
  }
}
