package ld.maven.ldspringjpahib;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ld.maven.ldspringjpahib.dao.PlayerDao;
import ld.maven.ldspringjpahib.model.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testSpring.xml"})
@Transactional // toutes les méthodes de test sont transactionnelles
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerDaoTest {
	private static final Logger LOG = LoggerFactory.getLogger(PlayerDao.class);

	@Autowired
	protected PlayerDao playerDao;
	
	private static ArrayList<Player> initPlayerList = new ArrayList<Player>();
	private static Calendar cal = Calendar.getInstance();
	private static int testnb = 0;

	
	@BeforeClass
	public static void initForTestSuite () {
		Random r = new Random();
		Integer j = 0;
		Player player;
		for (int i = 0; i < 10; i++) {
			j = r.nextInt(1000000000);
			player = new Player();
			player.setName("player"+j);
			cal.setTime(new Date(j.longValue()*100));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			player.setBirthDate(cal.getTime());
			initPlayerList.add(player);
		}
	}
	
	private static Player clonePlayerFromInitList (int index) {
		Player player = new Player();
		player.setName(initPlayerList.get(index).getName());
		player.setBirthDate(initPlayerList.get(index).getBirthDate());
		return player;
	}
	
	@Before
	public void logging () {
		testnb++;
		LOG.debug("**** TEST #"+testnb+" ****");
	}
	
	
	@Test
	@Rollback(false) // On veut committer
	public void a1RemoveAll() {
		playerDao.removeAll();
		assertTrue(playerDao.findAll().isEmpty());
	}
	
	
	@Test
	@Rollback(false) // On veut committer
	public void b1CreateNewRandomPlayer() {
		Player player = clonePlayerFromInitList(0);
		playerDao.persist(player);
		playerDao.flush(); // Sans cela rien n'est poussé à la base
	}

	@Test (expected=javax.persistence.PersistenceException.class)
	@Rollback(true) // On veut rollbacker
	public void b2CreateDuplicatePlayer() {
		Player player = clonePlayerFromInitList(0);
		playerDao.persist(player);
		playerDao.flush(); 
	}

	@Test
	public void b3GetPlayerByName() {
		Player player = playerDao.findByName(initPlayerList.get(0).getName());
		assertTrue(player.getName().equals(initPlayerList.get(0).getName()));
		assertTrue(player.getBirthDate().getTime() == initPlayerList.get(0).getBirthDate().getTime());
	}

	@Test
	@Rollback(false) // On veut committer
	public void b4UpdatePlayerBirthDate() {
		Player player = playerDao.findByName(initPlayerList.get(0).getName());
		// Change birth date
		cal.setTime(player.getBirthDate());
		cal.add(Calendar.DATE , 1);
		player.setBirthDate(cal.getTime());
		// merge
		playerDao.merge(player);
		
		assertTrue(playerDao.checkIfManaged(player));
		assertTrue(player.getBirthDate().getTime() == initPlayerList.get(0).getBirthDate().getTime()+86400000);
	}
	
	@Test
	@Rollback(false) // On veut committer
	public void b5UpdatePlayerBirthDateWhenNotManaged() {
		Player player = playerDao.findByName(initPlayerList.get(0).getName());
		Player playerNotManaged = clonePlayerFromInitList(0);
		// Change birth date
		cal.setTime(initPlayerList.get(0).getBirthDate());
		cal.add(Calendar.DATE , 2);
		playerNotManaged.setBirthDate(cal.getTime());
		// merge
		Player playerManaged = playerDao.merge(playerNotManaged,player.getId());
		assertTrue(playerDao.checkIfManaged(playerManaged));
		assertTrue(player.toString().equals(playerManaged.toString()));
	}
	
	
	@Test (expected=javax.persistence.NoResultException.class)
	@Rollback(false)
	public void b6RemoveOneThatExists() {
		Player player = playerDao.findByName(initPlayerList.get(0).getName());
		playerDao.remove(player);
		player = playerDao.findByName(initPlayerList.get(0).getName());
	}
	
	@Test
	@Rollback(false) // On veut committer
	public void c1CreateAllPlayers() {
		for (int i=0; i<10; i++) {
			Player player = clonePlayerFromInitList(i);
			playerDao.persist(player);
		}
		playerDao.flush(); // Sans cela rien n'est poussé à la base
	}

	

}
