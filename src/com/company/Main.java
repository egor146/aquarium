package com.company;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            Aquarium aquarium = new Aquarium(new File("aquarium.xml"));
            aquarium.addAccessory(Accessories.SUBSTRATE);
            aquarium.addAccessory(Accessories.FILTER);
            aquarium.addAccessory(Accessories.AIR_PUMP);
            aquarium.calculatePrice();
            aquarium.saveToXML(new File("out.xml"));
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
