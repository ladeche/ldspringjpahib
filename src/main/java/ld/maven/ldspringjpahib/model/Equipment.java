package ld.maven.ldspringjpahib.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.core.style.ToStringCreator;

import ld.maven.ldspringjpahib.model.subobjects.JsonEquipmentDetails;

@Entity
@Table(name = "EQUIPMENTS")
@TypeDef(name = "JsonPostgresType", typeClass = ld.maven.ldspringjpahib.model.utils.JsonPostgresType.class)
public class Equipment {

	@Id
	@Column(name = "EQUIPMENT_ID")
	//@GeneratedVaue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "EQUIPMENT_TYPE")
	private String type;

	
	@Column(name = "JSONEQ_DETAILS")
	@Type(type = "JsonPostgresType")
	private JsonEquipmentDetails jsonEquipmentDetails;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JsonEquipmentDetails getJsonEquipmentDetails() {
		return jsonEquipmentDetails;
	}

	public void setJsonEquipmentDetails(JsonEquipmentDetails jsonEquipmentDetails) {
		this.jsonEquipmentDetails = jsonEquipmentDetails;
	}

	@Override
	public String toString() {
		String string = "JsonEquipment : " + id 
				+ " , " + type;
		string = string + " , [ "+ (jsonEquipmentDetails==null?"null":jsonEquipmentDetails.toString()) + " ] ";
		return string; 
	}

}
