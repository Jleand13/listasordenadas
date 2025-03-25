package ordenarlist;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Ruta de los archivos
        String archivoProductos = "C:\\Users\\LENOVO\\Desktop\\productos.txt";
        String archivoVendedores = "C:\\Users\\LENOVO\\Desktop\\vendedores.txt";

        try {
            // Leer los productos
            Map<Integer, String> productos = leerProductos(archivoProductos);
            
            // Leer los vendedores y las ventas
            Map<String, Map<Integer, Integer>> ventas = leerVendedores(archivoVendedores);

            // Crear archivo CSV para los vendedores
            generarArchivoVendedores(ventas);
            // Crear archivo CSV para los productos
            generarArchivoProductos(productos);
            
            System.out.println("Archivos generados con éxito.");
        } catch (IOException e) {
            System.out.println("Error al leer los archivos: " + e.getMessage());
        }
    }

    // Leer los productos desde el archivo
    public static Map<Integer, String> leerProductos(String archivo) throws IOException {
        Map<Integer, String> productos = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // Saltamos el encabezado
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    int idProducto = Integer.parseInt(partes[0].trim());
                    String nombreProducto = partes[1].trim();
                    productos.put(idProducto, nombreProducto);
                }
            }
        }
        return productos;
    }

    // Leer los vendedores y sus ventas desde el archivo
    public static Map<String, Map<Integer, Integer>> leerVendedores(String archivo) throws IOException {
        Map<String, Map<Integer, Integer>> vendedores = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // Saltamos la primera línea (encabezado)
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 17) { // 2 campos del vendedor + 15 productos
                    String vendedorId = partes[0] + ";" + partes[1];
                    Map<Integer, Integer> ventas = new HashMap<>();
                    for (int i = 2; i <= 16; i++) {
                        int cantidad = Integer.parseInt(partes[i].trim());
                        ventas.put(i - 1, cantidad); // Producto 1 a Producto 15
                    }
                    vendedores.put(vendedorId, ventas);
                }
            }
        }
        return vendedores;
    }

    // Generar el archivo CSV con los vendedores ordenados por ventas
    public static void generarArchivoVendedores(Map<String, Map<Integer, Integer>> vendedores) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reporte_vendedores.csv"))) {
            for (Map.Entry<String, Map<Integer, Integer>> entry : vendedores.entrySet()) {
                String vendedorId = entry.getKey();
                Map<Integer, Integer> ventas = entry.getValue();
                writer.write(vendedorId);
                for (int i = 1; i <= 15; i++) {
                    writer.write(";" + ventas.getOrDefault(i, 0));
                }
                writer.newLine();
            }
        }
    }

    // Generar el archivo CSV con los productos ordenados por nombre
    public static void generarArchivoProductos(Map<Integer, String> productos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reporte_productos.csv"))) {
            for (Map.Entry<Integer, String> entry : productos.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue());
                writer.newLine();
            }
        }
    }
}
