
package com.Admin.dashboard_admin.BUS;
import javax.swing.JPanel;
import com.Admin.dashboard_admin.DAO.DAOProfile_ad;
import com.Admin.dashboard_admin.DTO.DTOProfile_ad;

public class BusProfile_ad {

    private DAOProfile_ad daoProfile;

    public BusProfile_ad() {
        daoProfile = new DAOProfile_ad();
    }

    public DTOProfile_ad showProfile(String adminID,JPanel panelUpload) {
        return daoProfile.showProfile_ad(adminID, panelUpload);
    }
    
    public boolean updateProfile(DTOProfile_ad profile){
        return daoProfile.updateProfile(profile);
    }
    
    public String getAdminName(String adminID) {
        return daoProfile.getAdminName(adminID);
    }
}
