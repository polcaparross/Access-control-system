package basenostates;

/**Before executing enable assertions :
 *<a
 * href="https://se-education.org/guides/tutorials/intellijUsefulSettings.html">
 * ...</a>
 */

public final class Main {
  private Main() {

  }
  public static void main(final String[] args) {
    DirectoryDoorsAndAreas.makeDoorsAndAreas();
    DirectoryUserGroups.makeUserGroup();
    WebServer.getInstance();
  }
}
