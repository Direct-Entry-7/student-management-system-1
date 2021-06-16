package service;

import model.Student;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public class StudentServiceTest {

    /*
     * Syntax of the assert
     * assert <boolean expression> [:"string message"]
     *  Test Driven Development
     */

    public static void main(String[] args) throws NotFoundException, DuplicateEntryException {
        saveStudent();
        updateStudent();
        deleteStudent();
        findAllStudents();
        findStudents();
    }

    public static void saveStudent() throws DuplicateEntryException, NotFoundException {
        StudentService studentService = new StudentService();
        Student s = new Student("123456789V", "Dhanushka Chandimal", "address", LocalDate.now(), "077-1234567", "abc@ijse.lk");
        Student s2 = new Student("213456789V", "Manoj Randeni", "address", LocalDate.now(), "077-1234567", "abc@ijse.lk");
        studentService.saveStudent(s);
        studentService.saveStudent(s2);
        assert studentService.findStudent("123456789V") != null : "failed save test";
        assert studentService.findStudent("213456789V") != null : "failed save test";
    }

    public static void updateStudent() throws NotFoundException {
        StudentService studentService = new StudentService();
        Student s = new Student("123456789V", "New Student", "address", LocalDate.now(), "077-1234567", "abc@ijse.lk");
        studentService.updateStudent(s);
        assert studentService.findStudent("123456789V") != null: "failed update test";
        assert studentService.findStudent("123456789V").getFullName().equals("New Student") : "failed update test";
    }

    public static void deleteStudent() throws NotFoundException {
        StudentService studentService = new StudentService();
        studentService.deleteStudent("213456789V");
        assert studentService.findStudent("213456789V") == null: "failed delete test";
    }

    public static void findAllStudents(){
        StudentService studentService = new StudentService();
        assert studentService.findAllStudents().size() == 1: "failed findAllStudents test";
    }

    public static void findStudents() throws DuplicateEntryException {
        Student s1 = new Student("456789123V", "Chandima Herath", "Galle", LocalDate.of(1996,05,01), "077-1234567", "abc@ijse.lk");
        Student s2 = new Student("879456123V", "Pethum Jeewantha", "Matara", LocalDate.of(1989,10,01), "077-456789", "pethum@hotmail.lk");
        Student s3 = new Student("456132789V", "Dilan Chathuranga", "Panadura", LocalDate.now(), "077-1234567", "dilan@ijse.lk");
        Student s4 = new Student("879456123V", "Pethum Nuwan", "Matara", LocalDate.of(1989,10,01), "077-456789", "pethum@ijse.lk");
        StudentService studentService = new StudentService();
        studentService.saveStudent(s1);
        studentService.saveStudent(s2);
        studentService.saveStudent(s3);
        studentService.saveStudent(s4);
        List<Student> result = studentService.findStudents("ijse.lk");
        for (Student student : result) {
            System.out.println(student);
        }
        assert result.size() >= 3: "failed findStudents test";
    }
}