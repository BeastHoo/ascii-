package can.code.func;

import can.code.startFrame;
import gnu.io.SerialPort;
import javax.swing.*;
import java.awt.*;

public class FunctionPanel extends JPanel {
    private JCheckBox speedMode;
    private JCheckBox angleMode;
    private static boolean checkFlag;
    private startFrame sF;
    private SerialPort serialPort;

    //方位角模式变量
    private JButton rightMove;
    private JButton leftMove;
    private JButton startAndStop;
    private JLabel angleSet;
    private JTextArea expValField;
    private JLabel expValTag;
    private static int expVal;
    private JLabel diff;
    private JComboBox diffBox;

    //速度模式相关变量
    private JLabel speedSet;
    private static int DefaultSpeed;
    private JSlider speedSlider;
    private JLabel defaultSpeedTag;
    private JTextArea defaultSpeedArea;

    //显示获取的值
    private JLabel CurStatus;
    private JLabel CurSpeed;
    private JLabel CurAngle;
    private JTextArea speed;
    private JTextArea angle;

    //Listener
    private LeftListener leftListener;
    private RightListener rightListener;
    private SsListener ssListener;
    private IOOperate ioOperate;

    static {
        checkFlag=false;
        expVal=15000;
        DefaultSpeed=10;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
        defaultEnabled();
        ssListener.setSerialPort(serialPort);
        ioOperate=ssListener.getIoOperate();
        leftListener.setIoOperate(ioOperate);
        rightListener.setIoOperate(ioOperate);
    }

    public FunctionPanel(startFrame sF)
    {
        init();
        this.sF=sF;
        setLayout(null);
        defaultEnabled();
    }

    private void init()
    {
        speedMode=new JCheckBox("速度模式");
        speedMode.setBounds(40,10,120,40);
        speedMode.setFont(new Font("黑体",Font.PLAIN,16));
        speedMode.setBackground(Color.white);
        angleMode=new JCheckBox("方位角模式");
        angleMode.setBounds(170,10,120,40);
        angleMode.setFont(new Font("黑体",Font.PLAIN,16));
        angleMode.setBackground(Color.white);
        angleMode.setSelected(true);
        speedMode.addChangeListener(e -> {
            JCheckBox checkBox= (JCheckBox) e.getSource();
            if(checkBox.isSelected()==false)
            {
                angleMode.setSelected(true);
                checkFlag=false;
                enabled();
            }else
            {
                angleMode.setSelected(false);
                checkFlag=true;
                enabled();
            }
        });
        angleMode.addChangeListener(e -> {
            JCheckBox checkBox= (JCheckBox) e.getSource();
            if(checkBox.isSelected()==false)
            {
                speedMode.setSelected(true);
                checkFlag=true;
                enabled();
            }else
            {
                speedMode.setSelected(false);
                checkFlag=false;
                enabled();
            }
        });
        //方位角模式相关设置
        angleSet = new JLabel("方位角设置");
        angleSet.setFont(new Font("黑体",Font.BOLD,18));
        angleSet.setBackground(Color.white);
        angleSet.setBounds(20,60,120,40);
        ImageIcon left=new ImageIcon("src/can/code/func/left.png");
        leftMove=new JButton();
        leftMove.setIcon(left);
        ImageIcon right=new ImageIcon("src/can/code/func/right.png");
        rightMove=new JButton();
        rightMove.setIcon(right);
        leftMove.setBounds(60,100,60,40);
        rightMove.setBounds(230,100,60,40);
        leftMove.setBackground(Color.white);
        rightMove.setBackground(Color.white);
        startAndStop=new JButton("开始");
        startAndStop.setFont(new Font("黑体",Font.BOLD,16));
        startAndStop.setBackground(Color.WHITE);
        startAndStop.setBounds(140,100,70,40);


        expValField=new JTextArea("");
        expValTag=new JLabel("当前设定值:");
        expValTag.setBackground(Color.WHITE);
        expValTag.setFont(new Font("黑体",Font.PLAIN,16));
        expValTag.setBounds(30,145,90,40);
        expValField.setEditable(false);
        expValField.setBounds(120,155,50,20);
        expValField.setColumns(5);
        expValField.setRows(1);
        expValField.setFont(new Font("黑体",Font.PLAIN,16));
        expValField.setForeground(Color.magenta);
        expValField.setText(String.valueOf(expVal));

        diff=new JLabel("脉冲间隔:");
        diff.setFont(new Font("黑体",Font.PLAIN,16));
        diff.setBounds(180,145,100,40);
        diff.setBackground(Color.white);
        diffBox=new JComboBox();
        int [] val={1,10,100,1000,10000};
        for(int i:val)
        {
            diffBox.addItem(i);
        }
        diffBox.setFont(new Font("黑体",Font.PLAIN,16));
        diffBox.setBackground(Color.white);
        diffBox.setBounds(255,150,70,25);



        //添加监听
        leftListener=new LeftListener(this,sF);
        leftMove.addActionListener(leftListener);
        rightListener=new RightListener(this,sF);
        rightMove.addActionListener(rightListener);
        ssListener=new SsListener(startAndStop,this);
        startAndStop.addActionListener(ssListener);



        //速度模式相关设置
        speedSet=new JLabel("速度设置");
        speedSet.setBackground(Color.WHITE);
        speedSet.setBounds(20,190,120,40);
        speedSet.setFont(new Font("黑体",Font.BOLD,18));

        defaultSpeedTag=new JLabel("当前设定速度:");
        defaultSpeedArea=new JTextArea();
        defaultSpeedTag.setBackground(Color.WHITE);
        defaultSpeedTag.setFont(new Font("黑体",Font.PLAIN,16));
        defaultSpeedTag.setBounds(30,275,120,40);
        defaultSpeedArea.setEditable(false);
        defaultSpeedArea.setBounds(150,285,50,20);
        defaultSpeedArea.setColumns(5);
        defaultSpeedArea.setRows(1);
        defaultSpeedArea.setFont(new Font("黑体",Font.PLAIN,16));
        defaultSpeedArea.setForeground(Color.magenta);
        defaultSpeedArea.setText(String.valueOf(DefaultSpeed));

        speedSlider=new JSlider(0,60,10);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(2);
        speedSlider.setFont(new Font("黑体",Font.BOLD,10));
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setBackground(Color.white);
        speedSlider.setBounds(20,230,300,40);
        speedSlider.addChangeListener(e -> {
            DefaultSpeed=speedSlider.getValue();
            if(checkFlag)
            {
                if(ioOperate!=null&&ioOperate.isCheck()==2)
                {
                    ioOperate.makeSpeedSendingDataFrame();
                    ioOperate.makeGetDataFrame(checkFlag);
                }
            }
            else {
                if(ioOperate!=null&&ioOperate.isCheck()==1)
                {
                    ioOperate.makeAngleSendingDataFrame();
                    ioOperate.makeGetDataFrame(checkFlag);
                }
            }
            defaultSpeedArea.setText(String.valueOf(DefaultSpeed));
        });


        //当前状态
        CurStatus=new JLabel("当前状态");
        CurStatus.setFont(new Font("黑体",Font.BOLD,18));
        CurStatus.setBounds(20,320,120,40);
        CurStatus.setBackground(Color.WHITE);

        CurAngle=new JLabel("当前方位角:");
        angle=new JTextArea();
        expValTag.setBackground(Color.WHITE);
        CurAngle.setFont(new Font("黑体",Font.PLAIN,16));
        CurAngle.setBounds(30,350,90,40);
        angle.setEditable(false);
        angle.setBounds(120,360,50,20);
        angle.setColumns(5);
        angle.setRows(1);
        angle.setFont(new Font("黑体",Font.PLAIN,16));
        angle.setForeground(Color.RED);
        angle.append(String.valueOf(0));

        CurSpeed=new JLabel("当前速度:");
        speed=new JTextArea();
        CurSpeed.setBackground(Color.WHITE);
        CurSpeed.setFont(new Font("黑体",Font.PLAIN,16));
        CurSpeed.setBounds(180,350,90,40);
        speed.setEditable(false);
        speed.setBounds(270,360,50,20);
        speed.setColumns(5);
        speed.setRows(1);
        speed.setFont(new Font("黑体",Font.PLAIN,16));
        speed.setForeground(Color.red);
        speed.append(String.valueOf(0));


        setBackground(Color.WHITE);
        add(angleMode);
        add(speedMode);
        add(angleSet);
        add(leftMove);
        add(startAndStop);
        add(rightMove);
        add(expValTag);
        add(expValField);
        add(diff);
        add(diffBox);
        add(defaultSpeedTag);
        add(defaultSpeedArea);
        add(speedSet);
        add(speedSlider);
        add(CurStatus);
        add(CurAngle);
        add(angle);
        add(CurSpeed);
        add(speed);
    }


    private void enabled()
    {
        if(checkFlag==false)
        {
            leftMove.setEnabled(true);
            rightMove.setEnabled(true);
            angleSet.setEnabled(true);
            expValTag.setEnabled(true);
            expValField.setEnabled(true);
            diffBox.setEnabled(true);
            diff.setEnabled(true);
            angle.setEnabled(true);
            CurAngle.setEnabled(true);
        }
        else
        {
            leftMove.setEnabled(false);
            rightMove.setEnabled(false);
            angleSet.setEnabled(false);
            expValTag.setEnabled(false);
            expValField.setEnabled(false);
            diffBox.setEnabled(false);
            diff.setEnabled(false);
            angle.setEnabled(false);
            CurAngle.setEnabled(false);
        }
    }


    public int[] getAngleVal()
    {
        int [] ret = new int[]{expVal,Integer.parseInt(diffBox.getSelectedItem().toString().trim())};
        return ret;
    }

    public boolean isCheckFlag()
    {
        return checkFlag;
    }

    public int[] speedAndAngleArray()  //向开始键监听发送值
    {
        return new int[]{expVal,DefaultSpeed};
    }

    public  void setExpVal(int expVal) {
        FunctionPanel.expVal = expVal;
        expValField.setText(String.valueOf(expVal));
    }

    public  void setCurVal(String defaultAngle,String defaultSpeed) {
        angle.setText(defaultAngle);
        speed.setText(defaultSpeed);
    }

    private void defaultEnabled()
    {
        if(serialPort==null)
        {
             speedMode.setEnabled(false);
             angleMode.setEnabled(false);

            //方位角模式变量
            rightMove.setEnabled(false);
            leftMove.setEnabled(false);
            startAndStop.setEnabled(false);
            angleSet.setEnabled(false);
            expValField.setEnabled(false);
            expValTag.setEnabled(false);
            diff.setEnabled(false);
            diffBox.setEnabled(false);

            //速度模式相关变量
            speedSet.setEnabled(false);
            speedSlider.setEnabled(false);
            defaultSpeedTag.setEnabled(false);
            defaultSpeedArea.setEnabled(false);

            //显示获取的值
            CurStatus.setEnabled(false);
            CurSpeed.setEnabled(false);
            CurAngle.setEnabled(false);
            speed.setEnabled(false);
            angle.setEnabled(false);
        }
        else {
            speedMode.setEnabled(true);
            angleMode.setEnabled(true);

            //方位角模式变量
            rightMove.setEnabled(true);
            leftMove.setEnabled(true);
            startAndStop.setEnabled(true);
            angleSet.setEnabled(true);
            expValField.setEnabled(true);
            expValTag.setEnabled(true);
            diff.setEnabled(true);
            diffBox.setEnabled(true);

            //速度模式相关变量
            speedSet.setEnabled(true);
            speedSlider.setEnabled(true);
            defaultSpeedTag.setEnabled(true);
            defaultSpeedArea.setEnabled(true);

            //显示获取的值
            CurStatus.setEnabled(true);
            CurSpeed.setEnabled(true);
            CurAngle.setEnabled(true);
            speed.setEnabled(true);
            angle.setEnabled(true);
        }
    }
}
