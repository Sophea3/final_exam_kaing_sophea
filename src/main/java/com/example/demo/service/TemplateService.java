package com.example.demo.service;



import com.example.demo.model.Template;
import com.example.demo.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }
}
