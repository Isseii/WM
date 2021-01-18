package pl.lodz.p.it.isdp.wm.exception;

import javax.persistence.NoResultException;
import pl.lodz.p.it.isdp.wm.model.Location;

public class LocationException extends AppBaseException {

    static final public String KEY_LOCATION_EXIST = "error.location.exist.problem";
    static final public String KEY_LOCATION_WRONG_STATE = "error.location.wrong.state.problem";
    static final public String KEY_LOCATION_IS_NOT_EMPTY = "error.location.is.not.empty.problem";
    static final public String KEY_LOCATION_WRONG_WEIGHT = "error.location.wrong.weight.problem";
    static final public String KEY_LOCATION_NOT_EXIST = "error.location.not.exist.problem";

    private Location location;

    public Location getLocation() {
        return location;
    }

    private LocationException(String message, Location location) {
        super(message);
        this.location = location;
    }

    private LocationException(String message, Throwable cause, Location location) {
        super(message, cause);
        this.location = location;
    }

    private LocationException(String message, Throwable cause) {
        super(message, cause);
    }

    static public LocationException createExceptionLocationExists(Throwable cause, Location location) {
        return new LocationException(KEY_LOCATION_EXIST, cause, location);
    }

    static public LocationException createExceptionWrongState(Location location) {
        return new LocationException(KEY_LOCATION_WRONG_STATE, location);
    }

    static public LocationException createExceptionLocationIsNotEmpty(Location location) {
        return new LocationException(KEY_LOCATION_IS_NOT_EMPTY, location);
    }

    static public LocationException createExceptionLocationOverweighted(Location location) {
        return new LocationException(KEY_LOCATION_WRONG_WEIGHT, location);
    }

    static public LocationException createExceptionLocationNotExists(NoResultException e) {
        return new LocationException(KEY_LOCATION_NOT_EXIST, e);
    }

}
