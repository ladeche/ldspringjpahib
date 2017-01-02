package ld.maven.ldspringjpahib.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ld.maven.ldspringjpahib.dao.PlayerDao;
import ld.maven.ldspringjpahib.model.Player;

@Component
public class PlayerService {
	private static final Logger LOG = LoggerFactory.getLogger(PlayerService.class);
	
	@Autowired
	private PlayerDao playerDao;

	// CREATE
	@Transactional
	public void add(Player player) {
		playerDao.persist(player);
		LOG.debug("Managed(PlayerService):"+playerDao.checkIfManaged(player));
	}

	// UPDATE
	@Transactional
	public Player update(Player player) {
		return (Player)playerDao.merge(player);
	}

	@Transactional
	public Player update(Player player, Integer id) {
		return (Player)playerDao.merge(player, id);
	}

	// DELETE
	//@Transactional
	public void delete(Player player) {
		LOG.debug("Managed(PlayerService):"+playerDao.checkIfManaged(player));
		playerDao.remove(player);
		LOG.debug("Managed(PlayerService):"+playerDao.checkIfManaged(player));
	}
	@Transactional
	public void removeAll() {
		playerDao.removeAll();
	}
	
	// SELECT
	@Transactional(readOnly=true)
	public Player findById(Integer id) {
		return playerDao.find(id);
	}

	@Transactional(readOnly=true)
	public List<Player> listAll() {
		return playerDao.findAll();
	}
	
	@Transactional(readOnly=true)
	public Player findYoungest() {
		return playerDao.findYoungest();
	}
	@Transactional(readOnly=true)
	public Player findYoungestNamedQuery() {
		return playerDao.findYoungestNamedQuery();
	}
	@Transactional(readOnly=true)
	public Player findOldest() {
		return playerDao.findOldest();
	}

	
	public boolean checkIfManaged(Player player) {
		return playerDao.checkIfManaged(player);
	}

}
