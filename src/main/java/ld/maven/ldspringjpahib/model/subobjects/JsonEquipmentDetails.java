package ld.maven.ldspringjpahib.model.subobjects;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JsonEquipmentDetails extends JsonGenericObject {

	private String mainColor;
	private String size;
	private Decoration decoration;
	private Map<String,String> miscFeatures;

	public String getMainColor() {
		return mainColor;
	}


	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public Decoration getDecoration() {
		return decoration;
	}


	public void setDecoration(Decoration decoration) {
		this.decoration = decoration;
	}


	public Map<String, String> getMiscFeatures() {
		return miscFeatures;
	}


	public void setMiscFeatures(Map<String, String> miscFeatures) {
		this.miscFeatures = miscFeatures;
	}


	@Override
	public String toString() {
		String string = mainColor+" , "+size;
		string = string + " , [ " + (decoration==null?"null":decoration.toString())+ " ] ";
		string = string + " , [ " + (miscFeatures==null?"null":miscFeatures.toString())+ " ] ";
		return string;
	}
}
