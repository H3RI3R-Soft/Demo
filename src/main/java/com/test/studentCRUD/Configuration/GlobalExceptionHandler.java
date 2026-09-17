package com.test.studentCRUD.Configuration;

import com.test.studentCRUD.Exception.StudentNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseGlobal<?> handleStudentNotFoundException(StudentNotFoundException ex) {
        return ResponseGlobal.onError(ex.getMessage());
    }
}
