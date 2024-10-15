package tdc.edu.vn.project_mobile_be.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.ValidateException;
import tdc.edu.vn.project_mobile_be.dtos.ErrorResponseDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateIdCardRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.IdCardResponseDTO;
import tdc.edu.vn.project_mobile_be.interfaces.service.IdCardService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/idcards")
public class IdCardController {

    @Autowired
    private IdCardService idCardService;

    //Them du lieu idCard
    @PostMapping
    public ResponseEntity<ResponseData<IdCardResponseDTO>> createIdCard(
            @RequestBody @Valid CreateIdCardRequestDTO idCardRequestDTO,
             BindingResult bindingResult) {
        if(bindingResult.hasErrors() ){
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            String errorString = "";
            for (String key : errors.keySet()){
                errorString += key + errors.get(key);
            }
            throw  new ValidateException(errorString);
        }
        IdCardResponseDTO createdIdCard = idCardService.createIdCard(idCardRequestDTO);
        ResponseData<IdCardResponseDTO> responseData =
                new ResponseData<>(HttpStatus.CREATED, "Thêm mới thành công", createdIdCard);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    //Lay tat ca danh sach IdCard
    @GetMapping
    public ResponseEntity<ResponseData<List<IdCardResponseDTO>>> getAllIdCards() {
        //Lay tat ca danh sach IdCard
        List<IdCardResponseDTO> idCards = idCardService.getAllIdCards();
        ResponseData<List<IdCardResponseDTO>> responseData =
                new ResponseData<>(
                        HttpStatus.OK, //Trang thai HTTP 200 OK
                        "Lấy tất cả thành công", //Messger
                        idCards  //Danh sach cac idCard
                );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    //Xoa du lieu theo CardId
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<String>> deleteIdCard(
            @PathVariable("id") UUID cardId){
        idCardService.deleteIdCard(cardId);
        ResponseData<String> responseData = new ResponseData<>(
                HttpStatus.OK,//Trang thai HTTP 200 OK
                "Xóa thành công", //Messger
                "Thẻ ID với ID: " + cardId + " đã được xóa." // cardId
        );
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    //Cap nhat
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<IdCardResponseDTO>> updateIdCard(
            @PathVariable("id") UUID cardId,
            @RequestBody CreateIdCardRequestDTO idCardRequestDTO
            ) {
        IdCardResponseDTO updateIdCard = idCardService.
                updateIdCard(cardId, idCardRequestDTO);
        ResponseData<IdCardResponseDTO> responseData = new ResponseData<>(
                HttpStatus.OK, //Trang thai Http 200 OK
                "Cap nhat thanh cong", //Messger
                updateIdCard // UpdateIdCard
        );
        return new ResponseEntity<>(responseData,HttpStatus.OK);

    }
    //Lay ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<IdCardResponseDTO>> getIdCardById
    (@PathVariable("id") UUID cardId) {
        IdCardResponseDTO idCardResponseDTO = idCardService.getIdCardById(cardId);
        ResponseData<IdCardResponseDTO> responseData =
                new ResponseData<>(
                        HttpStatus.OK, //Trang thai Http 200 OK
                        "Lay du lieu thanh cong", //Messger
                        idCardResponseDTO // UpdateIdCard
                );
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }


}
