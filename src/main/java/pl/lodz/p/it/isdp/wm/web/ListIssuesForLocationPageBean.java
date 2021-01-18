package pl.lodz.p.it.isdp.wm.web;

import java.io.Serializable;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import pl.lodz.p.it.isdp.wm.dto.IssueDTO;
import pl.lodz.p.it.isdp.wm.ejb.endpoint.IssueEndpoint;
import pl.lodz.p.it.isdp.wm.exception.AppBaseException;
import pl.lodz.p.it.isdp.wm.web.utils.ContextUtils;

@Named(value = "listIssuesForLocationPageBean")
@ViewScoped
public class ListIssuesForLocationPageBean implements Serializable {

    @EJB
    private IssueEndpoint issueEndpoint;

    private List<IssueDTO> listIssues;
    private DataModel<IssueDTO> dataModelIssues;

    private List<String> locations;
    private DataModel<String> dataModelLocations;

    private IssueDTO issueDTO;
    private TimeZone timeZone;

    public ListIssuesForLocationPageBean() {
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<IssueDTO> getListIssues() {
        return listIssues;
    }

    public void setListIssues(List<IssueDTO> listIssues) {
        this.listIssues = listIssues;
    }

    public IssueDTO getIssueDTO() {
        return issueDTO;
    }

    public void setIssueDTO(IssueDTO issueDTO) {
        this.issueDTO = issueDTO;
    }

    public TimeZone getTimeZone() {
        timeZone = TimeZone.getDefault();
        return timeZone;
    }

    @PostConstruct
    public void initListIssues() {
        try {
            locations = issueEndpoint.listLocations();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListIssuesForLocationPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelLocations = new ListDataModel<>(locations);
        issueDTO = new IssueDTO();
    }

    public String showListAction() {
        try {
            listIssues = issueEndpoint.listIssuesForLocation(issueDTO.getLocationSymbol());
            dataModelIssues = new ListDataModel<>(listIssues);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListIssuesForLocationPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        return null;
    }

}
