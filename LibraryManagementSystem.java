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

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getAutor() { return autor; }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public String getIsbn() { return isbn; }
        
        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public int getTotalCopias() { return totalCopias; }

        public void setTotalCopias(int copias) {
            this.totalCopias = copias;
        }

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

        public Usuario(String nombre, String id, String contacto) {
            this.nombre = nombre;
            this.id = id;
            this.contacto = contacto;
        }

        public String getNombre() { return nombre; }
        public String getId() { return id; }
        public String getContacto() { return contacto; }

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

        public void editarLibro(Libro libro, String titulo, String autor, int copias) {
            
            if (libro != null) {

                libro.setTitulo(titulo);
                libro.setAutor(autor);
                libro.setTotalCopias(copias);

            } else {
                System.out.println("Libro no encontrado");
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

        private Libro buscarLibroPorTitulo(String titulo) {
            for (Libro libro : libros) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    return libro;
                }
            }
            return null;
        }

        private List<Libro> buscarLibroPorAutor(String autor) {
            List<Libro> librosPorAutor = new ArrayList<>();
            for (Libro libro : libros) {
                System.err.println(libro.getAutor());
                if (libro.getAutor().equalsIgnoreCase(autor)) {
                    librosPorAutor.add(libro);
                }
            }
            return librosPorAutor;
        }

        private Usuario buscarUsuarioPorId(String id) {
            for (Usuario usuario : usuarios) {
                if (usuario.getId().equals(id)) {
                    return usuario;
                }
            }
            return null;
        }
    }

    // Metodo principal con menu interactivo
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        int opcion;
        do {
            System.out.println("Continuar...");
            sc.nextLine();
            System.out.println("\n=== Sistema de Gestion de Biblioteca ===");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Remover Libro");
            System.out.println("3. Mostrar Libros");
            System.out.println("4. Editar Libro");
            System.out.println("5. Registrar Usuario");
            System.out.println("6. Mostrar Usuarios");
            System.out.println("7. Prestar Libro");
            System.out.println("8. Devolver Libro");
            System.out.println("9. Buscar Libro por Titulo");
            System.out.println("10. Buscar Libro por Autor");
            System.out.println("11. Salir");
            System.out.print("Ingrese su opcion: ");
            opcion = sc.nextInt();
            sc.nextLine(); // consumir nueva linea

            switch(opcion) {
                case 1:
                    System.out.print("Ingrese el titulo del libro: ");
                    String titulo = sc.nextLine();
                    System.out.print("Ingrese el autor del libro: ");
                    String autor = sc.nextLine();
                    System.out.print("Ingrese el ISBN del libro: ");
                    String isbn = sc.nextLine();
                    System.out.print("Ingrese el numero de copias: ");
                    int copias = sc.nextInt();
                    sc.nextLine(); // consumir nueva linea
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
                    System.out.print("Ingrese el ISBN del libro a modificar: ");
                    isbn = sc.nextLine();
                    Libro edicionLibro = biblioteca.buscarLibroPorISBN(isbn);
                    
                    if (edicionLibro != null) {
                        System.out.print("Titulo anterior " + edicionLibro.getTitulo());
                        System.out.print(" Titulo nuevo (Enter si no desea modificar): ");
                        String tituloNuevo = sc.nextLine();
                        if (tituloNuevo.equals("")) {
                            tituloNuevo = edicionLibro.getTitulo();
                        }

                        System.out.print("Autor anterior " + edicionLibro.getAutor());
                        System.out.print(" Autor nuevo (Enter si no desea modificar): ");
                        String autorNuevo = sc.nextLine();
                        if (autorNuevo.equals("")) {
                            autorNuevo = edicionLibro.getAutor();
                        }

                        System.out.print("Copias anteriores " );
                        System.out.print(" Copias nuevas (0 si no desea modificar): ");
                        int copiasNuevo = sc.nextInt();
                        if (copiasNuevo == 0) {
                            copiasNuevo = edicionLibro.getCopiasDisponibles();
                        }
                        biblioteca.editarLibro(edicionLibro, tituloNuevo, autorNuevo, copiasNuevo);
                    } else {
                        System.err.println("Libro no encontrado");
                    };
                    break;

                case 5:
                    System.out.print("Ingrese el nombre del usuario: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese el ID del usuario: ");
                    String id = sc.nextLine();
                    System.out.print("Ingrese el contacto del usuario: ");
                    String contacto = sc.nextLine();
                    Usuario usuario = new Usuario(nombre, id, contacto);
                    biblioteca.registrarUsuario(usuario);
                    break;
                case 6:
                    biblioteca.mostrarUsuarios();
                    break;
                case 7:
                    System.out.print("Ingrese el ID del usuario: ");
                    String idUsuario = sc.nextLine();
                    System.out.print("Ingrese el ISBN del libro a prestar: ");
                    String isbnPrestar = sc.nextLine();
                    biblioteca.prestarLibro(idUsuario, isbnPrestar);
                    break;
                case 8:
                    System.out.print("Ingrese el ID del usuario: ");
                    String idUsuarioDevolver = sc.nextLine();
                    System.out.print("Ingrese el ISBN del libro a devolver: ");
                    String isbnDevolver = sc.nextLine();
                    biblioteca.devolverLibro(idUsuarioDevolver, isbnDevolver);
                    break;
                case 9:
                    System.out.print("Ingrese el titulo del libro a buscar: ");
                    String tituloBuscar = sc.nextLine();
                    Libro libroEncontrado = biblioteca.buscarLibroPorTitulo(tituloBuscar);
                    if (libroEncontrado != null) {
                        System.out.println("Libro encontrado: " + libroEncontrado);
                    } else {
                        System.out.println("Libro no encontrado.");
                    }
                    break;
                case 10:
                    System.out.print("Ingrese el autor del libro a buscar: ");
                    String autorBuscar = sc.nextLine();
                    List<Libro> librosEncontrados = biblioteca.buscarLibroPorAutor(autorBuscar);
                    if (!librosEncontrados.isEmpty()) {
                        System.out.println("Libros encontrados:");
                        for (Libro libroAutor : librosEncontrados) {
                            System.out.println(libroAutor);
                        }
                    } else {
                        System.out.println("No se encontraron libros para este autor.");
                    }
                    break;
                case 11:
                    System.out.println("Saliendo del sistema. Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while(opcion != 11);
        sc.close();
    }
}
