insert into planet (id, planet_node,planet_name) 
values (1,'A','Earth'), (2,'B','Moon'),(3,'C','Jupiter'),(4,'D','Venus'),(5,'E','Mars'),(6,'F','Saturn'),(7,'G','Uranus'),(8,'H','Pluto'),(9,'I','Neptune'),(10,'J','Mercury'),(11,'K','Alpha Centauri'),(12,'L','Luhman 16'),(13,'M','Epsilon Eridani'),(14,'N','Groombridge 34'),(15,'O','Epsilon Indi'),(16,'P','Tau Ceti'),(17,'Q','Kapteyns star'),(18,'R','Gliese 687'),(19,'S','Gliese 674'),(20,'T','Gliese 876#'),(21,'U','Gliese 832'),(22,'V','Fomalhaut'),(23,'W', 'Virginis'),(24,'X', 'HD 102365'),(25,'Y', 'Gliese 176'),(26,'Z', 'Gliese 436'),(27,'A''', 'Pollux'),(28,'B''', 'Gliese 86'),(29,'C''', 'HIP 57050'),(30,'D''', 'Piscium'),(31,'E''', 'GJ 1214'),(32,'F''', 'Upsilon Andromedae'),(33,'G''', 'Gamma Cephei'),(34,'H''', 'HD 176051'),(35,'I''', 'Gliese 317'),(36,'J''', 'HD 38858'),(37,'K''', 'Ursae Majoris');

insert into route (id, origin,destination, distance, delay) 
values 
(1,'A','B',0.44,0.30),
(2,'A','C',1.89,0.90),
(3,'A','D',0.10,0.10),
(4,'B','H',2.44,0.20),
(5,'B','E',3.45,1.30),
(6,'C','F',0.49,0.30),
(7,'D','L',2.34,0.00),
(8,'D','M',2.61,0.20),
(9,'H','G',3.71,3.10),
(10,'E','I',8.09,6.10),
(11,'F','J',5.46,2.40),
(12,'F','K',3.67,0.30),
(13,'G','Z',5.25,4.00),
(14,'I','Z',13.97,3.70),
(15,'I','J',14.78,2.90),
(16,'L','T',15.23,9.80),
(17,'T','N',10.43,8.80),
(18,'T','S',14.22,13.40),
(19,'S','O',6.02,6.00),
(20,'O','U',5.26,3.50),
(21,'J','R',12.34,5.40),
(22,'R','P',10.10,1.90),
(23,'R','L''',9.95,0.70),
(24,'Z','Y',18.91,3.20),
(25,'Y','Q',22.04,20.30),
(26,'Q','X',10.51,10.40),
(27,'L''','X',23.61,8.10),
(28,'X','K''',19.94,0.70),
(29,'P','U',9.31,8.40),
(30,'U','K''',21.23,12.30),
(31,'U','J''',25.96,2.50),
(32,'J''','V',3.16,3.00),
(33,'K''','V',20.42,7.20),
(34,'J''','I''',17.10,13.40),
(35,'Y','A''',19.52,17.00),
(36,'A''','B''',31.56,7.20),
(37,'B''','C''',23.13,21.30),
(38,'K''','W',28.89,16.00),
(39,'W','C''',10.64,4.70),
(40,'W','E''',36.19,33.90),
(41,'C''','D''',36.48,30.10),
(42,'E''','D''',41.26,7.60),
(43,'E''','F''',42.07,34.20),
(44,'F''','G''',17.63,2.40),
(45,'G''','H''',40.48,1.10);