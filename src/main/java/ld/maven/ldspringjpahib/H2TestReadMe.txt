H2Test : CRUD, transactions de base
- Player : entité simple
	- Colonnes : int,varchar,date
	- Mapping du nom de la table et de certaines colonnes
- PlayerDao : dao simple
	- Une méthode persist (create) de base
	- Une méthode merge (update)
	- Une méthode find (byId)
	- Une méthode select avec JPQL
- PlayerService : service CRUD transactionnel

Player : 
1) Truncate table
2) Ajout dans Players
3) Liste les joueurs
4) Updates divers (voir code)
5) Suppression
6) Requêtes HQL / Criteria
7) Test avec cache (voir log H2)
Pour le cache : 
	=> conf spring
	=> conf ehcache.xml
	=> annotation de Cache sur Player.java
	=> gérer le volume (maxElementsInMemory) du cache dans ehcache; si une requête apparait dans la log Hibernate et H2 alors le cache n'est pas utilisé
8) Cache requête voir log H2 : test la requête avec et sans cache
	=> conf spring/hibernate (use_query_cache)
	=> conf ehcache org.hibernate.cache.internal.StandardQueryCache
	=> Paramétrage namedQuery dans Entity
	=> Utilisation NamedQuery dans DAO
	
Team : Tests avec un DAO pur Spring Data Jpa, donc pas de code.
Fonctionnalités restreintes.
Nécessité de déclarer dans le fichier de conf spring le repo jpa : 
	<jpa:repositories base-package="ld.maven.ldspringjpahib" entity-manager-factory-ref="entityManagerFactory" />
Derrière sans code accès à des fonctionnalités de base de JPA automatiquement générés par Spring.


	