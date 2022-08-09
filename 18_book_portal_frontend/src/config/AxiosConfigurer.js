import axios from "axios";
import LocalStorageUtil from "../util/LocalStorageUtil";

const AxiosConfigurer = (function () {
    const _configure = () => {
        // Add a request interceptor
        axios.interceptors.request.use(
            (config) => {
                const token = LocalStorageUtil.getToken();
                if (token) {
                    config.headers["Authorization"] = "Bearer " + token;
                }
                // config.headers['Content-Type'] = 'application/json';
                return config;
            },
            (error) => {
                Promise.reject(error);
            }
        );
    };

    return {
        configure: _configure,
    };
})();

export default AxiosConfigurer;
