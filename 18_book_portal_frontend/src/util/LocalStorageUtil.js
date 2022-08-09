const LocalStorageService = (function () {
    const TOKEN = "token";

    function _setToken(token) {
        localStorage.setItem(TOKEN, token);
    }

    function _getToken() {
        return localStorage.getItem(TOKEN);
    }

    function _clearToken() {
        localStorage.removeItem(TOKEN);
    }

    return {
        setToken: _setToken,
        getToken: _getToken,
        clearToken: _clearToken,
    };
})();

export default LocalStorageService;
