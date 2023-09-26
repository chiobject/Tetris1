
// 서버 여는 사람이 포트를 입력하는 창
package Tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisServerDialog extends JDialog {
    private JTextField portTextField;
    private TetrisServer tetris_server;
    int port;

    public TetrisServerDialog(Frame owner) {
        super(owner, "Port 입력", true);
        setSize(300, 150);
        setLocationRelativeTo(owner);

        JPanel inputPanel = new JPanel();
        GridBagLayout gbl_inputPanel = new GridBagLayout();
        gbl_inputPanel.columnWidths = new int[]{80, 53, 7, 55, 0, 0, 0};
        gbl_inputPanel.rowHeights = new int[]{23, 0, 0, 0, 0};
        gbl_inputPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_inputPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        inputPanel.setLayout(gbl_inputPanel);

        getContentPane().add(inputPanel, BorderLayout.EAST);
        
        JLabel label = new JLabel("포트 번호:");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.anchor = GridBagConstraints.WEST;
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 1;
        gbc_label.gridy = 1;
        inputPanel.add(label, gbc_label);
        portTextField = new JTextField();
        GridBagConstraints gbc_portTextField = new GridBagConstraints();
        gbc_portTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_portTextField.gridwidth = 3;
        gbc_portTextField.insets = new Insets(0, 0, 5, 5);
        gbc_portTextField.gridx = 2;
        gbc_portTextField.gridy = 1;
        inputPanel.add(portTextField, gbc_portTextField);
        JButton saveButton = new JButton("저장");
        GridBagConstraints gbc_saveButton = new GridBagConstraints();
        gbc_saveButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_saveButton.gridx = 5;
        gbc_saveButton.gridy = 3;
        inputPanel.add(saveButton, gbc_saveButton);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String portText = portTextField.getText();
                port = Integer.parseInt(portText); // 포트 값을 정수로 변환
            	tetris_server = new TetrisServer();
            	tetris_server.start();
            	MyTetris.tetrisCanvas.ismulti = true;
                MyTetris.tetrisCanvas.repaint();
                dispose(); // 다이얼로그 닫기
         
            }
        });
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        TetrisServerDialog dialog = new TetrisServerDialog(frame);
        dialog.setVisible(true);

        int port = dialog.getPort();
        System.out.println("입력된 포트 번호: " + port);
    }
}
