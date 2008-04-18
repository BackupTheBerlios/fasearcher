# HeidiSQL Dump 
#
# --------------------------------------------------------
# Host:                 127.0.0.1
# Database:             fasearcher
# Server version:       4.1.22-community-nt
# Server OS:            Win32
# Target-Compatibility: Standard ANSI SQL
# HeidiSQL version:     3.2 Revision: 1129
# --------------------------------------------------------

/*!40100 SET CHARACTER SET latin1;*/
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ANSI';*/
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;*/


#
# Database structure for database 'fasearcher'
#

CREATE DATABASE /*!32312 IF NOT EXISTS*/ "fasearcher";

USE "fasearcher";


#
# Table structure for table 'aceptadas'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "aceptadas" (
  "id" varchar(20) NOT NULL default '',
  "Cadena" varchar(50) NOT NULL default '',
  KEY "NewIndex" ("id"),
  CONSTRAINT "aceptadas_ibfk_1" FOREIGN KEY ("id") REFERENCES "problema" ("id")
) /*!40100 DEFAULT CHARSET=latin1*/;



#
# Table structure for table 'configuraciones'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "configuraciones" (
  "id" varchar(20) NOT NULL default '',
  "Estados" int(10) unsigned NOT NULL default '0',
  "PobMax" int(10) unsigned NOT NULL default '0',
  "Muestras" int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  ("Estados","id","Muestras","PobMax"),
  KEY "id" ("id")
) /*!40100 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci*/;



#
# Table structure for table 'extrainfo'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "extrainfo" (
  "id" varchar(20) NOT NULL default '',
  "Estados" int(10) unsigned default NULL,
  "PobMax" int(10) unsigned default NULL,
  "TipoAutomata" varchar(4) default NULL,
  "AutomataOriginal" blob,
  PRIMARY KEY  ("id"),
  CONSTRAINT "extrainfo_ibfk_1" FOREIGN KEY ("id") REFERENCES "problema" ("id")
) /*!40100 DEFAULT CHARSET=latin1*/;



#
# Table structure for table 'problema'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "problema" (
  "id" varchar(20) NOT NULL default '',
  "Soluciones" int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  ("id")
) /*!40100 DEFAULT CHARSET=latin1*/;



#
# Table structure for table 'rechazadas'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "rechazadas" (
  "id" varchar(20) NOT NULL default '0',
  "Cadena" varchar(50) NOT NULL default '',
  KEY "NewIndex" ("id"),
  CONSTRAINT "rechazadas_ibfk_1" FOREIGN KEY ("id") REFERENCES "problema" ("id")
) /*!40100 DEFAULT CHARSET=latin1*/;



#
# Table structure for table 'solucion'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "solucion" (
  "id_solucion" int(3) unsigned NOT NULL auto_increment,
  "id" varchar(20) NOT NULL default '',
  "Estados" int(10) unsigned NOT NULL default '0',
  "Mejor" double unsigned NOT NULL default '0',
  "TipoAutomata" varchar(4) NOT NULL default '',
  "Automata" blob,
  "Pasos" int(10) unsigned NOT NULL default '0',
  "Mutador" varchar(20) NOT NULL default '',
  "FuncBondad" varchar(20) NOT NULL default '',
  "Cruzador" varchar(20) NOT NULL default '',
  "MetodoRes" varchar(20) default NULL,
  "PobMax" int(10) unsigned NOT NULL default '0',
  "Muestras" int(10) unsigned NOT NULL default '0',
  "Particiones" int(10) unsigned default NULL,
  PRIMARY KEY  ("id_solucion"),
  KEY "solucion" ("id"),
  CONSTRAINT "solucion_ibfk_1" FOREIGN KEY ("id") REFERENCES "problema" ("id")
) /*!40100 DEFAULT CHARSET=latin1*/;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE;*/
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;*/
