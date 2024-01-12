package basenostates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *Propped class, the door passes to propped state when the
 *door is unlocked_shortly during 10s, the door is open
 *and no one closes it.
 */
public class Propped extends DoorState {
  private static final Logger LOGGER = LoggerFactory.getLogger("fita1");

  // class constructor
  public Propped(final Door doorId) {
    super(doorId);
    setName(States.PROPPED);
  }
  // open, close, lock and unlock function
  // for the propped state
  // we can't open the door because it's open,
  // can't unlock or lock it because it's propped
  // we can close it only
  public void open() {
    LOGGER.warn("Can't open the door " + this.getDoor().getId()
            + " because it's already open");
  }

  public void close() {
    this.getDoor().setClosed(true);
    this.getDoor().setState(new Locked(this.getDoor()));
  }

  public void lock() {
    LOGGER.warn("Can't lock the door " + this.getDoor().getId()
            + " because it's propped");
  }

  public void unlock() {
    LOGGER.warn("Can't unlock the door " + this.getDoor().getId()
            + " because it's propped");
  }
  public void unlockShortly() {
    LOGGER.warn("Can't unlock_shortly the door " + this.getDoor().getId()
            + " because it's propped");
  }
}
