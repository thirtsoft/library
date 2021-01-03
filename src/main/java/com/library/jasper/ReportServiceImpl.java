package com.library.jasper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service(value = "reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    DataSource dataSource;

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public StringResult createReport(Report report) throws SQLException {
        try {
            Resource resource = resourceLoader.getResource("classpath://reports/"+report.getName()+".jasper");
            InputStream reportStream = resource.getInputStream();
            Map parameters = new HashMap();
            StringResult reportName = new StringResult();
        }
        return null;
    }
}
