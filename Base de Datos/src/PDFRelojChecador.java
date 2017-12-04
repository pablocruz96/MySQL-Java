import com.itextpdf.text.BaseColor; 
import com.itextpdf.text.Document;  
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;   
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;    
import com.itextpdf.text.Paragraph; 
import com.itextpdf.text.Phrase;   
import com.itextpdf.text.pdf.PdfPCell;  
import com.itextpdf.text.pdf.PdfPTable; 
import com.itextpdf.text.pdf.PdfWriter; 
import java.io.File;
import java.io.FileNotFoundException;   
import java.io.FileOutputStream;        
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;         
import javax.swing.JTable;             
import javax.swing.filechooser.FileNameExtensionFilter;

public class PDFRelojChecador {
    private Document Doc;
    private JFileChooser Ventana;
    private FileNameExtensionFilter Filtro;
    private File Archivo;
    private String Ruta;
    
    //Clase Construtora que trae una tabla y tres String con dos Exepciones
    public PDFRelojChecador(JTable tabla, String name, String day1, String day2)       
        //Exepciones para evitar error al crear archivo y documento, damos la
        //ubicación del fichero
        throws FileNotFoundException, DocumentException{
        Ventana= new JFileChooser();
        Filtro= new FileNameExtensionFilter("Archivos PDF","pdf");
        Ventana.setFileFilter(Filtro);
        
        if(Ventana.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            Archivo= Ventana.getSelectedFile();
            Ruta= Archivo.getPath();
            Ruta= Ruta + ".pdf";
        }
        
        Archivo= new File(Ruta);
        
        try{
            Doc = new Document();
            
            //Creando Escritura del Archivo de tipo PDF, se importa una imagen 
            //que encabezara el pdf, y estqara alineado al centro
            PdfWriter.getInstance(Doc, new FileOutputStream(Ruta));
            Doc.open();
            Image foto = Image.getInstance(getClass().getResource("reloj.png"));
            foto.setAlignment(Element.ALIGN_CENTER);
            
            /*Creando un parrafo con formato de letra para el PDF*/
            Paragraph titulo= new Paragraph("Reloj Checador\n",
            FontFactory.getFont("Arial Black",30, Font.BOLD,BaseColor.BLUE));      
            titulo.setAlignment(Element.ALIGN_CENTER);
            
            //Creando una tabla para el PDF y declaración de la columna
            PdfPTable pdftabla = new PdfPTable(tabla.getColumnCount());
            PdfPCell columna;
            
            /*Creando las celdas de las columnas, con el texto centrado y relle-
            nado de color */
            for(int i=0; i < tabla.getColumnCount(); i++){  
                columna= new PdfPCell(new Phrase(tabla.getColumnName(i)));
                columna.setHorizontalAlignment(Element.ALIGN_CENTER); 
                columna.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdftabla.addCell(columna);
            }
            
            pdftabla.setHeaderRows(1);//Encabezado de las filas
            
            /*Creando Filas para la tabla, y se agrega las celdas y demás elementos al documento pdf*/
            for(int i=0; i<tabla.getRowCount();i++){
                for(int j=0; j < tabla.getColumnCount(); j++){
                    pdftabla.addCell(tabla.getValueAt(i, j).toString());
                }
            }
            
            Doc.add(foto);      
            Doc.add(titulo);  
            Doc.add(new Paragraph("Nombre: "+name));
            Doc.add(new Paragraph("Fecha: "+day1+" "+day2));
            Doc.add(new Paragraph(" "));
            Doc.add(pdftabla);
            Doc.close();
            
            JOptionPane.showMessageDialog(null,"Se ha Creado un PDF");
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,
                    "Checar si el PDF esta Abierto u otro Error");
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
 * Olvera Rivera Maria Josefina                                            *
 * Cruz Meza Pablo Antonio                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */