package com.library.services;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {

    String exportReport(Long id) throws FileNotFoundException, JRException;
}
