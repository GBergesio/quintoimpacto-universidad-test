import {
  Box,
  Button,
  Checkbox,
  FormControlLabel,
  TextField,
} from "@mui/material";
import { Formik } from "formik";
import React, { useEffect, useState } from "react";
import TurnoSelect from "./TurnoSelect";
import ProfesorSelect from "./ProfesorSelect";
import { CursoSchema } from "@/schemas/CursoSchema";
import createData from "@/utils/axiosPost";
import patchData from "@/utils/axiosPatch";

export default function CursoForm({
  cSelected,
  dataProfesores,
  refreshData,
  handleOpenSnackBar,
  handleClose,
  setData,
  body,
}) {
  const initialValues = {
    nombre: cSelected.length === 0 ? "" : cSelected.curso.nombre,
    turno: cSelected.length === 0 ? "" : cSelected.curso.turno,
    deleted: cSelected.length === 0 ? false : cSelected.curso.deleted,
    profesor:
      cSelected.length === 0
        ? null
        : cSelected.curso.profesor
        ? cSelected.curso.profesor.id
        : null,
  };

  const submitCurso = async (values, { resetForm }) => {
    const dataToSend = {
      nombre: values.nombre,
      turno: values.turno,
      idProfesor: values.profesor,
      deleted: values.deleted,
    };

    if (body === "create") {
      await createData(
        dataToSend,
        "/cursos/current",
        handleClose,
        handleOpenSnackBar,
        resetForm
      );
    } else if (body === "edit") {
      let uriEdit = "/cursos/current/id/" + `${cSelected.curso.id}`;
      await patchData(
        dataToSend,
        uriEdit,
        handleClose,
        handleOpenSnackBar,
        resetForm
      );
    }
    refreshData("/cursos/current", setData);
  };

  return (
    <>
      <Formik
        initialValues={initialValues}
        validationSchema={CursoSchema}
        onSubmit={submitCurso}
      >
        {({
          handleSubmit,
          handleChange,
          handleBlur,
          errors,
          touched,
          isSubmitting,
          values,
          resetForm,
        }) => (
          <Box component="form" noValidate autoComplete="off">
            <TextField
              disabled={isSubmitting}
              autoFocus
              fullWidth
              id="nombre"
              label="Nombre del curso"
              required
              sx={{ mb: 2, mt: 2 }}
              onChange={handleChange}
              value={values.nombre} // Agrega esta línea
              error={touched.nombre && Boolean(errors.nombre)}
              helperText={
                touched.nombre && Boolean(errors.nombre) ? errors.nombre : " "
              }
              onBlur={handleBlur}
              touched={touched}
            />
            <TurnoSelect
              value={values.turno}
              onChange={handleChange("turno")}
            />
            <ProfesorSelect
              dataProfesores={dataProfesores}
              value={values.profesor}
              onChange={handleChange("profesor")}
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={values.deleted}
                  onChange={handleChange("deleted")}
                />
              }
              label={
                cSelected.length === 0
                  ? "Deshabilitar curso"
                  : cSelected.curso.deleted
                  ? "El curso está deshabilitado"
                  : "Deshabilitar curso"
              }
            />
            <Button
              fullWidth
              size="large"
              variant="contained"
              sx={{ marginBottom: 7, marginTop: 3 }}
              onClick={handleSubmit}
              disabled={values.turno === "" || isSubmitting}
              color="success"
            >
              {body === "create" ? "Crear" : "Editar"}
            </Button>
            <Button
              onClick={() => {
                resetForm();
                handleClose();
              }}
              variant="contained"
              color="error"
            >
              Salirrr
            </Button>
          </Box>
        )}
      </Formik>
    </>
  );
}
