package com.kernel360.file.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_id_gen")
    @SequenceGenerator(name = "file_id_gen", sequenceName = "file_file_no_seq")
    @Column(name = "file_no", nullable = false)
    private Long fileNo;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_key", nullable = false)
    private String fileKey;

    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;

    @Column(name = "reference_type", nullable = false, length = 50)
    private String referenceType;

    @Column(name = "reference_no", nullable = false)
    private Long referenceNo;

    private File(Long fileNo, String fileName, String fileKey, String fileUrl, String referenceType, Long referenceNo) {
        this.fileNo = fileNo;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.fileUrl = fileUrl;
        this.referenceType = referenceType;
        this.referenceNo = referenceNo;
    }

    public static File of(Long fileNo, String fileName, String fileKey, String fileUrl, String referenceType, Long referenceNo) {
        return new File(fileNo, fileName, fileKey, fileUrl, referenceType, referenceNo);
    }

    public static File of(Long fileNo, String fileName, String fileKey, String fileUrl) {
        return new File(fileNo, fileName, fileKey, fileUrl, null, null);
    }
}