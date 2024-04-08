package com.example.findmyproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import com.example.findmyproject.model.Project;
import com.example.findmyproject.model.Researcher;
import com.example.findmyproject.repository.*;

@Service
public class ProjectJpaService implements ProjectRepository {

	@Autowired
	private ProjectJpaRepository pjr;
	@Autowired
	private ResearcherJpaRepository rjr;

	@Override
	public ArrayList<Project> getAllProjects() {
		return new ArrayList<>(pjr.findAll());
	}

	@Override
	public Project addProject(Project project) {

		List<Integer> researcherIds = new ArrayList<>();
		for (Researcher r : project.getResearchers()) {
			researcherIds.add(r.getResearcherId());
		}
		List<Researcher> allResearchers = rjr.findAllById(researcherIds);
		if (allResearchers.size() != researcherIds.size()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		project.setResearchers(allResearchers);
		for (Researcher r : allResearchers) {
			r.getProjects().add(project);
		}
		Project p = pjr.save(project);
		rjr.saveAll(allResearchers);
		return p;
	}

	@Override
	public Project getProject(int projectId) {
		try {
			Project p = pjr.findById(projectId).get();
			return p;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Project updateProject(int projectId, Project project) {
		try {
			Project p = pjr.findById(projectId).get();
			if (project.getProjectName() != null) {
				p.setProjectName(project.getProjectName());
			}
			if (project.getBudget() != 0.0) {
				p.setBudget(project.getBudget());
			}
			if (project.getResearchers() != null) {
				List<Researcher> rs = p.getResearchers();
				for (Researcher r : rs) {
					r.getProjects().remove(p);
				}
				rjr.saveAll(rs);
				List<Integer> rIds = new ArrayList<>();
				for (Researcher r : project.getResearchers()) {
					rIds.add(r.getResearcherId());
				}
				List<Researcher> rss = rjr.findAllById(rIds);
				if (rss.size() != rIds.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}
				for (Researcher t : rss) {
					t.getProjects().add(p);
				}
				rjr.saveAll(rss);
				p.setResearchers(rss);
			}
			return pjr.save(p);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override

	public void deleteProject(int projectId) {
		try {
			Project p = pjr.findById(projectId).get();
			List<Researcher> rr = p.getResearchers();
			for (Researcher r : rr) {
				r.getProjects().remove(p);
			}
			rjr.saveAll(rr);
			pjr.deleteById(projectId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);

	}

	@Override
	public List<Researcher> getProjectResearchers(int projectId) {
		try {
			Project p = pjr.findById(projectId).get();
			return p.getResearchers();

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
