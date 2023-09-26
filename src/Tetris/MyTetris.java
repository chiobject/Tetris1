package Tetris;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JButton;
;
public class MyTetris extends JFrame {

	private JPanel contentPane;
	private TetrisData tetrisData;
    private TetrisServer tetrisServer;
	private Ranking_Leaderboard ranking_Leaderboard;
    private static TetrisServerDialog serverinputdialog;
    private static TetrisClientDialog clientinputdialog;
    protected static JMenuItem mntmNewMenuItem;
    protected static TetrisCanvas tetrisCanvas;
    protected static TetrisMultiCanvas tetrisMultiCanvas;
	protected static JMenuItem mntmNewMenuItem_start;
	protected static JMenuItem mntmNewMenuItem_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyTetris frame = new MyTetris();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyTetris() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 590);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("메뉴");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem_start = new JMenuItem("시작");
		mntmNewMenuItem_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Music.Play_Bgm("TetrisBGM.wav",-20);	// 테트리스 노래 시작
				tetrisCanvas.start();	// 테트리스 시작
				mntmNewMenuItem_start.setEnabled(false);	// 시작 버튼 비활성화
			}
		});
		mnNewMenu.add(mntmNewMenuItem_start);
		
		JMenuItem mntmNewMenuItem_restart = new JMenuItem("재시작");
		mntmNewMenuItem_restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Music.stop(); // 실행 중인 노래 초기화
				tetrisCanvas.stop();  // 테트리스 캔버스 중단
				tetrisCanvas.worker.interrupt();	// 테트리스 캔버스 초기화
				Music.Play_Bgm("TetrisBGM.wav",-20);	// 테트리스 노래 시작
				tetrisCanvas.start(); // 테트리스 시작
			}
		});
		mnNewMenu.add(mntmNewMenuItem_restart);
		
		JMenuItem mntmNewMenuItem_exit = new JMenuItem("종료");
		mntmNewMenuItem_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_exit);
		
		JMenu mnNewMenu_1 = new JMenu("멀티");
		menuBar.add(mnNewMenu_1);
		
		mntmNewMenuItem = new JMenuItem("서버 생성");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverinputdialog = new TetrisServerDialog(null);
				serverinputdialog.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem("서버 접속");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientinputdialog = new TetrisClientDialog(null);
				clientinputdialog.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("창 확장");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tetrisMultiCanvas.setVisible(true);
				setBounds(100, 100, 725, 590);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("창 축소");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tetrisMultiCanvas.setVisible(false);
				setBounds(100, 100, 440, 590);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_2 = new JMenu("랭크");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("랭크");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ranking_Leaderboard = new Ranking_Leaderboard();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ranking_Leaderboard.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		tetrisCanvas = new TetrisCanvas();
		contentPane.add(tetrisCanvas);
		tetrisCanvas.setLayout(new BorderLayout(0, 0));
		
		tetrisMultiCanvas = new TetrisMultiCanvas();
		contentPane.add(tetrisMultiCanvas, BorderLayout.EAST);
		tetrisCanvas.setLayout(new BorderLayout(0, 0));
		tetrisMultiCanvas.setVisible(false);
	}

	public TetrisCanvas getTetrisCanvas() {
		return tetrisCanvas;
	}
	

	
	public static TetrisClientDialog getClientInputDialog() {
		return clientinputdialog;
	}
	
	public static TetrisServerDialog getServerInputDialog() {
		return serverinputdialog;
	}
	
}
