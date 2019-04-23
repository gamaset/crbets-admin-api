package com.gamaset.crbetadmin.client.crbetsportal;

import com.gamaset.crbetadmin.client.crbetsportal.schema.CompetitionEventsResponse;
import com.gamaset.crbetadmin.client.crbetsportal.schema.GenericResponse;

import feign.RequestLine;

public interface CrbetsPortalClient {

	@RequestLine("GET /v1/event-types/1/events?period=NEXT_3_DAYS")
	GenericResponse<CompetitionEventsResponse> listEventsAvailable();

}
