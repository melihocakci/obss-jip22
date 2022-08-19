import axios from "axios";

const _fetchBooks = async (params) => {
  let response;
  try {
    response = await axios.get("http://localhost:8080/api/book", { params });
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
    response = await axios.get("http://localhost:8080/api/book/" + id);
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
    response = await axios.post("http://localhost:8080/api/book/", credentials);
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
    response = await axios.put(
      "http://localhost:8080/api/book/" + bookId,
      credentials
    );
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
    response = await axios.delete("http://localhost:8080/api/book/" + bookId);
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
