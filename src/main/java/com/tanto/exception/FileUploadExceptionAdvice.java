package com.tanto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tanto.model.MessageModel;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<MessageModel> handleMaxSize(MaxUploadSizeExceededException exc){
		MessageModel msg = new MessageModel();
		
		msg.setData(null);
		msg.setMessage("File terlalu besar");
		msg.setStatus(400);
		
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(msg);
	}
}
