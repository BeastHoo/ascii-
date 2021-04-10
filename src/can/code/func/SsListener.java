package can.code.func;

import gnu.io.SerialPort;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SsListener implements ActionListener {
    private JButton jButton;
    private FunctionPanel functionPanel;
    private IOOperate ioOperate;
    public SsListener(JButton jButton,FunctionPanel functionPanel)
    {
        this.jButton=jButton;
        this.functionPanel=functionPanel;
    }

    public IOOperate getIoOperate() {
        return ioOperate;
    }

    public void setSerialPort(SerialPort serialPort) {
        ioOperate=new IOOperate(serialPort,functionPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(jButton.getText().equals("开始"))
        {
                jButton.setText("停止");
//                int [] val=functionPanel.speedAndAngleArray();
                boolean flag= functionPanel.isCheckFlag();

                if(!flag)
                {
                    ioOperate.setCheck(1);
                    ioOperate.modeSet(false);
                    ioOperate.makeAngleSendingDataFrame(/*val[0],val[1]*/);
                    ioOperate.makeGetDataFrame(false);
                }
                else
                {
                    ioOperate.setCheck(2);
                    ioOperate.modeSet(true);
                    ioOperate.makeSpeedSendingDataFrame();
                    ioOperate.makeGetDataFrame(true);
                }
        }
        else {
            boolean flag= functionPanel.isCheckFlag();
            if(!flag)
            {
                ioOperate.makeAngleStopDataFrame();
            }else
            {
                ioOperate.makeSpeedStopDataFrame();
            }

            ioOperate.setCheck(0);
            jButton.setText("开始");
        }
    }
}
