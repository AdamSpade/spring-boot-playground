package com.example.mydemoapp.services;

import com.example.mydemoapp.models.FileEntity;
import com.example.mydemoapp.models.FileInfo;
import com.example.mydemoapp.repositories.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    public FileService(final FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity storeFile(final MultipartFile file) throws IOException {
        final String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        byte[] fileBytes = file.getBytes();

        log.info("File name: {}", fileName);
        log.info("File size; {} bytes", fileBytes);
        log.info("Content type: {}", file.getContentType());

        final FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());

        return fileRepository.save(fileEntity);
    }

    public FileEntity retrieveFile(final long id) throws FileNotFoundException {
        return fileRepository.findById(id).orElseThrow(
                () -> new FileNotFoundException("File not found with id: " + id));
    }

    public List<FileInfo> getFileInfo() {
        List<FileEntity> files = fileRepository.findAll();
        return files.stream()
                .map(file -> new FileInfo(
                        file.getId(),
                        file.getFileName(),
                        file.getFileType()
                ))
                .collect(Collectors.toList());
    }

    /**
     * This method triggers the lazy loading of file data.
     *
     * @param id the file id
     * @return the file byte data as an array
     */
    public byte[] getFileData(final long id) {
        final FileEntity fileEntity =
                fileRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("File not found with id: " + id));
        return fileEntity.getData();
    }
}
