package com.fitcheck;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UploadedImageRepository extends JpaRepository<UploadedImage, Long> {

    List<UploadedImage> findByOwnerEmail(String ownerEmail);

    List<UploadedImage> findByOwnerEmailAndType(String ownerEmail, String type);
}