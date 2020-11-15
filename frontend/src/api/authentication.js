import axios from "axios";

const REFRESH_TOKEN_KEY = "refresh_key";
const ACCESS_TOKEN_KEY = "access_key";

const authApi = axios.create({
    baseURL: "http://localhost:8080/api/auth/",
    responseType: "json"
});

async function updateApiTokens() {
    const refresh = localStorage.getItem(REFRESH_TOKEN_KEY);
    if (refresh) {
        const res = await authApi.post("/refresh", refresh, {
            headers: { 'Content-Type': 'text/plain' }
        });
        if (res.status === 200) {
            return createApi(res.data);
        } else {
            return null;
        }
    }
    return null;
}

async function sign(url, credentials) {
    const res = await authApi.post(url, credentials);
    return createApi(res.data);
}

async function authenticate(credentials) {
    return await sign("/login", credentials);
}

async function registration(credentials) {
    return await sign("/register", credentials);
}

function createApi(tokens) {
    localStorage.setItem(REFRESH_TOKEN_KEY, tokens.refresh);
    localStorage.setItem(ACCESS_TOKEN_KEY, tokens.access);
    return axios.create({
        baseURL: "http://localhost:8080/api/",
        responseType: "json",
        headers: {
            "Authorization": tokens.access
        }
    });
}

function getRole() {
    const token = localStorage.getItem(ACCESS_TOKEN_KEY);
    const payload = token.split(".")[1];
    const jsonPayload = JSON.parse(atob(payload));
    return jsonPayload.role;
}

export  {
    updateApiTokens,
    authenticate,
    registration,
    getRole
}