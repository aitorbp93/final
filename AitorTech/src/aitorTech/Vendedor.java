package aitorTech;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Vendedor extends Tienda {
    // Definimos el nombre de usuario y la contraseña del administrador.
    private String usuario = "Admin";
    private String pass = "admin1234";

    // Constructor por defecto.
    public Vendedor() {
    }

    // Constructor que permite crear un vendedor con un nombre de usuario y contraseña específicos.
    public Vendedor(String usuario, String pass) {
        this.usuario = usuario;
        this.pass = pass;
    }

    // Métodos para obtener y establecer el nombre de usuario.
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Métodos para obtener y establecer la contraseña.
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    // Método para validar el nombre de usuario y la contraseña.
    public String validarPass(String usuario, String pass) {
        // Creamos campos de texto para que el usuario ingrese su nombre y contraseña.
        JTextField fieldUsuario = new JTextField();
        JPasswordField fieldPass = new JPasswordField();
        // Creamos un mensaje que mostrara los campos de texto en un cuadro de diálogo.
        Object[] mensaje = { "Usuario:", fieldUsuario, "Contraseña:", fieldPass };
        boolean incorrecto = true; // Variable para controlar el ciclo de validacion.
        int opcion;
        do {
            // Mostramos un cuadro de dialogo para que el usuario ingrese su nombre y contraseña.
            opcion = JOptionPane.showConfirmDialog(null, mensaje, "Acceso", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (opcion == JOptionPane.OK_OPTION) {
                // Verificamos si el nombre de usuario y la contraseña son correctos.
                if (this.usuario.equals(fieldUsuario.getText())
                        && this.pass.equals(new String(fieldPass.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Acceso permitido"); // Mensaje de acceso permitido.
                    incorrecto = true;
                    return "Usuario correcto"; // Devolvemos "Usuario correcto" si las credenciales son correctas.
                } else {
                    JOptionPane.showMessageDialog(null, "Acceso incorrecto"); // Mensaje de acceso incorrecto.
                    incorrecto = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Operación cancelada"); // Mensaje de operacion cancelada.
                incorrecto = false;
            }
        } while (!incorrecto); // Repetimos el ciclo hasta que las credenciales sean correctas o se cancele.

        return "Operación cancelada"; // Devolvemos "Operacion cancelada" si se cancela.
    }

    @Override
    // Método para mostrar el menu del vendedor.
    public void mostrarMenu() {
        boolean seguir = true; // Variable para controlar el ciclo del menú del vendedor.

        while (seguir) {
            // Opciones del menu del vendedor.
            String[] opciones = { "1-Mostrar el inventario", "2-Añadir producto al inventario",
                    "3-Borrar producto del inventario", "4-Editar producto del inventario", "Salir" };
            // Mostramos un cuadro de dialogo para que el vendedor seleccione una opcion.
            String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona una opción:", "Menú Vendedor",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion != null) {
                // Dependiendo de la opcion seleccionada, llamamos al método correspondiente.
                switch (seleccion) {
                case "1-Mostrar el inventario":
                    inventario(); // Llamamos al metodo que muestra el inventario.
                    break;
                case "2-Añadir producto al inventario":
                    crearProducto(); // Llamamos al metodo que añade un producto al inventario.
                    break;
                case "3-Borrar producto del inventario":
                    eliminarProducto(); // Llamamos al metodo que borra un producto del inventario.
                    break;
                case "4-Editar producto del inventario":
                    editarProducto(); // Llamamos al metodo que edita un producto del inventario.
                    break;
                case "Salir":
                    seguir = false; // Ponemos la variable seguir en false para salir del menu.
                    break;
                default:
                    break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una opción"); // Mensaje de error si no se selecciona ninguna opcion.
            }
        }
    }

 // Metodo para mostrar el inventario
    public String inventario() {
        // Definimos las opciones que el vendedor puede seleccionar.
        String[] opciones = { "1. Laptops", "2. Sobremesa", "3. Workstation", "4. Smartphone", "5. Monitores" };
        String seleccion;

        // Hacemos que el usuario seleccione una opcion hasta que elija una valida.
        do {
            // Mostramos un cuadro de dialogo para que el usuario seleccione una opcion.
            seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona una opcion:", "Menu Vendedor",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            // Verificamos si el usuario ha seleccionado una opcion.
            if (seleccion != null) {
                try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root",
                        "")) {
                    // Mensaje en consola indicando que se esta conectando a la base de datos.
                    System.out.println("Conectando a la base de datos");

                    // Dependiendo de la opcion seleccionada, mostramos los datos correspondientes.
                    switch (seleccion) {
                    case "1. Laptops":
                        // Mostramos un cuadro de dialogo con los datos de los laptops.
                        JOptionPane.showMessageDialog(null, "Mostrando laptops" + verdatosLaptop(conexion));
                        break;
                    case "2. Sobremesa":
                        // Mostramos un cuadro de dialogo con los datos de las computadoras de sobremesa.
                        JOptionPane.showMessageDialog(null, "Mostrando Sobremesas" + verdatosSobremesa(conexion));
                        break;
                    case "3. Workstation":
                        // Mostramos un cuadro de dialogo con los datos de las estaciones de trabajo.
                        JOptionPane.showMessageDialog(null, "Mostrando Workstations" + verdatosWorkstation(conexion));
                        break;
                    case "4. Smartphone":
                        // Mostramos un cuadro de dialogo con los datos de los smartphones.
                        JOptionPane.showMessageDialog(null, "Mostrando Smartphone" + verdatosSmarphone(conexion));
                        break;
                    case "5. Monitores":
                        // Mostramos un cuadro de dialogo con los datos de los monitores.
                        JOptionPane.showMessageDialog(null, "Mostrando Monitores" + verdatosMonitores(conexion));
                        break;
                    }
                } catch (SQLException e) {
                    // Si ocurre un error al conectar a la base de datos, lo mostramos en consola.
                    System.out.println("Error al conectar" + e);
                }
            } else {
                // Si no se selecciono ninguna opcion, mostramos un mensaje indicando que se debe seleccionar una.
                JOptionPane.showMessageDialog(null, "Debe seleccionar una opcion");
            }
        } while (seleccion == null); // Repetimos el ciclo si no se selecciono ninguna opcion.
        return seleccion; // Devolvemos la opcion seleccionada.
    }

    // Metodo para ver los portatiles en la base de datos
    public static String verdatosLaptop(Connection conexion) {
        String datos = "";
        // Consulta SQL para obtener los datos de los laptops.
        String sql = "SELECT * FROM laptop";

        try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
            // Recorremos los resultados de la consulta.
            while (resultado.next()) {
               
                datos += "id: " + resultado.getInt("id") + ", Modelo: " + resultado.getString("modelo")
                        + ", Procesador: " + resultado.getString("procesador") + ", Memoria RAM: "
                        + resultado.getInt("memoria_ram") + ", Almacenamiento: " + resultado.getString("disco_duro")
                        + ", Precio: " + resultado.getString("precio") + ", Garantia: "
                        + resultado.getString("fecha_garantia") + ", Stock: " + resultado.getInt("stock") + "\n";
            }
        } catch (SQLException e) {
            // Si ocurre un error al consultar los datos, mostramos un mensaje de error.
            JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return "Error al consultar datos";
        }
        // Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para mostrar.
        return datos.isEmpty() ? "No hay datos para mostrar" : datos;
    }

    // Metodo para ver los sobremesa en la base de datos
    public static String verdatosSobremesa(Connection conexion) {
        String datos = "";
        // Consulta SQL para obtener los datos de las computadoras de sobremesa.
        String sql = "SELECT * FROM sobremesas";

        try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
            // Recorremos los resultados de la consulta.
            while (resultado.next()) {
                
                datos += "id: " + resultado.getInt("id") + ", Modelo: " + resultado.getString("modelo")
                        + ", Procesador: " + resultado.getString("procesador") + ", Memoria RAM: "
                        + resultado.getInt("memoria_ram") + ", Almacenamiento: " + resultado.getString("disco_duro")
                        + ", Precio: " + resultado.getString("precio") + ", Garantia: "
                        + resultado.getString("fecha_garantia") + ", Stock: " + resultado.getInt("stock") + "\n";
            }
        } catch (SQLException e) {
            // Si ocurre un error al consultar los datos, mostramos un mensaje de error.
            JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return "Error al consultar datos";
        }
        // Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para mostrar.
        return datos.isEmpty() ? "No hay datos para mostrar" : datos;
    }

    // Metodo para ver las estaciones de trabajo en la base de datos
    public static String verdatosWorkstation(Connection conexion) {
        String datos = "";
        // Consulta SQL para obtener los datos de las estaciones de trabajo.
        String sql = "SELECT * FROM workstation";

        try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
            // Recorremos los resultados de la consulta.
            while (resultado.next()) {
                
                datos += "id: " + resultado.getInt("id") + ", Modelo: " + resultado.getString("modelo")
                        + ", Procesador: " + resultado.getString("procesador") + ", Memoria RAM: "
                        + resultado.getInt("memoria_ram") + ", Almacenamiento: " + resultado.getString("disco_duro")
                        + ", Precio: " + resultado.getString("precio") + ", Garantia: "
                        + resultado.getString("fecha_garantia") + ", Stock: " + resultado.getInt("stock")
                        + ", Tarjeta Grafica: " + resultado.getString("tarjeta_grafica") + "\n";
            }
        } catch (SQLException e) {
            // Si ocurre un error al consultar los datos, mostramos un mensaje de error.
            JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return "Error al consultar datos.";
        }
        // Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para mostrar.
        return datos.isEmpty() ? "No hay datos para mostrar" : datos;
    }

 // Metodo para ver los smartphone de la base de datos
    public static String verdatosSmarphone(Connection conexion) {
        String datos = "";
        String sql = "SELECT * FROM smartphone";

        try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
            while (resultado.next()) {
                // Aqui vamos a juntar todos los datos de cada smartphone en una sola cadena de texto
                datos += "id: " + "\n" + resultado.getInt("id") + ", Modelo: " + resultado.getString("modelo")
                        + ", Sistema Operativo: " + resultado.getString("sistema_operativo") + ", memoria_ram: "
                        + resultado.getInt("memoria_ram") + ", Almacenamiento: " + resultado.getString("almacenamiento")
                        + ", Precio: " + resultado.getString("precio") + ", Garantia: "
                        + resultado.getString("fecha_garantia") + ", Stock: " + resultado.getInt("stock") + "\n";
            }
        } catch (SQLException e) {
            // Si hay un error al consultar los datos, mostramos un mensaje de error en una ventana emergente
            JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return "Error al consultar datos.";
        }
        // Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para mostrar
        return datos.isEmpty() ? "No hay datos para mostrar" : datos;
    }

    // Metodo para ver los monitores de la base de datos
    public static String verdatosMonitores(Connection conexion) {
        String datos = "";
        String sql = "SELECT * FROM monitores";

        try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
            while (resultado.next()) {
                // Aqui vamos a juntar todos los datos de cada monitor en una sola cadena de texto
                datos += "id: " + "\n" + resultado.getInt("id") + ", Modelo: " + resultado.getString("modelo")
                        + ", Tamano Pantalla: " + resultado.getString("tamaño") + ", Resolucion: "
                        + resultado.getString("resolucion") + ", Precio: " + resultado.getString("precio")
                        + ", Garantia: " + resultado.getString("fecha_garantia") + ", Stock: "
                        + resultado.getInt("stock") + "\n";
            }
        } catch (SQLException e) {
            // Si hay un error al consultar los datos, mostramos un mensaje de error en una ventana emergente
            JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return "Error al consultar datos.";
        }
        // Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para mostrar
        return datos.isEmpty() ? "No hay datos para mostrar" : datos;
    }

    // Metodo para eliminar un producto de la base de datos
    private void eliminarProducto() {
        // Opciones para seleccionar el tipo de producto que se quiere eliminar
        String[] opciones = { "1. Laptops", "2. Sobremesa", "3. Workstation", "4. Smartphone", "5. Monitores" };
        // Mostramos un cuadro de dialogo para que el usuario elija una categoria
        String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona una categoria:", "Eliminar Producto",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            // Obtenemos el nombre de la tabla correspondiente a la categoria seleccionada
            String tabla = obtenerTabla(seleccion);
            if (tabla != null) {
                // Pedimos al usuario que ingrese el ID del producto a eliminar
                String idStr = JOptionPane.showInputDialog(null, "Ingresa el ID del producto a eliminar:");
                if (idStr != null) {
                    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root",
                            "")) {
                        int id = Integer.parseInt(idStr);
                        String sql = "DELETE FROM " + tabla + " WHERE id = ?";
                        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                            pstmt.setInt(1, id);
                            int rowsAffected = pstmt.executeUpdate();
                            // Si se elimina al menos un producto, mostramos un mensaje que es correcto
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
                            } else {
                                // Si no se encuentra ningun producto con ese ID, mostramos un mensaje de advertencia
                                JOptionPane.showMessageDialog(null, "No se encontro ningun producto con esa id");
                            }
                        }
                    } catch (SQLException e) {
                        // Si hay un error al eliminar el producto, mostramos un mensaje de error
                        JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException e) {
                        // Si el ID ingresado no es un numero valido, mostramos un mensaje de error
                        JOptionPane.showMessageDialog(null, "ID incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            // Si no se selecciona ninguna opcion, mostramos un mensaje diciendo que debe seleccionar una opcion
            JOptionPane.showMessageDialog(null, "Debes seleccionar una opcion");
        }
    }

    // Metodo para editar un producto de la base de datos
    private void editarProducto() {
        // Opciones para seleccionar el tipo de producto que se quiere editar
        String[] opciones = { "1. Laptops", "2. Sobremesa", "3. Workstation", "4. Smartphone", "5. Monitores" };
        // Mostramos un cuadro de dialogo para que el usuario elija una categoria
        String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona una categoria:", "Editar Producto",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            // Obtenemos el nombre de la tabla correspondiente a la opcion seleccionada
            String tabla = obtenerTabla(seleccion);
            if (tabla != null) {
                // Pedimos al usuario que ingrese el ID del producto a editar
                String idStr = JOptionPane.showInputDialog(null, "Introduce el ID del producto a editar: ");
                if (idStr != null) {
                    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root",
                            "")) {
                        int id = Integer.parseInt(idStr);
                        String campo = null;
                        // Dependiendo de la tabla, pedimos al usuario que ingrese el campo a editar
                        if (tabla.equals("laptop")) {
                            campo = JOptionPane.showInputDialog(null,
                                    "Que valor quieres cambiar? id, modelo, procesador, memoria_ram, disco_duro, precio, fecha_garantia, stock");
                        }
                        if (tabla.equals("sobremesas")) {
                            campo = JOptionPane.showInputDialog(null,
                                    "Que valor quieres cambiar? id, modelo, procesador, memoria_ram, disco_duro, precio, fecha_garantia, stock");
                        }
                        if (tabla.equals("workstation")) {
                            campo = JOptionPane.showInputDialog(null,
                                    "Que valor quieres cambiar? id, modelo, procesador, memoria_ram, disco_duro, tarjeta_grafica, precio, fecha_garantia, stock");
                        }
                        if (tabla.equals("smartphone")) {
                            campo = JOptionPane.showInputDialog(null,
                                    "Que valor quieres cambiar? id, modelo, sistema_operativo, memoria_ram, almacenamiento, precio, fecha_garantia, stock");
                        }
                        if (tabla.equals("monitores")) {
                            campo = JOptionPane.showInputDialog(null,
                                    "Que valor quieres cambiar? id, modelo, tamano, resolucion, precio, fecha_garantia, stock");
                        }
                        if (campo != null) {
                            // Pedimos al usuario que ingrese el nuevo valor para el campo seleccionado
                            String nuevoValor = JOptionPane.showInputDialog(null,
                                    "Ingresa el nuevo valor para " + campo + ":");
                            if (nuevoValor != null) {
                                String sql = "UPDATE " + tabla + " SET " + campo + " = ? WHERE id = ?";
                                try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                                    pstmt.setString(1, nuevoValor);
                                    pstmt.setInt(2, id);
                                    int rowsAffected = pstmt.executeUpdate();
                                    // Si se actualiza al menos un producto, mostramos un mensaje de exito
                                    if (rowsAffected > 0) {
                                        JOptionPane.showMessageDialog(null, "Producto actualizado");
                                    } else {
                                        // Si no se encuentra ningun producto con ese ID, mostramos un mensaje de advertencia
                                        JOptionPane.showMessageDialog(null, "No se encontro ningun producto con ese ID.");
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        // Si hay un error al editar el producto, mostramos un mensaje de error
                        JOptionPane.showMessageDialog(null, "Error al editar el producto: " + e.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException e) {
                        // Si el ID ingresado no es un numero valido, mostramos un mensaje de error
                        JOptionPane.showMessageDialog(null, "ID incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            // Si no se selecciona ninguna opcion, mostramos un mensaje indicando que debe seleccionar una opcion
            JOptionPane.showMessageDialog(null, "Debes seleccionar una opcion");
        }
    }

    // Metodo para crear un producto en la base de datos
    private void crearProducto() {
        // Opciones para seleccionar el tipo de producto que se quiere crear
        String[] opciones = { "1. Laptops", "2. Sobremesa", "3. Workstation", "4. Smartphone", "5. Monitores" };
        // Mostramos un cuadro de dialogo para que el usuario elija una categoria
        String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona una categoria:", "Crear Producto",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            // Obtenemos el nombre de la tabla correspondiente a la categoria seleccionada
            String tabla = obtenerTabla(seleccion);
            if (tabla != null) {
                // Creamos campos de texto para ingresar los datos del nuevo producto
                JTextField modelo = new JTextField();
                JTextField procesador = new JTextField();
                JTextField memoriaRam = new JTextField();
                JTextField discoDuro = new JTextField();
                JTextField precio = new JTextField();
                JTextField garantia = new JTextField();
                JTextField stock = new JTextField();

                // Agrupamos los campos de texto en un arrays para enseñarlos en un cuadro de dialogo
                Object[] message = { "Modelo:", modelo, "Procesador:", procesador, "Memoria RAM:", memoriaRam,
                        "Disco Duro:", discoDuro, "Precio:", precio, "Garantia:", garantia, "Stock:", stock };

                // Mostramos un cuadro de dialogo para ingresar los datos del nuevo producto
                int option = JOptionPane.showConfirmDialog(null, message, "Crear Producto",
                        JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root",
                            "")) {
                        String sql = "INSERT INTO " + tabla
                                + " (modelo, procesador, memoria_ram, disco_duro, precio, fecha_garantia, stock) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                            // Asignamos los valores ingresados a la consulta SQL
                            pstmt.setString(1, modelo.getText());
                            pstmt.setString(2, procesador.getText());
                            pstmt.setInt(3, Integer.parseInt(memoriaRam.getText()));
                            pstmt.setString(4, discoDuro.getText());
                            pstmt.setString(5, precio.getText());
                            pstmt.setString(6, garantia.getText());
                            pstmt.setInt(7, Integer.parseInt(stock.getText()));
                            pstmt.executeUpdate();
                            // Mostramos un mensaje de exito si el producto se crea correctamente
                            JOptionPane.showMessageDialog(null, "Producto creado correctamente");
                        }
                    } catch (SQLException e) {
                        // Si hay un error al crear el producto, mostramos un mensaje de error
                        JOptionPane.showMessageDialog(null, "Error al crear el producto: " + e.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException e) {
                        // Si se ingresa un valor incorrecto, mostramos un mensaje de error
                        JOptionPane.showMessageDialog(null, "Opcion incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            // Si no se selecciona ninguna opcion, mostramos un mensaje indicando que debe seleccionar una opcion
            JOptionPane.showMessageDialog(null, "Debes seleccionar una opcion");
        }
    }

    // Metodo para dar el nombre de la tabla en la base de datos
    private String obtenerTabla(String categoria) {
        // Dependiendo de la categoria seleccionada, devolvemos el nombre de la tabla correspondiente
        switch (categoria) {
        case "1. Laptops":
            return "laptop";
        case "2. Sobremesa":
            return "sobremesas";
        case "3. Workstation":
            return "workstation";
        case "4. Smartphone":
            return "smartphone";
        case "5. Monitores":
            return "monitores";
        default:
            // Si se introduce una categoria incorrecta, mostramos un mensaje de error
            JOptionPane.showMessageDialog(null, "Introducelo exactamente", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}