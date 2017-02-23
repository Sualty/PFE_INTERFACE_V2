package tools.network;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by blou on 30/11/16.
 */
public class ConnectorPi implements NetworkInterface {

    private Session session;
    private String password_rpi;
    private String password_pc;

    @Override
    public void connexionPi(String password_rpi) throws JSchException {
        String user = "pi";
        this.password_rpi = password_rpi;
        String host = "invitee.local";
        int port=22;

        JSch jsch = new JSch();
        session = jsch.getSession(user, host, port);
        session.setPassword(password_rpi);
        session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing Connection...");

        session.connect();

        System.out.println("Connection established.");
    }

    @Override
    public void lancementAcquisition() throws JSchException, IOException {
        String commandeLancement = "cd ~/Epee/Valeurs/Lancement/Output;./Lancement";//TODO à modifier le cas échéant
        System.out.println("Preparing launching...");
        Channel channel=session.openChannel("exec");
        ((ChannelExec)channel).setCommand(commandeLancement);
        channel.setInputStream(null);
        ((ChannelExec)channel).setErrStream(System.err);

        InputStream in=channel.getInputStream();
        channel.connect();
        System.out.println("LAUNCHED!!");
        byte[] tmp=new byte[1024];
        while(true){
            while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
            }
            if(channel.isClosed()){
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception ee){}
        }

        channel.disconnect();
    }

    //TODO get path of project dynamicly
    //TODO make history of datas , multiple files instead of only one
    @Override
    public void recuperationFichiers(String password) throws SftpException, IOException, JSchException {
        String commandeRecuperation = "echo "+password+" | sudo -S sshpass -p \'"+password_rpi+"\' scp pi@invitee.local:~/Epee/Valeurs/Lancement/Output/centrale1.txt /home/user/Bureau/PFE_V2/PFE_INTERFACE_V2/res/data\n";
        String[] cmd = {"/bin/bash","-c",commandeRecuperation};
        Process process = Runtime.getRuntime().exec(cmd);
        System.out.println("PASSWORD "+password);
        printStream(process.getInputStream(), "OUTPUT");
        printStream(process.getErrorStream(), "ERROR");
    }

    public static void printStream(InputStream is, String type){
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println(type + ">" + line);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }


    @Override
    public void disconnect() {
        session.disconnect();
    }
}
