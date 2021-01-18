package pl.lodz.p.it.isdp.wm.exception;

import javax.persistence.NoResultException;
import pl.lodz.p.it.isdp.wm.model.Contractor;

public class ContractorException extends AppBaseException {

    static final public String KEY_CONTRACTOR_NUMBER_EXIST = "error.contractor.number.exist.problem";
    static final public String KEY_CONTRACTOR_WRONG_STATE = "error.contractor.wrong.state.problem";
    static final public String KEY_CONTRACTOR_NOT_EXISTS = "error.contractor.not.exists.problem";
    static final public String KEY_CONTRACTOR_HAS_STOCK = "error.contractor.has.stock.problem";

    private Contractor contractor;

    public Contractor getContractor() {
        return contractor;
    }

    private ContractorException(String message, Contractor contractor) {
        super(message);
        this.contractor = contractor;
    }

    private ContractorException(String message, Throwable cause, Contractor contractor) {
        super(message, cause);
        this.contractor = contractor;
    }

    private ContractorException(String message, Throwable cause) {
        super(message, cause);
    }

    static public ContractorException createExceptionContractorNotExists(NoResultException e) {
        return new ContractorException(KEY_CONTRACTOR_NOT_EXISTS, e);
    }

    static public ContractorException createExceptionWrongState(Contractor contractor) {
        return new ContractorException(KEY_CONTRACTOR_WRONG_STATE, contractor);
    }

    static public ContractorException createExceptionContractorNumberExists(Throwable cause, Contractor contractor) {
        return new ContractorException(KEY_CONTRACTOR_NUMBER_EXIST, cause, contractor);
    }

    static public ContractorException createExceptionDeleteContractor(Contractor contractor) {
        return new ContractorException(KEY_CONTRACTOR_HAS_STOCK, contractor);
    }

}
