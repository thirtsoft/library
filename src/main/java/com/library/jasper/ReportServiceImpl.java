package com.library.jasper;

import java.sql.SQLException;
import javax.sql.DataSource;

import com.library.utils.Constants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.util.*;


@Service(value = "reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    DataSource dataSource;

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public StringResult createReport(Report report) throws SQLException {
        try {
            Resource resource = resourceLoader.getResource("classpath:/reports/"+report.getName()+".jasper");
            InputStream reportputStream = resource.getInputStream();
            Map parameters = new HashMap();
            StringResult reportName = new StringResult();
            parameters.put("comId", resourceLoader.getResource("classpath:/reports/"+report.getName()+".jasper")
            .getFile().getAbsolutePath());
            Connection conn = this.dataSource.getConnection();
            byte[] reportBytes = JasperRunManager.runReportToPdf(reportputStream, parameters, conn);
            reportName.setName(report.getName()+"_"+System.currentTimeMillis()+".pdf");

            FileOutputStream fileOutputStream = new FileOutputStream(Constants.REPORT_RESULT_FOLDER_COMMANDE+reportName.getName());
            fileOutputStream.write(reportBytes);
            fileOutputStream.close();
            return  reportName;
        }catch (JRException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
