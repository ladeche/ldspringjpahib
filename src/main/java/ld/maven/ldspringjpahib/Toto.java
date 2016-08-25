package ld.maven.ldspringjpahib;

import ld.maven.ldspringjpahib.model.Equipment;
import ld.maven.ldspringjpahib.model.subobjects.JsonEquipmentDetails;

public class Toto {
	String a = "toto";
	String b;
	public static void main(String[] args) {
		Toto toto = new Toto();
		System.out.println(toto.a+toto.b);
		System.out.println(toto.toString());
		
		Equipment e = new Equipment();
		e.setId(1);
		e.setType("t");
		e.setJsonEquipmentDetails(new JsonEquipmentDetails());
		System.out.println(e.toString());

	}
	
	@Override
	public String toString () {
		return a+"-"+b;
	}

}
