package com.weiyl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.weiyl.model.Result;
import com.weiyl.model.Station;

public class Util {
	private static ArrayList<Station> analysised = new ArrayList<>();  //已经分析过的站点
	private static HashMap<Station, Result> resultmap = new HashMap<>();  //结果集
	
	private static List<String> getsameline(List<String> list1,List<String> list2) {//得到list1和list2中相同的线路
		List<String> sameline=new ArrayList<String>();
		for(String l1:list1) {
			for(String l2:list2) {
				if(l1.equals(l2))
					sameline.add(l1);
			}
		}
		return sameline;
	}
	
	private static Station getnextstation() {//得到下一个分析站点
        int min=66666;//存储以本站出发各个节点到到达站的最小距离。首先将min设置为一个较大值，后续比较逐渐缩小
        Station nextstation = null;//下一个节点先置空节点
        Set<Station> stations = resultmap.keySet();
        for (Station station : stations) {
            if (analysised.contains(station)) {//首先判断当前节点是否已经被分析过，若分析过，则跳过
                continue;
            }
            Result result1 = resultmap.get(station);//存储结果
            if (result1.getStep() < min) {
                min = result1.getStep();
                nextstation = result1.getEnd();
            }
        }
        return nextstation;
    }
	
	public static Result dijkstrashortest(Station begin, Station end) {  //dijkstra算法计算两地铁站中最短路径
		for(List<Station> list:SubwayData.lineset) {  
			for(int k = 0 ; k < list.size() ; k++) {
                Result result = new Result();//初始化结果集
                result.setBegin(begin);
                result.setEnd(list.get(k));
                result.setStep(666666);
                result.setChangeline(0);
                resultmap.put(list.get(k), result);
			}
		}
		
		for(Station s:begin.getConnect()) {  //对结果集逐个赋值
			resultmap.get(s).setStep(1);
			resultmap.get(s).setStationbefore(begin);
			List<String> samelines = getsameline(begin.getSubway(),s.getSubway());
			resultmap.get(s).setLinenumber(samelines.get(0));
		}
		
		resultmap.get(begin).setStep(0);
		analysised.add(begin);
        Station nextstation = getnextstation(); //对下一个站点进行分析
        while(nextstation!=null) { //计算最短路径
        	for(Station s:nextstation.getConnect()) {
        		if(resultmap.get(nextstation).getStep()+1<resultmap.get(s).getStep()) {  //更新最短路径
        			resultmap.get(s).setStep(resultmap.get(nextstation).getStep()+1);
        			resultmap.get(s).setStationbefore(nextstation);
        			List<String> samelines = getsameline(nextstation.getSubway(),s.getSubway());
        			if(!samelines.contains(resultmap.get(nextstation).getLinenumber())) {  //判断是否换乘
        				resultmap.get(s).setLinenumber(samelines.get(0));
        				resultmap.get(s).setChangeline(1);
        			}
        			else {
        				resultmap.get(s).setLinenumber(resultmap.get(nextstation).getLinenumber());
        			}
        		}
        	}
        	analysised.add(nextstation); //在已分析节点数组中加上此站
        	nextstation = getnextstation();//得到下一站，继续循环分析
        }
        return resultmap.get(end);
    }
	
	public static List<Station> getLineStation(String linename){  //获取地铁线路的所有站点
		int flag = 0;//标识符，flag=1时处理结束，跳出循环
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
	
	public static List<String> getPath(Result r){//生成乘车路线
		List<String> path=new ArrayList<String>();//存储乘车路线
		Stack<Station> stationtemp=new Stack<Station>();//利用栈存储各站信息
		Station s=r.getStationbefore();
		while(!s.equals(r.getBegin())) {
			stationtemp.push(s);
			s=resultmap.get(s).getStationbefore();
		}
		path.add("！！首先在" + r.getBegin().getName() + "乘坐" + resultmap.get(stationtemp.peek()).getLinenumber());
		path.add(r.getBegin().getName());
		while(!stationtemp.empty()) {
			if(resultmap.get(stationtemp.peek()).getChangeline()==1) {
				path.add("！！在本站换乘"+resultmap.get(stationtemp.peek()).getLinenumber());
				path.add(stationtemp.pop().getName());
			}
			else
				path.add(stationtemp.pop().getName());
		}
		if(r.getChangeline()==1) {
			path.add("！！在本站换乘"+r.getLinenumber());
			path.add(r.getEnd().getName());
		}
		else
		    path.add(r.getEnd().getName());
		return path;
	}
}
