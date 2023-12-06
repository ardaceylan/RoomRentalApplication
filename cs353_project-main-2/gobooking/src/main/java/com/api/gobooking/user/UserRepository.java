package com.api.gobooking.user;

import com.api.gobooking.user.appuser.AppUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /*
     * Adds user to database and returns the automatically generated id.
     */
    @Transactional
    public int save(User user){
        String userSql = "INSERT INTO " +
                "\"user\" (name, surname, email, password, birth_date, role) " +
                "VALUES (:name, :surname, :email, :password, :birthdate, :role)";

        Query userQuery = entityManager.createNativeQuery(userSql);

        userQuery.setParameter("name", user.getName());
        userQuery.setParameter("surname", user.getSurname());
        userQuery.setParameter("email", user.getEmail());
        userQuery.setParameter("password", user.getPassword());
        userQuery.setParameter("birthdate", user.getBirthDate());
        userQuery.setParameter("role", user.getRole().toString());

        userQuery.executeUpdate();

        User insertedUser = findByEmail(user.getEmail()).get();

        return insertedUser.getId();
    }

    public List<User> findAll(){
        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }

    public Optional<User> findById(Integer id){
        String jpql = "SELECT u FROM User u WHERE u.id = :id";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("id", id);

        List<User> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    public Optional<User> findByEmail(String email){
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("email", email);

        List<User> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    @Transactional
    public void updateUser(User user){
        String updateUserSql = "UPDATE \"user\" " +
                "SET name = :name, surname = :surname, password = :password, birth_date = :birthdate " +
                "WHERE user_id = :id";
        Query updateUserQuery = entityManager.createNativeQuery(updateUserSql);
        updateUserQuery.setParameter("name", user.getName());
        updateUserQuery.setParameter("surname", user.getSurname());
        updateUserQuery.setParameter("password", user.getPassword());
        updateUserQuery.setParameter("birthdate", user.getBirthDate());
        updateUserQuery.setParameter("id", user.getId());

        updateUserQuery.executeUpdate();
    }

    @Transactional
    public void deleteById(Integer id) {
        String sql = "DELETE FROM \"user\" WHERE user_id = :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
