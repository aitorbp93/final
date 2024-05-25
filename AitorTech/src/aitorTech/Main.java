package aitorTech;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        mostrarFechaHora(); // Llamamos al método que muestra la fecha y la hora actual.

        boolean continuar = true; // Variable para mantener el menú principal en funcionamiento.
        while (continuar) {
            // Opciones del menú principal.
            String[] opciones = { "Vendedor", "Comprador", "Salir" };
            // Mostramos un diálogo para que el usuario seleccione una opción.
            String input = (String) JOptionPane.showInputDialog(null, "Selecciona el tipo de usuario:",
                    "Menu de inicio", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            // Verificamos si el usuario seleccionó alguna opción.
            if (input != null) {
                System.out.println("Ha seleccionado: " + input);
                // Dependiendo de la opción seleccionada, mostramos el menú correspondiente.
                switch (input) {
                case "Vendedor":
                    mostrarMenuVendedor(); // Llamamos al método que muestra el menú del vendedor.
                    break;
                case "Comprador":
                    mostrarMenuComprador(); // Llamamos al método que muestra el menú del comprador.
                    break;
                case "Salir":
                    continuar = false; // Ponemos la variable continuar en false para salir del menú.
                    break;
                default:
                    break;
                }
            } else {
                System.out.println("No se ha seleccionado ninguna opción");
            }
        }
        // Mostramos un mensaje al salir del programa.
        JOptionPane.showInternalMessageDialog(null, "<html><p style = \"color: red \">Agur</p></html>");
    }

    // Método para mostrar el menú del vendedor
    private static void mostrarMenuVendedor() {
        boolean regresar = true; // Variable para mantener el menú del vendedor en funcionamiento.
        Vendedor vendedor1 = new Vendedor(); // Creamos una instancia de la clase Vendedor.

        // Bucle para manejar el inicio de sesión del vendedor
        while (regresar) {
            // Opciones del menú del vendedor.
            String[] opcion = { "Iniciar sesión", "Volver" };
            // Mostramos un diálogo para que el vendedor seleccione una opción.
            String seleccion = (String) JOptionPane.showInputDialog(null, "Vendedor:", "Vendedor",
                    JOptionPane.QUESTION_MESSAGE, null, opcion, opcion[0]);

            // Verificamos si el vendedor seleccionó alguna opción.
            if (seleccion != null) {
                switch (seleccion) {
                case "Iniciar sesión":
                    // Verificamos si las credenciales del vendedor son correctas.
                    if (vendedor1.validarPass(null, null).equals("Usuario correcto")) {
                        vendedor1.mostrarMenu(); // Mostramos el menú del vendedor si las credenciales son correctas.
                    }
                    break;
                case "Volver":
                    regresar = false; // Ponemos la variable regresar en false para salir del menú.
                    break;
                default:
                    break;
                }
            } else {
                regresar = false; // Ponemos la variable regresar en false para salir del menú si no se seleccionó ninguna opción.
            }
        }
    }

    // Método para mostrar el menú del comprador
    private static void mostrarMenuComprador() {
        boolean regresar = true; // Variable para mantener el menú del comprador en funcionamiento.
        Comprador comprador1 = new Comprador(); // Creamos una instancia de la clase Comprador.

        while (regresar) {
            // Opciones del menú del comprador.
            String[] opcion = { "Crear nuevo usuario", "Iniciar sesión", "Volver" };
            // Mostramos un diálogo para que el comprador seleccione una opción.
            String input = (String) JOptionPane.showInputDialog(null, "Comprador:", "Comprador",
                    JOptionPane.QUESTION_MESSAGE, null, opcion, opcion[0]);

            // Verificamos si el comprador seleccionó alguna opción.
            if (input != null) {
                switch (input) {
                case "Crear nuevo usuario":
                    comprador1.crearUsuario(); // Llamamos al método para crear un nuevo usuario.
                    // Verificamos si las credenciales del comprador son correctas.
                    if (comprador1.validarPass(null, null).equals("Usuario correcto")) {
                        comprador1.mostrarMenu(); // Mostramos el menú del comprador si las credenciales son correctas.
                    }
                    break;
                case "Iniciar sesión":
                    // Verificamos si las credenciales del comprador son correctas.
                    if (comprador1.validarPass(null, null).equals("Usuario correcto")) {
                        comprador1.mostrarMenu(); // Mostramos el menú del comprador si las credenciales son correctas.
                    }
                    break;
                case "Volver":
                    regresar = false; // Ponemos la variable regresar en false para salir del menú.
                    break;
                default:
                    break;
                }
            } else {
                regresar = false; // Ponemos la variable regresar en false para salir del menú si no se seleccionó ninguna opción.
            }
        }
    }

    // Método para mostrar la fecha y hora local
    public static void mostrarFechaHora() {
        LocalDateTime ahora = LocalDateTime.now(); // Obtenemos la fecha y hora actual.

        // Formateamos la fecha y hora a un formato legible.
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = ahora.format(formato);

        // Mostramos un diálogo con la fecha y hora actual.
        JOptionPane.showMessageDialog(null, "Fecha y Hora Actual: " + fechaHoraFormateada, "Fecha y Hora",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
