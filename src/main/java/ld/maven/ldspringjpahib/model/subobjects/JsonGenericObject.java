package ld.maven.ldspringjpahib.model.subobjects;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JsonGenericObject implements Serializable {

	private Map<String,String> miscFeatures;

	public Map<String, String> getMiscFeatures() {
		return miscFeatures;
	}

	public void setMiscFeatures(Map<String, String> miscFeatures) {
		this.miscFeatures = miscFeatures;
	}

}
