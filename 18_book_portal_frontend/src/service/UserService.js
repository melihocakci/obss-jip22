import axios from "axios";
import LocalStorageService from "../util/LocalStorageUtil";

const UserService = (function () {
    const _fetchUsers = async (params) => {
        const response = await axios.get("http://localhost:8080/api/user", {
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

    const _fetchAuthUser = async () => {
        const response = await axios.get("http://localhost:8080/api/user/profile");

        if (!response) {
            console.log("An error occured");
            //ToDo: Display error message to user not just log it
            //Ex: https://www.npmjs.com/package/react-toastify
            return;
        }

        return response.data.body;
    };

    const _fetchUser = async (userId) => {
        const response = await axios.get("http://localhost:8080/api/user/" + userId);

        if (!response) {
            console.log("An error occured");
            //ToDo: Display error message to user not just log it
            //Ex: https://www.npmjs.com/package/react-toastify
            return;
        }

        return response.data.body;
    };

    const _addRead = async (bookId) => {
        let response;
        try {
            response = await axios.post("http://localhost:8080/api/user/read/" + bookId);
        } catch (error) {
            console.log(error);
            return;
        }

        return response;
    };

    const _addFavorite = async (bookId) => {
        let response;
        try {
            response = await axios.post("http://localhost:8080/api/user/favorite/" + bookId);
        } catch (error) {
            console.log(error);
            return;
        }

        return response;
    };

    const _removeRead = async (bookId) => {
        let response;
        try {
            response = await axios.delete("http://localhost:8080/api/user/read/" + bookId);
        } catch (error) {
            console.log(error);
            return;
        }

        return response;
    };

    const _removeFavorite = async (bookId) => {
        let response;
        try {
            response = await axios.delete("http://localhost:8080/api/user/favorite/" + bookId);
        } catch (error) {
            console.log(error);
            return;
        }

        return response;
    };

    const _createUser = async (credentials) => {
        let response;
        try {
            response = await axios.post("http://localhost:8080/api/user", credentials);
            console.log(response);
        } catch (error) {
            console.log(error);
            return;
        }

        return response;
    };

    const _delete = async () => {
        const response = await axios.delete("http://localhost:8080/api/user/admin", {});

        return response.data;
    };

    return {
        fetchUsers: _fetchUsers,
        delete: _delete,
        fetchAuthUser: _fetchAuthUser,
        fetchUser: _fetchUser,
        createUser: _createUser,
        addRead: _addRead,
        addFavorite: _addFavorite,
        removeRead: _removeRead,
        removeFavorite: _removeFavorite,
    };
})();

export default UserService;
