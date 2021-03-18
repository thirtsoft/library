package com.library.services;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    /**
     * Enregistre le fichier dans la BD
     *
     * @param file fichier au format souhaité
     */
    void store(MultipartFile file);

    void storeCategorieFile(MultipartFile file);

    void storeScategorieFile(MultipartFile file);

    // public String storeContratFile(MultipartFile file) throws IOException;
}