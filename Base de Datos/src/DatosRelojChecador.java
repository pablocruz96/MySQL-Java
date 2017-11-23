//Importar todos los datos para la base de datos de un archivo de texto

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

public class DatosRelojChecador extends JFrame{//Clase con una Venta
    
    //Etiqueta ya declarada
    private JLabel carga= new JLabel("Cargando datos...");
    
    private Connection con;         //Conexion de la BD
    private PreparedStatement ps;   //Consulta directamente Preparada
    
    private File archivo;       //Componente para Extraer Archivo
    private FileReader fr;      //Componente para Leer el Archivo
    private BufferedReader br;  //Componente para 
    
    //Nombre de la url para la conecion de la BD
    private String url="jdbc:mysql://localhost:3306/relojchecador";
    
    private String user="root";         //Usuario de la BD
    private String pass="96Pablobros";  //Contraseña del usuario
    
    public DatosRelojChecador(){
        super("Insertando Datos");//Titulo de la ventana
        setLayout(new FlowLayout());//Borde de Ventana de tipo FlowLayout
        
        //Declaran formato a la Etiqueta
        carga.setFont(new Font("Times New Roman", Font.BOLD,32));
        add(carga);//Agregando etiqueta a la ventana 
        setSize(400,100);//Tamaño de la Ventana
        setVisible(true);//Ventana Visible
        
        try {
            
            //Cargando el archivo
            archivo= new File("C:\\TXT\\relojchecador_empleado.txt");
            
            
            fr= new FileReader(archivo);//Leyendo el archivo
            br= new BufferedReader(fr);//Leyendo la escritura del archivo
            
            //Clase del conductor de la BD
            Class.forName("com.mysql.jdbc.Driver");
            
            //Creando coneccion de la BD
            con= DriverManager.getConnection(url,user,pass);
            
            String arg;//Nombre de la linea de consulta
            
            //Buscando Consultas
            while((arg=br.readLine())!=null){
                System.out.println(arg);
                //Creando la Preparacion de la consulta de Insercion
                ps= con.prepareStatement(arg);
                ps.executeUpdate();//Ejecuta consulta
            }
            
        }catch(FileNotFoundException ex) {//Exepcion por si no existe el Archivo
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
        
        setVisible(false);//Ventana no visible
        
        //Mensage de datos ya cargados
        JOptionPane.showMessageDialog(null, "Datos Cargados");
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
