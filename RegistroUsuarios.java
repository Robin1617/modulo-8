import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class RegistroUsuarios {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Usuario> usuarios = new ArrayList<>();

        System.out.println("=== REGISTRO DE USUARIOS ===");

        String nombre, correo, contraseña;

        System.out.print("Ingrese su nombre completo: ");
        nombre = scanner.nextLine();
        while (!Validador.validarNombre(nombre)) {
            System.out.println(" Nombre inválido. Solo letras y espacios.");
            System.out.print("Ingrese su nombre completo: ");
            nombre = scanner.nextLine();
        }

        System.out.print("Ingrese su correo electrónico: ");
        correo = scanner.nextLine();
        while (!Validador.validarCorreo(correo)) {
            System.out.println(" Correo inválido. Debe tener formato ejemplo@dominio.com.");
            System.out.print("Ingrese su correo electrónico: ");
            correo = scanner.nextLine();
        }

        System.out.print("Cree una contraseña: ");
        contraseña = scanner.nextLine();
        while (!Validador.validarContrasena(contraseña)) {
            System.out.println(" Contraseña inválida. Debe tener al menos 8 caracteres, 2 mayúsculas, 3 minúsculas, 1 número y 1 carácter especial.");
            System.out.print("Cree una contraseña: ");
            contraseña = scanner.nextLine();
        }

       
        Usuario nuevoUsuario = new Usuario(nombre, correo, contraseña);
        usuarios.add(nuevoUsuario);

        System.out.println("\n Usuario registrado exitosamente:");
        System.out.println(nuevoUsuario);
    }
}
