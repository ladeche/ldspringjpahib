package ld.maven.ldspringjpahib;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Query;
import javax.persistence.Table;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ld.maven.ldspringjpahib.model.Player;
import ld.maven.ldspringjpahib.model.Team;
import ld.maven.ldspringjpahib.service.PlayerService;
import ld.maven.ldspringjpahib.service.TeamService;

public class H2Test {
	
	private static final Logger LOG = LoggerFactory.getLogger(H2Test.class);

	private static ClassPathXmlApplicationContext ctx;

	public static void main(String[] args) {
		
		// Classpath
		ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		playerTest();
		teamTest();

		ctx.close();
		
		LOG.info("************** END *********************");

	}
	
	private static void playerTest() {
		PlayerService playerService = ctx.getBean(PlayerService.class);
		
		// Nettoyage de la table
		LOG.info("************** TRUNCATE *********************");
		playerService.removeAll();
		
		LOG.info("************* AJOUT JOUEURS ********************");
		String pl[] = {"Ronaldo","Messi","Griezmann","Bale","Akinfeev","Hazard","Neuer","Cana","Perisic","Rooney"};
		Player player;
		Random r = new Random();
		ArrayList<Player> listOfPlayers = new ArrayList<Player>();
		for (int i =0; i<pl.length; i++) {
			player = new Player();
			player.setName(pl[i]);
			Integer bd = r.nextInt(1000000000);
			player.setBirthDate(new Date(bd.longValue()*100));
			LOG.info(player.toString());
			playerService.add(player);
			LOG.info("Manage(Main):"+playerService.checkIfManaged(player));
			listOfPlayers.add(player);
		}
		//SQL:insert into PLAYERS (player_id, birth_date, name, version) values (null, ?, ?, ?) {1: DATE '1972-11-14', 2: 'Ronaldo', 3: 0};
		
		LOG.info("************* LISTE JOUEURS ********************");
		List<Player> list = playerService.listAll();
		list.forEach(p->LOG.info(p.toString()+"..."+playerService.checkIfManaged(p)));
		//SQL:/select player0_.player_id as player_i1_0_, player0_.birth_date as birth_da2_0_, player0_.name as name3_0_, player0_.version as version4_0_ from PLAYERS player0_;
		
		LOG.info("************* MAJ JOUEURS ********************");
		LOG.info(">>> Update by Id : on passe le joueur (sans id) et un id cible, et on écrase avec le nouveau joueur en base");
		Player plUpByIpd = new Player();
		plUpByIpd.setName("Ronaldo2");
		plUpByIpd.setBirthDate(new Date(1000000000L)); // 12-01-1970
		LOG.info(plUpByIpd.toString());
		playerService.update(plUpByIpd, listOfPlayers.get(0).getId());
		//SQL:select player0_.player_id as player_i1_0_0_, player0_.birth_date as birth_da2_0_0_, player0_.name as name3_0_0_, player0_.version as version4_0_0_ from PLAYERS player0_ where player0_.player_id=? {1: 531};
		//SQL:update PLAYERS set birth_date=?, name=?, version=? where player_id=? and version=? {1: DATE '1970-01-12', 2: 'Ronaldo2', 3: 1, 4: 531, 5: 0};

		LOG.info(">>> Update simple : on a un joueur complet (id compris) et on met à jour");
		LOG.info(">>"+playerService.checkIfManaged(listOfPlayers.get(1)));
		Player plUpSimple = playerService.findById(listOfPlayers.get(1).getId());
		//SQL:select player0_.player_id as player_i1_0_0_, player0_.birth_date as birth_da2_0_0_, player0_.name as name3_0_0_, player0_.version as version4_0_0_ from PLAYERS player0_ where player0_.player_id=? {1: 532};
		plUpSimple.setName("Messi2");
		plUpSimple.setBirthDate(new Date(2000000000L)); // 24-01-1970
		playerService.update(plUpSimple);
		//SQL:select player0_.player_id as player_i1_0_0_, player0_.birth_date as birth_da2_0_0_, player0_.name as name3_0_0_, player0_.version as version4_0_0_ from PLAYERS player0_ where player0_.player_id=? {1: 532};
		//SQL:update PLAYERS set birth_date=?, name=?, version=? where player_id=? and version=? {1: DATE '1970-01-24', 2: 'Messi2', 3: 1, 4: 532, 5: 0};

		LOG.info("************* SUPPRESSION JOUEURS ********************");
		playerService.delete(listOfPlayers.get(2));
		//SQL:select player0_.player_id as player_i1_0_0_, player0_.birth_date as birth_da2_0_0_, player0_.name as name3_0_0_, player0_.version as version4_0_0_ from PLAYERS player0_ where player0_.player_id=? {1: 533};
		//SQL:delete from PLAYERS where player_id=? and version=? {1: 533, 2: 0};
		
		LOG.info("************* REQUETE SPECIALE ********************");
		Player youngest = playerService.findYoungest();
		//SQL:select player0_.player_id as player_i1_0_, player0_.birth_date as birth_da2_0_, player0_.name as name3_0_, player0_.version as version4_0_ from PLAYERS player0_ where player0_.birth_date=(select max(player1_.birth_date) from PLAYERS player1_);
		LOG.info(youngest.toString());
		Player oldest = playerService.findOldest();
		LOG.info(oldest.toString());
		//SQL:select min(player0_.birth_date) as col_0_0_ from PLAYERS player0_;
		//SQL:select player0_.player_id as player_i1_0_, player0_.birth_date as birth_da2_0_, player0_.name as name3_0_, player0_.version as version4_0_ from PLAYERS player0_ where player0_.birth_date=? {1: DATE '1970-01-12'};

		LOG.info("************* CACHE NIVEAU 2 ********************");
		// Recherche dans le cache avant de retourner en base
		// Contrôler avec les logs de la base
		for (int i=pl.length-1; i>=0; i=i-1) {
			player = playerService.findById(listOfPlayers.get(i).getId());
			LOG.info((player==null) ? "Id "+listOfPlayers.get(i).getId()+"Inconnu" : player.toString());
		}
		
		LOG.info("************* CACHE REQUETE ********************");
		youngest = playerService.findYoungestNamedQuery();
		LOG.info(youngest.toString());
		youngest = playerService.findYoungestNamedQuery();
		LOG.info(youngest.toString());
		youngest = playerService.findYoungestNamedQuery();
		LOG.info(youngest.toString());
		youngest = playerService.findYoungest();
		LOG.info(youngest.toString());
		
	}

	private static void teamTest() {
		TeamService teamService = ctx.getBean(TeamService.class);
		
		
		
		// Nettoyage de la table
		LOG.info("************** TEAM REPOSITORY *********************");

		Team team = new Team();
		team.setName("RC Lens");
		team.setId(1);
		// insert seulement s'il n'existe pas déjà
		teamService.add(team);

		team.setName("Le Havre AC");
		team.setId(2);
		// insert seulement s'il n'existe pas déjà
		teamService.add(team);

		team=teamService.findById(1);
		LOG.info(team.toString());
		team=teamService.findById(2);
		LOG.info(team.toString());
	}
	
}
