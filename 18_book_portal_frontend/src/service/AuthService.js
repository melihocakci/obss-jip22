import axios from "axios";
import LocalStorageUtil from "../util/LocalStorageUtil";

const _signin = async (credentials) => {
  let response;
  try {
    response = await axios.post("http://localhost:8080/api/auth", credentials);
    if (response && response.data) {
      const token = response.data.token;
      LocalStorageUtil.setToken(token);
    }
  } catch (error) {
    console.log(error);
  }

  return response;
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
