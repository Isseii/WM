package pl.lodz.p.it.isdp.wm.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.isdp.wm.dto.LocationDTO;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.model.LocationType;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "createNewLocationPageBean")
@RequestScoped
public class CreateNewLocationPageBean {

    private LocationDTO locationDTO;

    private List<LocationType> listLocationTypes;

    @Inject
    private LocationControllerBean locationControllerBean;

    public CreateNewLocationPageBean() {
    }

    public LocationDTO getLocationDTO() {
        return locationDTO;
    }

    public void setLocationDTO(LocationDTO locationDTO) {
        this.locationDTO = locationDTO;
    }

    public List<LocationType> getListLocationTypes() {
        return listLocationTypes;
    }

    @PostConstruct
    public void init() {
        LocationType[] listAllLocationTypes = LocationType.values();
        for (LocationType locationType : listAllLocationTypes) {
            locationType.setLoactionTypeI18NValue(ContextUtils.getI18NMessage(locationType.getLocationTypeKey()));
        }
        listLocationTypes = new ArrayList<>(Arrays.asList(listAllLocationTypes));
        locationDTO = new LocationDTO();
    }

    public String createNewLocationAction() {
        try {
            locationControllerBean.createNewLocation(locationDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(CreateNewLocationPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "main";
    }

}
