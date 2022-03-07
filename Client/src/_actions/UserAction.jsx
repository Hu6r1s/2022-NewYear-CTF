export function LoginUser(LoginResult) {
    return {
        type: "LOGIN_USER",
        payload: LoginResult
    };
}

export function LogoutUser() {
    return {
        type: "LOGOUT_USER"
    };
}
export function Init() {
    return {
        type: "INIT"
    };
}
export function RefreshToken() {
    return {
        type: "REFRESH_TOKEN"
    };
}
