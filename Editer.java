
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import javax.swing.*;


public class Editer extends JFrame implements ActionListener,KeyListener
{
    JFrame jf;
    JMenuBar mb;
    JMenu mFile,mEdit,mFormat,mRun,mHelp;
    JMenuItem mFilMenuItem[]=new JMenuItem[6];
    String StrFileMenuItem[]={"New","Open","Save","Save As...","Print...","Exit"};
    JMenuItem mEditMenuItem[]=new JMenuItem[6];
    String strEditMenuItem[]={"Cut","Copy","Paste","Delete","Select All","Time & Date"};
    JMenuItem mFormatJMenuItem[]=new JMenuItem[1];
    String strFormatItem[]={"Color"};
    JMenuItem mRunMenuItem[]=new JMenuItem[2];
    String strRunMenuItem[]={"Compile","Run"};
    JTextArea ta,ta1;
    JScrollPane sp,sp1;
    int x;
    FileDialog fd;
    FileInputStream fis;
    FileOutputStream fos;
    byte b[];
    Runtime r;
    String filename="";
    String fname="";
    String result="";
    boolean textchange;
    JColorChooser cc;
    public Editer()
    {
        jf=new JFrame();
        jf.setLayout(null);
        mb=new JMenuBar();
        jf.setJMenuBar(mb);

        mFile=new JMenu("File");
        mFile.setMnemonic(KeyEvent.VK_F);
        mb.add(mFile);

        mEdit=new JMenu();
        mEdit.setMnemonic(KeyEvent.VK_E);
        mb.add(mEdit);

        mFormat=new JMenu("Format");
        mFormat.setMnemonic(KeyEvent.VK_O);
        mb.add(mFormat);
        mRun=new JMenu("Run");
        mRun.setMnemonic(KeyEvent.VK_R);
        mb.add(mRun);
        mHelp=new JMenu("Help");
        mHelp.setMnemonic(KeyEvent.VK_H);
        mb.add(mHelp);
        JMenuItem mHelpJMenuItem=new JMenuItem("About Editer");
        mHelp.add(mHelpJMenuItem);
        mHelpJMenuItem.addActionListener(this);
        for(x=0;x<6;x++)
        {
            mFilMenuItem[x]=new JMenuItem(StrFileMenuItem[x]);
            if(x==4||x==5)
            {
                mFile.addSeparator();
                mFile.add(mFilMenuItem[x]);
                mFilMenuItem[x].addActionListener(this);
            }
            for(x=0;x<6;x++)
            {
                mEdit.addSeparator();
                mEdit.add(mEditMenuItem[x]);
                mEditMenuItem[x].addActionListener(this);
            }
            for(x=0;x<2;x++)
            {
                mFormatJMenuItem[x]=new JMenuItem(strRunMenuItem[x]);
                mFormat.add(mRunMenuItem[x]);
                mRunMenuItem[x].addActionListener(this);
            }
            ta=new JTextArea(50,50);
            ta1=new JTextArea(50,50);
            ta.setFont(new Font("courier new",Font.PLAIN,20));
            ta1.setFont(new Font("courier new",Font.PLAIN,20));

            sp=new JScrollPane(ta);
            sp1=new JScrollPane(ta1);
            setBounds(0,0,1350,520);
            sp1.setBounds(0,530,1250,135);
            jf.add(sp);
            jf.add(sp1);
            ta.addKeyListener(this);
            r=Runtime.getRuntime();
            textchange=false;
            mFilMenuItem[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
            mFilMenuItem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
            mFilMenuItem[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
            mFilMenuItem[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
            mFilMenuItem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
            mFilMenuItem[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
            jf.setTitle("Editer:Untitled");
            jf.setLocation(0,0);
            jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
            jf.setSize(1500,740);
            jf.setVisible(true);
        }
        public void keyPressed(KeyEvent te)
        {
            if(te.getSource()==ta)
            {
                textchange=true;
            }
        }
        public void keyReleased(KeyEvent te){}
        public void keyTyped(KeyEvent te){}
        public void actionPerformed(ActionEvent ae)
        {
            if(ae.getActionCommand().equals("New"))
            {
                int ask;
                if(textchange==true)
                {
                    ask=JOptionPane.showConfirmDialog(this, "Text has been changed.\nDo you want to save it",
                    "Changes Save",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(ask==0)
                    {
                        SaveButtonCoding();
                        ta.setText(null);
                        NewButtonCoding();
                    }
                    else if(ask==1)
                    {
                        ta.setText(null);
                        NewButtonCoding();
                    }

                }
                else
                {
                    ta.setText(null);
                    NewButtonCoding();
                }
            }
            try
            {
                if (ae.getActionCommand().equals("Open")) {
                    id=new FileDialog(this,"Open a File",FileDialog.LOAD);
                    fd.show();
                    String st3=sd.getFile();
                    if (st3==null) {
                        return;
                    }
                    else
                    fis=new FileInputStream(fd.getDirectory()+fd.getFile());
                    b=new byte[fis.available()];
                    fis.read(b);
                    ta.setText(new String(b));
                    jf.setTitle("Editer:"+fd.getFile());
                    filename=fd.getDirectory()+fd.getFile();
                    fname=fd.getFile();
                    textchange=false;
                }
                else if(ae.getActionCommand().equals("Save"))
                {
                    SaveButtonCoding();
                }
                else if(ae.getActionCommand().equals("Save As..."))
                {
                    int ask;
                    if(textchange==true)
                    {
                        ask.JOptionPane.showConfirmDialog(this."Text has been changed.\nDo you want to save it",
                        "Changed Save",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if (ask==0)
                         {
                            SaveButtonCoding();
                            SaveAs();
                        }
                        else if(ask==1)
                        SaveAs();
                    }
                    else
                    SaveAs();   
                }
                else if(ae.getActionCommand().equals("EXIT "))
                {
                    int ask;
                    if(textchange==true)
                    {
                        ask.JOptionPane.showConfirmDialog(this."Text has been changed.\nDo you want to save it",
                        "Changed Save",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if (ask==0)
                         {
                            SaveButtonCoding();
                            System.exit(0);
                        }
                        else if(ask==1)
                        System.exit(0);
                    }
                    else
                    System.exit(0);  
                }
                else if(ae.getActionCommand().equals("Cut"))
                ta.cut();
                else if(ae.getActionCommand().equals("Paste"))
                ta.paste();
                else if(ae.getActionCommand().equals("Copy"))
                ta.copy();
                else if(ae.getActionCommand().equals("Select All"))
                ta.selectAll();
                else if(ae.getActionCommand().equals("Time & Date"))
                ta.insert("",new Date(),ta.getSelectionStart());
                else if(ae.getActionCommand().equals("Delete"))
                ta.replaceSelection("");
                else if(ae.getActionCommand().equals("Color"))
                {
                    cc=new JColorChooser();
                    Color newColor=cc.showDialog(this, "Select Color",Color.BLACK);
                    ta.setForeground(newColor);
                    ta.setSelectedTextColor(newColor);
                    ta1.setForeground(newColor);
                    ta1.setSelectedTextColor(newColor);
                }
                else if(ae.getActionCommand().equals("Font"))
                {
                    // int retValue=fontChooser.showDialog(null);
                    // if(retValue==JFontChooser.OK_OPTION)
                    // Font selected=fontChooser.getSelectedFont();
                    // ta.setFont(fontChooser.getFont());
                }
                else if(ae.getActionCommand().equals("Compile"))
                {
                   if(!filename.equals(""))
                    {
                        try {
                            result="";
                            Process error=r.exec("C:\\Oracle\\Middleware\\jdk160_29\\bin\\java.exe"+filename);
                            BufferedReader err=new BufferedReader(new InputStreamReader(error.getErrorStream()));
                            while (true) {
                                String temp=err.readLine();
                                if(temp!=null)
                                {
                                    result+=temp;
                                    result+="\n";
                                }
                                else break;
                            }
                            if(result.equals(""))
                            {
                                ta1.setText("Compilation Successfully"+filename);
                                err.close();
                            }
                            else
                            ta1.setText(result);
                        } catch (Exception e1) {
                           ta1.setText(""+e1);
                        }
                    }
                    else
                    ta1.setText("Plaese provide java file name");
                }
                else if(ae.getActionCommand().equals("Run..."))
                {
                    int start=0;
                    if (!fname.equals("")) {
                        try {
                            int num=0;
                            num=fname.indexOf(".");
                            String fn=fname.substring(0,num);
                            Process p=r.exec("C:\\Oracle\\Middleware\\jdk160_29\\bin\\java.exe -cp . "+fn);
                            BufferedReader output=new BufferedReader(new InputStreamReader(p.getInputStream()));
                            BufferedReader error=new BufferedReader(new InputStreamReader(p.getErrorStream()));
                            while (true) {
                                String temp=output.readLine();
                                if(temp!=null)
                                {
                                    result+=temp;
                                    result+="\n";
                                }
                                else break;
                            }
                            if(result.equals("error"))
                            {
                                while (true) {
                                    String temp=error.readLine();
                                    if (temp!=null) {
                                        result+=temp;
                                        result+="\n";
                                    }
                                    else
                                    {
                                        break;
                                    }
                                }
                            }
                            output.close();
                            error.close();
                            ta1.setText(result);
                        } catch (Exception e2) {
                            System.out.println(e2);
                        }
                    }
                }
                else if(ae.getActionCommand().equals("About Editor"))
                {
                    String h1="This Editor has been created by Mr. Ambuj.\nProject was completed under the  guidence of Prof.Manish Bhatia";
                    JOptionPane.showMessageDialog(this, h1);
                }
                else if(ae.getActionCommand().equals("Font"))
                {

                }
               
            }
            catch(Exception e1)
            {
                JOptionPane.showMessageDialog(this,""+ e1);
            }

        }
        public void SaveButtonCoding()
        {
            try 
            {
                if(jf.getTitle().equals("Editer:Untitled"))
                {
                    fd.new FileDialog(this,"Save a File",FileDialog.SAVE);
                    fd.show();
                    String st=fd.getFile();
                    if(st==null)
                    {
                        return;
                    }
                    else
                    fos=new FileOutputStream(fd.getDirectory()+fd.getFile());

                }
                else
                {
                    fos=new FileOutputStream(filename);
                }
                b=new byte[ta.getText().length()];
                b=ta.getText().getBytes();
                fos.write(b);
                jf.setTitle("Editer:",fd.getFile());
                filename=fd.getDirectory()+fd.getFile();
                fname=fd.getFile();
                ta1.setText("File has been saved now");
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(this,""+ "No file to save");
            }
        }
        public void SaveAs()
        {
            try {
                fd=new FileDialog(this,"Save As a File",FileDialog.SAVE);
                b=new byte[ta.getText().length()];
                b=ta.getText().getBytes();
                fos.write(b);
                jf.setTitle("Editer:"+fd.getFile());
                filename=fd.getDirectory()+fd.getFile();
                fname=fd.getFile();
                ta1.setText("File has been Saved Now.");
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(this,""+"No file to Save.");
            }
        }
        public void NewButtonCoding()
        {
            String str=JOptionPane.showInputDialog(null,"Please Enter class name","Provide a class name",
            JOptionPane.PLAIN_MESSAGE);
            if(str==null)
            return;
            else
            ta.setText("import java.lang.*;\n\npublic class "+str+"\n"+"{"+"\n"+"\tpublic static void main(String[] s)"+"\n\t"+"{"+"   "+"\n\t"+"}"+"\n"+"}" );
            jf.setTitle("Editer:Untitled");
        }
    }
    public static void main (String[] args)
    {
        Editer a=new Editer();
    }
}