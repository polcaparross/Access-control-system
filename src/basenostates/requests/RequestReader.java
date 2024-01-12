package basenostates.requests;

import basenostates.User;
import basenostates.Door;
import basenostates.DirectoryDoorsAndAreas;
import basenostates.DirectoryUserGroups;
import basenostates.UserGroup;
import basenostates.Area;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestReader implements Request {
  private final String credential; // who
  private final String action;     // what
  private final LocalDateTime now; // when
  private final String doorId;     // where
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // why not authorized
  private String doorStateName;
  private boolean doorClosed;


  public RequestReader(final String credentialId, final String actionId,
                       final LocalDateTime hourId, final String doorIdent) {
    this.credential = credentialId;
    this.action = actionId;
    this.doorId = doorIdent;
    reasons = new ArrayList<>();
    this.now = hourId;
  }

  public void setDoorStateName(final String name) {
    doorStateName = name;
  }

  public String getAction() {
    return action;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void addReason(final String reason) {
    reasons.add(reason);
  }


  @Override
  public String toString() {
    if (userName == null) {
      userName = "unknown";
    }
    return "Request{"
            + "credential=" + credential
            + ", userName=" + userName
            + ", action=" + action
            + ", now=" + now
            + ", doorID=" + doorId
            + ", closed=" + doorClosed
            + ", authorized=" + authorized
            + ", reasons=" + reasons
            + "}";
  }

  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    return json;
  }

  // see if the request is authorized and
  // put this into the request, then send it to the door.
  // if authorized, perform the action.
  public void process() {
    User user = DirectoryUserGroups.findUserByCredential(credential);
    Door door = DirectoryDoorsAndAreas.findDoorById(doorId);
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    // this sets the boolean authorize attribute of the request
    door.processRequest(this);
    // even if not authorized we process the request,
    // so that if desired we could log all
    // the requests made to the server as part of
    // processing the request
    doorClosed = door.isClosed();
  }

  // the result is put into the request object plus, if not authorized, why not,
  // only for testing
  private void authorize(final User user, final Door door) {
    if (user == null) {
      authorized = false;
      addReason("user doesn't exists");
    } else {
      // TO DO: get the who, where, when and what
      // in order to decide, and if not
      // authorized add the reason(s)

      UserGroup userGroup =
              DirectoryUserGroups.findUserGroupByUser(user.getCredential());

      List<Area> areas = userGroup.getAreas();
      List<String> actions = userGroup.getActions();
      ArrayList<User> users = userGroup.getUsers();

      boolean areaTrue = false;
      if (areas.size() == 1 && areas.get(0).getId().equals("building")) {
        areaTrue = true;
      } else {
        for (Area area : areas) {
          if (door.getTo().getFrom() == area || door.getTo() == area) {
            areaTrue = true;
          }
        }
      }

      boolean isSchedule = userGroup.getSchedule().isSchedule(now);

      boolean actionsTrue = actions.contains(action);

      if (areaTrue && isSchedule && actionsTrue) {
        authorized = true;
      } else {
        authorized = false;
      }

      if (!authorized) {
        if (!areaTrue) {
          reasons.add("User " + user.getName() + " has no access to this area");
        } else if (!isSchedule) {
          reasons.add("User " + user.getName()
                  + " has no access in this schedule");
        } else if (!actionsTrue) {
          reasons.add("User " + user.getName() + " can't do " + action);
        }
      }
    }
  }
}

