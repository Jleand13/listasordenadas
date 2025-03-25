package ordenarlist;

import java.io.*;
import java.util.*;

public class GenerateInfoFiles {

    public static void main(String[] args) {
        // Rutas de los archivos en el escritorio de Windows
        String rutaProductos = "C:\\Users\\LENOVO\\Desktop\\productos.txt"; // Ruta del archivo productos.txt
        String rutaVendedores = "C:\\Users\\LENOVO\\Desktop\\vendedores.txt"; // Ruta del archivo vendedores.txt

        // Leer los archivos de productos y vendedores
        List<String> productos = leerArchivo(rutaProductos);
        List<String> vendedores = leerArchivo(rutaVendedores);

        // Imprimir los datos leídos para verificar
        System.out.println("Productos leídos:");
        for (String producto : productos) {
            System.out.println(producto);
        }

        System.out.println("\nVendedores leídos:");
        for (String vendedor : vendedores) {
            System.out.println(vendedor);
        }
    }

    // Método para leer un archivo y devolver una lista de líneas
    public static List<String> leerArchivo(String ruta) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + ruta);
            e.printStackTrace();
        }
        return lineas;
    }
}
