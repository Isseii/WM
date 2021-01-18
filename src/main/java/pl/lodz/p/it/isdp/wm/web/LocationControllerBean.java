package pl.lodz.p.it.isdp.wm.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import pl.lodz.p.it.isdp.wm.dto.LocationDTO;
import pl.lodz.p.it.isdp.wm.ejb.endpoint.LocationEndpoint;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;

@Named(value = "locationControllerBean")
@SessionScoped
public class LocationControllerBean implements Serializable {

    @EJB
    private LocationEndpoint locationEndpoint;

    private int lastActionMethod = 0;

    private LocationDTO selectedLocationDTO;

    public LocationControllerBean() {
    }

    public LocationDTO getSelectedLocationDTO() {
        return selectedLocationDTO;
    }

    public void setSelectedLocationDTO(LocationDTO selectedLocationDTO) {
        this.selectedLocationDTO = selectedLocationDTO;
    }

    void createNewLocation(LocationDTO locationDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = locationDTO.hashCode() + 1;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = locationEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                locationEndpoint.createNewLocation(locationDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (locationEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void selectLocationForChange(LocationDTO locationDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = locationDTO.hashCode() + 2;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = locationEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                selectedLocationDTO = locationEndpoint.rememberSelectedLocationInState(locationDTO.getLocationSymbol()); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (locationEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void deleteLocation(LocationDTO locationDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = locationDTO.hashCode() + 3;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = locationEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                locationEndpoint.deleteLocation(locationDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (locationEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void editLocation(LocationDTO locationDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = locationDTO.hashCode() + 4;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = locationEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                locationEndpoint.editLocation(locationDTO); // wywołanie metody biznesowej punktu dostępowego EJB
                endpointCallCounter--;
            } while (locationEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.createExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

}
