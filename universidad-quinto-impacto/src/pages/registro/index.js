import CommonUsuarioForm from "@/components/Forms/CommonUsuarioForm";
import UtilSnackBar from "@/components/Snackbar";
import { AppBar, Avatar, Button, Grid, Toolbar, Typography } from "@mui/material";
import { useRouter } from "next/router";
import { Link as NextLink } from "next/link";
import React, { useState } from "react";

export default function index() {
  const uri = "/alumnos/current";
  const uris = [uri, ""];
  const [bodySnack, setBodySnack] = useState("");
  const [severity, setSeverity] = useState("");
  const [openSnack, setOpenSnack] = useState(false);
  const router = useRouter();

  const redirect = () => {
    goTo("/login");
  };

  const goTo = (site) => {
    router.push(site, undefined, { shallow: true });
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

  const initialValues = {
    nombre: "",
    apellido: "",
    dni: "",
    celular: "",
    email: "",
    deleted: false,
  };

  return (
    <>
      <AppBar position="static" sx={{ backgroundColor: "#4052da" }}>
        <Toolbar>
          <Avatar
            alt="Logo Universidad XYZ"
            src="/images/logo.png"
            sx={{ marginRight: "1rem" }}
          />
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Universidad Quinto Impacto
          </Typography>
          <Button color="inherit" component={NextLink} href="/login">
            Login
          </Button>
        </Toolbar>
      </AppBar>
      <Grid container justifyContent="center" alignItems="center" spacing={2}>
        <Grid item xs={12}>
          <Grid container justifyContent="center">
            <Typography variant="h4" sx={{ mt: 3, mb: 3 }}>
              Registrarse como alumno
            </Typography>
          </Grid>
        </Grid>
        <Grid item xs={12} sm={8} md={6} lg={4}>
          <CommonUsuarioForm
            uris={uris}
            body="create"
            initialValues={initialValues}
            type={"alumno"}
            from="registro"
            handleOpenSnackBar={handleOpenSnackBar}
            handleClose={redirect}
          />
        </Grid>
      </Grid>
      <UtilSnackBar
        open={openSnack}
        handleCloseSnackBar={handleCloseSnackBar}
        severity={severity}
        body={bodySnack}
      />
    </>
  );
}
