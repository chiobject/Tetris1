
package Tetris;

public class Z_Piece extends Piece {
	public Z_Piece(TetrisData data) {
		super(data);
		c[0] = 0;	r[0] = 0;
		c[1] = 1;	r[1] = 0;
		c[2] = 0;	r[2] = -1;
		c[3] = -1;	r[3] = -1;
	}
	
	public int getType() {
		return 1;
	}
	
	public int roteType() {
		return 3;
	}
}
