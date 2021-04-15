package can.code.func;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;


public class IOOperate  {
    private Socket socket;
    private final FunctionPanel functionPanel;
    private int check;
    private ErrorInfoPanel errorInfoPanel;
    private String ip;
    private int port;

    public IOOperate(Socket socket, FunctionPanel functionPanel,String ip,int port)
    {
        check=0;
        this.functionPanel=functionPanel;
        this.socket=socket;
        listenThread listenthread=new listenThread();
        listenthread.start();
        this.ip=ip.substring(1);
        this.port=port;
    }

    public void setCheck(int flag) {
        this.check = flag;
    }

    public int isCheck() {
        return check;
    }

    public void setErrorInfoPanel(ErrorInfoPanel errorInfoPanel) {
        this.errorInfoPanel = errorInfoPanel;
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
                if(socket.isOutputShutdown())
                {
                    socket=new Socket(ip,port);
                }
                outputStream=socket.getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();
                socket.shutdownOutput();
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
                if(socket.isOutputShutdown())
                {
                    socket=new Socket(ip,port);
                }
                outputStream=socket.getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void makeSpeedSendingDataFrame()
    {
        int[] val= functionPanel.speedAndAngleArray();
        String str1="s "+"r0x2f "+ val[1] * 1666.666;
        if(str1.length()>17) {
            str1 = str1.substring(0, 17);
        }
        byte[] bytes= new byte[18];
        int i;


        for(i=0;i<str1.length();i++)
        {
            bytes[i]= (byte) (((int)str1.charAt(i))&0xff);
        }
        bytes[i]=(byte) 0x0D;

        OutputStream outputStream;
        try {
            if(socket.isOutputShutdown())
            {
                socket=new Socket(ip,port);
            }
            outputStream=socket.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void makeAngleSendingDataFrame(/*int val,int val1*/)
    {
        int[] val= functionPanel.speedAndAngleArray();
        String str1="s "+"r0xca "+ val[0];

        String str3="s "+"r0xcb "+ val[1] * 1666.666;
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
            if(socket.isOutputShutdown())
            {
                socket=new Socket(ip,port);
            }
            outputStream=socket.getOutputStream();
            outputStream.write(bytes[0]);
            outputStream.flush();
            outputStream.write(bytes[1]);
            outputStream.flush();
            outputStream.write(bytes[2]);
            outputStream.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeAngleStopDataFrame()
    {
        String str="s r0x24 0";
        byte[] bytes=new byte[10];
        int i;
        for(i=0;i<str.length();i++)
        {
            bytes[i]=(byte)((int)str.charAt(i)&0xff);
        }
        bytes[i]=(byte)0x0D;
        try {
            if(socket.isOutputShutdown())
            {
                socket=new Socket(ip,port);
            }
            OutputStream outputStream=socket.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            socket.shutdownOutput();
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
            if(socket.isOutputShutdown())
            {
                socket=new Socket(ip,port);
            }
            OutputStream outputStream=socket.getOutputStream();
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
            socket.shutdownOutput();
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
            if(socket.isOutputShutdown())
            {
                socket=new Socket(ip,port);
            }
            outputStream=socket.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void divideDataFrame(byte[] bytes)
    {
        int start=0;
        for(int i=0;i<bytes.length;i++)
        {
            if(bytes[i]==(byte)0x0d)
            {
                byte[] temp= Arrays.copyOfRange(bytes,start,i);
                analyze(new String(temp));
                start=i+1;
            }
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
                    str=str.substring(2);
                    double speed=Double.parseDouble(str)/1666.666;
                    functionPanel.setCurVal(null,String.valueOf(speed));
                    break;
                case 'a':
                    str=str.substring(2);
                    functionPanel.setCurVal(str,null);
                    break;
                default:
                    break;
            }
        }

    }

    class listenThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            try {
                if(socket.isInputShutdown())
                {
                    socket=new Socket(ip,port);
                }
                InputStream inputStream=socket.getInputStream();
                byte[] bytes=null;
                while(0<inputStream.available())
                {
                    inputStream.read(bytes);
                    divideDataFrame(bytes);
                }
                socket.shutdownInput();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

