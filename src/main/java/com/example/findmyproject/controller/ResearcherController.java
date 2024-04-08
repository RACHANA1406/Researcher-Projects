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
public class ResearcherController {
    @Autowired
    private ResearcherJpaService rjs;

    @GetMapping("/researchers")
    public ArrayList<Researcher> getAllResearchers() {
        return rjs.getAllResearchers();
    }

    @PostMapping("/researchers")
    public Researcher addResearcher(@RequestBody Researcher researcher) {
        return rjs.addResearcher(researcher);
    }

    @GetMapping("researchers/{researcherId}")
    public Researcher getResearcher(@PathVariable("researcherId") int researcherId) {
        return rjs.getResearcher(researcherId);
    }

    @PutMapping("researchers/{researcherId}")
    public Researcher updateResearcher(@PathVariable("researcherId") int researcherId,
            @RequestBody Researcher researcher) {
        return rjs.updateResearcher(researcherId, researcher);
    }

    @DeleteMapping("researchers/{researcherId}")
    public void deleteResearcher(@PathVariable("researcherId") int researcherId) {
        rjs.deleteResearcher(researcherId);
    }

    @GetMapping("/researchers/{researcherId}/projects")
    public List<Project> getResearcherProjects(@PathVariable("researcherId") int researcherId) {
        return rjs.getResearchersProjects(researcherId);
    }
}