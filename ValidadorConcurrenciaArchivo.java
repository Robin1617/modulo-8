import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.function.Function;

public class ValidadorConcurrenciaArchivo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Thread> hilos = new ArrayList<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("registro_contrasenas.txt", true))) {
            System.out.print("¿Cuántas contraseñas desea validar? ");
            int cantidad = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < cantidad; i++) {
                System.out.print("Ingrese la contraseña #" + (i + 1) + ": ");
                String contraseña = scanner.nextLine();

                Thread hilo = new Thread(new ValidadorContrasenaArchivo(contraseña, i + 1, writer));
                hilos.add(hilo);
                hilo.start();
            }

            
            for (Thread hilo : hilos) {
                try {
                    hilo.join();
                } catch (InterruptedException e) {
                    System.out.println("Error al esperar el hilo.");
                }
            }

        } catch (IOException e) {
            System.out.println(" Error al abrir el archivo de registro.");
        }

        System.out.println("\n Validación finalizada y registrada en 'registro_contrasenas.txt'");
    }
}

class ValidadorContrasenaArchivo implements Runnable {
    private String contraseña;
    private int id;
    private BufferedWriter writer;

    public ValidadorContrasenaArchivo(String contraseña, int id, BufferedWriter writer) {
        this.contraseña = contraseña;
        this.id = id;
        this.writer = writer;
    }

    @Override
    public void run() {
        System.out.println("🔍 Validando contraseña #" + id + "...");

        boolean esValida = true;
        ArrayList<String> errores = new ArrayList<>();

        if (contraseña.length() < 8) {
            errores.add("Debe tener al menos 8 caracteres.");
            esValida = false;
        }

        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(contraseña).find()) {
            errores.add("Debe contener al menos un carácter especial.");
            esValida = false;
        }

        Matcher mayusculas = Pattern.compile("[A-Z]").matcher(contraseña);
        int conteoMayusculas = 0;
        while (mayusculas.find()) conteoMayusculas++;
        if (conteoMayusculas < 2) {
            errores.add("Debe contener al menos 2 letras mayúsculas.");
            esValida = false;
        }

        Matcher minusculas = Pattern.compile("[a-z]").matcher(contraseña);
        int conteoMinusculas = 0;
        while (minusculas.find()) conteoMinusculas++;
        if (conteoMinusculas < 3) {
            errores.add("Debe contener al menos 3 letras minúsculas.");
            esValida = false;
        }

        if (!Pattern.compile("[0-9]").matcher(contraseña).find()) {
            errores.add("Debe contener al menos un número.");
            esValida = false;
        }

       
        Function<Boolean, String> resultado = valido -> valido ? "VÁLIDA" : "INVÁLIDA";

        synchronized (writer) {
            try {
                writer.write("Contraseña #" + id + ": " + contraseña + " => " + resultado.apply(esValida));
                writer.newLine();
                if (!esValida) {
                    for (String error : errores) {
                        writer.write("   - " + error);
                        writer.newLine();
                    }
                }
                writer.flush();
            } catch (IOException e) {
                System.out.println(" Error al escribir en el archivo.");
            }
        }

        if (esValida) {
            System.out.println(" Contraseña #" + id + " válida.");
        } else {
            System.out.println(" Contraseña #" + id + " inválida por las siguientes razones:");
            errores.forEach(error -> System.out.println("   - " + error));
        }
    }
}
