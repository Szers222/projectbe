package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.entities.idcards.IdCard;
import tdc.edu.vn.project_mobile_be.repositories.IdCardRepository;
import tdc.edu.vn.project_mobile_be.services.IdCardService;

import java.util.List;
import java.util.Optional;

@Service
public class IdCardServiceImpl implements IdCardService {

    @Autowired
    private IdCardRepository idCardRepository;

    @Override
    public List<IdCard> getAllIdCards() {
        return idCardRepository.findAll();
    }

    @Override
    public IdCard getIdCardById(String id) {
        Optional<IdCard> optional = idCardRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public IdCard saveIdCard(IdCard idCard) {
        return idCardRepository.save(idCard);
    }

    @Override
    public IdCard updateIdCard(IdCard idCard) {
        return idCardRepository.save(idCard);
    }

    @Override
    public void deleteIdCard(String id) {
        idCardRepository.deleteById(id);
    }
}
