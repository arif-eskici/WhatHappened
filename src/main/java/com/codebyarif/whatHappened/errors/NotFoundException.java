package com.codebyarif.whatHappened.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException  extends  RuntimeException{

    public NotFoundException(Long id) {
        super("Could not found with id " + id);
    }

}
