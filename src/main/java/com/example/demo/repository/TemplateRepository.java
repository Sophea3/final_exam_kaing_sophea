package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    boolean existsByTemplateName(String templateName);

}