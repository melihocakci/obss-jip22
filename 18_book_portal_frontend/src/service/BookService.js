import axios from "axios";
import LocalStorageService from "../util/LocalStorageUtil";

const BookService = (function () {
  const _fetchBooks = async (params) => {
    const response = await axios.get("http://localhost:8080/api/book", { params });

    if (!response) {
      console.log("An error occured");
      //ToDo: Display error message to user not just log it
      //Ex: https://www.npmjs.com/package/react-toastify
      return;
    }

    return response.data.body;
  };

  const _fetchBookCount = async () => {
    let response;
    try {
      response = await axios.get("http://localhost:8080/api/book/count");
    } catch (error) {
      console.log(error);
      return;
    }

    return response.data.body;
  };

  const _fetchBook = async (id) => {
    const response = await axios.get("http://localhost:8080/api/book/" + id);

    if (!response) {
      console.log("An error occured");
      //ToDo: Display error message to user not just log it
      //Ex: https://www.npmjs.com/package/react-toastify
      return;
    }

    return response.data.body;
  };

  const _createBook = async (credentials) => {
    const response = await axios.post("http://localhost:8080/api/book/", credentials);

    if (!response) {
      console.log("An error occured");
      //ToDo: Display error message to user not just log it
      //Ex: https://www.npmjs.com/package/react-toastify
      return;
    }

    return response;
  };

  const _updateBook = async (bookId, credentials) => {
    const response = await axios.put("http://localhost:8080/api/book/" + bookId, credentials);

    if (!response) {
      console.log("An error occured");
      //ToDo: Display error message to user not just log it
      //Ex: https://www.npmjs.com/package/react-toastify
      return;
    }

    return response;
  };

  const _removeBook = async (bookId) => {
    const response = await axios.delete("http://localhost:8080/api/book/" + bookId);

    if (!response) {
      console.log("An error occured");
      //ToDo: Display error message to user not just log it
      //Ex: https://www.npmjs.com/package/react-toastify
      return;
    }

    return response;
  };

  return {
    fetchBooks: _fetchBooks,
    fetchBook: _fetchBook,
    createBook: _createBook,
    removeBook: _removeBook,
    updateBook: _updateBook,
    fetchBookCount: _fetchBookCount,
  };
})();

export default BookService;
