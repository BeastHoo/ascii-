package can.code.func;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

public class IOOperate implements SerialPortEventListener {
    private final SerialPort serialPort;
    private final FunctionPanel functionPanel;
    private int check;

    public IOOperate(SerialPort serialPort,FunctionPanel functionPanel)
    {
        check=0;
        this.functionPanel=functionPanel;
        this.serialPort=serialPort;
        try {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }

    public void setCheck(int flag) {
        this.check = flag;
    }

    public int isCheck() {
        return check;
    }

    public void modeSet(boolean flag)
    {
        if(!flag)
        {
            String str2="s "+"r0xc8 "+ 0;
            byte[] bytes=new byte[10];
            int i;
            for(i=0;i<str2.length();i++)
            {
                bytes[i]= (byte) (((int)str2.charAt(i))&0xff);
            }
            bytes[i]=(byte) 0x0D;
            OutputStream outputStream;
            try {
                outputStream=serialPort.getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String str2="s "+"r0x24 "+ 11;
            byte[] bytes=new byte[11];
            int i;
            for(i=0;i<str2.length();i++)
            {
                bytes[i]= (byte) (((int)str2.charAt(i))&0xff);
            }
            bytes[i]=(byte) 0x0D;
            OutputStream outputStream;
            try {
                outputStream=serialPort.getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void makeSpeedSendingDataFrame()
    {
        int[] val= functionPanel.speedAndAngleArray();
        String str1="s "+"r0x2f "+ val[1] * 166.6666;
        if(str1.length()>17) {
            str1 = str1.substring(0, 17);
        }
        byte[] bytes= new byte[20];
        int i;


        for(i=0;i<str1.length();i++)
        {
            bytes[i]= (byte) (((int)str1.charAt(i))&0xff);
        }
        bytes[i]=(byte) 0x0D;

        OutputStream outputStream;
        try {
            outputStream=serialPort.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void makeAngleSendingDataFrame(/*int val,int val1*/)
    {
        int[] val= functionPanel.speedAndAngleArray();
        String str1="s "+"r0xca "+ val[0];

        String str3="s "+"r0xcb "+ val[1] * 166.6666;
        if(str3.length()>17) {
            str3 = str3.substring(0, 17);
        }
        byte[][] bytes= new byte[3][];
        int i;
        bytes[0]=new byte[14];

        for(i=0;i<str1.length();i++)
        {
            bytes[0][i]= (byte) (((int)str1.charAt(i))&0xff);
        }
        bytes[0][i]=(byte) 0x0D;

        bytes[1]=new byte[18];
        for(i=0;i<str3.length();i++)
        {
            bytes[1][i]= (byte) (((int)str3.charAt(i))&0xff);
        }
        bytes[1][i]=(byte) 0x0D;

        bytes[2]=new byte[4];
        bytes[2][0]=(byte)((int) 't'&0xff);
        bytes[2][1]=(byte)((int) ' '&0xff);
        bytes[2][2]=(byte)((int) '1'&0xff);
        bytes[2][3]=(byte)0x0D;
        OutputStream outputStream;
        try {
            outputStream=serialPort.getOutputStream();
            outputStream.write(bytes[0]);
            outputStream.flush();
            outputStream.write(bytes[1]);
            outputStream.flush();
            outputStream.write(bytes[2]);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeAngleStopDataFrame()
    {
        byte[] bytes=new byte[4];
        bytes[0]=(byte)((int)'t' &0xff);
        bytes[1]=(byte)((int)' ' &0xff);
        bytes[2]=(byte)((int)'0' &0xff);
        bytes[3]=(byte)0x0D;
        try {
            OutputStream outputStream=serialPort.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeGetDataFrame(boolean flag)
    {
        byte [][] bytes=new byte[3][8];
        String str1="g r0x32";
        String str2="g r0x3b";
        String str3="g r0x18";
        int i;
        for(i=0;i<str1.length();i++)
        {
            bytes[0][i]= (byte) (((int)str1.charAt(i))&0xff);
        }
        bytes[0][i]=(byte) 0x0D;

        for(i=0;i<str2.length();i++)
        {
            bytes[1][i]= (byte) (((int)str2.charAt(i))&0xff);
        }
        bytes[1][i]=(byte) 0x0D;

        for(i=0;i<str3.length();i++)
        {
            bytes[2][i]= (byte) (((int)str3.charAt(i))&0xff);
        }
        bytes[2][i]=(byte) 0x0D;

        try {
            OutputStream outputStream=serialPort.getOutputStream();
            if(!flag)
            {
                outputStream.write(bytes[0]);
                outputStream.flush();
                outputStream.write(bytes[1]);
            }else
            {
                outputStream.write(bytes[2]);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeSpeedStopDataFrame()
    {
        String str2="s "+"r0x24 "+ 0;
        byte[] bytes=new byte[10];
        int i;
        for(i=0;i<str2.length();i++)
        {
            bytes[i]= (byte) (((int)str2.charAt(i))&0xff);
        }
        bytes[i]=(byte) 0x0D;
        OutputStream outputStream;
        try {
            outputStream=serialPort.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void analyze(String str)
    {
        if(str.equals("ok "))
        {
            return;
        }
        else
        {
            switch (str.charAt(0))
            {
                case 'v':
                    str=str.substring(2,str.length()-1);
                    double speed=Double.parseDouble(str)/166.6666;
                    functionPanel.setCurVal(null,String.valueOf(speed));
                    break;
                case 'a':
                    str=str.substring(2,str.length()-1);
                    functionPanel.setCurVal(str,null);
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if(serialPortEvent.getEventType()==SerialPortEvent.DATA_AVAILABLE) {
            InputStream inputStream = null;
            try {
                inputStream = serialPort.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = new byte[200];
            try {
//
                while(inputStream.available()>0)
                {
                    inputStream.read(bytes);
                    analyze(new String(bytes));
                }

            }catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }
}
