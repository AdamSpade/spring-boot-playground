package com.example.mydemoapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileInfo {
    private long id;
    private String fileName;
    private String fileType;
}
