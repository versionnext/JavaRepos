package com.vsnm.framework.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Provider
public class GenericExceptionHandler implements ExceptionMapper<Exception> {

	private static final Logger logger = LoggerFactory
			.getLogger(GenericExceptionHandler.class);

	@ExceptionHandler(AppException.class)
	public ErrorResponse handleAppException(AppException ex) {
		logger.error(ex.getCode() + "--" + ex.getMessage());
		return new ErrorResponse(ex.getCode(), ex.getMessage());

	}

	@ExceptionHandler(Exception.class)
	public ErrorResponse handleException(Exception ex) {
//		if(ex.getCause().getCause() instanceof ConstraintViolationException){`
//		TODO to get constraint violation message
//		}
		logger.error(Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
		return new ErrorResponse(502,
				Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

	}

	@Override
	public Response toResponse(Exception ex) {
		ErrorResponse er = null;
		if (ex instanceof AppException) {
			AppException ae = (AppException) ex;
			er = handleAppException(ae);
		} else {
			er = handleException(ex);
		}
		return Response.status(er.getCode()).entity(er).build();
	}

}
