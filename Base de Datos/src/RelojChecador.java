import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.itextpdf.text.DocumentException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class RelojChecador {

    JFrame Frame;
    private JLabel NombreTrab, Fecha, Fechados, Uno, Tres;
    private JTextField NombreT, FechaT, FechaT2;
    private JPanel Primario, Primario2, Secundario2, Secundario, Terciario;
    private JButton Buscar, CrearPDF, Salir;
    private JTable TablaT;
    private DefaultTableModel ModeloT;
    private JScrollPane ScrollT;
    private String[] TitulosT = {"ID_Trabajador", "Nombre", "Fecha", "Hora", 
        "EntradaSalida"};
    private Object[][] DatosT;
    private Connection con = null;

    public RelojChecador() {

        Primario = new JPanel();
        Primario2 = new JPanel();
        Primario2.setLayout(new GridLayout(3, 3, 2, 2));
        NombreTrab = new JLabel("Nombre");
        Uno = new JLabel();
        NombreT = new JTextField();
        Fecha = new JLabel("Fecha Final");
        Fechados = new JLabel("Fecha Inicio");
        FechaT = new JTextField();
        FechaT2 = new JTextField();
        Tres = new JLabel();
        Buscar = new JButton("Buscar");

        Primario2.add(NombreTrab);
        Primario2.add(NombreT);
        Primario2.add(Tres);
        Primario2.add(Fechados);
        Primario2.add(FechaT);
        Primario2.add(Uno);
        Primario2.add(Fecha);
        Primario2.add(FechaT2);
        Primario2.add(Buscar);
        Primario.add(Primario2);
        
        Secundario2 = new JPanel();
        Secundario = new JPanel();
        ModeloT = new DefaultTableModel(DatosT, TitulosT);
        TablaT = new JTable(ModeloT);
        ScrollT = new JScrollPane(TablaT);
        ScrollT.setPreferredSize(new Dimension(450, 120));
        Secundario2.add(ScrollT);
        Secundario.add(Secundario2);
        
        Terciario = new JPanel();
        CrearPDF = new JButton("Crear PDF");
        Salir = new JButton("Salir");
        Terciario.add(CrearPDF);
        Terciario.add(Salir);

        Frame = new JFrame("Checador ");
        Frame.setLayout(new BoxLayout(Frame.getContentPane(),BoxLayout.Y_AXIS));

        Frame.add(Primario);
        Frame.add(Secundario);
        Frame.add(Terciario);
        Frame.pack();
        Frame.setVisible(true);

        Frame.addWindowListener(new WindowAdapter() {
            @Override
            //declaramos un evento que crea la conexión con la bd en mysql
            public void windowClosing(WindowEvent e) {
                Connection con;         
                PreparedStatement ps;   
                String url = "jdbc:mysql://localhost:3306/relojchecador";
                String user = "root";         
                String pass = "96Pablobros";  
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(url, user, pass);

                //Creando la preparacion de la consulta que al terminar de
                //ejecutarla borra los datos para poder hacer una consulta nueva.
                    ps = con.prepareStatement("DROP TABLE Empleado;");
                    ps.executeUpdate();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RelojChecador.class.getName()).
                            log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(RelojChecador.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
                System.out.println("DROP TABLE Empleado;");
                System.exit(0);
            }

        });
        Frame.setSize(480, 300);//se da dimención a la ventana
        //se implementa el ActionListener para el evento de la creación del pdf
        CrearPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creadorPDF();//llama al metodo CradorPDF
            }
        });

        Buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                busqueda();//llama al metodo Busqueda

            }
        });

        //implementa el ActionListener para Botón Salir, se hace la conexión
        //con la base de datos para que borre la tabla que se creo anteriormente
        Salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eas) {
                Connection con;         
                PreparedStatement ps;   
                String url = "jdbc:mysql://localhost:3306/relojchecador";
                String user = "root";         
                String pass = "96Pablobros"; 
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(url, user, pass);
                    ps = con.prepareStatement("DROP TABLE Empleado;");
                    ps.executeUpdate();

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RelojChecador.class.getName()).
                            log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(RelojChecador.class.getName()).
                            log(Level.SEVERE, null, ex);
                }

                System.out.println("DROP TABLE Empleado;");
                System.exit(0);
            }
        }
        );

    }

    public void busqueda() {
        /*usamos una condición para que en caso de que los datos no esten debi-
        damente llenos emita un mensaje, y si estan llenos comience a buscar 
        en la tabla */
        if ("".equals(NombreT.getText()) || "".equals(FechaT.getText())
                || "".equals(FechaT2.getText())) {
            JOptionPane.showMessageDialog(null, "Hay un Campo Vacio");
        } else { 
            TablaRelojChecador rj = new TablaRelojChecador(FechaT.getText(),
                    FechaT2.getText(), NombreT.getText());
            TablaT.setModel(rj.dtm);
        }
    }

    public void creadorPDF() {
        /*Se crea una condicion en la cual empieza a contar las filas que hay 
        en la tabla como resultado de la busqueda, y las pasa al pdf, si no se ha
        realizado la busqueda, manda un mensaje*/
        if (TablaT.getRowCount() > 0) {
            try {
                PDFRelojChecador checar = new PDFRelojChecador(TablaT, 
                        NombreT.getText(), FechaT.getText(), FechaT2.getText());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RelojChecador.class.getName()).
                        log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(RelojChecador.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Filas Vacias");
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
 * Olvera Rivera Maria Josefina                                                *
 * Cruz Meza Pablo Antonio                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
