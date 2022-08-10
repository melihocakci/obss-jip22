import LocalStorageService from "../util/LocalStorageUtil";
import jwt from "jwt-decode";

const isAuthenticated = () => {
    const token = LocalStorageService.getToken();

    if (!token) {
        return false;
    }

    const user = jwt(token);

    if (!user || !user.id) {
        return false;
    }

    return true;
};

const isAdmin = () => {
    const token = LocalStorageService.getToken();

    if (!token) {
        return false;
    }

    const user = jwt(token);

    if (!user) {
        return false;
    }

    if (user.role != "ROLE_ADMIN") {
        return false;
    }

    return true;
};

const isUser = () => {
    const token = LocalStorageService.getToken();

    if (!token) {
        return false;
    }

    const user = jwt(token);

    if (!user) {
        return false;
    }

    if (user.role != "ROLE_USER") {
        return false;
    }

    return true;
};

const getId = () => {
    const token = LocalStorageService.getToken();

    if (!token) {
        return null;
    }

    const user = jwt(token);

    if (!user) {
        return null;
    }

    return user.id;
};

export default { isAdmin, isUser, isAuthenticated, getId };
