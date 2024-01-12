package basenostates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *Extend of the abstract class DoorState.
 *It indicates that the door is Unlocked.
 *When the door is unlocked we can open, close and lock it.
 */
public class Unlocked extends DoorState {
  private static final Logger LOGGER = LoggerFactory.getLogger("fita1");
  //sets the door state to unlocked
  public Unlocked(final Door door) {
    super(door);
    setName(States.UNLOCKED);
  }

  /**
   * sets the attribute closed of the door to false so the door is now open.
   */
  public void open() {
    if (this.getDoor().isClosed() && this.getName().equals(States.UNLOCKED)) {
      this.getDoor().setClosed(false);
    } else {
      LOGGER.warn("Can't open door " + this.getDoor().getId()
              + " because it's already open");
    }
  }

  public void close() {
    if (!this.getDoor().isClosed()) {
      this.getDoor().setClosed(true);
    } else {
      LOGGER.warn("Can't close door " + this.getDoor().getId()
              + " because it's already closed");
    }
  }

  public void lock() {
    if (this.getDoor().isClosed()) {
      this.getDoor().setState(new Locked(this.getDoor()));
    } else {
      LOGGER.warn("Can't lock door " + this.getDoor().getId()
              + " because it's open");
    }
  }

  public void unlock() {
    LOGGER.warn("Can't unlock door " + this.getDoor().getId()
            + " because it's already unlocked");
  }

  public void unlockShortly() {
    System.out.println("Can't unlock_shortly door " + this.getDoor().getId()
            + " because it's already unlocked");
  }
}

