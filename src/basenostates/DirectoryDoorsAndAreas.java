package basenostates;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *Class to create areas(spaces, partitions) and doors.
 */

public final class DirectoryDoorsAndAreas {
  private static final Logger LOGGER =
          LoggerFactory.getLogger("fita1");

  private static final Logger LOGGER2 = LoggerFactory.getLogger("fita2");

  /**
   * array to safe all the doors
   * and be able to search for them.
   */
  private static ArrayList<Door> allDoors;
  /**
   * array to safe all
   * the areas and be able to search for them.
   */
  private static ArrayList<Area> allAreas;
  private static Area root = null;
  private DirectoryDoorsAndAreas() {

  }

  /**
   * function to intialize
   * all the areas and doors.
   */
  public static void makeDoorsAndAreas() {
    // Partitions:
    // building --> basement, groundFloor,
    // floor1, stair and exterior

    // Spaces:
    // basement --> parking
    // groundFloor --> hall, room1, room2
    // floor1 --> room3, corridor, iT
    // Every Space/Partition have the "id"
    // (to identify) and "fromSpace" (to know
    // who is the parent Area)


    Partitions building = new Partitions("building", null);
    Partitions basement = new Partitions("basement", building);
    Partitions groundFloor = new Partitions("groundFloor", building);
    Partitions floor1 = new Partitions("floor1", building);
    Spaces parking = new Spaces("parking", basement);
    Spaces stairs = new Spaces("stairs", building);
    Spaces room1 = new Spaces("room1", groundFloor);
    Spaces room2 = new Spaces("room2", groundFloor);
    Spaces room3 = new Spaces("room3", floor1);
    Spaces hall = new Spaces("hall", groundFloor);
    Spaces corridor = new Spaces("corridor", floor1);
    Spaces iT = new Spaces("iT", floor1);
    Spaces exterior = new Spaces("exterior", building);

    building.addChilds(new ArrayList<>(Arrays.asList(basement, groundFloor,
        floor1, exterior, stairs)));

    basement.addChilds(new ArrayList<>(Arrays.asList(parking)));

    groundFloor.addChilds(new ArrayList<>(Arrays.asList(room1, room2, hall)));

    floor1.addChilds(new ArrayList<>(Arrays.asList(corridor, iT)));

    allAreas = new ArrayList<>(Arrays.asList(building, groundFloor,
        floor1, parking, stairs, room1, room2, room3, hall, corridor, iT,
        basement, exterior)); // initilize the allAreas

    root = building;

    LOGGER2.debug("Creating clock");
    Clock clock = Clock.getInstance();
    // basement
    Door d1 = new Door("D1", exterior, parking,
            clock); // exterior, parking
    Door d2 = new Door("D2", stairs, parking, clock); // stairs, parking

    // ground floor
    Door d3 = new Door("D3", exterior, hall, clock); // exterior, hall
    Door d4 = new Door("D4", stairs, hall, clock); // stairs, hall
    Door d5 = new Door("D5", hall, room1, clock); // hall, room1
    Door d6 = new Door("D6", hall, room2, clock); // hall, room2

    // first floor
    Door d7 = new Door("D7", stairs, corridor,
            clock); // stairs, corridor
    Door d8 = new Door("D8", corridor, room3, clock); // corridor, room3
    Door d9 = new Door("D9", corridor, iT, clock); // corridor, iT

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8,
        d9)); // initalize allDoors
  }

  // function for search in all the doors by the id
  // return: door that matches the id
  public static Door findDoorById(final String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    LOGGER.warn("door with id " + id + " not found");
    return null; // otherwise we get a Java error
  }

  // function for search in all the areas by the id
  // return: area that matches the id
  public static Area findAreaById(final String id) {
    if (id.equals("ROOT")) {
      // Special id that means that the wanted area is the root.
      // This is because the Flutter app client doesn't know the
      // id of the root, differently from the simulator
      return root;
    }else{
      return root.findAreaById(id);
    }
  }

  // this is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {
    LOGGER.info("" + allDoors);
    return allDoors;
  }

  // gettes for the array allAreas
  // return: Array of all the areas
  public static ArrayList<Area> getAllAreas() {
    LOGGER.info("" + allAreas);
    return allAreas;
  }

}
