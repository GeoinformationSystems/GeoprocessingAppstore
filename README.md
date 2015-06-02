# GeoprocessingAppstore 

Based on ESRI Geoportal (https://github.com/Esri/geoportal-server).<br/><br/>
Information about modified ESRI files can be found here: https://github.com/GeoinformationSystems/GeoprocessingAppstore/blob/master/src/de/tudresden/gis/docu/modifications<br/>
Information about used tutorials/howtos can be found here: https://github.com/GeoinformationSystems/GeoprocessingAppstore/blob/master/src/de/tudresden/gis/docu/readme<br/>

The Geoprocessing Appstore is a Web-platform respectively a portal to share geospatial algorithms in a community, and provides a software solution for current algorithm discovery and access problems. Based on the core concept of the publish-find-bind paradigm for service-oriented architectures, it connects developers and users of geospatial software (Figure 1): an algorithm provider publishes algorithm source code including a description of the provided functionality with technical requirements to execute the source code. Eventually, users can discover algorithms according to their needs and finally bind, respectively apply them. <br/><br/>
A live demo is available at: http://apps1.glues.geo.tu-dresden.de:8080/appstore.

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

The Geoprocessing Appstore project uses and integrates a modified <b>ESRI Geoportal Facet</b> project (https://github.com/GeoinformationSystems/GeoprocessingAppstoreFacets). The facet website replaces the default advanced search in the ESRI geoportal. To remove this dependencies and use the default advanced search please modify WebContent/catalog/search/searchBody.jsp -> iframe.

## Configuration

Configurations (e.g. admin, database, ldap, ...) can be made in geoportal\WEB-INF\classes\gpt\config\gpt.xml

## ESRI Geoportal Server

Features, License, Support, Issues for the ESRI Geoportal Server can be found here: https://github.com/Esri/geoportal-server

## Contact (for TUD modifications)

Christin Henzen (christin.henzen@tu-dresden.de)
