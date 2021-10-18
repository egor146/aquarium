package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Aquarium {
    private final List<AquariumCreature> creatures = new ArrayList<>();
    private final List<Accessories> accessories = new ArrayList<>();
    private double price;

    public Aquarium() {
    }

    public Aquarium(File file) throws ParserConfigurationException, IOException, SAXException {
        price = 0;
        if (file.getName().endsWith(".xml")) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList creatureNodeList = document.getElementsByTagName("creature");
            for (int i = 0; i < creatureNodeList.getLength(); i++) {
                Node creatureNode = creatureNodeList.item(i);
                if (creatureNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element creatureElement = (Element) creatureNode;
                    AquariumCreature creature;
                    switch (creatureElement.getAttribute("type")) {
                        case "fish":
                            creature = new Fish();
                            break;
                        case "reptile":
                            creature = new Reptile();
                            break;
                        default:
                            continue;
                    }
                    NodeList childNodes = creatureNode.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node childNode = childNodes.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element childElement = (Element) childNode;
                            switch (childElement.getNodeName()) {
                                case "name":
                                    creature.setName(childElement.getTextContent());
                                    break;
                                case "price":
                                    creature.setPrice(Double.parseDouble(childElement.getTextContent()));
                                    break;
                            }
                        }
                    }
                    creatures.add(creature);
                }
            }
            calculatePrice();
        }
    }
    public void calculatePrice() {
        double price = 0;
        for (AquariumCreature creature : creatures) {
            price += creature.getPrice();
        }
        for (Accessories a : accessories) {
            price += a.price;
        }
        this.price = price;
    }

    public void addAccessory(Accessories accessory) {
        accessories.add(accessory);
    }

    public void addCreature(AquariumCreature creature) {
        creatures.add(creature);
    }

    public void saveToXML(File file) throws IOException, ParserConfigurationException,
            TransformerException {
        if (file.getName().endsWith(".xml")) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.newDocument();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Element root = document.createElement("aquarium");
            root.setAttribute("price", String.valueOf(price));
            document.appendChild(root);
            for (AquariumCreature creature : creatures) {
                Element item = document.createElement("creature");
                item.setAttribute("type", creature.getClass().getSimpleName().toLowerCase());
                Element elemName = document.createElement("name");
                elemName.setTextContent(creature.getName());
                item.appendChild(elemName);
                Element elemPrice = document.createElement("price");
                elemPrice.setTextContent(String.valueOf(creature.getPrice()));
                item.appendChild(elemPrice);
                root.appendChild(item);
            }
            for (Accessories accessory : accessories) {
                Element item = document.createElement("accessory");
                item.setAttribute("type", accessory.name().toLowerCase());
                Element name = document.createElement("name");
                name.setTextContent(accessory.name);
                item.appendChild(name);
                Element price = document.createElement("price");
                price.setTextContent(String.valueOf(accessory.price));
                item.appendChild(price);
                root.appendChild(item);
            }
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileWriter(file));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        }
    }


    @Override
    public String toString() {
        return "Aquarium{" +
                "creatures=" + creatures +
                '}';
    }
}
