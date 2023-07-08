import {
  Box,
  Button,
  Dialog,
  DialogContent,
  DialogTitle,
  Divider,
  Grid,
  TextField,
  Typography,
} from "@mui/material";
import React, { useState } from "react";
import Scrollbar from "react-smooth-scrollbar";

export default function ListaAlumnosDialog({ open, handleClose, alumnos }) {
  const [filtroNombre, setFiltroNombre] = useState("");
  const [filtroApellido, setFiltroApellido] = useState("");
  const [filtroDNI, setFiltroDNI] = useState("");

  const filtrarAlumnos = (alumnos) => {
    return alumnos.filter((alumno) => {
      const nombreCumpleFiltro =
        alumno.nombre.toLowerCase().includes(filtroNombre.toLowerCase()) ||
        alumno.apellido.toLowerCase().includes(filtroNombre.toLowerCase());
      const apellidoCumpleFiltro = alumno.apellido
        .toLowerCase()
        .includes(filtroApellido.toLowerCase());
      const dniCumpleFiltro = alumno.dni.includes(filtroDNI);

      return nombreCumpleFiltro && apellidoCumpleFiltro && dniCumpleFiltro;
    });
  };

  const handleResetFilters = () => {
    setFiltroNombre("");
    setFiltroApellido("");
    setFiltroDNI("");
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Lista de Alumnos</DialogTitle>
      <DialogContent>
        <Box component="form" noValidate autoComplete="off" sx={{ mb: 2 }}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6} md={4}>
              <TextField
                label="Nombre"
                value={filtroNombre}
                onChange={(e) => setFiltroNombre(e.target.value)}
                fullWidth
                
              />
            </Grid>
            <Grid item xs={12} sm={6} md={4}>
              <TextField
                label="Apellido"
                value={filtroApellido}
                onChange={(e) => setFiltroApellido(e.target.value)}
                fullWidth
                
              />
            </Grid>
            <Grid item xs={12} sm={6} md={4}>
              <TextField
                label="DNI"
                value={filtroDNI}
                onChange={(e) => setFiltroDNI(e.target.value)}
                fullWidth
                s
              />
            </Grid>
            <Grid item xs={12} sm={6} md={4}>
              <Button variant="outlined" onClick={handleResetFilters}>
                Limpiar filtros
              </Button>
            </Grid>
          </Grid>
        </Box>
        <Scrollbar style={{ maxHeight: "200px" }}>
          {filtrarAlumnos(alumnos).length === 0 ? (
            <Typography>No se encontraron alumnos.</Typography>
          ) : (
            filtrarAlumnos(alumnos).map((alumno) => (
              <div key={alumno.id}>
                <Typography>
                  <strong>Nombre:</strong> {alumno.nombre} {alumno.apellido}
                </Typography>
                <Typography>
                  <strong>DNI:</strong> {alumno.dni}
                </Typography>
                <Typography>
                  <strong>Email:</strong> {alumno.email}
                </Typography>
                <Divider sx={{ mt: 1, mb: 1 }} />
              </div>
            ))
          )}
        </Scrollbar>
      </DialogContent>
    </Dialog>
  );
}
