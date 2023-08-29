package lk.ijse.gdse.hostelManagement.bo.custom.impl;

import lk.ijse.gdse.hostelManagement.bo.custom.ReservationInfoBO;
import lk.ijse.gdse.hostelManagement.config.SessionFactoryConfig;
import lk.ijse.gdse.hostelManagement.dao.DAOFactory;
import lk.ijse.gdse.hostelManagement.dao.custom.QueryDAO;
import lk.ijse.gdse.hostelManagement.dto.ReservationProDTO;
import lk.ijse.gdse.hostelManagement.dto.tm.ReservationProTM;
import lk.ijse.gdse.hostelManagement.projection.ReservationProjection;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ReservtionInfoBOImpl implements ReservationInfoBO {
    private Session session;
    QueryDAO queryDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    @Override
    public List<ReservationProDTO> loadAll(String id) {
        try{
            session=SessionFactoryConfig.getInstance ().getSession ();
            queryDAO.setSession (session);
            List<ReservationProjection> list=queryDAO.loadAllInfo (id);
            List<ReservationProDTO> listDto=new ArrayList<>();
            for (ReservationProjection rs:list) {
                listDto.add(rs.toDto());
            }
            if(listDto != null) {
                return listDto;
            }
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}
