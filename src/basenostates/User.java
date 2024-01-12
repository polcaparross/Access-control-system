package basenostates;

/**
 *User class, we create on it a user with
 *his name and his credential.
 */
public class User {
  private final String name;
  private final String credential;

  // class constructor, we give to the object his name and credential
  public User(final String nameId, final String credentialId) {
    this.name = nameId;
    this.credential = credentialId;
  }

  // getters
  public String getCredential() {
    return credential;
  }

  public String getName() {
    return name;
  }
  //all user's information to a concatenated string
  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}
