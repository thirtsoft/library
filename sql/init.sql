/* CATEGORY */
INSERT INTO category(id,code,designation) VALUES(1,'CAT01','Ordinateur pordtable');
INSERT INTO category(id,code,designation) VALUES(2,'CAT02','Téléphone portable');
INSERT INTO category(id,code,designation) VALUES(3,'CAT03','Boisson');
INSERT INTO category(id,code,designation) VALUES(4,'CAT04','Eau minérale');
INSERT INTO category(id,code,designation) VALUES(5,'CAT05','Nescafé');

/* SUBCATEGORY */
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(1,'SUBCAT01','Ordinateur pordtable HP',1);
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(2,'SUBCAT02','Ordinateur pordtable DELL',1);
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(2,'SUBCAT03','Téléphone portable IPhone',2);
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(1,'SUBCAT04','Téléphone portable Samsung',2);
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(2,'SUBCAT05','Boisson Gazeuse',3);
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(2,'SUBCAT06','Bierree',3);
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(2,'SUBCAT07','Casamançaise',4);
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(2,'SUBCAT08','Kirène',4);
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(2,'SUBCAT09','Gold Intenso',5);
INSERT INTO subcategory(id,code,libelle,cat_id) VALUES(2,'SUBCAT010','Nescafé',5);

/* PRODUCT */
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'123456789','Product-01','HP elitebook core i7 vPro',760000.0, 790000.0,800000.0, 12,5,1);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'459789','Product-02','HP Probook core i5',320000.0, 500000.0, 510000.0, 12,5,1);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'678912346','Product-03','Dell Promodel core i7',540000.0, 720000.0, 740000.0, 12,5,2);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'810987654','Product-04','HP Iphone 14s',460000.0, 660000.0,690000.0, 10,2,3);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'5789985','Product-05','Iphone 12s',320000.0, 530000.0,560000.0 8,3);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'12345678915','Product-06','Samsung A23',11000.0, 125000.0,135000.0 16,5,4);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'012345678915','Product-07','Samsung A32',130000.0, 145000.0,150000.0, 16,5,4);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'80987654','Product-08','Coca cola',680.0, 800.0,900.0, 30,10,5);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'51789985','Product-09','Fanta',680.0, 800.0,900.0, 30,10,5);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'112345678915','Product-010','Malta',890.0, 1250.0,1250.0, 16,5,6);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'22345678915','Product-011','Malta 001',890.0, 1250.0,1250.0, 16,5,6);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'52789985','Product-012','Casamançaise 1L',360.0,500.0,500.0, 8, 2,7);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'42345678915','Product-013','Casamançaise 5L',760.0, 1100.0,1200.0, 16,5,7);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'62345678915','Product-014','Kirène 1L',390.0, 500.0,500.0, 16,5,8);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'53789985','Product-015','Kirène 5L',890.0, 1250.0,1250.0, 16,5,8);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'72345678915','Product-016','Gold Intenso PM',1800.0, 2500.0,2500.0, 16,5,9);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'82345678915','Product-017','Gold Intenso GM',5500.0, 6000.0,6000.0, 16,5,9);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'92345678915','Product-018','Nescafé PM',680.0, 900.0,900.0, 16,5,10);
INSERT INTO product(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'102345678915','Product-019','Nescafé GM',1100.0, 1900.0,1900.0, 16,5,10);

/* FOURNISSEUR */
INSERT INTO fournisseur(id,address,code,email,fax,message,mobile,numero_compte,raison_social,subject,telephone) VALUES(1,'','','contact@wokite.net','','','779440310','12345678-BOA','Wokite','','');
INSERT INTO fournisseur(id,address,code,email,fax,message,mobile,numero_compte,raison_social,subject,telephone) VALUES(1,'','','support@sat.com','','','779440310','987654321-BNDE','SAT','','');
INSERT INTO fournisseur(id,address,code,email,fax,message,mobile,numero_compte,raison_social,subject,telephone) VALUES(1,'','','contact@fiit.com','','','77440310','23456789-CBEAO','FIIT','','');
INSERT INTO fournisseur(id,address,code,email,fax,message,mobile,numero_compte,raison_social,subject,telephone) VALUES(1,'','','contact@sunuchauffeur.com','','','779440310','12346543-ECOBANK','Sunuchauffeur','','');
INSERT INTO fournisseur(id,address,code,email,fax,message,mobile,numero_compte,raison_social,subject,telephone) VALUES(1,'','','fournisseur05.net','','','77440310','20FFA','Fournisseur05','','');

/* CLIENT */
INSERT INTO client(id,address,code_client,email,message,mobile,raison_social,subject) VALUES(1,'','','contact@wokite.net','','779440310','Wokite','');
INSERT INTO client(id,address,code_client,email,message,mobile,raison_social,subject) VALUES(1,'','','contact@sunuchauffeur.com','','777643217','Sunuchauffeur','');
INSERT INTO client(id,address,code_client,email,message,mobile,raison_social,subject) VALUES(1,'','','contact@parfumdp.sn','','768643217','ParfumD&P','');
INSERT INTO client(id,address,code_client,email,message,mobile,raison_social,subject) VALUES(1,'','','support@funteks.com','','788643217','Funteks','');
INSERT INTO client(id,address,code_client,email,message,mobile,raison_social,subject) VALUES(1,'','','client05@gmail.com','','778543217','Client05','');

/* ROLES */
INSERT INTO roles(id,name) VALUES(1,'ROLE_VENDEUR');
INSERT INTO roles(id,name) VALUES(1,'ROLE_GERANT');
INSERT INTO roles(id,name) VALUES(1,'ROLE_MANAGER');
INSERT INTO roles(id,name) VALUES(1,'ROLE_ADMIN');

/* UTILISATEURS */
INSERT INTO utilisateur(id,name,username,email,password,photo,isActive) VALUES(1,'Admin','admin','admin@gmail.com','$2a$10$R44dGnwuk2cMQFQXc35fXOER2c8XLC6Sll4j/fqjepLplg4gBG.sK','',true);
INSERT INTO utilisateur(id,name,username,email,password,photo,isActive) VALUES(1,'Vendeur','vendeur','vendeur@gmail.com','$2a$10$v5RaZqqWEs.iupW7r.oErOSqlb4CJjWUXY5XepZ4O8Vt.lOYQiDMi','',true);
INSERT INTO utilisateur(id,name,username,email,password,photo,isActive) VALUES(1,'Gerant','gerant','gerant@gmail.com','$2a$10$1Ch4l3YxBNzsQXTOLflqmOnxLQbp9XwGkSeTEmcucCtHvcYauXzo2','',true);
INSERT INTO utilisateur(id,name,username,email,password,photo,isActive) VALUES(1,'Manageur','manageur','manageur@gmail.com','$2a$10$QCtMpd6M1ShbWcnDGrIYzO1hfQc0FS.EWrt68hDLe2mh64gEYVHqe','',true);





/*

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setActive(true);
        utilisateur.setName("Mawdo");
        utilisateur.setUsername("mawdo");
        utilisateurRepository.save(utilisateur);

        Utilisateur vendor = new Utilisateur();
        vendor.setActive(true);
        vendor.setName("Alpha");
        vendor.setUsername("alpha");
        utilisateurRepository.save(vendor);

        Commande commande = new Commande(1452L, 250000.5, 10000.5, "Cash", "VALIDEE", new Date());
        commande.setClient(client);
        commande.setUtilisateur(utilisateur);
        commandeRepository.save(commande);

        Commande commande2 = new Commande(14522L, 220000.5, 20000.5, "Wave", "PAYEE", new Date());
        commande2.setClient(client02);
        commande2.setUtilisateur(vendor);
        commandeRepository.save(commande2);

        Commande commande3 = new Commande(1453L, 230000.5, 30000.5, "OrangeMoney", "VALIDEE", new Date());
        commande3.setClient(client03);
        commande3.setUtilisateur(utilisateur);
        commandeRepository.save(commande3);

        Commande commande4 = new Commande(1454L, 240000.5, 40000.5, "FreeMoney", "ENCOURS", new Date());
        commande4.setClient(client04);
        commande4.setUtilisateur(vendor);
        commandeRepository.save(commande4);

        Commande commande5 = new Commande(1455L, 250000.5, 50000.5, "Credit card", "VALIDEE", new Date());
        commande5.setClient(client04);
        commande5.setUtilisateur(utilisateur);
        commandeRepository.save(commande5);

        LigneCommande ligneCommande = new LigneCommande(1L, 5, 15000.5, 15000.0, product);
        ligneCommande.setCommande(commande);
        ligneCommandeRepository.save(ligneCommande);
        LigneCommande ligneCommande1 = new LigneCommande(2L, 11, 17000.5, 11000.0, product02);
        ligneCommande1.setCommande(commande);
        ligneCommandeRepository.save(ligneCommande1);

        LigneCommande ligneCommande2 = new LigneCommande(3L, 8, 18000.5, 11000.0, product03);
        ligneCommande2.setCommande(commande2);
        ligneCommandeRepository.save(ligneCommande2);

        LigneCommande ligneCommande3 = new LigneCommande(4L, 6, 16000.5, 13000.0, product04);
        ligneCommande3.setCommande(commande3);
        ligneCommandeRepository.save(ligneCommande2);

        LigneCommande ligneCommande4 = new LigneCommande(5L, 4, 19000.5, 75000.0, product05);
        ligneCommande4.setCommande(commande4);
        ligneCommandeRepository.save(ligneCommande4);

        LigneCommande ligneCommande5 = new LigneCommande(6L, 2, 2000.5, 17000.0, product06);
        ligneCommande5.setCommande(commande5);
        ligneCommandeRepository.save(ligneCommande5);

        LigneCommande ligneCommande7 = new LigneCommande(7L, 7, 7000.5, 77000.0, product05);
        ligneCommande7.setCommande(commande5);
        ligneCommandeRepository.save(ligneCommande7);

        Vente vente1 = new Vente();
        vente1.setNumeroVente(1300L);
		vente1.setTotalVente(15000.5);
		vente1.setStatus("ENCOURS");
		vente1.setTypeReglement("Wave");
		vente1.setMontantReglement(15000.5);
		vente1.setUtilisateur(utilisateur);
        venteRepository.save(vente1);

        Vente vente2 = new Vente();
		vente2.setNumeroVente(1400L);
		vente2.setTotalVente(15000.5);
		vente2.setStatus("PAYEE");
		vente2.setTypeReglement("OrangeMoney");
		vente2.setMontantReglement(15000.5);
		vente2.setUtilisateur(utilisateur);
        venteRepository.save(vente2);
        Vente vente3 = new Vente();
		vente3.setNumeroVente(1500L);
		vente3.setTotalVente(15000.5);
		vente3.setStatus("PAYEE");
		vente3.setTypeReglement("OrangeMoney");
		vente3.setMontantReglement(15000.5);
		vente3.setUtilisateur(vendor);
        venteRepository.save(vente3);

		LigneVente ligneVente1 = new LigneVente();
		ligneVente1.setVente(vente1);ligneVente1.setPrixVente(1200.0);ligneVente1.setQuantite(2);ligneVente1.setProduct(product02);
		ligneVenteRepository.save(ligneVente1);

		LigneVente ligneVente2 = new LigneVente();
		ligneVente2.setVente(vente2);ligneVente2.setPrixVente(1200.0);ligneVente2.setQuantite(2);ligneVente2.setProduct(product03);
		ligneVenteRepository.save(ligneVente2);

		LigneVente ligneVente3 = new LigneVente();
		ligneVente3.setVente(vente3);ligneVente3.setPrixVente(1200.0);ligneVente3.setQuantite(2);ligneVente3.setProduct(product04);
		ligneVenteRepository.save(ligneVente3);
*/