package com.viethoc.smartbuilding.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.viethoc.smartbuilding.model.Automate;
import com.viethoc.smartbuilding.model.Sensor;
import com.viethoc.smartbuilding.model.SensorData;
import com.viethoc.smartbuilding.model.Watchlist;
import com.viethoc.smartbuilding.repository.AutomateRepository;
import com.viethoc.smartbuilding.repository.SensorDataRepository;
import com.viethoc.smartbuilding.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

@Service
public class ObixService {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public void saveSensorData(SensorData sensorData){
        sensorDataRepository.save(sensorData);
    }


    public static  String sendRequestMakeWatch(String ipAddress) throws UnirestException {
        String SERVER = String.format("http://%s/obix/watchService/make", ipAddress);

//        * Skip
//        call api
//        HttpResponse<String> response = Unirest.post(SERVER)
//                .header("Content-Length", "")
//                .header("Authorization", "Basic T0JJWDpPQklY")
//                .body("")
//                .asString();

//        return response

        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<obj href=\"http://(ip_address)/obix/watchService/watch43/\" is=\"obix:Watch\" display=\"Obix Watch\" xmlns=\"http://obix.org/ns/schema/1.0\" xsi:schemaLocation=\"http://obix.org/ns/schema/1.0/obix/xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <reltime name=\"lease\" val=\"PT30S\" href=\"http://(ip_address)/obix/watchService/watch43/lease/\" display=\" 30secs\" displayName=\"Lease\" writable=\"true\"/>\n" +
                "    <op name=\"add\" href=\"http://(ip_address)/obix/watchService/watch43/add/\" in=\"obix:WatchIn\" out=\"obix: WatchOut\"/>\n" +
                "    <op name=\"remove\" href=\"http://(ip_address)/obix/watchService/watch43/remove/\" in=\"obix:WatchIn\" out=\"obix:Nil\"/>\n" +
                "    <op name=\"pollChanges\" href=\"http://(ip_address)/obix/watchService/watch43/pollChanges/\" in=\"obix:Nil\" out=\"obix:WatchOut\"/>\n" +
                "    <op name=\"pollRefresh\" href=\"http://(ip_address)/obix/watchService/watch43/pollRefresh/\" in=\"obix:Nil\" out=\"obix:WatchOut\"/>\n" +
                "    <op name=\"delete\" href=\"http://(ip_address)/obix/watchService/watch43/delete/\" in=\"obix:Nil\" out=\"obix: Nil\"/>\n" +
                "    <abc>hoc</abc>\n" +
                "</obj>";
    }

    public  String sendRequestAddWatches(String server, Long automateId) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // root element
        Element root = document.createElement("obj");
        document.appendChild(root);
        root.setAttribute("is", "obix: WatchIn");

        // list element
        Element list = document.createElement("list");
        root.appendChild(list);
        list.setAttribute("name", "hrefs");
        list.setAttribute("of", "obix:Uri");

        // uri element
        List<Sensor> sensors = sensorService.findAllByAutomateId(automateId);
        sensors.forEach(res -> {
            Element uri = document.createElement("uri");
            list.appendChild(uri);
            uri.setAttribute("val", res.getUri());
        });

        System.out.println(toStringDocument(document));

//      * Skip
//      call api
//        HttpResponse<String> response = Unirest.post(server)
//                .header("cache-control", "no-cache")
//                .header("Connection", "keep-alive")
//                .header("Content-Length", "847")
//                .header("Cache-Control", "no-cache")
//                .header("Accept", "*/*")
//                .header("Content-Type", "text/plain")
//                .header("Authorization", "Basic T0JJWDpPQklY")
//                .body(toStringDocument(document))
//                .asString();

//        return response

        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<obj is=\"obix:WatchOut\" xsi:schemaLocation=\"http://obix.org/ns/schema/1.0/obix/xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <list name=\"values\" of=\"obix:obj\">\n" +
                "        <bool val=\"false\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom /FB_Movement_SEWI_1/\" is=\"/obix/def/control:BooleanPoint obix:Point\" display=\"not occupied {ok}\" icon=\" /ord?module://icons/x16/control/booleanPoint.png\" range=\"/obix/config/Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom/FB_Movement_SEWI_1/out/~bool\">\n" +
                "            <str name=\"facets\" val=\"falseText=s:not$20occupied|trueText=s:occupied\" href=\"/uri&gt;/obix/config/Drivers /KnxNetwork/KNX/points/Bx_405_QuietRoom/FB_Movement_SEWI_1/facets/\" display=\"falseText=not occupied,trueText=occupied\" displayName=\"Facets\" writable=\"true\"/>\n" +
                "            <ref name=\"proxyExt\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom /FB_Movement_SEWI_1/proxyExt/\" is=\"/obix/def/knxnetIp:KnxBooleanProxyExt\" display=\"Knx Boolean Proxy Ext - Group Address:8866\" displayName=\"Proxy Ext\" icon=\"/ord?module://icons/x16/control /controlExtension.png\"/>\n" +
                "            <bool name=\"out\" val=\"false\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_405_QuietRoom/FB_Movement_SEWI_1/out/\" is=\"/obix/def/baja:StatusBoolean\" display=\"not occupied {ok}\" displayName=\"Out\" icon=\"/ord?module://icons/x16/statusBoolean.png\" range=\"/uri&gt;/obix/config /Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom/FB_Movement_SEWI_1/out/~bool\">\n" +
                "            </bool>\n" +
                "            <str name=\"wsAnnotation\" val=\"1,131,18\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_405_QuietRoom/FB_Movement_SEWI_1/wsAnnotation/\"/>\n" +
                "        </bool>\n" +
                "        <bool val=\"false\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom /FB_Movement_SEWI_2/\" is=\"/obix/def/control:BooleanPoint obix:Point\" display=\"not occupied {ok}\" icon=\" /ord?module://icons/x16/control/booleanPoint.png\" range=\"/obix/config/Drivers/KnxNetwork/KNX/points /Bx_405_QuietRoom/FB_Movement_SEWI_2/out/~bool\">\n" +
                "            <str name=\"facets\" val=\"falseText=s:not$20occupied|trueText=s:occupied\" href=\"/uri&gt;/obix/config/Drivers /KnxNetwork/KNX/points/Bx_405_QuietRoom/FB_Movement_SEWI_2/facets/\" display=\"falseText=not occupied,trueText=occupied\" displayName=\"Facets\" writable=\"true\"/>\n" +
                "            <ref name=\"proxyExt\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom /FB_Movement_SEWI_2/proxyExt/\" is=\"/obix/def/knxnetIp:KnxBooleanProxyExt\" display=\"Knx Boolean Proxy Ext - Group Address:8869\" displayName=\"Proxy Ext\" icon=\"/ord?module://icons/x16/control /controlExtension.png\"/>\n" +
                "            <bool name=\"out\" val=\"false\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_405_QuietRoom/FB_Movement_SEWI_2/out/\" is=\"/obix/def/baja:StatusBoolean\" display=\"not occupied {ok}\" displayName=\"Out\" icon=\"/ord?module://icons/x16/statusBoolean.png\" range=\"/uri&gt;/obix/config /Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom/FB_Movement_SEWI_2/out/~bool\">\n" +
                "            </bool>\n" +
                "            <str name=\"wsAnnotation\" val=\"1,126,18\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_405_QuietRoom/FB_Movement_SEWI_2/wsAnnotation/\"/>\n" +
                "        </bool>\n" +
                "        <real val=\"53.72\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_629_MultifonctionnalRoom /FB_Humdity_CALA/\" is=\"/obix/def/control:NumericPoint obix:Point\" display=\"53.72 % {ok}\" icon=\"/ord?modu le://icons/x16/control/numericPoint.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/percent\" >\n" +
                "            <str name=\"facets\" val=\"precision=i:2|max=d:670760.0|min=d:0.0|units=u:percent;%;;;\" href=\"/uri&gt;/obix /config/Drivers/KnxNetwork/KNX/points/Bx_629_MultifonctionnalRoom/FB_Humdity_CALA/facets/\" display=\" precision=2,max=670760.00,min=0.00,units=%\" displayName=\"Facets\" writable=\"true\"/>\n" +
                "            <ref name=\"proxyExt\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_629_MultifonctionnalRoom/FB_Humdity_CALA/proxyExt/\" is=\"/obix/def/knxnetIp:KnxNumericProxyExt\" display=\"Knx Numeric Proxy Ext - Group Address:8382\" displayName=\"Proxy Ext\" icon=\"/ord?module://icons /x16/control/controlExtension.png\"/>\n" +
                "            <real name=\"out\" val=\"53.72\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_629_MultifonctionnalRoom/FB_Humdity_CALA/out/\" is=\"/obix/def/baja:StatusNumeric\" display=\"53.72 % {ok}\" displayName=\"Out\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/percent\">\n" +
                "            </real>\n" +
                "            <str name=\"wsAnnotation\" val=\"1,45,19\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_629_MultifonctionnalRoom/FB_Humdity_CALA/wsAnnotation/\"/>\n" +
                "        </real>\n" +
                "        <real val=\"23.4\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /FB_Temperature_ATMO/\" is=\"/obix/def/control:NumericPoint obix:Point\" display=\"23.40 &#xb0;C {ok}\" icon=\"/ord?module://icons/x16/control/numericPoint.png\" max=\"670760.0\" min=\"-273.0\" precision=\"2\" unit=\"obix:units/celsius\">\n" +
                "            <str name=\"facets\" val=\"precision=i:2|max=d:670760.0|min=d:-273.0|units=u:celsius;&#xb0;C;(K);+273.15;\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube/FB_Temperature_ATMO /facets/\" display=\"precision=2,max=670760.00,min=-273.00,units=&#xb0;C\" displayName=\"Facets\" writable=\"true\"/>\n" +
                "            <ref name=\"proxyExt\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /FB_Temperature_ATMO/proxyExt/\" is=\"/obix/def/knxnetIp:KnxNumericProxyExt\" display=\"Knx Numeric\n" +
                "Proxy Ext - Group Address:8709\" displayName=\"Proxy Ext\" icon=\"/ord?module://icons/x16/control /controlExtension.png\"/>\n" +
                "            <real name=\"out\" val=\"23.4\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /FB_Temperature_ATMO/out/\" is=\"/obix/def/baja:StatusNumeric\" display=\"23.40 &#xb0;C {ok}\" displayName=\"Out\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"-273.0\" precision=\"2\" unit=\"obix:units/celsius\">\n" +
                "            </real>\n" +
                "        </real>\n" +
                "        <real val=\"300.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/\" is=\"/obix/def/control:NumericWritable /obix/def/control:NumericPoint obix:Point\" display=\" 300.00 lx {ok} @ def\" icon=\"/ord?module://icons/x16/control/numericPoint.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/lux\">\n" +
                "            <str name=\"facets\" val=\"precision=i:2|max=d:670760.0|min=d:0.0|units=u:lux;lx;(m-2)(cd);;\" href=\"/uri&gt; /obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube/SP_Lux_ATMO/facets/\" display=\" precision=2,max=670760.00,min=0.00,units=lx\" displayName=\"Facets\" writable=\"true\"/>\n" +
                "            <ref name=\"proxyExt\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/proxyExt/\" is=\"/obix/def/knxnetIp:KnxNumericProxyExt\" display=\"Knx Numeric Proxy Ext - Group Address:8708\" displayName=\"Proxy Ext\" icon=\"/ord?module://icons/x16/control/controlExtension.png\" />\n" +
                "            <real name=\"out\" val=\"300.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/out/\" is=\"/obix/def/baja:StatusNumeric\" display=\"300.00 lx {ok} @ def\" displayName=\"Out\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in1\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in1/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In1\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in2\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in2/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In2\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in3\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in3/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In3\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in4\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in4/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In4\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in5\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in5/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In5\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in6\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in6/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In6\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in7\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in7/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In7\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in8\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in8/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In8\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in9\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in9/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In9\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in10\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in10/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In10\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in11\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in11/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In11\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in12\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in12/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In12\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in13\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in13/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In13\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in14\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in14/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In14\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in15\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in15/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In15\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"in16\" val=\"0.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/in16/\" is=\"/obix/def/baja:StatusNumeric\" null=\"true\" display=\"- {null}\" displayName=\"In16\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix: units/lux\">\n" +
                "            </real>\n" +
                "            <real name=\"fallback\" val=\"300.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_632_Petit_Cube/SP_Lux_ATMO/fallback/\" is=\"/obix/def/baja:StatusNumeric\" display=\"300.00 lx {ok}\" displayName=\"Fallback\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/lux\">\n" +
                "            </real>\n" +
                "            <abstime name=\"overrideExpiration\" val=\"1970-01-01T01:00:00.000+01:00\" href=\"/uri&gt;/obix/config /Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube/SP_Lux_ATMO/overrideExpiration/\" null=\"true\" display=\"null\" displayName=\"Override Expiration\" tz=\"Europe/Berlin\"/>\n" +
                "            <op name=\"emergencyOverride\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_632_Petit_Cube/SP_Lux_ATMO/emergencyOverride/\" in=\"obix:real\" displayName=\"Emergency Override\" />\n" +
                "            <op name=\"emergencyAuto\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/emergencyAuto/\" displayName=\"Emergency Auto\"/>\n" +
                "            <op name=\"override\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/override/\" in=\"/obix/def/control:NumericOverride /obix/def/control:Override\" displayName=\" Override\"/>\n" +
                "            <op name=\"auto\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/auto/\" displayName=\"Auto\"/>\n" +
                "            <op name=\"set\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_632_Petit_Cube /SP_Lux_ATMO/set/\" in=\"obix:real\" displayName=\"Set\"/>\n" +
                "        </real>\n" +
                "        <real val=\"599.04\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_642_Grand_Cube /FB_CO2_ATMO/\" is=\"/obix/def/control:NumericPoint obix:Point\" display=\"599.04 ppm {ok}\" icon=\"/ord?mod ule://icons/x16/control/numericPoint.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/parts per million\">\n" +
                "            <str name=\"facets\" val=\"precision=i:2|max=d:670760.0|min=d:0.0|units=u:parts per million;ppm;;*1.0E-6;\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_642_Grand_Cube/FB_CO2_ATMO/facets/\" display=\"precision=2,max=670760.00,min=0.00,units=ppm\" displayName=\"Facets\" writable=\"true\"/>\n" +
                "            <ref name=\"proxyExt\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_642_Grand_Cube /FB_CO2_ATMO/proxyExt/\" is=\"/obix/def/knxnetIp:KnxNumericProxyExt\" display=\"Knx Numeric Proxy Ext - Group Address:8726\" displayName=\"Proxy Ext\" icon=\"/ord?module://icons/x16/control/controlExtension.png\" />\n" +
                "            <real name=\"out\" val=\"599.04\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_642_Grand_Cube/FB_CO2_ATMO/out/\" is=\"/obix/def/baja:StatusNumeric\" display=\"599.04 ppm {ok}\" displayName=\"Out\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/parts per million\">\n" +
                "            </real>\n" +
                "        </real>\n" +
                "        <real val=\"68.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_652_MultifonctionnalRoom /FB_Lux_MINI/\" is=\"/obix/def/control:NumericPoint obix:Point\" display=\"68.00 lx {ok}\" icon=\"/ord?module://ic ons/x16/control/numericPoint.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/lux\">\n" +
                "            <str name=\"facets\" val=\"precision=i:2|max=d:670760.0|min=d:0.0|units=u:lux;lx;(m-2)(cd);;\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_652_MultifonctionnalRoom/FB_Lux_MINI/facets/\" display=\" precision=2,max=670760.00,min=0.00,units=lx\" displayName=\"Facets\" writable=\"true\"/>\n" +
                "            <ref name=\"proxyExt\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_652_MultifonctionnalRoom/FB_Lux_MINI/proxyExt/\" is=\"/obix/def/knxnetIp:KnxNumericProxyExt\" display=\"Knx Numeric Proxy Ext - Group Address:8361\" displayName=\"Proxy Ext\" icon=\"/ord?module://icons /x16/control/controlExtension.png\"/>\n" +
                "            <real name=\"out\" val=\"68.0\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_652_MultifonctionnalRoom/FB_Lux_MINI/out/\" is=\"/obix/def/baja:StatusNumeric\" display=\"68.00 lx {ok}\" displayName=\"Out\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/lux\">\n" +
                "            </real>\n" +
                "            <str name=\"wsAnnotation\" val=\"1,44,19\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_652_MultifonctionnalRoom/FB_Lux_MINI/wsAnnotation/\"/>\n" +
                "        </real>\n" +
                "    </list>\n" +
                "</obj>";
    }

    public String SendRequestPollChange(String server) throws UnirestException {

//      * Skip
//      call api
//        HttpResponse<String> response = Unirest.post(server)
//                .header("cache-control", "no-cache")
//                .header("Connection", "keep-alive")
//                .header("Content-Length", "847")
//                .header("Cache-Control", "no-cache")
//                .header("Accept", "*/*")
//                .header("Content-Type", "text/plain")
//                .header("Authorization", "Basic T0JJWDpPQklY")
//                .body("")
//                .asString();

//        return response
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<obj is=\"obix:WatchOut\" xsi:schemaLocation=\"http://obix.org/ns/schema/1.0/obix/xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <list name=\"values\" of=\"obix:obj\">\n" +
                "        <real val=\"53.72\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_629_MultifonctionnalRoom/FB_Temperature_CALA/\" is=\"/obix/def/control:NumericPoint obix:Point\" display=\"53.72 % {ok}\" icon=\"/ord?modu le://icons/x16/control/numericPoint.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/percent\" >\n" +
                "            <str name=\"facets\" val=\"precision=i:2|max=d:670760.0|min=d:0.0|units=u:percent;%;;;\" href=\"/uri&gt;/obix /config/Drivers/KnxNetwork/KNX/points/Bx_629_MultifonctionnalRoom/FB_Humdity_CALA/facets/\" display=\" precision=2,max=670760.00,min=0.00,units=%\" displayName=\"Facets\" writable=\"true\"/>\n" +
                "            <ref name=\"proxyExt\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_629_MultifonctionnalRoom/FB_Humdity_CALA/proxyExt/\" is=\"/obix/def/knxnetIp:KnxNumericProxyExt\" display=\"Knx Numeric Proxy Ext - Group Address:8382\" displayName=\"Proxy Ext\" icon=\"/ord?module://icons /x16/control/controlExtension.png\"/>\n" +
                "            <real name=\"out\" val=\"53.72\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_629_MultifonctionnalRoom/FB_Humdity_CALA/out/\" is=\"/obix/def/baja:StatusNumeric\" display=\"53.72 %{ok}\" displayName=\"Out\" icon=\"/ord?module://icons/x16/statusNumeric.png\" max=\"670760.0\" min=\"0.0\" precision=\"2\" unit=\"obix:units/percent\">\n" +
                "            </real>\n" +
                "            <str name=\"wsAnnotation\" val=\"1,45,19\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_629_MultifonctionnalRoom/FB_Humdity_CALA/wsAnnotation/\"/>\n" +
                "        </real>\n" +
                "        <bool val=\"false\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom/FB_Movement_SEWI_2/\" is=\"/obix/def/control:BooleanPoint obix:Point\" display=\"not occupied {ok}\" icon=\" /ord?module://icons/x16/control/booleanPoint.png\" range=\"/obix/config/Drivers/KnxNetwork/KNX/points /Bx_405_QuietRoom/FB_Movement_SEWI_2/out/~bool\">\n" +
                "            <str name=\"facets\" val=\"falseText=s:not$20occupied|trueText=s:occupied\" href=\"/uri&gt;/obix/config/Drivers /KnxNetwork/KNX/points/Bx_405_QuietRoom/FB_Movement_SEWI_2/facets/\" display=\"falseText=not occupied,trueText=occupied\" displayName=\"Facets\" writable=\"true\"/>\n" +
                "            <ref name=\"proxyExt\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom /FB_Movement_SEWI_2/proxyExt/\" is=\"/obix/def/knxnetIp:KnxBooleanProxyExt\" display=\"Knx Boolean Proxy Ext - Group Address:8869\" displayName=\"Proxy Ext\" icon=\"/ord?module://icons/x16/control /controlExtension.png\"/>\n" +
                "            <bool name=\"out\" val=\"false\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_405_QuietRoom/FB_Movement_SEWI_2/out/\" is=\"/obix/def/baja:StatusBoolean\" display=\"not occupied {ok}\" displayName=\"Out\" icon=\"/ord?module://icons/x16/statusBoolean.png\" range=\"/uri&gt;/obix/config /Drivers/KnxNetwork/KNX/points/Bx_405_QuietRoom/FB_Movement_SEWI_2/out/~bool\">\n" +
                "            </bool>\n" +
                "            <str name=\"wsAnnotation\" val=\"1,126,18\" href=\"/uri&gt;/obix/config/Drivers/KnxNetwork/KNX/points /Bx_405_QuietRoom/FB_Movement_SEWI_2/wsAnnotation/\"/>\n" +
                "        </bool>\n" +
                "    </list>\n" +
                "</obj>";
    }

    public static String toStringDocument(Document newDoc) throws Exception{
        DOMSource domSource = new DOMSource(newDoc);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        transformer.transform(domSource, sr);
        return sw.toString();
    }
}
