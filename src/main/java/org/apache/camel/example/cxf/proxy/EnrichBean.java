package org.apache.camel.example.cxf.proxy;

import org.apache.camel.example.reportincident.Priority;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * A bean to enrich the proxied web service to ensure the input is valid and add
 * additional information
 */
public class EnrichBean {

	public Document enrich(Document doc) {
		Node node = doc.getElementsByTagName("incidentId").item(0);
		Node prio = doc.getElementsByTagName("incidentPriority").item(0);
		if (prio == null){
			throw new RuntimeException("priority is null");
		}
		Priority prioValue = Priority.fromValue(prio.getTextContent());
		String incident = node.getTextContent();

		if (prioValue.equals(Priority.BLOCKER)) {
			node.setTextContent("666");
		} else {
			System.out.println("priority was" + prioValue);
			// here we enrich the document by changing the incident id to
			// another value
			// you can of course do a lot more in your use-case
			node.setTextContent("456");
		}
		System.out.println("Incident was " + incident + ", changed to " + node.getTextContent());

		return doc;
	}
}

