package tdc.edu.vn.project_mobile_be.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.commond.customexception.MultipleFieldsNullOrEmptyException;
import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.post.PostUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.post.PostResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.post.Post;
import tdc.edu.vn.project_mobile_be.interfaces.service.PostService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping(value = {"/post", "/post/"})
    public ResponseEntity<ResponseData<?>> createPost(
            @RequestBody @Valid PostCreateRequestDTO params,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }

        Post data = postService.createPost(params);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Post đã tạo thành công!", data);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PutMapping(value = {"/post/{postId}", "/pos/"})
    public ResponseEntity<ResponseData<?>> updatePost(
            @RequestBody @Valid PostUpdateRequestDTO params,
            @PathVariable UUID postId,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new MultipleFieldsNullOrEmptyException(errorMessages);
        }

        Post data = postService.updatePost(params, postId);
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Post đã cập nhật thành công!", data);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/post/{postId}", "/post/{postId}/"})
    public ResponseEntity<ResponseData<?>> deletePost(@PathVariable UUID postId) {
        boolean isDeleted = postService.deletePost(postId);
        if (isDeleted) {
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Post đã xóa thành công!", null);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            ResponseData<?> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Post không tồn tại!", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = {"/posts/news", "/posts/news/"})
    public ResponseEntity<ResponseData<List<PostResponseDTO>>> getNewsPosts() {
        List<PostResponseDTO> data = postService.findAllNewPost();
        ResponseData<List<PostResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", data);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/posts/user/{userId}", "/posts/user/{userId}/"})
    public ResponseEntity<ResponseData<List<PostResponseDTO>>> getPostsByUserId(@PathVariable UUID userId) {
        List<PostResponseDTO> data = postService.findAllPostByUserId(userId);
        ResponseData<List<PostResponseDTO>> responseData = new ResponseData<>(HttpStatus.OK, "Success", data);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = {"/posts", "/posts/"})
    public ResponseEntity<ResponseData<PagedModel<EntityModel<PostResponseDTO>>>> getPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "role") int role,
            PagedResourcesAssembler<PostResponseDTO> assembler) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("productPriceSale").ascending());
        Page<PostResponseDTO> postResponseDTOS = postService.findAllPost(role, pageable);

        if (postResponseDTOS.isEmpty()) {
            ResponseData<PagedModel<EntityModel<PostResponseDTO>>> responseData = new ResponseData<>(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        PagedModel<EntityModel<PostResponseDTO>> pagedModel = assembler.toModel(postResponseDTOS);

        ResponseData<PagedModel<EntityModel<PostResponseDTO>>> responseData = new ResponseData<>(HttpStatus.OK, "Success", pagedModel);
        return ResponseEntity.ok(responseData);
    }
}
