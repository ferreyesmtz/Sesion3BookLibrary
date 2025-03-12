import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {

    // Clase Libro
    static class Libro {
        private String titulo;
        private String autor;
        private String isbn;
        private int totalCopias;
        private int copiasDisponibles;

        public Libro(String titulo, String autor, String isbn, int totalCopias) {
            this.titulo = titulo;
            this.autor = autor;
            this.isbn = isbn;
            this.totalCopias = totalCopias;
            this.copiasDisponibles = totalCopias;
        }

        public String getTitulo() { return titulo; }
        public String getAutor() { return autor; }
        public String getIsbn() { return isbn; }
        public int getTotalCopias() { return totalCopias; }
        public int getCopiasDisponibles() { return copiasDisponibles; }
        public void setCopiasDisponibles(int copiasDisponibles) {
            this.copiasDisponibles = copiasDisponibles;
        }

        @Override
        public String toString() {
            return "Titulo: " + titulo + ", Autor: " + autor + ", ISBN: " + isbn +
                   ", Total de Copias: " + totalCopias + ", Disponibles: " + copiasDisponibles;
        }

        
    }

    // Clase Usuario (Patron)
    static class Usuario {
        private String nombre;
        private String id;
        private String contacto;
        private List<Prestamo> historialPrestamos;

        public Usuario(String nombre, String id, String contacto) {
            this.nombre = nombre;
            this.id = id;
            this.contacto = contacto;
        }

        public String getNombre() { return nombre; }
        public String getId() { return id; }
        public String getContacto() { return contacto; }

        

        // Obtener historial de préstamos
        public List<Prestamo> obtenerHistorialPrestamos() {
            return historialPrestamos;
        }

        @Override
        public String toString() {
            return "Nombre: " + nombre + ", ID: " + id + ", Contacto: " + contacto;
        }
    }

    

    // Clase Registro de Prestamo
    static class RegistroPrestamo {
        private Usuario usuario;
        private Libro libro;

        public RegistroPrestamo(Usuario usuario, Libro libro) {
            this.usuario = usuario;
            this.libro = libro;
        }

        public Usuario getUsuario() { return usuario; }
        public Libro getLibro() { return libro; }
    }

    // Clase Biblioteca: contiene la lista de libros, usuarios y registros de prestamo
    static class Biblioteca {
        private List<Libro> libros;
        private List<Usuario> usuarios;
        private List<RegistroPrestamo> registrosPrestamo;

        public Biblioteca() {
            libros = new ArrayList<>();
            usuarios = new ArrayList<>();
            registrosPrestamo = new ArrayList<>();
        }

        // Gestion de Libros
        public void agregarLibro(Libro libro) {
            libros.add(libro);
            System.out.println("Libro agregado exitosamente.");
        }

        public void removerLibro(String isbn) {
            Libro libro = buscarLibroPorISBN(isbn);
            if (libro != null) {
                libros.remove(libro);
                System.out.println("Libro removido exitosamente.");
            } else {
                System.out.println("Libro no encontrado.");
            }
        }

        public void mostrarLibros() {
            if (libros.isEmpty()) {
                System.out.println("No hay libros en la biblioteca.");
            } else {
                System.out.println("Lista de Libros:");
                for (Libro libro : libros) {
                    System.out.println(libro);
                }
            }
        }

        // Gestion de Usuarios
        public void registrarUsuario(Usuario usuario) {
            usuarios.add(usuario);
            System.out.println("Usuario registrado exitosamente.");
        }

        public void mostrarUsuarios() {
            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
            } else {
                System.out.println("Lista de Usuarios:");
                for (Usuario usuario : usuarios) {
                    System.out.println(usuario);
                }
            }
        }

        // Gestion de Prestamos
        public void prestarLibro(String usuarioId, String isbn) {
            Usuario usuario = buscarUsuarioPorId(usuarioId);
            if (usuario == null) {
                System.out.println("Usuario no encontrado.");
                return;
            }
            Libro libro = buscarLibroPorISBN(isbn);
            if (libro == null) {
                System.out.println("Libro no encontrado.");
                return;
            }
            if (libro.getCopiasDisponibles() <= 0) {
                System.out.println("No hay copias disponibles para este libro.");
                return;
            }
            // Verificar si ya se ha prestado el libro al usuario
            for (RegistroPrestamo registro : registrosPrestamo) {
                if (registro.getUsuario().getId().equals(usuarioId) && registro.getLibro().getIsbn().equals(isbn)) {
                    System.out.println("El usuario ya tiene prestado este libro.");
                    return;
                }
            }
            // Realizar prestamo
            libro.setCopiasDisponibles(libro.getCopiasDisponibles() - 1);
            RegistroPrestamo registro = new RegistroPrestamo(usuario, libro);
            registrosPrestamo.add(registro);
            System.out.println("Libro prestado exitosamente.");
        }

        public void devolverLibro(String usuarioId, String isbn) {
            RegistroPrestamo registroADevolver = null;
            for (RegistroPrestamo registro : registrosPrestamo) {
                if (registro.getUsuario().getId().equals(usuarioId) && registro.getLibro().getIsbn().equals(isbn)) {
                    registroADevolver = registro;
                    break;
                }
            }
            if (registroADevolver == null) {
                System.out.println("No se encontro registro de prestamo para este usuario y libro.");
                return;
            }
            // Devolver el libro
            Libro libro = registroADevolver.getLibro();
            libro.setCopiasDisponibles(libro.getCopiasDisponibles() + 1);
            registrosPrestamo.remove(registroADevolver);
            System.out.println("Libro devuelto exitosamente.");
        }

        // Metodos auxiliares
        private Libro buscarLibroPorISBN(String isbn) {
            for (Libro libro : libros) {
                if (libro.getIsbn().equals(isbn)) {
                    return libro;
                }
            }
            return null;
        }

        public Libro buscarLibroPorTitulo(String titulo) {
            for (Libro libro : libros) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    return libro;
                }
            }
            return null; // No encontrado
        }

        public List<Libro> buscarLibroPorAutor(String autor) {
            List<Libro> librosEncontrados = new ArrayList<>();
            for (Libro libro : libros) {
                if (libro.getAutor().equalsIgnoreCase(autor)) {
                    librosEncontrados.add(libro);
                }
            }
            return librosEncontrados;
        }

        private Usuario buscarUsuarioPorId(String id) {
            for (Usuario usuario : usuarios) {
                if (usuario.getId().equals(id)) {
                    return usuario;
                }
            }
            return null;
        }

        public void exportarLibrosJSON(String archivo) {
        try (FileWriter writer = new FileWriter(archivo)) {
            Gson gson = new Gson();
            gson.toJson(libros, writer);
            System.out.println("Exportación exitosa a " + archivo);
        } catch (IOException e) {
            System.out.println("Error al exportar: " + e.getMessage());
        }
        }
    }

    // Metodo principal con menu interactivo
    import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorPrestamos gestorPrestamos = new GestorPrestamos();
        
        int opcion;
        do {
            System.out.println("\n=== Sistema de Gestión de Biblioteca ===");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Remover Libro");
            System.out.println("3. Mostrar Libros");
            System.out.println("4. Registrar Usuario");
            System.out.println("5. Mostrar Usuarios");
            System.out.println("6. Prestar Libro");
            System.out.println("7. Devolver Libro");
            System.out.println("8. Buscar Libro por Título");
            System.out.println("9. Buscar Libros por Autor");
            System.out.println("10. Renovar Préstamo");
            System.out.println("11. Calcular Multa por Retraso");
            System.out.println("12. Exportar Libros a JSON");
            System.out.println("13. Exportar Usuarios a CSV");
            System.out.println("14. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Consumir nueva línea

            switch(opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = sc.nextLine();
                    System.out.print("Ingrese el autor del libro: ");
                    String autor = sc.nextLine();
                    System.out.print("Ingrese el ISBN del libro: ");
                    String isbn = sc.nextLine();
                    System.out.print("Ingrese el número de copias: ");
                    int copias = sc.nextInt();
                    sc.nextLine();
                    Libro libro = new Libro(titulo, autor, isbn, copias);
                    biblioteca.agregarLibro(libro);
                    break;
                case 2:
                    System.out.print("Ingrese el ISBN del libro a remover: ");
                    String isbnRemover = sc.nextLine();
                    biblioteca.removerLibro(isbnRemover);
                    break;
                case 3:
                    biblioteca.mostrarLibros();
                    break;
                case 4:
                    System.out.print("Ingrese el nombre del usuario: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese el ID del usuario: ");
                    String idUsuario = sc.nextLine();
                    System.out.print("Ingrese el contacto del usuario: ");
                    String contacto = sc.nextLine();
                    Usuario usuario = new Usuario(nombre, idUsuario, contacto);
                    gestorUsuarios.registrarUsuario(usuario);
                    break;
                case 5:
                    gestorUsuarios.mostrarUsuarios();
                    break;
                case 6:
                    System.out.print("Ingrese el ID del usuario: ");
                    String idUsuarioPrestar = sc.nextLine();
                    System.out.print("Ingrese el ISBN del libro a prestar: ");
                    String isbnPrestar = sc.nextLine();
                    gestorPrestamos.registrarPrestamo(idUsuarioPrestar, isbnPrestar);
                    break;
                case 7:
                    System.out.print("Ingrese el ID del usuario: ");
                    String idUsuarioDevolver = sc.nextLine();
                    System.out.print("Ingrese el ISBN del libro a devolver: ");
                    String isbnDevolver = sc.nextLine();
                    gestorPrestamos.devolverLibro(idUsuarioDevolver, isbnDevolver);
                    break;
                case 8:
                    System.out.print("Ingrese el título del libro a buscar: ");
                    String tituloBuscar = sc.nextLine();
                    System.out.println(biblioteca.buscarLibroPorTitulo(tituloBuscar));
                    break;
                case 9:
                    System.out.print("Ingrese el autor de los libros a buscar: ");
                    String autorBuscar = sc.nextLine();
                    System.out.println(biblioteca.buscarLibroPorAutor(autorBuscar));
                    break;
                case 10:
                    System.out.print("Ingrese el ID del usuario: ");
                    String idRenovar = sc.nextLine();
                    System.out.print("Ingrese el ISBN del libro a renovar: ");
                    String isbnRenovar = sc.nextLine();
                    System.out.print("Ingrese el número de días extra: ");
                    int diasExtra = sc.nextInt();
                    sc.nextLine();
                    if (gestorPrestamos.renovarPrestamo(idRenovar, isbnRenovar, diasExtra)) {
                        System.out.println("📅 Préstamo renovado correctamente.");
                    } else {
                        System.out.println("❌ No se pudo renovar el préstamo.");
                    }
                    break;
                case 11:
                    System.out.print("Ingrese el ID del usuario: ");
                    String idMulta = sc.nextLine();
                    System.out.print("Ingrese el ISBN del libro: ");
                    String isbnMulta = sc.nextLine();
                    double multa = gestorPrestamos.calcularMulta(idMulta, isbnMulta);
                    System.out.println("💰 Multa calculada: $" + multa);
                    break;
                case 12:
                    biblioteca.exportarLibrosJSON("libros.json");
                    System.out.println("📂 Libros exportados correctamente a libros.json.");
                    break;
                case 13:
                    gestorUsuarios.exportarUsuariosCSV("usuarios.csv");
                    System.out.println("📂 Usuarios exportados correctamente a usuarios.csv.");
                    break;
                case 14:
                    System.out.println("👋 Saliendo del sistema. Hasta luego!");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        } while(opcion != 14);
        
        sc.close();
    }
}

}
