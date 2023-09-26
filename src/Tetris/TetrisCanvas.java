//테트리스 캔버스

package Tetris;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisCanvas extends JPanel 
	implements Runnable, KeyListener{
	protected static Thread worker;
	protected Color colors[];	// 블록 색
	protected int w = 25;
	protected TetrisData data = new TetrisData();;	// 테트리스 데이터
	protected int margin = 20;	
	protected boolean stop, makeNew;	 // 게임 상태 관리 변수
	protected Piece current, Next;	// 현재 블록 및 다음 블록
	protected int interval = 1000;	// 블록 이동 간격
	protected int level = 1;	// 테트리스 스테이지 레벨
	protected int score = 0;
	protected int enemy_score = 0;
	protected static boolean isHold = false;	// 홀드 기능 여부
	protected boolean ismulti = false;
	protected boolean value;	// 테트리스 스테이지 레벨	
	protected Piece Hold = null;	// 홀드 블록
	
	public TetrisCanvas() {	// 테트리스 색상 설정
		addKeyListener(this);	// 키 이벤트 리스너 설정
		colors = new Color[8];	// 테트리스 배경 및 조각 색
		colors[0] = new Color(80, 80, 80);	//배경색(검은회색)
		colors[1] = new Color(255,0,0);	//빨간색
		colors[2] = new Color(0,255,0);	//녹색
		colors[3] = new Color(0,200,255);	//노란색
		colors[4] = new Color(255,255,0);	//하늘색
		colors[5] = new Color(255,150,0);	//황토색
		colors[6] = new Color(210,0,240);	//보라색
		colors[7] = new Color(40,0,240);	//파란색
		
	}
	public void start() {	//게임 시작
		if (stop = true) {
			stop();
		}
		data.clear();	// 테트리스 데이터 초기화
		worker = new Thread(this);
		worker.start();
		makeNew = true;
		stop = false;
		value = false;
		requestFocus();
		repaint();
	}
	
	public void stop() {
		stop = true;
		current = null;
	}
	
	public void paint(Graphics g) {
		  super.paint(g);
		  score = data.getLine() * 175 * level;
		  g.setFont(new Font(null, Font.BOLD, 18));	// 점수 표시하기
		  g.drawString("score : " + score, 270, 510);
		  
		  int line = data.getLine(); 
		  g.setFont(new Font(null, Font.BOLD, 18));	// 지운 줄 표시하기
		  g.drawString("line : " + line, 270, 490);
		  
		  g.setFont(new Font(null, Font.BOLD, 18));	// 레벨 표시하기
		  g.drawString("level : " + level, 270, 470);
		  
		  if(ismulti == true) {
			  g.setFont(new Font(null, Font.BOLD, 18));	// 상대 점수 표시하기
			  g.drawString("enemy score : " + enemy_score, 270, 400);
		  }
		  
		//쌓인 조각들 그리기
		for(int i = 0; i < TetrisData.ROW; i++) {
			for(int k = 0; k < TetrisData.COL; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(colors[data.getAt(i, k)]);
					g.draw3DRect(margin/2 + w * k,
							margin/2 + w * i, w, w, true);
				}	else {
					g.setColor(colors[data.getAt(i, k)]);
					g.fill3DRect(margin/2 + w * k,
							margin/2 + w * i, w, w, true);
				}
			}
			
		}
		
		// 현재 내려오고 있는 테트리스 조각 그리기
		if(current != null) {
			for(int i = 0; i < 4; i++) {
				g.setColor(colors[current.getType()]);
				g.fill3DRect(margin/2 + w * (current.getX()+current.c[i]), margin/2 + w * (current.getY()+current.r[i]), w, w, true);
				
				g.setColor(colors[Next.getType()]);
				g.fill3DRect(w * Next.c[i] + 330, w * Next.r[i] + 100, w, w, true);
				
				if (Hold != null) {
					g.setColor(colors[Hold.getType()]);
					g.fill3DRect(w * (Hold.c[i]) + 330, w * (Hold.r[i]) + 280, w, w, true);
				}
			}
		}
		
		// Next 피스 공간
		g.setColor(colors[1]);
		g.draw3DRect(280, 40, 130, 130, true);
		g.drawString("Next", 320, 30);
		
		// Hold 피스 공간
		g.setColor(colors[1]);
		g.draw3DRect(280, 220, 130, 130, true);
		g.drawString("Hold", 320, 200);
	}
	
	// 바둑판 모양 공간
	public Dimension getPreferredSize() {
		int tw = w * TetrisData.COL + margin;
		int th = w * TetrisData.ROW + margin;
		return new Dimension(tw, th);
	}
	
	
	public synchronized void run() {
		int random, random2;	// piece 무작위 선정
		random2 = (int)(Math.random() * Integer.MAX_VALUE) % 7;
		data.line = 0;
		level = 1;
		while(!stop) {
			try {
				if(makeNew) {	// 블록을 새로 출력해야 될 경우
					if (value == false) {
						random = (int)(Math.random() * Integer.MAX_VALUE) % 7;
						value = true;
					} else {
						random = random2;
						random2 = (int)(Math.random() * Integer.MAX_VALUE) % 7;
				}
					switch(random) {	// current 블록 생성
					case 0:
						current = new I_Piece(data);
						break;
					case 1:
						current = new J_Piece(data);
						break;
					case 2:
						current = new L_Piece(data);
						break;
					case 3:
						current = new O_Piece(data);
						break;
					case 4:
						current = new S_Piece(data);
						break;
					case 5:
						current = new T_Piece(data);
						break;
					case 6:
						current = new Z_Piece(data);
						break;
					}
					
					switch(random2) {	// NEXT 블록 생성
					case 0:
						Next = new I_Piece(data);
						break;
					case 1:
						Next = new J_Piece(data);
						break;
					case 2:
						Next = new L_Piece(data);
						break;
					case 3:
						Next = new O_Piece(data);
						break;
					case 4:
						Next = new S_Piece(data);
						break;
					case 5:
						Next = new T_Piece(data);
						break;
					case 6:
						Next = new Z_Piece(data);
						break;
					}
					makeNew = false;	// 블록 새로 생성 X
					continue;
					
				}	else {	// 현재 만들어진 테트리스 조각 아래로 이동
						if(current.moveDown()) {	// 현재 블록이 다 내려왔을 경우
							makeNew = true;	// 블록 새로 생성
							if(current.copy()) {	// 게임 오버
								MyTetris.tetrisCanvas.stop();
								Music.stop();
								Ranking_Record RankingSave = new Ranking_Record();
								RankingSave.setVisible(true);
								MyTetris.mntmNewMenuItem_start.setEnabled(true);
							}
							current = null;
							continue;
						}
					}
					repaint();	// 테트리스 화면 갱신
					// 테트리스 레벨에 따른 스피드 조절, 레벨업하는데 드는 블록 = 10 + 레벨당 * 2, 최대 10레벨
					if (data.getLine() / (10 * level + level * 2)== 1 && level < 10) {
						level++;
					}
					data.removeLines();	// 줄 검사
					Thread.currentThread().sleep(interval - (level-1) * 90);	// 블록 딜레이 발생
					
			}	catch(Exception e) {
				}
			}
		}
	
	// 키보드를 이용해서 테트리스 조각 제어
	public void keyPressed(KeyEvent e) {
		if(current == null) return;
		
		switch(e.getKeyCode())	{
		case 67 :
			if (Hold == null) {
				Hold = current;	//현재 piece을 hold
				makeNew = true;	// piece 갱신
				isHold = true;	// hold 기능 비활성화(TetrisData에서 활성화)
				worker.interrupt();
			}
			
			else if (Hold != null && isHold != true) {
				Piece temp;
				temp = current;	//현재 piece을 hold
				current = Hold;	// piece 갱신
				Hold = temp;
				isHold = true;	// hold 기능 비활성화
				worker.interrupt();
			}
			Hold.center.x = 5;	// 블록 위치 중앙으로 이동
			Hold.center.y = 0; 	//현재 작업 중단
			break;
			
		case 32: // 스페이스 바
			repaint();
			current.moveBottom();
			repaint();
			worker.interrupt();
			break;
			
		case 37: // 왼쪽 화살표
			current.moveLeft();
			repaint();
			break;
			
		case 39:	// 오른쪽 화살표
			current.moveRight();
			repaint();
			break;
			
		case 38 :	// 윗쪽 화살표
			current.rotate();
			repaint();
			break;
			
		case 40 :	// 아랫쪽 화살표
			boolean temp = current.moveDown();
			if(temp) {
				makeNew = true;
				if(current.copy()) {
					stop();
					score = data.getLine() * 175 * level;
					JOptionPane.showMessageDialog(this,
							 "게임끝\n점수 : " + score);
				}
			}
				repaint();
		}
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
	
