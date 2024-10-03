package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.DTO.IdCardDTO;
import tdc.edu.vn.project_mobile_be.entities.idcards.IdCard;
import tdc.edu.vn.project_mobile_be.services.IdCardService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/idcards")
public class IdCardController {

    @Autowired
    private IdCardService idCardService;

    @GetMapping
    public List<IdCardDTO> getAllIdCards() {
        List<IdCard> idCards = idCardService.getAllIdCards();
        return idCards.stream().map(IdCardDTO::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdCardDTO> getIdCardById(@PathVariable String id) {
        IdCard idCard = idCardService.getIdCardById(id);
        return idCard != null ? ResponseEntity.ok(IdCardDTO.fromEntity(idCard)) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public IdCardDTO createIdCard(@RequestBody IdCard idCard) {
        IdCard createdIdCard = idCardService.saveIdCard(idCard);
        return IdCardDTO.fromEntity(createdIdCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdCardDTO> updateIdCard(@PathVariable String id, @RequestBody IdCard idCard) {
        IdCard existingIdCard = idCardService.getIdCardById(id);
        if (existingIdCard != null) {
            idCard.setIdCardId(id); // Đảm bảo ID không thay đổi
            return ResponseEntity.ok(IdCardDTO.fromEntity(idCardService.updateIdCard(idCard)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdCard(@PathVariable String id) {
        idCardService.deleteIdCard(id);
        return ResponseEntity.noContent().build();
    }
}
