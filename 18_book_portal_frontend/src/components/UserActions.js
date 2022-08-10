import React from "react";
import "antd/dist/antd.css";
import LocalStorageService from "../util/LocalStorageUtil";
import jwt from "jwt-decode";
import { Link } from "react-router-dom";
import AuthService from "../service/AuthService";
import { useNavigate } from "react-router-dom";
import UserService from "../service/UserService";

const UserActions = ({ userId }) => {
    const navigate = useNavigate();

    const signOut = () => {
        AuthService.signout();
        navigate("/login");
        window.location.reload();
    };

    const removeUser = async () => {
        const response = await UserService.removeUser(userId);
        if (response) {
            navigate("/users");
            alert("User deleted");
        }
    };

    const removeThisUser = async () => {
        const response = await UserService.removeUser("");
        if (response) {
            AuthService.signout();
            navigate("/");
            alert("Account deleted");
            window.location.reload();
        }
    };

    const token = LocalStorageService.getToken();

    if (!token) {
        return;
    }

    const user = jwt(token);

    if (user.id == userId) {
        return (
            <div>
                <h3>Actions:</h3>
                <button onClick={signOut}>Sign out</button>
                <button onClick={removeThisUser}>Delete Account</button>
                <button
                    onClick={() => {
                        navigate("/users/" + userId + "/update");
                    }}>
                    Update Account
                </button>
            </div>
        );
    } else if (user.role == "ROLE_ADMIN") {
        return (
            <div>
                <button onClick={removeUser}>Delete User</button>
                <button
                    onClick={() => {
                        navigate("/users/" + userId + "/update");
                    }}>
                    Update User
                </button>
            </div>
        );
    }
};

export default UserActions;
