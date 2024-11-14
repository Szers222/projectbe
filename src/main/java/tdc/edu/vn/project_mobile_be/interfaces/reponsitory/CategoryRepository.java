package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.category.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> , JpaSpecificationExecutor<Category> {

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL")
    List<Category> findAllRootCategoriesWithChildren();

    @Query("SELECT c FROM Category c")
    Page<Category> findAllCategories(Pageable pageable);


    @Query("SELECT c FROM Category c WHERE c.categoryId = :categoryId")
    Category findByCategoryId(@Param("categoryId") UUID categoryId);




    //    void deleteAllByStatusAndDeletionDateLessThanEqual(int status, LocalDate deletionDate);

    @Query("SELECT c FROM Category c join c.categoryStatus cs WHERE cs.categoryStatusType = :statusType AND c.deletionDate = :deletionDate")
    List<Category> deleteByStatusAndDeletionDate(@Param("statusType") int statusType, @Param("deletionDate") LocalDate deletionDate);

}

