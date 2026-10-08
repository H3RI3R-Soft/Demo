package com.test.studentCRUD.Student.Service;

import com.test.studentCRUD.Configuration.ResponseGlobal;
import com.test.studentCRUD.Configuration.SecurityConfig;
import com.test.studentCRUD.Student.DTO.AdminDTO;
import com.test.studentCRUD.Student.DTO.StudentDto;
import com.test.studentCRUD.Student.ENUM.RoleEnum;
import com.test.studentCRUD.Student.Entity.Address;
import com.test.studentCRUD.Student.Entity.Admin;
import com.test.studentCRUD.Student.Entity.Login;
import com.test.studentCRUD.Student.Entity.Student;
import com.test.studentCRUD.Student.Repo.AdminRepo;
import com.test.studentCRUD.Student.Repo.LoginRepository;
import com.test.studentCRUD.Student.Repo.StudentRepos;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepos studentRepos;
    private final LoginRepository loginRepository;
    private final AdminRepo adminRepo;
    private final SecurityConfig securityConfig;
    private final String secretKey = "uebefubqrufbhrbqburfuiqbefiubqinbf"; // Replace with

    public ResponseGlobal<StudentDto> createStudent(StudentDto student) {

        //validations
        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            return ResponseGlobal.onError("Email is required.");
        }

        //another validation for email uniqueness
        Student existingStudent = studentRepos.findByEmail(student.getEmail());
        System.out.println("This is my Requested Email " +student.getEmail()+" And this is my Existing Email object returned from Db  "+existingStudent);

        if (existingStudent != null){
            return ResponseGlobal.onFailure("This Student email is being attached with another Student Email");
        }


        Student newStudent = new Student();
        newStudent.setName(student.getName());
        newStudent.setAge(student.getAge());
        newStudent.setPercentage(student.getPercentage());
        newStudent.setEmail(student.getEmail());
        newStudent.setStatus(true);

        Address address = new Address();
        address.setCity(student.getCity());
        address.setState(student.getState());
        address.setPincode(student.getPincode());
        newStudent.setAddress(address);
        studentRepos.save(newStudent);

        Login login = new Login();
        login.setPassword(student.getPassword());
        login.setEmail(student.getEmail());
        login.setName(student.getName());
        loginRepository.save(login);


        return ResponseGlobal.onSuccess("Student and login details are being set for Student.",student);

    }

    public ResponseGlobal<Student> getStudentById(Long id) {
        try{
            Student student = studentRepos.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
            return ResponseGlobal.onSuccess("Student retrieved successfully.", student);
        }
        catch (Exception e){
            throw new RuntimeException("Error retrieving student with id: " + id, e);

        }
    }

    public ResponseGlobal<List<Student>> getAllStudents() {

        List<Student> students = studentRepos.findAll();
        if (students.isEmpty()) {
            return ResponseGlobal.onFailure("No students found.");
        }
        return ResponseGlobal.onSuccess("Students retrieved successfully.", students);
    }


    //List is a Collection of Object or it is a format for object to be stored in a collection format.

    public ResponseGlobal<Student>  updateStudent (Long id , Student student ){

        if(id == null ){
            return null;
        } //suppose that we have student for like 1,3,5 id  we don not have 6 id for students table
        Student existingStudent = studentRepos.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setPercentage(student.getPercentage());
//        existingStudent.setEmail(student.getEmail());
        Student existingEmail =studentRepos.findByEmail(student.getEmail());

        if (existingEmail != null){
            return ResponseGlobal.onFailure("This Student email is being attached with another Student Email");
        }

        return ResponseGlobal.onSuccess("Student has been Updated well.",studentRepos.save(existingStudent));
    }
    public String deleteStudent(Long id) {
        if (!studentRepos.existsById(id)) {
            return "Student with id " + id + " does not exist.";
        }
        studentRepos.deleteById(id);
        return "Student with id " + id + " has been deleted successfully.";
    }


    public ResponseGlobal<AdminDTO> createAdmin(AdminDTO adminDTO) {

        if (adminDTO.getEmail() == null || adminDTO.getEmail().isEmpty()) {
            return ResponseGlobal.onFailure("Email is required for Admin creation.");
        }
        if (adminDTO.getPassword() == null || adminDTO.getPassword().isEmpty()) {
            return ResponseGlobal.onFailure("Password is required for Admin creation.");
        }
        if (adminDTO.getSecretKey() == null || adminDTO.getSecretKey().isEmpty()) {
            return ResponseGlobal.onFailure("Secret Key is required for Admin creation.");
        }
        if (!adminDTO.getSecretKey().equals(secretKey)) {
            return ResponseGlobal.onFailure("Invalid Secret Key provided for Admin creation.");
        }

        boolean ifEmailExists = loginRepository.existsByEmail(adminDTO.getEmail());

        if(ifEmailExists){
            return ResponseGlobal.onFailure("This Email is already being used by another Admin or Student.");
        }
        Admin admin = new Admin();
        admin.setEmail(adminDTO.getEmail());
        admin.setName(adminDTO.getName());
        adminRepo.save(admin);

        Login login = new Login();
        login.setEmail(adminDTO.getEmail());
        login.setPassword(adminDTO.getPassword());
        login.setName(adminDTO.getName());
        login.setRole(String.valueOf(RoleEnum.ADMIN));
        loginRepository.save(login);

        return ResponseGlobal.onSuccess("Admin has been created successfully.",adminDTO);

    }
}




//Http Request Codes

//There are 5 group s of HTTP status codes, each representing a different category of response.
// These groups are:

//1xx Informational responses. Like - Request has been Received and server is continuing to process it .

//2xx Successs. its Like  - Request was successfully recived and server understood and accepted it .

//3xx Redirection. its Like - Further action needs to be taken in order to complete the request.

//4xx Client errors. its like - the request has a problem on the client side and server recieved the request but the request cannot be successfully processed or executed.

//5xx Server errors. where the request was valid but the server failed to fulfill it.
// 500 internal server error
