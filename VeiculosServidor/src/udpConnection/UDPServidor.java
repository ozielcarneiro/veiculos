/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpConnection;

import entity.Carro;
import entity.Veiculo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TransactionRequiredException;

/**
 *
 * @author ozielcarneiro
 */
public class UDPServidor {
    
    public static void listen(){
        try{
            DatagramSocket socket = new DatagramSocket(1234);
            byte[] data = new byte[1024];
            System.out.println("Servidor em espera de dados...");
            while(true){
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);
                byte[] packetData = packet.getData();
                ByteArrayInputStream inStream = new ByteArrayInputStream(packetData);
                ObjectInputStream objStream = new ObjectInputStream(inStream);
                try {
                    Veiculo veic = (Veiculo) objStream.readObject();
                    System.out.println("Dados recebidos");
                    Carro c = new Carro();
                    c.setModelo(veic.getModelo());
                    c.setMarca(veic.getMarca());
                    c.setAno(veic.getAno());
                    c.setPlaca(veic.getPlaca());
                    c.setNomeProprietario(veic.getNomeProprietario());
                    c.setEndereco(veic.getEndereco());
                    c.setDataNascimento(veic.getDataNascimento());
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SisgraphServidorPU");
                    EntityManager em = emf.createEntityManager();
                    em.getTransaction().begin();
                    em.persist(c);
                    em.getTransaction().commit();
                    System.out.println(veic);
                } catch (IOException | ClassNotFoundException | IllegalStateException 
                        | EntityExistsException | IllegalArgumentException | TransactionRequiredException ex ) {
                    Logger.getLogger(UDPServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(UDPServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        listen();
    }
    
}
