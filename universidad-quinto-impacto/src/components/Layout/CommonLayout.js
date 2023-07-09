import * as React from "react";
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
import MenuIcon from "@material-ui/icons/Menu";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import NotificationsIcon from "@material-ui/icons/Notifications";
import getData from "@/utils/axiosGet";
import { styled, createTheme, ThemeProvider } from "@mui/material/styles";
import { useState } from "react";
import { useRouter } from "next/router";
import { useEffect } from "react";
import { checkTypeUser, cleanToken } from "@/utils/security";
import { Avatar, Button } from "@mui/material";
import { Link as NextLink } from "next/link";

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

export default function CommonLayout({ componente }) {
  //USE STATE
  const [open, setOpen] = useState(true);
  const [userLogged, setUserLogged] = useState([]);
  const router = useRouter();
  let userType = checkTypeUser(userLogged);

  const toggleDrawer = () => {
    setOpen(!open);
  };

  const goTo = (site) => {
    router.push(site, undefined, { shallow: true });
  };

  function logout() {
    goTo("/");
    cleanToken();
  }

  const refreshData = async (endpoint, setDataFunc) => {
    return await getData(endpoint, goTo).then((res) => {
      setDataFunc(res.dto);
    });
  };

  //USE EFFECT
  useEffect(() => {
    refreshData("/currentUser", setUserLogged);
  }, []);

  return (
    <ThemeProvider theme={defaultTheme}>
      <Box sx={{ display: "flex" }}>
        <CssBaseline />
        <AppBar position="absolute" open={open}>
          <Toolbar sx={{ backgroundColor: "#4052da", pr: "24px" }}>
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
            <Avatar
              alt="Logo Universidad XYZ"
              src="/images/logo.png"
              sx={{ marginRight: "1rem" }}
            />
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
            <Button color="inherit" component={NextLink} href="/dashboard">
              Dashboard / Cursos
            </Button>
            <Divider sx={{ my: 1 }} />
            {userType === "administrador" && (
              <>
                <Button
                  color="inherit"
                  component={NextLink}
                  href="/administracion/profesores"
                  disabled={userLogged.deleted}
                >
                  Ver profesores
                </Button>
                <Divider sx={{ my: 1 }} />
                <Button
                  color="inherit"
                  component={NextLink}
                  href="/administracion/alumnos"
                  disabled={userLogged.deleted}
                >
                  Ver alumnos
                </Button>
                <Divider sx={{ my: 1 }} />
                <Button
                  color="inherit"
                  component={NextLink}
                  href="/administracion/administradores"
                  disabled={userLogged.deleted}
                >
                  Ver administradores
                </Button>
              </>
            )}
            {userType === "profesor" && (
              <Button
                color="inherit"
                component={NextLink}
                href="/administracion/alumnos"
                disabled={userLogged.deleted}
              >
                Ver alumnos
              </Button>
            )}
            <Divider sx={{ my: 1 }} />
            <Button color="inherit" onClick={() => logout()}>
              Salir
            </Button>
          </List>
        </Drawer>
        {/* // generico para todas las vistas */}
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
          {componente}
        </Box>
      </Box>
    </ThemeProvider>
  );
}
