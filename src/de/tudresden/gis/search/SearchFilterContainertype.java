package de.tudresden.gis.search;

import com.esri.gpt.catalog.search.ISearchFilter;
import com.esri.gpt.catalog.search.SearchException;
import com.esri.gpt.catalog.search.SearchParameterMap;
import com.esri.gpt.catalog.search.SearchParameterMap.Value;
import com.esri.gpt.framework.util.Val;

/**
 * Implements a filter for container types 
 * 
 * @author Christin Henzen
 * 
 */
public class SearchFilterContainertype implements ISearchFilter { 
	
	/**
	 * generated id
	 */
	private static final long serialVersionUID = -475818244706045219L;
	private static String KEY_CONTAINERTYPE = "containertype";
	private String containertype;
 
	/**
	 * Method to get selected container type value
	 * @return container type as string
	 */
	public String getContainertype() { 		
		return Val.chkStr(containertype);
	}
 
	/**
	 * Method to set container type value from JSP pages.
	 * @param container type as string
	 */
	public void setContainertype(String containertype) { 
		this.containertype = containertype;
	}
 
	/**
	 * Method to serialize selected container type as map
	 * @return container types as map
	 */
	public SearchParameterMap getParams() {
		SearchParameterMap map = new SearchParameterMap();
		map.put(KEY_CONTAINERTYPE, map.new Value(this.getContainertype(), "")); 
		return map;
	}
 
	/**
	 * Method to filter container type from all given search parameters and to set the intern container type value.
	 */
	public void setParams(SearchParameterMap parameterMap) throws SearchException {
		Value val = parameterMap.get(KEY_CONTAINERTYPE);
		this.setContainertype(val.getParamValue());
	}
 
	/**
	 * Method to check whether search filter type is search filter container type
	 */
	public boolean isEquals(Object obj) {
		if (obj instanceof SearchFilterContainertype) 
			return ((SearchFilterContainertype) obj).getContainertype().equals(this.getContainertype());
		return false;
	}
 
	/**
	 * Method to reset selected values.
	 */
	public void reset() { 
		this.setContainertype("");
	}
 
	/**
	 * Method to validate.
	 * (non-Javadoc)
	 * @see com.esri.gpt.catalog.search.ISearchFilter#validate()
	 */
	public void validate() throws SearchException {
		if (this.getContainertype().equals("this should throw an exception")) { 
			throw new SearchException("this should throw an exception");
		}
	}

	/* @check unused? */
	public String getSelectedContainertype() { 
		return containertype;
	}

	/* @check unused? */
	public void setSelectedContainertype(String containertype) { 
		this.containertype = containertype;
	}
}
