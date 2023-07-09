import CommonLayout from "@/components/Layout/CommonLayout";
import CommonUserTable from "@/components/Tables/CommonUserTable";
import getData from "@/utils/axiosGet";
import { useRouter } from "next/router";
import React, { useEffect } from "react";
import { useState } from "react";

export default function index() {
  const [userSelected, setUserSelected] = useState([]);
  const uri = "/administradores/current";
  const uris = [uri, uri + "/id/" + `${userSelected.administrador?.id}`];

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

  //USE EFFECT
  useEffect(() => {
    refreshData("/currentUser", setUserLogged);
  }, []);

  useEffect(() => {
    const fetchUserLogged = async () => {
      try {
        if (userLogged !== "administrador") {
          goTo("/dashboard");
        } else if (userLogged.deleted) {
          goTo("/dashboard");
        }
      } catch (error) {
        console.log(error);
      }
    };

    fetchUserLogged();
  }, []);

  const initialValues = {
    nombre: userSelected.length === 0 ? "" : userSelected.administrador.nombre,
    apellido:
      userSelected.length === 0 ? "" : userSelected.administrador.apellido,
    dni: userSelected.length === 0 ? "" : userSelected.administrador.dni,
    celular:
      userSelected.length === 0 ? "" : userSelected.administrador.celular,
    email: userSelected.length === 0 ? "" : userSelected.administrador.email,
    deleted:
      userSelected.length === 0 ? false : userSelected.administrador.deleted,
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
                ? "Deshabilitar Administrador"
                : userSelected.administrador.deleted
                ? "El administrador está deshabilitado"
                : "Deshabilitar Administrador"
            }
            type={"administrador"}
          />
        }
      />
    </>
  );
}
