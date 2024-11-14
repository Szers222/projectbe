package tdc.edu.vn.project_mobile_be.interfaces.reponsitory;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tdc.edu.vn.project_mobile_be.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    boolean existsByUserEmail(String userEmail);

    @Query("SELECT u from User u where u.userEmail = :userEmail")
    Optional<User> findByUserEmail(@Param("userEmail") String userEmail);

//    @Query("SELECT u FROM User u JOIN FETCH u.roles r w ")
//    Optional<User> findByEmailWithRoles(@Param("userId") UUID userId);

//    @Query(value = "SELECT COUNT(*) > 0 FROM users_roles WHERE user_id = UNHEX(:userId)", nativeQuery = true)
//    boolean existsInUsersRolesByUserId(@Param("userId") String userId);

}

