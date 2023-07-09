import CommonLayout from "@/components/Layout/CommonLayout";
import CommonUserTable from "@/components/Tables/CommonUserTable";
import getData from "@/utils/axiosGet";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useState } from "react";

export default function index() {
  const [userSelected, setUserSelected] = useState([]);
  const uri = "/alumnos/current";
  const uris = [uri, uri + "/id/" + `${userSelected.alumno?.id}`];

  const router = useRouter();
  const goTo = (site) => {
    router.push(site, undefined, { shallow: true });
  };
  const [userLogged, setUserLogged] = useState([]);

  const refreshData = async (endpoint, setDataFunc) => {
    return await getData(endpoint, goTo).then((res) => {
      setDataFunc(res.dto);
    });
  };

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUserLogged = async () => {
      try {
        const userData = await getData("/currentUser", goTo);
        if (userData && userData.dto) {
          setUserLogged(userData.dto);
          setLoading(false);
        } else {
          setUserLogged({});
        }
      } catch (error) {
        console.log(error);
      }
    };

    fetchUserLogged();
  }, []);

  useEffect(() => {
    if (!loading) {
      if (userLogged.tipo === "alumno") {
        goTo("/dashboard");
      } else if (userLogged.deleted) {
        goTo("/dashboard");
      }
    }
  }, [loading, userLogged]);
  const initialValues = {
    nombre: userSelected.length === 0 ? "" : userSelected.alumno.nombre,
    apellido: userSelected.length === 0 ? "" : userSelected.alumno.apellido,
    dni: userSelected.length === 0 ? "" : userSelected.alumno.dni,
    celular: userSelected.length === 0 ? "" : userSelected.alumno.celular,
    email: userSelected.length === 0 ? "" : userSelected.alumno.email,
    deleted: userSelected.length === 0 ? false : userSelected.alumno.deleted,
  };

  return (
    <>
      <CommonLayout
        componente={
          <CommonUserTable
            uris={uris}
            setUserSelected={setUserSelected}
            initialValues={initialValues}
            isDeleted={
              userSelected.length === 0
                ? "Deshabilitar Alumno"
                : userSelected.alumno.deleted
                ? "El alumno está deshabilitado"
                : "Deshabilitar Alumno"
            }
            type={"alumno"}
          />
        }
      />
    </>
  );
}
