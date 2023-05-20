import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class RutaInvalida extends Exception {

    public RutaInvalida(){
        
    }

    public RutaInvalida(String mensaje) {
        super(mensaje);
    }

    public void recordLogs() {
        try {
            try (FileWriter logFile = new FileWriter("./logs_error.txt")) {
                logFile.write(super.getMessage()+ "\n" + LocalDate.now() + "\n" + Arrays.toString(this.getStackTrace()) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error abriendo logs_error.txt");
        }

    }
}
