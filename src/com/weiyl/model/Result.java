package com.weiyl.model;

public class Result {
	private Station begin;  //��ʼվ
    private Station end;   //�յ�վ
    private int step;  //����վ��
    private Station stationbefore;  //��վ����һվ
    private String linenumber;   //��վ��·
    private int changeline;  //��һվ����վ�Ƿ��л��ˣ�0Ϊ�޻��ˣ�1Ϊ�軻��
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
