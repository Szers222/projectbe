package tdc.edu.vn.project_mobile_be.services;

import tdc.edu.vn.project_mobile_be.entities.idcards.IdCard;
import java.util.List;

public interface IdCardService {
    List<IdCard> getAllIdCards();
    IdCard getIdCardById(String id);
    IdCard saveIdCard(IdCard idCard);
    IdCard updateIdCard(IdCard idCard);
    void deleteIdCard(String id);
}
