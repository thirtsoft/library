package com.library.jasper;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service(value = "reportService")
public interface ReportService {

    public StringResult createReport(Report report) throws SQLException;
}
