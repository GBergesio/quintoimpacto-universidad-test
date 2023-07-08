import axios from "axios";
import { getToken } from "./security";

const apiUrl = "http://localhost:8080";

const token = getToken();

const config = {
  headers: {
    Authorization: `Bearer ${token}`,
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-methods": "*",
  },
};
const createData = async (
  params,
  servlet,
  handleClose,
  handleOpenSnackBar,
  resetForm
) => {
  const res = await axios
    .post(`${apiUrl}${servlet}`, params, config)
    .then((res) => {
      handleClose();
      handleOpenSnackBar(res.data.message, "success");
      resetForm();
    })
    .catch((error) => {
      console.log(error);
      // if (error) {
      //   handleOpenSnackBar(error.response.data.message, "error");
      // } else {
      //   handleOpenSnackBar(error.response.data.message, "error");
      // }
    });
};

export default createData;
