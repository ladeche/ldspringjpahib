package ld.maven.ldspringjpahib.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="PLAYERS")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)

@NamedQuery(name="player.findYoungest"
,query="SELECT p FROM Player p WHERE p.birthDate = (SELECT MAX(p2.birthDate) FROM Player p2)"
,hints={@QueryHint(name="org.hibernate.cacheable",value="true")}
)

public class Player {
	
	@Id
	@Column(name="player_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name="birth_date")
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@Version
	private Integer version;

	
	public Player() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Player : "+id+" | "+name+" | "+birthDate;
	}

}
