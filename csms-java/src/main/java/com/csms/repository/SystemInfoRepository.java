package com.csms.repository;

import com.csms.entity.SystemInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemInfoRepository extends JpaRepository<SystemInfo, Long> {
    Optional<SystemInfo> findByMetaField(String metaField);
}
