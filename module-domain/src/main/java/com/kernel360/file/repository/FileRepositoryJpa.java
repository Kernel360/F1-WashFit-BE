package com.kernel360.file.repository;

import com.kernel360.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepositoryJpa extends JpaRepository<File, Long> {
    List<File> findByReferenceTypeAndReferenceNo(String referenceType, Long referenceNo);
}
