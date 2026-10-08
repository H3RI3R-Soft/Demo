package com.test.studentCRUD.Student.Controller;

import com.test.studentCRUD.Configuration.JWTService;
import com.test.studentCRUD.Configuration.ResponseGlobal;
import com.test.studentCRUD.Student.DTO.AdminDTO;
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
    public ResponseGlobal<List<Student>> getAllStudents(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null) {
            String email = jwtService.getEmailFromToken(token);
            String name = jwtService.getNameFromToken(token);
            String role = jwtService.getRoleFromToken(token);
            System.out.println("Calling getAllStudent with token - Email: " + email + ", Name: " + name + ", Role: " + role);
        }
        return studentService.getAllStudents();
    }

    @GetMapping("/getStudentById/{id}")
    public ResponseGlobal<Student> getStudentById(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null) {
            String email = jwtService.getEmailFromToken(token);
            System.out.println("Calling getStudentById for ID " + id + " - Email: " + email);
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


    @PostMapping("/createAdmin")
    public ResponseGlobal<AdminDTO> createAdmin(@Valid @RequestBody AdminDTO admin){
        return studentService.createAdmin(admin);
    }

}
