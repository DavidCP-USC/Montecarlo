package pi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class InterfazImpl extends UnicastRemoteObject implements Interfaz {
    public InterfazImpl() throws RemoteException {
        // Constructor por defecto
    }

    public ArrayList<Float> crearPares(Integer num) throws RemoteException {
        // Los pares validos se almaenan en un arraylist con
        // el formato [x1, y1, x2, y2, ...]
        ArrayList<Float> lista = new ArrayList<Float>();
        float x, y;
        for (int i = 0; i < num; i++) {
            x = (float) (Math.random());
            y = (float) (Math.random());
            if (x*x + y*y <= 1){
                //System.out.println("Punto valido: (" + x + ", " + y + ")");
                lista.add(x);
                lista.add(y);
            }
        }
        return lista;
    }
}