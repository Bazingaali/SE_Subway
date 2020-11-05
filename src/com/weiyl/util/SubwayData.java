package com.weiyl.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import com.weiyl.model.Station;

public class SubwayData {
	public static LinkedHashSet<List<Station>> lineset = new LinkedHashSet<List<Station>>(); //存储所有线路
	
	public SubwayData(String filename) throws IOException {
		File file = new File(filename);
		InputStreamReader reader = new InputStreamReader( new FileInputStream(file),"UTF-8"); //输入字符格式为UTF-8,防止乱码
        BufferedReader br = new BufferedReader(reader);
        
        String readtxt = "";
        readtxt = br.readLine();//在文档第一行表示共有几条地铁线路，获取地铁线路数量，为后面的循环做准备
        int linenumber = Integer.parseInt(readtxt);//linenumber中存放地铁线路数
        
        for(int i = 0 ; i < linenumber ; i++) {  //往lineSet集合中添加各条地铁线路信息
        	List<Station> line = new ArrayList<Station>();//存储各条地铁线信息
        	readtxt = br.readLine();
        	String[] lineinformation = readtxt.split(" "); 
        	String name = lineinformation[0];
        	for(int j = 1 ; j < lineinformation.length ; j++) {  //往line中添加各个站的信息
        		int flag = 0;//标识符，flag=1时处理结束，跳出循环
        		for(List<Station> l:lineset) {  //处理换乘站信息
        			for(int k = 0  ; k < l.size() ; k++) {
        				if(l.get(k).getName().equals(lineinformation[j])) {  
        					ArrayList<String> line1 = l.get(k).getSubway();
        					line1.add(name);
        					l.get(k).setSubway(line1);
        					line.add(l.get(k));
        					flag=1;
        					break;
        				}
        			}
        			if(flag==1)
        				break;
        		}
        		if(j==lineinformation.length-1&&lineinformation[j].equals(lineinformation[1])) {  //处理环线信息
        			line.get(0).getConnect().add(line.get(line.size()-1));//环线的第1站与最后一站相同，将首尾连接起来
        			line.get(line.size()-1).getConnect().add(line.get(0));
        			flag=1;
        		}
        		if(flag==0)
        			line.add(new Station(lineinformation[j],name));
        	}
        	for(int j = 0;j < line.size() ;j++) {  //处理每一个车站的相邻车站
        		ArrayList<Station> connectStations=line.get(j).getConnect();
        		if(j==0) {//处理首站的相邻车站
        			connectStations.add(line.get(j+1));
        			line.get(j).setConnect(connectStations);
        		}
        		else if(j==line.size()-1) {//处理终点站的相邻车站
        			connectStations.add(line.get(j-1));
        			line.get(j).setConnect(connectStations);
        		}
        		else {//处理其他车站的相邻车站
        			connectStations.add(line.get(j+1));
        			connectStations.add(line.get(j-1));
        			line.get(j).setConnect(connectStations);
        		}
        	}
        	lineset.add(line); 
        }
        br.close();
	}
}
