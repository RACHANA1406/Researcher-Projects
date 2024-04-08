package com.example.findmyproject.repository;

import java.util.*;
import com.example.findmyproject.model.*;

public interface ResearcherRepository {
    ArrayList<Researcher> getAllResearchers();

    Researcher addResearcher(Researcher researcher);

    Researcher getResearcher(int researcherId);

    Researcher updateResearcher(int researcherId, Researcher researcher);

    void deleteResearcher(int researcherId);

    List<Project> getResearchersProjects(int researcherId);
}