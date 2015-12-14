package myjavaai.utils;

import java.util.Observer;
import java.util.Optional;

/**
 * Created by Hallvard on 30.11.2015.
 */
public abstract class UnitListener<T> {

    private Optional<T> t = Optional.empty();

    public UnitListener(T t) {
        this.t = Optional.of(t);
    }

    public final Optional<T> getFocal() {
        return t;
    }

    public abstract void unitDestroyed();
    public abstract void TaskCompleted();
    public abstract void TaskInterrupted();
}
