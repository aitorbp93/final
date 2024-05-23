package aitorTech;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class Comprador extends Tienda {
	private String usuario;
	private String pass;
	private ArrayList<String> carrito = new ArrayList<>(); // Lista para almacenar los productos en el carrito

	public Comprador() {
		super();
	}

	public Comprador(String usuario, String pass) {
		super();
		this.usuario = usuario;
		this.pass = pass;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String validarPass(String usuario, String pass) {
		// Creamos campos de texto para que el usuario ingrese su nombre y contrasena
		JTextField fieldUsuario = new JTextField();
		JPasswordField fieldPass = new JPasswordField();
		// Creamos un mensaje que mostrara los campos de texto en un cuadro de dialogo
		Object[] message = { "Usuario:", fieldUsuario, "pass:", fieldPass };

		int opcion;
		do {
			// Mostramos un cuadro de dialogo para que el usuario ingrese su nombre y
			// contrasena
			opcion = JOptionPane.showConfirmDialog(null, message, "Acceso", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (opcion == JOptionPane.OK_OPTION) {
				// Obtenemos el nombre de usuario y la contrasena ingresados
				String usuarioIngresado = fieldUsuario.getText();
				String passIngresada = new String(fieldPass.getPassword());
				// Validamos las credenciales ingresadas
				if (validarCredenciales(usuarioIngresado, passIngresada)) {
					this.usuario = usuarioIngresado; // Establecemos el usuario logueado
					JOptionPane.showMessageDialog(null, "Acceso correcto"); // Mostramos un mensaje de acceso correcto
					return "Usuario correcto"; // Devolvemos "Usuario correcto" si las credenciales son validas
				} else {
					JOptionPane.showMessageDialog(null, "Usuario incorrecto"); // Mostramos un mensaje de acceso
																				// incorrecto
				}
			} else {
				return "Saliendo"; // Devolvemos "Saliendo" si se cancela la operacion
			}
		} while (opcion != JOptionPane.CANCEL_OPTION);

		return "Saliendo"; // Devolvemos "Saliendo" si se cancela la operacion
	}

	public boolean validarCredenciales(String usuario, String contrasena) {
		// Intentamos conectar a la base de datos y verificar las credenciales
		try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", "");
				PreparedStatement pstmt = conexion.prepareStatement("SELECT pass FROM usuarios WHERE nombre = ?")) {
			pstmt.setString(1, usuario);

			try (ResultSet resultado = pstmt.executeQuery()) {
				if (resultado.next()) {
					// Obtenemos la contrasena almacenada en la base de datos
					String passGuardada = resultado.getString("pass");
					// Comparamos la contrasena ingresada con la almacenada
					return contrasena.equals(passGuardada);
				}
			}
		} catch (SQLException e) {
			// Si hay un error al verificar el usuario, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al verificar usuario: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return false; // Devolvemos false si las credenciales no son validas
	}

	@Override
	public void mostrarMenu() {
		boolean seguir = true; // Variable para controlar si seguimos mostrando el menu

		while (seguir) {
			// Opciones que el comprador puede seleccionar en el menu
			String[] options = { "1-Ver Productos", "2-Anadir producto al carrito", "3-Borrar producto del carrito",
					"4-Ver carrito", "5-Comprar", "6-Ver Compras", "Salir" };
			// Mostramos un cuadro de dialogo para que el comprador seleccione una opcion
			String input = (String) JOptionPane.showInputDialog(null, "Selecciona una opcion:", "Menu Comprador",
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if (input != null) {
				// Dependiendo de la opcion seleccionada, llamamos al metodo correspondiente
				switch (input) {
				case "1-Ver Productos":
					// Mostramos los productos disponibles
					inventario();
					break;
				case "2-Anadir producto al carrito":
					// Anadir un producto al carrito
					agregarProductoCarrito();
					break;
				case "3-Borrar producto del carrito":
					// Borrar un producto del carrito
					borrarProductoCarrito();
					break;
				case "4-Ver carrito":
					// Ver los productos en el carrito
					verCarrito();
					break;
				case "5-Comprar":
					// Realizar la compra de los productos en el carrito
					comprar();
					break;
				case "6-Ver Compras":
					// Ver las compras realizadas
					verCompras();
					break;
				case "Salir":
					// Salir del menu
					seguir = false;
					break;
				default:
					break;
				}
			} else {
				// Si no se selecciona ninguna opcion, salimos del menu
				seguir = false;
			}
		}
	}

	// Metodo para crear un usuario comprador nuevo
	public void crearUsuario() {
		// Pedimos al usuario que ingrese su nombre de usuario y contrasena
		usuario = JOptionPane.showInputDialog("Di tu nombre de usuario");
		pass = JOptionPane.showInputDialog("Contrasena");

		// Verificamos que el nombre de usuario y la contrasena no esten vacios
		if (usuario != null && pass != null && !usuario.isEmpty() && !pass.isEmpty()) {//isempty es para ver si una cadena de texto esta vacia.
			// Intentamos conectar a la base de datos y crear el nuevo usuario
			try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", "");
					PreparedStatement pstmt = conexion
							.prepareStatement("INSERT INTO usuarios (nombre, pass) VALUES (?, ?)")) {
				// Asignamos el nombre de usuario y la contrasena a la consulta SQL
				pstmt.setString(1, usuario);
				pstmt.setString(2, pass);
				int resultado = pstmt.executeUpdate();

				// Si el usuario se crea correctamente, mostramos un mensaje de exito
				if (resultado > 0) {
					JOptionPane.showMessageDialog(null, "Usuario creado");
				} else {
					// Si no se puede crear el usuario, mostramos un mensaje de error
					JOptionPane.showMessageDialog(null, "No se ha podido crear el usuario");
				}
			} catch (SQLException e) {
				// Si hay un error al crear el usuario, mostramos un mensaje de error
				JOptionPane.showMessageDialog(null, "Error al crear el usuario: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			// Si el nombre de usuario o la contrasena estan vacios, mostramos un mensaje de
			// advertencia
			JOptionPane.showMessageDialog(null, "Rellena el usuario y la contrasena");
		}
	}

	// Metodo para mostrar los productos
	public String inventario() {
		// Definimos las opciones que el comprador puede seleccionar
		String[] opciones = { "1-Laptops", "2-Sobremesa", "3-Workstation", "4-Smartphone", "5-Monitores" };
		String seleccion;

		do {
			// Mostramos un cuadro de dialogo para que el comprador seleccione una opcion
			seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona una opcion:", "Menu Comprador",
					JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

			if (seleccion != null) {
				try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root",
						"")) {
					// Mensaje en consola indicando que se esta conectando a la base de datos
					System.out.println("Conectandose a la bbdd");
					// Dependiendo de la opcion seleccionada, mostramos los datos correspondientes
					switch (seleccion) {
					case "1-Laptops":
						return verdatosLaptop(conexion); // Mostrar datos de laptops
					case "2-Sobremesa":
						return verdatosSobremesa(conexion); // Mostrar datos de sobremesas
					case "3-Workstation":
						return verdatosWorkstation(conexion); // Mostrar datos de workstations
					case "4-Smartphone":
						return verdatosSmarphone(conexion); // Mostrar datos de smartphones
					case "5-Monitores":
						return verdatosMonitores(conexion); // Mostrar datos de monitores
					}
				} catch (SQLException e) {
					// Si hay un error al conectar a la base de datos, lo mostramos en consola
					System.err.println("Error al conectar" + e);
				}
			} else {
				// Si no se selecciona ninguna opcion, mostramos un mensaje indicando que debe
				// seleccionar una opcion
				JOptionPane.showMessageDialog(null, "Selecciona una opcion");
			}
		} while (seleccion == null); // Repetimos el ciclo si no se selecciono ninguna opcion
		return "No hay datos para mostrar";
	}

	// Metodo para ver los portatiles de la base de datos
	public static String verdatosLaptop(Connection conexion) {
		String datos = "";
		String sql = "SELECT * FROM laptop"; // Consulta SQL para obtener los datos de los laptops

		try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
			// Recorremos los resultados de la consulta
			while (resultado.next()) {

				datos += "ID: " + "\n" + resultado.getInt("id") + "Modelo: " + resultado.getString("modelo")
						+ "Procesador: " + resultado.getString("procesador") + "Memoria RAM: "
						+ resultado.getInt("memoria_ram") + "Almacenamiento: " + resultado.getString("disco_duro")
						+ "Precio: " + resultado.getString("precio") + "Garantia: "
						+ resultado.getString("fecha_garantia") + "Stock: " + resultado.getInt("stock");
			}
		} catch (SQLException e) {
			// Si hay un error al consultar los datos, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return "Error al consultar datos";
		}
		// Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para
		// mostrar
		return datos.isEmpty() ? "No hay datos para mostrar" : datos;
	}

	// Metodo para ver los sobremesas de la base de datos
	public static String verdatosSobremesa(Connection conexion) {
		String datos = "";
		String sql = "SELECT * FROM sobremesas"; // Consulta SQL para obtener los datos de las computadoras de sobremesa

		try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
			// Recorremos los resultados de la consulta
			while (resultado.next()) {

				datos += "ID: " + "\n" + resultado.getInt("id") + "Modelo: " + resultado.getString("modelo")
						+ "Procesador: " + resultado.getString("procesador") + "Memoria RAM: "
						+ resultado.getInt("memoria_ram") + "Almacenamiento: " + resultado.getString("disco_duro")
						+ "\n" + "Precio: " + resultado.getString("precio") + "Garantia: "
						+ resultado.getString("fecha_garantia") + "Stock: " + resultado.getInt("stock");
			}
		} catch (SQLException e) {
			// Si hay un error al consultar los datos, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return "Error al consultar datos";
		}
		// Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para
		// mostrar
		return datos.isEmpty() ? "No hay datos para mostrar" : datos;
	}

	// Metodo para ver las estaciones de trabajo de la base de datos
	public static String verdatosWorkstation(Connection conexion) {
		String datos = "";
		String sql = "SELECT * FROM workstation"; // Consulta SQL para obtener los datos de las estaciones de trabajo

		try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
			// Recorremos los resultados de la consulta
			while (resultado.next()) {

				datos += "ID: " + "\n" + resultado.getInt("id") + "Modelo: " + resultado.getString("modelo")
						+ "Procesador: " + resultado.getString("procesador") + "Memoria RAM: "
						+ resultado.getInt("memoria_ram") + "Almacenamiento: " + resultado.getString("disco_duro")
						+ "Precio: " + resultado.getString("precio") + "Garantia: "
						+ resultado.getString("fecha_garantia") + "Stock: " + resultado.getInt("stock")
						+ "Tarjeta Grafica: " + resultado.getString("tarjeta_grafica");
			}
		} catch (SQLException e) {
			// Si hay un error al consultar los datos, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return "Error al consultar datos.";
		}
		// Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para
		// mostrar
		return datos.isEmpty() ? "No hay datos para mostrar" : datos;
	}

	// Metodo para ver los smartphones de la base de datos
	public static String verdatosSmarphone(Connection conexion) {
		String datos = "";
		String sql = "SELECT * FROM smartphone"; // Consulta SQL para obtener los datos de los smartphones

		try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
			// Recorremos los resultados de la consulta
			while (resultado.next()) {

				datos += "ID: " + "\n" + resultado.getInt("id") + "Modelo: " + resultado.getString("modelo")
						+ "Sistema Operativo: " + resultado.getString("sistema_operativo") + "Memoria RAM: "
						+ resultado.getInt("memoria_ram") + "Almacenamiento: " + resultado.getString("almacenamiento")
						+ "Precio: " + resultado.getString("precio") + "Garantia: "
						+ resultado.getString("fecha_garantia") + "Stock: " + resultado.getInt("stock");
			}
		} catch (SQLException e) {
			// Si hay un error al consultar los datos, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return "Error al consultar datos.";
		}
		// Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para
		// mostrar
		return datos.isEmpty() ? "No hay datos para mostrar" : datos;
	}

	// Metodo para ver los monitores de la base de datos
	public static String verdatosMonitores(Connection conexion) {
		String datos = "";
		String sql = "SELECT * FROM monitores"; // Consulta SQL para obtener los datos de los monitores

		try (Statement consulta = conexion.createStatement(); ResultSet resultado = consulta.executeQuery(sql)) {
			// Recorremos los resultados de la consulta
			while (resultado.next()) {
				// Concatenamos los datos de cada monitor en una cadena
				datos += "ID: " + "\n" + resultado.getInt("id") + "Modelo: " + resultado.getString("modelo")
						+ "Tamano Pantalla: " + resultado.getString("tamaño") + "Resolucion: "
						+ resultado.getString("resolucion") + "Precio: " + resultado.getString("precio") + "Garantia: "
						+ resultado.getString("fecha_garantia") + "Stock: " + resultado.getInt("stock");
			}
		} catch (SQLException e) {
			// Si hay un error al consultar los datos, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al consultar los datos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return "Error al consultar datos.";
		}
		// Devolvemos los datos obtenidos o un mensaje indicando que no hay datos para
		// mostrar
		return datos.isEmpty() ? "No hay datos para mostrar" : datos;
	}

	// Metodo para anadir productos al carrito
	public void agregarProductoCarrito() {
		// Opciones para seleccionar el tipo de producto que se quiere anadir al carrito
		String[] opciones = { "1. Laptops", "2. Sobremesa", "3. Workstation", "4. Smartphone", "5. Monitores" };
		// Mostramos un cuadro de dialogo para que el usuario elija una categoria
		String categoria = (String) JOptionPane.showInputDialog(null, "Selecciona una categoria:",
				"Anadir Producto al Carrito", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (categoria != null) {
			// Obtenemos el nombre de la tabla correspondiente a la categoria seleccionada
			String tabla = obtenerTabla(categoria);
			if (tabla != null) {
				// Pedimos al usuario que ingrese el ID del producto a anadir al carrito
				String idStr = JOptionPane.showInputDialog(null, "Introduce el ID del producto a anadir:");

				if (idStr != null) {
					// Anadimos el producto al carrito
					carrito.add("ID: " + idStr + " - Categoria: " + tabla);
					JOptionPane.showMessageDialog(null, "Producto anadido al carrito");
				}
			}
		} else {
			// Si no se selecciona ninguna opcion, mostramos un mensaje indicando que debe
			// seleccionar un tipo de producto
			JOptionPane.showMessageDialog(null, "Seleciona un tipo de producto");
		}
	}

	// Metodo para borrar productos del carrito
	public void borrarProductoCarrito() {
		// Verificamos si el carrito esta vacio
		if (carrito.isEmpty()) {
			JOptionPane.showMessageDialog(null, "El carrito esta vacio");
			return;
		}

		// Convertimos el carrito a un arreglo de cadenas para mostrarlo en un cuadro de
		// dialogo
		String[] itemsCarrito = carrito.toArray(new String[0]);
		// Mostramos un cuadro de dialogo para que el usuario elija un producto para
		// eliminar
		String producto = (String) JOptionPane.showInputDialog(null, "Selecciona un producto para eliminar:",
				"Borrar Producto del Carrito", JOptionPane.QUESTION_MESSAGE, null, itemsCarrito, itemsCarrito[0]);

		if (producto != null) {
			// Eliminamos el producto seleccionado del carrito
			carrito.remove(producto);
			JOptionPane.showMessageDialog(null, "Has eliminado el producto");
		} else {
			// Si no se selecciona ningun producto, mostramos un mensaje indicando que debe
			// elegir un producto
			JOptionPane.showMessageDialog(null, "Elige un producto");
		}
	}

	// Metodo para ver el carrito
	public void verCarrito() {
		if (carrito.isEmpty()) {
			// Si el carrito esta vacio, mostramos un mensaje indicando que esta vacio
			JOptionPane.showMessageDialog(null, "El carrito vacio");
		} else {
			// Si el carrito no esta vacio, construimos una cadena con el contenido del
			// carrito
			StringBuilder contenidoCarrito = new StringBuilder();
			for (String producto : carrito) {
				contenidoCarrito.append(producto).append(" \n ");
			}
			// Mostramos el contenido del carrito en un cuadro de dialogo
			JOptionPane.showMessageDialog(null, "Productos: \n " + contenidoCarrito);
		}
	}

	// Metodo para comprar y reducir el stock
	public void comprar() {
		if (carrito.isEmpty()) {
			// Si el carrito esta vacio, mostramos un mensaje indicando que esta vacio
			JOptionPane.showMessageDialog(null, "El carrito esta vacio");
			return;
		}

		try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", "")) {
			// Obtenemos el ID del usuario
			int usuarioId = obtenerUsuarioId(conexion, usuario);
			if (usuarioId == -1) {
				// Si el usuario no se encuentra, mostramos un mensaje de error
				JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
				return;
			}

			for (String item : carrito) {
				// Extraemos el ID y la tabla de cada elemento en el carrito
				String[] partes = item.split(" - ");
				if (partes.length < 2) {
					continue; // Evitamos el error si no se puede dividir correctamente
				}
				String idStr = partes[0].replace("ID: ", "").trim();
				String tabla = partes[1].replace("Categoria: ", "").trim();

				// Debugging: Verificar los valores de idStr y tabla
				System.out.println("ID: " + idStr);
				System.out.println("Tabla: " + tabla);

				// Reducir el stock
				String sql = "UPDATE " + tabla + " SET stock = stock - 1 WHERE id = ? AND stock > 0";

				try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
					int productoId = Integer.parseInt(idStr);
					pstmt.setInt(1, productoId);
					int rowsAffected = pstmt.executeUpdate();
					if (rowsAffected > 0) {
						// Registrar la compra
						int cantidad = 1; // Asumiendo que la cantidad siempre es 1 por cada item en el carrito
						String fechaCompra = new java.sql.Date(System.currentTimeMillis()).toString(); // Fecha actual
						int compraId = generarIdCompra(conexion); // Metodo para generar el ID de la compra
						registrarCompra(cantidad, compraId, fechaCompra, productoId, tabla, usuarioId);
						System.out.println("Producto comprado correctamente: " + idStr);
					} else {
						System.out.println("No se pudo comprar el producto con ID: " + idStr);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// Mostramos un mensaje de agradecimiento por la compra y vaciamos el carrito
			JOptionPane.showMessageDialog(null, "Gracias por su compra");
			carrito.clear(); // Vacia el carrito cuando se compra
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al realizar la compra: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ID incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Metodo para registrar las compras en la base de datos
	public void registrarCompra(int cantidad, int compra_id, String fecha_compra, int producto_id, String tipo_producto,
			int usuario_id) {
		try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", "")) {
			// Mensajes de depuracion
			System.out.println("Registrando compra:");
			System.out.println("cantidad = " + cantidad);
			System.out.println("compra_id = " + compra_id);
			System.out.println("fecha_compra = " + fecha_compra);
			System.out.println("producto_id = " + producto_id);
			System.out.println("tipo_producto = " + tipo_producto);
			System.out.println("usuario_id = " + usuario_id);

			// Registrar la compra
			String sql = "INSERT INTO compras (compra_id, usuario_id, cantidad, tipo_producto, producto_id, fecha_compra) VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
				pstmt.setInt(1, compra_id);
				pstmt.setInt(2, usuario_id);
				pstmt.setInt(3, cantidad);
				pstmt.setString(4, tipo_producto);
				pstmt.setInt(5, producto_id);
				pstmt.setString(6, fecha_compra);
				pstmt.executeUpdate();
				// Mostramos un mensaje indicando que la compra se ha registrado correctamente
				System.out.println("Compra registrada correctamente.");
			}
		} catch (SQLException e) {
			// Si hay un error al registrar la compra, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al registrar la compra: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Metodo para ver las compras realizadas por el usuario
	public void verCompras() {
		StringBuilder compras = new StringBuilder(); // Usamos StringBuilder para construir la lista de compras
		try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", "")) {
			// Obtenemos el ID del usuario
			int usuarioId = obtenerUsuarioId(conexion, usuario);
			if (usuarioId != -1) {
				String sql = "SELECT cantidad, compra_id, fecha_compra, producto_id, tipo_producto FROM compras WHERE usuario_id = ?";
				try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
					pstmt.setInt(1, usuarioId);
					try (ResultSet rs = pstmt.executeQuery()) {
						// Recorremos los resultados de la consulta
						while (rs.next()) {
							String categoria = rs.getString("tipo_producto");
							int producto_id = rs.getInt("producto_id");
							String fechaCompra = rs.getString("fecha_compra");

							// Anadimos los detalles de cada compra al StringBuilder
							compras.append("Categoria: ").append(categoria).append(", Producto ID: ")
									.append(producto_id).append(", Fecha de compra: ").append(fechaCompra)
									.append("\n\n");
						}
					}
				}
			} else {
				// Mostramos un mensaje si el usuario no se encuentra
				JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
			}
		} catch (SQLException e) {
			// Si hay un error al obtener las compras, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al obtener las compras: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		// Guardamos las compras en un archivo de texto
	    try (FileWriter writer = new FileWriter("compras.txt", true)) {
	        writer.write(compras.length() > 0 ? compras.toString() : "No has realizado compras.\n");
	        writer.write("\n"); // Añadimos una línea en blanco entre ejecuciones
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "Error al guardar las compras en el archivo: " + e.getMessage(), "Error",
	                JOptionPane.ERROR_MESSAGE);
	    }
		// Mostramos las compras o un mensaje indicando que no se han realizado compras
		JOptionPane.showMessageDialog(null, compras.length() > 0 ? compras.toString() : "No has realizado compras.");
	}

	// Metodo para generar un nuevo ID de compra
	public int generarIdCompra(Connection conexion) {
		try {
			String sql = "SELECT MAX(compra_id) AS max_id FROM compras";
			try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
				if (rs.next()) {
					return rs.getInt("max_id") + 1; // Retornamos el siguiente ID de compra
				}
			}
		} catch (SQLException e) {
			// Si hay un error al generar el ID de la compra, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al generar el ID de la compra: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return 1; // Retornamos 1 si no hay registros previos
	}

	// Metodo para dar el nombre de la tabla en la base de datos
	public String obtenerTabla(String categoria) {
		// Dependiendo de la categoria seleccionada, devolvemos el nombre de la tabla
		// correspondiente
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
			return null; // Retornamos null si la categoria no es valida
		}
	}

	// Metodo para obtener el ID del usuario
	public int obtenerUsuarioId(Connection conexion, String usuario) {
		try {
			String sql = "SELECT id FROM usuarios WHERE nombre = ?";
			try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
				pstmt.setString(1, usuario);
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("id"); // Retornamos el ID del usuario si se encuentra
					}
				}
			}
		} catch (SQLException e) {
			// Si hay un error al obtener el ID del usuario, mostramos un mensaje de error
			JOptionPane.showMessageDialog(null, "Error al obtener el ID del usuario: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return -1; // Retornamos -1 si no se encuentra el usuario
	}
}
