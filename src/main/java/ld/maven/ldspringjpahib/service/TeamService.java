package ld.maven.ldspringjpahib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ld.maven.ldspringjpahib.dao.TeamRepository;
import ld.maven.ldspringjpahib.model.Player;
import ld.maven.ldspringjpahib.model.Team;

@Component
public class TeamService {
	
	@Autowired
	private TeamRepository teamRepository;
	
	// CREATE
	@Transactional
	public void add(Team team) {
		teamRepository.save(team);
	}

	@Transactional(readOnly=true)
	public Team findById(Integer id) {
		return teamRepository.findOne(id);
	}


}
