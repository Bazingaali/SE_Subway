package com.weiyl.model;

public class Result {
	private Station begin;  //起始站
    private Station end;   //终点站
    private int step;  //经过站数
    private Station stationbefore;  //本站的上一站
    private String linenumber;   //本站线路
    private int changeline;  //上一站到本站是否有换乘，0为无换乘，1为需换乘
	public Station getBegin() {
		return begin;
	}
	public void setBegin(Station begin) {
		this.begin = begin;
	}
	public Station getEnd() {
		return end;
	}
	public void setEnd(Station end) {
		this.end = end;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public Station getStationbefore() {
		return stationbefore;
	}
	public void setStationbefore(Station stationbefore) {
		this.stationbefore = stationbefore;
	}
	public String getLinenumber() {
		return linenumber;
	}
	public void setLinenumber(String linenumber) {
		this.linenumber = linenumber;
	}
	public int getChangeline() {
		return changeline;
	}
	public void setChangeline(int changeline) {
		this.changeline = changeline;
	} 
}
