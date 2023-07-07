import React, { useEffect, useState } from "react";
import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";

const ProfesorSelect = ({ value, onChange, dataProfesores }) => {
  const [profesores, setProfesores] = useState([]);
  useEffect(() => {
    const profesoresData = dataProfesores.map((item) => item.profesor);
    setProfesores(profesoresData);
  }, [dataProfesores]);

  return (
    <FormControl sx={{ minWidth: 250 }}>
      <InputLabel id="profesor-label">Profesor</InputLabel>
      <Select
        labelId="profesor-label"
        id="profesor-select"
        value={value}
        onChange={onChange}
        sx={{ minWidth: 120, fontSize: "0.875rem" }}
      >
        {profesores.map((profesor) => (
          <MenuItem key={profesor.id} value={profesor.id}>
            {profesor.nombre + " " + profesor.apellido}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
};

export default ProfesorSelect;
