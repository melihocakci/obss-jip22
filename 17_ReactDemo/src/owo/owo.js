import axios from "axios";

const INSTRUCTOR = "melih";
const PASSWORD = "123";
const API_URL = "http://localhost:8080";

/*
export default () => {
    console.log("executed service");
    axios.get(`${INSTRUCTOR_API_URL}/basicauth`, {
        headers: { authorization: "Basic " + window.btoa(INSTRUCTOR + ":" + PASSWORD) },
    });
};
*/

export default () => {
    console.log("executed service");
    axios.get(API_URL + "/api/user/profile", {
        headers: { authorization: "Basic " + window.btoa(INSTRUCTOR + ":" + PASSWORD) },
    });
};
