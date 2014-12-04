package de.tudresden.gis.manage.geooperators;
  
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.IOException; 

import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent; 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.esri.gpt.framework.context.RequestContext;
import com.esri.gpt.framework.jsf.BaseActionListener;
import com.esri.gpt.framework.jsf.MessageBroker;
import com.esri.gpt.framework.util.Val;

/**
 * The class implements the action listener for managing (add and remove) geooperators.
 * The class is used to execute actions based on user input in backend section manage geooperators.
 * 
 * @author Christin Henzen 
 *
 */
public class GeooperatorController extends BaseActionListener {
	
	private String geooperator = "";
	private String parentgeooperator = "";
	private String removegeooperator = "";
	private SelectableGeooperators selectableGeooperators;
	private String path = Thread.currentThread().getContextClassLoader().getResource("gpt/search/browse/browse-catalog.xml").getPath();
	
	/**
	 * Constructor
	 */
	public GeooperatorController() {
		super();
		selectableGeooperators = new SelectableGeooperators();
		selectableGeooperators.build();
	}
	
	//-- add geooperator ---------------------------------------
	
	/**
	 * Method to get new geooperator name.
	 * 
	 * @return geooperator
	 */
	public String getGeooperator() {
		return geooperator;
	}
	
	/**
	 * Method to set new geooperator name.
	 * 
	 * @param geooperator
	 */
	public void setGeooperator(String geooperator) {
		this.geooperator = geooperator;
	}

	/**
	 * Method to get chosen parent of new geooperator.
	 * 
	 * @return parent geooperator name
	 */
	public String getParentgeooperator() {
		return parentgeooperator;
	}
	
	/**
	 * Method to set parent of new geooperator.
	 * 
	 * @param parentgeooperator
	 */
	public void setParentgeooperator(String parentgeooperator) {
		this.parentgeooperator = parentgeooperator;
	}
	
	//-- remove geooperator ---------------------------------------
	
	/**
	 * Method to get chosen geooperator, which should be removed.
	 * 
	 * @return geooperator name to be removed
	 */
	public String getRemovegeooperator() {
		return removegeooperator;
	}
	
	/**
	 * Method to set chosen geooperator, which should be removed.
	 * 
	 * @param removegeooperator
	 */
	public void setRemovegeooperator(String removegeooperator) {
		this.removegeooperator = removegeooperator;
	}
	
	//------------------------------------------------------------
	
	/**
	 * Method to get available geooperators.
	 * 
	 * @return geooperators as @see SelectableGeooperators.
	 */
	public SelectableGeooperators getSelectableGeooperators() {
		return selectableGeooperators;
	}
	
	@Override
	/**
	 * Method to trigger add or remove scripts, based on user's action.
	 */
	protected void processSubAction(ActionEvent event, RequestContext context)
	    throws AbortProcessingException, Exception {
		
		 UIComponent component = event.getComponent();
		 String sCommand = Val.chkStr((String) component.getAttributes().get("submit"));
		 if (sCommand.equals("add")) { 
			 addGeooperator(); 
		 } else { 
			 removeGeooperator(); 
		 } 
	}
	
	/**
	 * Method to add a geooperator to the geooperator registry, stored in a xml file.
	 * The registry is used to fill browse view in the appstore front end.
	 */
	private void addGeooperator() { 
		MessageBroker msgBroker = extractMessageBroker();
    
		try { 
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();   
			NodeList nList = doc.getElementsByTagName("item"); 
			 
			String[] addedGeoopArray = new String[1];
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp); 

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
  
					if (eElement.getElementsByTagName("id").item(0).getTextContent().equals(parentgeooperator)) {
						Node geooperatorNode = createGeooperatorNode(doc, createGeooperatorId(geooperator), geooperator, "searchText=keywords:\"" + geooperator + "\"");
						Node geooperatorImportNode = doc.importNode(geooperatorNode, true);
						 
						nNode.appendChild(geooperatorImportNode); 
						writeUpdatedXMLFile(doc, path);
						
						addedGeoopArray[0] = geooperator;
					}
				} 
			}
			
			if (addedGeoopArray[0] == null) {
				Node geooperatorNode = createGeooperatorNode(doc, createGeooperatorId(geooperator), geooperator, "searchText=keywords:\"" + geooperator + "\"");
				Node geooperatorImportNode = doc.importNode(geooperatorNode, true);
				NodeList treeList = doc.getElementsByTagName("tree"); 
				
				for (int temp = 0; temp < treeList.getLength(); temp++) {
					Node nNode = treeList.item(temp); 
					nNode.appendChild(geooperatorImportNode); 
					writeUpdatedXMLFile(doc, path);
					addedGeoopArray[0] = geooperator;
				} 
			}
			
			msgBroker.addSuccessMessage("catalog.publication.addGeooperator.success", addedGeoopArray); 
		} catch (Exception e) {
			e.printStackTrace();
			msgBroker.addErrorMessage("catalog.publication.addGeooperator.error");
		}
	}
	
	/**
	 * Method to generate a random id for new geooperators.
	 * Geooperator registry requires unique id for each stored geooperator.
	 * 
	 * @param geooperator
	 * @return id
	 */
	private String createGeooperatorId(String geooperator) {
		String id = geooperator.replace(" ", "");
		if (selectableGeooperators.getItems().contains(geooperator)) 
			id = id + Math.random(); 
		return id;
	}
	
	/**
	 * Method to generate a new geooperator node to insert in the geooperator registry file.
	 * 
	 * @param doc - geooperator registry store file.
	 * @param id - id of new geooperator
	 * @param name - name of new geooperator
	 * @param query - query string: how to query process descriptions based on new geooperator
	 * @return xml node with information about new geooperator
	 */
	private Node createGeooperatorNode(Document doc, String id, String name, String query) {
		Node item = doc.createElement("item");
		
		Node idNode = doc.createElement("id");
		Node idTextNode = doc.createTextNode(id);
		Node idTextImportNode = doc.importNode(idTextNode, true);
		idNode.appendChild(idTextImportNode);
		Node idImportNode = doc.importNode(idNode, true);
		item.appendChild(idImportNode);
		
		Node nameNode = doc.createElement("name");
		Node nameTextNode = doc.createTextNode(name);
		Node nameTextImportNode = doc.importNode(nameTextNode, true);
		nameNode.appendChild(nameTextImportNode);
		Node nameImportNode = doc.importNode(nameNode, true);
		item.appendChild(nameImportNode);
		
		Node queryNode = doc.createElement("query");
		Node queryTextNode = doc.createTextNode(query);
		Node queryTextImportNode = doc.importNode(queryTextNode, true);
		queryNode.appendChild(queryTextImportNode);
		Node queryImportNode = doc.importNode(queryNode, true);
		item.appendChild(queryImportNode);
		
		return item;
	}
	
	/**
	 * Method to store updated geooperator registry.
	 * 
	 * @param doc - updated list of geooperators as xml document
	 * @param path - path of geooperator registry xml document
	 */
	private void writeUpdatedXMLFile(Document doc, String path) { 
	    try { 
	      Source xmlSource = new DOMSource(doc); 
	      Result result = new StreamResult(new FileOutputStream(path)); 
	      TransformerFactory transformerFactory = TransformerFactory.newInstance(); 
	      Transformer transformer = transformerFactory.newTransformer(); 
	      transformer.setOutputProperty("indent", "yes"); 
	      transformer.transform(xmlSource, result);
	    } 
	    catch (TransformerFactoryConfigurationError | TransformerException | IOException e) {
	      e.printStackTrace();
	    }
	}
	
	/**
	 * Method to remove a geooperator from the registry.
	 */
	private void removeGeooperator() {
		MessageBroker msgBroker = extractMessageBroker();
		
		try { 
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();   
			NodeList nList = doc.getElementsByTagName("item"); 
			 
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp); 

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
  
					if (eElement.getElementsByTagName("id").item(0).getTextContent().equals(removegeooperator)) {
						nNode.getParentNode().removeChild(nNode);
						writeUpdatedXMLFile(doc, path);
					}
				}
			}
			
			msgBroker.addSuccessMessage("catalog.publication.removeGeooperator.success", new String[1]); 
		} catch (Exception e) {
			e.printStackTrace();
			msgBroker.addErrorMessage("catalog.publication.removeGeooperator.error");
		}
	}
}
