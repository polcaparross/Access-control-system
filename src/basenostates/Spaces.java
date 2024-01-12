package basenostates;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *Spaces class, a space can have one or more doors.
 *It's a closed site by doors.
 */
public class Spaces extends Area {
  private Area from;

  // spaces constructor, id, area and partition assignments.

  public Spaces(final String ident, final Area fromId) {
    super(ident);
    this.from = fromId;
  }

  // from getter
  public Area getFrom() {
    return from;
  }

  // this function searchs and returns a
  // list of doors that are accessible for this space
  // return: ArrayList Door type with all doors
  @Override
  public ArrayList<Door> getDoorsGivingAccess() {
    // RETURN DOORS

    ArrayList<Door> doors = new ArrayList<>();
    ArrayList<Door> allDoors = new ArrayList<>();
    allDoors = DirectoryDoorsAndAreas.getAllDoors();

    for (int i = 0; i < allDoors.size(); i++) {
      if (allDoors.get(i).getTo() == this) {
        doors.add(allDoors.get(i));
      }
    }
    this.setDoors(doors);
    return doors;
  }

  // doors attribute getter
  @Override
  public ArrayList<Door> getDoors() {
    return this.getDoors();
  }

  @Override
  public Area findAreaById(final String id) {
    if (this.getId().equals(id)) {
      return this;
    }
    return null;
  }

  @Override
  public String getId() {
    return super.getId();
  }

  public JSONObject toJson(int depth) { // depth not used here
    JSONObject json = new JSONObject();
    json.put("class", "space");
    json.put("id", getId());
    JSONArray jsonDoors = new JSONArray();
    for (Door d : getDoorsGivingAccess()) {
      jsonDoors.put(d.toJson());
    }
    json.put("access_doors", jsonDoors);
    return json;
  }
}
