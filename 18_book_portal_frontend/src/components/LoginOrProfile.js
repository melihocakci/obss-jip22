import React from "react";
import "antd/dist/antd.css";
import LocalStorageService from "../util/LocalStorageUtil";
import jwt from "jwt-decode";
import { Link } from "react-router-dom";

const LoginOrProfile = (props) => {
    const token = LocalStorageService.getToken();

    if (token) {
        return <Link to={"/users/" + jwt(token).id}>Profile</Link>;
    } else {
        return <Link to={"/login"}>Login</Link>;
    }
};

export default LoginOrProfile;
