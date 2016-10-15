/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fivechess;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;
/**
 *
 * @author Rear82
 */
public class FiveChess {

	/*
	 * To change this license header, choose License Headers in Project Properties.
	 * To change this template file, choose Tools | Templates
	 * and open the template in the editor.
	 */


	/**
	 *
	 * @author Rear82
	 */


	static int[][] tu = new int[10][10];
	static int[] last = new int[2];
	static int[][] lastback = new int[1000][2];
	static int[][][] tuback = new int[1000][10][10];
	static int who,result,bushu;
	static int[] whoback=new int[1000];
	/**
	 * @param args the command line arguments
	 */
	
	public static void cundang()throws Exception{
		File file = new File("存档.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		BufferedWriter br=new BufferedWriter(new FileWriter(file));
		
		br.write(Integer.toString(who));
		br.newLine();
		br.write(Integer.toString(last[0]));
		br.newLine();
		br.write(Integer.toString(last[1]));
		
		
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				br.newLine();
				br.write(Integer.toString(tu[i][j]));
			}
		}
		br.close();
	}
	
	
	
	public static void duqu()throws Exception
	{
		File file =new File("存档.txt");
		if(!file.exists())
		{
			System.out.println("没有发现存档文件！");
			result=0;
			qingkong();
		}
		else{
			InputStreamReader isr=new InputStreamReader(new FileInputStream(file),"utf-8");
			BufferedReader br=new BufferedReader(isr);
			String s = null;
			int[] shuju=new int[103];
			int i=0;
			while((s=br.readLine())!=null){
			shuju[i]=Integer.parseInt(s);
			i++;
			}
			who=shuju[0];
			last[0]=shuju[1];
			last[1]=shuju[2];
			for(i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				tu[i][j]=shuju[i*10+j+3];
			}
		}
		}
	}	
		
		
	public static  String zhuanhuan(int i,int j)
	{
		if(i==last[0]&&j==last[1]){
			switch(tu[i][j])
			{
			case 0:
				return " "+Integer.toString(i)+Integer.toString(j)+" ";
			case 1:
				return "*● ";
			case 2:
				return "*○ ";
			}
			
		}
		else{
		switch(tu[i][j])
		{
			case 0:
				return " "+Integer.toString(i)+Integer.toString(j)+" ";
			case 1:
				return " ● ";
			case 2:
				return " ○ ";
		}
		}
		return "    ";
	}

	public static void qingkong(){
		who=1;
		result=0;
		bushu=1;
		last[0]=1;
		last[1]=-1;
	for (int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				tu[i][j]=0;
			}
		}
	 }
	
	public static void jilu(){
		for (int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				tuback[bushu][i][j]=tu[i][j];
			}
		}	
		whoback[bushu]=who;
		lastback[bushu][0]=last[0];
		lastback[bushu][1]=last[1];
	}

	
	public static void huifu(){
		bushu--;
		for (int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				tu[i][j]=tuback[bushu][i][j];
			}
		}	
		who=whoback[bushu];
		last[0]=lastback[bushu][0];
		last[1]=lastback[bushu][1];
	}
	
	
	public static void xmlprint(int arr[][])
	{
		System.out.println("     0     1     2     3     4     5     6     7     8     9  ");
		System.out.println(" ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐");
		String[][] b= new String[10][10];
		for (int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				b[i][j]=zhuanhuan(i,j);
				b[i][j] = b[i][j]+"│";
				if(j==0)
				{
					
						b[i][j] =Integer.toString(i)+"│"+b[i][j];
					
					
				}
			}
		}

		for (int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
		    {

				System.out.print(b[i][j]);
				if(j==9&i!=9){
					System.out.println("\n ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤");
				}
			}
		}
		System.out.println("\n └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘\n");
		

	}

	
	
	public static int luozi(int a,int b)
	{
		if(tu[a][b]==0){
		tu[a][b]=who;
		last[0]=a;
		last[1]=b;
		return 1;
		}
		System.out.println("这里不可以落子！\n\n");
		return 0; 	
	}
	
	
	private static int qiujie1(int a) {
		if(a-4>=0){
			return a-4;
		}
		else{
			return 0;
		}
	}

	
	
	private static int qiujie2(int a) {
		if(a+4<=9){
			return a+4;
		}
		else{
			return 9;
		}
	}

	
	
	private static int min(int a,int b) {
		if(a>b){
			return b;
		}
		else{
			return a;
		}
	}

	
	
	public static int panduan(int a,int b)
	{
		int sj,xj,zj,yj;
		xj=qiujie1(a);
		zj=qiujie1(b);
		sj=qiujie2(a);
		yj=qiujie2(b);
		//System.out.println(a+" "+b+" "+xj+" "+zj+" "+sj+" "+yj);
		int jieguo=1;
		for(int i=xj;i<=sj-4;i++){
			jieguo=1;
			for(int j=i;j<=i+4;j++){
				jieguo=jieguo*tu[j][b];
			}
			if(jieguo==1){
				return 1;
			}
			if(jieguo==32){
				return 2;
			}
		}
		for(int i=zj;i<=yj-4;i++){
			jieguo=1;
			for(int j=i;j<=i+4;j++){
				jieguo=jieguo*tu[a][j];
			}
			if(jieguo==1){
				return 1;
			}
			if(jieguo==32){
				return 2;
			}
		}
		int juli1,juli2,juli3,juli4;
		juli1 =min(a-xj,b-zj);
		juli2 =min(sj-a,yj-b);
		juli3 =min(a-xj,yj-b);
		juli4=min(sj-a,b-zj);
		for(int i=a-juli1;i<=a+juli2-4;i++){
			jieguo=1;
			for(int j=i-a;j<=i-a+4;j++)
			{
				jieguo=jieguo*tu[a+j][b+j];
			}
			if(jieguo==1){
				return 1;
			}
			if(jieguo==32){
				return 2;
			}
		}

		for(int i=a-juli3;i<=a+juli4-4;i++){
			jieguo=1;
			for(int j=i-a;j<=i-a+4;j++)
			{
				jieguo=jieguo*tu[a+j][b-j];
			}
			if(jieguo==1){
				return 1;
			}
			if(jieguo==32){
				return 2;
			}
		}
		return 0;
	}

	public static void game() throws Exception{
		String s=null;
		int m=0,n=0;
		int t;
		t=0;
		do{
		
		if(who==1){
			System.out.println("现在轮到黑棋落子:");
		}
		if(who==2){
			System.out.println("现在轮到白棋落子:");
		}
		Scanner input = new Scanner(System.in);
		System.out.println("请输入要落子的位置:");
		s =  input.next();
		if("b".equals(s)||"B".equals(s)){
			t=2;
			huifu();
		}
		
		if(t==0){
			if (s=="00"){
				m=0;
				n=0;
			}
			else{
				m=(int)Integer.parseInt(s)/10;
				n=(int)Integer.parseInt(s)%10;
			}
		}
		
		if(t==0){
			if(luozi(m,n)==1){
				t=1;
			}
		}
		}while(t==0);
		
		
		
		
		if(t==1){
		result=panduan(m,n);
		if(result == 0){
		who=2/who;
		bushu++;
		jilu();
		cundang();
		}
		}
		xmlprint(tu);
		
	}
	
	public static void main(String[] args) throws Exception {
		// TODO code application logic here
		System.out.println("欢迎来到2048小游戏！\n开发者:Rear82\n操作说明:使用w键向上，s键向下，d键向右，a键向左。\n您也可以输入b键来撤销上一步操作。\n输入1来载入上一次游戏，输入2来开始新游戏！");
		Scanner input = new Scanner(System.in);
		String m = input.next();
		if(Integer.parseInt(m)==1){
			result=0;
			duqu();
			
		}
		else{
		qingkong();
		}
		jilu();
		//tu[1][1]=1;
		//tu[2][1]=1;
		//tu[3][1]=1;
		//tu[4][1]=1;
		//tu[5][1]=1;
		xmlprint(tu);
		while(result==0){
			game();
		}
		System.out.println("*********************************");
		System.out.println("\n");
		if(result==1){
			
			System.out.println("        游戏结束！  黑旗胜!");
			
		}
		else{
			System.out.println("        游戏结束！  白旗胜!");
		}
		System.out.println("\n");
		System.out.println("*********************************");
		//System.out.println(panduan(1,1));
	}



}
