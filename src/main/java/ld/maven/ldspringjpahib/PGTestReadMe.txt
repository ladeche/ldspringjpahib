PGTest : Player, Team : voir H2
JsonPostgres (Equipment) : 
	Entité : Equipment (attributs classiques + Json (JsonEquipmentDetails)
			 JsonEquipmentDetails est un objet Json typé + une Map libre (extension de JsonGenericObject)
			 JsonGenericObject : contient juste une map String,String
		 	 Decoration : sous-object de JsonEquipmentDetails
 	Utils :
 		JsonPostgresType : prend en charge les problématiques de sérialisation/désérialisation du Json et
 			étends la fonctionnalité UserType de Hibernate. Voir http://www.thoughts-on-java.org/persist-postgresqls-jsonb-data-type-hibernate/
			A noter que cette classe devrait être refactorée car il y un lien trop fort entre JsonPostgresType et JsonEquipmentDetails.
		JsonPostgres94Dialect : Dialecte Postgres permettant l'utilisation de type JsonB dans le driver Postgres.
	Repository, Service : rien de neuf. A noter l'utilisation de SQL spécifique Postgres pour Json dans findOneByDetailsMainColor
	PgTest : Divers cas de test (CRUD+recherches spécifiques notamment dans le Json).
