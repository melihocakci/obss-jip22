import axios from "axios";
import LocalStorageService from "../util/LocalStorageUtil";

const BookService = (function () {
    const _fetchBooks = async (params) => {
        const response = await axios.get("http://localhost:8080/api/book", {
            params: {
                results: params.pagination.pageSize,
                page: params.pagination.current,
                ...params,
            },
        });

        if (!response) {
            console.log("An error occured");
            //ToDo: Display error message to user not just log it
            //Ex: https://www.npmjs.com/package/react-toastify
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

    return {
        fetchBooks: _fetchBooks,
        fetchBook: _fetchBook,
        createBook: _createBook,
    };
})();

export default BookService;
