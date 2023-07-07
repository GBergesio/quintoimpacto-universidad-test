export const getToken = () => {
  if (typeof window !== "undefined") {
    const token = localStorage.getItem("SSN_TKN");

    return token !== null ? token : null;
  }
};

export const cleanToken = () => {
  if (typeof window !== "undefined") {
    localStorage.removeItem("SSN_TKN");
  }
};

export const checkTypeUser = (usuario) => {
  const tipoUsuarioMap = {
    administrador: "administrador",
    profesor: "profesor",
    alumno: "alumno",
  };

  return tipoUsuarioMap[usuario.tipo] || "null";
};
