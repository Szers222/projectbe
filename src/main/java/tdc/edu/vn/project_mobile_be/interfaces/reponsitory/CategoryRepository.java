package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.dtos.responses.CategoryResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.category.Category;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> , JpaSpecificationExecutor<Category> {
    boolean existsByCategorySlug(String slug);

    Category save(Category category);

    CategoryResponseDTO findByCategorySlug(String slug);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL")
    List<Category> findAllRootCategoriesWithChildren();


}
