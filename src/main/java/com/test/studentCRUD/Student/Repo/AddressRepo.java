package com.test.studentCRUD.Student.Repo;

import com.test.studentCRUD.Student.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
}
