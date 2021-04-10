package can.code;

import gnu.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


//串口设置界面
public class SerialPortGSPanel extends JPanel implements ActionListener {
    private JComboBox portName;
    private JLabel PortName;
    private JComboBox baudRate;
    private JLabel BaudRate;
    private final String[] rate={"1200","2400","4800","9600","19200","38400","57600","115200","128000","256000","921600"};
    private JLabel DataBits;
    private JComboBox dataBits;
    private JComboBox checkBits;
    private JLabel CheckBits;
    private JComboBox stopBits;
    private JLabel StopBits;
    private JButton flushButton;
    private JButton openButton;
    private SerialPort serialPort;
    private FlushListener flushListener;
    private startFrame sF;

    
    public SerialPortGSPanel(startFrame sF)
    {
        setLayout(null);
        setBackground(Color.white);
        this.sF=sF;
        init();
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }
    public JComboBox getportName()
    {
        return portName;
    }


    private void init(){
        flushListener=new FlushListener(this,sF);
        serialPort=null;
        JLabel name=new JLabel("串口设置");
        name.setFont(new Font("黑体",Font.BOLD,20));
        name.setForeground(Color.darkGray);
        name.setBackground(Color.white);
        name.setBounds(0,0,100,40);


        //串口名展示
        portName=new JComboBox();
        portName.setBounds(110,50,110,40);
        List<String> list=getPortName();
        if(list.isEmpty()==true)
        {
            CustomDialog NoSerialPortFound=new CustomDialog(sF,"Error");
            NoSerialPortFound.setVal(new String[]{"未检测到串口!请重试"});
        }
        for(String str:list)
        {
            portName.addItem(str);
        }
        PortName=new JLabel("串口号");
        PortName.setBackground(Color.WHITE);
        PortName.setBounds(30,50,90,40);
        PortName.setForeground(Color.black);
        PortName.setFont(new Font("黑体",Font.BOLD,16));
        portName.setForeground(Color.black);
        portName.setBackground(Color.white);
        portName.setFont(new Font("黑体",Font.BOLD,16));


        //波特率展示
        baudRate=new JComboBox();
        for(String str:rate)
        {
            baudRate.addItem(str);
        }
        baudRate.setBackground(Color.white);
        baudRate.setForeground(Color.black);
        BaudRate=new JLabel("波特率");
        BaudRate.setBackground(Color.white);
        BaudRate.setForeground(Color.black);
        BaudRate.setFont(new Font("黑体",Font.BOLD,16));
        BaudRate.setBounds(30,100,60,40);
        baudRate.setBounds(110,100,110,40);
        baudRate.setFont(new Font("黑体",Font.BOLD,16));


        //数据位
        DataBits=new JLabel("数据位");
        DataBits.setBounds(30,150,60,40);
        DataBits.setBackground(Color.WHITE);
        DataBits.setForeground(Color.black);
        DataBits.setFont(new Font("黑体",Font.BOLD,16));
        dataBits=new JComboBox();
        int []val={5,6,7,8};
        for(int i:val)
        {
            dataBits.addItem(i);
        }
        dataBits.setBounds(110,150,110,40);
        dataBits.setForeground(Color.black);
        dataBits.setBackground(Color.white);
        dataBits.setFont(new Font("黑体",Font.BOLD,16));


        //奇偶校验位
        String []str=new String[]{"NoParity","EvenParity","OddParity","SpaceParity","MarkParity"};
        checkBits=new JComboBox();
        for(String s:str)
        {
            checkBits.addItem(s);
        }
        CheckBits=new JLabel("校 验");
        CheckBits.setForeground(Color.black);
        CheckBits.setBackground(Color.WHITE);
        CheckBits.setFont(new Font("黑体",Font.BOLD,16));
        CheckBits.setBounds(30,200,60,40);
        checkBits.setFont(new Font("黑体",Font.TYPE1_FONT,14));
        checkBits.setBackground(Color.WHITE);
        checkBits.setForeground(Color.black);
        checkBits.setBounds(110,200,110,40);


        //停止位
        str=new String[]{"1","1.5","2"};
        stopBits=new JComboBox();
        for(String s:str)
        {
            stopBits.addItem(s);
        }
        stopBits.setBounds(110,250,110,40);
        stopBits.setForeground(Color.black);
        stopBits.setBackground(Color.white);
        stopBits.setFont(new Font("黑体",Font.BOLD,16));
        StopBits=new JLabel("停止位");
        StopBits.setBounds(30,250,60,40);
        StopBits.setBackground(Color.white);
        StopBits.setForeground(Color.black);
        StopBits.setFont(new Font("黑体",Font.BOLD,16));


        //刷新 键设置
        flushButton=new JButton("刷 新");
        flushButton.setBounds(20,300,80,45);
        flushButton.setBackground(Color.lightGray);
        flushButton.setForeground(Color.BLACK);
        flushButton.setFont(new Font("黑体",Font.TRUETYPE_FONT,16));
        flushButton.addActionListener(flushListener);


        //打开串口 键设置
        openButton=new JButton("打开串口");
        openButton.setFont(new Font("黑体",Font.TRUETYPE_FONT,16));
        openButton.setBackground(Color.lightGray);
        openButton.setForeground(Color.BLACK);
        openButton.setBounds(120,300,100,45);

        openButton.addActionListener(this);
        add(name);
        add(PortName);
        add(portName);
        add(BaudRate);
        add(baudRate);
        add(DataBits);
        add(dataBits);
        add(CheckBits);
        add(checkBits);
        add(StopBits);
        add(stopBits);
        add(flushButton);
        add(openButton);
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    //获取系统所有的串口
    public List<String> getPortName()
    {
        List<String> list=new ArrayList<>();
        Enumeration<CommPortIdentifier> portIdentifierEnumeration=CommPortIdentifier.getPortIdentifiers();
        while(portIdentifierEnumeration.hasMoreElements())
        {
            list.add(portIdentifierEnumeration.nextElement().getName());
        }
        return list;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        CommPort commPort = null;
        CommPortIdentifier commPortIdentifier=null;
        String trim1=portName.getSelectedItem().toString().trim();
        try {
            commPortIdentifier=CommPortIdentifier.getPortIdentifier(trim1);
        } catch (NoSuchPortException noSuchPortException) {
            noSuchPortException.printStackTrace();
        }

        try {
            commPort=commPortIdentifier.open(trim1,5000);
        } catch (PortInUseException portInUseException) {

            //errorDialog
            CustomDialog PortInUseDialog = new CustomDialog(sF,"Error");
            PortInUseDialog.setVal(new String[]{"该串口已被其他应用占用!"});
            portInUseException.printStackTrace();
        }
        String trim2 = baudRate.getSelectedItem().toString().trim();
        String trim3 = dataBits.getSelectedItem().toString().trim();
        String trim4 = checkBits.getSelectedItem().toString().trim();
        String trim5 = stopBits.getSelectedItem().toString().trim();
        int checkBit=0;
        switch (trim4)
        {
            case "NoParity":
                checkBit= SerialPort.PARITY_NONE;
                break;
            case "EvenParity":
                checkBit=SerialPort.PARITY_EVEN;
                break;
            case "OddParity":
                checkBit=SerialPort.PARITY_ODD;
                break;
            case "SpaceParity":
                checkBit=SerialPort.PARITY_SPACE;
                break;
            case "MarkParity":
                checkBit=SerialPort.PARITY_MARK;
                break;
            default:
                break;
        }


        if(commPort instanceof SerialPort)
        {
            serialPort= (SerialPort) commPort;

            try {
                serialPort.setSerialPortParams(Integer.parseInt(trim2),Integer.parseInt(trim3),Integer.parseInt(trim5),checkBit);
                //
                CustomDialog OpenSuccess = new CustomDialog(sF,"Success");
                OpenSuccess.setVal(new String[]{"串口 "+serialPort.getName()+ "打开成功"});

                /*FunctionFrame functionFrame =new FunctionFrame(serialPort);
                jf.dispose();
                functionFrame.show();*/
                sF.setSerialPort(serialPort);
            } catch (UnsupportedCommOperationException unsupportedCommOperationException) {
                unsupportedCommOperationException.printStackTrace();
                if(serialPort==null)
                {
                    serialPort.close();
                }
                //errorDialog
            }
        }else{
            //errorDialog
            CustomDialog NotSerialPortDialog = new CustomDialog(sF,"Error");
            NotSerialPortDialog.setVal(new String[]{"当前端口 "+commPort.getName()+" 不是串口!"});
        }

    }

}
