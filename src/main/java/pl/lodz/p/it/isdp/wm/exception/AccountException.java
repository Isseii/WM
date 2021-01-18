package pl.lodz.p.it.isdp.wm.exception;

import javax.persistence.NoResultException;
import pl.lodz.p.it.isdp.wm.model.Account;

public class AccountException extends AppBaseException {

    static final public String KEY_ACCOUNT_LOGIN_EXIST = "error.account.login.exist.problem";
    static final public String KEY_ACCOUNT_EMAIL_EXIST = "error.account.email.exist.problem";
    static final public String KEY_ACCOUNT_WRONG_STATE = "error.account.wrong.state.problem";
    static final public String KEY_ACCOUNT_ACTIVE_STATE = "error.account.active.state.problem";
    static final public String KEY_ACCOUNT_NOT_EXISTS = "error.account.not.exists.problem";
    static final public String KEY_ACCOUNT_WRONG_PASSWORD = "error.account.wrong.password.problem";
    static final public String KEY_DELETE_ACCOUNT_AUTHORIZED = "error.delete.account.authorized.problem";
    static final public String KEY_ACCOUNT_ALREADY_AUTHORIZED = "error.account.already.authorized.problem";
    static final public String KEY_ACCOUNT_WRONG_ANSWER = "error.account.wrong.answer.problem";
    static final public String KEY_ACCOUNT_CHANGE_ACCESS_LEVEL = "error.account.has.relations.problem";
    static final public String KEY_ACCOUNT_ALREADY_CHANGED_ACCESS_LEVEL = "error.account.already.changed.access.level.problem";

    private Account account;

    public Account getAccount() {
        return account;
    }

    private AccountException(String message) {
        super(message);
    }

    private AccountException(String message, Account account) {
        super(message);
        this.account = account;
    }

    private AccountException(String message, Throwable cause, Account account) {
        super(message, cause);
        this.account = account;
    }

    private AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    static public AccountException createExceptionLoginExists(Throwable cause, Account account) {
        return new AccountException(KEY_ACCOUNT_LOGIN_EXIST, cause, account);
    }

    static public AccountException createExceptionEmailExists(Throwable cause, Account account) {
        return new AccountException(KEY_ACCOUNT_EMAIL_EXIST, cause, account);
    }

    static public AccountException createExceptionWrongState(Account account) {
        return new AccountException(KEY_ACCOUNT_WRONG_STATE, account);
    }

    static public AccountException createExceptionForChangeActiveState(Account account) {
        return new AccountException(KEY_ACCOUNT_ACTIVE_STATE, account);
    }

    static public AccountException createExceptionAccountNotExists(NoResultException e) {
        return new AccountException(KEY_ACCOUNT_NOT_EXISTS, e);
    }

    static public AccountException createExceptionWrongPassword(Account account) {
        return new AccountException(KEY_ACCOUNT_WRONG_PASSWORD, account);
    }

    static public AccountException createExceptionDeleteAuthorizedAccount(Account account) {
        return new AccountException(KEY_DELETE_ACCOUNT_AUTHORIZED, account);
    }

    static public AccountException createExceptionAccountAlreadyAuthorized(Account account) {
        return new AccountException(KEY_ACCOUNT_ALREADY_AUTHORIZED, account);
    }

    static public AccountException createExceptionWrongAnswer(Account account) {
        return new AccountException(KEY_ACCOUNT_WRONG_ANSWER, account);
    }

    static public AccountException createExceptionForChangeAccessLevel(Throwable cause, Account account) {
        return new AccountException(KEY_ACCOUNT_CHANGE_ACCESS_LEVEL, cause, account);
    }

    static public AccountException createExceptionAccessLevelAlreadyChanged() {
        return new AccountException(KEY_ACCOUNT_ALREADY_CHANGED_ACCESS_LEVEL);
    }
}
