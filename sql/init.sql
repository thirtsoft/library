/* CATEGORY */
INSERT INTO categorie(id,code,designation) VALUES(1,'CAT01','Ordinateur pordtable');
INSERT INTO categorie(id,code,designation) VALUES(2,'CAT02','Téléphone portable');
INSERT INTO categorie(id,code,designation) VALUES(3,'CAT03','Boisson');
INSERT INTO categorie(id,code,designation) VALUES(4,'CAT04','Eau minérale');
INSERT INTO categorie(id,code,designation) VALUES(5,'CAT05','Nescafé');

/* SUBCATEGORY */
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(1,'SUBCAT01','Ordinateur pordtable HP',1);
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(2,'SUBCAT02','Ordinateur pordtable DELL',1);
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(3,'SUBCAT03','Téléphone portable IPhone',2);
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(4,'SUBCAT04','Téléphone portable Samsung',2);
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(5,'SUBCAT05','Boisson Gazeuse',3);
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(6,'SUBCAT06','Bierree',3);
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(7,'SUBCAT07','Casamançaise',4);
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(8,'SUBCAT08','Kirène',4);
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(9,'SUBCAT09','Gold Intenso',5);
INSERT INTO s_categorie(id,code,libelle,cat_id) VALUES(10,'SUBCAT010','Nescafé',5);

/* PRODUCT */
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'123456789','Product-01','HP elitebook core i7 vPro',760000.0, 790000.0,800000.0, 12,5,1);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'459789','Product-02','HP Probook core i5',320000.0, 500000.0, 510000.0, 12,5,1);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'678912346','Product-03','Dell Promodel core i7',540000.0, 720000.0, 740000.0, 12,5,2);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,subcat_id) VALUES(1,'810987654','Product-04','HP Iphone 14s',460000.0, 660000.0,690000.0, 10,2,3);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(5,'5789985','Product-05','Iphone 12s',320000.0, 530000.0,560000.0, 8,3,3);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(6,'12345678915','Product-06','Samsung A23',11000.0, 125000.0,135000.0, 16,5,4);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(7,'012345678915','Product-07','Samsung A32',130000.0, 145000.0,150000.0, 16,5,4);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(8,'80987654','Product-08','Coca cola',680.0, 800.0,900.0, 30,10,5);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(9,'51789985','Product-09','Fanta',680.0, 800.0,900.0, 30,10,5);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(10,'112345678915','Product-010','Malta',890.0, 1250.0,1250.0, 16,5,6);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(11,'22345678915','Product-011','Malta 001',890.0, 1250.0,1250.0, 16,5,6);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(12,'52789985','Product-012','Casamançaise 1L',360.0,500.0,500.0, 8, 2,7);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(13,'42345678915','Product-013','Casamançaise 5L',760.0, 1100.0,1200.0, 16,5,7);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(14,'62345678915','Product-014','Kirène 1L',390.0, 500.0,500.0, 16,5,8);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(15,'53789985','Product-015','Kirène 5L',890.0, 1250.0,1250.0, 16,5,8);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(16,'72345678915','Product-016','Gold Intenso PM',1800.0, 2500.0,2500.0, 16,5,9);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(17,'82345678915','Product-017','Gold Intenso GM',5500.0, 6000.0,6000.0, 16,5,9);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(18,'92345678915','Product-018','Nescafé PM',680.0, 900.0,900.0, 16,5,10);
INSERT INTO produit(id,barcode,reference,designation,prix_achat,prix_vente,prix_detail,qtestock,stock_initial,scat_id) VALUES(19,'102345678915','Product-019','Nescafé GM',1100.0, 1900.0,1900.0, 16,5,10);

/* FOURNISSEUR */
INSERT INTO fournisseur(id,adresse,code,email,fax,message,mobile,numero_compte,raison_sociale,subject,telephone) VALUES(1,'','','contact@wokite.net','','','779440310','12345678-BOA','Wokite','','');
INSERT INTO fournisseur(id,adresse,code,email,fax,message,mobile,numero_compte,raison_sociale,subject,telephone) VALUES(2,'','','support@sat.com','','','778440310','987654321-BNDE','SAT','','');
INSERT INTO fournisseur(id,adresse,code,email,fax,message,mobile,numero_compte,raison_sociale,subject,telephone) VALUES(3,'','','contact@fiit.com','','','77440310','23456789-CBEAO','FIIT','','');
INSERT INTO fournisseur(id,adresse,code,email,fax,message,mobile,numero_compte,raison_sociale,subject,telephone) VALUES(4,'','','contact@sunuchauffeur.com','','','777440310','12346543-ECOBANK','Sunuchauffeur','','');
INSERT INTO fournisseur(id,adresse,code,email,fax,message,mobile,numero_compte,raison_sociale,subject,telephone) VALUES(5,'','','fournisseur05.net','','','77440310','20FFA','Fournisseur05','','');

/* CLIENT */
INSERT INTO client(id,adresse,code_client,email,message,mobile,raison_social,subject) VALUES(1,'','','contact@wokite.net','','779440310','Wokite','');
INSERT INTO client(id,adresse,code_client,email,message,mobile,raison_social,subject) VALUES(2,'','','contact@sunuchauffeur.com','','777643217','Sunuchauffeur','');
INSERT INTO client(id,adresse,code_client,email,message,mobile,raison_social,subject) VALUES(3,'','','contact@parfumdp.sn','','768643217','ParfumD&P','');
INSERT INTO client(id,adresse,code_client,email,message,mobile,raison_social,subject) VALUES(4,'','','support@funteks.com','','788643217','Funteks','');
INSERT INTO client(id,adresse,code_client,email,message,mobile,raison_social,subject) VALUES(5,'','','client05@gmail.com','','778543217','Client05','');

/* ROLES */
INSERT INTO roles(id,name) VALUES(1,'ROLE_VENDEUR');
INSERT INTO roles(id,name) VALUES(1,'ROLE_GERANT');
INSERT INTO roles(id,name) VALUES(1,'ROLE_ADMIN');

/* UTILISATEURS */
INSERT INTO utilisateur(id,name,username,email,password,photo,isActive) VALUES(1,'Admin','admin','admin@gmail.com','$2a$10$R44dGnwuk2cMQFQXc35fXOER2c8XLC6Sll4j/fqjepLplg4gBG.sK','',true);
INSERT INTO utilisateur(id,name,username,email,password,photo,isActive) VALUES(1,'Vendeur','vendeur','vendeur@gmail.com','$2a$10$v5RaZqqWEs.iupW7r.oErOSqlb4CJjWUXY5XepZ4O8Vt.lOYQiDMi','',true);
INSERT INTO utilisateur(id,name,username,email,password,photo,isActive) VALUES(1,'Gerant','gerant','gerant@gmail.com','$2a$10$1Ch4l3YxBNzsQXTOLflqmOnxLQbp9XwGkSeTEmcucCtHvcYauXzo2','',true);
#INSERT INTO utilisateur(id,name,username,email,password,photo,isActive) VALUES(1,'Manageur','manageur','manageur@gmail.com','$2a$10$QCtMpd6M1ShbWcnDGrIYzO1hfQc0FS.EWrt68hDLe2mh64gEYVHqe','',true);
