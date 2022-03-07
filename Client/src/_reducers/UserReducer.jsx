export default function (state = { login: { success: false } }, action) {
    switch (action.type) {
        case "LOGIN_USER":
            return { ...state, login: action.payload };
        case "LOGOUT_USER":
            return { ...state, login: { success: false } };
        case "REFRESH_TOKEN":
            return { ...state, login: { refreshToken: action } };
        case "INIT":
            return { login: { success: false } };
        default:
            return state;
    }
}
