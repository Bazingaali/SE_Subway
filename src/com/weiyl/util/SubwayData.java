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
	public static LinkedHashSet<List<Station>> lineset = new LinkedHashSet<List<Station>>(); //�洢������·
	
	public SubwayData(String filename) throws IOException {
		File file = new File(filename);
		InputStreamReader reader = new InputStreamReader( new FileInputStream(file),"UTF-8"); //�����ַ���ʽΪUTF-8,��ֹ����
        BufferedReader br = new BufferedReader(reader);
        
        String readtxt = "";
        readtxt = br.readLine();//���ĵ���һ�б�ʾ���м���������·����ȡ������·������Ϊ�����ѭ����׼��
        int linenumber = Integer.parseInt(readtxt);//linenumber�д�ŵ�����·��
        
        for(int i = 0 ; i < linenumber ; i++) {  //��lineSet��������Ӹ���������·��Ϣ
        	List<Station> line = new ArrayList<Station>();//�洢������������Ϣ
        	readtxt = br.readLine();
        	String[] lineinformation = readtxt.split(" "); 
        	String name = lineinformation[0];
        	for(int j = 1 ; j < lineinformation.length ; j++) {  //��line����Ӹ���վ����Ϣ
        		int flag = 0;//��ʶ����flag=1ʱ�������������ѭ��
        		for(List<Station> l:lineset) {  //������վ��Ϣ
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
        		if(j==lineinformation.length-1&&lineinformation[j].equals(lineinformation[1])) {  //��������Ϣ
        			line.get(0).getConnect().add(line.get(line.size()-1));//���ߵĵ�1վ�����һվ��ͬ������β��������
        			line.get(line.size()-1).getConnect().add(line.get(0));
        			flag=1;
        		}
        		if(flag==0)
        			line.add(new Station(lineinformation[j],name));
        	}
        	for(int j = 0;j < line.size() ;j++) {  //����ÿһ����վ�����ڳ�վ
        		ArrayList<Station> connectStations=line.get(j).getConnect();
        		if(j==0) {//������վ�����ڳ�վ
        			connectStations.add(line.get(j+1));
        			line.get(j).setConnect(connectStations);
        		}
        		else if(j==line.size()-1) {//�����յ�վ�����ڳ�վ
        			connectStations.add(line.get(j-1));
        			line.get(j).setConnect(connectStations);
        		}
        		else {//����������վ�����ڳ�վ
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
