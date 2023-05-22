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
        try (FileInputStream entrada = new FileInputStream(pedirRuta("entrada"));
                FileOutputStream salida = new FileOutputStream(pedirRuta("salida"))) {

            int character = 0;
            int i = 0;

            //Escribimos Titulo
            salida.write(campos[i].getBytes());

            //Mientras no acabe el texto
            while ((character = entrada.read()) != -1) {
                //I ira de 0 a 7
                int aumentarI = (i + 1) % 7;

                if (character == '#') {
                    if (i == 3) {
                        salida.write(" minutos".getBytes());
                    }
                    i = aumentarI;
                    salida.write(lineSeparator.getBytes());
                    salida.write(campos[i].getBytes());
                } else if (character == '{') {
                    i = aumentarI;
                    salida.write((lineSeparator + lineSeparator).getBytes());
                } else {
                    // Procesa el resto de caracteres
                    if (character == '\n') {
                        salida.write(' ');
                    } else {
                        salida.write(character);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error en la lectura o escritura: " + e.getMessage());
        }
    }

    public void leerEscribirCarCar() throws RutaInvalida {
        try (FileReader entrada = new FileReader(pedirRuta("entrada"));
                FileWriter salida = new FileWriter(pedirRuta("salida"))) {

            int character = 0;
            int i = 0;

            //Escribimos Titulo
            salida.write(campos[i]);

            //Mientras no acabe el texto
            while ((character = entrada.read()) != -1) {
                //I ira de 0 a 7
                int aumentarI = (i + 1) % 7;

                if (character == '#') {
                    if (i == 3) {
                        salida.write(" minutos");
                    }
                    i = aumentarI;
                    salida.write(lineSeparator);
                    salida.write(campos[i]);
                } else if (character == '{') {
                    i = aumentarI;
                    salida.write(lineSeparator + lineSeparator);
                } else {
                    // Process other characters according to your logic
                    if (character == '\n') {
                        salida.write(' ');
                    } else {
                        salida.write(character);
                    }
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
            String rutaSalida = pedirRuta("salida");
            try (BufferedReader mibuffer = new BufferedReader(new FileReader(rutaEntrada));
                    ObjectOutputStream objectSalida = new ObjectOutputStream(new FileOutputStream(rutaSalida))) {
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
                for (Pelicula pelicula : cartelera) {
                    objectSalida.writeObject(pelicula);
                }
            }
            // Escribir los objetos en el archivo de salida
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
