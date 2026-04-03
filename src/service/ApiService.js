import { jwtDecode } from "jwt-decode";


export default class ApiService {
    static getHeader() {
        const token = localStorage.getItem('accessToken');

        return {
            Authorization: `Bearer ${token}`,
        };
    }

    static logout() {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("role");
    }

    static isAuthenticated() {
        const token = localStorage.getItem("accessToken");

        return !!token;
    }

    static isAdmin() {
        const token = localStorage.getItem("accessToken");
        if(token && token !== 'undefined' && token !== 'null') {
            try {
                const decodedToken = jwtDecode(token);
                return decodedToken.scope === "ADMIN";
            } catch(e) {
                localStorage.removeItem("accessToken");
                return false;
            }
        }
        return false;
    }

    static isUser() {
        const token = localStorage.getItem("accessToken");
        if(token && token !== 'undefined' && token !== 'null') {
            try {
                const decodedToken = jwtDecode(token);
                return decodedToken.scope === "USER";
            } catch(e) {
                localStorage.removeItem("accessToken");
                return false;
            }
        }

        return false;
    }
}

