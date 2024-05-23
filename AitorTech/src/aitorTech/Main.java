package aitorTech;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        mostrarFechaHora(); // Llamamos al metodo que muestra la fecha y la hora actual.

        // Utilizamos SwingUtilities para asegurarnos de que la interfaz grafica se ejecute en el hilo adecuado.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                boolean continuar = true; // Variable para mantener el menu principal en funcionamiento.
                while (continuar) {
                    // Opciones del menu principal.
                    String[] options = { "Vendedor", "Comprador", "Salir" };
                    // Mostramos un dialogo para que el usuario seleccione una opcion.
                    String input = (String) JOptionPane.showInputDialog(null, "Selecciona el tipo de usuario:",
                            "Menu de inicio", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    // Verificamos si el usuario selecciono alguna opcion.
                    if (input != null) {
                        System.out.println("Ha seleccionado: " + input);
                        // Dependiendo de la opcion seleccionada, mostramos el menu correspondiente.
                        switch (input) {
                        case "Vendedor":
                            mostrarMenuVendedor(); // Llamamos al metodo que muestra el menu del vendedor.
                            break;
                        case "Comprador":
                            mostrarMenuComprador(); // Llamamos al metodo que muestra el menu del comprador.
                            break;
                        case "Salir":
                            continuar = false; // Ponemos la variable continuar en false para salir del menu.
                            break;
                        default:
                            break;
                        }
                    } else {
                        System.out.println("No se ha seleccionado ninguna opcion");
                    }
                }
                // Mostramos un mensaje al salir del programa.
                JOptionPane.showInternalMessageDialog(null, "<html><p style = \"color: red \">Agur</p></html>");
            }
        });
    }

    // Metodo para mostrar el menu del vendedor
    private static void mostrarMenuVendedor() {
        boolean regresar = true; // Variable para mantener el menu del vendedor en funcionamiento.
        Vendedor vendedor1 = new Vendedor(); // Creamos una instancia de la clase Vendedor.

        // Bucle para manejar el inicio de sesion del vendedor
        while (regresar) {
            // Opciones del menu del vendedor.
            String[] options = { "Iniciar sesion", "Volver" };
            // Mostramos un dialogo para que el vendedor seleccione una opcion.
            String input = (String) JOptionPane.showInputDialog(null, "Vendedor:", "Vendedor",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            // Verificamos si el vendedor selecciono alguna opcion.
            if (input != null) {
                switch (input) {
                case "Iniciar sesion":
                    // Verificamos si las credenciales del vendedor son correctas.
                    if (vendedor1.validarPass(null, null).equals("Usuario correcto")) {
                        vendedor1.mostrarMenu(); // Mostramos el menu del vendedor si las credenciales son correctas.
                    }
                    break;
                case "Volver":
                    regresar = false; // Ponemos la variable regresar en false para salir del menu.
                    break;
                default:
                    break;
                }
            } else {
                regresar = false; // Ponemos la variable regresar en false para salir del menu si no se selecciono ninguna opcion.
            }
        }
    }

    // Metodo para mostrar el menu del comprador
    private static void mostrarMenuComprador() {
        boolean regresar = true; // Variable para mantener el menu del comprador en funcionamiento.
        Comprador comprador1 = new Comprador(); // Creamos una instancia de la clase Comprador.

        while (regresar) {
            // Opciones del menu del comprador.
            String[] options = { "Crear nuevo usuario", "Iniciar sesion", "Volver" };
            // Mostramos un dialogo para que el comprador seleccione una opcion.
            String input = (String) JOptionPane.showInputDialog(null, "Comprador:", "Comprador",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            // Verificamos si el comprador selecciono alguna opcion.
            if (input != null) {
                switch (input) {
                case "Crear nuevo usuario":
                    comprador1.crearUsuario(); // Llamamos al metodo para crear un nuevo usuario.
                    // Verificamos si las credenciales del comprador son correctas.
                    if (comprador1.validarPass(null, null).equals("Usuario correcto")) {
                        comprador1.mostrarMenu(); // Mostramos el menu del comprador si las credenciales son correctas.
                    }
                    break;
                case "Iniciar sesion":
                    // Verificamos si las credenciales del comprador son correctas.
                    if (comprador1.validarPass(null, null).equals("Usuario correcto")) {
                        comprador1.mostrarMenu(); // Mostramos el menu del comprador si las credenciales son correctas.
                    }
                    break;
                case "Volver":
                    regresar = false; // Ponemos la variable regresar en false para salir del menu.
                    break;
                default:
                    break;
                }
            } else {
                regresar = false; // Ponemos la variable regresar en false para salir del menu si no se selecciono ninguna opcion.
            }
        }
    }

    // Metodo para mostrar la fecha y hora local
    public static void mostrarFechaHora() {
        LocalDateTime ahora = LocalDateTime.now(); // Obtenemos la fecha y hora actual.

        // Formateamos la fecha y hora a un formato legible.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = ahora.format(formatter);

        // Mostramos un dialogo con la fecha y hora actual.
        JOptionPane.showMessageDialog(null, "Fecha y Hora Actual: " + fechaHoraFormateada, "Fecha y Hora",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
