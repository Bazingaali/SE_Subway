package com.weiyl.model;

import java.util.ArrayList;
import java.util.List;

public class Station{
	private String name; //վ��
	private ArrayList<String> subway = new ArrayList<String>();  //վ����·
	private ArrayList<Station> connect = new ArrayList<Station>();  //��վ�������ĵ���վ
	
	public Station() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Station(String name, String subway) {
        this.name = name;
        this.subway.add(subway);
    }

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getSubway() {
		return subway;
	}
	public void setSubway(ArrayList<String> subway) {
		this.subway = subway;
	}
	public ArrayList<Station> getConnect() {
		return connect;
	}
	public void setConnect(ArrayList<Station> connect) {
		this.connect = connect;
	}
}
