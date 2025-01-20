package com.example.mydemoapp.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * The data model object for files in the database.
 */
@Entity
@Data
@RequiredArgsConstructor
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "data", columnDefinition = "bytea")
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;
}
