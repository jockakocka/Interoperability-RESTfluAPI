package com.example.rest.restcontroller;

import com.example.rest.models.ComplexObjectList;
import com.example.rest.models.ComplexProcess;
import com.example.rest.models.ComplexRepairCompany;
import com.example.rest.restservice.OEEService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
//@RequestMapping("/oee")
@RequestMapping("/oee")
@Component
public class OEEController {

    final
    OEEService oeeService;

    public OEEController(OEEService oeeService) {
        this.oeeService = oeeService;
    }

    @RequestMapping(value = "/repair-companies-countries", method = RequestMethod.GET)
    private ResponseEntity<String> getAllRepairCompaniesCountriesBasedOnCountry(@RequestParam List<String> countryNames) throws IOException, ParserConfigurationException, SAXException {
        return new ResponseEntity<>(oeeService.repairCompanyName(countryNames), HttpStatus.OK);
    }

    @RequestMapping(value = "/total-countries-number-based", method = RequestMethod.GET)
    private ResponseEntity<String> getTotalCountriesBasedOnStreetNumber(@RequestParam List<Integer> streetNumbers) throws IOException, ParserConfigurationException, SAXException {
        return new ResponseEntity<>(oeeService.countRepairCountriesAddressesBasedOnNumber(streetNumbers), HttpStatus.OK);
    }

    @RequestMapping(value = "/total-names-firstname-based", method = RequestMethod.GET)
    private ResponseEntity<String> getTotalNamesBasedOnEmployeeFirstName(@RequestParam List<String> firstNames) throws IOException, ParserConfigurationException, SAXException {
        return new ResponseEntity<>(oeeService.countNamesBasedOnEmployeeFirstName(firstNames), HttpStatus.OK);
    }

    @RequestMapping(value = "/total-threshold-rows-equinum", method = RequestMethod.GET)
    private ResponseEntity<String> getTotalThresholdRowsBasedOnEquiNumber(@RequestParam List<Integer> equipmentNumbers) throws IOException, ParserConfigurationException, SAXException {
        return new ResponseEntity<>(oeeService.countThresholdRowsBasedOnEquiNumber(equipmentNumbers), HttpStatus.OK);
    }

    @RequestMapping(value = "/all-given-names", method = RequestMethod.GET)
    private ResponseEntity<JSONObject> getAllGivenNamesBasedOnSubstring(@RequestParam List<String> givenNames) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        return new ResponseEntity<>(oeeService.givenNameBasedOnSubstring(givenNames), HttpStatus.OK);
    }

    @RequestMapping(value = "/update-company-date/{id}", method = RequestMethod.PATCH)
    private ResponseEntity<JSONObject> updateFoundationDateOnRepairCompany(@PathVariable("id") String id, @RequestParam Date foundationDate) throws IOException, ParserConfigurationException, SAXException, ParseException, TransformerException {
        return new ResponseEntity<>(oeeService.returnRepairCompanyBasedOnDate(id, foundationDate), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-repair-company-foundation/{streetName}", method = RequestMethod.DELETE)
    private ResponseEntity<JSONObject> deleteStreetNameFromXmlElements(@PathVariable("streetName") String streetName) throws IOException, ParserConfigurationException, SAXException, ParseException, TransformerException {
        return new ResponseEntity<>(oeeService.returnNameOfStreetOnCompanyBasedOnStreet(streetName), HttpStatus.OK);
    }

    @RequestMapping(value = "/first-names", method = RequestMethod.GET)
    private ResponseEntity<String> getAllFirstNamesBasedOnCompanyIdAndSubstringName(@RequestBody ArrayList<ComplexObjectList> complexObjectLists) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return new ResponseEntity<>(oeeService.firstNameBasedOnSubstringAndCompanyId(complexObjectLists), HttpStatus.OK);
    }

    @RequestMapping(value = "/update-repair-company/{id}", method = RequestMethod.PUT)
    private ResponseEntity<JSONObject> updateRepairCompany(@PathVariable("id") Integer id, @RequestBody ComplexRepairCompany complexRepairCompany) throws IOException, ParserConfigurationException, SAXException, ParseException, TransformerException {
        return new ResponseEntity<>(oeeService.foundationDateContainsRepairCompanyBasedOnSubstring(id, complexRepairCompany), HttpStatus.OK);
    }

    @RequestMapping(value = "/repair-company-number", method = RequestMethod.GET)
    private ResponseEntity<JSONObject> getHashMap(@RequestBody HashMap<String, Integer> hashMap) throws IOException, ParserConfigurationException, SAXException {
        return new ResponseEntity<>(oeeService.streetHash(hashMap), HttpStatus.OK);
    }

    @RequestMapping(value = "/new-processes", method = RequestMethod.POST)
    private ResponseEntity<JSONObject> createNewProcess(@RequestBody ComplexProcess complexProcess) throws IOException, ParserConfigurationException, SAXException, ParseException, TransformerException {
        return new ResponseEntity<>(oeeService.createNewProcess(complexProcess), HttpStatus.OK);
    }

}
