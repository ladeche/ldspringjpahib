package ld.maven.ldspringjpahib.model;

import java.io.Serializable;

public class JsonEquipmentDetails implements Serializable {

	private String mainColor;
	private String size;
	private String decoration;

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


	public String getDecoration() {
		return decoration;
	}


	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}


	@Override
	public String toString() {
		return mainColor
				+","+size
				+","+decoration;
	}
}
