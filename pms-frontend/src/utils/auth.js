export const saveUser = (user) => {
    localStorage.setItem("token", user.token);
    localStorage.setItem("role", user.role);
    localStorage.setItem("userId", user.id);
    localStorage.setItem("name", user.name);
    localStorage.setItem("email", user.email);
};

export const logout = () => {
    localStorage.clear();
};

export const getRole = () => {
    return localStorage.getItem("role");
};

export const isLoggedIn = () => {
    return !!localStorage.getItem("token");
};
