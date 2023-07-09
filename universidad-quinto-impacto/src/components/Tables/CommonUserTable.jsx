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
  Tooltip,
  Grid,
} from "@mui/material";
import { useRouter } from "next/router";
import getData from "@/utils/axiosGet";
import CursoView from "@material-ui/icons/Book";
import EditIcon from "@material-ui/icons/Edit";
import FilterListIcon from "@material-ui/icons/FilterList";
import CursoAlumnoFilter from "../Filters/CursoAlumnoFilter";
import SimpleDialog from "../Dialog/SimpleDialog";
import CommonUsuarioForm from "../Forms/CommonUsuarioForm";
import { Warning } from "@material-ui/icons";
import EstadoSelect from "../Forms/EstadoSelect";
import UtilSnackBar from "../Snackbar";

const CommonUserTable = ({
  uris,
  setUserSelected,
  initialValues,
  isDeleted,
  type,
}) => {
  //USE STATE
  const [dataUsers, setDataUsers] = useState([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [openDialogFilter, setOpenDialogFilter] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);
  const [filterValue, setFilterValue] = useState("todos");
  const [filteredUsers, setFilteredUsers] = useState(dataUsers);
  const [openCursosDialog, setOpenCursosDialog] = useState(false);
  const [openUserForm, setOpenUserForm] = useState(false);
  const [body, setBody] = useState("create");
  const [bodySnack, setBodySnack] = useState("");
  const [severity, setSeverity] = useState("");
  const [openSnack, setOpenSnack] = useState(false);

  const router = useRouter();

  const handleFilterChange = (event) => {
    setFilterValue(event.target.value);
  };

  //HANDLERS
  const handleOpenSnackBar = (body, severity) => {
    setOpenSnack(true);
    setBodySnack(body);
    setSeverity(severity);
  };

  const handleCloseSnackBar = (reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpenSnack(false);
  };

  const handleOpenUserForm = (body) => {
    setOpenUserForm(true);
    setBody(body);
  };

  const handleCloseUserForm = () => {
    setOpenUserForm(false);
    setBody("create");
    setUserSelected([]);
  };

  const goTo = (site) => {
    router.push(site, undefined, { shallow: true });
  };

  const refreshData = async (endpoint, setDataFunc) => {
    return await getData(endpoint, goTo).then((res) => {
      // setDataFunc(res.dto);
      setDataFunc(res && res.dto ? res.dto : []);
    });
  };

  //USE EFFECT
  useEffect(() => {
    refreshData(uris[0], setDataUsers);
  }, []);

  useEffect(() => {
    setFilteredUsers(dataUsers);
  }, [dataUsers]);

  //HANDLERS
  const handleFilterUsers = () => {
    const filtered = dataUsers.filter(
      (u) =>
        u[type].nombre.toLowerCase().includes(filterValue.toLowerCase()) ||
        u[type].apellido.toLowerCase().includes(filterValue.toLowerCase()) ||
        u[type].dni.toLowerCase().includes(filterValue.toLowerCase())
    );
    setFilteredUsers(filtered);
    handleCloseDialogFilter();
  };

  const handleCleanFilterUsers = () => {
    setFilteredUsers(dataUsers);
    setFilterValue("todos");
    handleCloseDialogFilter();
  };

  const handleOpenCursosDialog = () => {
    setOpenCursosDialog(true);
  };

  const handleCloseCursosDialog = () => {
    setOpenCursosDialog(false);
  };

  const handleUserSelected = (user) => {
    setOpenUserForm(true);
    setUserSelected(user);
    setBody("edit");
  };

  const handleOpenDialog = (user) => {
    setSelectedUser(user);
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
        <h1>
          Tabla de{" "}
          {type === "alumno"
            ? "Alumnos"
            : type === "profesor"
            ? "Profesores"
            : type === "administrador"
            ? "Administradores"
            : ""}
        </h1>
        <IconButton color="primary" onClick={handleOpenDialogFilter}>
          <FilterListIcon />
        </IconButton>
        {type === "alumno" ? (
          <Button onClick={() => handleOpenCursosDialog()} sx={{ ml: 1 }}>
            Filtrar por Curso
          </Button>
        ) : (
          ""
        )}
        <Grid item xs={12} sm={4}>
          <EstadoSelect
            filterValue={filterValue}
            handleFilterChange={handleFilterChange}
          />
        </Grid>
        <Button
          onClick={() => handleOpenUserForm("create")}
          variant="contained"
          sx={{ ml: 3 }}
        >
          Crear{" "}
          {type === "alumno"
            ? "Alumno"
            : type === "profesor"
            ? "Profesor"
            : type === "administrador"
            ? "Administrador"
            : ""}
        </Button>
      </Box>
      <TableContainer style={{ maxHeight: "750px" }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell
                style={{
                  position: "sticky",
                  top: 0,
                  background: "white",
                  paddingBottom: 13,
                }}
              >
                Nombre
              </TableCell>
              <TableCell
                style={{
                  position: "sticky",
                  top: 0,
                  background: "white",
                  paddingBottom: 13,
                }}
              >
                Apellido
              </TableCell>
              <TableCell
                style={{
                  position: "sticky",
                  top: 0,
                  background: "white",
                  paddingBottom: 13,
                }}
              >
                DNI
              </TableCell>
              <TableCell
                style={{
                  position: "sticky",
                  top: 0,
                  background: "white",
                  paddingBottom: 13,
                }}
              >
                Email
              </TableCell>
              <TableCell
                style={{
                  position: "sticky",
                  top: 0,
                  background: "white",
                  paddingBottom: 13,
                }}
              >
                Teléfono
              </TableCell>
              <TableCell
                style={{
                  position: "sticky",
                  top: 0,
                  background: "white",
                  paddingBottom: 5,
                  zIndex: 111,
                }}
              >
                Acciones
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredUsers.length > 0 ? (
              filteredUsers
                .filter((item) =>
                  filterValue === "habilitados"
                    ? !item[type].deleted
                    : filterValue === "deshabilitados"
                    ? item[type].deleted
                    : true
                )
                .map((u) => (
                  <TableRow key={u[type]?.id}>
                    <TableCell>{u[type]?.nombre}</TableCell>
                    <TableCell>{u[type]?.apellido}</TableCell>
                    <TableCell>{u[type]?.dni}</TableCell>
                    <TableCell>{u[type]?.email}</TableCell>
                    <TableCell>{u[type]?.celular}</TableCell>
                    <TableCell>
                      <IconButton
                        color="primary"
                        onClick={() => handleOpenDialog(u)}
                      >
                        <CursoView />
                      </IconButton>
                      <IconButton
                        color="primary"
                        onClick={() => handleUserSelected(u)}
                      >
                        <EditIcon />
                      </IconButton>
                      {u[type].deleted ? (
                        <Tooltip title="Alumno deshabilitado">
                          <IconButton color="warning">
                            <Warning fontSize="small" />
                          </IconButton>
                        </Tooltip>
                      ) : (
                        ""
                      )}
                    </TableCell>
                  </TableRow>
                ))
            ) : (
              <TableRow>
                <TableCell colSpan={6}>
                  No se encontraron{" "}
                  {type === "alumno"
                    ? "Alumnos"
                    : type === "profesor"
                    ? "Profesores"
                    : type === "administrador"
                    ? "Administradores"
                    : ""}
                  .
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogTitle>Cursos del alumno</DialogTitle>
        <DialogContent>
          {selectedUser && selectedUser.cursos.length > 0 ? (
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
                  {selectedUser.cursos.map((curso) => (
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
        <DialogTitle sx={{ mt: 1 }}>Filtrar Alumnos</DialogTitle>
        <DialogContent>
          <TextField
            label="Filtrar por nombre/apellido/DNI"
            value={filterValue}
            onChange={handleFilterChange}
            fullWidth
            sx={{ mt: 3 }}
          />
          <Button
            onClick={handleFilterUsers}
            variant="contained"
            sx={{ mr: 2, mt: 2 }}
          >
            Aplicar Filtro
          </Button>
          <Button
            onClick={handleCleanFilterUsers}
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
      <SimpleDialog
        open={openUserForm}
        close={handleCloseUserForm}
        titulo={body === "create" ? "Crear" : "Editar"}
        form={
          <CommonUsuarioForm
            refreshData={refreshData}
            handleOpenSnackBar={handleOpenSnackBar}
            handleClose={handleCloseUserForm}
            setData={setDataUsers}
            body={body}
            from="admin"
            initialValues={initialValues}
            uris={uris}
            isDeleted={isDeleted}
          />
        }
      />
      <UtilSnackBar
        open={openSnack}
        handleCloseSnackBar={handleCloseSnackBar}
        severity={severity}
        body={bodySnack}
      />
    </Box>
  );
};

export default CommonUserTable;
