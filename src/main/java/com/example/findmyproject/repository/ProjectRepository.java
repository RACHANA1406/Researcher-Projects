package com.example.findmyproject.repository;

import java.util.*;
import com.example.findmyproject.model.*;

public interface ProjectRepository {
    ArrayList<Project> getAllProjects();

    Project addProject(Project project);

    Project getProject(int projectId);

    Project updateProject(int projectId, Project project);

    void deleteProject(int projectId);

    List<Researcher> getProjectResearchers(int projectId);
}