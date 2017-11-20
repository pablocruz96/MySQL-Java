import com.itextpdf.text.DocumentException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;
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
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class RelojChecador {
    JFrame frame;
    private JLabel NombreTrab, Fecha, uno, dos, tres;

    private JTextField NombreT, FechaT;
    private JPanel Primario, Primario2, Secundario2, Secundario, Terciario;
    private JButton Buscar, Crear_PDF, Salir;
    private JTable TablaT;
    private DefaultTableModel ModeloT;
    private JScrollPane ScrollT;
    private String[] TitulosT = {"ID_Trabajador", "Nombre", "Fecha", "Hora", "EntradaSalida"};
    private Object[][] DatosT;
    private Connection con = null;

    public RelojChecador(){
        Primario = new JPanel();
        Primario2 = new JPanel();
        Primario2.setLayout(new GridLayout(3, 3, 2, 2));
        NombreTrab = new JLabel("Nombre");
        uno = new JLabel();
        NombreT = new JTextField();
        Fecha = new JLabel("Fecha");
        dos = new JLabel();
        FechaT = new JTextField();
        tres = new JLabel();
        Buscar = new JButton("Buscar");
        Primario2.add(NombreTrab);
        Primario2.add(uno);
        Primario2.add(NombreT);
        Primario2.add(Fecha);
        Primario2.add(dos);
        Primario2.add(FechaT);
        Primario2.add(tres);
        Primario2.add(Buscar);
        Primario.add(Primario2);

        Secundario2 = new JPanel();
        Secundario = new JPanel();
        ModeloT = new DefaultTableModel(DatosT, TitulosT);
        TablaT = new JTable(ModeloT);
        ScrollT = new JScrollPane(TablaT);
        ScrollT.setPreferredSize(new Dimension(450,120));
        Secundario2.add(ScrollT);
        Secundario.add(Secundario2);

        Terciario = new JPanel();
        Crear_PDF = new JButton("Crear PDF");
        Salir = new JButton("Salir");
        Terciario.add(Crear_PDF);
        Terciario.add(Salir);

        frame = new JFrame("Checador ");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(Primario);
        frame.add(Secundario);
        frame.add(Terciario);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(480, 300);
        
        Crear_PDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creadorPDF();
            }
        });

        Buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                busqueda();

            }
        });

        Salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eas) {
                System.exit(0);
            }
        }
        );

    }
    
    public void busqueda() {
        if("".equals(NombreT.getText()) || "".equals(FechaT.getText())){
            JOptionPane.showMessageDialog(null, "Hay un Campo Vacio");
        }else{
            try {
                TablaRelojChecador rj=
                    new TablaRelojChecador(FechaT.getText(), NombreT.getText());
                TablaT.setModel(rj.dtm);
            } catch (SQLException ex) {
                Logger.getLogger(RelojChecador.class.getName()).
                log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void creadorPDF(){
      if(TablaT.getRowCount()>0){
            try {
                PDFRelojChecador checar= new PDFRelojChecador
                    (TablaT,NombreT.getText(),FechaT.getText());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RelojChecador.class.getName()).
                        log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(RelojChecador.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Filas Vacias");
        }
    }
    
    public static void main(String[] args) {
        RelojChecador reloj= new RelojChecador();
    }
}
