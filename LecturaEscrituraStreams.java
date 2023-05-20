import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LecturaEscrituraStreams {
    private final static String[] campos = { "Título: ", "Año: ", "Director: ", "Duración: ", "Sinopsis: ", "Reparto: ",
            "Sesión: " };

    private final static String lineSeparator = System.getProperty("line.separator");

    public String pedirRuta(String tipo) throws RutaInvalida {
        Scanner scan = new Scanner(System.in);
        System.out.println("(Ejemplo archivo.txt)");
        System.out.println("Introduce la ruta del archivo de " + tipo + ":");

        String ruta = scan.nextLine();
        if ("".equals(ruta)) { // si la ruta introducida está vacía
            throw new RutaInvalida("Ruta de " + tipo + " sin informar"); // lanza la excepción de Fichero de Salida
        }

        return ruta;
    }

    public void leerEscribirByteByte() throws RutaInvalida {
        try (FileInputStream lectura = new FileInputStream(pedirRuta("entrada"));
             FileOutputStream salida = new FileOutputStream(pedirRuta("salida"))) {
    
            int c = 0;
            int i = 0;
    
            salida.write(campos[i].getBytes());
    
            while (c != -1) {
                c = lectura.read();
                if (c != -1 && (char) c != '#' && (char) c != '{') {
                    if ((char) c == '\n') {
                        salida.write(' ');
                    } else {
                        salida.write(c);
                    }
                } else if ((char) c == '#') {
                    if (i == 3) {
                        salida.write(" minutos".getBytes());
                    }
    
                    i = (i + 1) % 7;
    
                    salida.write(lineSeparator.getBytes());
                    salida.write(campos[i].getBytes());
                } else if ((char) c == '{') {
                    i = (i + 1) % 7;
                    salida.write((lineSeparator + lineSeparator).getBytes());
                }
            }
        } catch (IOException e) {
            System.out.println("Error en la lectura o escritura: " + e.getMessage());
        }
    }
    

    public void leerEscribirCarCar() throws RutaInvalida {
        try (FileReader lectura = new FileReader(pedirRuta("entrada"));
             FileWriter salida = new FileWriter(pedirRuta("salida"))) {
    
            int c = 0;
            int i = 0;
    
            salida.write(campos[i]);
    
            while (c != -1) {
                c = lectura.read();
                if (c != -1 && c != '#' && c != '{') {
                    if (c == '\n') {
                        salida.write(' ');
                    } else {
                        salida.write(c);
                    }
                } else if (c == '#') {
                    if (i == 3) {
                        salida.write(" minutos");
                    }
    
                    i = (i + 1) % 7;
    
                    salida.write(lineSeparator);
                    salida.write(campos[i]);
                } else if (c == '{') {
                    i = (i + 1) % 7;
                    salida.write((lineSeparator + lineSeparator));
                }
            }
    
        } catch (IOException e) {
            System.out.println("Error en la lectura o escritura: " + e.getMessage());
        }
    }
    

    public void leerLineaEscribirObj() throws RutaInvalida {
        try {
            // Crear una lista de películas
            ArrayList<Pelicula> cartelera = new ArrayList<>();
    
            // Leer el archivo de entrada
            String rutaEntrada = pedirRuta("entrada");
            try (BufferedReader mibuffer = new BufferedReader(new FileReader(rutaEntrada))) {
    
                // Leer líneas del archivo y construir el texto completo
                StringBuilder textoCompleto = new StringBuilder();
                String linea;
                while ((linea = mibuffer.readLine()) != null) {
                    textoCompleto.append(linea);
                }
    
                // Dividir el texto en películas
                String[] division = textoCompleto.toString().split("\\{");
    
                // Crear objetos de tipo película y agregarlos a la cartelera
                for (String peliculaString : division) {
                    String[] atributos = peliculaString.split("#");
                    if (atributos.length == 7) {
                        Pelicula pelicula = new Pelicula();
                        pelicula.setTitulo(atributos[0]);
                        pelicula.setYear(Integer.parseInt(atributos[1]));
                        pelicula.setDirector(atributos[2]);
                        pelicula.setDuration(Integer.parseInt(atributos[3]));
                        pelicula.setDescription(atributos[4]);
                        pelicula.setReparto(atributos[5]);
                        pelicula.setHora(atributos[6]);
                        cartelera.add(pelicula);
                    }
                }
            }
    
            // Escribir en el archivo de salida
            String rutaSalida = pedirRuta("salida");
            try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(rutaSalida))) {
                for (Pelicula pelicula : cartelera) {
                    bufferWriter.write(pelicula.toString());
                    bufferWriter.newLine();
                }
            }
        } catch (RutaInvalida e) {
            throw new RutaInvalida();
        } catch (IOException e) {
            System.out.println("Ruta no encontrada.");
        }
    }
    

    public void leerObjEscribirCons() throws RutaInvalida {
        try {
            String rutaEntrada = pedirRuta("entrada");
            try (ObjectInputStream objetoEntrada = new ObjectInputStream(new FileInputStream(rutaEntrada))) {
                boolean endOfFile = false;
                Pelicula pelicula;
    
                while (!endOfFile) {
                    try {
                        pelicula = (Pelicula) objetoEntrada.readObject();
                        System.out.println(pelicula.toString());
                    } catch (EOFException e) {
                        endOfFile = true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RutaInvalida("Archivo no encontrado.");
        } catch (IOException e) {
            throw new RutaInvalida("Error de lectura/escritura en el archivo.");
        } catch (ClassNotFoundException e) {
            throw new RutaInvalida("Clase no encontrada.");
        }
    }
    

    public void leerObjEscribirObj() throws RutaInvalida {
        try {
            String rutaEntrada = pedirRuta("entrada");
            String rutaSalida = pedirRuta("salida");
    
            try (ObjectInputStream objetoEntrada = new ObjectInputStream(new FileInputStream(rutaEntrada));
                    ObjectOutputStream objetoSalida = new ObjectOutputStream(new FileOutputStream(rutaSalida))) {
                
                boolean endOfFile = false;
                Pelicula pelicula;
    
                while (!endOfFile) {
                    try {
                        pelicula = (Pelicula) objetoEntrada.readObject();
                        objetoSalida.writeObject(pelicula);
                    } catch (EOFException e) {
                        endOfFile = true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RutaInvalida("Archivo no encontrado.");
        } catch (IOException e) {
            throw new RutaInvalida("Error de lectura/escritura en el archivo.");
        } catch (ClassNotFoundException e) {
            throw new RutaInvalida("Clase no encontrada.");
        }
    }
    

    public void leerConsEscribirObj() throws RutaInvalida {
        try {
            String rutaSalida = pedirRuta("salida");
            try (ObjectOutputStream objetoSalida = new ObjectOutputStream(new FileOutputStream(rutaSalida))) {
                Pelicula pelicula = new Pelicula();
                pelicula.crearPelicula();
                objetoSalida.writeObject(pelicula);
            }
        } catch (IOException e) {
            throw new RutaInvalida("Error de escritura en el archivo.");
        }
    }

}
