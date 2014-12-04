# GeoprocessingAppstore 

Based on ESRI Geoportal (https://github.com/Esri/geoportal-server)
Information about modifies ESRI files can be found here: https://github.com/GeoinformationSystems/GeoprocessingAppstore/blob/master/src/de/tudresden/gis/docu/modifications
Information about used tutorials/howtos can be found here: https://github.com/GeoinformationSystems/GeoprocessingAppstore/blob/master/src/de/tudresden/gis/docu/readme

The Geoprocessing Appstore is community platform to share geoprocessing concepts and scripts.
A live demo will be available soon.

## Structure

The application is based on Java, JSP, Javascript and HTML. The modules and their functionality are briefly described here.

* /WebContent - Browser part of the application (Javscript, HTML, JSP)
  * New manage websites for geooperators: https://github.com/GeoinformationSystems/GeoprocessingAppstore/blob/master/WebContent/catalog/publication
* /src - Server components of the application (Java)
  * New search, upload, download, edit implemenations: https://github.com/GeoinformationSystems/GeoprocessingAppstore/tree/master/src/de/tudresden/gis
  * New schema files for editor: https://github.com/GeoinformationSystems/GeoprocessingAppstore/tree/master/src/gpt/gxe/mcp
  * New schema files for detail view and template: https://github.com/GeoinformationSystems/GeoprocessingAppstore/tree/master/src/gpt/metadata/mcp

## Installation

Information about the installation can be found here:
https://github.com/Esri/geoportal-server/wiki/Install-Esri-Geoportal-Server
(The appstore uses Tomcat and PostGIS)

The geoprocessing appstore needs additional folders: C:/MCPackage (for source code upload) and C:/MCComment (for rating).
These paths are used/configured in geoportal\WEB-INF\classes\gpt\config\gpt.xml and de.tudresden.gis.manage.xml.Constants.

## Configuration

Configurations (e.g. admin, database, ldap, ...) can be made in geoportal\WEB-INF\classes\gpt\config\gpt.xml

## ESRI Geoportal Server

Features, License, Support, Issues for the ESRI Geoportal Server can be found here: https://github.com/Esri/geoportal-server
