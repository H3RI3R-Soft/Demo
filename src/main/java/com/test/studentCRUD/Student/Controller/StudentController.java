package com.test.studentCRUD.Student.Controller;

import com.test.studentCRUD.Configuration.JWTService;
import com.test.studentCRUD.Configuration.ResponseGlobal;
import com.test.studentCRUD.Student.DTO.StudentDto;
import com.test.studentCRUD.Student.Entity.Student;
import com.test.studentCRUD.Student.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequestMapping("/students")
// i have a method inside my Controller whcih is getAll student and it haa mapping /getAllStudent
//then my api endpoint it will become as a localhost:8124/students/getAllStudent
//Lombok
public class StudentController {

    private final StudentService studentService;
    @Autowired
    private JWTService jwtService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/getAllStudent")
    public ResponseGlobal<List<Student>> getAllStudents(@RequestHeader("Authorization") String token) {

        //token  = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaXRpazExQGdtYWlsLmNvbSIsIm5hbWUiOiJSaXRpayBTb25pIiwiZW1haWwiOiJyaXRpazExQGdtYWlsLmNvbSIsImlhdCI6MTc5MTI2OTQ1MSwiZXhwIjoxNzkxMzA1NDUxfQ.2A5QtjB1euPhKRfjLXSlYNMOGV__SdwANJ0ugI8qIqM"
        if(token.isEmpty() || token == null){
            return ResponseGlobal.onFailure("Authorization header is missing.Please add token to the request.");
        }
        if (!jwtService.validateAuthorizationHeader(token)) {
            return ResponseGlobal.onFailure("Invalid or missing token. Access denied.");
        }
        String emailFromToken = jwtService.getEmailFromToken(token);
        System.out.println("This is the email "+emailFromToken+" We got from the token -:"+token);
        String removePrefixfromToken = token.replace("Bearer ", "");
        System.out.println("this si the token without bearer prefix "+removePrefixfromToken);

        // from here we wil ge thte namr from the token

        String nameFromToken = jwtService.getNameFromToken(token);
        System.out.println("This is the name we got from the token "+nameFromToken+" and this is the token we got from the request header "+token);

        return studentService.getAllStudents();
    }

    @GetMapping("/getStudentById/{id}")
   public ResponseGlobal<Student> getStudentById(@RequestHeader("Authorization")String token,@PathVariable Long id ){
        if(token.isEmpty() || token == null){
            return ResponseGlobal.onFailure("Token is provided but its is Empty.");
        }
        if(!jwtService.validateAuthorizationHeader(token)){
            return ResponseGlobal.onFailure("Invalid or missing token. Access denied.");
        }

        return studentService.getStudentById(id);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable Long id){
       return studentService.deleteStudent(id);
    }

    @PostMapping("/createStudent")
    public ResponseGlobal<StudentDto> createStudent(@Valid @RequestBody StudentDto student){
        return studentService.createStudent(student);
    }


    @PutMapping("/updateStudent/{id}")
    public ResponseGlobal<Student> updateStudent(@PathVariable Long id, @RequestBody Student student){
        return studentService.updateStudent(id,student);
    }



}
