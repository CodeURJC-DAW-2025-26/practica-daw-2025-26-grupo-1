import type { UserBasicDTO } from "~/dtos/UserBasicDTO";

const API_ADMIN_URL = "/api/v1/users";
const PAGE_SIZE = 10;

export type UserPageResult = {
    items: UserBasicDTO[];
    hasNext: boolean;
}


export async function getUser(id: string): Promise<UserBasicDTO> {
    const res = await fetch(`${API_ADMIN_URL}/${id}`);
    if (!res.ok) {
        throw new Error("Usuario no encontrado.");
    }
    return await res.json();
}


export async function getUsers(page: number): Promise<UserPageResult> {
    const res = await fetch(`${API_ADMIN_URL}/?page=${page}&size=${PAGE_SIZE}`);
    if (!res.ok) {
        throw new Error("Error al obtener usuarios.");
    }

    const data = await res.json();

    if (Array.isArray(data?.content)) {
        return { 
            items: data.content, 
            hasNext: data.last === false 
        };
    }

    return { items: [], hasNext: false };
}


export async function updateUser(id: number, name: string, roles: string[]): Promise<UserBasicDTO> {
    const response = await fetch(`${API_ADMIN_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, roles }),
    });

    if (!response.ok) {
        throw new Error("Se ha producido un error al editar al usuario.");
    }

    return await response.json();
}


export async function deleteUser(id: number): Promise<void> {
    const response = await fetch(`${API_ADMIN_URL}/${id}`, {
        method: "DELETE",
    });

    if (!response.ok) {
        throw new Error("Se ha producido un error al eliminar al usuario.");
    }
}