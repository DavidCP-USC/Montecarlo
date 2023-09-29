package pi;
import java.net.*;
import java.io.*;

public class Servidor extends Thread{
    private String nombre;
    
    public Servidor(String nombre) {
        super();
        this.nombre = nombre;
    }

    //private synchronized int sacarBolas(){  
    //}

    @Override
    public void run() {
        System.out.println("Hilo servidor " + this.nombre + " iniciado");
    }
}
