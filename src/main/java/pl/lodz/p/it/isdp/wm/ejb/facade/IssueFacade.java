/*
 * Projekt końcowy realizowany w ramach studiów podyplomowych Nowoczesne aplikacje biznesowe Java EE edycja 8
 */
package pl.lodz.p.it.isdp.wm.ejb.facade;

import java.sql.SQLNonTransientConnectionException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.model.Issue;

@Stateless
@RolesAllowed("Warehouse")
public class IssueFacade extends AbstractFacade<Issue> {

    @PersistenceContext(unitName = "pl.lodz.p.it.spjava_WMPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IssueFacade() {
        super(Issue.class);
    }

    @Override
    public void create(Issue entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (DatabaseException e) {
            if (e.getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        } catch (PersistenceException e) {
            throw AppBaseException.createExceptionDatabaseQueryProblem(e);
        }
    }

    @RolesAllowed({"Office", "Warehouse"})
    public List<Issue> findIssuesForProduct(String productSymbol) throws AppBaseException {
        List<Issue> issues;
        try {
            TypedQuery<Issue> tq = em.createNamedQuery("Issue.findByProduct", Issue.class);
            tq.setParameter("p", productSymbol);
            issues = tq.getResultList();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        }
        return issues;
    }

    @RolesAllowed({"Office", "Warehouse"})
    public List<Issue> findIssuesForLocation(String locationSymbol) throws AppBaseException {
        List<Issue> issues;
        try {
            TypedQuery<Issue> tq = em.createNamedQuery("Issue.findByLocation", Issue.class);
            tq.setParameter("loc", locationSymbol);
            issues = tq.getResultList();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        }
        return issues;
    }

    @RolesAllowed({"Office", "Warehouse"})
    public List<Issue> findIssuesForContractorNumber(String contractorNumber) throws AppBaseException {
        List<Issue> issues;
        try {
            TypedQuery<Issue> tq = em.createNamedQuery("Issue.findByContractor", Issue.class);
            tq.setParameter("cn", contractorNumber);
            issues = tq.getResultList();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        }
        return issues;
    }

}
