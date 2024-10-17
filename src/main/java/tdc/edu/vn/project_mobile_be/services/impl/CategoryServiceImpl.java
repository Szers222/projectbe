package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.InvalidRoleException;
import tdc.edu.vn.project_mobile_be.dtos.requests.CategoryCreateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.CategoryUpdateRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.entities.status.CategoryStatus;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryStatusRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CategoryService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends AbService<Category, UUID> implements CategoryService {

    public final int USER_ROLE_ADMIN = 0;
    public final int USER_ROLE_USER = 1;
    public final int CATEGORY_STATUS_ACTIVE = 1;
    public final int CATEGORY_STATUS_DELETE = 2;
    public final int CATEGORY_STATUS_INACTIVE = 0;
    public final int CATEGORY_DELETE_AFTER_DAYS = 5;
    /**
     * Inject CategoryRepository
     */
    @Autowired
    private CategoryRepository categoryRepository;
    /**
     * Inject CategoryStatusRepository
     */
    @Autowired
    private CategoryStatusRepository categoryStatusRepository;

    /**
     * Create category
     *
     * @param params
     * @return Optional<Category>
     */
    @Override
    public Category createCategory(CategoryCreateRequestDTO params) {

        LocalDateTime releaseDateTime;

        if (params.getCategoryRelease() == null) {
            releaseDateTime = LocalDateTime.now();

        } else {
            releaseDateTime = params.getCategoryRelease().atStartOfDay();
        }

        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        CategoryStatus status = getStatus(params.getStatusId());

        if (status == null) {
            throw new EntityNotFoundException("Trạng thái không tồn tại !");
        }

        Category parent = getParentCategoryById(params.getParentId());
        if (parent == null) {
            throw new EntityNotFoundException("Parent không tồn tại !");
        }

        Category category = params.toEntity();
        category.setCategoryId(UUID.randomUUID());
        category.setParent(parent);
        category.setCategoryStatus(status);
        category.setCategoryRelease(releaseTimestamp);

        System.console().printf("category12321321: " + category);

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(CategoryUpdateRequestDTO params, UUID categoryId) {

        LocalDateTime releaseDateTime;

        if (params.getCategoryRelease() == null) {
            releaseDateTime = LocalDateTime.now();

        } else {
            releaseDateTime = params.getCategoryRelease().atStartOfDay();
        }

        Timestamp releaseTimestamp = Timestamp.valueOf(releaseDateTime);

        CategoryStatus status = getStatus(params.getStatusId());

        if (status == null) {
            throw new EntityNotFoundException("Trạng thái không tồn tại !");
        }

        Category parent = getParentCategoryById(params.getParentId());
        if (parent == null) {
            throw new EntityNotFoundException("Parent không tồn tại !");
        }

        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new EntityNotFoundException("Category không tồn tại !"));
        category.setCategoryName(params.getCategoryName());
        category.setParent(parent);
        category.setCategoryStatus(status);
        category.setCategoryRelease(releaseTimestamp);
        return categoryRepository.save(category);
    }


    @Override
    public List<CategoryResponseDTO> getCategories(int role, Pageable pageable) {

        if (role == this.USER_ROLE_USER) {
            List<Category> categoryList = new ArrayList<>();
            categoryList = categoryRepository.findAllRootCategoriesWithChildren();
            if (categoryList.isEmpty()) {
                throw new EntityNotFoundException("Không có dữ liệu !");
            }
            categoryList.forEach(this::filterCategoryTree);
            return categoryList.stream().map(category -> {
                CategoryResponseDTO dto = new CategoryResponseDTO();
                dto.toDto(category);
                return dto;
            }).collect(Collectors.toList());
        } else if (role == this.USER_ROLE_ADMIN) {
            Page<Category> categoryPage = categoryRepository.findAllCategories(pageable);

            if (categoryPage.isEmpty()) {
                throw new EntityNotFoundException("Không có dữ liệu !");
            }
            return categoryPage.stream().map(category -> {
                CategoryResponseDTO dto = new CategoryResponseDTO();
                dto.toDto(category, role);
                return dto;
            }).collect(Collectors.toList());
        } else {
            throw new InvalidRoleException("Bạn không có quyền truy cập !");
        }
    }


    @Override
    public boolean deleteCategory(UUID categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new EntityNotFoundException("Category không tồn tại !");
        }
        Category category = categoryOptional.get();

        category.setCategoryStatus(categoryStatusRepository.findByCategoryStatusType(this.CATEGORY_STATUS_DELETE));
        category.setDeletionDate(LocalDate.now().plusDays(this.CATEGORY_DELETE_AFTER_DAYS));
        categoryRepository.save(category);
        return true;
    }



    /**
     * Filter category tree
     * @param category
     * @return void
     */
    public void filterCategoryTree(Category category) {
        category.setChildren(category.getChildren().stream().filter(child -> child.getCategoryStatus() != null && child.getCategoryStatus().getCategoryStatusType() == 1 && child.getCategoryRelease().before(Timestamp.valueOf(LocalDateTime.now())) || child.getCategoryRelease().equals(Timestamp.valueOf(LocalDateTime.now()))).collect(Collectors.toList()));
        category.getChildren().forEach(this::filterCategoryTree);
    }


    /**
     * Get parent category by id
     *
     * @param parentId
     * @return Category
     */
    private Category getParentCategoryById(UUID parentId) {
        return parentId != null ? categoryRepository.findById(parentId).orElseThrow(() -> new EntityNotFoundException("Parent không tồn tại!")) : null;
    }

    private CategoryStatus getStatus(UUID statusId) {
        return categoryStatusRepository.findByCategoryStatusId((statusId)) != null ? categoryStatusRepository.findByCategoryStatusId((statusId)) : null;
    }

}
