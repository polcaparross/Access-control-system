package basenostates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *Unlocked shortly class, we use that class when
 *we want to unlock the door for a short period of time.
 */
public class UnlockedShortly extends DoorState {
  private static final Logger LOGGER =
          LoggerFactory.getLogger("fita1");
  //basic class constructor
  public UnlockedShortly(final Door door) {
    super(door);
    setName(States.UNLOCKED_SHORTLY);
    door.setTiempoInicio(System.currentTimeMillis());
    door.getClock().iniciarReloj();
    door.getClock().addObserver(this.getDoor());
  }
  //we open the door in the Unlocked_shortly state
  public void open() {
    if (this.getDoor().isClosed()) {
      this.getDoor().setClosed(false);
    } else {
      LOGGER.warn("Can't open door " + this.getDoor().getId()
              + " because it's already open");
    }
  }

  /**
   * we close the door in the unlocked_shortly state.
   */

  public void close() {
    if (!this.getDoor().isClosed()) {
      this.getDoor().setClosed(true);
    } else {
      LOGGER.warn("Can't close door " + this.getDoor().getId()
              + " because it's already closed");
    }
  }

  /**
   * the door is unlocked shortly,
   * it will lock automatically after 10 s.
   */

  public void lock() {
    LOGGER.warn("Can't lock the door " + this.getDoor().getId()
            + " because it's already locked");
  }
  /**
   * the door is unlocked, we cant unlock it.
   */
  public void unlock() {
    LOGGER.warn("Can't unlock door " + this.getDoor().getId()
            + " because it's already unlocked");
  }
  public void unlockShortly() {
    LOGGER.warn("Can't unlock_shortly door " + this.getDoor().getId()
            + " because it's already unlocked");
  }
}
