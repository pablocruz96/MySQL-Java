import java.sql.Connection;         
import java.sql.DriverManager;      
import java.sql.ResultSet;          
import java.sql.SQLException;       
import java.sql.Statement;          
import java.util.logging.Level;    
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class TablaRelojChecador {
    
    private String Colum[]=
            {"ID_Trabajador", "Nombre", "Fecha", "Hora", "EntradaSalida"};
    private String Filas[][];       
    private Connection con;         
    private ResultSet Res;        
    private Statement Stm;          
    private String Url="jdbc:mysql://localhost:3306/relojchecador";   
    private String User="root";         
    private String Pass="96Pablobros";   
    DefaultTableModel dtm= new DefaultTableModel(Filas,Colum){
        
        @Override
        public boolean isCellEditable (int row, int column){
            return false;
        }
    };
    
    private String idempl, fecha, hora, nombre, IO;
    
    //Clase constructora que trae tres String y la ExceptionSQL por algun error
    public TablaRelojChecador(String date1,String date2, String name){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(Url,User,Pass);
            Stm= con.createStatement();
            
            /*se crea la consulta en la base de datos donde se selecciona el
            nombre y las fechas entre las cuales queremos obtener información*/
            Res= Stm.executeQuery("SELECT * FROM empleado WHERE "
                    + "nombre = '"+name+"' AND "
                    + "fecha BETWEEN '"+date1+"' AND '"+date2+"';");
            System.out.println(("SELECT * FROM empleado WHERE "
                    + "nombre = '"+name+"' AND "
                    + "fecha BETWEEN '"+date1+"' AND '"+date2+"';"));
            
            /*Buscando los datos hasta llegar al ultimo dato a buscar y obtiene
            el dato que se esta buscandop*/
            while(Res.next()){
 
                idempl = Res.getString("idempleado");
                fecha  = Res.getString("fecha");
                hora   = Res.getString("hora");
                nombre = Res.getString("nombre");
                IO     = Res.getString("EntSal");
                
                //Creando y Agregando fila de datos a la tabla
                String datos []={idempl,nombre,fecha,hora,IO};
                dtm.addRow(datos);
            }
        }catch (ClassNotFoundException ex){//Atrapar algun Error de la Coneccion
           Logger.getLogger
           (TablaRelojChecador.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex){
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
 * Olvera Rivera Maria Josefina                                              *
 * Cruz Meza Pablo Antonio                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */