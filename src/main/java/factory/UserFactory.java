package factory;

import config.EmailConfig;
import config.EnvConfig;
import model.User;

public class UserFactory {

  public static User createDefaultUser(EnvConfig config) {
    String email = config.getEmail();

    if (email.isEmpty()) {
      try {
        email = EmailConfig.getEmailAddress();
      } catch (Exception e) {
        throw new RuntimeException("Failed to fetch email from API", e);
      }
    }

    String password = config.getPassword();

    return User.builder()
        .email(email)
        .password(password)
        .build();
  }
}
