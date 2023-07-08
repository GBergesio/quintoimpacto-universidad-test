import {
  Button,
  MenuItem,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import React, { useEffect, useState } from "react";

export default function CursoAlumnoFilter({ refreshData }) {
  const [selectedCurso, setSelectedCurso] = useState("");
  const [dataCursos, setDataCursos] = useState([]);
  const [alumnosCurso, setAlumnosCurso] = useState([]);
  const handleApplyCursoFilter = async () => {
    if (selectedCurso) {
      const endpoint = `/alumnos/current/curso/${selectedCurso}`;
      await refreshData(endpoint, setAlumnosCurso);
    }
  };

  useEffect(() => {
    refreshData("/cursos/current", setDataCursos);
  }, []);

  const sortedCursos = [...dataCursos].sort((a, b) =>
  a.curso.nombre.localeCompare(b.curso.nombre)
);

  return (
    <>
      <Select
        value={selectedCurso}
        onChange={(e) => setSelectedCurso(e.target.value)}
        MenuProps={{
          PaperProps: {
            style: {
              maxHeight: "300px",
            },
          },
        }}
        sx={{ minWidth: 200 }}
      >
        {sortedCursos.map((c) => (
          <MenuItem key={c.curso.id} value={String(c.curso.id)}>
            {c.curso.nombre}
          </MenuItem>
        ))}
      </Select>
      <Button onClick={handleApplyCursoFilter}>Aplicar filtro</Button>
      {alumnosCurso.length > 0 ? (
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nombre</TableCell>
                <TableCell>Apellido</TableCell>
                <TableCell>DNI</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {alumnosCurso.map((alumno) => (
                <TableRow key={alumno.id}>
                  <TableCell>{alumno.nombre}</TableCell>
                  <TableCell>{alumno.apellido}</TableCell>
                  <TableCell>{alumno.dni}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography sx={{ mt: 4 }}>
          No se encontraron alumnos para el curso seleccionado.
        </Typography>
      )}
    </>
  );
}
