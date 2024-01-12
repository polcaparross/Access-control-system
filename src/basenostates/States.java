package basenostates;

/**
 *Door states, static declarations.
 *(Only one assignation for all objects of the class.)
 */
public final class States {
  private States() {

  }
  static final String LOCKED = "locked";
  static final String UNLOCKED = "unlocked";
  static final String UNLOCKED_SHORTLY = "unlocked_shortly";
  static final String PROPPED = "propped";
}
