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

    const _fetchAuthUser = async (params) => {
        const response = await axios.get("http://localhost:8080/api/user/profile");

        if (!response) {
            console.log("An error occured");
            //ToDo: Display error message to user not just log it
            //Ex: https://www.npmjs.com/package/react-toastify
            return;
        }

        return response.data.body;
    };

    const _fetchUser = async (id) => {
        const response = await axios.get("http://localhost:8080/api/user/" + id);

        if (!response) {
            console.log("An error occured");
            //ToDo: Display error message to user not just log it
            //Ex: https://www.npmjs.com/package/react-toastify
            return;
        }

        return response.data.body;
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
    };
})();

export default UserService;
