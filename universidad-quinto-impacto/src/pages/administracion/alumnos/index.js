import CommonLayout from "@/components/Layout/CommonLayout";
import Alumnos from "@/components/Tables/Alumnos";
import React from "react";

export default function index() {
  return (
    <>
      <CommonLayout componente={<Alumnos />} />
    </>
  );
}
