import axios from "axios";
import LocalStorageUtil from "../util/LocalStorageUtil";

const AuthService = (function () {
    const _signin = async (credentials) => {
        let token = null;

        try {
            const response = await axios.post("http://localhost:8080/api/auth", credentials);
            if (response && response.data) {
                token = response.data.token;
                LocalStorageUtil.setToken(token);
            }
        } catch (error) {
            console.log(error);
        }

        return token;
    };

    return {
        signin: _signin,
    };
})();

export default AuthService;
