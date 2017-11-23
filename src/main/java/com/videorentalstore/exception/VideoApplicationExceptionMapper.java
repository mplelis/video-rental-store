package com.videorentalstore.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.videorentalstore.util.VideoLogging;

import io.dropwizard.jersey.errors.ErrorMessage;

/**
 * Exception Mapper to map thrown Video Application Exceptions to JSON responses.
 */
public class VideoApplicationExceptionMapper implements ExceptionMapper<VideoApplicationException> {

    @Override
    public Response toResponse(VideoApplicationException e) {
        VideoLogging.logError("Video Application Exception thrown: " + e.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),
                "VideoApplicationException occured due to " + Response.Status.BAD_REQUEST, e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
