package com.example.tp4.demo.controller;

import com.example.tp4.demo.domaine.EmpVo;
import com.example.tp4.demo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmpController {
    @Autowired
    private IService service;

    @RequestMapping("/")
    public String showWelcomeFile(Model m) {
        return "index";
    }

    @RequestMapping("/empform")
    public String showform(Model m) {
        m.addAttribute("empVo", new EmpVo());
        return "empform";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("empVo") EmpVo emp) {
        service.save(emp);
        return "redirect:/viewemp";// will redirect to viewemp request mapping
    }

    @RequestMapping("/viewemp")
    public String viewemp(Model m) {
        List<EmpVo> list = service.getEmployees();
        m.addAttribute("list", list);
        return "viewemp";
    }

    @RequestMapping(value = "/editemp/{id}")
    public String edit(@PathVariable Long id, Model m) {
        EmpVo emp = service.getEmpById(id);
        m.addAttribute("empVo", emp);
        return "empeditform";
    }

    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editsave(@ModelAttribute("empVo") EmpVo emp) {
        service.save(emp);
        return "redirect:/viewemp";
    }

    @RequestMapping(value = "/deleteemp/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/viewemp";
    }

    @RequestMapping("/salary/{salary}")
    public String getBySalary(@PathVariable Double salary, Model m) {
        List<EmpVo> list = service.findBySalary(salary);
        m.addAttribute("list", list);
        return "viewemp";
    }

    @RequestMapping("/fonction/{fonction}")
    public String getByFonction(@PathVariable String fonction, Model m) {
        List<EmpVo> list = service.findByFonction(fonction);
        m.addAttribute("list", list);
        return "viewemp";
    }


    @RequestMapping("/salary_and_fonction/{salary}/{fonction}")
    public String getBySalaryAndFonction(@PathVariable Double salary, @PathVariable
            String fonction, Model m) {
        List<EmpVo> list = service.findBySalaryAndFonction(salary, fonction);
        m.addAttribute("list", list);
        return "viewemp";
    }

    @RequestMapping("/max_salary")
    public String getMaxSalary(Model m) {
        EmpVo empVo = service.getEmpHavaingMaxSalary();
        List<EmpVo> list = new ArrayList<>();
        list.add(empVo);
        m.addAttribute("list", list);
        return "viewemp";
    }

    @RequestMapping("/pagination/{pageid}/{size}")
    public String pagination(@PathVariable int pageid, @PathVariable int size, Model m) {
        List<EmpVo> list = service.findAll(pageid, size);
        m.addAttribute("list", list);
        return "viewemp";
    }

    @RequestMapping("/sort/{fieldName}")
    public String sortBy(@PathVariable String fieldName, Model m) {
        List<EmpVo> list = service.sortBy(fieldName);
        m.addAttribute("list", list);
        return "viewemp";
    }
}
