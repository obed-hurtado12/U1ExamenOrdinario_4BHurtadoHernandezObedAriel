package mx.edu.utez.client;

import mx.edu.utez.server.Handler;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class JavaClient {

    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        System.out.println(read(client));
        System.out.println();
    }

    public static String read(XmlRpcClient client) throws XmlRpcException{
        Scanner sc = new Scanner(System.in);
        String res = "";
        int opc = 0 ;

        System.out.println("Iniciando mi sistema");
        while(opc!=5) {
            System.out.println("Menú\n1.- Registrar Usuario\n2.- Ver todos\n3.- Actualizar\n4.- Eliminar\n5.- Salir");
            opc = sc.nextInt();
            switch (opc) {
                case 1:
                    // Registrar
                    System.out.println("Ingresa tu nombre");
                    String name = sc.next();
                    System.out.println("Ingresa tu apellido");
                    String lastname = sc.next();
                    System.out.println("Ingresa tu correo");
                    String email = sc.next();
                    System.out.println("Ingresa tu password");
                    String password = sc.next();

                    Object[] params1 = {name, lastname, email, password};
                    boolean result = (Boolean) client.execute("Handler.createUser", params1);
                    res = result ? "Se ha registrado correctamente" : "No se ha registrado correctamente";
                    return res;
                case 2:
                    // Ver todos
                    Handler Handler = new Handler();
                    for (Person person : Handler.consultaRegistros()) {
                        System.out.println("-------------------------------------");
                        System.out.println("Id: " + person.getId());
                        System.out.println("Nombre: " + person.getName());
                        System.out.println("Apellido: " + person.getLastname());
                        System.out.println("E-mail: " + person.getEmail());
                        System.out.println("Fecha de Registro: " + person.getDate_registered());
                        System.out.println("Status: " + person.getStatus());
                        System.out.println("-------------------------------------");
                    }
                    res = "Se ha listado correctamente";
                    break;
                case 3:
                    // Actualizar
                    System.out.println("Ingresa el ID");
                    int id = sc.nextInt();
                    System.out.println("Ingresa tu nombre");
                    String name2 = sc.next();
                    System.out.println("Ingresa tu apellido");
                    String lastname2 = sc.next();
                    System.out.println("Ingresa tu correo");
                    String email2 = sc.next();
                    System.out.println("Ingresa tu password");
                    String password2 = sc.next();

                    Object[] params2 = {id, name2, lastname2, email2, password2};
                    boolean result2 = (Boolean) client.execute("Handler.updateUser", params2);
                    res = result2 ? "Se ha actualizado correctamente" : "No se ha actualizado correctamente";
                    return res;
                case 4:
                    // Eliminar
                    System.out.println("Ingresa el ID");
                    int id2 = sc.nextInt();

                    Object[] params3 = {id2};
                    boolean result3 = (Boolean) client.execute("Handler.deleteUser", params3);
                    res = result3 ? "Se ha eliminado correctamente" : "No se ha eliminado correctamente";
                    return res;

                case 5:
                    System.out.println("Saliendo");
                default:
                    System.out.println("Opcion no válida.....");
                    break;
            }

        }

        return res;
    }
    

    }
