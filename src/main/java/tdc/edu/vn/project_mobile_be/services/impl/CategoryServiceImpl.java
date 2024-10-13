package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.customexception.EntityNotFoundException;
import tdc.edu.vn.project_mobile_be.commond.customexception.ExistsSlugException;
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

        if (categoryRepository.existsByCategorySlug(categoryRequestDTO.getCategorySlug())) {
            throw new ExistsSlugException("Slug đã tồn tại !");
        }


        ZonedDateTime releaseZonedDateTime = getReleaseZonedDateTime(categoryRequestDTO.getCategoryRelease());


        Category parent = getParentCategoryById(categoryRequestDTO.getParentId());

        Category category = new Category();
        category.setCategoryId(UUID.randomUUID());
        category.setCategoryName(categoryRequestDTO.getCategoryName());
        category.setCategorySlug(categoryRequestDTO.getCategorySlug());
        category.setParent(parent);
        category.setCategoryRelease(releaseZonedDateTime);

        if (category.getCategoryRelease().isAfter(ZonedDateTime.now())) {
            category.setCategoryStatus(categoryStatusRepository.findByCategoryStatusType(0).orElseThrow(() -> new EntityNotFoundException("Status không tồn tại !")));
        } else {
            category.setCategoryStatus(categoryStatusRepository.findByCategoryStatusType(1).orElseThrow(() -> new EntityNotFoundException("Status không tồn tại !")));
        }

        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponseDTO> getCategoryTree(int role) {
        // Lấy danh sách tất cả các danh mục gốc cùng với danh mục con
        List<Category> categoryList = categoryRepository.findAllRootCategoriesWithChildren();

        // Kiểm tra nếu danh sách rỗng, ném ngoại lệ
        if (categoryList.isEmpty()) {
            throw new EntityNotFoundException("Không có dữ liệu !");
        }

        // Xử lý tùy thuộc vào vai trò người dùng
        if (role == this.USER_ROLE_USER) {
            // Lọc cây danh mục cho người dùng thông thường
            categoryList.forEach(this::filterCategoryTree);

        } else if (role == this.USER_ROLE_ADMIN) {
            // Có thể thêm logic xử lý riêng cho admin, nếu cần
            // Ví dụ: Admin có thể thấy tất cả mà không lọc danh mục con
        }

        // Chuyển đổi danh sách Category sang CategoryResponseDTO
        return categoryList.stream()
                .map(category -> {
                    CategoryResponseDTO dto = new CategoryResponseDTO();
                    dto.toDto(category); // Giới hạn độ sâu trong phương thức toDto nếu cần
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Filter category tree
     * @param category
     * @return void
     */
    public void filterCategoryTree(Category category) {
        category.setChildren(category.getChildren()
                .stream()
                .filter(child -> child.getCategoryStatus() != null && child.getCategoryStatus().getCategoryStatusType() == 1
                        && child.getCategoryRelease() != null
                        && child.getCategoryRelease()
                        .isBefore(ZonedDateTime.now()))
                .collect(Collectors.toList()));
        category.getChildren().forEach(this::filterCategoryTree);
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
