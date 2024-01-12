package basenostates;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that implements the observer design pattern, we need it to observe
 * how changes the clock we instanciate for the autorized hours to our users.
 */
abstract class Observable {
    private ArrayList<Observer> observers = new ArrayList<>();
    private boolean changed = false;

    private boolean deteniendoReloj = false;

    public boolean isChanged() {
        return changed;
    }
    public synchronized void addObserver(final Observer ob) {
        if (!observers.contains(ob)) {
            observers.add(ob);
        }
    }

    /**
     * we delete the observed object.
     * @param ob
     */
    public synchronized void deleteObserver(final Observer ob) {
        Iterator<Observer> iterator = observers.iterator();
        while (iterator.hasNext()) {
            Observer obs = iterator.next();
            if (obs == ob) {
                iterator.remove();
            }
        }
    }

    public void setChanged(final boolean cambiado) {
        this.changed = cambiado;
    }
    /**
     * we notify the changes in the observed object.
     */
    public synchronized void notifyObservers(final Long dateTime) {
        if (isChanged()) {
            Iterator<Observer> iterator = new ArrayList<>(observers).iterator();
            while (iterator.hasNext()) {
                Observer ob = iterator.next();
                if (deteniendoReloj) {
                    break;
                } else {
                    ob.update(this, dateTime);
                }
            }
        }
    }
}
