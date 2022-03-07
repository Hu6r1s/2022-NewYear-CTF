import axios from "axios";
import history from "../../history/history";

export async function get(url) {
    const headers = {
        Authorization: `${sessionStorage.getItem("accessToken")}`
    };
    try {
        const result = await axios.get(`/api${url}`, {
            headers
        });
        return { data: result.data, status: result.status };
    } catch (err) {
        if (err.response.status === 401) {
            const RefreshResult = await Refresh();
            if (RefreshResult.success) {
                try {
                    const headers = {
                        Authorization: `${sessionStorage.getItem("accessToken")}`
                    };

                    const result = await axios.get(`/api${url}`, {
                        headers
                    });
                    return { data: result.data };
                } catch (err) {
                    sessionStorage.removeItem("accessToken");
                    sessionStorage.removeItem("refreshToken");
                    sessionStorage.removeItem("uid");
                    sessionStorage.removeItem("persist:root");
                    window.location.href = "/login";
                }
            }
        }
    }
}

export async function fileDown(url) {
    try {
        const result = await axios.get(`/api${url}`, {
            responseType: "blob",
            headers: {
                Authorization: `${sessionStorage.getItem("accessToken")}`
            }
        });

        return result;
    } catch (err) {
        if (err.response.status === 401) {
            const RefreshResult = await Refresh();
            if (RefreshResult.success) {
                try {
                    const headers = {
                        Authorization: `${sessionStorage.getItem("accessToken")}`
                    };

                    const result = await axios.get(`/api${url}`, {
                        responseType: "blob",
                        headers: {
                            Authorization: `${sessionStorage.getItem("accessToken")}`
                        }
                    });
                    return result;
                } catch (err) {
                    sessionStorage.removeItem("accessToken");
                    sessionStorage.removeItem("refreshToken");
                    sessionStorage.removeItem("uid");
                    sessionStorage.removeItem("persist:root");
                    window.location.href = "/login";
                }
            }
        }
    }
}

export async function post(url, data) {
    const headers = {
        Authorization: `${sessionStorage.getItem("accessToken")}`
    };
    try {
        const result = await axios.post(`/api${url}`, data, {
            headers
        });
        return { data: result.data };
    } catch (err) {
        if (err.response.status === 401) {
            const RefreshResult = await Refresh();
            if (RefreshResult.success) {
                try {
                    const headers = {
                        Authorization: `${sessionStorage.getItem("accessToken")}`
                    };

                    const result = await axios.post(`/api${url}`, data, {
                        headers
                    });
                    return { data: result.data };
                } catch (err) {
                    sessionStorage.removeItem("accessToken");
                    sessionStorage.removeItem("refreshToken");
                    sessionStorage.removeItem("uid");
                    sessionStorage.removeItem("persist:root");
                    window.location.href = "/login";
                }
            }
        } else {
            return err.response;
        }
    }
}
export async function login(data) {
    const result = await axios.post(`/api/login`, data);
    return { data: result.data };
}

export async function put(url, data) {
    const headers = {
        Authorization: `${sessionStorage.getItem("accessToken")}`
    };
    try {
        const result = await axios.put(`/api${url}`, data, {
            headers
        });
        return { data: result.data };
    } catch (err) {
        if (err.response.status === 401) {
            const RefreshResult = await Refresh();
            if (RefreshResult.success) {
                try {
                    const headers = {
                        Authorization: `${sessionStorage.getItem("accessToken")}`
                    };

                    const result = await axios.put(`/api${url}`, data, {
                        headers
                    });
                    return { data: result.data };
                } catch (err) {
                    sessionStorage.removeItem("accessToken");
                    sessionStorage.removeItem("refreshToken");
                    sessionStorage.removeItem("uid");
                    sessionStorage.removeItem("persist:root");
                    window.location.href = "/login";
                }
            }
        }
    }
}

export async function Refresh() {
    const headers = {
        refreshToken: sessionStorage.getItem("refreshToken")
    };
    try {
        const result = await axios.get(`/api/auth/refresh`, {
            headers
        });

        if (result.data.success === true) {
            sessionStorage.setItem("accessToken", result.data.token.accessToken);
            sessionStorage.setItem("refreshToken", result.data.token.refreshToken);
            return { success: true, data: result.data };
        }
    } catch (err) {
        if (err.response.status) {
            sessionStorage.removeItem("accessToken");
            sessionStorage.removeItem("refreshToken");
            sessionStorage.removeItem("uid");
            sessionStorage.removeItem("persist:root");
            window.location.href = "/login";
        }
    }
}
