import LocalStorageService from "./LocalStorageUtil";
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

  return user.role === "admin";
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

  return user.role === "user";
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

const get = () => {
  const token = LocalStorageService.getToken();

  if (token) {
    return jwt(token);
  }
};

export default { isAdmin, isUser, isAuthenticated, getId, get };
