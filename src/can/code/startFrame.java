package can.code;

import can.code.func.FunctionPanel;
import gnu.io.SerialPort;

import javax.swing.*;

public class startFrame extends JFrame {
    private SerialPortGSPanel serialPortGSPanel;
    private ImageIcon imageIcon;
    private JSplitPane jSplitPane;
    private FunctionPanel functionPanel;

    public startFrame()
    {
        init();

        setBounds(600,250,620,430);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("通信上位机");
        setVisible(true);
    }

    public void init()
    {
        imageIcon=new ImageIcon("src/can/code/need2.png");
        setIconImage(imageIcon.getImage());
        serialPortGSPanel=new SerialPortGSPanel(this);
        functionPanel=new FunctionPanel(this);

        jSplitPane=new JSplitPane();
        jSplitPane.setDividerSize(1);
        jSplitPane.setDividerLocation(260);
        jSplitPane.setLeftComponent(serialPortGSPanel);
        jSplitPane.setRightComponent(functionPanel);
        add(jSplitPane);
    }

    public void setSerialPort(SerialPort serialPort) {
        functionPanel.setSerialPort(serialPort);
    }
}
