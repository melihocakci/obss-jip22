import axios from "axios";

const _fetchUsers = async (params) => {
  let response;
  try {
    response = await axios.get("http://localhost:8080/api/user", { params });
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _fetchUserCount = async () => {
  let response;
  try {
    response = await axios.get("http://localhost:8080/api/user/count");
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _fetchThisUser = async () => {
  let response;
  try {
    response = await axios.get("http://localhost:8080/api/user/profile");
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _fetchUser = async (userId) => {
  let response;
  try {
    response = await axios.get("http://localhost:8080/api/user/" + userId);
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _addRead = async (bookId) => {
  let response;
  try {
    response = await axios.post("http://localhost:8080/api/user/read/" + bookId);
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _addFavorite = async (bookId) => {
  let response;
  try {
    response = await axios.post("http://localhost:8080/api/user/favorite/" + bookId);
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _removeRead = async (bookId) => {
  let response;
  try {
    response = await axios.delete("http://localhost:8080/api/user/read/" + bookId);
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _removeFavorite = async (bookId) => {
  let response;
  try {
    response = await axios.delete("http://localhost:8080/api/user/favorite/" + bookId);
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _createUser = async (credentials) => {
  let response;
  try {
    response = await axios.post("http://localhost:8080/api/user", credentials);
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _removeUser = async (userId) => {
  let response;
  try {
    response = await axios.delete("http://localhost:8080/api/user/" + userId);
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

const _updateUser = async (userId, credentials) => {
  let response;
  try {
    response = await axios.put("http://localhost:8080/api/user/" + userId, credentials);
    console.log(response);
  } catch (error) {
    console.log(error);
  }

  return response.data;
};

export default {
  fetchUsers: _fetchUsers,
  fetchThisUser: _fetchThisUser,
  fetchUser: _fetchUser,
  createUser: _createUser,
  addRead: _addRead,
  addFavorite: _addFavorite,
  removeRead: _removeRead,
  removeFavorite: _removeFavorite,
  removeUser: _removeUser,
  updateUser: _updateUser,
  fetchUserCount: _fetchUserCount,
};
