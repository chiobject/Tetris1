
// 서버 입장하는 사람이 포트&IP를 입력하는 창
package Tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TetrisClientDialog extends JDialog {
    private JTextField portTextField;
    private JTextField IPtextField;
    private TetrisClient tetris_client;
    int port;
    String IP;
    

    public TetrisClientDialog(Frame owner) {
        super(owner, "Port 입력", true);
        setSize(300, 150);
        setLocationRelativeTo(owner);

        JPanel inputPanel = new JPanel();
        GridBagLayout gbl_inputPanel = new GridBagLayout();
        gbl_inputPanel.columnWidths = new int[]{24, 48, 49, 44, 42, 41, 0};
        gbl_inputPanel.rowHeights = new int[]{30, 0, 0, 0, 0};
        gbl_inputPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_inputPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        inputPanel.setLayout(gbl_inputPanel);

        getContentPane().add(inputPanel, BorderLayout.EAST);
        
        JLabel label_1 = new JLabel("아이피 :");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.anchor = GridBagConstraints.WEST;
        gbc_label_1.insets = new Insets(0, 0, 5, 5);
        gbc_label_1.gridx = 1;
        gbc_label_1.gridy = 1;
        inputPanel.add(label_1, gbc_label_1);
        
        IPtextField = new JTextField();
        GridBagConstraints gbc_IPtextField = new GridBagConstraints();
        gbc_IPtextField.fill = GridBagConstraints.BOTH;
        gbc_IPtextField.gridwidth = 3;
        gbc_IPtextField.insets = new Insets(0, 0, 5, 5);
        gbc_IPtextField.gridx = 2;
        gbc_IPtextField.gridy = 1;
        inputPanel.add(IPtextField, gbc_IPtextField);
        
        JLabel label = new JLabel("포트 번호:");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.anchor = GridBagConstraints.WEST;
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 1;
        gbc_label.gridy = 2;
        inputPanel.add(label, gbc_label);
        portTextField = new JTextField();
        GridBagConstraints gbc_portTextField = new GridBagConstraints();
        gbc_portTextField.insets = new Insets(0, 0, 5, 5);
        gbc_portTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_portTextField.gridwidth = 3;
        gbc_portTextField.gridx = 2;
        gbc_portTextField.gridy = 2;
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
                IP = IPtextField.getText();
                tetris_client = new TetrisClient();
                tetris_client.start();
                MyTetris.tetrisCanvas.ismulti = true;
                MyTetris.tetrisCanvas.repaint();
                dispose(); // 다이얼로그 닫기 
            }
        });
    }

    public int getPort() {
        return port;
    }
    
    public String getIP() {
        return IP;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        TetrisClientDialog dialog = new TetrisClientDialog(frame);
        dialog.setVisible(true);

        int port = dialog.getPort();
        System.out.println("입력된 포트 번호: " + port);
    }
}
