import axios from "axios";
import { getToken } from "./security";

const apiUrl = "http://localhost:8080";

const token = getToken();

const config = {
  headers: {
    "Content-type": "application/json",
    Authorization: `Bearer ${token}`,
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-methods": "*",
  },
};
const deleteData = async (servlet, handleOpenSnackBar) => {
  const res = await axios
    .delete(`${apiUrl}${servlet}`, config)
    .then((res) => {
      handleOpenSnackBar(res.data.message, "success");
    })
    .catch((error) => {
      const errorMessage =
        error.response.data.message || "Se ha producido un error";
      handleOpenSnackBar(errorMessage, "error");
    });
};

export default deleteData;
