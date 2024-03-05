package com.kernel360.file.repository;

import com.kernel360.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepositoryJpa extends JpaRepository<File, Long> {
}
