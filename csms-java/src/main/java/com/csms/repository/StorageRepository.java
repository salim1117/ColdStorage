package com.csms.repository;

import com.csms.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    List<Storage> findByStatus(Integer status);
    Optional<Storage> findByNameAndIdNot(String name, Long id);
    long countByNameAndIdNot(String name, Long id);
    long countByName(String name);
    long countByStatus(Integer status);
}
