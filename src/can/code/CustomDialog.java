package can.code;

import javax.swing.*;
import java.awt.*;

public class CustomDialog extends JDialog {
    private JFrame jf;
    public CustomDialog(JFrame jf,String title)
    {
        super(jf,title,true);

    }

    public void setVal(String []str)
    {
        setLayout(new GridLayout(str.length,1));
        setBounds(800,350,300,str.length*80);
        setBackground(Color.white);
        for(String s:str)
        {
            JLabel jLabel=new JLabel(s);
            jLabel.setBackground(Color.white);
            jLabel.setFont(new Font("宋体",Font.BOLD,16));
            jLabel.setForeground(Color.RED);
            add(jLabel);
        }
        setVisible(true);
    }
}
