package ld.maven.ldspringjpahib.dao;

import java.util.List;

import javax.persistence.NamedNativeQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ld.maven.ldspringjpahib.model.Equipment;

/*
@NamedNativeQuery(name="findOneByDetailsMainColor"
,query="SELECT e. FROM equipment e.equipment_id,e.equipment_type,e.jsoneq_details "
		+ "where e.jsoneq_details ->> 'mainColor' = '%'"
,resultClass=Equipment.class)
*/
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
	
	public Equipment findOneByType(String type);

	@Query(value="SELECT e.equipment_id,e.equipment_type,e.jsoneq_details FROM equipments e "
		+ "where e.jsoneq_details ->> 'mainColor' = ?1"
			,nativeQuery = true)
	public Equipment findOneByDetailsMainColor (String type);
	
}
