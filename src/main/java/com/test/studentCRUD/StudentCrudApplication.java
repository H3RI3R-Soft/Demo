package com.test.studentCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentCrudApplication.class, args);
		System.out.println("StudentCrudApplication is running on port 5001");
	}

}

/*
What is my problem statement whcih i need to do ?
So i need to Create CRUD Operation for Students.

What details should be there when Creating a Student?
Student will have following details:
1. Student ID (Unique Identifier)
2. Name
3. Age
4. percentage
5. Address

for proceeding with the test cases or test scenarios, you can consider the following CRUD operations for Students:

for Creating a student :
		1. Can we create a student with same name,age,percentage , address ?yes
		2. can a 2 student have same Student ID? No

for Reading a student:
		1. Can we read a student by Student ID? yes
		2. Can we read a student by Name? yes (I can get multiple student if they have same name but i cannot update multiple student with same name )
		3. Can we read a student by Age? yes
		4. Can we read a student by percentage? yes
		5. Can we read a student by Address? yes

for Updating a student:
		1. Can we update a student by Student ID? yes
		2. Can we update a student by Name? no
		3. Can we update a student by Age? no
		4. Can we update a student by percentage? no
		5. Can we update a student by Address? no

for deleting a student:
		1. Can we delete a student by Student ID? yes
		2. Can we delete a student by Name? no
		3. Can we delete a student by Age? no
		4. Can we delete a student by percentage? no
		5. Can we delete a student by Address? no


 */