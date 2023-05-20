import java.io.Serializable;
import java.util.Scanner;

public class Pelicula implements Serializable{
    private String titulo;
    private int year;
    private String director;
    private int duration;
    private String description;
    private String reparto;
    private String hora;

    public Pelicula(){

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReparto() {
        return reparto;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "-----" + titulo +"-----" + "\n Año: " + year + "\n Director: " + director + "\n Duración: " + duration + " minutos"
                + "\n Sinopsis: " + description + "\n Reparto: " + reparto + "\n Sesión: " + hora + " horas\n";
    }

    public void crearPelicula(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce un título: ");
        this.setTitulo(scan.nextLine());
        System.out.println("Introduce el año: ");
        this.setYear(scan.nextInt());
        System.out.println("Introduce la duración: ");
        this.setDuration(scan.nextInt());
        System.out.println("Introduce una descripción: ");
        this.setDescription(scan.nextLine());
        System.out.println("Introduce el reparto: ");
        this.setReparto(scan.nextLine());
        System.out.println("Introduce un sesión: ");
        this.setHora(scan.nextLine());
    }
}
