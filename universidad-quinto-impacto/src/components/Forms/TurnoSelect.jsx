import React from "react";
import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";

const TurnoSelect = ({ value, onChange }) => {
  return (
    <FormControl sx={{ minWidth: 140, mr: 3 }}>
      <InputLabel id="turno-label">Turno</InputLabel>
      <Select
        labelId="turno-label"
        id="turno-select"
        value={value}
        onChange={onChange}
        sx={{ minWidth: 150, fontSize: "0.875rem" }}
      >
        <MenuItem value="M">Mañana</MenuItem>
        <MenuItem value="T">Tarde</MenuItem>
        <MenuItem value="N">Noche</MenuItem>
      </Select>
    </FormControl>
  );
};

export default TurnoSelect;
