package ld.maven.ldspringjpahib;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ld.maven.ldspringjpahib.dao.PlayerDao;
import ld.maven.ldspringjpahib.model.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testSpring.xml"})
public class PlayerDaoTest {

/*	@Autowired
	protected PlayerDao playerDao;
	
	private static Date initBirthDate(String birthDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(birthDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

	}
	
	@Test
	public void testPersist() {
		Player player = new Player();
		player.setName("player1");
		player.setBirthDate(initBirthDate("07/06/1990"));
		playerDao.persist(player);
	}

	@Test
	public void testMergePlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testMergePlayerInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFind() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindYoungest() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindYoungestNamedQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOldest() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckIfManaged() {
		fail("Not yet implemented");
	}
*/
}
