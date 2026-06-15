import { type RouteConfig, index, layout, route } from "@react-router/dev/routes";

export default [
    layout("routes/home.tsx", [
        index("routes/main-page.tsx"),
        route("/sections", "routes/welcome-page.tsx"),
        route("/register", "routes/register-page.tsx"),
        route("/login", "routes/login-page.tsx"),
        route("/list-users", "routes/list-users-page.tsx"),
        route("/notification", "routes/notification-page.tsx"),

        route("/objects/:type", "routes/section-page.tsx"),
        route("/objects/:type/:id", "routes/object-page.tsx"),
        route("/new-note/:type/:objectId/new", "routes/new-note-page.tsx"),
        route("/new-object/:type", "routes/new-object-page.tsx"),

        route("/profile/:id?", "routes/profile-page.tsx"),

        route("/statistics/:id", "routes/statistics-page.tsx"),
    ]),
] satisfies RouteConfig;
