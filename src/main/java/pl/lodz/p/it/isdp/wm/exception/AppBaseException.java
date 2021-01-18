package pl.lodz.p.it.isdp.wm.exception;

import javax.ejb.ApplicationException;
import javax.persistence.OptimisticLockException;

/**
 * Klasa bazowego wyjątku aplikacyjnego
 */
@ApplicationException(rollback = true)
public class AppBaseException extends Exception {

    static final public String KEY_OPTIMISTIC_LOCK = "error.optimistic.lock.problem";
    static final public String KEY_REPEATED_TRANSACTION_ROLLBACK = "error.repeated.transaction.rollback.problem";
    static final public String KEY_DATABASE_QUERY_PROBLEM = "error.database.query.problem";
    static final public String KEY_DATABASE_CONNECTION_PROBLEM = "error.database.connection.problem";
    static final public String KEY_ACTION_NOT_AUTHORIZED = "error.action.not.authorized.problem";
    static final public String KEY_OBJECT_WAS_EDITED = "error.object.was.edited.problem";

    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AppBaseException(String message) {
        super(message);
    }

    static public AppBaseException createExceptionForRepeatedTransactionRollback() {
        return new AppBaseException(KEY_REPEATED_TRANSACTION_ROLLBACK);
    }

    static public AppBaseException createExceptionDatabaseQueryProblem(Throwable e) {
        return new AppBaseException(KEY_DATABASE_QUERY_PROBLEM, e);
    }

    static public AppBaseException createExceptionDatabaseConnectionProblem(Throwable e) {
        return new AppBaseException(KEY_DATABASE_CONNECTION_PROBLEM, e);
    }

    static public AppBaseException createExceptionOptimisticLock(OptimisticLockException e) {
        return new AppBaseException(KEY_OPTIMISTIC_LOCK, e);
    }

    static public AppBaseException createExceptionNotAuthorizedAction() {
        return new AppBaseException(KEY_ACTION_NOT_AUTHORIZED);
    }

    static public AppBaseException createExceptionEditedObjectData() {
        return new AppBaseException(KEY_OBJECT_WAS_EDITED);
    }

}
