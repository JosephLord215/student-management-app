package com.lordlord.studentmanagement.controller;

import com.lordlord.studentmanagement.exception.ResourceNotFoundException;
import com.lordlord.studentmanagement.model.Student;
import com.lordlord.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;


    @GetMapping("/students")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    // create employee rest api
    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) { return studentRepository.save(student);
    }

    // get employee by id rest api
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getEmployeeById(@PathVariable Long id) {
       Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id :" + id));
        return ResponseEntity.ok(student);
    }

    // update employee rest api

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Students not exist with id :" + id));

        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmailId(studentDetails.getEmailId());

        Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    // delete employee rest api
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not exist with id :" + id));

        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
