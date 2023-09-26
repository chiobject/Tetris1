package Tetris;

import java.awt.Point;


public abstract class Piece {
	final int DOWN = 0; // 블록 이동 방향을 나타내는 상수 (아래)
	final int LEFT = 1; // 블록 이동 방향을 나타내는 상수 (왼쪽)
	final int RIGHT = 2; // 블록 이동 방향을 나타내는 상수 (오른쪽)
	protected int r[]; // 블록의 가로 크기를 저장하는 배열
	protected int c[]; // 블록의 세로 크기를 저장하는 배열
	protected TetrisData data; // 테트리스 판 정보를 담은 객체
	protected Point center; // 블록의 중심 좌표
	
	public Piece(TetrisData data) {
		r = new int[4];
		c = new int[4];
		this.data = data;
		center = new Point(5,0); // 블록의 중심 좌표를 생성하고 초기화합니다. (가로 5, 세로 0)
	}
	
	public abstract int getType();	// 블록 종류 반환
	public abstract int roteType();	// 블록 회전 종류 반환
	
	public int getX() { return center.x; }	// 블록 x 좌표 반환
	public int getY() { return center.y; }	// 블록 y 좌표 반환
	
	public boolean copy() {	//블록을 테트리스 판에 복사하는 메서드
		boolean value = false;
		int x = getX();	
		int y = getY();
		if(getMinY() + y <= 0) {	// 블록의 최소 y 좌표와 현재 y 좌표를 더했을 때 0보다 작으면 블록이 테트리스 판 위에 위치하게 되므로 value를 true로 설정합니다.
			value = true;
		}
		
		for(int i=0; i < 4; i++) {
			data.setAt(y + r[i], x + c[i], getType());	// 테트리스 판의 해당 좌표에 블록의 종류를 설정합니다.
		}
		return value;	// 블록이 테트리스 판 위에 위치하는지 여부를 반환합니다.
	}
	
	public boolean isOverlap(int dir) {	// 블록이 다른 블록과 겹치는지 확인하는 함수
		int x = getX();
		int y = getY();
		switch(dir) {
		case 0 :	//아래로 이동
			for(int i=0; i < r.length; i++) {
				if(data.getAt(y+r[i]+1, x+c[i]) != 0) {
					return true;
				}
			}
			break;
		case 1: // 왼쪽으로 이동
			for(int i=0; i < r.length; i++) {
				if(data.getAt(y+r[i], x+c[i]-1) != 0) {
					return true;
				}
			}
			break;
		case 2: // 오른쪽
			for(int i=0; i < r.length; i++) {
				if(data.getAt(y+r[i], x+c[i] +1) != 0) {
					return true;
				}
			}
			break;
		}
		return false;	
	}
	
	public int getMinX() {	// 현 블록의 최소 x 좌표
		int min = c[0];
		for(int i=1; i < c.length; i++) {
			if(c[i] < min) {
				min = c[i];
			}
		}
		return min;
	}
	
	public int getMaxX() {
		int max = c[0];
		for(int i=1; i < c.length; i++) {
			if(c[i] > max) {
				max = c[i];
			}
		}
		return max;
	}
	
	public int getMinY() {
		int min = r[0];
		for(int i=1; i < r.length; i++) {
			if(r[i] < min) {
				min = r[i];
			}
		}
		return min;
	}
	
	public int getMaxY() {	// 현재 블록의 최대 y 좌표를 반환하는 메서드
		int max = r[0];
		for(int i=1; i < r.length; i++) {
			if(r[i] > max) {
				max = r[i];
			}
		}
		return max;
	}
	
	public void moveBottom() { // 맨 아래로 이동
		  for(int i=0; i<19; i++){
		   this.moveDown();
		  }
		 }	
	
	public boolean moveDown() { // 아래로 이동
		if(center.y + getMaxY() + 1 < TetrisData.ROW) {	// 블록 센터 + y 좌표
			if(isOverlap(DOWN) != true) {
				center.y++;
			} else {
				return true;
			}
		} else {return true;}
		return false;
	}
	
	public void moveLeft() { // 왼쪽으로 이동
		if(center.x + getMinX() -1 >= 0)
			if(isOverlap(LEFT) != true) {center.x--;}
			else return;
	}
	
	public void moveRight() { // 오른쪽으로 이동
		if(center.x + getMaxX() +1 < TetrisData.COL)
			if(isOverlap(RIGHT) != true) {center.x++;}
			else return;
	}
	
	public void rotate() {
		int rc = roteType();
		if(rc <= 1) return;
		if(rc == 2) {
			rotate4();
			rotate4();
			rotate4();
		} else {
			rotate4();
		}
	}
	
	public void rotate4() { // 조각 회전
		for(int i = 0; i < 4; i++) {
			int temp = c[i];
			c[i] = -r[i];
			r[i] = temp;
		}
		// 블록이 좌측 벽에서 회전했을 때 맵 밖으로 넘어가는 경우
		if(center.x + getMinX()  < 0 && canmove() == false) {
			center.x++;	//블록을 우로 이동
		}

		// 블록이 우측 벽에서 회전했을 때 맵 밖으로 넘어가는 경우
		if(center.x + getMaxX() +1 > TetrisData.COL && canmove() == false) {
			center.x--;	// 블록을 좌로 이동
		}
		
		// 블록 회전 시 다른 블록과 겹치는 경우
		if(canmove() || center.y + getMaxY() +1 > TetrisData.ROW) {
			for(int i = 0; i < 4; i++) {
				int temp = r[i];
				r[i] = -c[i];
				c[i] = temp;
			}
		}
		
	}
	
	// 블록이 다른 블록과 겹치는지 판별하는 함수
	public boolean canmove() {
		if (isOverlap(RIGHT) || isOverlap(LEFT) || isOverlap(DOWN)){
			return true;
		}
		return false;
	}
}
