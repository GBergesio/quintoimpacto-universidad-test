import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  IconButton,
  InputLabel,
  MenuItem,
  Select,
  Tooltip,
} from "@mui/material";
import { Formik } from "formik";
import Edit from "@material-ui/icons/Edit";
import { CheckCircle, Warning } from "@material-ui/icons";
import createData from "@/utils/axiosPost";
import ListaAlumnosDialog from "../Dialog/ListaAlumnosDialog";

export default function Curso({
  d,
  userType,
  userLogged,
  releaseProfesor,
  dataProfesores,
  refreshData,
  setData,
  handleOpenSnackBar,
  setCursoSelected,
  handleOpenCursoForm,
  setBody,
}) {
  //USE STATE
  const [botonTexto, setBotonTexto] = useState("");
  const [selectedProfesor, setSelectedProfesor] = useState("");
  const [profesores, setProfesores] = useState([]);
  const [open, setOpen] = useState(false);
  const [openTomarCurso, setOpenTomarCurso] = useState(false);
  const [alumnosCurso, setAlumnosCurso] = useState([]);

  const initialValues = {
    idProfesor: "",
    idCurso: "",
  };
  const isAlumnoInscrito = d.alumnos.some(
    (alumno) => alumno.id === userLogged.id
  );

  //USE EFFECT
  useEffect(() => {
    if (userType === "administrador") {
      if (d.curso.profesor === null) {
        setBotonTexto("Asignar profesor");
      } else {
        setBotonTexto("Eliminar profesor");
      }
    }
    if (userType === "alumno") {
      setBotonTexto("Tomar curso");
    }
  }, [d.curso.profesor, userType]);

  console.log("dat", d);

  useEffect(() => {
    const profesoresData = dataProfesores.map((item) => item.profesor);
    setProfesores(profesoresData);
  }, [dataProfesores]);

  //Actualizo estado del body para poder editarlo, si no me llega en null en el componente cursoForm
  useEffect(() => {
    if (d.curso && d.curso.id) {
      setBody("edit");
    }
  }, [d.curso, setBody]);

  //HANDLERS
  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedProfesor("");
  };

  const handleReleaseProfesor = async () => {
    await releaseProfesor(d.curso.id);
    handleClose();
    refreshData("/cursos/current", setData);
  };

  const handleSelectChange = (event) => {
    setSelectedProfesor(event.target.value);
  };

  const handleOpenTomarCurso = () => {
    setOpenTomarCurso(true);
  };

  const handleCloseTomarCurso = () => {
    setOpenTomarCurso(false);
  };

  const handleCursoSelected = (d) => {
    handleOpenCursoForm();
    setCursoSelected(d);
    setBody("edit");
  };

  const handleTomarCurso = async (values) => {
    const tipo = isAlumnoInscrito ? "remove" : "set";

    const dataToSend = {
      idCurso: d.curso.id,
      idAlumno: userLogged.id,
      tipo: tipo,
    };

    await createData(
      dataToSend,
      "/cursos/current/setCursoAlumno",
      handleCloseTomarCurso,
      handleOpenSnackBar
    );

    refreshData("/cursos/current", setData);
  };

  const isDeleted = d.curso.deleted;

  const handleAsignarProfesor = async (values, { resetForm }) => {
    const dataToSend = {
      idProfesor: selectedProfesor,
      idCurso: d.curso.id,
    };
    await createData(
      dataToSend,
      "/profesores/current/setCursoProfesor",
      handleClose,
      handleOpenSnackBar,
      resetForm
    );
    refreshData("/cursos/current", setData);
  };

  const [openListaAlumnos, setOpenListaAlumnos] = useState(false);

  const handleOpenListaAlumnos = () => {
    setAlumnosCurso(d.alumnos);
    setOpenListaAlumnos(true);
  };

  const handleCloseListaAlumnos = () => {
    setOpenListaAlumnos(false);
  };

  return (
    <div>
      <div style={{ display: "flex", alignItems: "center" }}>
        <h4 style={{ marginRight: "5px" }}>
          {d.curso.nombre.length > 19
            ? `${d.curso.nombre.substring(0, 17)}...`
            : d.curso.nombre}
        </h4>
        {userType === "alumno" && isAlumnoInscrito ? (
          <>
            {" "}
            <Tooltip title="Ya estas inscripto en este curso">
              <IconButton color="success">
                <CheckCircle fontSize="small" />
              </IconButton>
            </Tooltip>
          </>
        ) : (
          ""
        )}
        {userType === "administrador" ? (
          <>
            <Tooltip
              title={isDeleted ? "Curso deshabilitado" : "Curso habilitado"}
            >
              <IconButton color={isDeleted ? "warning" : "success"}>
                {isDeleted ? (
                  <Warning fontSize="small" />
                ) : (
                  <CheckCircle fontSize="small" />
                )}
              </IconButton>
            </Tooltip>
            <Tooltip title="Editar curso">
              <IconButton>
                <Edit
                  fontSize="small"
                  onClick={() => {
                    handleCursoSelected(d);
                  }}
                />
              </IconButton>
            </Tooltip>
          </>
        ) : (
          ""
        )}
      </div>
      <h5>
        Profesor:{" "}
        {d.curso.profesor
          ? `${d.curso.profesor.nombre} ${d.curso.profesor.apellido}`
          : "No hay profesor asignado"}
      </h5>
      <h5>
        Turno:{" "}
        {d.curso.turno === "T"
          ? "Tarde"
          : d.curso.turno === "M"
          ? "Mañana"
          : "Noche"}
      </h5>
      <h5>
        {d.alumnos.length === 0
          ? "No hay alumnos inscriptos"
          : d.alumnos.length === 1
          ? "Un alumno inscripto"
          : d.alumnos.length + " alumnos inscriptos"}
      </h5>
      {userType === "alumno" && (
        <div>
          {userType === "alumno" && isAlumnoInscrito ? (
            <Button
              variant="outlined"
              onClick={handleOpenTomarCurso}
              sx={{
                borderColor: "#4052da",
                "&:hover": {
                  borderColor: "#4454dc",
                },
              }}
            >
              Dejar curso
            </Button>
          ) : (
            <Button
              variant="outlined"
              onClick={handleOpenTomarCurso}
              sx={{
                borderColor: "#4052da",
                "&:hover": {
                  borderColor: "#4454dc",
                },
              }}
            >
              Tomar curso
            </Button>
          )}
          <Dialog open={openTomarCurso} onClose={handleCloseTomarCurso}>
            <DialogTitle>Confirmación de tomar el curso</DialogTitle>
            <DialogContent>
              <p>¿Está seguro de que desea tomar este curso?</p>
            </DialogContent>
            <DialogActions>
              <Button
                onClick={handleCloseTomarCurso}
                sx={{
                  borderColor: "#4052da",
                  "&:hover": {
                    borderColor: "#4454dc",
                  },
                }}
              >
                Cancelar
              </Button>
              <Button
                onClick={handleTomarCurso}
                sx={{
                  borderColor: "#4052da",
                  "&:hover": {
                    borderColor: "#4454dc",
                  },
                }}
              >
                {isAlumnoInscrito ? "Dejar curso" : "Tomar curso"}
              </Button>
            </DialogActions>
          </Dialog>
        </div>
      )}
      {userType === "administrador" && (
        <div>
          {botonTexto === "Asignar profesor" ? (
            <>
              <Button
                variant="outlined"
                onClick={handleOpen}
                sx={{
                  borderColor: "#4052da",
                  "&:hover": {
                    borderColor: "#4454dc",
                  },
                }}
              >
                {botonTexto}
              </Button>
              <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Asignar profesor</DialogTitle>
                <DialogContent>
                  <Formik
                    onSubmit={handleAsignarProfesor}
                    initialValues={initialValues}
                  >
                    {({ handleSubmit }) => (
                      <Box component="form">
                        <FormControl sx={{ minWidth: 200, mt: 1, mb: 1 }}>
                          <InputLabel id="profesor-label">
                            Seleccionar profesor
                          </InputLabel>
                          <Select
                            labelId="profesor-label"
                            id="profesor-select"
                            value={selectedProfesor}
                            onChange={handleSelectChange}
                          >
                            {profesores.map((profesor) => (
                              <MenuItem key={profesor.id} value={profesor.id}>
                                {profesor.nombre + " " + profesor.apellido}
                              </MenuItem>
                            ))}
                          </Select>
                        </FormControl>
                        <DialogActions>
                          <Button
                            onClick={handleClose}
                            sx={{
                              borderColor: "#4052da",
                              "&:hover": {
                                borderColor: "#4454dc",
                              },
                            }}
                          >
                            Cancelar
                          </Button>
                          <Button
                            sx={{
                              borderColor: "#4052da",
                              "&:hover": {
                                borderColor: "#4454dc",
                              },
                            }}
                            onClick={handleSubmit}
                            disabled={selectedProfesor === ""}
                          >
                            Asignar
                          </Button>
                        </DialogActions>
                      </Box>
                    )}
                  </Formik>
                </DialogContent>
              </Dialog>
            </>
          ) : (
            <>
              <Button
                variant="outlined"
                onClick={handleOpen}
                sx={{
                  borderColor: "#4052da",
                  "&:hover": {
                    borderColor: "#4454dc",
                  },
                }}
              >
                {botonTexto}
              </Button>
              <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Confirmación de eliminación</DialogTitle>
                <DialogContent>
                  <p>¿Está seguro de que desea eliminar al profesor?</p>
                </DialogContent>
                <DialogActions>
                  <Button
                    onClick={handleClose}
                    sx={{
                      borderColor: "#4052da",
                      "&:hover": {
                        borderColor: "#4454dc",
                      },
                    }}
                  >
                    Cancelar
                  </Button>
                  <Button
                    onClick={handleReleaseProfesor}
                    sx={{
                      borderColor: "#4052da",
                      "&:hover": {
                        borderColor: "#4454dc",
                      },
                    }}
                  >
                    Eliminar
                  </Button>
                </DialogActions>
              </Dialog>
            </>
          )}
        </div>
      )}
      {userType !== "alumnos" && (
        <Button
          variant="outlined"
          onClick={handleOpenListaAlumnos}
          sx={{
            mt: 3,
            borderColor: "#4052da",
            "&:hover": {
              borderColor: "#4454dc",
            },
          }}
        >
          Ver lista de alumnos
        </Button>
      )}
      <ListaAlumnosDialog
        open={openListaAlumnos}
        handleClose={handleCloseListaAlumnos}
        alumnos={alumnosCurso}
      />
    </div>
  );
}
