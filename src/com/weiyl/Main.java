package com.weiyl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.weiyl.model.Result;
import com.weiyl.model.Station;
import com.weiyl.util.SubwayData;
import com.weiyl.util.Util;

import javafx.scene.chart.PieChart.Data;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		new SubwayData("subwaydata.txt");
		
		System.out.print("��ѡ���ѯ���ݣ�1����ѯָ��������·��2����ѯ��վ�����·��  ");
		int nn = Integer.parseInt(scanner.next());
		if(nn==1) {
			System.out.print("��������Ҫ��ѯ�ĵ�����·  ");
			String searchline = scanner.next();
			List<Station> stations = Util.getLineStation(searchline);
			String put = "";
			if(stations==null)
				put = "�õ�����·������";
			else {
				for(int i=0;i<stations.size();i++) {
					if(i==stations.size()-1&&stations.get(i).getConnect().contains(stations.get(0)))
						put = put + stations.get(i).getName() + "  ����" + searchline + "Ϊ����" + "����";
				    else
				    	put = put + stations.get(i).getName()+" ";
			    }
			}
			printout(put , "result.txt");
			System.out.print(put);
		}	
		else if(nn==2) {
			System.out.print("��������ʼվ�뵽��վ����վ���Կո�ָ�  ");
			String station1 = scanner.next();
			String station2 = scanner.next();
			
		   	Station begin = null;
		   	Station end = null;
		   	for(List<Station> list : SubwayData.lineset){
				for(int k=0 ;k<list.size() ;k++) {
	                if(list.get(k).getName().equals(station1)) {
	                	begin = list.get(k);
	                }
	                if(list.get(k).getName().equals(station2)) {
	                	end = list.get(k);
	                }
				}
			}
		   	String put = "";
		   	if(begin==null)
		   		put = "����վ������";
		   	else if (end==null)
		   		put = "����վ������";
		   	else {
		   		Result result = Util.dijkstrashortest(begin , end);
		   		List<String> path = Util.getPath(result);
		   		put = "��" + station1 + "��" + station2 + "���پ���"+ (result.getStep() + 1) + "վ,����Ϊ��ϸ�˳�����\n";
		   		for(String s:path)
		   			put = put + s + "\n";
		   	}
		   	printout(put,"result.txt");
			System.out.print(put);
		}
		return;
	}
	
	 public static void printout(String content,String path) throws IOException {
		 File file = new File(path);
	     if(!file.exists()){
	 	    file.createNewFile();
	     }
	     FileOutputStream outputStream = new FileOutputStream(file);
		    byte[]  bytes = content.getBytes("UTF-8");
		    outputStream.write(bytes);
		    outputStream.close();
	 }
}
