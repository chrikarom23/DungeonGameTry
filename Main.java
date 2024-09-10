package dungeon;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Dimensions of the dungeon: ");
		int dimenX = sc.nextInt();
		int dimenY = sc.nextInt();
//		int dungeon[][] = new int[dimenX][dimenY];
		System.out.println("Enter the position of the adventurer: ");
		int advenX = sc.nextInt();
		int advenY = sc.nextInt();
//		dungeon[advenX][advenY] = 2;
		System.out.println("Enter the position of the Monster: ");
		int monsterX = sc.nextInt();
		int monsterY = sc.nextInt();
		System.out.println("Enter the position of the Gold: ");
		int goldX = sc.nextInt();
		int goldY = sc.nextInt();
//		dungeon[goldX][goldY] = 9999;
		System.out.println("Enter the position trigger: ");
		int triggerX = sc.nextInt();
		int triggerY = sc.nextInt();
//		dungeon[monsterX][monsterY] = 6;
		solution s = new solution(dimenX, dimenY,advenX,advenY,monsterX,monsterY,goldX,goldY, triggerX, triggerY);
		s.game();
	}
}

class solution {
	int dimenX;
	int dimenY;
	int advenX;
	int advenY;
	int monsterX;
	int monsterY;
	int goldX;
	int goldY;
	int advenMoveCount;
	int monsterMoveCount;
	int triggerX;
	int triggerY;
	int startAdvenX;
	int startAdvenY;
	int startMonsterX;
	int startMonsterY;
	Boolean Trigger;
	String path = "";
	solution(int dimenX, int dimenY, int advenX, int advenY,int monsterX,int monsterY,int goldX,int goldY, int triggerX, int triggerY){
		this.dimenX = dimenX;
		this.dimenY = dimenY;
		this.advenX = advenX;
		this.advenY = advenY;
		this.startAdvenX = advenX;
		this.startAdvenY = advenY;
		this.startMonsterX = monsterX;
		this.startMonsterY = monsterY;
		this.monsterX = monsterX;
		this.monsterY = monsterY;
		this.goldX = goldX;
		this.goldY = goldY;
		this.triggerX = triggerX;
		this.triggerY = triggerY;
	}
	
	public void game() {
		this.advenMoveCount = 0;
		this.monsterMoveCount = 0;
		boolean flag = false;
		while((Math.abs(advenX-goldX)+Math.abs(advenY-goldY))>0) {
			addPath();
			int nextSideAdven = nextMove(advenX, advenY, goldX, goldY);
			int res1 = moveAdven(nextSideAdven);
			if(res1<0) {
				flag = true;
				 break;
			}
			advenMoveCount++;
			int nextSideMonster = nextMove(monsterX, monsterY, advenX, advenY);
			int res2 = moveMonster(nextSideMonster);
			if(res2<0) {
				flag = true;
				 break;
			}
			monsterMoveCount++;
//			if(monsterMoveCount>10) return ;
		}
		if(!flag) {
			output(true);
			return ;
		}
		advenX = startAdvenX;
		advenY = startAdvenY;
		monsterX = startMonsterX;
		monsterY = startMonsterY;
		System.out.println(advenX+" "+advenY);
		System.out.println(monsterX+" "+monsterY);
		advenMoveCount = 0;
		monsterMoveCount = 0;
		path = "";
		while((Math.abs(advenX-triggerX)+Math.abs(advenY-triggerY))>0) {
			addPath();
			int nextSideAdven = nextMove(advenX, advenY, triggerX, triggerY);
			int res1 = moveAdven(nextSideAdven);
			if(res1<0) {
				break ;
			}
			advenMoveCount++;
			int nextSideMonster = nextMove(monsterX, monsterY, advenX, advenY);
			int res2 = moveMonster(nextSideMonster);
			if(res2<0) {
				break ;
			}
			monsterMoveCount++;
//			if(monsterMoveCount>10) return ;
		}
		while((Math.abs(advenX-goldX)+Math.abs(advenY-goldY))>0) {
			addPath();
			int nextSideAdven = nextMove(advenX, advenY, goldX, goldY);
			int res1 = moveAdven(nextSideAdven);
			advenMoveCount++;
		}
		System.out.println("here 5");
		output(true);
	}
	
	public int nextMove(int x, int y, int targetX, int targetY) {
		int up = dimenX*dimenY;
		int down = up;
		int left = up;
		int right = up;
		if((x-1)>0) {
			up = Math.abs(x-1-targetX)+Math.abs(y-targetY);
		}
		if((x+1)<=dimenX) {
			down = Math.abs(x+1-targetX)+Math.abs(y-targetY);
		}
		if((y-1)>0) {
			left = Math.abs(x-targetX)+Math.abs(y-1-targetY);
		}
		if((y+1)<=dimenY) {
			right = Math.abs(x-targetX)+Math.abs(y+1-targetY);
		}
		if(up<=down && up<=left && up<=right) {
			return -1;
		}
		if(down<=up && down<=left && down<=right) {
			return 1;
		}
		if(left<=down && left<=up && left<=right) {
			return -2;
		}
		if(right<=down && right<=left && right<=up) {
			return 2;
		}
		return 0;
	}
	
	public int moveAdven(int nextSide) {
		if(nextSide == 1) {
			advenX++;
		}
		else if(nextSide == -1) {
			advenX--;
		}
		else if(nextSide == 2) {
			advenY++;
		}
		else if(nextSide == -2) {
			advenY--;
		}
		if(Math.abs(advenX-monsterX)+Math.abs(advenY-monsterY)==0) {
			return -1;
		}
		System.out.println("here 1");
		System.out.println("Adventurer at: "+advenX+" "+advenY+" "+nextSide);
		return 1;
	}
	
	public int moveMonster(int nextSide) {
		if(nextSide == 1) {
			monsterX++;
		}
		else if(nextSide == -1) {
			monsterX--;
		}
		else if(nextSide == 2) {
			monsterY++;
		}
		else if(nextSide == -2) {
			monsterY--;
		}
		if(Math.abs(advenX-monsterX)+Math.abs(advenY-monsterY)==0) {
			return -1;
		}
		System.out.println("here 2");
		return 1;
	}
	
	public void addPath() {
		path+="("+advenX+","+advenY+") ->";
	}
	
	public void output(boolean b) {
		if(!b) {
			System.out.println("No possible solution");
		}
		else {
			System.out.println("Minimum distance to gold is: "+advenMoveCount);
			System.out.println(path+" GOLD!");
		}
		return ;
	}
}
