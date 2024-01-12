package basenostates;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This abstract class "contains" the Area objects,
 * which can be of two types: Space or Partition.
 */
public abstract class Area {
    private final String id;
    private ArrayList<Door> doors; // list of doors that the area has

    public Area(final String ident) {
        this.doors = new ArrayList<Door>();
        this.id = ident;
    }

    public String getId() {
        return id;
    } // getter of area's id

    public abstract Area getFrom(); // getter from the object's parent area

    /**
     * getter of the array of doors of the area.
     *
     * @return ArrayList<>().
     */
    public abstract ArrayList<Door> getDoors();

    /**
     * getter of the doors that the object area has access to.
     *
     * @return ArrayList<>().
     */
    public abstract ArrayList<Door> getDoorsGivingAccess();

    /**
     * function that search in all the area by the id.
     *
     * @param ident
     * @return Area
     */
    public abstract Area findAreaById(String ident);

    public void setDoors(final ArrayList<Door> doorsSet) {
        this.doors = doorsSet;
    }

    public abstract JSONObject toJson(int depth);
}
