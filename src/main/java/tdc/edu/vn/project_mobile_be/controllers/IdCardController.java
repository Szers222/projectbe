package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.entities.idcards.IdCard;
import tdc.edu.vn.project_mobile_be.services.IdCardService;

import java.util.List;

@RestController
@RequestMapping("/api/idcards")
public class IdCardController {

    @Autowired
    private IdCardService idCardService;

    @GetMapping
    public List<IdCard> getAllIdCards() {
        return idCardService.getAllIdCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdCard> getIdCardById(@PathVariable String id) {
        IdCard idCard = idCardService.getIdCardById(id);
        return idCard != null ? ResponseEntity.ok(idCard) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public IdCard createIdCard(@RequestBody IdCard idCard) {
        return idCardService.saveIdCard(idCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdCard> updateIdCard(@PathVariable String id, @RequestBody IdCard idCard) {
        IdCard existingIdCard = idCardService.getIdCardById(id);
        if (existingIdCard != null) {
            idCard.setIdCardId(id);  // Đảm bảo ID không thay đổi
            return ResponseEntity.ok(idCardService.updateIdCard(idCard));
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
