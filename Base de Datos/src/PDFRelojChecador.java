//Creando un PDF desde JAVA

//Importando Bibliotecas
import com.itextpdf.text.BaseColor; //Rellenar Celda de una tabla del Documento
import com.itextpdf.text.Document;  //Generar documento
import com.itextpdf.text.DocumentException;//Excepcion del Documento
import com.itextpdf.text.Element;//Elementos de ocupa un componete del Documento
import com.itextpdf.text.Font;  //Formato de Letra 
import com.itextpdf.text.FontFactory;//Declarar Formato de Letras del Documento
import com.itextpdf.text.Image;     //Imagen o Icono dentro de un Documento
import com.itextpdf.text.Paragraph; //Parrafos de un PDF
import com.itextpdf.text.Phrase;   //Frase para una celda de una tabla en el PDF
import com.itextpdf.text.pdf.PdfPCell;  //Celda de tabla en el PDF
import com.itextpdf.text.pdf.PdfPTable; //Tabla en un PDF
import com.itextpdf.text.pdf.PdfWriter; //Escribir en el PDF
import java.io.FileNotFoundException;   //Excepcion de Archivo a Crear
import java.io.FileOutputStream;        //Archivar el Archivo a Crear
import javax.swing.JOptionPane;         //Ventanas de JAVA a Mostrar
import javax.swing.JTable;              //Tablas a Crear

public class PDFRelojChecador {
    Document doc;//Componete de un Documento a Crear
    
    //Clase Construtora que trae una tabla y tres String con dos Exepciones
    public PDFRelojChecador(JTable tabla, String name, String day1, String day2)
        
        //Exepciones para evitar error al crear archivo y documento    
        throws FileNotFoundException, DocumentException{
        
        try{
            doc = new Document();//Creando Documento
            
            //Creando Escritura del Archivo de tipo PDF
            PdfWriter.getInstance(doc, new FileOutputStream("Checador.pdf"));
            
            doc.open();//Abriendo el documento
            
            //Creando una imagen para el PDF exportada de la clase
            Image foto = Image.getInstance(getClass().getResource("reloj.png"));
            foto.setAlignment(Element.ALIGN_CENTER);//Centrando imagen
            
            //Creando un parrafo con formato de letra para el PDF
            Paragraph titulo= new Paragraph("Reloj Checador\n",
            FontFactory.getFont("Arial Black",30,//Fuente y tamaño de letra
                    Font.BOLD,BaseColor.BLUE));      //Estilo y color de letra
            titulo.setAlignment(Element.ALIGN_CENTER);//Centrando parrafo
            
            //Creando una tabla para el PDF
            PdfPTable pdftabla = new PdfPTable(tabla.getColumnCount());
            PdfPCell columna;//Declarando celda para las columnas de tabla
            
            //Creando las celdas de las columnas
            for(int i=0; i < tabla.getColumnCount(); i++){
                
                //Creando celda para la tabla
                columna= new PdfPCell(new Phrase(tabla.getColumnName(i)));
                
                //Centrando texto de la Celda
                columna.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                //Rellenado celda de un color
                columna.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdftabla.addCell(columna);//Agregando celda a la tabla
            }
            
            pdftabla.setHeaderRows(1);//Encabezado de las filas
            
            //Creando Filas para la tabla
            for(int i=0; i<tabla.getRowCount();i++){
                for(int j=0; j < tabla.getColumnCount(); j++){
                    
                    //Agregando celda a la Tabla
                    pdftabla.addCell(tabla.getValueAt(i, j).toString());
                }
            }
            
            doc.add(foto);      //Agregando foto al PDF
            doc.add(titulo);    //Agregando parrafo al PDF
            doc.add(new Paragraph("Nombre: "+name));
            doc.add(new Paragraph("Fecha: "+day1+" "+day2));
            doc.add(new Paragraph(" "));//Agregando parrafo vacio al PDF
            doc.add(pdftabla);//Agregando tabla al PDF
            
            doc.close();//Cerrar Documento
            
            //Mensage de Creacion del PDF
            JOptionPane.showMessageDialog(null,"Se ha Creado un PDF");
        
        }catch(Exception e){//Excepcion con mensage por cualquier error ocurrido
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
 * Olvera Rivera Maria Josefina                                                *
 * Cruz Meza Pablo Antonio                                                 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */