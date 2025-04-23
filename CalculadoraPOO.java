import java.util.Scanner;

public class CalculadoraPOO {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Calculadora Geométrica (POO) ===");
            System.out.println("1. Área");
            System.out.println("2. Perímetro");
            System.out.println("3. Potencia");
            System.out.print("Seleccione una operación (1-3): ");
            int opcion = scanner.nextInt();

            Operacion operacion = null;

            switch (opcion) {
                case 1:
                    operacion = crearOperacion(scanner, true);
                    break;
                case 2:
                    operacion = crearOperacion(scanner, false);
                    break;
                case 3:
                    System.out.print("Ingrese la base: ");
                    int base = scanner.nextInt();
                    System.out.print("Ingrese el exponente: ");
                    int exponente = scanner.nextInt();
                    operacion = new Potencia(base, exponente);
                    break;
                default:
                    System.out.println(" Opción no válida.");
                    continue;
            }

            System.out.printf(" Resultado: %.2f\n", operacion.calcular());

            System.out.print("\n¿Desea realizar otra operación? (s/n): ");
            char resp = scanner.next().toLowerCase().charAt(0);
            if (resp != 's') continuar = false;
        }

        System.out.println(" ¡Gracias por usar la calculadora!");
    }

    public static Operacion crearOperacion(Scanner scanner, boolean esArea) {
        System.out.print("Ingrese el nombre de la figura (circulo, cuadrado, triangulo, rectangulo, pentagono): ");
        String figura = scanner.next().toLowerCase();

        switch (figura) {
            case "circulo":
            case "cuadrado":
                System.out.print("Ingrese el valor: ");
                double valor = scanner.nextDouble();
                return esArea ? new Area(figura, valor) : new Perimetro(figura, valor);

            case "triangulo":
                if (esArea) {
                    System.out.print("Base: ");
                    double base = scanner.nextDouble();
                    System.out.print("Altura: ");
                    double altura = scanner.nextDouble();
                    return new Area(figura, base, altura);
                } else {
                    System.out.print("Lado a: ");
                    double a = scanner.nextDouble();
                    System.out.print("Lado b: ");
                    double b = scanner.nextDouble();
                    System.out.print("Lado c: ");
                    double c = scanner.nextDouble();
                    return new Perimetro(figura, a, b, c);
                }

            case "rectangulo":
                System.out.print("Base: ");
                double base = scanner.nextDouble();
                System.out.print("Altura: ");
                double altura = scanner.nextDouble();
                return esArea ? new Area(figura, base, altura) : new Perimetro(figura, base, altura);

            case "pentagono":
                System.out.print("Lado: ");
                double lado = scanner.nextDouble();
                if (esArea) {
                    System.out.print("Apotema: ");
                    double apotema = scanner.nextDouble();
                    return new Area(figura, lado, apotema);
                } else {
                    return new Perimetro(figura, lado);
                }

            default:
                System.out.println(" Figura no válida.");
                return null;
        }
    }
}
