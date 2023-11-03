package com.netcracker.unc.team35.task_manager.ui.network.sse_client;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;


/**
 * Base class for event stream listeners that can be used for the {@link HttpEventStreamClient}
 * @author LupCode.com (Luca Vogels)
 * @since 2020-12-22
 */
public abstract class EventStreamAdapter implements EventStreamListener {

	@Override
	public void onEvent(HttpEventStreamClient client, HttpEventStreamClient.Event event) throws JsonProcessingException {}

	@Override
	public void onError(HttpEventStreamClient client, Throwable throwable) {
		throwable.printStackTrace();
	}
	
	@Override
	public void onReconnect(HttpEventStreamClient client, HttpResponse<Void> response, boolean hasReceivedEvents, long lastEventID) {}
	
	@Override
	public void onClose(HttpEventStreamClient client, HttpResponse<Void> response) {}
}
