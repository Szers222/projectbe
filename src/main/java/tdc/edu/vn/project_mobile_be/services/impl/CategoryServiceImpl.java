package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.ExistsSlugException;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateCategoryRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.responses.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.category.Category;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.CategoryStatusRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.CategoryService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends AbService<Category, UUID> implements CategoryService {

    public final int USER_ROLE_ADMIN = 0;
    public final int USER_ROLE_USER = 1;
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
     * @param categoryRequestDTO
     * @return Optional<Category>
     */
    @Override
    public Category createCategory(CreateCategoryRequestDTO categoryRequestDTO) {

        if (categoryRepository.existsBySlug(categoryRequestDTO.getSlug())) {
            throw new ExistsSlugException("Slug đã tồn tại !");
        }


        ZonedDateTime releaseZonedDateTime = getReleaseZonedDateTime(categoryRequestDTO.getRelease());


        Category parent = getParentCategoryById(categoryRequestDTO.getParentId());

        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName(categoryRequestDTO.getName());
        category.setSlug(categoryRequestDTO.getSlug());
        category.setParent(parent);
        category.setRelease(releaseZonedDateTime);

        if (category.getRelease().isAfter(ZonedDateTime.now())) {
            category.setStatus(categoryStatusRepository.findByType(0).orElseThrow(() -> new EntityNotFoundException("Status không tồn tại !")));
        } else {
            category.setStatus(categoryStatusRepository.findByType(1).orElseThrow(() -> new EntityNotFoundException("Status không tồn tại !")));
        }

        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponseDTO> getCategoryTree(int role) {
        List<Category> categoryList = categoryRepository.findAllRootCategoriesWithChildren();

        if (categoryList.isEmpty()) {
            throw new EntityNotFoundException("Không có dữ liệu !");
        }

        if (role == this.USER_ROLE_USER) {
            categoryList.forEach(category -> {
                filterCategoryTree(category);
            });

        } else if (role == this.USER_ROLE_ADMIN) {
            // Xử lý cho ADMIN nếu cần
            // Ví dụ: không lọc danh mục con nào, hoặc thực hiện một số xử lý khác
        }

        return categoryList.stream().map(category -> {
            CategoryResponseDTO dto = new CategoryResponseDTO();
            dto.toDto(category);  // Giới hạn độ sâu nếu cần
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Filter category tree
     * @param category
     * @return void
     */
    public void filterCategoryTree(Category category) {
        category.setChildrens(category.getChildrens().stream().filter(child -> child.getStatus() != null && child.getStatus().getType() == 1 && child.getRelease() != null && child.getRelease().isBefore(ZonedDateTime.now())).collect(Collectors.toList()));
        category.getChildrens().forEach(this::filterCategoryTree);
    }

    /**
     * Get release date time
     *
     * @param releaseTimestamp
     * @return ZonedDateTime
     */
    private ZonedDateTime getReleaseZonedDateTime(Timestamp releaseTimestamp) {
        if (releaseTimestamp != null) {
            LocalDateTime localDateTime = releaseTimestamp.toLocalDateTime();
            return localDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
        } else {
            return ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        }
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

}
