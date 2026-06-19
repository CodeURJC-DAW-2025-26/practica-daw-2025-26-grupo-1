import type { UserBasicDTO } from "~/dtos/UserBasicDTO";

const API_ADMIN_URL = "/api/v1/users";
const API_IMAGES_URL = "/api/v1/images";

export async function getUser(id: string): Promise<UserBasicDTO> {
    const res = await fetch(`${API_ADMIN_URL}/${id}`);
    if (!res.ok) {
        throw new Error("Usuario no encontrado.");
    }
    return await res.json();
}


export async function getUsers(): Promise<UserBasicDTO[]> {
    const res = await fetch(`${API_ADMIN_URL}/`);
    if (!res.ok) {
        throw new Error("Error al obtener usuarios.");
    }

    return await res.json();
}


export async function updateUser(
    id: number,
    name: string,
    removeImage: boolean,
    imageFile?: File | null,
    userImageId?: number 
): Promise<UserBasicDTO> {
    
    const updateData = { name: name };

    const response = await fetch(`${API_ADMIN_URL}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json" 
        },
        body: JSON.stringify(updateData)
    });

    if (!response.ok) {
        throw new Error("Se ha producido un error al editar los datos del usuario.");
    }

    if (imageFile && userImageId) {
        const formData = new FormData();
        formData.append("imageFile", imageFile);

        const imgRes = await fetch(`/api/v1/images/${userImageId}/media`, {
            method: "PUT",
            body: formData
        });

        if (!imgRes.ok) {
            throw new Error("Datos actualizados, pero falló el reemplazo de la imagen.");
        }
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