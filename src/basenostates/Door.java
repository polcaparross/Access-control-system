package basenostates;

import basenostates.requests.RequestReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *Doors class, we have on it the declaration.
 */

public class Door implements Observer {
  private static final Logger LOGGER = LoggerFactory.getLogger("fita1");
  private final String id;
  private final Area from;
  private final Area to;
  private boolean closed;
  private DoorState state;

  private long tiempoInicio;

  private final Clock clock;


  public Door(final String doorId, final Area sourceArea,
              final Area targetArea, final Clock reloj) {
    this.id = doorId;
    this.from = sourceArea;
    this.to = targetArea;
    closed = true;
    state = new Unlocked(this);
    this.clock = reloj;
  }

  public Clock getClock() {
    return clock;
  }
  public void processRequest(final RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      LOGGER.warn("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  public void setTiempoInicio(final Long tInicio) {
    this.tiempoInicio = tInicio;
  }
  @Override
  public void update(final Observable o, final Long dateTime) {
    if (States.UNLOCKED_SHORTLY.equals(state.getName())) {
      long currentTime = (Long) dateTime;
      long elapsedTime = currentTime - tiempoInicio;
      long umbral = 10000;
      LOGGER.debug("ELAPSED TIME: ");
      LOGGER.debug("" + elapsedTime / 1000);

      if (elapsedTime >= umbral) {
        LOGGER.debug("FIN UNLOCKED SHORTLY");
        clock.deleteObserver(this);
        clock.stopClock();
        setState(new Locked(this));
      }
    }
  }

  public void setState(final DoorState doorStates) {
    state = doorStates;
  }

  private void doAction(final String action) {
    switch (action) {
      case Actions.OPEN:
        state.open();
        break;
      case Actions.CLOSE:
        state.close();
        break;
      case Actions.LOCK:
        state.lock();
        break;
      case Actions.UNLOCK:
        state.unlock();
        break;
      case Actions.UNLOCK_SHORTLY:
        state.unlockShortly();
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  public boolean isClosed() {
    return closed;
  }

  public void setClosed(final boolean closedDoor) {
    this.closed = closedDoor; }

  public String getId() {
    return id;
  }
  public Area getTo() {
    return to;
  }
  public Area getFrom() {
    return from; }

  public String getStateName() {
    return state.getName();
  }

  @Override
  public String toString() {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }
}
