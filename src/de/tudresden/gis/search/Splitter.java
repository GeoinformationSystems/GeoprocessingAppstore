package de.tudresden.gis.search;

/**
 * This class provides functionality to split a text into abstract, platform and container type to use separately in the search result.
 * 
 * @author Jochen Lenz
 *
 */
public class Splitter {
 
	/**
	 * This method extracts and returns the abstract. 
	 * @see CSW_2.0.2_OGCCORE_ESRI_GPT_GetRecords_Response.xslt
	 * 
	 * @param abstractText consists of abstract, container type, platform information and two 'split-words'
	 * @return abstract text without platform and container type; "not defined" in case of empty abstract 
	 */
	public String getAbstract(String abstractText) { 
		abstractText = abstractText.replaceAll("\\s+",""); 
		String[] parts = abstractText.split("Thecontainertypeofthedescribedprocessis"); 
		if (parts[0].length() == 0)
			return "not defined";
		else 
			return parts[0]; 
	}

	/**
	 * This method extracts and returns the container type.
	 * Returns the text between the two defined 'split-words'. 
	 * @see gpt.search.profiles CSW_2.0.2_OGCCORE_ESRI_GPT_GetRecords_Response.xslt
	 * 
	 * @param abstractText consists of abstract, container type, platform information and two 'split-words'
	 * @param icon boolean, defines type of returned text. Set to true returns icon url, if false returns icon description  
	 * @return url to icon or icon description. If container type is empty returns "not defined".
	 * 		   If container type is not in the list returns "unknown containertype.".
	 */
	public String getContainertype(String abstractText, boolean icon) { 
		abstractText = abstractText.replaceAll("\\s+", "");		
		String result; 
		String[] parts = abstractText.split("Thecontainertypeofthedescribedprocessis");
		String[] parts2 = parts[1].split("Theplatformofthedescribedprocessis"); 
		if (parts2.length < 1) {
			if (icon)
				return "/catalog/images/mcp/icons/containertype/notDefined.png";				
			else 
				return "not defined"; 		
		} else {
			// Returns "not defined" if first part of second split is empty.
			if (parts2[0].length() == 0) {
				if (icon) {
					return "/catalog/images/mcp/icons/containertype/notDefined.png";
				} else {
					return "not defined";
				}
			// If first part of second split is not empty, compares it with different types and returns type.
			// If no type matches, "unknown containertype." is returned.
			} else {
				switch (parts2[0]) {
					case "http://gis.geo.tu-dresden.de/movingcode/containerregistry/python-2.7":
						if (icon) result = "/catalog/images/mcp/icons/containertype/python-2.7/favicon.png";
						else result = "Python 2.7"; 
						break;
					case "http://gis.geo.tu-dresden.de/movingcode/containerregistry/python-2.8":
						if (icon) result = "/catalog/images/mcp/icons/containertype/python-2.8/favicon.png";
						else result = "Python 2.8"; 
						break;
					case "http://gis.geo.tu-dresden.de/movingcode/containerregistry/python-2.9":
						if (icon) result = "/catalog/images/mcp/icons/containertype/python-2.9/favicon.png";
						else result = "Python 2.9"; 
						break;
					case "http://gis.geo.tu-dresden.de/movingcode/containerregistry/java-jar":
						if (icon) result = "/catalog/images/mcp/icons/containertype/java-jar/favicon.png";
						else result = "Java"; 
						break;				
					default:
						if (icon) result = "/catalog/images/mcp/icons/containertype/unknown.png";
						else result = "unkown container type."; 			
						break;
				}
				return result;
			}
		}
	}

	
	/**
	 * This method extracts and returns the platform.
	 * Returns the text after the second 'split-word'. 
	 * 
	 * @see gpt.search.profiles CSW_2.0.2_OGCCORE_ESRI_GPT_GetRecords_Response.xslt
	 * 
	 * @param abstractText consists of abstract, container type, platform information and two 'split-words'
	 * @param icon defines type of returned text. Set to true returns icon url, if false returns icon description  
	 * @return url to icon or icon description. If platform is empty returns "not defined".
	 * 		   If platform is not in the list returns "unknown containertype.".
	 */
	public String getPlatform(String abstractText, boolean icon) { 
		abstractText = abstractText.replaceAll("\\s+","");		
		String result; 
		String[] parts = abstractText.split("Theplatformofthedescribedprocessis");
		// If there is only one part after split then there is no part with platform value.
		// Returns "not defined".
		if (parts.length == 1){
			if (icon) return "/catalog/images/mcp/icons/platform/notDefined.png";				
			else return "not defined"; 
		// If second part exists compares it with different platform and returns platform.
		// If no platform matches returns "unknown platform".
		} else {
			switch (parts[1]) {
			case "http://gis.geo.tu-dresden.de/movingcode/platformregistry/platform/java-1.6":
				if (icon) result = "/catalog/images/mcp/icons/platform/java-1.6/favicon.png";
				else return "Java 1.6"; 
				break;
			case "http://gis.geo.tu-dresden.de/movingcode/platformregistry/platform/java-1.7":
				if (icon) result = "/catalog/images/mcp/icons/platform/java-1.7/favicon.png";
				else return "Java 1.7"; 
				break;
			case "http://gis.geo.tu-dresden.de/movingcode/platformregistry/platform/java-1.8":
				if (icon) result = "/catalog/images/mcp/icons/platform/java-1.8/favicon.png";
				else return "Java 1.8"; 
				break;				
			default:
				if (icon) result = "/catalog/images/mcp/icons/platform/unknown.png";
				else return "unknown platfrom."; 				
				break;	
			}
			return result;
		}
	}

}
