package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import javax.xml.transform.TransformerException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class SortXml {

    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document docSet = null;
        try {
            assert builder != null;
            docSet = builder.parse(new File("settings.xml"));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        assert docSet != null;
        docSet.getDocumentElement().normalize();
        String name = docSet.getElementsByTagName("array").item(0).getAttributes().getNamedItem("name")
                .getTextContent();
        String value = docSet.getElementsByTagName("attributeName").item(0).getAttributes()
                .getNamedItem("value").getTextContent();

        Document docIn = null;
        try {
            docIn = builder.parse(new File("input.xml"));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        assert docIn != null;
        docIn.getDocumentElement().normalize();
        NodeList nl = docIn.getElementsByTagName(name);
        NodeList childNodeIn = nl.item(0).getChildNodes();
        ArrayList<Node> sort = new ArrayList<>();
        for (int i = 0; i < childNodeIn.getLength(); i++) {
            if (childNodeIn.item(i).getNodeType() == Node.ELEMENT_NODE) {
                sort.add(childNodeIn.item(i));
            }
        }

        Comparator<Node> comparator = (o1, o2) -> {
            NamedNodeMap nnm1 = o1.getAttributes();
            NamedNodeMap nnm2 = o2.getAttributes();
            Node node1 = nnm1.getNamedItem(value);
            Node node2 = nnm2.getNamedItem(value);
            return node1.getTextContent().compareTo(node2.getTextContent());
        };
        sort.sort(comparator);

        for (int i = 0; i < sort.size(); i++) {
            nl.item(0).replaceChild(sort.get(i), childNodeIn.item(i));
        }

        File outFile = new File("./output.xml");
        StreamResult result = new StreamResult(outFile);
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        assert transformer != null;
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(docIn);
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}