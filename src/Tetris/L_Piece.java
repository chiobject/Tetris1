// EL 블록 데이터

package Tetris;

public class L_Piece extends Piece {
	public L_Piece(TetrisData data) {
		super(data);
		c[0] = 0;	r[0] = 0;
		c[1] = 1;	r[1] = 0;
		c[2] = -1;	r[2] = 0;
		c[3] = 1;	r[3] = -1;
	}
	
	public int getType() {
		return 5;
	}
	
	public int roteType() {
		return 4;
	} 
}
