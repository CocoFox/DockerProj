create table if not exists Chaussures(
  ID int not null auto_increment,
  nom varchar(20) not null,
  -- pointure int,
  couleur varchar(20),
  marque varchar(20),
  quantite int,
  prix float,
  filename varchar(20),
  primary key (ID)
);

create table if not exists Reservations(
  ID_client int not null,
  ID_chaussure int not null,
  date_reservation timestamp not null
);

create table if not exists Clients(
  ID int not null auto_increment,
  nom varchar(20),
  prenom varchar(20),
  identifiant varchar(20),
  mdp varchar(40),
  primary key (ID)
);

create table if not exists Administrateurs(
  ID int not null auto_increment,
  nom varchar(20),
  prenom varchar(20),
  identifiant varchar(20),
  mdp varchar(40),
  primary key (ID)
);

insert into Chaussures(nom, couleur, marque, quantite, prix) 
values 
  ("CONTINENTAL 80", "Rose", "Adidas", 10, 55.00),
  ("UNION WHARF OXFORD", "Noire", "Timberland", 5, 38.00),
  ("KILLINGTON", "Beige", "Timberland", 8, 104.00);

insert into Clients(nom, prenom, identifiant, mdp)
values
  ("Hajji", "Wadii", "whajji", "7c4a8d09ca3762af61e59520943dc26494f8941b"),
  ("Gord", "Quentin", "qgord", "7c4a8d09ca3762af61e59520943dc26494f8941b");

select * from Chaussures;
select * from Clients;