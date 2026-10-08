package com.test.studentCRUD.Student.Repo;

import com.test.studentCRUD.Student.Entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByEmail(String email);

    Login findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
