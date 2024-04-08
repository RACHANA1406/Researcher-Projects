/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.findmyproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import com.example.findmyproject.model.*;
import com.example.findmyproject.service.*;

@RestController
public class ProjectController {
    @Autowired
    private ProjectJpaService pjs;

    @GetMapping("/researchers/projects")
    public ArrayList<Project> getAllProjects() {
        return pjs.getAllProjects();
    }

    @PostMapping("/researchers/projects")
    public Project addProject(@RequestBody Project project) {
        return pjs.addProject(project);
    }

    @GetMapping("/researchers/projects/{projectId}")
    public Project getProject(@PathVariable("projectId") int projectId) {
        return pjs.getProject(projectId);
    }

    @PutMapping("/researchers/projects/{projectId}")
    public Project updateProject(@PathVariable("projectId") int projectId, @RequestBody Project project) {
        return pjs.updateProject(projectId, project);
    }

    @DeleteMapping("/researchers/projects/{projectId}")
    public void deleteProject(@PathVariable("projectId") int projectId) {
        pjs.deleteProject(projectId);
    }

    @GetMapping("/projects/{projectId}/researchers")
    public List<Researcher> getProjectResearchers(@PathVariable("projectId") int projectId) {
        return pjs.getProjectResearchers(projectId);
    }
}