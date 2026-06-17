package com.example.demo.controller;


import com.example.demo.model.Template;
import com.example.demo.service.TemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public List<Template> getAllTemplates() {
        return templateService.getAllTemplates();
    }

    @PostMapping
    public Template createTemplate(@RequestBody Template template) {
        return templateService.createTemplate(template);
    }
}