package ld.maven.ldspringjpahib.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import ld.maven.ldspringjpahib.model.Player;
import ld.maven.ldspringjpahib.model.Player_;

@Repository
@NamedQuery(name="player.findYoungest"
,query="SELECT p FROM Player p WHERE p.birthDate = (SELECT MAX(p2.birthDate) FROM Player p2)"
// ,hints={@QueryHint(name="org.hibernate.cacheable",value="true"))}
)
public class PlayerDao {
	private static final Logger LOG = LoggerFactory.getLogger(PlayerDao.class);
	
	@PersistenceContext
	private EntityManager em;

	public void persist (Player player) {
		em.persist(player);
	}

	public Player merge (Player player) {
		// on fait un merge car à priori le player passé est detached
		LOG.debug("Managed(Dao)>"+this.checkIfManaged(player));
		return (Player)em.merge(player);
	}
	
	public Player merge(Player player, Integer id) {
		Player playerPersisted = this.find(id);
		playerPersisted.setBirthDate(player.getBirthDate());
		playerPersisted.setName(player.getName());
		LOG.debug("playerPersisted Managed(Dao)>"+this.checkIfManaged(player));
		// Pas besoin de merge car le playerPersisted est Managed
		return playerPersisted;
	}
	
	public void remove(Player player) {
		LOG.debug("Managed(Dao)>"+this.checkIfManaged(player));
		em.remove(find(player.getId())); // effectue le select; le delete est effectué au dernier moment en fin de transaction
	}

	public void removeAll() {
		String tableName = Player.class.getAnnotation(Table.class).name();
		Query q = em.createNativeQuery("TRUNCATE TABLE "+tableName); // SQL
		q.executeUpdate();
	}

	
	@Cacheable(value="playerFindCache", key="#id")
	public Player find (Integer id) {
		return (Player) em.find(Player.class, id);
	}
	
	public List<Player> findAll() {
		return em.createQuery("SELECT p FROM Player p").getResultList(); // JPQL
	}
	

	public Player findYoungest() {
		// JPQL
		return (Player)em.createQuery("SELECT p FROM Player p WHERE p.birthDate = (SELECT MAX(p2.birthDate) FROM Player p2)").getSingleResult();
		
	}

	public Player findYoungestNamedQuery() {
		// JPQL avec NamedQuery indispensable pour utilisation Cache
		return (Player)em.createNamedQuery("player.findYoungest").getSingleResult();
	}

	
	public Player findOldest() {
		// Criteria
		CriteriaBuilder cb = em.getCriteriaBuilder();
		// On recherche d'abord le max
		CriteriaQuery<Date> cqmin = cb.createQuery(Date.class);
		Root<Player> rootmin = cqmin.from(Player.class);
		cqmin.select(cb.least(rootmin.get(Player_.birthDate)));
		TypedQuery<Date> tqmax = em.createQuery(cqmin);
		Date minDate = tqmax.getSingleResult();
		LOG.debug("Date"+minDate);
		
		// Puis on recherche le joueur correspondant
		CriteriaQuery<Player> cq = cb.createQuery(Player.class);
		Root<Player> root = cq.from(Player.class);
		cq.where(cb.equal(root.get("birthDate"), minDate));
		TypedQuery<Player> tq = em.createQuery(cq);
		return tq.getSingleResult();
		
	}
	
	public boolean checkIfManaged (Player player) {
		return em.contains(player);
	}

	
}
