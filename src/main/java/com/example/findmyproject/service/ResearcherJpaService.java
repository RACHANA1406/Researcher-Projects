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
public class ResearcherJpaService implements ResearcherRepository {
	@Autowired
	private ResearcherJpaRepository rjr;
	@Autowired
	private ProjectJpaRepository pjr;

	@Override
	public ArrayList<Researcher> getAllResearchers() {
		// TODO Auto-generated method stub
		return new ArrayList<>(rjr.findAll());
	}

	@Override
	public Researcher addResearcher(Researcher researcher) {
		List<Integer> pIds = new ArrayList<>();
		for (Project p : researcher.getProjects()) {
			pIds.add(p.getProjectId());
		}
		List<Project> prs = pjr.findAllById(pIds);
		if (prs.size() != pIds.size()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		researcher.setProjects(prs);
		// for (Project rr : prs) {
		// rr.getResearchers().add(researcher);
		// }
		// pjr.saveAll(prs);

		return rjr.save(researcher);
	}

	@Override
	public Researcher getResearcher(int researcherId) {
		try {
			Researcher r = rjr.findById(researcherId).get();
			return r;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public Researcher updateResearcher(int researcherId, Researcher researcher) {
		try {
			Researcher r = rjr.findById(researcherId).get();
			if (researcher.getResearcherName() != null) {
				r.setResearcherName(researcher.getResearcherName());
			}
			if (researcher.getSpecialization() != null) {
				r.setSpecialization(researcher.getSpecialization());
			}
			if (researcher.getProjects() != null) {
				List<Project> p = r.getProjects();
				for (Project pp : p) {
					pp.getResearchers().remove(r);
				}
				pjr.saveAll(p);
				List<Integer> pIds = new ArrayList<>();
				for (Project newp : researcher.getProjects()) {
					pIds.add(newp.getProjectId());
				}
				List<Project> newprojects = pjr.findAllById(pIds);
				if (newprojects.size() != pIds.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}
				for (Project ppp : newprojects) {
					ppp.getResearchers().add(r);
				}
				pjr.saveAll(newprojects);
				r.setProjects(newprojects);
			}
			return rjr.save(r);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteResearcher(int researcherId) {
		try {
			Researcher r = rjr.findById(researcherId).get();
			List<Project> p = r.getProjects();
			for (Project pp : p) {
				pp.getResearchers().remove(r);
			}
			pjr.saveAll(p);
			rjr.deleteById(researcherId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);

	}

	@Override
	public List<Project> getResearchersProjects(int researcherId) {
		try {
			Researcher r = rjr.findById(researcherId).get();
			return r.getProjects();

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

}