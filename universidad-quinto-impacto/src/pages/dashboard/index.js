import CommonLayout from "@/components/Layout/CommonLayout";
// import Dashboard from "@/components/Dashboard/Dashboard_old";
import Dashboard from "@/components/Dashboard/Dashboard";
import React from "react";

export default function index() {
  return (
    <>
      <CommonLayout componente={<Dashboard/>}/>
    </>
  );
}