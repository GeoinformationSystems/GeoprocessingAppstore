/* See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Esri Inc. licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudresden.gis.manage.geooperators;

import com.esri.gpt.framework.util.Val;

import java.io.File;
import java.util.ArrayList;

import javax.faces.model.SelectItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implements the container for selectable geooperators.
 * The code refactors the class Selectable ContentTypes.
 */
public class SelectableGeooperators {

	private ArrayList<SelectItem> _list = new ArrayList<SelectItem>();
	private String _selectedKey = "";

	/**
	 * Constructor
	 */
	public SelectableGeooperators() { }

	/**
	 * Method to get selectable geooperator items as array list.
	 * 
	 * @return array list of selectable geooperator items
	 */
	public ArrayList<SelectItem> getItems() {
		return _list;
	}

	/**
	 * Method to get key of selected geooperator item (key, value).
	 * 
	 * @return key as string
	 */
	public String getSelectedKey() {
		return _selectedKey;
	}

	/**
	 * Method to set key of selected geooperator item (key, value).
	 * 
	 * @param key as string
	 */
	public void setSelectedKey(String key) {
		_selectedKey = Val.chkStr(key);
	}

	/**
	 * Method to build list of selectable geooperators (key, value), which can be used in front end gui.
	 */
	public void build() {

		String path = Thread.currentThread().getContextClassLoader().getResource("gpt/search/browse/browse-catalog.xml").getPath();
		
		try {
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				//System.out.println("\nCurrent Element : " + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					_list.add(new SelectItem(
							eElement.getElementsByTagName("id").item(0).getTextContent(), 
							eElement.getElementsByTagName("name").item(0).getTextContent()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
