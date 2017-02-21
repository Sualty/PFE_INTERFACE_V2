package tools.network;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;

/**
 * Created by blou on 30/11/16.
 */
public interface NetworkInterface {

    /**
     * Connexion à la Raspberry Pi
     * @return true si la connexion est réussie
     */
    void connexionPi() throws JSchException;

    /**
     * Lancement d'une acquisition
     * @return true si l'acquisition a été bien effectuée
     */
    void lancementAcquisition() throws JSchException, IOException;

    /**
     * Récupération du fichier
     * @return le fichier sou forme de string
     */
    void recuperationFichiers() throws SftpException, IOException, JSchException;

    void disconnect();

}
