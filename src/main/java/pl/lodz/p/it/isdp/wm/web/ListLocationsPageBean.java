package pl.lodz.p.it.isdp.wm.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import pl.lodz.p.it.isdp.wm.dto.LocationDTO;
import pl.lodz.p.it.isdp.wm.ejb.endpoint.LocationEndpoint;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.model.LocationType;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "listLocationsPageBean")
@ViewScoped
public class ListLocationsPageBean implements Serializable {

    @EJB
    private LocationEndpoint locationEndpoint;

    @Inject
    private LocationControllerBean locationControllerBean;

    private List<LocationDTO> listLocations;

    private List<LocationType> listLocationTypes;

    private DataModel<LocationDTO> dataModelLocations;

    public ListLocationsPageBean() {
    }

    public DataModel<LocationDTO> getDataModelLocations() {
        return dataModelLocations;
    }

    public List<LocationType> getListLocationTypes() {
        return listLocationTypes;
    }

    @PostConstruct
    public void initListLocations() {
        try {
            listLocations = locationEndpoint.listLocations();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListLocationsPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelLocations = new ListDataModel<>(listLocations);
        LocationType[] listAllLocationTypes = LocationType.values();

        for (LocationType locationType : listAllLocationTypes) {
            locationType.setLoactionTypeI18NValue(ContextUtils.getI18NMessage(locationType.getLocationTypeKey()));
        }
        listLocationTypes = new ArrayList<>(Arrays.asList(listAllLocationTypes));

    }

    public String editLocationAction(LocationDTO locationDTO) {
        try {
            locationControllerBean.selectLocationForChange(locationDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListLocationsPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "editLocation";
    }

    public String deleteSelectedLocationAction(LocationDTO locationDTO) {
        if (locationDTO.isEmptyLocation() == true) {
            try {
                locationControllerBean.selectLocationForChange(locationDTO);
            } catch (AppBaseException ex) {
                Logger.getLogger(ListLocationsPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
                return null;
            }
        }
        initListLocations();
        return "deleteLocation";
    }
}
