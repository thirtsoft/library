package com.library.services;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    /**
     * Enregistre le fichier dans la BD
     * @param file fichier au format souhaité
     */
    void store(MultipartFile file);
}