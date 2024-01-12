package basenostates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *Extend of the abstract class DoorState.
 *It indicates that the door is locked.
 *When the door is locked we can't open it.
 */

public class Locked extends DoorState {
  private static final Logger LOGGER = LoggerFactory.getLogger("fita1");

  // sets the state of the door to Locked
  public Locked(final Door door) {
    super(door);
    setName(States.LOCKED);
  }

  // If we try to open in Locked state, an error will appear
  public void open() {
    LOGGER.warn("Can't open the door " + this.getDoor().getId()
            + " because it's locked");
  }

  // If we try to close in Locked state, an error will appear
  public void close() {
    LOGGER.warn("Can't close the door " + this.getDoor().getId()
            + " because it's locked");
  }

  // If we try to lock in Locked state, an error will appear because
  // it's already locked
  public void lock() {
    LOGGER.warn("Can't lock the door " + this.getDoor().getId()
            + " because it's already locked");
  }

  // We can unlock the door and we change the state to Unlocked
  public void unlock() {
    if (this.getName().equals(States.LOCKED)) {
      this.setName(States.UNLOCKED);
      this.getDoor().setState(new Unlocked(this.getDoor()));
    } else {
      LOGGER.warn("Can't unlock door " + this.getDoor().getId()
              + " because it's already unlock");
    }
  }

  // We can unlock shortly (10 seconds) the door so we change the state to
  // unlocked_shortly
  // Then a timer will start for 10 seconds, after this time the function will
  // check if the door is open or closed
  public void unlockShortly() {
    if (this.getName().equals(States.LOCKED)) {
      this.setName(States.UNLOCKED_SHORTLY);
      this.getDoor().setState(new UnlockedShortly(this.getDoor()));
    } else {
      LOGGER.warn("Can't unlock_shortly door "
          + this.getDoor().getId() + " because it's already unlock");
    }
  }
}
