import * as React from "react";
import { styled, createTheme, ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import MuiDrawer from "@mui/material/Drawer";
import Box from "@mui/material/Box";
import MuiAppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import List from "@mui/material/List";
import Typography from "@mui/material/Typography";
import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import Badge from "@mui/material/Badge";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import Link from "@mui/material/Link";
import MenuIcon from "@material-ui/icons/Menu";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import NotificationsIcon from "@material-ui/icons/Notifications";
import Curso from "./Curso";
import { useState } from "react";
import { useRouter } from "next/router";
import { useEffect } from "react";
import getData from "@/utils/axiosGet";
import { checkTypeUser, cleanToken } from "@/utils/security";
import {
  Button,
  Dialog,
  DialogContent,
  DialogTitle,
  FormControl,
  Input,
  InputLabel,
  MenuItem,
  Select,
} from "@mui/material";
import deleteData from "@/utils/axiosDelete";
import UtilSnackBar from "../Snackbar";
import SimpleDialog from "../Dialog/SimpleDialog";
import CursoForm from "../Forms/CursoForm";
import EstadoSelect from "../Forms/EstadoSelect";
// import { mainListItems, secondaryListItems } from './listItems';
// import Chart from './Chart';
// import Deposits from './Deposits';
// import Orders from './Orders';

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="https://mui.com/">
        Your Website
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const drawerWidth = 240;

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme, open }) => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(["width", "margin"], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  ...(open && {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));

const Drawer = styled(MuiDrawer, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme, open }) => ({
  "& .MuiDrawer-paper": {
    position: "relative",
    whiteSpace: "nowrap",
    width: drawerWidth,
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
    boxSizing: "border-box",
    ...(!open && {
      overflowX: "hidden",
      transition: theme.transitions.create("width", {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
      width: theme.spacing(7),
      [theme.breakpoints.up("sm")]: {
        width: theme.spacing(9),
      },
    }),
  },
}));

const defaultTheme = createTheme();

export default function Dashboard() {
  //USE STATE
  const [open, setOpen] = useState(true);
  const [dataCursos, setDataCursos] = useState([]);
  const [cursoSelected, setCursoSelected] = useState([]);
  const [dataProfesores, setProfesoresData] = useState([]);
  const [userLogged, setUserLogged] = useState([]);
  const [bodySnack, setBodySnack] = useState("");
  const [severity, setSeverity] = useState("");
  const [openSnack, setOpenSnack] = useState(false);
  const [searchValue, setSearchValue] = useState("");
  const [body, setBody] = useState("create");
  const [openCursoForm, setOpenCursoForm] = useState(false);
  const [filterValue, setFilterValue] = useState("todos");

  console.log(userLogged);

  let userType = checkTypeUser(userLogged);
  const router = useRouter();

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

  const handleOpenCursoForm = (body) => {
    setOpenCursoForm(true);
    setBody(body);
  };

  const handleCloseCursoForm = () => {
    setOpenCursoForm(false);
    setBody("create");
    setCursoSelected([]);
  };

  const toggleDrawer = () => {
    setOpen(!open);
  };

  const handleFilterChange = (event) => {
    setFilterValue(event.target.value);
  };

  const goTo = (site) => {
    router.push(site, undefined, { shallow: true });
  };

  function logout() {
    goTo("/login");
    cleanToken();
  }

  const refreshData = async (endpoint, setDataFunc) => {
    return await getData(endpoint, goTo).then((res) => {
      setDataFunc(res.dto);
    });
  };

  async function releaseProfesor(id) {
    await deleteData("/profesores/current/release/" + id, handleOpenSnackBar);
    refreshData("/cursos/current", setDataCursos);
  }

  //USE EFFECT
  useEffect(() => {
    refreshData("/cursos/current", setDataCursos);
    refreshData("/currentUser", setUserLogged);
    refreshData("/profesores/current", setProfesoresData);
  }, []);

  return (
    <ThemeProvider theme={defaultTheme}>
      <Box sx={{ display: "flex" }}>
        <CssBaseline />
        <AppBar position="absolute" open={open}>
          <Toolbar
            sx={{
              pr: "24px",
            }}
          >
            <IconButton
              edge="start"
              color="inherit"
              aria-label="open drawer"
              onClick={toggleDrawer}
              sx={{
                marginRight: "36px",
                ...(open && { display: "none" }),
              }}
            >
              <MenuIcon />
            </IconButton>
            <Typography
              component="h1"
              variant="h6"
              color="inherit"
              noWrap
              sx={{ flexGrow: 1 }}
            >
              Bienvenido {userLogged.nombre + " " + userLogged.apellido}
            </Typography>
            <IconButton color="inherit">
              <Badge badgeContent={4} color="secondary">
                <NotificationsIcon />
              </Badge>
            </IconButton>
          </Toolbar>
        </AppBar>
        <Drawer variant="permanent" open={open}>
          <Toolbar
            sx={{
              display: "flex",
              alignItems: "center",
              justifyContent: "flex-end",
              px: [1],
            }}
          >
            <IconButton onClick={toggleDrawer}>
              <ChevronLeftIcon />
            </IconButton>
          </Toolbar>
          <Divider />
          <List component="nav">
            {/* {mainListItems} */}
            12 3 4
            <Divider sx={{ my: 1 }} />
            {/* {secondaryListItems} */}
            bla bla
            <Divider sx={{ my: 1 }} />
            <Button onClick={() => logout()}>Salir</Button>
          </List>
        </Drawer>
        <Box
          component="main"
          sx={{
            backgroundColor: (theme) =>
              theme.palette.mode === "light"
                ? theme.palette.grey[100]
                : theme.palette.grey[900],
            flexGrow: 1,
            height: "100vh",
            overflow: "auto",
          }}
        >
          <Toolbar />
          <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Grid
              container
              alignItems="center"
              spacing={2}
              sx={{ mt: 4, mb: 4 }}
            >
              <Grid item xs={12} sm={6}>
                <Input
                  fullWidth
                  placeholder="Buscar curso"
                  value={searchValue}
                  onChange={(e) => setSearchValue(e.target.value)}
                />
              </Grid>
              {userType === "administrador" && (
                <Grid item xs={12} sm={4}>
                  <EstadoSelect
                    filterValue={filterValue}
                    handleFilterChange={handleFilterChange}
                  />
                </Grid>
              )}
              {userType === "administrador" && (
                <Grid item xs={12} sm={2}>
                  <Button
                    variant="contained"
                    fullWidth
                    onClick={() => {
                      handleOpenCursoForm("create");
                    }}
                  >
                    Agregar curso
                  </Button>
                </Grid>
              )}
            </Grid>
            <Grid container spacing={3}>
              {dataCursos
                .filter((item) =>
                  item.curso.nombre
                    ?.toLowerCase()
                    .includes(searchValue.toLowerCase())
                )
                .filter((item) =>
                  userType === "alumno" ? !item.curso.deleted : true
                )
                .filter((item) =>
                  userType === "profesor"
                    ? item.curso.profesor &&
                      item.curso.profesor.id === userLogged.id
                    : true
                )
                .map((item, index) => (
                  <Grid item key={index} xs={12} md={8} lg={3}>
                    <Paper
                      sx={{
                        p: 2,
                        display: "flex",
                        flexDirection: "column",
                        height: 350,
                      }}
                    >
                      <Curso
                        d={item}
                        userType={userType}
                        userLogged={userLogged}
                        releaseProfesor={releaseProfesor}
                        dataProfesores={dataProfesores}
                        refreshData={refreshData}
                        setData={setDataCursos}
                        handleOpenSnackBar={handleOpenSnackBar}
                        setCursoSelected={setCursoSelected}
                        handleOpenCursoForm={handleOpenCursoForm}
                        setBody={setBody}
                      />
                    </Paper>
                  </Grid>
                ))}
              {dataCursos.filter((item) =>
                item.curso.nombre
                  ?.toLowerCase()
                  .includes(searchValue.toLowerCase())
              ).length === 0 && (
                <Grid item xs={12}>
                  <Paper
                    sx={{ p: 2, display: "flex", flexDirection: "column" }}
                  >
                    <Typography variant="body1">
                      No se encontraron cursos disponibles.
                    </Typography>
                  </Paper>
                </Grid>
              )}
              {/* <Grid item xs={12}>
                <Paper sx={{ p: 2, display: "flex", flexDirection: "column" }}>
                  abajo
                </Paper>
              </Grid> */}
            </Grid>
            <Copyright sx={{ pt: 4 }} />
          </Container>
        </Box>
        <UtilSnackBar
          open={openSnack}
          handleCloseSnackBar={handleCloseSnackBar}
          severity={severity}
          body={bodySnack}
        />
        <SimpleDialog
          open={openCursoForm}
          close={handleCloseCursoForm}
          titulo={body === "create" ? "Crear curso" : "Editar curso"}
          form={
            <CursoForm
              cSelected={cursoSelected}
              dataProfesores={dataProfesores}
              refreshData={refreshData}
              handleOpenSnackBar={handleOpenSnackBar}
              handleClose={handleCloseCursoForm}
              setData={setDataCursos}
              body={body}
            />
          }
        />
      </Box>
    </ThemeProvider>
  );
}
