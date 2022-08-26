import { message } from "antd";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import UserContext from "../context/UserContext";
import AuthService from "../service/AuthService";

export default () => {
  const navigate = useNavigate();
  const { user, setUser } = useContext(UserContext);

  if (user) {
    if (user.exp * 1000 < Date.now()) {
      AuthService.signout();
      setUser(null);
      navigate("/signin");
      message.info("Authentication timed out. Please sign in again.");
    }
  }
};
