package com.weiyl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.weiyl.model.Result;
import com.weiyl.model.Station;

public class Util {
	private static ArrayList<Station> analysised = new ArrayList<>();  //�Ѿ���������վ��
	private static HashMap<Station, Result> resultmap = new HashMap<>();  //�����
	
	private static List<String> getsameline(List<String> list1,List<String> list2) {//�õ�list1��list2����ͬ����·
		List<String> sameline=new ArrayList<String>();
		for(String l1:list1) {
			for(String l2:list2) {
				if(l1.equals(l2))
					sameline.add(l1);
			}
		}
		return sameline;
	}
	
	private static Station getnextstation() {//�õ���һ������վ��
        int min=66666;//�洢�Ա�վ���������ڵ㵽����վ����С���롣���Ƚ�min����Ϊһ���ϴ�ֵ�������Ƚ�����С
        Station nextstation = null;//��һ���ڵ����ÿսڵ�
        Set<Station> stations = resultmap.keySet();
        for (Station station : stations) {
            if (analysised.contains(station)) {//�����жϵ�ǰ�ڵ��Ƿ��Ѿ���������������������������
                continue;
            }
            Result result1 = resultmap.get(station);//�洢���
            if (result1.getStep() < min) {
                min = result1.getStep();
                nextstation = result1.getEnd();
            }
        }
        return nextstation;
    }
	
	public static Result dijkstrashortest(Station begin, Station end) {  //dijkstra�㷨����������վ�����·��
		for(List<Station> list:SubwayData.lineset) {  
			for(int k = 0 ; k < list.size() ; k++) {
                Result result = new Result();//��ʼ�������
                result.setBegin(begin);
                result.setEnd(list.get(k));
                result.setStep(666666);
                result.setChangeline(0);
                resultmap.put(list.get(k), result);
			}
		}
		
		for(Station s:begin.getConnect()) {  //�Խ���������ֵ
			resultmap.get(s).setStep(1);
			resultmap.get(s).setStationbefore(begin);
			List<String> samelines = getsameline(begin.getSubway(),s.getSubway());
			resultmap.get(s).setLinenumber(samelines.get(0));
		}
		
		resultmap.get(begin).setStep(0);
		analysised.add(begin);
        Station nextstation = getnextstation(); //����һ��վ����з���
        while(nextstation!=null) { //�������·��
        	for(Station s:nextstation.getConnect()) {
        		if(resultmap.get(nextstation).getStep()+1<resultmap.get(s).getStep()) {  //�������·��
        			resultmap.get(s).setStep(resultmap.get(nextstation).getStep()+1);
        			resultmap.get(s).setStationbefore(nextstation);
        			List<String> samelines = getsameline(nextstation.getSubway(),s.getSubway());
        			if(!samelines.contains(resultmap.get(nextstation).getLinenumber())) {  //�ж��Ƿ񻻳�
        				resultmap.get(s).setLinenumber(samelines.get(0));
        				resultmap.get(s).setChangeline(1);
        			}
        			else {
        				resultmap.get(s).setLinenumber(resultmap.get(nextstation).getLinenumber());
        			}
        		}
        	}
        	analysised.add(nextstation); //���ѷ����ڵ������м��ϴ�վ
        	nextstation = getnextstation();//�õ���һվ������ѭ������
        }
        return resultmap.get(end);
    }
	
	public static List<Station> getLineStation(String linename){  //��ȡ������·������վ��
		int flag = 0;//��ʶ����flag=1ʱ�������������ѭ��
		for (List<Station> list:SubwayData.lineset) {
			flag = 0;
			for(Station station : list) {
				if(!station.getSubway().contains(linename))
					flag = 1;
			}
			if(flag == 0)
				return list;
		}
		return null;
	}
	
	public static List<String> getPath(Result r){//���ɳ˳�·��
		List<String> path=new ArrayList<String>();//�洢�˳�·��
		Stack<Station> stationtemp=new Stack<Station>();//����ջ�洢��վ��Ϣ
		Station s=r.getStationbefore();
		while(!s.equals(r.getBegin())) {
			stationtemp.push(s);
			s=resultmap.get(s).getStationbefore();
		}
		path.add("����������" + r.getBegin().getName() + "����" + resultmap.get(stationtemp.peek()).getLinenumber());
		path.add(r.getBegin().getName());
		while(!stationtemp.empty()) {
			if(resultmap.get(stationtemp.peek()).getChangeline()==1) {
				path.add("�����ڱ�վ����"+resultmap.get(stationtemp.peek()).getLinenumber());
				path.add(stationtemp.pop().getName());
			}
			else
				path.add(stationtemp.pop().getName());
		}
		if(r.getChangeline()==1) {
			path.add("�����ڱ�վ����"+r.getLinenumber());
			path.add(r.getEnd().getName());
		}
		else
		    path.add(r.getEnd().getName());
		return path;
	}
}
