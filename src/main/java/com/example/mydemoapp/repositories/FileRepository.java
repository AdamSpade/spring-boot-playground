package com.example.mydemoapp.repositories;

import com.example.mydemoapp.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {

}
