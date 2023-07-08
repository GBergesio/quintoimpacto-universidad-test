import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Container,
  Grid,
  Card,
  CardContent,
  CardMedia,
  Avatar,
} from "@mui/material";
import { Link as NextLink } from "next/link";

const WelcomePage = () => {
  return (
    <div>
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
          <Button color="inherit" component={NextLink} href="/registro">
            Registrarse como alumno
          </Button>
        </Toolbar>
      </AppBar>
      <Container maxWidth="md" sx={{ paddingTop: "2rem" }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Bienvenido a Quinto Impacto!
        </Typography>
        <Typography variant="body1" gutterBottom>
          Ubicada en la hermosa ciudad de Mendoza, nuestra universidad ofrece
          una amplia variedad de cursos en diferentes áreas de estudio. ¡Explora
          nuestras opciones y comienza tu viaje académico con nosotros!
        </Typography>
      </Container>
      <Container maxWidth="lg" sx={{ paddingTop: "5rem" }}>
        <Grid container spacing={3}>
          <Grid item xs={12} sm={4}>
            <Card sx={{ height: "100%" }}>
              <CardContent>
                <Typography variant="h5" component="h2">
                  Curso Biologia Molecular
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{mb:1}}>
                  El Curso de Biología Molecular ofrece una introducción
                  completa a los principios y técnicas fundamentales de la
                  biología molecular. Explora los procesos celulares y
                  moleculares que rigen la vida y las aplicaciones prácticas en
                  medicina, biotecnología e investigación científica. ¡Únete a
                  nosotros para ampliar tus conocimientos en este emocionante
                  campo!
                </Typography>
                <CardMedia
                  component="img"
                  height="250"
                  image="/images/biologia.jpg"
                  alt="biologia"
                  sx={{ marginTop: "auto" }}
                />
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} sm={4}>
            <Card sx={{ height: "100%" }}>
              <CardContent>
                <Typography variant="h5" component="h2">
                  Curso Matemática Financiera
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{mb:1}}>
                  El Curso de Matemática Financiera te proporciona las
                  herramientas necesarias para comprender conceptos financieros
                  y aplicar habilidades matemáticas en el ámbito empresarial y
                  personal. Aprende sobre tasas de interés, valor del dinero en
                  el tiempo, evaluación de proyectos de inversión y análisis
                  financiero. ¡Amplía tus conocimientos y toma decisiones
                  financieras informadas!
                </Typography>
                <CardMedia
                  component="img"
                  height="250"
                  image="/images/contabilidad.jpeg"
                  alt="contabilidad"
                  sx={{ marginTop: "auto" }}
                />
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} sm={4}>
            <Card sx={{ height: "100%" }}>
              <CardContent>
                <Typography variant="h5" component="h2">
                  Curso Oficial de pasteleria profesional
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{mb:2}}>
                  El Curso Oficial de Pastelería Profesional es una oportunidad
                  única para adentrarte en el fascinante mundo de la pastelería.
                  Aprenderás técnicas y secretos de la repostería de la mano de
                  chefs expertos. ¡Disfruta de una experiencia dulce y descubre
                  tu pasión por la pastelería!
                </Typography>
                <CardMedia
                  component="img"
                  height="250"
                  image="/images/pasteleria.jpg"
                  alt="pasteleria"
                  sx={{ marginTop: "auto" }}
                />
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};
export default WelcomePage;
