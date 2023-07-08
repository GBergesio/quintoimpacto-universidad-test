import { useRef, useState } from "react";
import {
  TextField,
  Button,
  Grid,
  Box,
  FormControl,
  InputLabel,
  AppBar,
  Toolbar,
  Avatar,
  Typography,
} from "@mui/material";
import axios from "axios";
import { useRouter } from "next/router";
import { Formik } from "formik";
import { LoginSchema } from "@/schemas/LoginSchema";
import UtilSnackBar from "../Snackbar";
import apiUrl from "@/utils/apiUrl";

const LoginForm = () => {
  const router = useRouter();
  const passwordInput = useRef(null);
  const formRef = useRef(null);

  const [bodySnack, setBodySnack] = useState("");
  const [severity, setSeverity] = useState("");
  const [openSnack, setOpenSnack] = useState(false);
  const goTo = (site) => {
    router.push(site, undefined, { shallow: true });
  };

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

  const handleSubmit = async (values) => {
    const params = {
      username: values.email,
      password: values.password,
    };

    const res = await axios
      .post(apiUrl + "/authenticate", JSON.stringify(params), {
        headers: {
          crossDomain: true,
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        if (res.status === 200) {
          localStorage.setItem("SSN_TKN", res.data.dto.token);
          goTo("/dashboard");
          handleOpenSnackBar("Ingreso exitoso", "success");
        }
        if (res.status === 200) {
          console.log("va al dashboard");
          goTo("/dashboard");
        }
      })
      .catch((error) => {
        console.error(error);
        handleOpenSnackBar(
          "Error al ingresar, revisa tu usuario o contraseña",
          "error"
        );
      });
  };

  return (
    <>
      <AppBar position="static" sx={{ backgroundColor: "#4052da" }}>
        <Toolbar>
          <Box sx={{ display: "flex", alignItems: "center" }}>
            <Avatar
              alt="Logo Universidad XYZ"
              src="/images/logo.png"
              sx={{ marginRight: "1rem" }}
            />
            <Typography variant="h6" component="div">
              Universidad Quinto Impacto
            </Typography>
          </Box>
        </Toolbar>
      </AppBar>
      <Grid container justifyContent="center" alignItems="center" spacing={2}>
        <Grid item xs={12}>
          <Grid container justifyContent="center">
            <Typography variant="h4" sx={{ mt: 3, mb: 3 }}>
              Iniciar sesión
            </Typography>
          </Grid>
        </Grid>
        <Formik
          innerRef={formRef}
          initialValues={{
            email: "",
            password: "",
          }}
          validationSchema={LoginSchema}
          onSubmit={(values) => handleSubmit(values)}
        >
          {({
            handleSubmit,
            handleChange,
            handleBlur,
            errors,
            touched,
            isSubmitting,
            values,
          }) => (
            <Box component="form" noValidate autoComplete="off">
              <TextField
                disabled={isSubmitting}
                autoFocus
                fullWidth
                id="email"
                label="Email"
                required
                sx={{ marginBottom: 4 }}
                onChange={handleChange}
                error={touched.email && Boolean(errors.email)}
                helperText={
                  touched.email && Boolean(errors.email) ? errors.email : " "
                }
                onBlur={handleBlur}
                touched={touched}
                onKeyDown={(e) => {
                  if (e.key === "Enter" && values.email != "") {
                    passwordInput.current.focus();
                  }
                }}
              />
              <FormControl fullWidth required>
                <TextField
                  disabled={isSubmitting}
                  label="Contraseña"
                  value={values.password}
                  type="password"
                  id="password"
                  inputRef={passwordInput}
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
                  onKeyDown={(e) => {
                    if (e.key === "Enter") {
                      handleSubmit();
                    }
                  }}
                />
              </FormControl>
              <Button
                fullWidth
                size="large"
                variant="contained"
                sx={{
                  marginBottom: 7,
                  marginTop: 3,
                  backgroundColor: "#4052da",
                }}
                onClick={handleSubmit}
                disabled={isSubmitting}
              >
                Ingresar
              </Button>
            </Box>
          )}
        </Formik>
      </Grid>
      <UtilSnackBar
        open={openSnack}
        handleCloseSnackBar={handleCloseSnackBar}
        severity={severity}
        body={bodySnack}
      />
    </>
  );
};

export default LoginForm;
