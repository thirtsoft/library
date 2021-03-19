package com.library.services.impl;

import com.library.entities.CommandeClient;
import com.library.services.CommandeClientService;
import com.library.services.ReportService;
import com.library.utils.Constants;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CommandeClientService commandeClientService;

    @Override
    public String exportReport(Long id) throws FileNotFoundException, JRException {

        String path = Constants.REPORT_RESULT_FOLDER_COMMANDE;

        Optional<CommandeClient> commandeClient = (commandeClientService.findCommandeClientById(id));


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("chefService", commandeClient.get().getClient().getChefService());
        /*
        parameters.put("chefService", commandeClient.get().getClient().getChefService());
        parameters.put("dateCommande", commandeClient.get().getDateCommande());
        parameters.put("totalCommande", commandeClient.get().getTotalCommande());
        parameters.put("numeroCommande", commandeClient.get().getNumeroCommande());
        parameters.put("quantite", commandeClient.get().getLcomms().get(1).getQuantite());
        parameters.put("designation", commandeClient.get().getLcomms().get(3).getProduit().getDesignation());
        parameters.put("prixCommande", commandeClient.get().getLcomms().get(2).getPrixCommande());
*/
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:FactureCommande.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        //JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "//FactureCommande.pdf");

        return "report generated in path : " + path;
    }
}
