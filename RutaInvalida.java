import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class RutaInvalida extends Exception {
    private String timestamp = 
    new java.text.SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date());

    public RutaInvalida(){
        
    }

    public RutaInvalida(String mensaje) {
        super(mensaje);
    }

    public void recordLogs() {
        try {
            try (FileWriter logFile = new FileWriter("./logs_error.txt")) {
                logFile.write(super.getMessage()+ "\n" + timestamp + "\n" + Arrays.toString(this.getStackTrace()) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error abriendo logs_error.txt");
        }

    }
}
