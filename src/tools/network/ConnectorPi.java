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

    @Override
    public void connexionPi() throws JSchException {
        String user = "pi";
        String password = "pusheen";
        String host = "invitee.local";
        int port=22;

        JSch jsch = new JSch();
        session = jsch.getSession(user, host, port);
        session.setPassword(password);
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

    @Override
    public void recuperationFichiers() throws SftpException, IOException, JSchException {
        String commandeRecuperation1 = "echo ZUdug@H! | sudo -S sshpass -p 'pusheen' scp pi@invitee.local:~/Epee/Valeurs/Lancement/Output/centrale1.txt /home/user/Bureau/Sauvegarde_PFE/Valeurs/centrale1.txt\n";
        String commandeRecuperation2 = "echo ZUdug@H! | sudo -S sshpass -p 'pusheen' scp pi@invitee.local:~/Epee/Valeurs/Lancement/Output/centrale2.txt /home/user/Bureau/Sauvegarde_PFE/Valeurs/centrale2.txt\n";
        String[] cmd1 = {"/bin/bash","-c",commandeRecuperation1};
        String[] cmd2 = {"/bin/bash","-c",commandeRecuperation2};
        Process process1 = Runtime.getRuntime().exec(cmd1);
        Process process2 = Runtime.getRuntime().exec(cmd2);

        printStream(process1.getInputStream(), "OUTPUT");
        printStream(process2.getErrorStream(), "ERROR");
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

    public static void main(String args[])
    {
        ConnectorPi connectorPi = new ConnectorPi();
        try {
            connectorPi.connexionPi();

            connectorPi.lancementAcquisition();

            connectorPi.recuperationFichiers();

            connectorPi.disconnect();

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
