import type { UserBasicDTO } from "~/dtos/UserBasicDTO";
import type { UserStatisticsDTO } from "~/dtos/UserStatisticsDTO";
import type { MuseumObjectBasicDTO } from "~/dtos/MuseumObjectBasicDTO";

const API_USERS_URL = "/api/v1/users";

export async function register(name: string, password: string): Promise<UserBasicDTO> {
    
    const registerData = {
        name: name,
        username: name,      
        password: password,   
        encodedPassword: password, 
        roles: ["USER"]
    };

    const response = await fetch(`${API_USERS_URL}/`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(registerData) 
    });

    if (!response.ok) {
        throw new Response("No se ha podido registrar al usuario.");
    }

    return await response.json();
}


export async function replaceUserImage(id: number, imageFile: File): Promise<void> {
    const formData = new FormData();

    formData.append("imageFile", imageFile);

    const response = await fetch(`${API_USERS_URL}/${id}/media`, {
        method: "PUT",
        body: formData
    });

    if (!response.ok) {
        throw new Error("La imagen del objeto no ha podido ser actualizada.");
    }
}


export async function getMyStats(): Promise<UserStatisticsDTO> {
  const response = await fetch(`${API_USERS_URL}/me/statistics`);

  if (!response.ok) {
    throw new Error("No se han podido obtener las estadísticas del usuario.");
  }

  return await response.json();
}


export async function markObjectAsSeen(objectId: number): Promise<MuseumObjectBasicDTO> {
  const response = await fetch(`${API_USERS_URL}/me/seen/${objectId}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ objectId }),
  });

  if (!response.ok) {
    throw new Error("No se ha podido marcar el objeto como visto.");
  }

  return await response.json();
}