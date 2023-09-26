
// 클라 소켓 통신
package Tetris;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TetrisClient extends Thread {
   
    TetrisClientDialog tetrisClientDialog = MyTetris.getClientInputDialog();
    Socket socket = null;
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;
    
    public TetrisClient(){
    	
    }    
    
    public void run() {  
        try {
            socket = new Socket(tetrisClientDialog.getIP(), tetrisClientDialog.getPort());
            System.out.println("서버에 연결되었습니다.");
            MyTetris.mntmNewMenuItem_1.setEnabled(false);
            MyTetris.tetrisMultiCanvas.start();
            while(true) {
	            dataInputStream = new DataInputStream(socket.getInputStream());
	            dataOutputStream = new DataOutputStream(socket.getOutputStream());
	
	            // 서버로부터 배열 크기 수신
	            int rows = dataInputStream.readInt();
	            int cols = dataInputStream.readInt();
	            
	            // 배열 크기로 새로운 배열 생성
	            int[][] receivedArray = new int[rows][cols];
	
	            // 서버로부터 배열 요소 수신
	            for (int i = 0; i < rows; i++) {
	                for (int j = 0; j < cols; j++) {
	                    receivedArray[i][j] = dataInputStream.readInt();
	                }
	            }
	
	            // Tetris 클래스에서 가져올 2차원 배열
	            int[][] tetrisArray = new int[TetrisData.ROW][TetrisData.COL];
		        for (int i = 0; i < TetrisData.ROW; i++) {
		            for (int j = 0; j < TetrisData.COL; j++) {
		            	tetrisArray[i][j] = MyTetris.tetrisCanvas.data.getAt(i,j);
		            }
	            }
	            // 클라이언트에서 서버로 배열 크기 전송
	            dataOutputStream.writeInt(tetrisArray.length);	// 행의 길이 전송
	            dataOutputStream.writeInt(tetrisArray[0].length); // 열의 길이 전송
	
	            // 클라이언트에서 서버로 배열 요소 전송
	            for (int i = 0; i < tetrisArray.length; i++) {
	                for (int j = 0; j < tetrisArray[i].length; j++) {
	                    dataOutputStream.writeInt(tetrisArray[i][j]);
	                }
	            }
	            
	            dataOutputStream.writeInt(MyTetris.tetrisCanvas.score);
	            
	            // 수신한 배열 출력
	            MyTetris.tetrisMultiCanvas.setmatrix(receivedArray);
	            
	            // 상대 점수 갱신
	            MyTetris.tetrisCanvas.enemy_score = dataInputStream.readInt();
	            
	            try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
