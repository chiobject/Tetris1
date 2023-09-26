// 테트리스 멀티 플레이 전용 캔버스
package Tetris;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisMultiCanvas extends JPanel 
	implements Runnable {
	protected static Thread worker;
	protected Color colors[];	// 블록 색
	protected int w = 25;
	protected TetrisData data = new TetrisData();	// 테트리스 데이터
	protected int margin = 20;	
	
	public TetrisMultiCanvas() {	// 테트리스 색 정하기	
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
		data.clear();	// 테트리스 데이터 초기화
		worker = new Thread(this);
		worker.start();
		requestFocus();
		repaint();
	}

	public void paint(Graphics g) {
		  super.paint(g);
		  
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
	}
		
	public Dimension getPreferredSize() {
		int tw = w * TetrisData.COL + margin;
		int th = w * TetrisData.ROW + margin;
		return new Dimension(tw, th);
	}
	
	public synchronized void run() {
		while(true) {
			repaint();	// 테트리스 화면 갱신
		}
	}
	
	public void setmatrix(int matrix[][]) {
		
		for (int i = 0; i < data.ROW; i++) {
            for (int j = 0; j < data.COL; j++) {
                data.setAt(i, j, matrix[i][j]);
            }
        }
	}
}
	






