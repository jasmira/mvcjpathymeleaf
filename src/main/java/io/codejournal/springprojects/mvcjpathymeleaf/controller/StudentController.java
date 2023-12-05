package io.codejournal.springprojects.mvcjpathymeleaf.controller;

import io.codejournal.springprojects.mvcjpathymeleaf.entity.Student;
import io.codejournal.springprojects.mvcjpathymeleaf.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
public class StudentController {

    static final int DEFAULT_PAGE_SIZE = 2;

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students/")
    public String index() {
        return "redirect:list";
    }

    @GetMapping("/students/list")
    public String getStudents(Model model,
                              @RequestParam(value = "page", defaultValue = "0") int pageNumber,
                              @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE + "") int pageSize) {
        final Page<Student> page = studentService.getStudents(pageNumber, pageSize);

        final int currentPageNumber = page.getNumber();
        final int previousPageNumber = page.hasPrevious() ? currentPageNumber - 1 : -1;
        final int nextPageNumber = page.hasNext() ? currentPageNumber + 1 : -1;

        model.addAttribute("students", page.getContent());
        model.addAttribute("previousPageNumber", previousPageNumber);
        model.addAttribute("nextPageNumber", nextPageNumber);
        model.addAttribute("currentPageNumber", currentPageNumber);
        model.addAttribute("pageSize", pageSize);
        return "students/list";
    }

    @GetMapping("/students/add")
    public String add(Model model) {
        model.addAttribute("student", new Student());
        return "students/add";
    }

    @GetMapping("/students/view")
    public String view(Model model,
                       @RequestParam UUID id) {
        final Optional<Student> record = studentService.getStudent(id);
        model.addAttribute("student", record.isPresent() ? record.get() : new Student());
        model.addAttribute("id", id);
        return "students/view";
    }

    @GetMapping("/students/edit")
    public String edit(Model model,
                       @RequestParam UUID id) {
        final Optional<Student> record = studentService.getStudent(id);
        model.addAttribute("student", record.isPresent() ? record.get() : new Student());
        model.addAttribute("id", id);
        return "students/edit";
    }

    @GetMapping("/students/delete")
    public String delete(Model model,
                         @RequestParam UUID id) {
        final Optional<Student> record = studentService.getStudent(id);
        model.addAttribute("student", record.isPresent() ? record.get() : new Student());
        model.addAttribute("id", id);
        return "students/delete";
    }

    @PostMapping("/students/save")
    public String save(Model model, @ModelAttribute Student student, BindingResult errors) {
        studentService.save(student);
        return "redirect:list";
    }

    @PostMapping("/students/delete")
    public String save(Model model, @RequestParam UUID id) {
        studentService.delete(id);
        return "redirect:list";
    }
}
