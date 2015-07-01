package de.tudresden.gis.search;

import com.esri.gpt.catalog.search.ISearchFilter;
 
/**
 * The Interface ISearchFilterGeooperators. 
 * Defines a geooperator filter object.
 * The code refactors the class ISearchFilterContentTypes. 
 */
public interface ISearchFilterGeooperators extends ISearchFilter {

/**
 * Gets the selected geooperator.
 * 
 * @return the selected geooperator
 */
public String getSelectedGeooperator();

/**
 * Sets the selected geooperator.
 * 
 * @param themeTypes the new selected geooperator
 */
public void setSelectedGeooperator(String geooperator);

}
