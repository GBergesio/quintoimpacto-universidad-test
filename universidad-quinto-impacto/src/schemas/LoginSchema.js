import * as Yup from "yup";

export const LoginSchema = Yup.object({
  email: Yup.string().required("Ingresa tu email"),
  password: Yup.string().required("Ingresa tu contraseña"),
});
