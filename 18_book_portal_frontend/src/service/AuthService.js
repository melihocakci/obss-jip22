import axios from "axios";
import LocalStorageUtil from "../util/LocalStorageUtil";

const host = "http://localhost:8080";

const _signin = async (credentials) => {
  let response;

  try {
    response = await axios.post(host + "/api/auth", credentials);
    LocalStorageUtil.setToken(response.data.body);
  } catch (error) {
    console.log(error);
    response = error.response;
  }

  return response.data;
};

const _signout = async () => {
  try {
    LocalStorageUtil.clearToken();
  } catch (error) {
    console.log(error);
  }
};

export default {
  signin: _signin,
  signout: _signout,
};
