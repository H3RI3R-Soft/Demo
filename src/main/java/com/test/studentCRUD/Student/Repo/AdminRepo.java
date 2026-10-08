package com.test.studentCRUD.Student.Repo;

import com.test.studentCRUD.Student.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}
