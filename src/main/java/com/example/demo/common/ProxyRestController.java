package com.example.demo.common;

import java.util.Date;

/**
 * Used by proxy Service (like Proxy-Customer) to forward commands.
 */
public class ProxyRestController {

	protected void initMessage(CloudEvent sourceMessage, CloudEvent destinationMessage) {
		destinationMessage.setFlowName(sourceMessage.getFlowName());
		destinationMessage.setFlowId(sourceMessage.getFlowId());
		destinationMessage.setSagaName(sourceMessage.getSagaName());
		destinationMessage.setSagaId(sourceMessage.getSagaId());
		destinationMessage.setServer(sourceMessage.getServer());
		destinationMessage.setSource(sourceMessage.getSource());
		destinationMessage.setReplyTo(sourceMessage.getSource());
		destinationMessage.setTime(new Date().toGMTString());
		destinationMessage.setId(sourceMessage.getId());
		destinationMessage.setType(destinationMessage.getClass().getSimpleName());
	}
}