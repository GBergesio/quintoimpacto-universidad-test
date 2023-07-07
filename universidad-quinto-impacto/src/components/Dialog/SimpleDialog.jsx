import { Dialog, DialogContent, DialogTitle } from "@mui/material";
import React from "react";

export default function SimpleDialog({ open, close, form, titulo }) {
  return (
    <Dialog open={open} onClose={close}>
      <DialogTitle>{titulo}</DialogTitle>
      <DialogContent>{form}</DialogContent>
    </Dialog>
  );
}
