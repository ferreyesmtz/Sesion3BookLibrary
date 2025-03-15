import { useState, useEffect } from "react";
import axios from "axios";
import { TextField, Card, CardContent, Typography, Container, Box } from "@mui/material";

function BookGrid() {
  const [books, setBooks] = useState([]);
  const [search, setSearch] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8080/api/books")
      .then(response => {
        console.log("Libros obtenidos:", response.data); 
        setBooks(response.data);
      })
      .catch(error => console.error("Error fetching books:", error));
  }, []);

  // Filtrar libros según la búsqueda en cualquier campo
  const filteredBooks = books.filter(book =>
    Object.values(book).some(value =>
      value.toString().toLowerCase().includes(search.toLowerCase())
    )
  );

  return (
    <Container>
      {/* Input de búsqueda con estilos llamativos */}
      <TextField
        label="Buscar libros..."
        variant="outlined"
        fullWidth
        margin="normal"
        onChange={(e) => setSearch(e.target.value)}
        sx={{
          backgroundColor: '#f7f7f7', 
          borderRadius: 2,
          boxShadow: 3, 
          "& .MuiInputBase-root": {
            color: "#333"
          },
          "& .MuiOutlinedInput-root": {
            borderRadius: 2,
            "& fieldset": {
              borderColor: "#00aaff",
            },
            "&:hover fieldset": {
              borderColor: "#0077cc",
            },
          }
        }}
      />

      {/* Grid con libros */}
      <Box
        display="grid"
        gridTemplateColumns="repeat(auto-fill, minmax(250px, 1fr))"
        gap={2}
        mt={2}
        sx={{
          "& > div": {
            transition: "transform 0.3s ease, box-shadow 0.3s ease",
            "&:hover": {
              transform: "scale(1.05)",
              boxShadow: "0px 8px 16px rgba(0, 0, 0, 0.2)"
            },
            borderRadius: 4
          }
        }}
      >
        {filteredBooks.map((book) => (
          <Card key={book.id} sx={{
            padding: 3,
            textAlign: "center",
            backgroundColor: "#ffffff",
            boxShadow: 2,
            transition: "all 0.3s ease",
            "&:hover": {
              boxShadow: 4,
              transform: "scale(1.03)",
              cursor: "pointer",
            }
          }}>
            <CardContent>
              <Typography variant="h6" sx={{ fontWeight: "bold", color: "#333", mb: 1 }}>
                {book.title}
              </Typography>
              <Typography color="text.secondary" sx={{ mb: 1 }}>{book.author}</Typography>
              <Typography variant="body2" sx={{ color: "#555", mb: 0.5 }}>ISBN: {book.isbn}</Typography>
              <Typography variant="body2" sx={{ color: "#555", mb: 0.5 }}>Stock: {book.stock}</Typography>
              <Typography variant="body2" sx={{ color: "#28a745" }}>Available Stock: {book.availableStock}</Typography>
            </CardContent>
          </Card>
        ))}
      </Box>
    </Container>
  );
}

export default BookGrid;
