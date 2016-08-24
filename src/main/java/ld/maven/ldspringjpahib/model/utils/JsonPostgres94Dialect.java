package ld.maven.ldspringjpahib.model.utils;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class JsonPostgres94Dialect extends PostgreSQL94Dialect {
	
	public JsonPostgres94Dialect() {
		// 9.4 and above
		//this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
		
		// 9.3 and below
		this.registerColumnType(Types.JAVA_OBJECT, "json");
	}

}
