package com.example.rest.restservice;

import com.example.rest.models.ComplexObjectList;
import com.example.rest.models.ComplexProcess;
import com.example.rest.models.ComplexRepairCompany;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class OEEService {

    //query15 GET/XML
    public String firstNameBasedOnSubstringAndCompanyId(ArrayList<ComplexObjectList> complexObjectLists) throws ParserConfigurationException, IOException, SAXException {
        String filePath = System.getProperty("user.dir")+"/xmls/result1.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        StringBuilder firstNames = new StringBuilder();
        firstNames.append("<content>\n");
        int count =0;
        for(ComplexObjectList complexObjectList : complexObjectLists)
        {
            if(count==1)
                break;
            count++;
            for(int i=0; i<results.getLength(); i++){
                Node node = results.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    //repairCompanyId
                    Node repairCompanyNode = element.getElementsByTagName("repair_company").item(0);
                    Element elementCompany = (Element) repairCompanyNode;
                    String repairCompanyId = elementCompany.getAttribute("company_id");
                    Integer id = Integer.valueOf(repairCompanyId);
                    //employeeFirstName
                    Node employeeNode = element.getElementsByTagName("employee").item(0);
                    Element elementEmployee = (Element) employeeNode;
                    String employeeFirstName = elementEmployee.getElementsByTagName("first_name").item(0).getTextContent();

                    if(id > complexObjectList.companyId && employeeFirstName.contains(complexObjectList.firstName)){
                        firstNames.append("<first_name>\n").append(employeeFirstName).append("</first_name>\n");
                    }
                }
            }
        }
        firstNames.append("</content>\n").append("<hateoas>\n").append("<link rel=\"self\" href=\"http://abgabe.cs.univie.ac.at:9766/oee/first-names\" request_method=\"GET\" content_type=\"application/xml\" status_code=200>\n")
        .append("</link>\n").append("</hateoas>\n");
        return firstNames.toString();
    }

    //query13 GET/XML
    public String repairCompanyName(List<String> countryNames) throws ParserConfigurationException, IOException, SAXException {

        String filePath = System.getProperty("user.dir")+"/xmls/result1.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        StringBuilder countryXml = new StringBuilder();
        countryXml.append("<content>\n");
        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                //repairCompanyAddress
                Node repairCompany = element.getElementsByTagName("repair_company").item(0);
                Element elementRepairCompany = (Element) repairCompany;
                Node address = elementRepairCompany.getElementsByTagName("address").item(0);
                Element elementAddress = (Element) address;
                String country = elementAddress.getElementsByTagName("country").item(0).getTextContent();
                if(countryNames.get(0).contains(country)){
                    countryXml.append("<county>").append(country).append("</country>\n");
                }
            }
        }
        countryXml.append("</content>\n").append("<hateoas>\n").append("<link rel=\"self\" href=\"http://abgabe.cs.univie.ac.at:9766/oee/repair-companies-countries\" request_method=\"GET\" content_type=\"application/xml\" status_code=200>\n")
                .append("</link>\n").append("</hateoas>\n");
        return countryXml.toString();
    }

    //query14 GET/XML
    public String countRepairCountriesAddressesBasedOnNumber(List<Integer> streetNumbers) throws IOException, SAXException, ParserConfigurationException {

        String filePath = System.getProperty("user.dir")+"/xmls/result1.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        StringBuilder countXml = new StringBuilder();
        int count=0;

        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                //repairCompanyAddress
                Node repairCompany = element.getElementsByTagName("repair_company").item(0);
                Element elementRepairCompany = (Element) repairCompany;
                Node address = elementRepairCompany.getElementsByTagName("address").item(0);
                Element elementAddress = (Element) address;
                Node street = elementAddress.getElementsByTagName("street").item(0);
                Element elementStreet = (Element) street;
                String numberValue = elementStreet.getElementsByTagName("number").item(0).getTextContent();
                Integer number = Integer.valueOf(numberValue);
                if(number > streetNumbers.get(0).intValue()){
                    count++;
                }
            }
        }
        countXml.append("<content>\n").append("<count>\n").append(count+"\n").append("</count>\n").append("</content>\n").
                append("<hateoas>\n").append("<link rel=\"self\" href=\"http://abgabe.cs.univie.ac.at:9766/oee/total-countries-number-based\" request_method=\"GET\" content_type=\"application/xml\" status_code=200>\n")
                .append("</link>\n").append("</hateoas>\n");
        return countXml.toString();
    }

    //query21 GET/XML
    public String countNamesBasedOnEmployeeFirstName(List<String> firstNames) throws ParserConfigurationException, IOException, SAXException {
        String filePath = System.getProperty("user.dir")+"/xmls/result2.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        StringBuilder countXml = new StringBuilder();
        int count=0;

        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                //repairCompanyAddress
                Node repairCompany = element.getElementsByTagName("repair_company").item(0);
                Element elementRepairCompany = (Element) repairCompany;
                Node employee = elementRepairCompany.getElementsByTagName("employee").item(0);
                Element elementEmployee = (Element) employee;
                Node basicInfo = elementEmployee.getElementsByTagName("basic_information").item(0);
                Element elementInfo = (Element) basicInfo;
                String firstName = elementInfo.getElementsByTagName("first_name").item(0).getTextContent();
                if(firstNames.get(0).contains(firstName)){
                    count++;
                }
            }
        }

        countXml.append("<content>\n").append("<count>\n").append(count+"\n").append("</count>\n").append("</content>").append("<content>\n").append("<count>\n").append(count+"\n").append("</count>\n").append("</content>\n").
                append("<hateoas>\n").append("<link rel=\"self\" href=\"http://abgabe.cs.univie.ac.at:9766/oee/total-names-firstname-based\" request_method=\"GET\" content_type=\"application/xml\" status_code=200>\n")
                .append("</link>\n").append("</hateoas>");
        return countXml.toString();
    }

    //query42 GET/XML
    public String countThresholdRowsBasedOnEquiNumber(List<Integer> equipmentNumbers) throws ParserConfigurationException, IOException, SAXException {
        String filePath = System.getProperty("user.dir")+"/xmls/result4.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        StringBuilder countXml = new StringBuilder();
        int count=0;

        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                //process
                Node process = element.getElementsByTagName("proccess").item(0);
                Element elementProcess = (Element) process;
                Node overallEquipment = elementProcess.getElementsByTagName("overall_equipment_efficiency").item(0);
                Element elementOverall = (Element) overallEquipment;
                String equipmentNumber = elementOverall.getElementsByTagName("equipment_number").item(0).getTextContent();
                Integer equNum = Integer.valueOf(equipmentNumber);
                Node values = elementOverall.getElementsByTagName("values").item(0);
                Element elementValues = (Element) values;
                String threshold = elementValues.getElementsByTagName("threshold").item(0).getTextContent();

                if(equNum > equipmentNumbers.get(0)){
                    count++;
                }
            }
        }
        countXml.append("<content>\n").append("<count>\n").append(count+"\n").append("</count>\n").append("</content>").
                append("<hateoas>\n").append("<link rel=\"self\" href=\"http://abgabe.cs.univie.ac.at:9766/oee/total-threshold-rows-equinum\" request_method=\"GET\" content_type=\"application/xml\" status_code=200>\n")
                .append("</link>\n").append("</hateoas>");
        return countXml.toString();
    }

    //query11 PUT/JSON
    public JSONObject foundationDateContainsRepairCompanyBasedOnSubstring(Integer repairId, ComplexRepairCompany complexRepairCompany) throws ParserConfigurationException, IOException, SAXException, ParseException, TransformerException {

        String filePath = System.getProperty("user.dir")+"/xmls/result1.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject linksObject = new JSONObject();
        JSONArray linksArray = new JSONArray();

        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                //repaircompany
                Node repairCompany = element.getElementsByTagName("repair_company").item(0);
                Element elementRepairCompany = (Element) repairCompany;

                String name = elementRepairCompany.getElementsByTagName("name").item(0).getTextContent();
                Node nameNode = elementRepairCompany.getElementsByTagName("name").item(0);

                String fieldOfWork = elementRepairCompany.getElementsByTagName("field_of_work").item(0).getTextContent();
                Node fieldOfWorkNode = elementRepairCompany.getElementsByTagName("field_of_work").item(0);

                String foundation = elementRepairCompany.getElementsByTagName("foundation_date").item(0).getTextContent();
                Node dateNode = elementRepairCompany.getElementsByTagName("foundation_date").item(0);

                String repairCompanyId = elementRepairCompany.getAttribute("company_id");
                Integer id = Integer.valueOf(repairCompanyId);

                Node address = elementRepairCompany.getElementsByTagName("address").item(0);
                Element addressElement = (Element) address;

                String city = addressElement.getElementsByTagName("city").item(0).getTextContent();
                Node cityNode = addressElement.getElementsByTagName("city").item(0);

                String postalCode = addressElement.getElementsByTagName("postal_code").item(0).getTextContent();
                Node postalCodeNode = addressElement.getElementsByTagName("postal_code").item(0);

                String country = addressElement.getElementsByTagName("country").item(0).getTextContent();
                Node countryNode = addressElement.getElementsByTagName("country").item(0);

                Node street = addressElement.getElementsByTagName("street").item(0);
                Element streetElement = (Element) street;

                String nameStreet = streetElement.getElementsByTagName("name_of_street").item(0).getTextContent();
                Node nameStreetNode = streetElement.getElementsByTagName("name_of_street").item(0);

                String numberStreet = streetElement.getElementsByTagName("number").item(0).getTextContent();
                Integer num = Integer.valueOf(numberStreet);
                Node numberStreetNode = streetElement.getElementsByTagName("number").item(0);

                if(id==repairId){
                    name = complexRepairCompany.getName();
                    fieldOfWork = complexRepairCompany.getFieldOfWork();
                    foundation = String.valueOf(complexRepairCompany.getFoundationDate());
                    nameStreet = complexRepairCompany.getStreetName();
                    num = complexRepairCompany.getNumber();
                    city = complexRepairCompany.getCity();
                    postalCode = complexRepairCompany.getPostalCode();
                    country = complexRepairCompany.getCountry();

                    nameNode.setTextContent(name);
                    fieldOfWorkNode.setTextContent(fieldOfWork);
                    cityNode.setTextContent(city);
                    postalCodeNode.setTextContent(postalCode);
                    countryNode.setTextContent(country);
                    nameStreetNode.setTextContent(nameStreet);
                    numberStreetNode.setTextContent(numberStreet);
                    dateNode.setTextContent(foundation);
                    jsonArray.add(id);
                    jsonArray.add(name);
                    jsonArray.add(fieldOfWork);
                    jsonArray.add(foundation);
                    jsonArray.add(city);
                    jsonArray.add(nameStreet);
                    jsonArray.add(num);
                    jsonArray.add(city);
                    jsonArray.add(postalCode);
                    jsonArray.add(country);
                }
            }
        }
        linksObject.put("ref", "self");
        linksObject.put("href", "http://abgabe.cs.univie.ac.at:9766/oee/update-repair-company/"+repairId);
        linksObject.put("request_method", "PUT");
        linksObject.put("content_type", "application/json");
        linksObject.put("status_code",  200);
        linksArray.add(linksObject);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer= transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result=new StreamResult(new File(filePath));
        transformer.transform(source, result);
        jsonObject.put("Updated content repair company object:", jsonArray);
        jsonObject.put("Links:" , linksArray);
        return jsonObject;
    }

    //query12 DELETE/JSON
    public JSONObject returnNameOfStreetOnCompanyBasedOnStreet(String streetString) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String filePath = System.getProperty("user.dir")+"/xmls/result1.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject linksObject = new JSONObject();
        JSONArray linksArray = new JSONArray();
        int count=0;
        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                //repaircompany
                Node repairCompany = element.getElementsByTagName("repair_company").item(0);
                Element elementRepairCompany = (Element) repairCompany;
                Node address = elementRepairCompany.getElementsByTagName("address").item(0);
                Element elementAddress = (Element) address;
                Node street = elementAddress.getElementsByTagName("street").item(0);
                Element streetElement = (Element) street;
                String streetName = streetElement.getElementsByTagName("name_of_street").item(0).getTextContent();
                Node streetNameNode = streetElement.getElementsByTagName("name_of_street").item(0);
                if(streetName.equals(streetString)){
                    count++;
                    streetNameNode.setTextContent("");
                }
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer= transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result=new StreamResult(new File(filePath));
        transformer.transform(source, result);

        linksObject.put("ref", "self");
        linksObject.put("href", "http://abgabe.cs.univie.ac.at:9766/oee/delete-repair-company-foundation/"+streetString);
        linksObject.put("request_method", "DELETE");
        linksObject.put("content_type", "application/json");
        linksObject.put("status_code",  200);
        linksArray.add(linksObject);
        jsonArray.add(streetString);
        jsonArray.add(count);
        jsonObject.put("Deleted content element name and times of deletion:", jsonArray);
        jsonObject.put("Links:", linksArray);
        return jsonObject;
    }

    //query23 GET/JSON
    public JSONObject givenNameBasedOnSubstring(List<String> givenNames) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String filePath = System.getProperty("user.dir")+"/xmls/result2.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray linksArray = new JSONArray();
        JSONObject linksObject = new JSONObject();
        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                 //repaircompany
                Node repairCompany = element.getElementsByTagName("repair_company").item(0);
                Element elementRepairCompany = (Element) repairCompany;
                Node employee = elementRepairCompany.getElementsByTagName("employee").item(0);
                Element employeeElement = (Element) employee;
                Node addressXml = employeeElement.getElementsByTagName("address_x005F_xml").item(0);
                Element elementAddressXml = (Element) addressXml;
                Node address = elementAddressXml.getElementsByTagName("address").item(0);
                Element elementAddress = (Element) address;
                Node name = elementAddress.getElementsByTagName("name").item(0);
                Element elementName = (Element) name;
                String givenName = elementName.getElementsByTagName("given_name").item(0).getTextContent();
                if(givenName.contains(givenNames.get(0))){
                    jsonArray.add(givenName);
                }
            }
        }
        linksObject.put("ref", "self");
        linksObject.put("href", "http://abgabe.cs.univie.ac.at:9766/oee/all-given-names");
        linksObject.put("request_method", "GET");
        linksObject.put("content_type", "application/json");
        linksObject.put("status_code",  200);
        linksArray.add(linksObject);
        jsonObject.put("Given Name Content:", jsonArray);
        jsonObject.put("Links:", linksArray);
        return jsonObject;
    }

    //query17 PATCH/JSON
    public JSONObject returnRepairCompanyBasedOnDate(String repId, Date foundationDate) throws ParserConfigurationException, IOException, SAXException, ParseException, TransformerException {

        String filePath = System.getProperty("user.dir")+"/xmls/result1.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject linksObject = new JSONObject();
        JSONArray linksArray = new JSONArray();
        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                Node repairCompany = element.getElementsByTagName("repair_company").item(0);
                Element elementRepairCompany = (Element) repairCompany;
                String repCom = elementRepairCompany.getElementsByTagName("name").item(0).getTextContent();
                String repairCompanyId = elementRepairCompany.getAttribute("company_id");
                String foundation = elementRepairCompany.getElementsByTagName("foundation_date").item(0).getTextContent();
                Node foundationDateNode = elementRepairCompany.getElementsByTagName("foundation_date").item(0);
                if(repairCompanyId.equals(repId)){
                    foundation = String.valueOf(foundationDate);
                    foundationDateNode.setTextContent(foundation);
                    jsonArray.add(repCom);
                    jsonArray.add(repId);
                    jsonArray.add(foundation);
                }

            }
        }
        linksObject.put("ref", "self");
        linksObject.put("href", "http://abgabe.cs.univie.ac.at:9766/oee/update-company-date/"+repId);
        linksObject.put("request_method", "PATCH");
        linksObject.put("content_type", "application/json");
        linksObject.put("status_code",  200);
        linksArray.add(linksObject);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer= transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result=new StreamResult(new File(filePath));
        transformer.transform(source, result);
        jsonObject.put("New content foundation date for company:", jsonArray);
        jsonObject.put("Links:", linksArray);
        return jsonObject;
    }

    //query16 GET/JSON
    public JSONObject streetHash(HashMap<String, Integer> hashMap) throws ParserConfigurationException, IOException, SAXException {
        String filePath = System.getProperty("user.dir")+"/xmls/result1.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject linksObject = new JSONObject();
        JSONArray linksArray = new JSONArray();
        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                Node repairCompany = element.getElementsByTagName("repair_company").item(0);
                Element elementRepairCompany = (Element) repairCompany;
                String repairCompanyId = elementRepairCompany.getAttribute("company_id");
                Node address = elementRepairCompany.getElementsByTagName("address").item(0);
                Element elementAddress = (Element) address;
                Node street = elementAddress.getElementsByTagName("street").item(0);
                Element elementStreet = (Element) street;
                String number = elementStreet.getElementsByTagName("number").item(0).getTextContent();
                for(Map.Entry<String, Integer>map : hashMap.entrySet()){
                    String key = map.getKey();
                    Integer value = map.getValue();
                    if(key.equals(repairCompanyId) && value==Integer.parseInt(number)){
                            jsonArray.add(repairCompanyId);
                    }
                }
            }
        }
        linksObject.put("ref", "self");
        linksObject.put("href", "http://abgabe.cs.univie.ac.at:9766/oee/repair-company-number");
        linksObject.put("request_method", "GET");
        linksObject.put("content_type", "application/json");
        linksObject.put("status_code",  200);
        linksArray.add(linksObject);
        jsonObject.put("Show content hash of IDs of Repair Companies:", jsonArray);
        jsonObject.put("Links:", linksArray);
        return jsonObject;
    }

    //POST/JSON
    public JSONObject createNewProcess(ComplexProcess complexProcess) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        String filePath = System.getProperty("user.dir")+"/xmls/result4.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList results = document.getElementsByTagName("result");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject linksObject = new JSONObject();
        JSONArray linksArray = new JSONArray();
        for(int i=0; i<results.getLength(); i++){
            Node node = results.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element root = document.getDocumentElement();
                Element process = document.createElement("process");
                Element name = document.createElement("name");
                Element status = document.createElement("status");
                Element overallTime = document.createElement("overall_time");
                Element overallEquiEfficiency = document.createElement("overall_equipment_efficiency");
                Element values = document.createElement("values");
                Element threshold = document.createElement("threshold");
                Element finalValue = document.createElement("final_value");
                Element equiNumber = document.createElement("equipment_number");
                Element thresholdSatisfied = document.createElement("threshold_satisfied");

                root.appendChild(process);
                process.setAttribute("proccess_id", String.valueOf(complexProcess.getProcessId()));
                name.appendChild(document.createTextNode(complexProcess.getProcessName()));
                status.appendChild(document.createTextNode(complexProcess.getProcessStatus()));
                overallTime.appendChild(document.createTextNode(complexProcess.getOverallTime()));

                threshold.appendChild(document.createTextNode(String.valueOf(complexProcess.getThreshold())));
                finalValue.appendChild(document.createTextNode(String.valueOf(complexProcess.getFinalValue())));
                equiNumber.appendChild(document.createTextNode(String.valueOf(complexProcess.getEquipmentNumber())));
                thresholdSatisfied.appendChild(document.createTextNode(String.valueOf(complexProcess.getThresholdSatisfied())));

                process.appendChild(name);
                process.appendChild(status);
                process.appendChild(overallTime);
                process.appendChild(overallEquiEfficiency);
                overallEquiEfficiency.appendChild(values);
                values.appendChild(threshold);
                values.appendChild(finalValue);
                overallEquiEfficiency.appendChild(equiNumber);
                overallEquiEfficiency.appendChild(thresholdSatisfied);

            }
        }

        linksObject.put("ref", "self");
        linksObject.put("href", "http://abgabe.cs.univie.ac.at:9766/oee/new-processes");
        linksObject.put("request_method", "POST");
        linksObject.put("content_type", "application/json");
        linksObject.put("status_code",  200);
        linksArray.add(linksObject);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer= transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result=new StreamResult(new File(filePath));
        transformer.transform(source, result);
        jsonArray.add(complexProcess.getProcessId());
        jsonArray.add(complexProcess.getProcessName());
        jsonArray.add(complexProcess.getProcessStatus());
        jsonArray.add(complexProcess.getOverallTime());
        jsonArray.add(complexProcess.getThreshold());
        jsonArray.add(complexProcess.getFinalValue());
        jsonArray.add(complexProcess.getEquipmentNumber());
        jsonArray.add(complexProcess.getThresholdSatisfied());
        jsonObject.put("New content process created:", jsonArray);
        jsonObject.put("Links:", linksArray);
        return jsonObject;
    }
}
