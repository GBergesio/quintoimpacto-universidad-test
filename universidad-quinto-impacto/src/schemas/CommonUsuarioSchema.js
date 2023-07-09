import * as Yup from "yup";

export const CommonUsuarioSchema = Yup.object({
  nombre: Yup.string()
    .required("Ingresa tu nombre")
    .min(2, "Mínimo 2 caracteres"),
  apellido: Yup.string()
    .required("Ingresa tu apellido")
    .min(2, "Mínimo 2 caracteres"),
  dni: Yup.string().required("Ingresa tu DNI"),
  celular: Yup.string()
    .required("Ingresa tu Telefono")
    .min(5, "Mínimo 5 caracteres"),
  email: Yup.string()
    .required("Ingresa tu email")
    .min(5, "Mínimo 5 caracteres"),
});
