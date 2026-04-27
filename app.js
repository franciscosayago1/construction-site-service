const API = "http://localhost:8080/v1";

/* ---------- AUTH ---------- */

function getUser() {
    return JSON.parse(localStorage.getItem("user"));
}

function requireLogin() {
    const user = getUser();
    if (!user) {
        window.location.href = "login.html";
    }
    return user;
}

function requireRole(role) {
    const user = requireLogin();
    if (user.role !== role) {
        alert("Access denied");
        window.location.href = "dashboard.html";
    }
    return user;
}

/* ---------- API WRAPPER ---------- */

async function api(url, options = {}) {
    const res = await fetch(API + url, {
        headers: { "Content-Type": "application/json" },
        ...options
    });

    if (!res.ok) {
        const msg = await res.text();
        console.error("API ERROR:", msg);
        throw new Error(msg);
    }

    return res.json();
}