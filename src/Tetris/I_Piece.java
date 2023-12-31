
// BAR 블록 데이터

package Tetris;

public class I_Piece extends Piece {
	public I_Piece(TetrisData data) {
		super(data);
		c[0] = -1;	r[0] = 0;
		c[1] = 0;	r[1] = 0;
		c[2] = 1;	r[2] = 0;
		c[3] = 2;	r[3] = 0;
	}
	
	public int getType() {
		return 3;
	}
	
	public int roteType() {
		return 2;
	}
}
