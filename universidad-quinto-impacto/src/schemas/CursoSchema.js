import * as Yup from "yup";

export const CursoSchema = Yup.object({
  nombre: Yup.string()
    .required("Ingresa un nombre para el curso")
    .min(5, "Mínimo 5 caracteres"),
});
