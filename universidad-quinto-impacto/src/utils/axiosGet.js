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

const getData = async (servlet, goTo) => {
  try {
    const { data, status } = await axios.get(`${apiUrl}${servlet}`, config);
    if (status === 200) {
      return data;
    } else {
      console.log("ELSE GET DATA");
    }
  } catch (error) {
    if (error.response.status === 401) {
      console.log("aca?");
        goTo("/login")
    }
  }
};

export default getData;
