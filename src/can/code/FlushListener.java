package can.code;

import can.code.func.FunctionPanel;
import gnu.io.SerialPort;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FlushListener implements ActionListener {
    private SerialPortGSPanel serialPortGSPanel;
    private startFrame sF;
    public FlushListener(SerialPortGSPanel serialPortGSPanel,startFrame sF)
    {
        this.sF=sF;
        this.serialPortGSPanel=serialPortGSPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SerialPort serialPort=serialPortGSPanel.getSerialPort();
        if(serialPort!=null)
        {
            serialPort.close();
            serialPortGSPanel.setSerialPort(null);
            sF.setSerialPort(null);
        }
        List<String> list=serialPortGSPanel.getPortName();
        serialPortGSPanel.getportName().removeAllItems();
        serialPortGSPanel.getportName().repaint();
        for(String str:list)
        {
            serialPortGSPanel.getportName().addItem(str);
        }
    }
}
