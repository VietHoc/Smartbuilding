package com.viethoc.smartbuilding.scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.viethoc.smartbuilding.model.Automate;
import com.viethoc.smartbuilding.model.Sensor;
import com.viethoc.smartbuilding.model.SensorData;
import com.viethoc.smartbuilding.service.AutomateService;
import com.viethoc.smartbuilding.service.ObixService;
import com.viethoc.smartbuilding.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class InitialDataConfiguration {

    static Boolean running = true;
    static int REQUEST_PER_MINUTE = 10;

    List<Automate> automates;
    List<Sensor> sensors;
    Map<Long, List<Sensor>> sensorGroupsHash;
    List<String> pollChangeUrls = new ArrayList<>();

    @Autowired
    private AutomateService automateService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private ObixService obixService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void postConstruct() throws Exception {
        System.out.println("Started after Spring boot application !");
        getDataDB();

        automates.forEach(automate -> {
            String addWatchUrl = null;
            try {
                addWatchUrl = makeWatch(automate.getIp());
            } catch (ParserConfigurationException | UnirestException | IOException | SAXException e) {
                e.printStackTrace();
            }

            try {
                addWatch(addWatchUrl, automate.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


//        while (running) {
            pollChangeUrls.forEach(pollChangeUrl -> {
                try {
                    pollChange(pollChangeUrl);
                } catch (UnirestException | IOException | SAXException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("-------------------------------------");
            try{
                Thread.sleep(1000*60 / REQUEST_PER_MINUTE);
            }catch (Exception ex){
                ex.printStackTrace();
            }
//        }
    }

    private void getDataDB() throws JsonProcessingException {
        automates = automateService.getAllAutomatesActive();
        sensors = sensorService.getAllSensorActive();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(sensors);
        redisTemplate.opsForValue().set("sensors", jsonInString);

        sensorGroupsHash = sensors.stream().collect(Collectors.groupingBy(Sensor::getAutomateId));

        System.out.println("Automates" + automates);
        System.out.println(sensorGroupsHash + "/n");
    }

    private String makeWatch(String ipAddress) throws UnirestException, IOException, SAXException, ParserConfigurationException {
        System.out.println("Make Watch");

        String responseWatchMake = obixService.sendRequestMakeWatch(ipAddress);
        Document doc = getDocumentFromXml(responseWatchMake);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("op");
        String addWatchUrl = new String();

        for(int x=0,size= nodeList.getLength(); x<size; x++) {
            String UriWatch = nodeList.item(x).getAttributes().getNamedItem("href").getNodeValue();
            //Remove later
            String Url = UriWatch.replaceAll("(ip_address)", ipAddress);
            if (Url.contains("add")) {
                addWatchUrl = Url;
                System.out.println(Url);
            }
            if (Url.contains("pollChanges")) {
                pollChangeUrls.add(Url);
                System.out.println(Url);
            }
        }
        return addWatchUrl;
    }

    private void addWatch(String addWatchUrl, Long automateId) throws Exception {
        System.out.println("Add Change");

        String responseAddWatch = obixService.sendRequestAddWatches(addWatchUrl, automateId);
        Document document = getDocumentFromXml(responseAddWatch);
        Element root = document.getDocumentElement();
        NodeList listValueNode = root.getChildNodes().item(1).getChildNodes();
        for (int i=1; i<listValueNode.getLength(); i = i+2) {
            String value = listValueNode.item(i).getAttributes().getNamedItem("val").getNodeValue();
            String href = listValueNode.item(i).getAttributes().getNamedItem("href").getNodeValue();
            System.out.println("href: "+href+ ": "+value);
        }
    }

    private void pollChange(String pollChangeUrl) throws UnirestException, IOException, SAXException, ParserConfigurationException {
        System.out.println("Poll Change: "+ pollChangeUrl);
        String responsePollChange = obixService.SendRequestPollChange(pollChangeUrl);

        Document documentPollChange = getDocumentFromXml(responsePollChange);
        Element rootPollChange = documentPollChange.getDocumentElement();
        NodeList listValueNodePollChange = rootPollChange.getChildNodes().item(1).getChildNodes();

        for (int i=1; i<listValueNodePollChange.getLength(); i = i+2) {
            String isPoint = listValueNodePollChange.item(i).getAttributes().getNamedItem("is").getNodeValue();
            if (isPoint.contains("obix:Point")) {
                String value = listValueNodePollChange.item(i).getAttributes().getNamedItem("val").getNodeValue();
                String href = listValueNodePollChange.item(i).getAttributes().getNamedItem("href").getNodeValue();
                System.out.println("href: "+href+ ": "+value);

                SensorData sensorData = new SensorData();
                switch (listValueNodePollChange.item(i).getNodeName()){
                    case "bool":
                        if (value == "true"){
                            sensorData.setBoolValue(true);
                        } else {
                            sensorData.setBoolValue(false);
                        }
                        break;
                    case "real":
                        sensorData.setDecValue(Float.parseFloat(value));
                        break;
                    default:
                        sensorData.setStr_val(value);
                }
                List<Sensor> sensorsServer = sensorService.getAllSensorActive();

                sensorsServer.forEach(sensor -> {
                    if (href.contains(sensor.getUri())){
                        sensorData.setSensorId(sensor.getId());
                    }
                });
                sensorData.setModification_date(new java.util.Date());
//                obixService.saveSensorData(sensorData);
            }
        }
    }

    private  Document getDocumentFromXml(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }
}
