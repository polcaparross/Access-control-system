package basenostates;

import java.util.ArrayList;
/**
 *UserGroup class that creates user groups in the system
 *for using it for the different roles.
 */

public class UserGroup {
  private final String id;

  private final Schedule schedule;
  private final ArrayList<String> actions;
  private final ArrayList<User> users;
  private final ArrayList<Area> areas;
  //constructor that initializes the attributes with the provided values
  public UserGroup(final String ident, final Schedule scheduleH,
                   final ArrayList<String> actionsId,
                   final ArrayList<User> usersId,
                   final ArrayList<Area> areasId) {
    this.id = ident;
    this.schedule = scheduleH;
    this.users = usersId;
    this.areas = areasId;
    this.actions = actionsId;
  }
  //all getters
  public ArrayList<User> getUsers() {
    return users;
  }

  public ArrayList<Area> getAreas() {
    return areas;
  }

  public ArrayList<String> getActions() {
    return actions;
  }

  public Schedule getSchedule() {
    return schedule;
  }
}
