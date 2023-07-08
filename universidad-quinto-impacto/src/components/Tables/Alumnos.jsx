import React, { useEffect, useState } from "react";
import {
  Box,
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Typography,
  IconButton,
  TextField,
  Select,
  MenuItem,
} from "@mui/material";
import { useRouter } from "next/router";
import getData from "@/utils/axiosGet";
import CursoView from "@material-ui/icons/Book";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";
import FilterListIcon from "@material-ui/icons/FilterList";
import CursoAlumnoFilter from "../Filters/CursoAlumnoFilter";

const Alumnos = () => {
  //USE STATE
  const [dataAlumnos, setDataAlumnos] = useState([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [openDialogFilter, setOpenDialogFilter] = useState(false);
  const [selectedAlumno, setSelectedAlumno] = useState(null);
  const [filterValue, setFilterValue] = useState("");
  const [filteredAlumnos, setFilteredAlumnos] = useState(dataAlumnos);
  const [openCursosDialog, setOpenCursosDialog] = useState(false);
  const router = useRouter();

  //   const [open, setOpen] = useState(true);
  //   const [cursoSelected, setCursoSelected] = useState([]);
  //   const [dataProfesores, setProfesoresData] = useState([]);
  //   const [userLogged, setUserLogged] = useState([]);
  //   const [bodySnack, setBodySnack] = useState("");
  //   const [severity, setSeverity] = useState("");
  //   const [openSnack, setOpenSnack] = useState(false);
  //   const [searchValue, setSearchValue] = useState("");
  //   const [body, setBody] = useState("create");
  //   const [openCursoForm, setOpenCursoForm] = useState(false);
  //   const [filterValue, setFilterValue] = useState("todos");

  //   let userType = checkTypeUser(userLogged);

  //HANDLERS
  //   const handleOpenSnackBar = (body, severity) => {
  //     setOpenSnack(true);
  //     setBodySnack(body);
  //     setSeverity(severity);
  //   };

  //   const handleCloseSnackBar = (reason) => {
  //     if (reason === "clickaway") {
  //       return;
  //     }
  //     setOpenSnack(false);
  //   };

  //   const handleOpenCursoForm = (body) => {
  //     setOpenCursoForm(true);
  //     setBody(body);
  //   };

  //   const handleCloseCursoForm = () => {
  //     setOpenCursoForm(false);
  //     setBody("create");
  //     setCursoSelected([]);
  //   };

  //   const toggleDrawer = () => {
  //     setOpen(!open);
  //   };

  //   const handleFilterChange = (event) => {
  //     setFilterValue(event.target.value);
  //   };

  const goTo = (site) => {
    router.push(site, undefined, { shallow: true });
  };

  const refreshData = async (endpoint, setDataFunc) => {
    return await getData(endpoint, goTo).then((res) => {
      setDataFunc(res.dto);
    });
  };

  //USE EFFECT
  useEffect(() => {
    refreshData("/alumnos/current", setDataAlumnos);
  }, []);

  useEffect(() => {
    setFilteredAlumnos(dataAlumnos);
  }, [dataAlumnos]);

  //HANDLERS
  const handleFilterAlumnos = () => {
    const filtered = dataAlumnos.filter(
      (al) =>
        al.alumno.nombre.toLowerCase().includes(filterValue.toLowerCase()) ||
        al.alumno.apellido.toLowerCase().includes(filterValue.toLowerCase()) ||
        al.alumno.dni.toLowerCase().includes(filterValue.toLowerCase())
    );
    setFilteredAlumnos(filtered);
    handleCloseDialogFilter();
  };

  const handleCleanFilterAlumnos = () => {
    setFilteredAlumnos(dataAlumnos);
    handleCloseDialogFilter();
  };

  const handleFilterChange = (event) => {
    setFilterValue(event.target.value);
  };

  const handleOpenCursosDialog = () => {
    setOpenCursosDialog(true);
  };

  const handleCloseCursosDialog = () => {
    setOpenCursosDialog(false);
  };

  const handleDeleteAlumno = (alumnoId) => {
    console.log("Eliminar alumno con ID:", alumnoId);
  };

  const handleEditAlumno = (alumnoId) => {
    console.log("Editar alumno con ID:", alumnoId);
  };

  const handleOpenDialog = (alumno) => {
    setSelectedAlumno(alumno);
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  const handleOpenDialogFilter = () => {
    setOpenDialogFilter(true);
  };

  const handleCloseDialogFilter = () => {
    setOpenDialogFilter(false);
  };

  return (
    <Box sx={{ mt: 10, ml: 4, mr: 4 }}>
      <Box sx={{ display: "flex", alignItems: "center", mb: 2 }}>
        <h1>Tabla de Alumnos</h1>
        <IconButton color="primary" onClick={handleOpenDialogFilter}>
          <FilterListIcon />
        </IconButton>
        <Button onClick={() => handleOpenCursosDialog()}>
          Filtrar por Curso
        </Button>
      </Box>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Nombre</TableCell>
              <TableCell>Apellido</TableCell>
              <TableCell>DNI</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>Teléfono</TableCell>
              <TableCell>Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredAlumnos.length > 0 ? (
              filteredAlumnos.map((al) => (
                <TableRow key={al.alumno.id}>
                  <TableCell>{al.alumno.nombre}</TableCell>
                  <TableCell>{al.alumno.apellido}</TableCell>
                  <TableCell>{al.alumno.dni}</TableCell>
                  <TableCell>{al.alumno.email}</TableCell>
                  <TableCell>{al.alumno.celular}</TableCell>
                  <TableCell>
                    <IconButton
                      color="primary"
                      onClick={() => handleOpenDialog(al)}
                    >
                      <CursoView />
                    </IconButton>
                    <IconButton
                      color="primary"
                      onClick={() => handleEditAlumno(al.alumno.id)}
                    >
                      <EditIcon />
                    </IconButton>
                    <IconButton
                      color="error"
                      onClick={() => handleDeleteAlumno(al.alumno.id)}
                    >
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={6}>No se encontraron alumnos.</TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogTitle>Cursos del alumno</DialogTitle>
        <DialogContent>
          {selectedAlumno && selectedAlumno.cursos.length > 0 ? (
            <TableContainer>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Nombre del Curso</TableCell>
                    <TableCell>Turno</TableCell>
                    <TableCell>Profesor</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {selectedAlumno.cursos.map((curso) => (
                    <TableRow key={curso.id}>
                      <TableCell>{curso.nombre}</TableCell>
                      <TableCell>{curso.turno}</TableCell>
                      <TableCell>
                        {curso.profesor
                          ? `${curso.profesor.nombre} ${curso.profesor.apellido}`
                          : "-"}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          ) : (
            <Typography>No se encontraron cursos para este alumno.</Typography>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Cerrar</Button>
        </DialogActions>
      </Dialog>

      <Dialog open={openDialogFilter} onClose={handleCloseDialogFilter}>
        <DialogTitle sx={{ mt: 1}}>Filtrar Alumnos</DialogTitle>
        <DialogContent>
          <TextField
            label="Filtrar por nombre/apellido/DNI"
            value={filterValue}
            onChange={handleFilterChange}
            fullWidth
            sx={{mt:3}}
          />
          <Button
            onClick={handleFilterAlumnos}
            variant="contained"
            sx={{ mr: 2, mt: 2 }}
          >
            Aplicar Filtro
          </Button>
          <Button
            onClick={handleCleanFilterAlumnos}
            variant="contained"
            sx={{ mr: 2, mt: 2 }}
          >
            Limpiar Filtro
          </Button>
        </DialogContent>
      </Dialog>
      <Dialog open={openCursosDialog} onClose={handleCloseCursosDialog}>
        <DialogTitle>Seleccione un curso</DialogTitle>
        <DialogContent>
          <CursoAlumnoFilter refreshData={refreshData} />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseCursosDialog}>Cerrar</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Alumnos;
