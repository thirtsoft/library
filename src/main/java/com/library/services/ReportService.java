package com.library.services;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import net.sf.jasperreports.engine.JRException;

public interface ReportService {

    public String exportReport(Long id) throws FileNotFoundException, JRException;
}
