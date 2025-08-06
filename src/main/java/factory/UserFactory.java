package factory;

import config.EmailConfig;
import config.EnvConfig;
import model.User;

public class UserFactory {

  private final EnvConfig config;

  public UserFactory(EnvConfig config) {
    this.config = config;
  }

  public User createDefaultUser() {
    String email = config.getEmail();

    if (email.isEmpty()) {
      try {
        email = EmailConfig.getEmailAddress();
      } catch (Exception e) {
        throw new RuntimeException("Failed to fetch email from API", e);
      }
    }

    String password = config.getPassword();

    return new User(email, password);
  }
}
