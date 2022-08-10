import React from "react";
import "antd/dist/antd.css";
import LocalStorageService from "../util/LocalStorageUtil";
import jwt from "jwt-decode";
import { Link } from "react-router-dom";
import AuthService from "../service/AuthService";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";

const UserActions = (props) => {
    const navigate = useNavigate();
    const { id } = useParams();

    const signOut = () => {
        AuthService.signout();
        navigate("/");
        window.location.reload();
    };

    const token = LocalStorageService.getToken();

    if (!token) {
        return;
    }

    const user = jwt(token);

    if (user.id == id) {
        return (
            <div>
                <h3>Actions:</h3>
                <button onClick={signOut}>Sign out</button>
            </div>
        );
    } else {
        return;
    }
};

export default UserActions;
