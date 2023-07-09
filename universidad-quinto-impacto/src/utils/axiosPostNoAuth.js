import axios from "axios";

const apiUrl = "http://localhost:8080";

const config = {
  headers: {
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-methods": "*",
  },
};
const createDataNoAuth = async (
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
      const errorMessage =
        error.response.data.message || "Se ha producido un error";
      handleOpenSnackBar(errorMessage, "error");
    });
};

export default createDataNoAuth;
