import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DatosRelojChecador extends JFrame{
    
    private JLabel Carga= new JLabel("Cargando datos...");   
    private Connection con;         
    private PreparedStatement Ps;   
    private File Archivo;       
    private FileReader Fr;      
    private BufferedReader Br;      
    private String Url="jdbc:mysql://localhost:3306/relojchecador"; 
    private String User="root";       
    private String Pass="96Pablobros";  
    
    public DatosRelojChecador(String Ruta){
        super("Insertando Datos");
        setLayout(new FlowLayout());
        Carga.setFont(new Font("Times New Roman", Font.BOLD,32));
        add(Carga);
        setSize(400,100);
        setVisible(true);
        
        try {            
            //Lee el archivo especificado por la ruta y lo carga
            Archivo= new File(Ruta);       
            Fr= new FileReader(Archivo);
            Br= new BufferedReader(Fr);
            
            Class.forName("com.mysql.jdbc.Driver");
            
            /*Creando conexion de la BD y creamos la la tabla llamada Empleado
            que tambien se imprimera en la consola de java*/
            con= DriverManager.getConnection(Url,User,Pass);            
            Ps= con.prepareStatement("CREATE TABLE Empleado ("
                    + "idempleado int, fecha date, hora varchar(10),"
                    + "nombre varchar(30), EntSal varchar(2))");
            Ps.executeUpdate();
            
            System.out.println("CREATE TABLE Empleado ("
                    + "idempleado int, fecha date, hora varchar(10),"
                    + "nombre varchar(30), EntSal varchar(2))");
            
            String arg;//Nombre de la linea de consulta
            String datos[];
            /*lee los datos que hay en el archivo que se selecciono anteriormente
            e inserta los datos de este*/
            while((arg=Br.readLine())!=null){
                arg= arg.replaceAll("\t1\t1", "");
                arg= arg.replaceAll("I\t0", "I");
                arg= arg.replaceAll("O\t0", "O");
                arg= arg.replaceAll(" 0", "\t0");
                arg= arg.replaceAll(" 1", "\t1");
                arg= arg.replaceAll(" 2", "\t2");
                datos= arg.split("\t");
                Ps= con.prepareStatement(
                    "INSERT INTO Empleado(idempleado,fecha,hora,nombre,EntSal) "
                  + "VALUES (" + datos[0] + ", '"+datos[1] + "', '" + datos[2]
                  + "', '" + datos[3]+"' ,'" + datos[4] + "');");
                Ps.executeUpdate();
                System.out.println(
                  "INSERT INTO Empleado(idempleado,fecha,hora,nombre,EntSal) "
                  + "VALUES (" + datos[0] + ", '"+datos[1] + "', '" + datos[2]
                  + "', '" + datos[3]+"' ,'" + datos[4] + "');");
            }
        //Exepcion por si no existe el Archivo
        }catch(FileNotFoundException ex) {
            Logger.getLogger(DatosRelojChecador.class.getName()).
            log(Level.SEVERE, null, ex);
        }catch (IOException ex){
            Logger.getLogger(DatosRelojChecador.class.getName()).
            log(Level.SEVERE, null, ex);
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(DatosRelojChecador.class.getName()).
            log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(DatosRelojChecador.class.getName()).
            log(Level.SEVERE, null, ex);
        }
        
        setVisible(false);
        JOptionPane.showMessageDialog(null, "Datos Cargados");
        
        RelojChecador reloj= new RelojChecador();
    }
}


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Instituto Tecnologico de Leon                                           *
 * Ingenieria en Sistemas Computacionales                                  *
 * Topicos Avanzados de Programacion                                       *
 *                                                                         *
 * Integrantes de Equipo                                                   *
 * Ramos Zuñiga Amado                                                      *
 * Olvera Rivera Maria Josefina                                                *
 * Cruz Meza Pablo Antonio                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
