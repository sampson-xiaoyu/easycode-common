package com.easycode.commons.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser {

	private static class PropertyCollector extends DefaultHandler {

		private Properties prop = new Properties();
		private LinkedList<String> keyStack = new LinkedList<String>();
		private LinkedList<StringBuilder> valueStack = new LinkedList<StringBuilder>();

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			keyStack.add(qName);
			valueStack.add(new StringBuilder());
		}

		@Override
		public void characters(char[] ch, int start, int length) {
			valueStack.getLast().append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			String key = this.keyStack.removeLast();
			StringBuilder value = this.valueStack.removeLast();
			prop.setProperty(key, value.toString());
			if (valueStack.size() > 0) {
				valueStack.getLast().append('<').append(key).append('>').append(value).append("</").append(key)
						.append('>');
			}
		}

		public Properties getProperties() {
			return this.prop;
		}

	}

	public static Properties parseXML(String xmlString) {
		if (xmlString == null) {
			throw new IllegalArgumentException("argument xmlString must not be null.");
		}
		PropertyCollector pc = new PropertyCollector();
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			InputStream source = new ByteArrayInputStream(xmlString.getBytes("utf-8"));
			parser.parse(source, pc);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("argument xmlStream is illegal");
		} catch (ParserConfigurationException e) {
			throw new IllegalArgumentException("argument xmlStream is illegal");
		} catch (SAXException e) {
			throw new IllegalArgumentException("argument xmlStream is illegal");
		} catch (IOException e) {
			throw new IllegalArgumentException("argument xmlStream is illegal");
		}
		return pc.getProperties();
	}

	public static Properties parseXML(InputStream xmlStream) {
		if (xmlStream == null) {
			throw new IllegalArgumentException("argument xmlStream must not be null.");
		}
		PropertyCollector pc = new PropertyCollector();
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(xmlStream, pc);
		} catch (ParserConfigurationException e) {
			throw new IllegalArgumentException("argument xmlStream is illegal");
		} catch (SAXException e) {
			throw new IllegalArgumentException("argument xmlStream is illegal");
		} catch (IOException e) {
			throw new IllegalArgumentException("argument xmlStream is illegal");
		}
		return pc.getProperties();
	}

}
