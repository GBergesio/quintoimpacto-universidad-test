import {
  Box,
  Button,
  Checkbox,
  FormControl,
  FormControlLabel,
  Grid,
  IconButton,
  TextField,
  Tooltip,
  Typography,
} from "@mui/material";
import { Formik } from "formik";
import React from "react";
import createData from "@/utils/axiosPost";
import patchData from "@/utils/axiosPatch";
import { CommonUsuarioSchema } from "@/schemas/CommonUsuarioSchema";
import { Warning } from "@material-ui/icons";

export default function CommonUsuarioForm({
  refreshData,
  handleOpenSnackBar,
  handleClose,
  setData,
  body,
  from,
  initialValues,
  isDeleted,
  uris,
}) {
  const submitCommonUsuario = async (values, { resetForm }) => {
    const dataToSend = {
      nombre: values.nombre,
      apellido: values.apellido,
      email: values.email,
      celular: values.celular,
      dni: values.dni,
      password: from === "admin" ? "123" : values.password,
      deleted: values.deleted,
    };

    if (body === "create") {
      await createData(
        dataToSend,
        uris[0],
        handleClose,
        handleOpenSnackBar,
        resetForm
      );
    } else if (body === "edit") {
      await patchData(
        dataToSend,
        uris[1],
        handleClose,
        handleOpenSnackBar,
        resetForm
      );
    }
    refreshData(uris[0], setData);
  };

  return (
    <>
      <Formik
        initialValues={initialValues}
        validationSchema={CommonUsuarioSchema}
        onSubmit={submitCommonUsuario}
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
              label="Nombre"
              required
              sx={{ mb: 2, mt: 2 }}
              onChange={handleChange}
              value={values.nombre}
              error={touched.nombre && Boolean(errors.nombre)}
              helperText={
                touched.nombre && Boolean(errors.nombre) ? errors.nombre : " "
              }
              onBlur={handleBlur}
              touched={touched}
            />
            <TextField
              disabled={isSubmitting}
              fullWidth
              id="apellido"
              label="Apellido"
              required
              sx={{ mb: 2, mt: 2 }}
              onChange={handleChange}
              value={values.apellido}
              error={touched.apellido && Boolean(errors.apellido)}
              helperText={
                touched.apellido && Boolean(errors.apellido)
                  ? errors.apellido
                  : " "
              }
              onBlur={handleBlur}
              touched={touched}
            />
            <TextField
              disabled={isSubmitting}
              fullWidth
              id="email"
              label="Email"
              required
              sx={{ mb: 2, mt: 2 }}
              onChange={handleChange}
              value={values.email}
              error={touched.email && Boolean(errors.email)}
              helperText={
                touched.email && Boolean(errors.email) ? errors.email : " "
              }
              onBlur={handleBlur}
              touched={touched}
            />
            <TextField
              disabled={isSubmitting}
              fullWidth
              id="celular"
              label="Telefono"
              required
              sx={{ mb: 2, mt: 2 }}
              onChange={handleChange}
              value={values.celular}
              error={touched.celular && Boolean(errors.celular)}
              helperText={
                touched.celular && Boolean(errors.celular)
                  ? errors.celular
                  : " "
              }
              onBlur={handleBlur}
              touched={touched}
            />
            <TextField
              disabled={isSubmitting}
              fullWidth
              id="dni"
              label="DNI"
              required
              sx={{ mb: 2, mt: 2 }}
              onChange={handleChange}
              value={values.dni}
              error={touched.dni && Boolean(errors.dni)}
              helperText={touched.dni && Boolean(errors.dni) ? errors.dni : " "}
              onBlur={handleBlur}
              touched={touched}
            />
            {from === "admin" ? (
              ""
            ) : (
              <FormControl fullWidth required>
                <TextField
                  disabled={isSubmitting}
                  label="Contraseña"
                  value={values.password}
                  type="password"
                  id="password"
                  fullWidth
                  onChange={handleChange}
                  error={touched.password && Boolean(errors.password)}
                  helperText={
                    touched.password && Boolean(errors.password)
                      ? errors.password
                      : " "
                  }
                  onBlur={handleBlur}
                  touched={touched}
                />
              </FormControl>
            )}
            <FormControlLabel
              control={
                <Checkbox
                  checked={values.deleted}
                  onChange={handleChange("deleted")}
                />
              }
              label={isDeleted}
            />
            {body === "create" ? (
              <Grid container alignItems="center" sx={{ mt: 2 }}>
                <Tooltip title="El password por defecto es 123">
                  <Grid item>
                    <IconButton color="warning">
                      <Warning fontSize="small" />
                    </IconButton>
                  </Grid>
                </Tooltip>
                <Grid item>
                  <Typography>El password por defecto es 123</Typography>
                </Grid>
              </Grid>
            ) : (
              ""
            )}
            <Button
              fullWidth
              size="large"
              variant="contained"
              sx={{
                marginBottom: 7,
                marginTop: 3,
                backgroundColor: "#4caf44",
                "&:hover": {
                  backgroundColor: "#50ad48",
                },
              }}
              onClick={handleSubmit}
              disabled={values.turno === "" || isSubmitting}
            >
              {body === "create" ? "Crear" : "Editar"}
            </Button>
            <Button
              onClick={() => {
                resetForm();
                handleClose();
              }}
              variant="contained"
              sx={{
                backgroundColor: "#de0734",
                "&:hover": {
                  backgroundColor: "#d82c59",
                },
              }}
            >
              Salir
            </Button>
          </Box>
        )}
      </Formik>
    </>
  );
}
