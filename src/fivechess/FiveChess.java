package fivechess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;

public class FiveChess {

	/*
	 * @作者: Rear82
	 */
	static int[][] chessBoard = new int[10][10];
	static int[] last = new int[2];
	static int[][] lastback = new int[1000][2];
	static int[][][] tuback = new int[1000][10][10];
	static int who, result, bushu;
	static int[] whoback = new int[1000];

	public static void saveOnFile() throws Exception {
		File file = new File("存档.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter br = new BufferedWriter(new FileWriter(file));

		br.write(Integer.toString(who));
		br.newLine();
		br.write(Integer.toString(last[0]));
		br.newLine();
		br.write(Integer.toString(last[1]));

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				br.newLine();
				br.write(Integer.toString(chessBoard[i][j]));
			}
		}
		br.close();
	}

	public static void readFromFile() throws Exception {
		File file = new File("存档.txt");
		if (!file.exists()) {
			System.out.println("没有发现存档文件！");
			result = 0;
			clear();
		} else {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String s = null;
			int[] shuju = new int[103];
			int i = 0;
			while ((s = br.readLine()) != null) {
				shuju[i] = Integer.parseInt(s);
				i++;
			}
			who = shuju[0];
			last[0] = shuju[1];
			last[1] = shuju[2];
			for (i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					chessBoard[i][j] = shuju[i * 10 + j + 3];
				}
			}
		}
	}

	public static boolean right_Move(String s) {
		String a, b = null;
		int i;
		if("b".equals(s))
			return true;
		for (i = 0; i <= 99; i++) {
			if (i <= 9) {
				a = "0" + Integer.toString(i);
				b = Integer.toString(i);
			} else {
				a = Integer.toString(i);
				b = Integer.toString(i);
			}
			if (a.equals(s)||b.equals(s)) {
				return true;
			}
		}
		return false;
	}

	public static String zhuanhuan(int i, int j) {
		if (i == last[0] && j == last[1]) {
			switch (chessBoard[i][j]) {
				case 0:
					return " " + Integer.toString(i) + Integer.toString(j) + " ";
				case 1:
					return "*● ";
				case 2:
					return "*○ ";
			}

		} else {
			switch (chessBoard[i][j]) {
				case 0:
					return " " + Integer.toString(i) + Integer.toString(j) + " ";
				case 1:
					return " ● ";
				case 2:
					return " ○ ";
			}
		}
		return "    ";
	}

	public static void clear() {
		who = 1;
		result = 0;
		bushu = 1;
		last[0] = 1;
		last[1] = -1;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				chessBoard[i][j] = 0;
			}
		}
	}

	public static void chessSave() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				tuback[bushu][i][j] = chessBoard[i][j];
			}
		}
		whoback[bushu] = who;
		lastback[bushu][0] = last[0];
		lastback[bushu][1] = last[1];
	}

	public static void chessBack() {
		bushu--;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				chessBoard[i][j] = tuback[bushu][i][j];
			}
		}
		who = whoback[bushu];
		last[0] = lastback[bushu][0];
		last[1] = lastback[bushu][1];
	}

	public static void xmlprint(int arr[][]) {
		System.out.println("     0     1     2     3     4     5     6     7     8     9  ");
		System.out.println(" ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐");
		String[][] b = new String[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				b[i][j] = zhuanhuan(i, j);
				b[i][j] = b[i][j] + "│";
				if (j == 0) {

					b[i][j] = Integer.toString(i) + "│" + b[i][j];

				}
			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				System.out.print(b[i][j]);
				if (j == 9 & i != 9) {
					System.out.println("\n ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤");
				}
			}
		}
		System.out.println("\n └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘\n");

	}

	public static int operateChess(int a, int b) {
		if (chessBoard[a][b] == 0) {
			chessBoard[a][b] = who;
			last[0] = a;
			last[1] = b;
			return 1;
		}
		System.out.println("这里不可以落子！\n\n");
		return 0;
	}

	private static int lookDownBound(int a) {
		if (a - 4 >= 0) {
			return a - 4;
		} else {
			return 0;
		}
	}

	private static int lookUpBound(int a) {
		if (a + 4 <= 9) {
			return a + 4;
		} else {
			return 9;
		}
	}

	private static int min(int a, int b) {
		if (a > b) {
			return b;
		} else {
			return a;
		}
	}

	public static int panduan(int a, int b) {
		int Bound_up, Bound_down, Bound_left, Bound_right;
		Bound_down = lookDownBound(a);
		Bound_left = lookDownBound(b);
		Bound_up = lookUpBound(a);
		Bound_right = lookUpBound(b);
		//System.out.println(a+" "+b+" "+Bound_down+" "+Bound_left+" "+Bound_up+" "+Bound_right);
		int result = 1;
		for (int i = Bound_down; i <= Bound_up - 4; i++) {
			result = 1;
			for (int j = i; j <= i + 4; j++) {
				result = result * chessBoard[j][b];
			}
			if (result == 1) {
				return 1;
			}
			if (result == 32) {
				return 2;
			}
		}
		for (int i = Bound_left; i <= Bound_right - 4; i++) {
			result = 1;
			for (int j = i; j <= i + 4; j++) {
				result = result * chessBoard[a][j];
			}
			if (result == 1) {
				return 1;
			}
			if (result == 32) {
				return 2;
			}
		}
		int juli1, juli2, juli3, juli4;
		juli1 = min(a - Bound_down, b - Bound_left);
		juli2 = min(Bound_up - a, Bound_right - b);
		juli3 = min(a - Bound_down, Bound_right - b);
		juli4 = min(Bound_up - a, b - Bound_left);
		for (int i = a - juli1; i <= a + juli2 - 4; i++) {
			result = 1;
			for (int j = i - a; j <= i - a + 4; j++) {
				result = result * chessBoard[a + j][b + j];
			}
			if (result == 1) {
				return 1;
			}
			if (result == 32) {
				return 2;
			}
		}

		for (int i = a - juli3; i <= a + juli4 - 4; i++) {
			result = 1;
			for (int j = i - a; j <= i - a + 4; j++) {
				result = result * chessBoard[a + j][b - j];
			}
			if (result == 1) {
				return 1;
			}
			if (result == 32) {
				return 2;
			}
		}
		return 0;
	}

	public static void game() throws Exception {
		String s = null;
		int m = 0, n = 0;
		int t;
		t = 0;
		do {

			if (who == 1) {
				System.out.println("现在轮到黑棋落子:");
			}
			if (who == 2) {
				System.out.println("现在轮到白棋落子:");
			}
			Scanner input = new Scanner(System.in);
			do {
				System.out.println("请输入要落子的位置:");
				s = input.next();
			} while (right_Move(s) == false);
			if ("b".equals(s) || "B".equals(s)) {
				t = 2;
				chessBack();
			}

			if (t == 0) {
				if (s == "00") {
					m = 0;
					n = 0;
				} else {
					m = (int) Integer.parseInt(s) / 10;
					n = (int) Integer.parseInt(s) % 10;
				}
			}

			if (t == 0) {
				if (operateChess(m, n) == 1) {
					t = 1;
				}
			}
		} while (t == 0);

		if (t == 1) {
			result = panduan(m, n);
			if (result == 0) {
				who = 2 / who;
				bushu++;
				chessSave();
				saveOnFile();
			}
		}
		xmlprint(chessBoard);

	}

	public static void main(String[] args) throws Exception {
		// TODO code application logic here
		System.out.println("欢迎来到五子棋小游戏！\n开发者:Rear82\n操作说明:输入棋盘上对应的数字来落子。\n您也可以输入b键来撤销上一步操作。");
		Scanner input = new Scanner(System.in);
		String m;
		do {
			System.out.println("\n输入1来载入上一次游戏，输入2来开始新游戏！");
			m = input.next();
		} while (!"1".equals(m) && !"2".equals(m));
		if (Integer.parseInt(m) == 1) {
			result = 0;
			readFromFile();

		} else {
			clear();
		}
		chessSave();
		//chessBoard[1][1]=1;
		//chessBoard[2][1]=1;
		//chessBoard[3][1]=1;
		//chessBoard[4][1]=1;
		//chessBoard[5][1]=1;
		xmlprint(chessBoard);
		while (result == 0) {
			game();
		}
		System.out.println("*********************************");
		System.out.println("\n");
		if (result == 1) {

			System.out.println("        游戏结束！  黑棋胜!");

		} else {
			System.out.println("        游戏结束！  白棋胜!");
		}
		System.out.println("\n");
		System.out.println("*********************************");
		//System.out.println(panduan(1,1));
	}

}
