import axios from "axios";

const host = "http://localhost:8080";

const _fetchBooks = async (params) => {
  let response;
  try {
    response = await axios.get(host + "/api/book", { params });
    console.log(response);
  } catch (error) {
    console.log(error);
    response = error.response;
  }

  return response.data;
};

const _fetchBook = async (id) => {
  let response;
  try {
    response = await axios.get(host + "/api/book/" + id);
    console.log(response);
  } catch (error) {
    console.log(error);
    response = error.response;
  }

  return response.data;
};

const _createBook = async (credentials) => {
  let response;
  try {
    response = await axios.post(host + "/api/book/", credentials);
    console.log(response);
  } catch (error) {
    console.log(error);
    response = error.response;
  }

  return response.data;
};

const _updateBook = async (bookId, credentials) => {
  let response;
  try {
    response = await axios.put(host + "/api/book/" + bookId, credentials);
    console.log(response);
  } catch (error) {
    console.log(error);
    response = error.response;
  }

  return response.data;
};

const _removeBook = async (bookId) => {
  let response;
  try {
    response = await axios.delete(host + "/api/book/" + bookId);
    console.log(response);
  } catch (error) {
    console.log(error);
    response = error.response;
  }

  return response.data;
};

export default {
  fetchBooks: _fetchBooks,
  fetchBook: _fetchBook,
  createBook: _createBook,
  removeBook: _removeBook,
  updateBook: _updateBook,
};
