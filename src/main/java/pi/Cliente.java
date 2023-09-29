package pi;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Cliente extends Thread{
    private String nombre;
    static int numServidores = 1;

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    public static void main(String[] args) {
        // Crear varios hilos que operarán sobre el ArrayList compartido
        for (int i = 1; i <= numServidores; i++) {
            Thread servidor = new Servidor("Servidor " + i);
            servidor.start();
        }
        
        Thread cliente = new Cliente("Hilo cliente ");
        cliente.start();
        // El cliente ha de generar tantos hilos como servidores va a haber
        // Cuando todos los hilos terminen, el hilo principal calculará el resultado
        


    }

    private synchronized void crearCarton(){
       
    }

    @Override
    public void run(){
        System.out.println("Hilo cliente iniciado");
    }
}





