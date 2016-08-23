package ld.maven.ldspringjpahib;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ld.maven.ldspringjpahib.dao.PlayerDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testSpring.xml" })
public class InitDaoTest {
	/*

	@Autowired
	protected PlayerDao playerDao;
*/
}
