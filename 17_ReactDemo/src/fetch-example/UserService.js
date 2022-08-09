import axios from "axios";

const _fetchUsers = async (params) => {
    const response = await axios.get("https://randomuser.me/api", {
        params: { results: params.pagination.pageSize * 3, page: params.pagination.current, ...params },
    });

    if (!response) {
        console.log("Cannot fetch data");
    }

    return response;
};

export default _fetchUsers;
