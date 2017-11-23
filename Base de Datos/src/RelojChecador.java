//Programa para Cosultar en una base de datos e imprimir en PDF los Resultados

//importación de lso paquetes necesarios para la interfaz 
import java.awt.Dimension;
import java.awt.GridLayout;

//importar los  paquetes de eventos para los botones
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.itextpdf.text.DocumentException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

//importar bibliotecas para hacer la conexión con la base de datos
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//importar las bibliotecas para  diseño la interfaz
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

//importar biblioteca para la tabla donde apareceran los datos a imprimir
import javax.swing.table.DefaultTableModel;

public class RelojChecador {//Nombree de la clase
    
    //declaración de los elementos que usaremos para la creación del programa
    JFrame frame;
    private JLabel NombreTrab, Fecha, Fechados, uno, tres;
    private JTextField NombreT, FechaT,FechaT2;
    private JPanel Primario, Primario2, Secundario2, Secundario, Terciario;
    private JButton Buscar, Crear_PDF, Salir;
    private JTable TablaT;
    private DefaultTableModel ModeloT;
    private JScrollPane ScrollT;
    private String[] TitulosT = {"ID_Trabajador", "Nombre", "Fecha", "Hora", "EntradaSalida"};
    private Object[][] DatosT;
    private Connection con = null;

    public RelojChecador(){//Constructor de la clase RelojChecador
        
        DatosRelojChecador d= new DatosRelojChecador();
        
        //declaración de los elementos a usar para el programa
        Primario = new JPanel();
        Primario2 = new JPanel();
        Primario2.setLayout(new GridLayout(3, 3, 2, 2));
        NombreTrab = new JLabel("Nombre");
        uno = new JLabel();
        NombreT = new JTextField();
        Fecha = new JLabel("Fecha Final");
        Fechados = new JLabel("Fecha Inicio");
        FechaT = new JTextField();
        FechaT2 = new JTextField();
        tres = new JLabel();
        Buscar = new JButton("Buscar");
        
        //se agregan los alementos ya antes declarados al panel primario2
        Primario2.add(NombreTrab);
        Primario2.add(NombreT);
        Primario2.add(tres);
        Primario2.add(Fechados);
        Primario2.add(FechaT);
        Primario2.add(uno);
        Primario2.add(Fecha);
        Primario2.add(FechaT2);
        Primario2.add(Buscar);
        Primario.add(Primario2);
        
        //pone asigna nombre a 2 panel
        Secundario2 = new JPanel();
        Secundario = new JPanel();
        
        //se pone el nobre al Modelo de la tabla donde apareceran los datos
        ModeloT = new DefaultTableModel(DatosT, TitulosT);
        TablaT = new JTable(ModeloT);//Se nombra la talba
        ScrollT = new JScrollPane(TablaT);
        ScrollT.setPreferredSize(new Dimension(450,120));
        Secundario2.add(ScrollT);//se agrega el Scroll al panel Secundario2
        Secundario.add(Secundario2);//El panel secundario2 se agrega al panel Secundario
        
        //se crea un nueve panel donde se pondrán los botones
        Terciario = new JPanel();
        Crear_PDF = new JButton("Crear PDF");//boton pdf
        Salir = new JButton("Salir");//botón salir
        //agregamos los botones al panel Terciario
        Terciario.add(Crear_PDF);
        Terciario.add(Salir);

        frame = new JFrame("Checador ");// se crea la instancia y se nombra el JFrame
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        //se agregan los panel al fame
        frame.add(Primario);
        frame.add(Secundario);
        frame.add(Terciario);
        frame.pack();
        frame.setVisible(true);//se hace visible
        
        //indicamos que cierre cuando se termine la ejecución
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                Connection con;         //Coneccion de la BD
                PreparedStatement ps;   //Consulta directamente Preparada
        
                String url="jdbc:mysql://localhost:3306/relojchecador";
    
                String user="root";         //Usuario de la BD
                String pass="96Pablobros";  //Contraseña del usuario
                try {
                    //Clase del conductor de la BD
                    Class.forName("com.mysql.jdbc.Driver");
                    //Creando coneccion de la BD
                    con= DriverManager.getConnection(url,user,pass);
                    
                    //Creando la Preparacion de la consulta
                    ps= con.prepareStatement("DROP TABLE empleado;");
                    ps.executeUpdate();
                    
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(RelojChecador.class.getName()).
                    log(Level.SEVERE, null, ex);
                }catch (SQLException ex) {
                    Logger.getLogger(RelojChecador.class.getName()).
                    log(Level.SEVERE, null, ex);
                }
                System.out.println("DROP TABLE empleado;");
                System.exit(0);//Sale del programa
            }
            
        });
        frame.setSize(480, 300);//se da dimención a la ventana
        
        
   //se implementa el ActionListener para crear el evento de la creación del pdf
        Crear_PDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creadorPDF();//llama al metodo CradorPDF
            }
        });
        
        //se implementa el AcionListner para el Botón Buscar
        Buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                busqueda();//llama al metodo Busqueda

            }
        });
        
        //implementa el ActionListener para Botón Salir
        Salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eas) {
                Connection con;         //Coneccion de la BD
                PreparedStatement ps;   //Consulta directamente Preparada
        
                String url="jdbc:mysql://localhost:3306/relojchecador";
    
                String user="root";         //Usuario de la BD
                String pass="96Pablobros";  //Contraseña del usuario
                try {
                    //Clase del conductor de la BD
                    Class.forName("com.mysql.jdbc.Driver");
                    //Creando coneccion de la BD
                    con= DriverManager.getConnection(url,user,pass);
                    
                    //Creando la Preparacion de la consulta
                    ps= con.prepareStatement("DROP TABLE empleado;");
                    ps.executeUpdate();
                    
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(RelojChecador.class.getName()).
                    log(Level.SEVERE, null, ex);
                }catch (SQLException ex) {
                    Logger.getLogger(RelojChecador.class.getName()).
                    log(Level.SEVERE, null, ex);
                }
                
                System.out.println("DROP TABLE empleado;");
                System.exit(0);//Sale del programa
            }
        }
        );

    }
    
    public void busqueda() {
        //usamos un if para condicionar los campos, donde uno o ambos esten vacios
        //manda mensaje
        if("".equals(NombreT.getText()) || "".equals(FechaT.getText()) || 
           "".equals(FechaT2.getText())){
            JOptionPane.showMessageDialog(null, "Hay un Campo Vacio");
        }else{ //si los campos etan llenos llena la tabla
            TablaRelojChecador rj = new TablaRelojChecador
            (FechaT.getText(),FechaT2.getText(),NombreT.getText()); //si los campos no errones llama la excepcion
            TablaT.setModel(rj.dtm);
        }
    }
    
    public void creadorPDF(){//metodo para crear el PDF
        
        //ponemos una condición que comience a contabilizar las filas
        if(TablaT.getRowCount()>0){
            try {//una vez contabilizadas las filas se crea el pdf
                PDFRelojChecador checar= new PDFRelojChecador
                  (TablaT,NombreT.getText(),FechaT.getText(),FechaT2.getText());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RelojChecador.class.getName()).
                        log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(RelojChecador.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }else{//si se oprime el botón cuando la tabla esta vacia mandara mensaje
            JOptionPane.showMessageDialog(null,"Filas Vacias");
        }
    }
    
    //Intanciamos la clase en el método principal para la ejecución del programa
    public static void main(String[] args) {
        RelojChecador reloj = new RelojChecador();
        
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