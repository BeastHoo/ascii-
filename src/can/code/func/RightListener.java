package can.code.func;

import can.code.CustomDialog;
import can.code.startFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightListener implements ActionListener {
    private FunctionPanel functionPanel;
    private int[] val;
    private IOOperate ioOperate;
    private startFrame sF;

    public RightListener(FunctionPanel functionPanel, startFrame sF)
    {
        this.functionPanel=functionPanel;
        this.sF=sF;
    }
    public void setIoOperate(IOOperate ioOperate) {
        this.ioOperate = ioOperate;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        val=functionPanel.getAngleVal();
        int cur=val[0]+val[1];
        if (cur>100000)
        {
            CustomDialog cd=new CustomDialog(sF,"Error");
            cd.setVal(new String[]{"目标值越界"});
            return;
        }
        functionPanel.setExpVal(cur);
        if(ioOperate!=null&& ioOperate.isCheck()==1)
        {
            ioOperate.makeAngleSendingDataFrame();
            ioOperate.makeGetDataFrame(false);
        }

    }
}
