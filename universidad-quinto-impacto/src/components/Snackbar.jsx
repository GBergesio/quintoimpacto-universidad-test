import { Snackbar, Alert } from "@mui/material";

const UtilSnackBar = ({ open, handleCloseSnackBar, severity, body }) => {
  return (
    <Snackbar open={open} autoHideDuration={2000} onClose={handleCloseSnackBar}>
      <Alert
        onClose={handleCloseSnackBar}
        severity={severity}
        sx={{ width: "100%" }}
      >
        {body}
      </Alert>
    </Snackbar>
  );
};

export default UtilSnackBar;
