import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BuscarArchivo extends JFrame{
    private JFileChooser Ventana;
    private FileNameExtensionFilter Filtro;
    private JLabel Jlbexa;
    private JTextField Jtfexa;
    private JButton Buscar, Aceptar;
    
    public BuscarArchivo(){
        super("Bucar TXT de los DATOS de la BD's");
        setLayout(new FlowLayout());
        
        Jlbexa= new JLabel("Ruta del Archivo de Texto: ");
        Jtfexa= new JTextField(20);
        Buscar= new JButton("Buscar");
        Aceptar= new JButton("Aceptar");
        
        add(Jlbexa);
        add(Jtfexa);
        add(Buscar);
        add(Aceptar);
        
        setSize(300,150);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //El evento del Boton buscar, selecciona uno de lso archivos y lo abre
        Buscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Ventana= new JFileChooser();
                Filtro= new  FileNameExtensionFilter("Archivos TXT", "txt");
                Ventana.setFileFilter(Filtro);
                
                int i= Ventana.showOpenDialog(null);               
                if(i==JFileChooser.APPROVE_OPTION){
                    String ruta= Ventana.getSelectedFile().getPath();
                    Jtfexa.setText(ruta);
                }
            }
        });
        //al dar clien en el boton aceptar, abre la clase DatosRelojChecador
        Aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                DatosRelojChecador ch= new DatosRelojChecador(Jtfexa.getText());
            }
        });
    }
    
    public static void main(String [] args){
        BuscarArchivo b= new BuscarArchivo();
    }
}
