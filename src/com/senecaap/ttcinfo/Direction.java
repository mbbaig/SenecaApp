package com.senecaap.ttcinfo;

import java.io.Serializable;
import java.util.List;

public class Direction implements Serializable{

	private String title;
	transient private List<Prediction> predictions;
	private String branch;
	
	public Direction() {}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Prediction> getPredictions() {
		return predictions;
	}
	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	
	
	
}
