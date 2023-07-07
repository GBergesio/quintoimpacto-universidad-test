import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";

export default function EstadoSelect({ filterValue, handleFilterChange }) {
  return (
    <FormControl sx={{ m: 1, width: 250 }}>
      <InputLabel id="filter-select-label">Filtrar</InputLabel>
      <Select
        labelId="filter-select-label"
        id="filter-select"
        value={filterValue}
        onChange={handleFilterChange}
      >
        <MenuItem value="todos">Mostrar todos</MenuItem>
        <MenuItem value="habilitados">Mostrar habilitados</MenuItem>
        <MenuItem value="deshabilitados">Mostrar deshabilitados</MenuItem>
      </Select>
    </FormControl>
  );
}