package com.api.gobooking.user.admin;

import com.api.gobooking.user.UserRepository;
import com.api.gobooking.user.appuser.AppUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class AdminRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private UserRepository userRepository;

    @Transactional
    public boolean save(Admin admin){

        int userId = userRepository.save(admin);

        String adminSql = "INSERT INTO " +
                "admin (user_id, admin_role) " +
                "VALUES (:id, :admin_role)";

        Query adminQuery = entityManager.createNativeQuery(adminSql);

        adminQuery.setParameter("id", userId);
        adminQuery.setParameter("admin_role", admin.getAdminRole().toString());

        adminQuery.executeUpdate();

        return true;
    }

    public Optional<Admin> findById(Integer id){
        String sql = "select * from \"user\" u natural join admin a where u.user_id = :id";
        Query query = entityManager.createNativeQuery(sql, Admin.class);

        query.setParameter("id", id);

        List<Admin> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    public Optional<Admin> findByEmail(String email){
        String sql = "select * from \"user\" u natural join admin a where u.email = :email";
        Query query = entityManager.createNativeQuery(sql, Admin.class);

        query.setParameter("email", email);

        List<Admin> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    public List<Admin> findAll() {
        String sql = "select * from \"user\" natural join admin";
        Query query = entityManager.createNativeQuery(sql, Admin.class);
        return query.getResultList();
    }

    @Transactional
    public void updateAdmin(Admin admin){
        userRepository.updateUser(admin);

        String updateAdminSql = "UPDATE admin SET admin_role = :admin_role WHERE user_id = :id";
        Query updateAdminQuery = entityManager.createNativeQuery(updateAdminSql);
        updateAdminQuery.setParameter("admin_role", admin.getAdminRole());
        updateAdminQuery.setParameter("id", admin.getId());

        updateAdminQuery.executeUpdate();
    }

    @Transactional
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
