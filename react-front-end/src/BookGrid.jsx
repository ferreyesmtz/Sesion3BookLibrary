import { useState, useEffect } from "react";
import axios from "axios";
import { TextField, Container, Box, Card, CardContent, Typography, Button, Modal, Paper } from "@mui/material";

function BookGrid() {
  const [books, setBooks] = useState([]);
  const [search, setSearch] = useState("");
  const [open, setOpen] = useState(false);
  const [newBook, setNewBook] = useState({ title: "", author: "", genre: "", publishedDate: "" , isbn: "", stock: "" });

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = () => {
    axios.get("http://localhost:8080/api/books")
      .then(response => {
        // Convertir "available" a booleano si es necesario
        const booksWithBooleanAvailability = response.data.map(book => ({
          ...book,
          available: book.available === 1  // Convierte a booleano
        }));
        setBooks(booksWithBooleanAvailability);
      })
      .catch(error => console.error("Error fetching books:", error));
  };
  

  const handleAddBook = () => {
    if (!newBook.title || !newBook.author || !newBook.genre || !newBook.publishedDate || !newBook.isbn || !newBook.stock) {
      alert('Todos los campos son obligatorios');
      return;
    }
  
    axios.post("http://localhost:8080/api/books", newBook)
      .then(() => {
        fetchBooks();
        setOpen(false);
      })
      .catch(error => console.error("Error adding book:", error));
  };
  

  const handleDeleteBook = (id) => {
    axios.delete(`http://localhost:8080/api/books/${id}`)
      .then(() => fetchBooks())
      .catch(error => console.error("Error deleting book:", error));
  };

  const filteredBooks = books.filter(book =>
    Object.values(book).some(value =>
      value.toString().toLowerCase().includes(search.toLowerCase())
    )
  );

  return (
    <Container>
      <TextField
        label="Buscar libros..."
        variant="outlined"
        fullWidth
        margin="normal"
        onChange={(e) => setSearch(e.target.value)}
      />

      <Button variant="contained" color="primary" onClick={() => setOpen(true)} sx={{ mb: 2 }}>
        Añadir Libro
      </Button>

      <Box display="grid" gridTemplateColumns="repeat(auto-fill, minmax(250px, 1fr))" gap={2}>
        {filteredBooks.map((book) => (
          <FlipCard key={book.id} book={book} onDelete={handleDeleteBook} />
        ))}
      </Box>

      {/* Modal para añadir libros */}
      <Modal open={open} onClose={() => setOpen(false)}>
        <Paper sx={{ p: 3, width: 300, margin: "auto", mt: 5 }}>
          <Typography variant="h6">Añadir Libro</Typography>
          <TextField label="Título" fullWidth margin="dense" onChange={(e) => setNewBook({ ...newBook, title: e.target.value })} />
          <TextField label="Autor" fullWidth margin="dense" onChange={(e) => setNewBook({ ...newBook, author: e.target.value })} />
          <TextField label="Género" fullWidth margin="dense" onChange={(e) => setNewBook({ ...newBook, genre: e.target.value })} />
          <TextField label="Año de Publicación" fullWidth margin="dense" onChange={(e) => setNewBook({ ...newBook, publishedDate: e.target.value })} />
          <TextField label="ISBN" fullWidth margin="dense" onChange={(e) => setNewBook({ ...newBook, isbn: e.target.value })} />
          <TextField label="Copias Disponibles" type="number" fullWidth margin="dense" onChange={(e) => setNewBook({ ...newBook,  stock: parseInt(e.target.value, 10) })} />
          <Box display="flex" justifyContent="space-between" mt={2}>
            <Button variant="contained" onClick={handleAddBook}>Añadir</Button>
            <Button variant="outlined" onClick={() => setOpen(false)}>Cancelar</Button>
          </Box>
        </Paper>
      </Modal>
    </Container>
  );
}

// Componente de tarjeta con flip y botón de eliminar
function FlipCard({ book, onDelete }) {
  

  return (
    <Box className={`flip-card-${book.stock > 1 ? 'available' : 'unavailable'}`}>
      <Box className="flip-card-inner">
        <Card className="flip-card-front">
          <CardContent>
            <Typography variant="h6" sx={{ fontWeight: "bold" }}>{book.title}</Typography>
            <Typography color="text.secondary">{book.author}</Typography>
          </CardContent>
        </Card>

        <Card className="flip-card-back">
          <CardContent>
            <Typography variant="h6" sx={{ fontWeight: "bold" }}>{book.title}</Typography>
            <Typography color="text.secondary">{book.author}</Typography>
            <Typography variant="body2">Genre: {book.genre}</Typography>
            <Typography variant="body2">Date Published: {book.publishedDate}</Typography>
            <Typography variant="body2">ISBN: {book.isbn}</Typography>
            <Typography variant="body2">Stock: {book.stock}</Typography>
            <Button variant="outlined" color="error" size="small" sx={{ mt: 1 }} onClick={(e) => {
              e.stopPropagation();
              onDelete(book.id);
            }}>
              Remover
            </Button>
          </CardContent>
        </Card>
      </Box>
    </Box>
  );
}

export default BookGrid;
