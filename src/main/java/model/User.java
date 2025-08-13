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
  private String email;
  private String password;
  private String phoneNumber;


  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public static User defaultUser() {
    EnvConfig config = new EnvConfig();
    String email = config.getEmail();
    String password = config.getPassword();

    if (email.isEmpty()) {
      try {
        email = EmailConfig.getEmailAddress();
      } catch (Exception e) {
        throw new RuntimeException("Failed to fetch email from API", e);
      }
    }
    return User.builder()
        .email(email)
        .password(password)
        .build();
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public String getFullAddress() {
    return address + " " + city + " " + country;
  }
}
