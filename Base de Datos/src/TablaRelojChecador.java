//Cambiar el Modelo de un Jtable con una coneccion de una Base de Datos

import java.sql.Connection;         //Biblioteca para realizar la coneccion
import java.sql.DriverManager;      //Biblioteca para traer conector
import java.sql.ResultSet;          //Biblioteca para respuesta de la BD
import java.sql.SQLException;       //Biblioteca de Excepcion de la BD
import java.sql.Statement;          //Biblioteca para Declarar la BD
import java.util.logging.Level;     //Biblioteca que nivela registros
import java.util.logging.Logger;    //Biblioteca para anotar cronologia
import javax.swing.table.DefaultTableModel;//Biblioteca para modelar tabla

public class TablaRelojChecador {
    
    //Nombre de las columnas de la tabla
    private String colum[]=
            {"ID_Trabajador", "Nombre", "Fecha", "Hora", "EntradaSalida"};
    
    private String filas[][];       //Filas de la tabla
    private Connection con;         //Coneccion de la BD
    private ResultSet res;          //Respuesta de la BD
    private Statement stm;          //Declaracion de la BD
    
    //Nombre de la url para la conecion de la BD
    private String url="jdbc:mysql://localhost:3306/relojchecador";
    
    private String user="root";         //Usuario de la BD
    private String pass="96Pablobros";  //Contraseña del usuario
    
    //Modelo de la Tabla a Mostrar
    DefaultTableModel dtm= new DefaultTableModel(filas,colum){
        
        //Haciendo la tabla no editable
        @Override
        public boolean isCellEditable (int row, int column){
            return false;
        }
    };
    
    //Nombre de los campos de la tabla
    private String idempl, fecha, hora, nombre, IO;
    
    //Clase constructora que trae dos String y la ExceptionSQL por algun error
    public TablaRelojChecador(String date, String name) throws SQLException{
        try {
            
            //Clase del conductor de la BD
            Class.forName("com.mysql.jdbc.Driver");
            
            //Creando coneccion de la BD
            con= DriverManager.getConnection(url,user,pass);
            
            //Creando la Declaracion de la BD
            stm= con.createStatement();
            
            //Creando una respuesta de la BD
            res= stm.executeQuery("SELECT * FROM empleado WHERE fecha='" +
                                   date + "' AND nombre='"+ name + "';");
            
            //Buscando los datos hasta llegar al ultimo dato a buscar
            while(res.next()){
                
                //Nombrando los campos de la tabla dependiendo del dato
                idempl = res.getString("idempleado");
                fecha  = res.getString("fecha");
                hora   = res.getString("hora");
                nombre = res.getString("nombre");
                IO     = res.getString("EntSal");
                
                //Creando y Agregando fila de datos a la tabla
                String datos []={idempl,nombre,fecha,hora,IO};
                dtm.addRow(datos);
            }
        }catch (ClassNotFoundException ex){//Atrapar algun Error de la Coneccion
           Logger.getLogger
           (TablaRelojChecador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Instituto Tecnologico de Leon                                           *
 * Ingenieria en Sistemas Computacionales                                  *
 * Topicos Avanzados de Programacion                                       *
 *                                                                         *
 * Integrantes de Equipo                                                   *
 * Ramos Zuñiga Amado                                                      *
 * Olvera Rivera Maria Jose                                                *
 * Cruz Meza Pablo Antonio                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */