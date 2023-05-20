import java.util.Scanner;

public class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        while (opcion != 5) {
            System.out.println(
                    "1. Byte a byte \n2. Carácter a carácter \n3. Línea a línea. \n4.Menú de objetos. \n5.Salir");
            System.out.print("Elige una opción: ");
            opcion = scan.nextInt();
            try {
                switch (opcion) {
                    case 1:
                        LecturaEscrituraStreams bytebyte = new LecturaEscrituraStreams();
                        bytebyte.leerEscribirByteByte();
                        break;
                    case 2:
                        LecturaEscrituraStreams carcar = new LecturaEscrituraStreams();
                        carcar.leerEscribirCarCar();

                        break;
                    case 3:
                        LecturaEscrituraStreams buffer = new LecturaEscrituraStreams();
                        buffer.leerLineaEscribirObj();
                        break;

                    case 4:
                        menuObjetos();
                        break;

                    case 5:
                        break;

                    default:
                        System.out.println("Opción errónea");
                        break;
                }

            } catch (RutaInvalida e) {
                System.out.println("Message: " + e.getMessage());
                e.recordLogs();
            }
        }

    }

    public static void menuObjetos() {
        int opcion = 0;

        while (opcion != 5) {
            System.out.println("1. Lectura línea a línea y escritura de objeto" +
                    "\n2. Lectura de objetos y escritura de objetos." +
                    "\n3. Lectura de objetos y escritura por consola. " +
                    "\n4. Lectura por consola y escritura de objetos. (añadirá objetos al final del fichero existente) "
                    +
                    "\n5.Salir");

            System.out.print("Elige una opción: ");
            opcion = scan.nextInt();

            try {
                switch (opcion) {
                    case 1:
                        LecturaEscrituraStreams buffer = new LecturaEscrituraStreams();
                        buffer.leerLineaEscribirObj();
                        break;
                    case 2:
                        LecturaEscrituraStreams objObj = new LecturaEscrituraStreams();
                        objObj.leerObjEscribirObj();
                        break;
                    case 3:
                        LecturaEscrituraStreams objCons = new LecturaEscrituraStreams();
                        objCons.leerObjEscribirCons();
                        break;
                    case 4:
                        LecturaEscrituraStreams consObj = new LecturaEscrituraStreams();
                        consObj.leerConsEscribirObj();
                        break;

                    default:
                        break;
                }
            } catch (RutaInvalida e) {
                System.out.println("Message: " + e.getMessage());
                e.recordLogs();
            }
        }
    }

}