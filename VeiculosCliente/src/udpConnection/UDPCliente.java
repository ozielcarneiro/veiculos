/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpConnection;

import entity.Veiculo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ozielcarneiro
 */
public class UDPCliente {
    
    public static boolean sendObject(Veiculo veic){
        try{
            DatagramSocket socket = new DatagramSocket();
            InetAddress ipAdd = InetAddress.getByName("localhost");
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(outStream);
            objStream.writeObject(veic);
            byte[] data = outStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(data, data.length, ipAdd, 1234);
            socket.send(packet);
            return true;
        } catch (SocketException ex) {
            Logger.getLogger(UDPCliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (UnknownHostException ex) {
            Logger.getLogger(UDPCliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(UDPCliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex){
            Logger.getLogger(UDPCliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
