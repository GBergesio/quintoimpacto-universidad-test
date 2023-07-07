import createData from "@/utils/axiosPost";
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
} from "@mui/material";
import { Formik } from "formik";
import React, { useEffect, useState } from "react";

export default function Curso({
  d,
  userType,
  userLogged,
  releaseProfesor,
  dataProfesores,
  refreshData,
  setData,
  handleOpenSnackBar,
}) {
  //USE STATE
  const [botonTexto, setBotonTexto] = useState("");
  const [selectedProfesor, setSelectedProfesor] = useState("");
  const [profesores, setProfesores] = useState([]);
  const [open, setOpen] = useState(false);
  const [openTomarCurso, setOpenTomarCurso] = useState(false);

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

  useEffect(() => {
    const profesoresData = dataProfesores.map((item) => item.profesor);
    setProfesores(profesoresData);
  }, [dataProfesores]);

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

  const handleAsignarProfesor = async (values, { resetForm }) => {
    const dataToSend = {
      idProfesor: selectedProfesor,
      idCurso: d.curso.id,
    };
    await createData(
      dataToSend,
      "/profesores/current/setCursoProfesor",
      handleClose,
      handleOpenSnackBar
    );
    resetForm();
    refreshData("/cursos/current", setData);
  };

  return (
    <div>
      <h4>Curso: {d.curso.nombre}</h4>
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
            <Button variant="outlined" onClick={handleOpenTomarCurso}>
              Dejar curso
            </Button>
          ) : (
            <Button variant="outlined" onClick={handleOpenTomarCurso}>
              Tomar curso
            </Button>
          )}
          <Dialog open={openTomarCurso} onClose={handleCloseTomarCurso}>
            <DialogTitle>Confirmación de tomar el curso</DialogTitle>
            <DialogContent>
              <p>¿Está seguro de que desea tomar este curso?</p>
            </DialogContent>
            <DialogActions>
              <Button onClick={handleCloseTomarCurso}>Cancelar</Button>
              <Button onClick={handleTomarCurso}>{isAlumnoInscrito ? "Dejar curso" : "Tomar curso"}</Button>
            </DialogActions>
          </Dialog>
        </div>
      )}
      {userType === "administrador" && (
        <div>
          {botonTexto === "Asignar profesor" ? (
            <>
              <Button variant="outlined" onClick={handleOpen}>
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
                        <FormControl>
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
                          <Button onClick={handleClose}>Cancelar</Button>
                          <Button
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
              <Button variant="outlined" onClick={handleOpen}>
                {botonTexto}
              </Button>
              <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Confirmación de eliminación</DialogTitle>
                <DialogContent>
                  <p>¿Está seguro de que desea eliminar al profesor?</p>
                </DialogContent>
                <DialogActions>
                  <Button onClick={handleClose}>Cancelar</Button>
                  <Button onClick={handleReleaseProfesor}>Eliminar</Button>
                </DialogActions>
              </Dialog>
            </>
          )}
        </div>
      )}
    </div>
  );
}
